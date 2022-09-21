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
import com.amplifyframework.geo.models.CountryCode;
import com.amplifyframework.geo.models.Place;
import com.amplifyframework.geo.maplibre.view.AmplifyMapView;
import com.amplifyframework.geo.options.GeoSearchByTextOptions;

import java.util.Collections;


public class MapActivity extends AppCompatActivity {
    double lat = 52.15;
    double lon = 21.02;
    TextView txtLocalization;
    private AmplifyMapView mapView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        txtLocalization = findViewById(R.id.txtLocalization);
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
        //amplifyMapView.autofill();

        GeoSearchByTextOptions options = GeoSearchByTextOptions.builder()
                .countries(Collections.singletonList(CountryCode.POL))
                .build();


        String searchQuery = "Warszawa";

        Amplify.Geo.searchByText(searchQuery,options,
                result -> {
                    for (final Place place : result.getPlaces()) {
                        Log.i("MyAmplifyApp", place.toString());
                    }
                },
                error -> Log.e("MyAmplifyApp", "Failed to search for " + searchQuery, error)
        );


        //TODO amplifyMapView.set

        mapView.setOnPlaceSelectListener((place, symbol) -> {
            Log.i("MyAmplifyApp", "The selected place is " + place.getLabel());
            Log.i("MyAmplifyApp", "It is located at " + place.getCoordinates());
            openMainActivity();
            Coordinates newOne = place.getCoordinates();
            lat = newOne.getLatitude();
            lon = newOne.getLongitude();
            Log.i("MyAmplifyApp", "Latitude is: " + lat);
            Log.i("MyAmplifyApp", "Longitude is: " + lon);
            //MainActivity setLatLon = new MainActivity();
            //setLatLon.setLat(lat);
            //setLatLon.setLon(lon);
        });
        Log.i("MyAmplifyApp", "Latitude2 is: " + lat);
        Log.i("MyAmplifyApp", "Longitude2 is: " + lon);




        }
    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public double getLat(){
        return this.lat;
    }
    public double getLon(){
        return this.lon;
    }



}





