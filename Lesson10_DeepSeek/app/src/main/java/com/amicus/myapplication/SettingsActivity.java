package com.amicus.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsActivity extends AppCompatActivity {

    Button saveAndBack;

    EditText promptText;
    EditText tempValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);

        saveAndBack = findViewById(R.id.saveSettingsAndBack);
        promptText = findViewById(R.id.setPrompt);
        tempValue = findViewById(R.id.setTemp);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        saveAndBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double temp = Double.parseDouble(tempValue.getText().toString());
                if (temp < 0)
                {
                    temp = 0;
                }
                if (temp > 1.5)
                {
                    temp = 1.5;
                }

                Common.setPrompt(promptText.getText().toString());
                Common.setTemp(temp);
                finish();
            }
        });
    }
}