package com.example.a5sec;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    private boolean is_timer_on = false;
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
            Button button = findViewById(R.id.button3);
            button.setText(getResources().getString(R.string.stop_botton));
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
            Button button = findViewById(R.id.button3);
            button.setText(getResources().getString(R.string.stop_botton));
        }
        String a = "lolik";
        final TextView tw = findViewById(R.id.textView2);
        tw.setText(a);
    }

}