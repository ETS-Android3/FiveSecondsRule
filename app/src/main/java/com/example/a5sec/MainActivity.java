package com.example.a5sec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String[] ListElements = new String[] {
            "Кошечки",
            "Собачки",
    };
    final List<String> ListElementsArrayList = new ArrayList<>(Arrays.asList(ListElements));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void Rules_button(View view){
        //TextView tw = findViewById(R.id.text_of_rules);
        //tw.setText(getResources().getString(R.string.FullRules));
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
    public void startOneOnOne(View view){
        Intent intent = new Intent(this, GameActivity.class);
        ArrayList<String> TeamsNames = new ArrayList<String>(ListElementsArrayList);
        intent.putExtra("TeamsNames", TeamsNames);
        intent.putExtra("Points", 10);
        intent.putExtra("Fine", false);
        startActivity(intent);
    }
    public void startTeamGame(View view){
        Intent intent = new Intent(this, FiltersActivity.class);
        startActivity(intent);
    }
}