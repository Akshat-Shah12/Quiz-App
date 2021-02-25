package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Result extends AppCompatActivity {

    FirebaseDatabase mRef;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    DatabaseReference reference;
    TextView result,resultOfAll;
    int score=0;
    ImageView image;
    String fullName;

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        image = findViewById(R.id.imageView);
        resultOfAll = (TextView)findViewById(R.id.resultOfAll);
        mRef = FirebaseDatabase.getInstance();
        result = (TextView)findViewById(R.id.result); 
        Bundle extras = getIntent().getExtras();
        int value = 0;
        if (extras != null) {
            value = extras.getInt("score");
            fullName=extras.getString("name");
        }
        score= value;
        result.setText(value+"/10");
        saveData();
        if(Integer.valueOf(value)>=5) {
            Toast.makeText(getApplicationContext(), "Great Job!!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Try harder next time!", Toast.LENGTH_SHORT).show();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                openData();
            }
        },1000);

    }
    public void saveData(){
        File f = new File(getApplicationContext().getFilesDir(),"userData.txt");
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream fos = getApplicationContext().openFileOutput("userData.txt",MODE_APPEND);
            fos.write((fullName+"\n"+score+"\n").getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        reference= mRef.getReference();
        reference.child("score").child(fullName).setValue(score+" ");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Picasso.with(getApplicationContext()).load(snapshot.child("Image").getValue().toString())
                        .into(image);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void openData(){
        try {
            FileInputStream fis = getApplicationContext().openFileInput("userData.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String answer = "";
            String a = br.readLine().trim();
            String b = br.readLine().trim();
            while(a!=null || b!=null) {
                //Toast.makeText(getApplicationContext(), a, Toast.LENGTH_LONG).show();
                answer=answer+a+" - "+b+"\n";
                a=br.readLine();
                b=br.readLine();
                //Toast.makeText(getApplicationContext(), b, Toast.LENGTH_LONG).show();
            }
            resultOfAll.setText(answer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}