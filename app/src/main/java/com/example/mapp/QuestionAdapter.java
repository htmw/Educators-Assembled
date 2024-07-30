package com.example.mapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class QuestionAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Question> questions;
    private SharedPreferences sharedPreferences;
    public static boolean isTEACHER = false;  // 假设静态变量

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
        TextView comment_text = convertView.findViewById(R.id.comment_text);
        Button statusButton = convertView.findViewById(R.id.status_button);
        Button deleteButton = convertView.findViewById(R.id.delete_button);
        Button commentButton = convertView.findViewById(R.id.comment_button);

        Question question = questions.get(position);
        questionText.setText(question.getText());
        String comment = question.getComment();
        if(comment.length()>0) comment_text.setText("Comment: "+question.getComment());

        statusButton.setText(question.isCompleted() ? "Completed" : "In Progress");

        statusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question.setCompleted(!question.isCompleted());
                statusButton.setText(question.isCompleted() ? "Completed" : "In Progress");
                saveQuestionStatus(position, question.isCompleted());
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete Question")
                        .setMessage("Are you sure you want to delete this question?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                questions.remove(position);
                                notifyDataSetChanged();
                                //saveQuestions();
                                DelQuestions(position);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });

        if (isTEACHER) {
            commentButton.setVisibility(View.VISIBLE);
            commentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCommentDialog(question);
                }
            });
        } else {
            commentButton.setVisibility(View.GONE);
        }

        return convertView;
    }
    private void DelQuestions(int i) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("question_count", questions.size());
        editor.remove("question_" + i);
        editor.remove("question_comment_" + i);
        editor.remove("question_status_" + i);
        editor.apply();
    }

    private void saveQuestionStatus(int position, boolean status) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("question_status_" + position, status);
        editor.apply();
    }

    private void saveQuestions() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("question_count", questions.size());
        for (int i = 0; i < questions.size(); i++) {
            editor.putString("question_" + i, questions.get(i).getText());
            editor.putString("question_comment_" + i, questions.get(i).getComment());
            editor.putBoolean("question_status_" + i, questions.get(i).isCompleted());
        }
        editor.apply();
    }

    private void showCommentDialog(Question question) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add Comment");

        final EditText input = new EditText(context);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String comment = input.getText().toString();
                question.setComment(comment);
                Toast.makeText(context, "Comment added", Toast.LENGTH_SHORT).show();
                saveQuestions();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
