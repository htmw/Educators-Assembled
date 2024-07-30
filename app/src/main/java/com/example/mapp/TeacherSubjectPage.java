package com.example.mapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TeacherSubjectPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_subject_page);

        Button button1 = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTeacherMainPage("Algebra");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTeacherMainPage("Geometry");
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTeacherMainPage("Precalculus");
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTeacherMainPage("Counting and Probability");
            }
        });
    }


    private void openTeacherMainPage(String role){
        //        Intent intent = new Intent(TeacherSubjectPage.this, TeacherMainActivity.class);
        Intent intent = new Intent(TeacherSubjectPage.this, MyProblemList.class);
        QuestionAdapter.isTEACHER = true;
        intent.putExtra("Subject", role);
        startActivity(intent);
        finish();
    }
}