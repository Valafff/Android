package com.example.lists;

public class Animal
{
    String animalName;
    int age;

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Animal(int age, String animalName) {
        this.age = age;
        this.animalName = animalName;
    }
}
