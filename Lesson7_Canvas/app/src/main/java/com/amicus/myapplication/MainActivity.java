package com.amicus.myapplication;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    PaintView paintView;
    Button saveButton;
    Button change;
    Button clear;
    Button undo;
    Button changeThickness;
    Button erase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        paintView = findViewById(R.id.paintView);
        saveButton = findViewById(R.id.btnSave);
        change = findViewById(R.id.btnColor);
        clear = findViewById(R.id.clear);
        undo = findViewById(R.id.btnUndo);
        changeThickness = findViewById(R.id.btnChangeThickness);
        erase = findViewById(R.id.eraser);

        paintView.setChangeThicknessButton(changeThickness);

        erase.setOnClickListener(v -> {
            paintView.erase();
        });

        changeThickness.setOnClickListener(v -> {
            paintView.changeThickness();
        });

        undo.setOnClickListener(v -> {
            paintView.undoStroke();
        });

        saveButton.setOnClickListener(v->{
            paintView.saveToGallery(this);
        });

        clear.setOnClickListener(v->{
            paintView.clearCanvas();
        });

        change.setOnClickListener(v->{
            paintView.changeColor(change);
        });
    }
}