package com.example.weatherapp;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import 	java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    TextView txtLocation;
    TextView todayTemperature, todayTitle, tomorrowTitle, in2daysTitle, in3daysTitle, in4daysTitle, in5daysTitle, in6daysTitle, txtWind, txtRain, txtDescription, txtPressure, txtFeelsLike, txtWindDirection;
    TextView txtIn3h, txtIn6h, txtIn9h, txtIn12h, txtIn15h;
    TextView txtMinMaxTmp, txtIn3hTemp, txtIn6hTemp, txtIn9hTemp, txtIn12hTemp, txtIn15hTemp;
    TextView txtIn3hWind, txtIn6hWind, txtIn9hWind, txtIn12hWind, txtIn15hWind;
    TextView txtIn3hWindGust, txtIn6hWindGust, txtIn9hWindGust, txtIn12hWindGust, txtIn15hWindGust;
    TextView txtIn3hRain, txtIn6hRain, txtIn9hRain, txtIn12hRain, txtIn15hRain;
    TextView txtSunrise, txtSunset, txtTomorrowSunrise, txtTomorrowSunset, txtIn2DSunrise, txtIn2DSunset;
    TextView txtTomorrowDescription, txtTomorrowWind, txtTomorrowWindGust, txtTomorrowWindDirection, txtTomorrowRain, txtTomorrowMinMax;
    TextView txtTomorrowPressure, txtTomorrowTempMorning, txtTomorrowTempDay, txtTomorrowTempEve, txtTomorrowTempNight;
    TextView txtIn2DDescription, txtIn2DWind, txtIn2DWindGust, txtIn2DWindDirection, txtIn2DRain, txtIn2DMinMax;
    TextView txtIn2DPressure, txtIn2DTempMorning, txtIn2DTempDay, txtIn2DTempEve, txtIn2DTempNight;
    TextView txtIn3DDescription, txtIn3DWind, txtIn3DWindGust, txtIn3DWindDirection, txtIn3DRain, txtIn3DMinMax;
    TextView txtIn3DPressure, txtIn3DTempMorning, txtIn3DTempDay, txtIn3DTempEve, txtIn3DTempNight, txtIn3DSunrise, txtIn3DSunset;
    TextView txtIn4DDescription, txtIn4DWind, txtIn4DWindGust, txtIn4DWindDirection, txtIn4DRain, txtIn4DMinMax;
    TextView txtIn4DPressure, txtIn4DTempMorning, txtIn4DTempDay, txtIn4DTempEve, txtIn4DTempNight, txtIn4DSunrise, txtIn4DSunset;
    TextView txtIn5DDescription, txtIn5DWind, txtIn5DWindGust, txtIn5DWindDirection, txtIn5DRain, txtIn5DMinMax;
    TextView txtIn5DPressure, txtIn5DTempMorning, txtIn5DTempDay, txtIn5DTempEve, txtIn5DTempNight, txtIn5DSunrise, txtIn5DSunset;
    TextView txtIn6DDescription, txtIn6DWind, txtIn6DWindGust, txtIn6DWindDirection, txtIn6DRain, txtIn6DMinMax;
    TextView txtIn6DPressure, txtIn6DTempMorning, txtIn6DTempDay, txtIn6DTempEve, txtIn6DTempNight, txtIn6DSunrise, txtIn6DSunset;
    TextView txtAlert;

    ImageView todayIcon, iconIn3h, iconIn6h, iconIn9h, iconIn12h, iconIn15h, iconTomorrow, iconTomorrowRain, iconIn2D, iconIn2DRain, iconIn3D, iconIn3DRain, iconIn4D, iconIn4DRain;;
    ImageView iconIn5D, iconIn5DRain, iconIn6D, iconIn6DRain, iconHourlyRain;

    SwipeRefreshLayout swipeRefreshLayout;

    private final String url = "https://api.openweathermap.org/data/3.0/onecall?";
    String weatherAPI = BuildConfig.WEATHER_API_KEY;

    double lat = 52.15;
    double lon = 21.02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        todayIcon = findViewById(R.id.imageView); iconTomorrow = findViewById(R.id.iconTomorrow);
        todayTemperature = findViewById(R.id.txtTemperature);
        txtWind = findViewById(R.id.txtWind); txtTomorrowWind = findViewById(R.id.txtTomorrowWind);
        txtRain = findViewById(R.id.txtRain); txtTomorrowRain = findViewById(R.id.txtTomorrowRain);
        txtDescription = findViewById(R.id.txtDescription); txtTomorrowDescription = findViewById(R.id.txtTomorrowDescription);
        txtPressure = findViewById(R.id.txtPressure); txtTomorrowPressure = findViewById(R.id.txtTomorrowPressure);
        txtFeelsLike = findViewById(R.id.txtFeelsLike);
        txtWindDirection = findViewById(R.id.txtWindDirection); txtTomorrowWindDirection = findViewById(R.id.txtTomorrowWindDeg);
        txtMinMaxTmp = findViewById(R.id.txtMaxMinTmp);
        txtIn3h = findViewById(R.id.in3h); txtIn6h = findViewById(R.id.in6h); txtIn9h = findViewById(R.id.in9h); txtIn12h = findViewById(R.id.in12h); txtIn15h = findViewById(R.id.in15h);
        txtIn3hTemp = findViewById(R.id.txtIn3hTemp); txtIn6hTemp = findViewById(R.id.txtIn6hTemp); txtIn9hTemp = findViewById(R.id.txtIn9hTemp);
        txtIn12hTemp = findViewById(R.id.txtIn12hTemp); txtIn15hTemp = findViewById(R.id.txtIn15hTemp);
        txtIn3hWind = findViewById(R.id.txtIn3hWind); txtIn6hWind = findViewById(R.id.txtIn6hWind); txtIn9hWind = findViewById(R.id.txtIn9hWind);
        txtIn12hWind = findViewById(R.id.txtIn12hWind); txtIn15hWind = findViewById(R.id.txtIn15hWind);
        txtIn3hWindGust = findViewById(R.id.txtIn3hWindGust); txtIn6hWindGust = findViewById(R.id.txtIn6hWindGust); txtIn9hWindGust = findViewById(R.id.txtIn9hWindGust);
        txtIn12hWindGust = findViewById(R.id.txtIn12hWindGust); txtIn15hWindGust = findViewById(R.id.txtIn15hWindGust);
        txtIn3hRain = findViewById(R.id.txtIn3hPrecipitation); txtIn6hRain = findViewById(R.id.txtIn6hPrecipitation); txtIn9hRain = findViewById(R.id.txtIn9hPrecipitation);
        txtIn12hRain = findViewById(R.id.txtIn12hPrecipitation); txtIn15hRain = findViewById(R.id.txtIn15hPrecipitation);
        txtSunrise = findViewById(R.id.txtSunrise); txtSunset = findViewById(R.id.txtSunset);
        txtTomorrowSunrise = findViewById(R.id.txtTomorrowSunrise); txtTomorrowSunset = findViewById(R.id.txtTomorrowSunset);
        txtTomorrowWindGust = findViewById(R.id.txtTomorrowWindGust);
        txtTomorrowMinMax = findViewById(R.id.txtTomorrowTempMinMax); txtTomorrowTempMorning = findViewById(R.id.txtTomorrowTempMorning); txtTomorrowTempDay = findViewById(R.id.txtTomorrowTempDay);
        txtTomorrowTempEve = findViewById(R.id.txtTomorrowTempEve); txtTomorrowTempNight = findViewById(R.id.txtTomorrowTempNight);
        iconIn3h = findViewById(R.id.IconIn3h); iconIn6h = findViewById(R.id.IconIn6h); iconIn9h = findViewById(R.id.IconIn9h);
        iconIn12h = findViewById(R.id.IconIn12h); iconIn15h = findViewById(R.id.IconIn15h); iconTomorrowRain = findViewById(R.id.iconRainTomorrow);
        txtIn2DSunrise = findViewById(R.id.txtIn2DSunrise); txtIn2DSunset = findViewById(R.id.txtIn2DSunset);
        txtIn2DWindGust = findViewById(R.id.txtIn2DWindGust);
        txtIn2DMinMax = findViewById(R.id.txtIn2DTempMinMax); txtIn2DTempMorning = findViewById(R.id.txtIn2DTempMorning); txtIn2DTempDay = findViewById(R.id.txtIn2DTempDay);
        txtIn2DTempEve = findViewById(R.id.txtIn2DTempEve); txtIn2DTempNight = findViewById(R.id.txtIn2DTempNight); txtIn2DDescription = findViewById(R.id.txtIn2DDescription);
        txtIn2DPressure = findViewById(R.id.txtIn2DPressure); txtIn2DWindDirection = findViewById(R.id.txtIn2DWindDeg); txtIn2DWind = findViewById(R.id.txtIn2DWind);
        txtIn2DRain = findViewById(R.id.txtIn2DRain); iconIn2D = findViewById(R.id.iconIn2D); iconIn2DRain = findViewById(R.id.iconRainIn2D);
        txtIn3DSunrise = findViewById(R.id.txtIn3DSunrise); txtIn3DSunset = findViewById(R.id.txtIn3DSunset);
        txtIn3DWindGust = findViewById(R.id.txtIn3DWindGust);
        txtIn3DMinMax = findViewById(R.id.txtIn3DTempMinMax); txtIn3DTempMorning = findViewById(R.id.txtIn3DTempMorning); txtIn3DTempDay = findViewById(R.id.txtIn3DTempDay);
        txtIn3DTempEve = findViewById(R.id.txtIn3DTempEve); txtIn3DTempNight = findViewById(R.id.txtIn3DTempNight); txtIn3DDescription = findViewById(R.id.txtIn3DDescription);
        txtIn3DPressure = findViewById(R.id.txtIn3DPressure); txtIn3DWindDirection = findViewById(R.id.txtIn3DWindDeg); txtIn3DWind = findViewById(R.id.txtIn3DWind);
        txtIn3DRain = findViewById(R.id.txtIn3DRain); iconIn3D = findViewById(R.id.iconIn3D); iconIn3DRain = findViewById(R.id.iconRainIn3D);
        txtIn4DSunrise = findViewById(R.id.txtIn4DSunrise); txtIn4DSunset = findViewById(R.id.txtIn4DSunset);
        txtIn4DWindGust = findViewById(R.id.txtIn4DWindGust);
        txtIn4DMinMax = findViewById(R.id.txtIn4DTempMinMax); txtIn4DTempMorning = findViewById(R.id.txtIn4DTempMorning); txtIn4DTempDay = findViewById(R.id.txtIn4DTempDay);
        txtIn4DTempEve = findViewById(R.id.txtIn4DTempEve); txtIn4DTempNight = findViewById(R.id.txtIn4DTempNight); txtIn4DDescription = findViewById(R.id.txtIn4DDescription);
        txtIn4DPressure = findViewById(R.id.txtIn4DPressure); txtIn4DWindDirection = findViewById(R.id.txtIn4DWindDeg); txtIn4DWind = findViewById(R.id.txtIn4DWind);
        txtIn4DRain = findViewById(R.id.txtIn4DRain); iconIn4D = findViewById(R.id.iconIn4D); iconIn4DRain = findViewById(R.id.iconRainIn4D);
        txtIn5DSunrise = findViewById(R.id.txtIn5DSunrise); txtIn5DSunset = findViewById(R.id.txtIn5DSunset);
        txtIn5DWindGust = findViewById(R.id.txtIn5DWindGust);
        txtIn5DMinMax = findViewById(R.id.txtIn5DTempMinMax); txtIn5DTempMorning = findViewById(R.id.txtIn5DTempMorning); txtIn5DTempDay = findViewById(R.id.txtIn5DTempDay);
        txtIn5DTempEve = findViewById(R.id.txtIn5DTempEve); txtIn5DTempNight = findViewById(R.id.txtIn5DTempNight); txtIn5DDescription = findViewById(R.id.txtIn5DDescription);
        txtIn5DPressure = findViewById(R.id.txtIn5DPressure); txtIn5DWindDirection = findViewById(R.id.txtIn5DWindDeg); txtIn5DWind = findViewById(R.id.txtIn5DWind);
        txtIn5DRain = findViewById(R.id.txtIn5DRain); iconIn5D = findViewById(R.id.iconIn5D); iconIn5DRain = findViewById(R.id.iconRainIn5D);
        txtIn6DSunrise = findViewById(R.id.txtIn6DSunrise); txtIn6DSunset = findViewById(R.id.txtIn6DSunset);
        txtIn6DWindGust = findViewById(R.id.txtIn6DWindGust);
        txtIn6DMinMax = findViewById(R.id.txtIn6DTempMinMax); txtIn6DTempMorning = findViewById(R.id.txtIn6DTempMorning); txtIn6DTempDay = findViewById(R.id.txtIn6DTempDay);
        txtIn6DTempEve = findViewById(R.id.txtIn6DTempEve); txtIn6DTempNight = findViewById(R.id.txtIn6DTempNight); txtIn6DDescription = findViewById(R.id.txtIn6DDescription);
        txtIn6DPressure = findViewById(R.id.txtIn6DPressure); txtIn6DWindDirection = findViewById(R.id.txtIn6DWindDeg); txtIn6DWind = findViewById(R.id.txtIn6DWind);
        txtIn6DRain = findViewById(R.id.txtIn6DRain); iconIn6D = findViewById(R.id.iconIn6D); iconIn6DRain = findViewById(R.id.iconRainIn6D);
        txtAlert = findViewById(R.id.txtAlert);
        iconHourlyRain = findViewById(R.id.iconHourlyRain);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        txtLocation = findViewById(R.id.txtLocation);
        
        getWeatherInfo();

        txtLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startForResult.launch(intent);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getWeatherInfo();

                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }
// opening new activity with map to search for new location and getting lat lon as result
    public void openMapActivity() {
        Intent intent = new Intent(this, MapActivity.class);
    }
    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result != null && result.getResultCode() == RESULT_OK){
                if(result.getData() != null &&
                        result.getData().getDoubleExtra("LAT1", 52.15) != 52.15 &&
                        result.getData().getDoubleExtra("LON1", 21.02) != 21.02 &&
                        result.getData().getStringExtra(MapActivity.PLACE_LABEL) != null){
                    lat = result.getData().getDoubleExtra("LAT1", 52.15);
                    lon = result.getData().getDoubleExtra("LON1", 21.02);
                    txtLocation.setText(result.getData().getStringExtra(MapActivity.PLACE_LABEL));
                    getWeatherInfo();
                    Log.i("MyWeatherApp: " , "Changed lat is: " + lat );
                    Log.i("MyWeatherApp: " , "Changed lon is: " + lon );
                }
            }
        }
    });


    public void getWeatherInfo() {
        String requestUrl = url + "lat=" + lat + "&lon=" + lon + "&units=metric" + "&appid=" + weatherAPI;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String timeZone = jsonResponse.getString("timezone");
                    setTitles(timeZone);

                    Context context = getApplicationContext();
                    // setting current weather
                    JSONObject jsonObjectCurrent = jsonResponse.getJSONObject("current");
                    JSONArray jsonArrayWeather = jsonObjectCurrent.getJSONArray("weather");
                    JSONObject jsonObjectWeather0 = jsonArrayWeather.getJSONObject(0);
                    //daily
                    JSONArray jsonArrayDaily = jsonResponse.getJSONArray("daily");
                    JSONObject jsonObjectDaily0 = jsonArrayDaily.getJSONObject(0);
                    JSONObject jsonObjectDaily0temp = jsonObjectDaily0.getJSONObject("temp");

                    long DailyMaxTemp0 = Math.round(jsonObjectDaily0temp.getDouble("max"));
                    long DailyMinTemp0 = Math.round(jsonObjectDaily0temp.getDouble("min"));
                    txtMinMaxTmp.setText(" " + DailyMaxTemp0 + "" + '\u00B0' + "C" + "/" + DailyMinTemp0 + '\u00B0' + "C");
                    if (jsonObjectDaily0.has("rain")) {
                        long currentRain = Math.round((jsonObjectDaily0.getDouble("rain") * 10)) / 10;
                        if(currentRain>0){
                            txtRain.setText(" " + currentRain + "mm");
                        }
                    }else if(jsonObjectDaily0.has("snow")) {
                        long currentSnow = Math.round(jsonObjectDaily0.getDouble("snow"));
                        if(currentSnow>0) {
                            txtRain.setText(" " + currentSnow + "mm");
                        }
                    }
                    //current weather conditions
                    long currentTime = jsonObjectCurrent.getLong("dt");
                    long currentTemp = Math.round(jsonObjectCurrent.getDouble("temp"));
                    long currentFeelsLike = Math.round(jsonObjectCurrent.getDouble("feels_like"));
                    int currentPressure = jsonObjectCurrent.getInt("pressure");
                    long todayWind = Math.round(jsonObjectDaily0.getDouble("wind_speed"));
                    int currentWindDirection = jsonObjectCurrent.getInt("wind_deg");
                    String currentDescription = jsonObjectWeather0.getString("description");
                    if(jsonObjectCurrent.has("sunrise")) {
                        long Sunrise = jsonObjectCurrent.getLong("sunrise");
                        txtSunrise.setText("Sunrise: " + unixConverter(Sunrise, timeZone) + " ");
                    }else{
                        txtSunrise.setText("Sunrise: ---");
                    }
                    if(jsonObjectCurrent.has("sunset")) {
                        long Sunset = jsonObjectCurrent.getLong("sunset");
                        txtSunset.setText("Sunset: " + unixConverter(Sunset, timeZone) + " ");
                    }else{
                        txtSunset.setText("Sunset: ---");
                    }
                    todayTemperature.setText(currentTemp + "" + '\u00B0' + "C");
                    txtWind.setText(todayWind + "km/h");
                    txtPressure.setText(currentPressure + "hPa");
                    txtDescription.setText(" " + currentDescription);
                    txtFeelsLike.setText(" Feels like: " + currentFeelsLike + '\u00B0' + "C");
                    txtWindDirection.setText(setWindDirection(currentWindDirection));
                    int iconToday = getImageId(context, jsonObjectWeather0.getString("icon"));
                    todayIcon.setImageResource(iconToday);

                    //Alert
                    if(jsonResponse.has("alerts")){
                        JSONArray jsonArrayAlerts = jsonResponse.getJSONArray("alerts");
                        JSONObject jsonObjectAlert0 = jsonArrayAlerts.getJSONObject(0);
                        String alertDescription = jsonObjectAlert0.getString("description");
                        String alertSender_Name = jsonObjectAlert0.getString("sender_name");
                        String alertEvent = jsonObjectAlert0.getString("event");
                        txtAlert.setPadding(10,2,4,10);
                        txtAlert.setText("Alert: " + alertEvent + " \n"
                                + alertSender_Name + ": \n"
                                + " \n"
                                + alertDescription);
                    }


                    JSONArray jsonArrayHourly = jsonResponse.getJSONArray("hourly");

                    //in3h
                    JSONObject jsonObjectHourly2 = jsonArrayHourly.getJSONObject(2);
                    JSONObject jsonObjectHourly3 = jsonArrayHourly.getJSONObject(3);
                    JSONObject jsonObjectHourly4 = jsonArrayHourly.getJSONObject(4);
                    JSONArray jsonArrayHourly3weather = jsonObjectHourly3.getJSONArray("weather");
                    JSONObject jsonObjectHourly3weather0 = jsonArrayHourly3weather.getJSONObject(0);
                    long timeIn3h = jsonObjectHourly3.getLong("dt");
                    txtIn3h.setText(unixConverter(timeIn3h, timeZone));
                    long tempIn3h = Math.round(jsonObjectHourly3.getDouble("temp"));
                    txtIn3hTemp.setText(tempIn3h + "" + '\u00B0');
                    long windIn3h = Math.round(jsonObjectHourly3.getDouble("wind_speed"));
                    txtIn3hWind.setText(windIn3h + "");
                    if(jsonObjectHourly3.has("wind_gust")) {
                        long windGustIn3h = Math.round(jsonObjectHourly3.getDouble("wind_gust"));
                        txtIn3hWindGust.setText(windGustIn3h + "");
                    }
                    int icIn3h = getImageId(context, jsonObjectHourly3weather0.getString("icon"));
                    iconIn3h.setImageResource(icIn3h);
                    long[] arrRain3 = {0,0,0};
                    long[] arrSnow3 = {0,0,0};
                    // Max rain of 2h, 3h, 4h
                    if (jsonObjectHourly2.has("rain") || jsonObjectHourly3.has("rain") || jsonObjectHourly4.has("rain") ) {
                        if(jsonObjectHourly2.has("rain")){
                            JSONObject jsonObjectHourly2Rain = jsonObjectHourly2.getJSONObject("rain");
                            long rainIn2h = Math.round(jsonObjectHourly2Rain.getDouble("1h"));
                            if(rainIn2h>0) {
                                arrRain3[0] = rainIn2h;
                            }
                        }if(jsonObjectHourly3.has("rain")){
                            JSONObject jsonObjectHourly3Rain = jsonObjectHourly3.getJSONObject("rain");
                            long rainIn3h = Math.round(jsonObjectHourly3Rain.getDouble("1h"));
                            if(rainIn3h>0) {
                                arrRain3[1] = rainIn3h;
                            }
                        }if(jsonObjectHourly4.has("rain")){
                            JSONObject jsonObjectHourly4Rain = jsonObjectHourly4.getJSONObject("rain");
                            long rainIn4h = Math.round(jsonObjectHourly4Rain.getDouble("1h"));
                            if(rainIn4h>0) {
                                arrRain3[2] = rainIn4h;
                            }
                        }
                        long largest = arrRain3[0];
                        for (int i = 1; i < arrRain3.length; i++) {
                            largest = Math.max(largest, arrRain3[i]);
                        }
                        if(largest>0) {
                            txtIn3hRain.setText(" " + largest);
                        }else{
                            txtIn3hRain.setText("1");
                        }

                    //Max snow of 2h, 3h, 4h
                    }else if(jsonObjectHourly2.has("snow") || jsonObjectHourly3.has("snow") || jsonObjectHourly4.has("snow")) {
                        if(jsonObjectHourly2.has("snow")) {
                            JSONObject jsonObjectHourly2Snow = jsonObjectHourly2.getJSONObject("snow");
                            long snowIn2h = Math.round(jsonObjectHourly2Snow.getDouble("1h"));
                            if(snowIn2h>0) {
                                arrSnow3[0] = snowIn2h;
                            }
                        }if(jsonObjectHourly3.has("snow")) {
                            JSONObject jsonObjectHourly3Snow = jsonObjectHourly3.getJSONObject("snow");
                            long snowIn3h = Math.round(jsonObjectHourly3Snow.getDouble("1h"));
                            if(snowIn3h>0) {
                                arrSnow3[1] = snowIn3h;
                            }
                        }if(jsonObjectHourly4.has("snow")) {
                            JSONObject jsonObjectHourly4Snow = jsonObjectHourly4.getJSONObject("snow");
                            long snowIn4h = Math.round(jsonObjectHourly4Snow.getDouble("1h"));
                            if(snowIn4h>0) {
                                arrSnow3[2] = snowIn4h;
                            }
                        }
                        long largest = arrSnow3[0];
                        for (int i = 1; i < arrSnow3.length; i++) {
                            largest = Math.max(largest, arrSnow3[i]);
                        }
                        if(largest>0) {
                            txtIn3hRain.setText(" " + largest);
                        }else{
                            txtIn3hRain.setText("1");
                        }

                    }else{
                        txtIn3hRain.setText("-");
                    }

                    //in6h
                    JSONObject jsonObjectHourly5 = jsonArrayHourly.getJSONObject(5);
                    JSONObject jsonObjectHourly6 = jsonArrayHourly.getJSONObject(6);
                    JSONObject jsonObjectHourly7 = jsonArrayHourly.getJSONObject(7);
                    JSONArray jsonArrayHourly6weather = jsonObjectHourly6.getJSONArray("weather");
                    JSONObject jsonObjectHourly6weather0 = jsonArrayHourly6weather.getJSONObject(0);
                    long timeIn6h = jsonObjectHourly6.getLong("dt");
                    txtIn6h.setText(unixConverter(timeIn6h, timeZone));
                    long tempIn6h = Math.round(jsonObjectHourly6.getDouble("temp"));
                    txtIn6hTemp.setText(tempIn6h + "" + '\u00B0');
                    long windIn6h = Math.round(jsonObjectHourly6.getDouble("wind_speed"));
                    txtIn6hWind.setText(windIn6h + "");
                    long windGustIn6h = Math.round(jsonObjectHourly6.getDouble("wind_gust"));
                    txtIn6hWindGust.setText(windGustIn6h + "");
                    int icIn6h = getImageId(context, jsonObjectHourly6weather0.getString("icon"));
                    iconIn6h.setImageResource(icIn6h);
                    long[] arrRain6 = {0,0,0};
                    long[] arrSnow6 = {0,0,0};
                    // Max rain of 5h, 6h, 7h
                    if (jsonObjectHourly5.has("rain") || jsonObjectHourly6.has("rain") || jsonObjectHourly7.has("rain") ) {
                        if(jsonObjectHourly5.has("rain")){
                            JSONObject jsonObjectHourly5Rain = jsonObjectHourly5.getJSONObject("rain");
                            long rainIn5h = Math.round(jsonObjectHourly5Rain.getDouble("1h"));
                            if(rainIn5h>0) {
                                arrRain6[0] = rainIn5h;
                            }
                        }if(jsonObjectHourly6.has("rain")){
                            JSONObject jsonObjectHourly6Rain = jsonObjectHourly6.getJSONObject("rain");
                            long rainIn6h = Math.round(jsonObjectHourly6Rain.getDouble("1h"));
                            if(rainIn6h>0) {
                                arrRain6[1] = rainIn6h;
                            }
                        }if(jsonObjectHourly7.has("rain")){
                            JSONObject jsonObjectHourly7Rain = jsonObjectHourly7.getJSONObject("rain");
                            long rainIn7h = Math.round(jsonObjectHourly7Rain.getDouble("1h"));
                            if(rainIn7h>0) {
                                arrRain6[2] = rainIn7h;
                            }
                        }
                        long largest = arrRain6[0];
                        for (int i = 1; i < arrRain6.length; i++) {
                            largest = Math.max(largest, arrRain6[i]);
                        }
                        if(largest>0) {
                            txtIn6hRain.setText(" " + largest);
                        }else{
                            txtIn6hRain.setText("1");
                        }

                        //Max snow of 5h, 6h, 7h
                    }else if(jsonObjectHourly5.has("snow") || jsonObjectHourly6.has("snow") || jsonObjectHourly7.has("snow")) {
                        if(jsonObjectHourly5.has("snow")) {
                            JSONObject jsonObjectHourly5Snow = jsonObjectHourly5.getJSONObject("snow");
                            long snowIn5h = Math.round(jsonObjectHourly5Snow.getDouble("1h"));
                            if(snowIn5h>0) {
                                arrSnow6[0] = snowIn5h;
                            }
                        }if(jsonObjectHourly6.has("snow")) {
                            JSONObject jsonObjectHourly6Snow = jsonObjectHourly6.getJSONObject("snow");
                            long snowIn6h = Math.round(jsonObjectHourly6Snow.getDouble("1h"));
                            if(snowIn6h>0) {
                                arrSnow6[1] = snowIn6h;
                            }
                        }if(jsonObjectHourly7.has("snow")) {
                            JSONObject jsonObjectHourly7Snow = jsonObjectHourly7.getJSONObject("snow");
                            long snowIn7h = Math.round(jsonObjectHourly7Snow.getDouble("1h"));
                            if(snowIn7h>0) {
                                arrSnow6[2] = snowIn7h;
                            }
                        }
                        long largest = arrSnow6[0];
                        for (int i = 1; i < arrSnow6.length; i++) {
                            largest = Math.max(largest, arrSnow6[i]);
                        }
                        if(largest>0) {
                            txtIn6hRain.setText(" " + largest);
                        }else{
                            txtIn6hRain.setText("1");
                        }

                    }else{
                        txtIn6hRain.setText("-");
                    }

                    //in9h
                    JSONObject jsonObjectHourly8 = jsonArrayHourly.getJSONObject(8);
                    JSONObject jsonObjectHourly9 = jsonArrayHourly.getJSONObject(9);
                    JSONObject jsonObjectHourly10 = jsonArrayHourly.getJSONObject(10);
                    JSONArray jsonArrayHourly9weather = jsonObjectHourly9.getJSONArray("weather");
                    JSONObject jsonObjectHourly9weather0 = jsonArrayHourly9weather.getJSONObject(0);
                    long timeIn9h = jsonObjectHourly9.getLong("dt");
                    txtIn9h.setText(unixConverter(timeIn9h, timeZone));
                    long tempIn9h = Math.round(jsonObjectHourly9.getDouble("temp"));
                    txtIn9hTemp.setText(tempIn9h + "" + '\u00B0');
                    long windIn9h = Math.round(jsonObjectHourly9.getDouble("wind_speed"));
                    txtIn9hWind.setText(windIn9h + "");
                    long windGustIn9h = Math.round(jsonObjectHourly9.getDouble("wind_gust"));
                    txtIn9hWindGust.setText(windGustIn9h + "");
                    int icIn9h = getImageId(context, jsonObjectHourly9weather0.getString("icon"));
                    iconIn9h.setImageResource(icIn9h);
                    long[] arrRain9 = {0,0,0};
                    long[] arrSnow9 = {0,0,0};
                    // Max rain of 8h, 9h, 10h
                    if (jsonObjectHourly8.has("rain") || jsonObjectHourly9.has("rain") || jsonObjectHourly10.has("rain") ) {
                        if(jsonObjectHourly8.has("rain")){
                            JSONObject jsonObjectHourly8Rain = jsonObjectHourly8.getJSONObject("rain");
                            long rainIn8h = Math.round(jsonObjectHourly8Rain.getDouble("1h"));
                            if(rainIn8h>0) {
                                arrRain9[0] = rainIn8h;
                            }
                        }if(jsonObjectHourly9.has("rain")){
                            JSONObject jsonObjectHourly9Rain = jsonObjectHourly9.getJSONObject("rain");
                            long rainIn9h = Math.round(jsonObjectHourly9Rain.getDouble("1h"));
                            if(rainIn9h>0) {
                                arrRain9[1] = rainIn9h;
                            }
                        }if(jsonObjectHourly10.has("rain")){
                            JSONObject jsonObjectHourly10Rain = jsonObjectHourly10.getJSONObject("rain");
                            long rainIn10h = Math.round(jsonObjectHourly10Rain.getDouble("1h"));
                            if(rainIn10h>0) {
                                arrRain9[2] = rainIn10h;
                            }
                        }
                        long largest = arrRain9[0];
                        for (int i = 1; i < arrRain9.length; i++) {
                            largest = Math.max(largest, arrRain9[i]);
                        }
                        if(largest>0) {
                            txtIn9hRain.setText(" " + largest);
                        }else{
                            txtIn9hRain.setText("1");
                        }

                        //Max snow of 8h, 9h, 10h
                    }else if(jsonObjectHourly8.has("snow") || jsonObjectHourly9.has("snow") || jsonObjectHourly10.has("snow")) {
                        if(jsonObjectHourly8.has("snow")) {
                            JSONObject jsonObjectHourly8Snow = jsonObjectHourly8.getJSONObject("snow");
                            long snowIn8h = Math.round(jsonObjectHourly8Snow.getDouble("1h"));
                            if(snowIn8h>0) {
                                arrSnow9[0] = snowIn8h;
                            }
                        }if(jsonObjectHourly9.has("snow")) {
                            JSONObject jsonObjectHourly9Snow = jsonObjectHourly9.getJSONObject("snow");
                            long snowIn9h = Math.round(jsonObjectHourly9Snow.getDouble("1h"));
                            if(snowIn9h>0) {
                                arrSnow9[1] = snowIn9h;
                            }
                        }if(jsonObjectHourly10.has("snow")) {
                            JSONObject jsonObjectHourly10Snow = jsonObjectHourly10.getJSONObject("snow");
                            long snowIn10h = Math.round(jsonObjectHourly10Snow.getDouble("1h"));
                            if(snowIn10h>0) {
                                arrSnow9[2] = snowIn10h;
                            }
                        }
                        long largest = arrSnow9[0];
                        for (int i = 1; i < arrSnow9.length; i++) {
                            largest = Math.max(largest, arrSnow9[i]);
                        }
                        if(largest>0) {
                            txtIn9hRain.setText(" " + largest);
                        }else{
                            txtIn9hRain.setText("1");
                        }

                    }else{
                        txtIn9hRain.setText("-");
                    }

                    //in12h
                    JSONObject jsonObjectHourly11 = jsonArrayHourly.getJSONObject(11);
                    JSONObject jsonObjectHourly12 = jsonArrayHourly.getJSONObject(12);
                    JSONObject jsonObjectHourly13 = jsonArrayHourly.getJSONObject(13);
                    JSONArray jsonArrayHourly12weather = jsonObjectHourly12.getJSONArray("weather");
                    JSONObject jsonObjectHourly12weather0 = jsonArrayHourly12weather.getJSONObject(0);
                    long timeIn12h = jsonObjectHourly12.getLong("dt");
                    txtIn12h.setText(unixConverter(timeIn12h, timeZone));
                    long tempIn12h = Math.round(jsonObjectHourly12.getDouble("temp"));
                    txtIn12hTemp.setText(tempIn12h + "" + '\u00B0');
                    long windIn12h = Math.round(jsonObjectHourly12.getDouble("wind_speed"));
                    txtIn12hWind.setText(windIn12h + "");
                    long windGustIn12h = Math.round(jsonObjectHourly12.getDouble("wind_gust"));
                    txtIn12hWindGust.setText(windGustIn12h + "");
                    int icIn12h = getImageId(context, jsonObjectHourly12weather0.getString("icon"));
                    iconIn12h.setImageResource(icIn12h);
                    long[] arrRain12 = {0,0,0};
                    long[] arrSnow12 = {0,0,0};
                    // Max rain of 11h, 12h, 13h
                    if (jsonObjectHourly11.has("rain") || jsonObjectHourly12.has("rain") || jsonObjectHourly13.has("rain") ) {
                        if(jsonObjectHourly11.has("rain")){
                            JSONObject jsonObjectHourly11Rain = jsonObjectHourly11.getJSONObject("rain");
                            long rainIn11h = Math.round(jsonObjectHourly11Rain.getDouble("1h"));
                            if(rainIn11h>0) {
                                arrRain12[0] = rainIn11h;
                            }
                        }if(jsonObjectHourly12.has("rain")){
                            JSONObject jsonObjectHourly12Rain = jsonObjectHourly12.getJSONObject("rain");
                            long rainIn12h = Math.round(jsonObjectHourly12Rain.getDouble("1h"));
                            if(rainIn12h>0) {
                                arrRain12[1] = rainIn12h;
                            }
                        }if(jsonObjectHourly13.has("rain")){
                            JSONObject jsonObjectHourly13Rain = jsonObjectHourly13.getJSONObject("rain");
                            long rainIn13h = Math.round(jsonObjectHourly13Rain.getDouble("1h"));
                            if(rainIn13h>0) {
                                arrRain12[2] = rainIn13h;
                            }
                        }
                        long largest = arrRain12[0];
                        for (int i = 1; i < arrRain12.length; i++) {
                            largest = Math.max(largest, arrRain12[i]);
                        }
                        if(largest>0) {
                            txtIn12hRain.setText(" " + largest);
                        }else{
                            txtIn12hRain.setText("1");
                        }

                        //Max snow of 11h, 12h, 13h
                    }else if(jsonObjectHourly11.has("snow") || jsonObjectHourly12.has("snow") || jsonObjectHourly13.has("snow")) {
                        if(jsonObjectHourly11.has("snow")) {
                            JSONObject jsonObjectHourly11Snow = jsonObjectHourly11.getJSONObject("snow");
                            long snowIn11h = Math.round(jsonObjectHourly11Snow.getDouble("1h"));
                            if(snowIn11h>0) {
                                arrSnow12[0] = snowIn11h;
                            }
                        }if(jsonObjectHourly12.has("snow")) {
                            JSONObject jsonObjectHourly12Snow = jsonObjectHourly12.getJSONObject("snow");
                            long snowIn12h = Math.round(jsonObjectHourly12Snow.getDouble("1h"));
                            if(snowIn12h>0) {
                                arrSnow12[1] = snowIn12h;
                            }
                        }if(jsonObjectHourly13.has("snow")) {
                            JSONObject jsonObjectHourly13Snow = jsonObjectHourly13.getJSONObject("snow");
                            long snowIn13h = Math.round(jsonObjectHourly13Snow.getDouble("1h"));
                            if(snowIn13h>0) {
                                arrSnow12[2] = snowIn13h;
                            }
                        }
                        long largest = arrSnow12[0];
                        for (int i = 1; i < arrSnow12.length; i++) {
                            largest = Math.max(largest, arrSnow12[i]);
                        }
                        if(largest>0) {
                            txtIn12hRain.setText(" " + largest);
                        }else{
                            txtIn12hRain.setText("1");
                        }

                    }else{
                        txtIn12hRain.setText("-");
                    }

                    //in15h
                    JSONObject jsonObjectHourly14 = jsonArrayHourly.getJSONObject(14);
                    JSONObject jsonObjectHourly15 = jsonArrayHourly.getJSONObject(15);
                    JSONObject jsonObjectHourly16 = jsonArrayHourly.getJSONObject(16);
                    JSONArray jsonArrayHourly15weather = jsonObjectHourly15.getJSONArray("weather");
                    JSONObject jsonObjectHourly15weather0 = jsonArrayHourly15weather.getJSONObject(0);
                    long timeIn15h = jsonObjectHourly15.getLong("dt");
                    txtIn15h.setText(unixConverter(timeIn15h, timeZone));
                    long tempIn15h = Math.round(jsonObjectHourly15.getDouble("temp"));
                    txtIn15hTemp.setText(tempIn15h + "" + '\u00B0');
                    long windIn15h = Math.round(jsonObjectHourly15.getDouble("wind_speed"));
                    txtIn15hWind.setText(windIn15h + "");
                    long windGustIn15h = Math.round(jsonObjectHourly15.getDouble("wind_gust"));
                    txtIn15hWindGust.setText(windGustIn15h + "");
                    int icIn15h = getImageId(context, jsonObjectHourly15weather0.getString("icon"));
                    iconIn15h.setImageResource(icIn15h);
                    long[] arrRain15 = {0,0,0};
                    long[] arrSnow15 = {0,0,0};
                    // Max rain of 14h, 15h, 16h
                    if (jsonObjectHourly14.has("rain") || jsonObjectHourly15.has("rain") || jsonObjectHourly16.has("rain") ) {
                        if(jsonObjectHourly14.has("rain")){
                            JSONObject jsonObjectHourly14Rain = jsonObjectHourly14.getJSONObject("rain");
                            long rainIn14h = Math.round(jsonObjectHourly14Rain.getDouble("1h"));
                            if(rainIn14h>0) {
                                arrRain15[0] = rainIn14h;
                            }
                        }if(jsonObjectHourly15.has("rain")){
                            JSONObject jsonObjectHourly15Rain = jsonObjectHourly15.getJSONObject("rain");
                            long rainIn15h = Math.round(jsonObjectHourly15Rain.getDouble("1h"));
                            if(rainIn15h>0) {
                                arrRain15[1] = rainIn15h;
                            }
                        }if(jsonObjectHourly16.has("rain")){
                            JSONObject jsonObjectHourly16Rain = jsonObjectHourly16.getJSONObject("rain");
                            long rainIn16h = Math.round(jsonObjectHourly16Rain.getDouble("1h"));
                            if(rainIn16h>0) {
                                arrRain15[2] = rainIn16h;
                            }
                        }
                        long largest = arrRain15[0];
                        for (int i = 1; i < arrRain15.length; i++) {
                            largest = Math.max(largest, arrRain15[i]);
                        }
                        if(largest>0) {
                            txtIn15hRain.setText(" " + largest);
                        }else{
                            txtIn15hRain.setText("1");
                        }

                        //Max snow of 14h, 15h, 16h
                    }else if(jsonObjectHourly14.has("snow") || jsonObjectHourly15.has("snow") || jsonObjectHourly16.has("snow")) {
                        if(jsonObjectHourly14.has("snow")) {
                            JSONObject jsonObjectHourly14Snow = jsonObjectHourly14.getJSONObject("snow");
                            long snowIn14h = Math.round(jsonObjectHourly14Snow.getDouble("1h"));
                            if(snowIn14h>0) {
                                arrSnow15[0] = snowIn14h;
                            }
                        }if(jsonObjectHourly15.has("snow")) {
                            JSONObject jsonObjectHourly15Snow = jsonObjectHourly15.getJSONObject("snow");
                            long snowIn15h = Math.round(jsonObjectHourly15Snow.getDouble("1h"));
                            if(snowIn15h>0) {
                                arrSnow15[1] = snowIn15h;
                            }
                        }if(jsonObjectHourly16.has("snow")) {
                            JSONObject jsonObjectHourly16Snow = jsonObjectHourly16.getJSONObject("snow");
                            long snowIn16h = Math.round(jsonObjectHourly16Snow.getDouble("1h"));
                            if(snowIn16h>0) {
                                arrSnow15[2] = snowIn16h;
                            }
                        }
                        long largest = arrSnow15[0];
                        for (int i = 1; i < arrSnow15.length; i++) {
                            largest = Math.max(largest, arrSnow15[i]);
                        }
                        if(largest>0) {
                            txtIn15hRain.setText(" " + largest);
                        }else{
                            txtIn15hRain.setText("1");
                        }

                    }else{
                        txtIn15hRain.setText("-");
                    }





                    //Tomorrow
                    JSONObject jsonObjectDaily1 = jsonArrayDaily.getJSONObject(1);
                    JSONArray jsonArrayDaily1weather = jsonObjectDaily1.getJSONArray("weather");
                    JSONObject jsonObjectDaily1weather0 = jsonArrayDaily1weather.getJSONObject(0);
                    String tomorrowDescription = jsonObjectDaily1weather0.getString("description");
                    txtTomorrowDescription.setText(tomorrowDescription);
                    JSONObject jsonObjectDaily1Temp = jsonObjectDaily1.getJSONObject("temp");
                    long tomorrowTempMin = Math.round(jsonObjectDaily1Temp.getDouble("min"));
                    long tomorrowTempMax = Math.round(jsonObjectDaily1Temp.getDouble("max"));
                    txtTomorrowMinMax.setText(tomorrowTempMax + "" + '\u00B0' + "C" + "/" + tomorrowTempMin + '\u00B0' + "C");
                    int icTomorrow = getImageId(context, jsonObjectDaily1weather0.getString("icon"));
                    iconTomorrow.setImageResource(icTomorrow);
                    long windTomorrow = Math.round(jsonObjectDaily1.getDouble("wind_speed"));
                    txtTomorrowWind.setText(" " + windTomorrow + "km/h");
                    long windGustTomorrow = Math.round(jsonObjectDaily1.getDouble("wind_gust"));
                    txtTomorrowWindGust.setText(" " + windGustTomorrow + "km/h");
                    int tomorrowWindDirection = jsonObjectDaily1.getInt("wind_deg");
                    txtTomorrowWindDirection.setText(setWindDirection(tomorrowWindDirection));
                    if (jsonObjectDaily1.has("rain")) {
                        long rainTomorrow = Math.round(jsonObjectDaily1.getDouble("rain"));
                        if(rainTomorrow>0) {
                            txtTomorrowRain.setText(" " + rainTomorrow + "mm");
                            iconTomorrowRain.setImageResource(R.drawable.water_drops);
                        }
                    }else if(jsonObjectDaily1.has("snow")) {
                        long snowTomorrow = Math.round(jsonObjectDaily1.getDouble("snow"));
                        if(snowTomorrow>0) {
                            txtTomorrowRain.setText(" " + snowTomorrow + " mm");
                            iconTomorrowRain.setImageResource(R.drawable.snowflake);
                        }
                    }
                    if(jsonObjectDaily1.has("sunrise")) {
                        long sunriseTomorrow = jsonObjectDaily1.getLong("sunrise");
                        txtTomorrowSunrise.setText(" Sunrise: " + unixConverter(sunriseTomorrow, timeZone) + " ");
                    }else{
                        txtTomorrowSunrise.setText(" Sunrise: - ");
                    }
                    if(jsonObjectDaily1.has("sunset")) {
                        long sunsetTomorrow = jsonObjectDaily1.getLong("sunset");
                        txtTomorrowSunset.setText(" Sunset: " + unixConverter(sunsetTomorrow, timeZone) + " ");
                    }else{
                        txtTomorrowSunset.setText(" Sunset: - ");
                    }
                    int pressureTomorrow = jsonObjectDaily1.getInt("pressure");
                    txtTomorrowPressure.setText(" " + pressureTomorrow + "hPa");
                    long tempMornTomorrow = Math.round(jsonObjectDaily1Temp.getDouble("morn"));
                    txtTomorrowTempMorning.setText(" Morning: " + tempMornTomorrow + '\u00B0' + "C");
                    long tempDayTomorrow = Math.round(jsonObjectDaily1Temp.getDouble("day"));
                    txtTomorrowTempDay.setText(" Day: " + tempDayTomorrow + '\u00B0' + "C");
                    long tempEveTomorrow = Math.round(jsonObjectDaily1Temp.getDouble("eve"));
                    txtTomorrowTempEve.setText(" Evening: " + tempEveTomorrow + '\u00B0' + "C");
                    long tempNightTomorrow = Math.round(jsonObjectDaily1Temp.getDouble("night"));
                    txtTomorrowTempNight.setText(" Night: " + tempNightTomorrow + '\u00B0' + "C");

                    //In 2 Days
                    JSONObject jsonObjectDaily2 = jsonArrayDaily.getJSONObject(2);
                    JSONArray jsonArrayDaily2weather = jsonObjectDaily2.getJSONArray("weather");
                    JSONObject jsonObjectDaily2weather0 = jsonArrayDaily2weather.getJSONObject(0);
                    String In2DDescription = jsonObjectDaily2weather0.getString("description");
                    txtIn2DDescription.setText(In2DDescription);
                    JSONObject jsonObjectDaily2Temp = jsonObjectDaily2.getJSONObject("temp");
                    long In2DTempMin = Math.round(jsonObjectDaily2Temp.getDouble("min"));
                    long In2DTempMax = Math.round(jsonObjectDaily2Temp.getDouble("max"));
                    txtIn2DMinMax.setText(In2DTempMax + "" + '\u00B0' + "C" + "/" + In2DTempMin + '\u00B0' + "C");
                    int icIn2D = getImageId(context, jsonObjectDaily2weather0.getString("icon"));
                    iconIn2D.setImageResource(icIn2D);
                    long windIn2D = Math.round(jsonObjectDaily2.getDouble("wind_speed"));
                    txtIn2DWind.setText(" " + windIn2D + "km/h");
                    long windGustIn2D = Math.round(jsonObjectDaily2.getDouble("wind_gust"));
                    txtIn2DWindGust.setText(" " + windGustIn2D + "km/h");
                    int In2DWindDirection = jsonObjectDaily2.getInt("wind_deg");
                    txtIn2DWindDirection.setText(setWindDirection(In2DWindDirection));
                    if (jsonObjectDaily2.has("rain")) {
                        long rainIn2D = Math.round(jsonObjectDaily2.getDouble("rain"));
                        if(rainIn2D>0) {
                            txtIn2DRain.setText(" " + rainIn2D + "mm");
                            iconIn2DRain.setImageResource(R.drawable.water_drops);
                        }
                    }else if(jsonObjectDaily2.has("snow")) {
                        long snowIn2D = Math.round(jsonObjectDaily2.getDouble("snow"));
                        if(snowIn2D>0) {
                            txtIn2DRain.setText(" " + snowIn2D + " mm");
                            iconIn2DRain.setImageResource(R.drawable.snowflake);
                        }
                    }
                    if(jsonObjectDaily2.has("sunrise")) {
                        long sunriseIn2D = jsonObjectDaily2.getLong("sunrise");
                        txtIn2DSunrise.setText(" Sunrise: " + unixConverter(sunriseIn2D, timeZone) + " ");
                    }else{
                        txtIn2DSunrise.setText(" Sunrise: - ");
                    }
                    if(jsonObjectDaily2.has("sunset")) {
                        long sunsetIn2D = jsonObjectDaily2.getLong("sunset");
                        txtIn2DSunset.setText(" Sunset: " + unixConverter(sunsetIn2D, timeZone) + " ");
                    }else{
                        txtIn2DSunset.setText(" Sunset: - ");
                    }
                    int pressureIn2D = jsonObjectDaily2.getInt("pressure");
                    txtIn2DPressure.setText(" " + pressureIn2D + "hPa");
                    long tempMornIn2D = Math.round(jsonObjectDaily2Temp.getDouble("morn"));
                    txtIn2DTempMorning.setText(" Morning: " + tempMornIn2D + '\u00B0' + "C");
                    long tempDayIn2D = Math.round(jsonObjectDaily2Temp.getDouble("day"));
                    txtIn2DTempDay.setText(" Day: " + tempDayIn2D + '\u00B0' + "C");
                    long tempEveIn2D = Math.round(jsonObjectDaily2Temp.getDouble("eve"));
                    txtIn2DTempEve.setText(" Evening: " + tempEveIn2D + '\u00B0' + "C");
                    long tempNightIn2D = Math.round(jsonObjectDaily2Temp.getDouble("night"));
                    txtIn2DTempNight.setText(" Night: " + tempNightIn2D + '\u00B0' + "C");

                    //In 3 Days
                    JSONObject jsonObjectDaily3 = jsonArrayDaily.getJSONObject(3);
                    JSONArray jsonArrayDaily3weather = jsonObjectDaily3.getJSONArray("weather");
                    JSONObject jsonObjectDaily3weather0 = jsonArrayDaily3weather.getJSONObject(0);
                    String In3DDescription = jsonObjectDaily3weather0.getString("description");
                    txtIn3DDescription.setText(In3DDescription);
                    JSONObject jsonObjectDaily3Temp = jsonObjectDaily3.getJSONObject("temp");
                    long In3DTempMin = Math.round(jsonObjectDaily3Temp.getDouble("min"));
                    long In3DTempMax = Math.round(jsonObjectDaily3Temp.getDouble("max"));
                    txtIn3DMinMax.setText(In3DTempMax + "" + '\u00B0' + "C" + "/" + In3DTempMin + '\u00B0' + "C");
                    int icIn3D = getImageId(context, jsonObjectDaily3weather0.getString("icon"));
                    iconIn3D.setImageResource(icIn3D);
                    long windIn3D = Math.round(jsonObjectDaily3.getDouble("wind_speed"));
                    txtIn3DWind.setText(" " + windIn3D + "km/h");
                    long windGustIn3D = Math.round(jsonObjectDaily3.getDouble("wind_gust"));
                    txtIn3DWindGust.setText(" " + windGustIn3D + "km/h");
                    int In3DWindDirection = jsonObjectDaily3.getInt("wind_deg");
                    txtIn3DWindDirection.setText(setWindDirection(In3DWindDirection));
                    if (jsonObjectDaily3.has("rain")) {
                        long rainIn3D = Math.round(jsonObjectDaily3.getDouble("rain"));
                        if(rainIn3D>0) {
                            txtIn3DRain.setText(" " + rainIn3D + "mm");
                            iconIn3DRain.setImageResource(R.drawable.water_drops);
                        }
                    }else if(jsonObjectDaily3.has("snow")) {
                        long snowIn3D = Math.round(jsonObjectDaily3.getDouble("snow"));
                        if(snowIn3D>0) {
                            txtIn3DRain.setText(" " + snowIn3D + " mm");
                            iconIn3DRain.setImageResource(R.drawable.snowflake);
                        }
                    }
                    if(jsonObjectDaily3.has("sunrise")) {
                        long sunriseIn3D = jsonObjectDaily3.getLong("sunrise");
                        txtIn3DSunrise.setText(" Sunrise: " + unixConverter(sunriseIn3D, timeZone) + " ");
                    }else{
                        txtIn3DSunrise.setText(" Sunrise: - ");
                    }
                    if(jsonObjectDaily3.has("sunset")) {
                        long sunsetIn3D = jsonObjectDaily3.getLong("sunset");
                        txtIn3DSunset.setText(" Sunset: " + unixConverter(sunsetIn3D, timeZone) + " ");
                    }else{
                        txtIn3DSunset.setText(" Sunset: - ");
                    }
                    int pressureIn3D = jsonObjectDaily3.getInt("pressure");
                    txtIn3DPressure.setText(" " + pressureIn3D + "hPa");
                    long tempMornIn3D = Math.round(jsonObjectDaily3Temp.getDouble("morn"));
                    txtIn3DTempMorning.setText(" Morning: " + tempMornIn3D + '\u00B0' + "C");
                    long tempDayIn3D = Math.round(jsonObjectDaily3Temp.getDouble("day"));
                    txtIn3DTempDay.setText(" Day: " + tempDayIn3D + '\u00B0' + "C");
                    long tempEveIn3D = Math.round(jsonObjectDaily3Temp.getDouble("eve"));
                    txtIn3DTempEve.setText(" Evening: " + tempEveIn3D + '\u00B0' + "C");
                    long tempNightIn3D = Math.round(jsonObjectDaily3Temp.getDouble("night"));
                    txtIn3DTempNight.setText(" Night: " + tempNightIn3D + '\u00B0' + "C");

                    //In 4 Days
                    JSONObject jsonObjectDaily4 = jsonArrayDaily.getJSONObject(4);
                    JSONArray jsonArrayDaily4weather = jsonObjectDaily4.getJSONArray("weather");
                    JSONObject jsonObjectDaily4weather0 = jsonArrayDaily4weather.getJSONObject(0);
                    String In4DDescription = jsonObjectDaily4weather0.getString("description");
                    txtIn4DDescription.setText(In4DDescription);
                    JSONObject jsonObjectDaily4Temp = jsonObjectDaily4.getJSONObject("temp");
                    long In4DTempMin = Math.round(jsonObjectDaily4Temp.getDouble("min"));
                    long In4DTempMax = Math.round(jsonObjectDaily4Temp.getDouble("max"));
                    txtIn4DMinMax.setText(In4DTempMax + "" + '\u00B0' + "C" + "/" + In4DTempMin + '\u00B0' + "C");
                    int icIn4D = getImageId(context, jsonObjectDaily4weather0.getString("icon"));
                    iconIn4D.setImageResource(icIn4D);
                    long windIn4D = Math.round(jsonObjectDaily4.getDouble("wind_speed"));
                    txtIn4DWind.setText(" " + windIn4D + "km/h");
                    long windGustIn4D = Math.round(jsonObjectDaily4.getDouble("wind_gust"));
                    txtIn4DWindGust.setText(" " + windGustIn4D + "km/h");
                    int In4DWindDirection = jsonObjectDaily4.getInt("wind_deg");
                    txtIn4DWindDirection.setText(setWindDirection(In4DWindDirection));
                    if (jsonObjectDaily4.has("rain")) {
                        long rainIn4D = Math.round(jsonObjectDaily4.getDouble("rain"));
                        if(rainIn4D>0) {
                            txtIn4DRain.setText(" " + rainIn4D + "mm");
                            iconIn4DRain.setImageResource(R.drawable.water_drops);
                        }
                    }else if(jsonObjectDaily4.has("snow")) {
                        long snowIn4D = Math.round(jsonObjectDaily4.getDouble("snow"));
                        if(snowIn4D>0) {
                            txtIn4DRain.setText(" " + snowIn4D + " mm");
                            iconIn4DRain.setImageResource(R.drawable.snowflake);
                        }
                    }
                    if(jsonObjectDaily4.has("sunrise")) {
                        long sunriseIn4D = jsonObjectDaily4.getLong("sunrise");
                        txtIn4DSunrise.setText(" Sunrise: " + unixConverter(sunriseIn4D, timeZone) + " ");
                    }else{
                        txtIn4DSunrise.setText(" Sunrise: - ");
                    }
                    if(jsonObjectDaily4.has("sunset")) {
                        long sunsetIn4D = jsonObjectDaily4.getLong("sunset");
                        txtIn4DSunset.setText(" Sunset: " + unixConverter(sunsetIn4D, timeZone) + " ");
                    }else{
                        txtIn4DSunset.setText(" Sunset: - ");
                    }
                    int pressureIn4D = jsonObjectDaily4.getInt("pressure");
                    txtIn4DPressure.setText(" " + pressureIn4D + "hPa");
                    long tempMornIn4D = Math.round(jsonObjectDaily4Temp.getDouble("morn"));
                    txtIn4DTempMorning.setText(" Morning: " + tempMornIn4D + '\u00B0' + "C");
                    long tempDayIn4D = Math.round(jsonObjectDaily4Temp.getDouble("day"));
                    txtIn4DTempDay.setText(" Day: " + tempDayIn4D + '\u00B0' + "C");
                    long tempEveIn4D = Math.round(jsonObjectDaily4Temp.getDouble("eve"));
                    txtIn4DTempEve.setText(" Evening: " + tempEveIn4D + '\u00B0' + "C");
                    long tempNightIn4D = Math.round(jsonObjectDaily4Temp.getDouble("night"));
                    txtIn4DTempNight.setText(" Night: " + tempNightIn4D + '\u00B0' + "C");

                    //In 5 Days
                    JSONObject jsonObjectDaily5 = jsonArrayDaily.getJSONObject(5);
                    JSONArray jsonArrayDaily5weather = jsonObjectDaily5.getJSONArray("weather");
                    JSONObject jsonObjectDaily5weather0 = jsonArrayDaily5weather.getJSONObject(0);
                    String In5DDescription = jsonObjectDaily5weather0.getString("description");
                    txtIn5DDescription.setText(In5DDescription);
                    JSONObject jsonObjectDaily5Temp = jsonObjectDaily5.getJSONObject("temp");
                    long In5DTempMin = Math.round(jsonObjectDaily5Temp.getDouble("min"));
                    long In5DTempMax = Math.round(jsonObjectDaily5Temp.getDouble("max"));
                    txtIn5DMinMax.setText(In5DTempMax + "" + '\u00B0' + "C" + "/" + In5DTempMin + '\u00B0' + "C");
                    int icIn5D = getImageId(context, jsonObjectDaily5weather0.getString("icon"));
                    iconIn5D.setImageResource(icIn5D);
                    long windIn5D = Math.round(jsonObjectDaily5.getDouble("wind_speed"));
                    txtIn5DWind.setText(" " + windIn5D + "km/h");
                    long windGustIn5D = Math.round(jsonObjectDaily5.getDouble("wind_gust"));
                    txtIn5DWindGust.setText(" " + windGustIn5D + "km/h");
                    int In5DWindDirection = jsonObjectDaily5.getInt("wind_deg");
                    txtIn5DWindDirection.setText(setWindDirection(In5DWindDirection));
                    if (jsonObjectDaily5.has("rain")) {
                        long rainIn5D = Math.round(jsonObjectDaily5.getDouble("rain"));
                        if(rainIn5D>0) {
                            txtIn5DRain.setText(" " + rainIn5D + "mm");
                            iconIn5DRain.setImageResource(R.drawable.water_drops);
                        }
                    }else if(jsonObjectDaily5.has("snow")) {
                        long snowIn5D = Math.round(jsonObjectDaily5.getDouble("snow"));
                        if(snowIn5D>0) {
                            txtIn5DRain.setText(" " + snowIn5D + " mm");
                            iconIn5DRain.setImageResource(R.drawable.snowflake);
                        }
                    }
                    if(jsonObjectDaily5.has("sunrise")) {
                        long sunriseIn5D = jsonObjectDaily5.getLong("sunrise");
                        txtIn5DSunrise.setText(" Sunrise: " + unixConverter(sunriseIn5D, timeZone) + " ");
                    }else{
                        txtIn5DSunrise.setText(" Sunrise: - ");
                    }
                    if(jsonObjectDaily5.has("sunset")) {
                        long sunsetIn5D = jsonObjectDaily5.getLong("sunset");
                        txtIn5DSunset.setText(" Sunset: " + unixConverter(sunsetIn5D, timeZone) + " ");
                    }else{
                        txtIn5DSunset.setText(" Sunset: - ");
                    }
                    int pressureIn5D = jsonObjectDaily5.getInt("pressure");
                    txtIn5DPressure.setText(" " + pressureIn5D + "hPa");
                    long tempMornIn5D = Math.round(jsonObjectDaily5Temp.getDouble("morn"));
                    txtIn5DTempMorning.setText(" Morning: " + tempMornIn5D + '\u00B0' + "C");
                    long tempDayIn5D = Math.round(jsonObjectDaily5Temp.getDouble("day"));
                    txtIn5DTempDay.setText(" Day: " + tempDayIn5D + '\u00B0' + "C");
                    long tempEveIn5D = Math.round(jsonObjectDaily5Temp.getDouble("eve"));
                    txtIn5DTempEve.setText(" Evening: " + tempEveIn5D + '\u00B0' + "C");
                    long tempNightIn5D = Math.round(jsonObjectDaily5Temp.getDouble("night"));
                    txtIn5DTempNight.setText(" Night: " + tempNightIn5D + '\u00B0' + "C");

                    //In 6 Days
                    JSONObject jsonObjectDaily6 = jsonArrayDaily.getJSONObject(6);
                    JSONArray jsonArrayDaily6weather = jsonObjectDaily6.getJSONArray("weather");
                    JSONObject jsonObjectDaily6weather0 = jsonArrayDaily6weather.getJSONObject(0);
                    String In6DDescription = jsonObjectDaily6weather0.getString("description");
                    txtIn6DDescription.setText(In6DDescription);
                    JSONObject jsonObjectDaily6Temp = jsonObjectDaily6.getJSONObject("temp");
                    long In6DTempMin = Math.round(jsonObjectDaily6Temp.getDouble("min"));
                    long In6DTempMax = Math.round(jsonObjectDaily6Temp.getDouble("max"));
                    txtIn6DMinMax.setText(In6DTempMax + "" + '\u00B0' + "C" + "/" + In6DTempMin + '\u00B0' + "C");
                    int icIn6D = getImageId(context, jsonObjectDaily6weather0.getString("icon"));
                    iconIn6D.setImageResource(icIn6D);
                    long windIn6D = Math.round(jsonObjectDaily6.getDouble("wind_speed"));
                    txtIn6DWind.setText(" " + windIn6D + "km/h");
                    long windGustIn6D = Math.round(jsonObjectDaily6.getDouble("wind_gust"));
                    txtIn6DWindGust.setText(" " + windGustIn6D + "km/h");
                    int In6DWindDirection = jsonObjectDaily6.getInt("wind_deg");
                    txtIn6DWindDirection.setText(setWindDirection(In6DWindDirection));
                    if (jsonObjectDaily6.has("rain")) {
                        long rainIn6D = Math.round(jsonObjectDaily6.getDouble("rain"));
                        if(rainIn6D>0) {
                            txtIn6DRain.setText(" " + rainIn6D + "mm");
                            iconIn6DRain.setImageResource(R.drawable.water_drops);
                        }
                    }else if(jsonObjectDaily6.has("snow")) {
                        long snowIn6D = Math.round(jsonObjectDaily6.getDouble("snow"));
                        if(snowIn6D>0) {
                            txtIn6DRain.setText(" " + snowIn6D + " mm");
                            iconIn6DRain.setImageResource(R.drawable.snowflake);
                        }
                    }
                    if(jsonObjectDaily6.has("sunrise")) {
                        long sunriseIn6D = jsonObjectDaily6.getLong("sunrise");
                        txtIn6DSunrise.setText(" Sunrise: " + unixConverter(sunriseIn6D, timeZone) + " ");
                    }else{
                        txtIn6DSunrise.setText(" Sunrise: - ");
                    }
                    if(jsonObjectDaily6.has("sunset")) {
                        long sunsetIn6D = jsonObjectDaily6.getLong("sunset");
                        txtIn6DSunset.setText(" Sunset: " + unixConverter(sunsetIn6D, timeZone) + " ");
                    }else{
                        txtIn6DSunset.setText(" Sunset: - ");
                    }

                    int pressureIn6D = jsonObjectDaily6.getInt("pressure");
                    txtIn6DPressure.setText(" " + pressureIn6D + "hPa");
                    long tempMornIn6D = Math.round(jsonObjectDaily6Temp.getDouble("morn"));
                    txtIn6DTempMorning.setText(" Morning: " + tempMornIn6D + '\u00B0' + "C");
                    long tempDayIn6D = Math.round(jsonObjectDaily6Temp.getDouble("day"));
                    txtIn6DTempDay.setText(" Day: " + tempDayIn6D + '\u00B0' + "C");
                    long tempEveIn6D = Math.round(jsonObjectDaily6Temp.getDouble("eve"));
                    txtIn6DTempEve.setText(" Evening: " + tempEveIn6D + '\u00B0' + "C");
                    long tempNightIn6D = Math.round(jsonObjectDaily6Temp.getDouble("night"));
                    txtIn6DTempNight.setText(" Night: " + tempNightIn6D + '\u00B0' + "C");




                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

// time converter
    public String unixConverter(long time, String timeZone){
        Date date = new Date(time*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        String newTime = sdf.format(date);
        return newTime;
    }

// setting titles for all tiles depending on time zone of current location
    void setTitles (String timeZone) {
        todayTitle = findViewById(R.id.txtToday);
        tomorrowTitle = findViewById(R.id.txtTomorrow);
        in2daysTitle = findViewById(R.id.txtIn2D);
        in3daysTitle = findViewById(R.id.txtIn3D);
        in4daysTitle = findViewById(R.id.txtIn4D);
        in5daysTitle = findViewById(R.id.txtIn5D);
        in6daysTitle = findViewById(R.id.txtIn6D);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
        String todayTxt = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
        //todayTitle.setText(todayTxt);

        calendar.add(Calendar.DAY_OF_WEEK, 1);
        String tomorrow = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
        tomorrowTitle.setText(tomorrow);

            calendar.add(Calendar.DAY_OF_WEEK, 1);
            String dayTxt2 = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
            in2daysTitle.setText(dayTxt2);

            calendar.add(Calendar.DAY_OF_WEEK, 1);
            String dayTxt3 = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
            in3daysTitle.setText(dayTxt3);

            calendar.add(Calendar.DAY_OF_WEEK, 1);
            String dayTxt4 = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
            in4daysTitle.setText(dayTxt4);

            calendar.add(Calendar.DAY_OF_WEEK, 1);
            String dayTxt5 = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
            in5daysTitle.setText(dayTxt5);

            calendar.add(Calendar.DAY_OF_WEEK, 1);
            String dayTxt6 = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
            in6daysTitle.setText(dayTxt6);
        }

// wind direction converter from degree to name
        public String setWindDirection(int windDegree){
            String direction = "";
            if(windDegree>=0 && windDegree<=23 || windDegree>=337 && windDegree<=360){
                direction = " North";
            }else if( windDegree>=24 && windDegree<=68){
                direction = " Northeast";
            }else if( windDegree>=69 && windDegree<=113){
                direction = " East";
            }else if( windDegree>=114 && windDegree<=158){
                direction = " Southeast";
            }else if( windDegree>=159 && windDegree<=203){
                direction = " South";
            }else if( windDegree>=204 && windDegree<=248){
                direction = " Southwest";
            }else if( windDegree>=249 && windDegree<=293){
                direction = " West";
            }else if( windDegree>=294 && windDegree<=336){
                direction = " Northwest";
            }
            return direction;
        }
        // setting icons on tiles depending on current weather
        public static int getImageId(Context context, String imageName){
        return context.getResources().getIdentifier("drawable/_" + imageName, null, context.getPackageName());
        }


    }