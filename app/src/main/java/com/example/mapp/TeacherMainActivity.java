package com.example.mapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TeacherMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);


        Intent intent = getIntent();
        String role = intent.getStringExtra("Subject");

        TextView roleTextView = findViewById(R.id.SubjectText);
        /*if ("Algebra".equals(role)) {
            roleTextView.setText("AlgebraPage");
        } else if ("Geometry".equals(role)) {
            roleTextView.setText("GeometryPage");
        }
        else  if("Precalculus".equals(role)) {
            roleTextView.setText("PrecalculusPage");
        }*/
    }
}