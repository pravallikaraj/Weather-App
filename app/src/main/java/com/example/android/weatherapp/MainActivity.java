package com.example.android.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.*;
//import android.icu.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    EditText city;
    Button button;
    TextView temperature,result;
    ImageView resultImage;


    //https://api.openweathermap.org/data/2.5/weather?q=India&appid=deabdd0ed08ec9445d21871fb0b10481
    String baseURL = "https://api.openweathermap.org/data/2.5/weather?q=";
    String apiKey = "&appid=deabdd0ed08ec9445d21871fb0b10481";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        city = findViewById(R.id.enterCity);
        button = findViewById(R.id.button);
        result = findViewById(R.id.result);
        temperature = findViewById(R.id.tempTextView);
        resultImage = findViewById(R.id.imageView);

        //requestQueue = MySingleton.getInstance(this).getRequestQueue();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myCity = city.getText().toString();
                if (TextUtils.isEmpty(myCity)) {
                    city.setError("Enter City");
                } else {
                    String myURL = baseURL + myCity + apiKey;
                    // Log.i("TEST", myURL);
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, myURL, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("JSON", "JSON: " + response);

                            try {
                                String tempInfo = response.getString("main");
                                Log.i("TEMP", "TEMP: "+tempInfo);
                                JSONObject tempObj = new JSONObject(tempInfo);

                                double tempKel = tempObj.getDouble("temp");
//                                long tempInLong = Math.round(tempKel);
//                                long toCelcius = Math.round(273.15);
//
//                                long tempKelToCel = tempInLong - toCelcius;

//                                BigDecimal toCelcius = new BigDecimal("308.569").subtract(new BigDecimal("273.15 "));
//                                Log.i("TEST","TEMPERATURE CHECK: "+toCelcius);
//
//                                long tempKelToCel = Math.round(toCelcius.intValue());
//
//                                temperature.setText(""+tempKelToCel+(char) 0x00B0);
//                                Log.i("TEMP", "TEMP: "+tempKelToCel);



                                BigDecimal a = new BigDecimal(tempKel);
                                BigDecimal b = new BigDecimal("273.15");
                                BigDecimal tempKelToCel = a.subtract(b);
                                MathContext m = new MathContext(2);
                                BigDecimal temp = tempKelToCel.round(m);
                                temperature.setText(""+temp+(char) 0x00B0);

                                //long tempKelToCel = Math.round(_c.intValue());
                                //long a = Math.round(_c.intValue());
                                Log.i("TEMP", "TEMP: "+temp);



                                int pressure = tempObj.getInt("pressure");
                                Log.i("PRESSURE", "PRESSURE IS: "+pressure);
                                Log.i("HUMIDITY", "HUMIDITY IS: "+tempObj.getInt("humidity"));
                                Log.i("TEMP_MIN", "TEMP_MIN IS: "+tempObj.getString("temp_min"));
                                Log.i("TEMP_MAX", "TEMP_MAX IS: "+tempObj.getString("temp_max"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


//                            try {
//                                    String coor = response.getString("coord");
//                                    Log.i("COOR", "COOR: " + coor);
//                                    JSONObject co = new JSONObject(coor);
//
//                                    String lon = co.getString("lon");
//                                    String lat = co.getString("lat");
//
//                                    Log.i("LON", "LON: " + lon);
//                                    Log.i("LAT", "LAT: " + lat);
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }



                            try {
                                String weatherInfo = response.getString("weather");
                                Log.i("WEATHERINFO", "WEATHERINFO: " + weatherInfo);
                                JSONArray weaArr = new JSONArray(weatherInfo);
                                for (int i = 0; i < weaArr.length(); i++) {
                                    JSONObject weatherObj = weaArr.getJSONObject(i);
                                    String weatherDes = weatherObj.getString("description");
                                    result.setText(weatherDes);
                                    Log.i("WEATHER", "WEATHER: " + weatherDes);
                                    String weaCon1 = "clear sky";
                                    if(weatherDes.equals(weaCon1))
                                    {
                                        resultImage.setImageResource(R.drawable.clear);
                                    }
                                    String weaCon2 = "shower rain";
                                    if(weatherDes.equals(weaCon2))
                                    {
                                        resultImage.setImageResource(R.drawable.showers);
                                    }
                                    String weaCon3 = "haze";
                                    if(weatherDes.equals(weaCon3))
                                    {
                                        resultImage.setImageResource(R.drawable.haze);
                                    }
                                    String weaCon4 = "few clouds";
                                    if(weatherDes.equals(weaCon4))
                                    {
                                        resultImage.setImageResource(R.drawable.fewclouds);
                                    }
                                    String weaCon5 = "light rain";
                                    if(weatherDes.equals(weaCon5))
                                    {
                                            resultImage.setImageResource(R.drawable.lightrain);
                                    }
                                    String weaCon6 = "smoke";
                                    if(weatherDes.equals(weaCon6))
                                    {
                                        resultImage.setImageResource(R.drawable.smoke);
                                    }
                                    String weaCon7 = "scattered clouds";
                                    if(weatherDes.equals(weaCon7))
                                    {
                                        resultImage.setImageResource(R.drawable.clouds);
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("Error", "Something went wrong" + error);

                        }
                    });


                    MySingleton.getInstance(MainActivity.this).addToRequestQue(request);
                }



            }

        });


    }
}
