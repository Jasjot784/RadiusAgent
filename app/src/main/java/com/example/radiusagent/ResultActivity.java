package com.example.radiusagent;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    TextView tvResult;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvResult = findViewById(R.id.tvResult);
        Intent intent = getIntent();
        ArrayList<String> selects = intent.getStringArrayListExtra("selects");
        StringBuilder ans = new StringBuilder();
        ans.append("You have Selected:");
        for(int i = 0;i<selects.size();i++){
            ans.append(selects.get(i));
            ans.append(", ");
        }
        tvResult.setText(ans);
    }
}