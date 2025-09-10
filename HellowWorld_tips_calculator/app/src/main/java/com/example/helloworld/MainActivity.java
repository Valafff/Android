package com.example.helloworld;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView sumTipsText;
    TextView sumOrderText;
    TextView sumTotalText;
    Button getResult;
    Button reset;
    EditText sumOrder;
    EditText tipsCount;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        sumOrder = findViewById(R.id.sumOrder);
        tipsCount = findViewById(R.id.sumTips);

        sumTipsText = findViewById(R.id.sumTipsText);
        sumOrderText = findViewById(R.id.sumOrderText);
        sumTotalText = findViewById(R.id.sumTotalText);

        getResult = findViewById(R.id.resultButton);
        reset = findViewById(R.id.resetButton);

        
        getResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             sumTipsText.setText(String.valueOf(tipsCount.getText()));
             sumOrderText.setText(String.valueOf(sumOrder.getText()));

             Double tips = Double.parseDouble(tipsCount.getText().toString());
             Double order = Double.parseDouble(sumOrder.getText().toString());

             var result = tips + order;
             sumTotalText.setText(String.valueOf(result));
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sumTipsText.setText(String.valueOf(""));
                sumOrderText.setText(String.valueOf(""));
                sumTotalText.setText(String.valueOf(""));
                tipsCount.setText(String.valueOf("0"));
                sumOrder.setText(String.valueOf("0"));
            }
        });


    }
}


//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//
//
//
//
//
//
//            return insets;
//        });