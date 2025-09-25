package com.amicus.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    String LOG="SQL";

    Button btnAdd;
    Button btnRead;
    Button btnClear;

    EditText etName;
    EditText etEmail;
    EditText etPhone;

    EditText etMoney;

    AppDatabase db;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = findViewById(R.id.btnAdd);
        btnRead = findViewById(R.id.btnRead);
        btnClear = findViewById(R.id.btnClear);
        etEmail = findViewById(R.id.etEmail);
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etMoney =  findViewById(R.id.money);

        db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"myDb").addMigrations(AppDatabase.MIGRATION_1_2).allowMainThreadQueries().build();
        userDao = db.userDao();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String phone = etPhone.getText().toString();
                double money = Double.parseDouble(etMoney.getText().toString());

                User user = new User();
                user.name = name;
                user.email = email;
                user.phone = phone;
                user.money = money;

                long id = userDao.insert(user);

                Log.d(LOG,"Добавлен пользователь с ID "+id);
            }
        });
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<User> users = userDao.getAll();
                if (users.size()==0) {
                    Log.d(LOG,"Ничего нет");
                }
                for (User user:users) {
                    Log.d(LOG,"ID= "+user.id+", name= "+ user.name+", email= "+user.email+", phone= "+user.phone + ", Money" +user.money);
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = userDao.clearAll();
                userDao.resetAutoIncrement();
                Log.d(LOG,"Удалено  "+count+" и сброшен инкремент");
            }
        });
    }
}