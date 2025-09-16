package com.example.lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.ViewHolder>
{

    interface OnStateClickListener{
        void onAnimalClick(Animal animal,int position);
    }
    OnStateClickListener onClickListener;
    LayoutInflater inflater;
    List<Animal> animals;

    public AnimalAdapter(Context context, List<Animal> animals, OnStateClickListener onClickListener)
    {
        this.onClickListener = onClickListener;
        this.inflater = LayoutInflater.from(context);
        this.animals = animals;
    }

    @NonNull
    @Override
    public AnimalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalAdapter.ViewHolder holder, int position) {
        Animal animal = animals.get(position);
        holder.nameView.setText(animal.getAnimalName());
        holder.ageView.setText(String.valueOf(animal.getAge()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onAnimalClick(animal,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return animals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameView,ageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.animalName);
            ageView = itemView.findViewById(R.id.age);
        }
    }
}
