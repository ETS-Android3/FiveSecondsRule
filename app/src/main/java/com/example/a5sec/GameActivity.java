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

import java.util.ArrayList;
import java.util.List;


public class GameActivity extends AppCompatActivity {

    private List<String> TeamsNames = new ArrayList<String>();
    private int number_of_teams = 2;
    private int[] TeamsPoints = new int[number_of_teams];
    private int Playing_team = 0;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        if (!getIntent().getExtras().isEmpty()) {
            TeamsNames = getIntent().getExtras().getStringArrayList("TeamsNames");
            number_of_teams = TeamsNames.size();
            TeamsPoints = new int[number_of_teams];
            TextView textView = findViewById(R.id.textView5);
            textView.setText("Now playing:\n" + TeamsNames.get(Playing_team) + " " + TeamsPoints[Playing_team]);
        }
    }

    private boolean is_timer_on = false;

    @SuppressLint("SetTextI18n")
    private void Change_PlayingTeam()
    {
        if (Playing_team != number_of_teams-1)
            Playing_team++;
        else
            Playing_team = 0;
        TextView textView = findViewById(R.id.textView5);
        textView.setText("Now playing:\n" + TeamsNames.get(Playing_team) + " " + TeamsPoints[Playing_team]);
    }
    private void OpenDialogWindow(){
        new AlertDialog.Builder(GameActivity.this)
                .setIcon(android.R.drawable.ic_delete)
                .setTitle("Answer check")
                .setMessage("Should the answer be counted?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TeamsPoints[Playing_team] += 1;
                        Change_PlayingTeam();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Change_PlayingTeam();
                    }
                })
                .show();
    }
    private CountDownTimer timer = new CountDownTimer(5000, 10) {
        @SuppressLint("SetTextI18n")
        @Override
        public void onTick(long l) {
            TextView time_bar = findViewById(R.id.textView3);
            time_bar.setText((int)l/1000+" : "+(l/10)%100);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onFinish() {
            TextView time_bar = findViewById(R.id.textView3);
            time_bar.setText("Time's up!");
            is_timer_on = false;
            Change_PlayingTeam();
            Button button = findViewById(R.id.button3);
            button.setText(R.string.stop_botton);
        }
    };

    public void Answer_Button(View view){
        if (!is_timer_on)
        {
            timer.start();
            is_timer_on = true;
            Button button = findViewById(R.id.button3);
            button.setText(getResources().getString(R.string.answer_button));
        }
        else
        {
            timer.cancel();
            is_timer_on = false;
            OpenDialogWindow();
            Button button = findViewById(R.id.button3);
            button.setText(R.string.stop_botton);
        }
        String a = "lolik";
        final TextView tw = findViewById(R.id.textView2);
        tw.setText(a);
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                GameActivity.this);
        quitDialog.setTitle("You sure you want to quit?");

        quitDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        quitDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
            }
        });

        quitDialog.show();
    }

}