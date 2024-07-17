package com.example.mapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class FirstLoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login_page);

        Button studentButton = findViewById(R.id.button_student);
        Button teacherButton = findViewById(R.id.button_teacher);

        studentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainPage("student");
            }
        });

        teacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTeacherSubjectPage("teacher");
            }
        });
    }

    private void openMainPage(String role) {
        Intent intent = new Intent(FirstLoginPage.this, MainActivity.class);
        intent.putExtra("ROLE", role);
        startActivity(intent);
        finish();
    }

    private void openTeacherSubjectPage(String role){
        Intent intent = new Intent(FirstLoginPage.this, TeacherSubjectPage.class);
        intent.putExtra("ROLE", role);
        startActivity(intent);
        finish();
    }


}