package proj.fivesecrule;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GameActivity extends AppCompatActivity {

    private List<String> TeamsNames = new ArrayList<String>();
    private int number_of_teams = 2;
    private int Number_of_Points = 10;
    private boolean fine = false;
    private QuestionChanger QS;
    private SoundPool soundPool;
    private int mSoundId = 1;
    private int mStreamId;
    private TeamChanger teamChanger;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        if (!getIntent().getExtras().isEmpty()) {
            Number_of_Points = getIntent().getExtras().getInt("Points");
            TeamsNames = getIntent().getExtras().getStringArrayList("TeamsNames");
            number_of_teams = TeamsNames.size();
            teamChanger = new TeamChanger(number_of_teams, TeamsNames, Number_of_Points);
            TextView textView = findViewById(R.id.textView5);
            textView.setText("Сейчас играет:\n" + teamChanger.get_team_name() + "\n Количество баллов: " + teamChanger.get_team_points());
            fine = getIntent().getExtras().getBoolean("Fine");
        }
        try {
            QS = new QuestionChanger(this);
            Change_Question();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        findViewById(R.id.change_button).setEnabled(false);
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 100);
        soundPool.load(this, R.raw.timer, 1);
        TextView textView = findViewById(R.id.textView5);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowTeams();
            }
        });
    }

    private boolean is_timer_on = false;

    private void CheckPoints(){
        if (teamChanger.Time_to_finish()) {
            String str = teamChanger.Generate_winning_teams();
            new AlertDialog.Builder(GameActivity.this)
                    .setTitle("Congratulation!")
                    .setMessage("Победитель: " + str)
                    .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .show().setCancelable(false);
        }
    }
    private void Change_Question() throws JSONException {
        String a = QS.get_question();
        final TextView tw = findViewById(R.id.textView2);
        tw.setText(a);
    }
    @SuppressLint("SetTextI18n")
    private void Change_PlayingTeam() throws JSONException {
        findViewById(R.id.change_button).setEnabled(true);
        CheckPoints();
        teamChanger.change_playing_team();
        TextView textView = findViewById(R.id.textView5);
        textView.setText("Сейчас играет:\n" + teamChanger.get_team_name() + "\n Количество баллов: " + teamChanger.get_team_points());
        Change_Question();
    }
    private void Fine_Team(){
        if (fine) teamChanger.decrease_points();
    }
    private void ShowTeams(){
        String points = teamChanger.get_all_point();
        new AlertDialog.Builder(GameActivity.this)
                .setTitle("Количество очков")
                .setMessage(points)
                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .show();
    }
    private void OpenDialogWindow(){
        new AlertDialog.Builder(GameActivity.this)
                .setIcon(android.R.drawable.ic_delete)
                .setTitle("Проверка")
                .setMessage("Засчитать ответ?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        teamChanger.increase_points();
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
                        Fine_Team();
                        try {
                            Change_PlayingTeam();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .show().setCancelable(false);
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
            Button button = findViewById(R.id.button3);
            unnable_timer.start();
            Fine_Team();
            try {
                Change_PlayingTeam();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            button.setBackground(getResources().getDrawable(R.drawable.green_button));
            //button.setBackgroundColor(getResources().getColor(R.color.mint));
            button.setText(R.string.stop_botton);
        }
    };

    private void play_sound(){
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        float curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int priority = 1;
        int no_loop = 0;
        float normal_playback_rate = 1f;
        mStreamId = soundPool.play(mSoundId, curVolume, curVolume, priority, no_loop,
                normal_playback_rate);
    }
    public void Answer_Button(View view){
        if (!is_timer_on)
        {
            play_sound();
            timer.start();
            is_timer_on = true;
            Button button = findViewById(R.id.button3);
            button.setBackground(getResources().getDrawable(R.drawable.pink_button));
            button.setText(getResources().getString(R.string.answer_button));
        }
        else
        {
            timer.cancel();
            soundPool.stop(mStreamId);
            is_timer_on = false;
            OpenDialogWindow();
            Button button = findViewById(R.id.button3);
            button.setBackground(getResources().getDrawable(R.drawable.green_button));
            button.setText(R.string.stop_botton);
        }

    }

    public void Change_points(View view){
        if (teamChanger.ifPreviousTeamAvailable())
            new AlertDialog.Builder(GameActivity.this)
                    .setIcon(android.R.drawable.ic_partial_secure)
                    .setTitle("Изменить количество баллов")
                    .setMessage("Уменьшить или увеличить количество баллов команде: " + teamChanger.get_previous_team_name() + "?")
                    .setPositiveButton("Увеличить", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            teamChanger.increase_points_to_previous_team();
                        }
                    })
                    .setNegativeButton("Уменьшить",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            teamChanger.decrease_points_to_previous_team();
                        }
                    })
                    .setNeutralButton("Отмена", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    })
                    .show().setCancelable(false);
        findViewById(R.id.change_button).setEnabled(false);
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                GameActivity.this);
        quitDialog.setTitle("Вы действительно хотите выйти?");

        quitDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        quitDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // nothing
            }
        });

        quitDialog.show().setCancelable(false);
    }

}