package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;

public class SQLActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlactivity);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseHelper myDb = new DatabaseHelper(SQLActivity.this);
        EditText StudentID = (EditText) findViewById(R.id.StudentIDSQL);
        EditText StudentFname = (EditText) findViewById(R.id.StudentFnameSQL);
        EditText StudentSname = (EditText) findViewById(R.id.StudentSnameSQL);
        EditText StudentFather = (EditText) findViewById(R.id.StudentFatherSQL);
        EditText NationalID = (EditText) findViewById(R.id.NationalIDSQL);
        EditText DOB = (EditText) findViewById(R.id.DOBSQL);
        EditText Gender = (EditText) findViewById(R.id.GenderSQL);
        Button Addbttn = (Button) findViewById(R.id.addbuttnSQL);
        Button Deletebttn = (Button) findViewById(R.id.DeletebttnSQL);
        Button Fetchbttn = (Button) findViewById(R.id.featchbttnSQL);
        Button Updatebttn = (Button) findViewById(R.id.UpdatebttnSQL);
        Button ViewDB = (Button) findViewById(R.id.ViewSQL);
        Button FBbttn = (Button) findViewById(R.id.FBbttn);
        Button SQLweatherbttn = (Button) findViewById(R.id.SQLweatherbttn);
        ImageView imageViewSQL = (ImageView)findViewById(R.id.imageViewSQL);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String shareicon =sharedPreferences.getString("Icon",String.valueOf(0));
        Glide.with(SQLActivity.this).load(shareicon).into(imageViewSQL);

        Addbttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ID = StudentID.getText().toString();
                String Fname = StudentFname.getText().toString();
                String Sname = StudentSname.getText().toString();
                String Father = StudentFather.getText().toString();
                String NID = NationalID.getText().toString();
                String DATE = DOB.getText().toString();
                String gender = Gender.getText().toString();
                if(ID.equals("") || Fname.equals("") || Sname.equals("") || Father.equals("") || NID.equals("") || DATE.equals("") || gender.equals("")){
                    Toasty.error(getBaseContext(), "Please fill all fields",
                            Toast.LENGTH_SHORT, true).show();
                }
                else {
                    if (!myDb.addData(ID, Fname, Sname,Father,NID,DATE,gender)) {
                        Toasty.error(getBaseContext(), "Insert Failed",
                                Toast.LENGTH_SHORT, true).show();

                    } else {
                        Toasty.success(getBaseContext(), "Insert Success.", Toast.LENGTH_SHORT,
                                true).show();
                    }
                }
            }
        });

        Deletebttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String delID = StudentID.getText().toString();
                if (StudentID.getText().toString().equals("")){
                    Toasty.error(getBaseContext(), "Please insert ID",
                            Toast.LENGTH_SHORT, true).show();
                }
                else {
                    myDb.deleteData(delID);
                    Toasty.success(getBaseContext(), "DELETE Success.", Toast.LENGTH_SHORT,
                            true).show();

                }
            }
        });
        Fetchbttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fetchID= StudentID.getText().toString();
                if (fetchID.equals("")){
                    Toasty.error(getBaseContext(), "Please insert ID",
                            Toast.LENGTH_SHORT, true).show();
                }
                else {
                    DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference().child("Students");
                    myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try {
                                String namefetch = snapshot.child("Student "+fetchID).child("firstname").getValue().toString();
                                String surnamefetch = snapshot.child("Student "+fetchID).child("surname").getValue().toString();
                                String fathernamefetch = snapshot.child("Student "+fetchID).child("fathersname").getValue().toString();
                                String nationalidfetch = snapshot.child("Student "+fetchID).child("nationalID").getValue().toString();
                                String datefetch = snapshot.child("Student "+fetchID).child("dob").getValue().toString();
                                String genderfetch = snapshot.child("Student "+fetchID).child("gender").getValue().toString();
                                StudentFname.setText(namefetch);
                                StudentSname.setText(surnamefetch);
                                StudentFather.setText(fathernamefetch);
                                NationalID.setText(nationalidfetch);
                                DOB.setText(datefetch);
                                Gender.setText(genderfetch);
                                if (!myDb.addData(fetchID, namefetch, surnamefetch,fathernamefetch,nationalidfetch,datefetch,genderfetch)) {
                                    Toasty.error(getBaseContext(), "Insert Failed",
                                            Toast.LENGTH_SHORT, true).show();

                                } else {
                                    Toasty.success(getBaseContext(), "Insert Success.", Toast.LENGTH_SHORT,
                                            true).show();
                                }
                            } catch (Exception E) {
                                Toasty.error(getBaseContext(), "ID does not exist",
                                        Toast.LENGTH_SHORT, true).show();
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d("Faisal", "error" + error.toException());
                        }
                    });



                }
            }
        });
        Updatebttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ID = StudentID.getText().toString();
                String Fname = StudentFname.getText().toString();
                String Sname = StudentSname.getText().toString();
                String Father = StudentFather.getText().toString();
                String NID = NationalID.getText().toString();
                String DATE = DOB.getText().toString();
                String gender = Gender.getText().toString();
                if(ID.equals("") || Fname.equals("") || Sname.equals("") || Father.equals("") || NID.equals("") || DATE.equals("") || gender.equals("")){
                    Toasty.error(getBaseContext(), "Please fill all fields",
                            Toast.LENGTH_SHORT, true).show();
                }
                else {
                    if (!myDb.Update(ID, Fname, Sname,Father,NID,DATE,gender)) {
                        Toasty.error(getBaseContext(), "Update Failed",
                                Toast.LENGTH_SHORT, true).show();

                    } else {
                        Toasty.success(getBaseContext(), "Update Success.", Toast.LENGTH_SHORT,
                                true).show();
                    }
                }
            }
        });
        ViewDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SQLActivity.this,ViewSQLActivity.class));
            }
        });
        FBbttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SQLActivity.this,MainActivity.class));
            }
        });

        SQLweatherbttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SQLActivity.this,WeatherActivity.class));
            }
        });

    }
}