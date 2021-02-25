package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
public class QuizPage extends AppCompatActivity {

    String questions[][] = {
        {"Who is the President of India","Ram Nath Kovind","Adhir Ranjan Chowdhury","Amit Shah","Antonio Maino","1"},
        {"Which is the first country to reach mars in its first try","USA","India","China","EU","2"},
        {"India is connected to North-East India by a small passage, What is it called?","Naroga way","Siliguri Corridor","Canda Corridor","Kartarpur Corridor","2"},
        {"Which is the country with the largest land area","USA","China","India","Russia","4"},
        {"Out of these countries which country does no recognize Israel","India","UAE","Bangladesh","Turkey","3"},
        {"How many countries have conducted surgical strike against Pakistan","1","2","3","4","3"},
        {"What is the national fruit of India?","Orange","Peach","Apple","Mango","4"},
        {"Who has annexed Tibet?","Mongolia","Kazakisthan","Pakistan","China","4"},
        {"Which famous politician's name is Antonio Maino","Sushmita Swaraj","Sonia Gandhi","Rahul Gandhi","Indira Gandhi","2"},
        {"What is Afganistan's Capital?","Faridabad","Peshawar","Kabul","Rawalpindi","3"},

    };
    ProgressBar pg;
    int score=0;
    String fullName;
    int positionOfProgressBar = 10;
    TextView FirstOption, SecondOption, ThirdOption, ForthOption,Question;
    TextView progress;
    Button submitBtn;
    int optionSelected=0;
    boolean restart = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_page);
        pg = (ProgressBar)findViewById(R.id.progressBar);
        progress = (TextView)findViewById(R.id.progress);
        FirstOption = (TextView)findViewById(R.id.FirstOption);
        SecondOption = (TextView)findViewById(R.id.SecondOption);
        ThirdOption = (TextView)findViewById(R.id.ThirdOption);
        ForthOption = (TextView)findViewById(R.id.ForthOption);
        Question = (TextView)findViewById(R.id.question);
        submitBtn = (Button)findViewById(R.id.submitBtn);
        startToProgress();
        insertQuestion();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            fullName = extras.getString("name");
        }
    }
    public void insertQuestion(){
        int a = (positionOfProgressBar/10) -1;
        Question.setText(questions[a][0]);
        FirstOption.setText(questions[a][1]);
        SecondOption.setText(questions[a][2]);
        ThirdOption.setText(questions[a][3]);
        ForthOption.setText(questions[a][4]);
    }
    public void clicked1(View view){
        if(restart==true) {
            optionSelected = 1;
            makeEverythingNormal();
            FirstOption.setBackgroundResource(R.drawable.selected);
            FirstOption.setTextColor(Color.BLACK);
        }
    }
    public void clicked2(View view){
        if(restart == true) {
            optionSelected = 2;
            makeEverythingNormal();
            SecondOption.setBackgroundResource(R.drawable.selected);
            SecondOption.setTextColor(Color.BLACK);
        }
    }
    public void clicked3(View view){
        if(restart==true) {
            optionSelected = 3;
            makeEverythingNormal();
            ThirdOption.setBackgroundResource(R.drawable.selected);
            ThirdOption.setTextColor(Color.BLACK);
        }
    }
    public void clicked4(View view){
        if(restart==true) {
            optionSelected = 4;
            makeEverythingNormal();
            ForthOption.setBackgroundResource(R.drawable.selected);
            ForthOption.setTextColor(Color.BLACK);
        }
    }

    public void startToProgress(){
        progress.setText(positionOfProgressBar/10+"/10");
        pg.setProgress(positionOfProgressBar);

    }
    public void makeEverythingNormal(){
        FirstOption.setBackgroundResource(R.drawable.options);
        FirstOption.setTextColor(Color.BLACK);
        SecondOption.setBackgroundResource(R.drawable.options);
        SecondOption.setTextColor(Color.BLACK);
        ThirdOption.setBackgroundResource(R.drawable.options);
        ThirdOption.setTextColor(Color.BLACK);
        ForthOption.setBackgroundResource(R.drawable.options);
        ForthOption.setTextColor(Color.BLACK);
    }
    public void submitClicked(View view){
        if(optionSelected==0 && submitBtn.getText().toString().equals("Submit")){
            Toast.makeText(getApplicationContext(),"Please select an option",Toast.LENGTH_SHORT).show();
        }
        else{
            int a = (positionOfProgressBar/10) -1;
            int correctOpt = Integer.valueOf(questions[a][5]);
            restart= false;
            if(correctOpt==1){
                FirstOption.setBackgroundResource(R.drawable.correctoption);
                FirstOption.setTextColor(Color.WHITE);
            }
            else if(correctOpt==2){
                SecondOption.setBackgroundResource(R.drawable.correctoption);
                SecondOption.setTextColor(Color.WHITE);
            }
            else if(correctOpt==3){
                ThirdOption.setBackgroundResource(R.drawable.correctoption);
                ThirdOption.setTextColor(Color.WHITE);
            }
            else if(correctOpt==4){
                ForthOption.setBackgroundResource(R.drawable.correctoption);
                ForthOption.setTextColor(Color.WHITE);
            }
            if(correctOpt!=optionSelected){
                if(optionSelected==1){
                    FirstOption.setBackgroundResource(R.drawable.wrongoption);
                    FirstOption.setTextColor(Color.WHITE);
                }
                else if(optionSelected==2){
                    SecondOption.setBackgroundResource(R.drawable.wrongoption);
                    SecondOption.setTextColor(Color.WHITE);
                }
                else if(optionSelected==3){
                    ThirdOption.setBackgroundResource(R.drawable.wrongoption);
                    ThirdOption.setTextColor(Color.WHITE);
                }
                else if(optionSelected==4){
                    ForthOption.setBackgroundResource(R.drawable.wrongoption);
                    ForthOption.setTextColor(Color.WHITE);
                }
            }
            else if(correctOpt==optionSelected && submitBtn.getText().toString().equals("Next")){
                score++;
            }
            if(submitBtn.getText().toString().equals("Next")) {
                if(positionOfProgressBar==100){
                    Intent i= new Intent();
                    i.setClass(this, Result.class);
                    i.putExtra("score",score);
                    i.putExtra("name",fullName);
                    startActivity(i);
                    finish();
                }
                else {
                    submitBtn.setText("Submit");
                    positionOfProgressBar += 10;
                    restart = true;
                    pg.setProgress(positionOfProgressBar);
                    progress.setText(positionOfProgressBar / 10 + "/10");
                    optionSelected = 0;
                    makeEverythingNormal();
                    insertQuestion();
                }
            }
            else{
                submitBtn.setText("Next");
            }
        }


    }
}