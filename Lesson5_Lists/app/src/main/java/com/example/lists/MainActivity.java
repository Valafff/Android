package com.example.lists;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Animal> animals = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        setInitialAnimals();

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        recyclerView = findViewById(R.id.list);
        AnimalAdapter.OnStateClickListener stateClickListener = new AnimalAdapter.OnStateClickListener() {
            @Override
            public void onAnimalClick(Animal animal, int position) {
                Toast.makeText(getApplicationContext(), "Был выбран пункт "+animal.getAnimalName(), Toast.LENGTH_SHORT).show();
            }

        };
        AnimalAdapter adapter = new AnimalAdapter(this,animals,stateClickListener);
        recyclerView.setAdapter(adapter);
    }
    public void setInitialAnimals()
    {
        animals.add(new Animal(5, "Кот"));
        animals.add(new Animal(7, "Собака"));
        animals.add(new Animal(4, "Лев"));
        animals.add(new Animal(5, "Тигр"));
        animals.add(new Animal(20, "Слон"));
    }
}