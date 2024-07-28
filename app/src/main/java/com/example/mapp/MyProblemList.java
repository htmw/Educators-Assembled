package com.example.mapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MyProblemList extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private ArrayList<Question> questions;
    private QuestionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_problem_list);

        sharedPreferences = getSharedPreferences("MyApp", Context.MODE_PRIVATE);
        ListView listView = findViewById(R.id.questions_list);

        questions = loadQuestions();
        adapter = new QuestionAdapter(this, questions);
        listView.setAdapter(adapter);
    }

    private ArrayList<Question> loadQuestions() {
        ArrayList<Question> questionsList = new ArrayList<>();
        int questionCount = sharedPreferences.getInt("question_count", 0);

        for (int i = 0; i < questionCount; i++) {
            String questionText = sharedPreferences.getString("question_" + i, null);
            boolean questionStatus = sharedPreferences.getBoolean("question_status_" + i, false);
            questionsList.add(new Question(questionText, questionStatus));
        }

        return questionsList;
    }
}
