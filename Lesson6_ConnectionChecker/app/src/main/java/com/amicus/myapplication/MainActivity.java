package com.amicus.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ConnectivityManager connectivityManager;
    AlertDialog alertDialog;
    TextView logTextView;

    SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy:MM:dd HH:mm:ss", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logTextView = findViewById(R.id.logTextView);
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        alertDialog = new AlertDialog.Builder(this)
                .setTitle("Нет интернета")
                .setMessage("Проверьте соединение")
                .setCancelable(false)
                .create();

        connectivityManager.registerNetworkCallback(new NetworkRequest.Builder().build(),new ConnectivityManager.NetworkCallback(){
            @Override
            public void onAvailable(@NonNull Network network) {
              runOnUiThread(()->{
                  String type = getNetworkType(network);
                  new Thread(()->{
                      boolean realInternet = checkInternetAccess();

                      runOnUiThread(()->{
                          if (realInternet){
                              if (alertDialog.isShowing()) {
                                  alertDialog.dismiss();
                              }
                              appendLog("Интернет появился ("+type+")");
                          }else {
                              if (!alertDialog.isShowing()) {
                                  alertDialog.show();
                              }
                              appendLog("Сеть "+type+" подключена но без доступа в интернет");
                          }
                      });
                  }).start();
              });
            }

            @Override
            public void onLost(@NonNull Network network) {
                runOnUiThread(()->{
                    if (!alertDialog.isShowing()) {
                        alertDialog.show();
                    }
                    appendLog("Интернет пропал");
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connectivityManager != null) {
            connectivityManager.unregisterNetworkCallback(new ConnectivityManager.NetworkCallback());
        }
    }
    private String getNetworkType(Network network) {
        NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
        if (capabilities == null)
            return "неизвестно";
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            return "Wi-Fi";
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
            return "мобильная сеть";
        }else {
            return "другая сеть";
        }
    }

    private void appendLog(String message){
        String time = dateFormat.format(new Date());
        logTextView.append("\n"+time+" - "+message);
    }

    //проверка пингом через сайт
    private boolean checkInternetAccess(){
        try {
            URL url = new URL("https://www.google.com");
            HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
            urlc.setConnectTimeout(2000);
            urlc.connect();
            return (urlc.getResponseCode()==200);
        }catch (Exception e){
            return false;
        }
    }
    //проверка через сокет Cloudflare DNS
    private  boolean checkInternetBySocket(){
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("1.1.1.1",53),1500);
            socket.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }
}