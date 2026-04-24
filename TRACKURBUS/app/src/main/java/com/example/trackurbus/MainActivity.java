package com.example.trackurbus;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.function.Consumer;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase db;
    DatabaseReference reference;
    private double lastLatitude = 0.0;
    private double lastLongitude = 0.0;
    private boolean isMapOpened = false;
    private Button btnOpenMaps;

    //float latitude;
    String val;
    String val_1;
    // float longitude;
    float num_1;
    float num_2;
    //float fire_data_la;
    //float fire_data_lo;
    double lat;
    double so_lat;
    double so_lon;
    private TextView textview;
    private TextView textview_1;
    private FusedLocationProviderClient fusedLocationClient;
    private GoogleMap googleMap;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        //textview = findViewById(R.id.textview);
        //textview_1=findViewById(R.id.textview_1);
        //reference= FirebaseDatabase.getInstance().getReference() ;
        Button btnOpenMap = findViewById(R.id.btnOpenMap);
        reference = FirebaseDatabase.getInstance().getReference("s_data");
        // getdata ();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                val_1=dataSnapshot.child("latitude").getValue().toString();
                // textview.setText(val_1);
                num_1=Float.parseFloat(val_1);
                val=dataSnapshot.child("longitude").getValue().toString();
                //textview_1.setText(val);
                num_2=Float.parseFloat(val);
                // updateLocationOnMap(num_1, num_2);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnOpenMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLocationOnMap(num_1, num_2);
            }
        });
        // Draw route on the map
        // drawRouteOnMap(so_lat,so_lon, num_1, num_2);
    }
    private void updateLocationOnMap(float lat, float lon) {
        // Implement your map logic to update the location
        Uri uri= Uri.parse("https://maps.google.com/maps?daddr="+lat+","+lon);
        Intent intent =new Intent(Intent.ACTION_VIEW,uri);

        // For example, open Google Maps with an intent
        //    Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude);
        //  Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        // mapIntent.setPackage("com.google.android.apps.maps");
      /*  Intent intent =new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + latitude +","+ longitude));
        intent.setPackage("com.google.android.apps.maps");
        // Check if there is an app to handle the intent
        if (intent.resolveActivity(getPackageManager()) != null) {
           startActivity(intent);
        } else {
            // Handle the case where Google Maps app is not installed
            // Alternatively, you can redirect the user to the Google Maps web page
            // using a WebView or another approach.
            // For simplicity, we'll show a Toast message.
            Toast.makeText(this, "Google Maps app not installed", Toast.LENGTH_SHORT).show();
        }*/
    }
    private void drawRouteOnMap(double startLat, double startLon, double endLat, double endLon) {
        // Use the Google Maps Directions API to get directions between start and end points
        // You need to implement this part using the Directions API

        // Once you have the directions, you can use PolylineOptions to draw the route on the map
        PolylineOptions polylineOptions = new PolylineOptions()
                .add(new LatLng(startLat, startLon))
                .add(new LatLng(endLat, endLon))
                .width(5)
                .color(Color.RED);

        // Add the polyline to the map
        googleMap.addPolyline(polylineOptions);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getLastLocation(Consumer<Location> locationConsumer) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            locationConsumer.accept(location);
                        }
                    });
        } else {
            // Handle the case where location permission is not granted
            // You may request permission here or handle it in a different way
        }
    }

}
