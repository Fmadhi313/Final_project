package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherActivity extends AppCompatActivity {

    ImageView imageViewweather;
    TextView weathersky,weathertemp,weatherhum,weathercity;
    EditText weathercitytext;
    Button weathercitybttn, FBgo, SQLgo;
    String city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
       imageViewweather = (ImageView) findViewById(R.id.imageViewweather);
       weathersky = (TextView) findViewById(R.id.weathersky);
       weathertemp = (TextView) findViewById(R.id.weathertemp);
       weatherhum = (TextView) findViewById(R.id.weatherhum);
        weathercity = (TextView) findViewById(R.id.weathercity);
       weathercitytext = (EditText) findViewById(R.id.weathercitytext);
       weathercitybttn= (Button) findViewById(R.id.weathercitybttn);
        FBgo= (Button) findViewById(R.id.FBgo);
        SQLgo= (Button) findViewById(R.id.SQLgo);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        city = sharedPreferences.getString("new City","");
        if (city.equals("")){
            city = "berlin";
        }

        String weatherWebserviceURL =
                "https://api.openweathermap.org/data/2.5/weather?q="+
                        city
                        +",&appid="+
                        "c65705e44ccf09d6ae90acb660fdb071"+
                        "&units=metric";
        weather(weatherWebserviceURL);
        weathercitybttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = weathercitytext.getText().toString();
                String weatherWebserviceURL =
                        "https://api.openweathermap.org/data/2.5/weather?q="+
                                city
                                +",&appid="+
                                "c65705e44ccf09d6ae90acb660fdb071"+
                                "&units=metric";
                weather(weatherWebserviceURL);
            }
        });
        FBgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WeatherActivity.this,MainActivity.class));
            }
        });
        SQLgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WeatherActivity.this,SQLActivity.class));
            }
        });

    }
    public void weather(String url){
        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Faisal", "respond ok");
                Log.d("Faisal", response.toString());
                try {
                    JSONObject jsonMain = response.getJSONObject("main");
                    JSONObject jsonMain2 = response.getJSONObject("sys");
                    Double temp = jsonMain.getDouble("temp");
                    weathertemp.setText(String.valueOf(temp));
                    weathercity.setText("city: "+city);
                    Log.d("Faisal-temp",String.valueOf(temp));


                    Double hum = jsonMain.getDouble("humidity");
                    weatherhum.setText("Humidity "+String.valueOf(hum));
                    Log.d("Faisal-humidity",String.valueOf(hum));

                    /* sub categories as JSON arrays */
                    JSONArray jsonArray = response.getJSONArray("weather");
                    for (int i=0; i<jsonArray.length();i++){
                        Log.d("Faisal-array",jsonArray.getString(i));
                        JSONObject oneObject = jsonArray.getJSONObject(i);
                        String wheater =
                                oneObject.getString("main");
                        weathersky.setText(wheater);
                        String icon =
                                oneObject.getString("icon");
                                String shareicon = "https://openweathermap.org/img/w/"+icon+".png";
                            Glide.with(WeatherActivity.this).load(shareicon).into(imageViewweather);
                        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Icon", shareicon);
                        editor.putString("new City", city);
                        editor.commit();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                Log.d("Faisal", "respond error");

            }
        });
        RequestQueue queue = Volley.newRequestQueue(this); queue.add(jsonObj);

    }}

