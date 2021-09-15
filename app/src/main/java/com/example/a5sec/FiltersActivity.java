package com.example.a5sec;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
        EditText ET = findViewById(R.id.editTextTextPersonName);
        if (Integer.parseInt(ET.getText().toString())<limitDown || Integer.parseInt(ET.getText().toString())>limitUp)
            {
                AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                    FiltersActivity.this);
                quitDialog.setTitle("Количество очков должно быть от 1 до 30");
                quitDialog.setNegativeButton("ОК", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //
                    }
                });
                quitDialog.show();
                ET.setText(getResources().getString(R.string.DefaultPoints));
            }
        else
        {
            Intent intent = new Intent(this, TeamsActivity.class);
            intent.putExtra("Points", Integer.parseInt(ET.getText().toString()));
            Switch sw = (Switch) findViewById(R.id.switch1);
            intent.putExtra("Fine", sw.isChecked());
            startActivity(intent);
        }
    }
}