package com.example.a5sec;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

public class FiltersActivity extends AppCompatActivity {

    private final int limitUp = 30;
    private final int limitDown = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        final EditText edT = (EditText)findViewById(R.id.editTextTextPersonName);


        edT.addTextChangedListener(new TextWatcher(){
            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable s) {
                int value = s.toString().equals("") ? 0 : Integer.parseInt(s.toString());
                if(value > limitUp || value < limitDown){
                    edT.setText("10");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

    }

    @SuppressLint("SetTextI18n")
    public void Less_Button(View view){
        EditText ET = findViewById(R.id.editTextTextPersonName);
        int number = Integer.parseInt(ET.getText().toString());
        if (number > limitDown)
        {
            number -= 1;
            ET.setText(number+ "");
        }
    }

    @SuppressLint("SetTextI18n")
    public void More_Button(View view){
        EditText ET = findViewById(R.id.editTextTextPersonName);
        int number = Integer.parseInt(ET.getText().toString());
        if (number < limitUp)
        {
            number += 1;
            ET.setText(number+ "");
        }
    }

    public void Next_Button(View view){
        Intent intent = new Intent(this, TeamsActivity.class);
        EditText ET = findViewById(R.id.editTextTextPersonName);
        intent.putExtra("Points", Integer.parseInt(ET.getText().toString()));
        Switch sw = (Switch) findViewById(R.id.switch1);
        intent.putExtra("Fine", sw.isChecked());
        startActivity(intent);
    }
}