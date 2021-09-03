package com.example.a5sec;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GameActivity extends AppCompatActivity {

    private List<String> TeamsNames = new ArrayList<String>();
    private int number_of_teams = 2;
    private int[] TeamsPoints = new int[number_of_teams];
    private int Number_of_Points = 10;
    private int Playing_team = 0;
    private QuestionChanger QS;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        if (!getIntent().getExtras().isEmpty()) {
            Number_of_Points = getIntent().getExtras().getInt("Points");
            TeamsNames = getIntent().getExtras().getStringArrayList("TeamsNames");
            number_of_teams = TeamsNames.size();
            TeamsPoints = new int[number_of_teams];
            TextView textView = findViewById(R.id.textView5);
            textView.setText("Сейчас играет:\n" + TeamsNames.get(Playing_team) + " " + TeamsPoints[Playing_team]);
        }
        try {
            QS = new QuestionChanger(this, Number_of_Points, number_of_teams);
            Change_Question();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean is_timer_on = false;

    private void CheckPoints(){
        List<String> WinningTeams = new ArrayList<>();
        for (int i = 0; i<TeamsPoints.length; i++){
            if (TeamsPoints[i] == Number_of_Points)
                WinningTeams.add(TeamsNames.get(i)+" ");
        }
        if (!WinningTeams.isEmpty()) {
            StringBuilder str = new StringBuilder();
            for (int i =0; i<WinningTeams.size(); i++)
                str.append(WinningTeams.get(i));
            new AlertDialog.Builder(GameActivity.this)
                    .setTitle("Congratulation!")
                    .setMessage("Победитель: " + str)
                    .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .show();
        }
    }
    @SuppressLint("SetTextI18n")
    private void Change_Question() throws JSONException {
        String a = QS.get_question();
        final TextView tw = findViewById(R.id.textView2);
        tw.setText("Назовите  3 \n" + a);
    }
    @SuppressLint("SetTextI18n")
    private void Change_PlayingTeam() throws JSONException {
        if (Playing_team != number_of_teams-1)
            Playing_team++;
        else {
            Playing_team = 0;
            CheckPoints();
        }
        TextView textView = findViewById(R.id.textView5);
        textView.setText("Сейчас играет: \n" + TeamsNames.get(Playing_team) + " " + TeamsPoints[Playing_team]);
        Change_Question();
    }
    private void OpenDialogWindow(){
        new AlertDialog.Builder(GameActivity.this)
                .setIcon(android.R.drawable.ic_delete)
                .setTitle("Проверка")
                .setMessage("Засчитать ответ?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TeamsPoints[Playing_team] += 1;
                        try {
                            Change_PlayingTeam();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("Нет",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            Change_PlayingTeam();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .show();
    }
    private CountDownTimer unnable_timer = new CountDownTimer(2000, 10) {
        @Override
        public void onTick(long l) {
            Button button = findViewById(R.id.button3);
            button.setEnabled(false);
        }

        @Override
        public void onFinish() {
            Button button = findViewById(R.id.button3);
            button.setEnabled(true);
        }
    };
    private CountDownTimer timer = new CountDownTimer(5000, 10) {
        @SuppressLint("SetTextI18n")
        @Override
        public void onTick(long l) {
            TextView time_bar = findViewById(R.id.textView3);
            time_bar.setText((int)l/1000+" : "+(l/10)%100);
        }

        @SuppressLint({"SetTextI18n", "ResourceAsColor"})
        @Override
        public void onFinish() {
            TextView time_bar = findViewById(R.id.textView3);
            time_bar.setText("5 : 00");
            is_timer_on = false;
            try {
                Change_PlayingTeam();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Button button = findViewById(R.id.button3);
            unnable_timer.start();
            button.setBackgroundColor(getResources().getColor(R.color.mint));
            button.setText(R.string.stop_botton);
        }
    };

    public void Answer_Button(View view){
        if (!is_timer_on)
        {
            timer.start();
            is_timer_on = true;
            Button button = findViewById(R.id.button3);
            button.setBackgroundColor(getResources().getColor(R.color.pink));
            button.setText(getResources().getString(R.string.answer_button));
        }
        else
        {
            timer.cancel();
            is_timer_on = false;
            OpenDialogWindow();
            Button button = findViewById(R.id.button3);
            button.setBackgroundColor(getResources().getColor(R.color.mint));
            button.setText(R.string.stop_botton);
        }

    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                GameActivity.this);
        quitDialog.setTitle("Вы действительно выйти?");

        quitDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        quitDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
            }
        });

        quitDialog.show();
    }

}