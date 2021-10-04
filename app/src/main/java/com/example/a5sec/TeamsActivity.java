package com.example.a5sec;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
    private boolean sw = false;
    ListView listview;
    Button addButton;
    EditText GetValue;
    private final int max_teams = 10;
    String[] ListElements = new String[] {
            "Кошечки",
            "Собачки",
    };

    final List<String> ListElementsArrayList = new ArrayList<>(Arrays.asList(ListElements));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);
        if (!getIntent().getExtras().isEmpty()) {
            Number_of_Points = getIntent().getExtras().getInt("Points");
            sw = getIntent().getExtras().getBoolean("Fine");
        }
        listview = findViewById(R.id.listView1);
        addButton = findViewById(R.id.button7);
        GetValue = findViewById(R.id.editText1);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>
                (TeamsActivity.this, R.layout.list_item, ListElementsArrayList);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int position = i;
                new AlertDialog.Builder(TeamsActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Вы уверены ?")
                        .setMessage("Удалить команду?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ListElementsArrayList.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Нет",null)
                        .show();
                //ListElementsArrayList.remove(i);
                //adapter.notifyDataSetChanged();
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String team_name = GetValue.getText().toString().trim();
                boolean exists = false;
                for (int i = 0; i<ListElementsArrayList.size(); i++){
                    if (ListElementsArrayList.get(i).equals(team_name))
                        exists = true;
                }
                if (!team_name.isEmpty()&&ListElementsArrayList.size()!=max_teams&&!exists)
                {
                    ListElementsArrayList.add(team_name);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
    public void Next_Button(View view){
        if (ListElementsArrayList.isEmpty() || ListElementsArrayList.size()==1)
            new AlertDialog.Builder(TeamsActivity.this)
                    .setIcon(android.R.drawable.stat_sys_warning)
                    .setTitle("Ошибка")
                    .setMessage("Должно быть как минимум две команды")
                    .setNegativeButton("Назад",null)
                    .show();
        else
        {
            Intent intent = new Intent(this, GameActivity.class);
            ArrayList<String> TeamsNames = new ArrayList<String>(ListElementsArrayList);
            intent.putExtra("TeamsNames", TeamsNames);
            intent.putExtra("Points", Number_of_Points);
            intent.putExtra("Fine", sw);
            startActivity(intent);
        }
    }
}