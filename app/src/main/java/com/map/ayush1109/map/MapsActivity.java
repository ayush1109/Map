package com.map.ayush1109.map;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float last_x, last_y, last_z;

    long lastUpdate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //setup the accelerometer
        sensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener( (SensorEventListener) this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long curTime = System.currentTimeMillis();

            if(Math.abs(curTime - lastUpdate) > 2000){
                SimpleDateFormat date = new SimpleDateFormat("dd-MM-yy")   ;
                String currentDateTime = date.format(new Date());

                lastUpdate = curTime;

                if (Math.abs(last_x - x) > 10){
                    mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(20.285803, 85.857888))
                    .icon (BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                    .title("Hey you Moved the X-Axis!!" + currentDateTime));
                }

                if (Math.abs(last_y - y) > 10) {
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(20.285803, 85.856788))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                            .title("Hey you Moved the Y-Axis!!" + currentDateTime));
                }

                if (Math.abs(last_z - z) > 10) {
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(20.285803, 85.856988))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                            .title("Hey you Moved the Z-Axis!!" + currentDateTime));
                }

                last_x = x;
                last_y = y;
                last_z = z;


            }

            }
        }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        LatLng Bhubaneswar = new LatLng(20.285803, 85.856888);
        mMap.addMarker(new MarkerOptions().position(Bhubaneswar).title("BhubaneswarCity"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Bhubaneswar));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(20.285803, 85.856888), 14.9f));
    }


}

