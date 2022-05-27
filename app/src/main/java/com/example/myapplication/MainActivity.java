package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Students");
        EditText StudentID = (EditText) findViewById(R.id.StudentID);
        EditText StudentFname = (EditText) findViewById(R.id.StudentFname);
        EditText StudentSname = (EditText) findViewById(R.id.StudentSname);
        EditText StudentFather = (EditText) findViewById(R.id.StudentFather);
        EditText NationalID = (EditText) findViewById(R.id.NationalID);
        EditText DOB = (EditText) findViewById(R.id.DOB);
        EditText Gender = (EditText) findViewById(R.id.Gender);
        Button Addbttn = (Button) findViewById(R.id.addbuttn);
        Button Deletebttn = (Button) findViewById(R.id.Deletebttn);
        Button Fetchbttn = (Button) findViewById(R.id.featchbttn);
        Button Updatebttn = (Button) findViewById(R.id.Updatebttn);
        Button ViewDB = (Button) findViewById(R.id.View);
        Button SQL = (Button) findViewById(R.id.SQLbttn);
        Button Weather = (Button) findViewById(R.id.weatherbttn);
        ImageView imageViewmain = (ImageView) findViewById(R.id.imageViewmain);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String shareicon =sharedPreferences.getString("Icon",String.valueOf(0));
        Glide.with(MainActivity.this).load(shareicon).into(imageViewmain);

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
                    Student Student = new Student(ID,Fname,Sname,Father,NID,DATE,gender);
                    myRef.child("Student "+ID).setValue(Student);
                    Toasty.success(getBaseContext(), "Insert Success.", Toast.LENGTH_SHORT,
                            true).show();
                }
            }
        });
        Deletebttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ID = StudentID.getText().toString();
                if(ID.equals("")){
                    Toasty.error(getBaseContext(), "Please insert ID",
                            Toast.LENGTH_SHORT, true).show();
                }
                else {
                    myRef.child("Student "+ID).removeValue();
                    Toasty.success(getBaseContext(), "Delete Success.", Toast.LENGTH_SHORT,
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

                    Updatebttn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String ID= StudentID.getText().toString();
                            if (ID.equals("")){
                                Toasty.error(getBaseContext(), "Please fetch the data you want",
                                        Toast.LENGTH_SHORT, true).show();
                            }
                            else {
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
                                    myRef.child("Student "+ID).child("firstname").setValue(Fname);
                                    myRef.child("Student "+ID).child("surname").setValue(Sname);
                                    myRef.child("Student "+ID).child("fathersname").setValue(Father);
                                    myRef.child("Student "+ID).child("nationalID").setValue(NID);
                                    myRef.child("Student "+ID).child("dob").setValue(DATE);
                                    myRef.child("Student "+ID).child("gender").setValue(gender);
                                    Toasty.success(getBaseContext(), "Update Success.", Toast.LENGTH_SHORT,
                                            true).show();
                                }
                            }
                        }
                    });

                }
            }
        });
        ViewDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this,ViewFBActivity.class));

            }
        });
        SQL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SQLActivity.class));
            }
        });
        Weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,WeatherActivity.class));
            }
        });
    }
}