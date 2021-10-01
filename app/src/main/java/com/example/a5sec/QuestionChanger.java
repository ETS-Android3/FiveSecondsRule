package com.example.a5sec;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class QuestionChanger {

    private String[] my_questions;
    private int maximum_size = 30;
    private JSONArray array;
    private int qs = 0;

    private void shuffle(){
        for (int i = 0; i<my_questions.length; i++){
            int new_place = (int) (Math.random() * my_questions.length);
            String buffer = my_questions[new_place];
            my_questions[new_place] = my_questions[i];
            my_questions[i] = buffer;
        }
    }
    private void Generate() throws JSONException {
        int ques = (int) (Math.random() * array.length());
        my_questions[0] = array.getString(ques);
        for (int i = 1; i < maximum_size; i++){
            int step = (int) array.length()/maximum_size;
            int l = 1 + (int) (Math.random() * step); //check later;
            if (ques + l < array.length())
                ques += l;
            else
                ques = array.length() - ques;
            my_questions[i] = array.getString(ques);
        }
        shuffle();
    }
    public QuestionChanger(Context context) throws IOException, JSONException {
        String jsonText = readText(context, R.raw.questions);
        JSONObject raw = new JSONObject(jsonText);
        my_questions = new String[maximum_size];
        this.array = raw.getJSONArray("questions");
        Generate();
    }
    private static String readText(Context context, int resId) throws IOException {
        InputStream is = context.getResources().openRawResource(resId);
        BufferedReader br= new BufferedReader(new InputStreamReader(is));
        StringBuilder sb= new StringBuilder();
        String s= null;
        while((s = br.readLine())!=null) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }
    public String get_question() throws JSONException {
        String str = my_questions[qs];
        if (qs == my_questions.length - 1)
            {
                qs = 0;
                Generate();
            }
        else
            qs++;
        return str;
    }
}
