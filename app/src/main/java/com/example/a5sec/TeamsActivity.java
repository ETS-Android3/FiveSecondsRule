package com.example.a5sec;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeamsActivity extends AppCompatActivity {

    private int Number_of_Points = 10;
    ListView listview;
    Button addButton;
    EditText GetValue;
    String[] ListElements = new String[] {
            "Cats",
            "Dogs",
    };

    final List<String> ListElementsArrayList = new ArrayList<>(Arrays.asList(ListElements));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);
        if (!getIntent().getExtras().isEmpty()) {
            Number_of_Points = getIntent().getExtras().getInt("Points");
        }
        listview = findViewById(R.id.listView1);
        addButton = findViewById(R.id.button7);
        GetValue = findViewById(R.id.editText1);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>
                (TeamsActivity.this, android.R.layout.simple_list_item_1, ListElementsArrayList);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int position = i;
                new AlertDialog.Builder(TeamsActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure ?")
                        .setMessage("Do you want to delete this item")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ListElementsArrayList.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
                //ListElementsArrayList.remove(i);
                //adapter.notifyDataSetChanged();
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListElementsArrayList.add(GetValue.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });
    }
    public void Next_Button(View view){
        Intent intent = new Intent(this, GameActivity.class);
        ArrayList<String> TeamsNames = new ArrayList<String>(ListElementsArrayList);
        intent.putExtra("TeamsNames", TeamsNames);
        intent.putExtra("Points", Number_of_Points);
        startActivity(intent);
    }
}