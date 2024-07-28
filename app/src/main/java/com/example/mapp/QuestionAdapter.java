package com.example.mapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

public class QuestionAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Question> questions;
    private SharedPreferences sharedPreferences;

    public QuestionAdapter(Context context, ArrayList<Question> questions) {
        this.context = context;
        this.questions = questions;
        this.sharedPreferences = context.getSharedPreferences("MyApp", Context.MODE_PRIVATE);
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public Object getItem(int position) {
        return questions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.question_item, parent, false);
        }

        TextView questionText = convertView.findViewById(R.id.question_text);
        Button statusButton = convertView.findViewById(R.id.status_button);

        Question question = questions.get(position);
        questionText.setText(question.getText());
        statusButton.setText(question.isCompleted() ? "Completed" : "In Progress");

        statusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question.setCompleted(!question.isCompleted());
                statusButton.setText(question.isCompleted() ? "Completed" : "In Progress");
                saveQuestionStatus(position, question.isCompleted());
            }
        });

        return convertView;
    }

    private void saveQuestionStatus(int position, boolean status) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("question_status_" + position, status);
        editor.apply();
    }
}
