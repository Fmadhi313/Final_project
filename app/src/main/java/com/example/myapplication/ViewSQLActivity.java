package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class ViewSQLActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList id,name,surname,fatherName,nationalID,dateOfBirth,gender;
    DatabaseHelper Mydb;
    MyAdapterListSQL myAdapterListSQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sqlactivity);
        recyclerView = findViewById(R.id.studentListSQL);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Mydb = new DatabaseHelper(this);
        id = new ArrayList<>();
        name = new ArrayList<>();
        surname = new ArrayList<>();
        fatherName = new ArrayList<>();
        nationalID = new ArrayList<>();
        dateOfBirth = new ArrayList<>();
        gender = new ArrayList<>();

        myAdapterListSQL = new MyAdapterListSQL(this, id,name,surname,fatherName,nationalID,dateOfBirth,gender);
        recyclerView.setAdapter(myAdapterListSQL);

        displaydata();
    }
    public void displaydata(){
        Cursor cursor = Mydb.ViewEmployee();
        if(cursor.getCount() ==0){
            Toasty.error(getBaseContext(), "there is no data",
                    Toast.LENGTH_SHORT, true).show();
        }
        else {
            while (cursor.moveToNext()){
                id.add(cursor.getString(0));
                name.add(cursor.getString(1));
                surname.add(cursor.getString(2));
                fatherName.add(cursor.getString(3));
                nationalID.add(cursor.getString(4));
                dateOfBirth.add(cursor.getString(5));
                gender.add(cursor.getString(6));
            }
        }
    }
}