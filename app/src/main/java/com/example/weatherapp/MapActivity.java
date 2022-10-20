package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.geo.location.AWSLocationGeoPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.geo.models.Coordinates;
import com.amplifyframework.geo.maplibre.view.AmplifyMapView;


public class MapActivity extends AppCompatActivity {
    double lat;
    double lon;
    public static final String PLACE_LABEL = "Warsaw, Poland";
    TextView txtLocation;
    private AmplifyMapView mapView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        txtLocation = findViewById(R.id.txtLocation);
        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSLocationGeoPlugin());
            Amplify.configure(getApplicationContext());
            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }
        setContentView(R.layout.activity_map);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        mapView = findViewById(R.id.mapView);

        //TODO amplifyMapView.set

        mapView.setOnPlaceSelectListener((place, symbol) -> {
            Log.i("MyAmplifyApp", "The selected place is " + place.getLabel());
            Log.i("MyAmplifyApp", "It is located at " + place.getCoordinates());
            Coordinates newOne = place.getCoordinates();
            lat = newOne.getLatitude();
            lon = newOne.getLongitude();
            String placeLabel = place.getLabel();
            Intent intent = new Intent();
            intent.putExtra("LAT1", lat);
            intent.putExtra("LON1", lon);
            intent.putExtra(PLACE_LABEL, placeLabel);
            setResult(RESULT_OK, intent);
            finish();
            Log.i("MyAmplifyApp", "Latitude is: " + lat);
            Log.i("MyAmplifyApp", "Longitude is: " + lon);
        });
        Log.i("MyAmplifyApp", "Latitude2 is: " + lat);
        Log.i("MyAmplifyApp", "Longitude2 is: " + lon);
        }
}