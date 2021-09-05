package com.example.a5sec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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