package com.example.a5sec;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;


public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }


    public void Answer_Button(View view){
        CountDownTimer timer = new CountDownTimer(5000, 10) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long l) {
                TextView time_bar = findViewById(R.id.textView3);
                time_bar.setText((int)l/1000+" : "+(l/10)%100);
            }

            @Override
            public void onFinish() {
                TextView time_bar = findViewById(R.id.textView3);
                time_bar.setText("Time's up!");
            }
        }.start();
        String a = "lolik";
        final TextView tw = findViewById(R.id.textView2);
        tw.setText(a);
    }

}