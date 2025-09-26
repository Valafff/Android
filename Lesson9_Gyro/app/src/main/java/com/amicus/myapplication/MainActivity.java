package com.amicus.myapplication;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.net.HttpURLConnection;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView sensorView;
    Button clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorView = findViewById(R.id.textviewSenosor);
        clear = findViewById(R.id.btnLear);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if (gyro == null) {
            Log.e("GyroSensor", "Гироскоп не доступен на этом устройстве");
            return;
        }

        SensorEventListener listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                float x = sensorEvent.values[0]; // Угловая скорость вокруг оси X (рад/с)
                float y = sensorEvent.values[1]; // Угловая скорость вокруг оси Y (рад/с)
                float z = sensorEvent.values[2]; // Угловая скорость вокруг оси Z (рад/с)

                if (Math.abs(x) > 0.1 || Math.abs( y) > 0.1 || Math.abs( z) > 0.1)
                {
                    Log.d("GyroSensor", String.format("Gyro: X=%.4f, Y=%.4f, Z=%.4f rad/s", x, y, z));
                    sensorView.append(String.format("Gyro: X=%.4f, Y=%.4f, Z=%.4f rad/s", x, y, z)+"\n");
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                Log.d("GyroSensor", "Точность гироскопа изменилась: " + accuracy);
            }
        };

        sensorManager.registerListener(listener, gyro, SensorManager.SENSOR_DELAY_NORMAL);

        clear.setOnClickListener(view -> {
            sensorView.setText("");
        });

    }
}