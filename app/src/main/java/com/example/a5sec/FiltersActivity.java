package com.example.a5sec;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class FiltersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
    }

    @SuppressLint("SetTextI18n")
    public void Less_Button(View view){
        EditText ET = findViewById(R.id.editTextTextPersonName);
        int number = Integer.parseInt(ET.getText().toString());
        if (number > 1)
        {
            number -= 1;
            ET.setText(number+ "");
        }
    }

    @SuppressLint("SetTextI18n")
    public void More_Button(View view){
        EditText ET = findViewById(R.id.editTextTextPersonName);
        int number = Integer.parseInt(ET.getText().toString());
        if (number < 30)
        {
            number += 1;
            ET.setText(number+ "");
        }
    }

    public void Next_Button(View view){
        Intent intent = new Intent(this, TeamsActivity.class);
        EditText ET = findViewById(R.id.editTextTextPersonName);
        intent.putExtra("Points", Integer.parseInt(ET.getText().toString()));
        startActivity(intent);
    }
}