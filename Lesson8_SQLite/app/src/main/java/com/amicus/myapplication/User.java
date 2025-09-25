package com.amicus.myapplication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "mytable")
public class User {

    @PrimaryKey(autoGenerate = true)
    int id;
    String name;
    String email;
    String phone;
    Double money;
}
