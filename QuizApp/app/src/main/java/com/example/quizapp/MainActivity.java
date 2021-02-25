package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText name,surname;
    String first,second;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=findViewById(R.id.name);
        surname = findViewById(R.id.surname);
    }
    public void startQuiz(View view){
        first=name.getText().toString();
        second=surname.getText().toString();
        if(first.trim().length()==0 || second.trim().length()==0){
            Toast.makeText(getApplicationContext(),"Please fill the name and surname",Toast.LENGTH_SHORT).show();
        }
        else {
            Intent i = new Intent();
            i.setClass(this, QuizPage.class);
            i.putExtra("name",first+" "+second);
            startActivity(i);
        }
    }
}