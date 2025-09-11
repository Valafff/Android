package com.example.lesson3_activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class TwoActivity extends AppCompatActivity {

    private Button buttonOpenBrowser;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_two);

        buttonOpenBrowser = findViewById(R.id.buttonOpenBrowser);
        buttonBack = findViewById(R.id.buttonBack);

        buttonOpenBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent implictIntent = new Intent(Intent.ACTION_VIEW);
                implictIntent.setData(Uri.parse("https://www.google.com"));

//                openWebPage("https://www.google.com");
                startActivity(implictIntent);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}