package com.amicus.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {



    Button setSettingsBtn;
    EditText messageInput;
    Button sendButton;
    TextView responseOutput;

    DeepSeekAPI deepSeekAPI;
    String systemPrompt = "";
    double temp = 0;
    int max_tokens=1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageInput = findViewById(R.id.message_input);
        sendButton = findViewById(R.id.send_button);
        responseOutput = findViewById(R.id.response_output);
        setSettingsBtn = findViewById(R.id.addSettings);



        HttpLoggingInterceptor loggingInterceptor =new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60,TimeUnit.SECONDS)
                .writeTimeout(60,TimeUnit.SECONDS)
                .build();

        setSettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.deepseek.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        deepSeekAPI = retrofit.create(DeepSeekAPI.class);

        sendButton.setOnClickListener(v->{

            systemPrompt = Common.getPrompt();
            temp = Common.getTemp();
            System.out.println(systemPrompt);
            System.out.println(temp);

            String userInput = messageInput.getText().toString().trim();
            if (!userInput.isEmpty()) {
                sendMessageToDeepSeek(userInput);
            }
        });
    }

    private void sendMessageToDeepSeek(String userInput) {
        responseOutput.setText("Загрузка");
        List<ChatRequest.Message> messages = new ArrayList<>();
        messages.add(new ChatRequest.Message("system",systemPrompt));
        messages.add(new ChatRequest.Message("user",userInput));
        ChatRequest request = new ChatRequest("deepseek-chat",messages,temp,max_tokens);

        Call<ChatResponse> call = deepSeekAPI.sendMessage(request);
        call.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String reply = response.body().choices.get(0).message.content;
                    responseOutput.setText(reply);
                    messageInput.setText("");
                }else {
                    responseOutput.setText("Ошибка "+response.code());
                }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                Log.d("GPT","Ошибка подключения");
                responseOutput.setText("Ошибка подключения "+t.getMessage());
            }
        });
    }

}