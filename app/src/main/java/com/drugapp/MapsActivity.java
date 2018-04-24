package com.drugapp;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.xml.parsers.SAXParser;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private String provider;

    public static String latLng="";

    SupportMapFragment mapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        int mapPermsn=ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if(mapPermsn==PackageManager.PERMISSION_GRANTED)
        {
            mapFragment.getMapAsync(this);
        }else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }





    }






    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(MapsActivity.this);

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
*/

        Criteria criteria = new Criteria();
     ///   criteria.setVerticalAccuracy(Criteria.ACCURACY_FINE);
        final LocationManager lManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = lManager.getBestProvider(criteria, true);


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lManager.requestLocationUpdates(provider, 2000, 0, new LocationListener() {



            @Override
            public void onLocationChanged(Location location)
            {
                LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
                MarkerOptions options=new MarkerOptions();
                options.position(latLng);
                mMap.addMarker(options);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,18));
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                lManager.removeUpdates(this);
                 MapsActivity.latLng=latLng.latitude+","+latLng.longitude;

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle)
            {

            }

            @Override
            public void onProviderEnabled(String s)
            {

            }

            @Override
            public void onProviderDisabled(String s)
            {

            }
        });
    }

    @Override
    public void onMapClick(LatLng latLng)
    {

        mMap.clear();
        MarkerOptions options=new MarkerOptions();
        options.position(latLng);
        mMap.addMarker(options);
        MapsActivity.latLng=latLng.latitude+","+latLng.longitude;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length>0)
        {
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                mapFragment.getMapAsync(this);
            }else {
                Toast.makeText(getApplicationContext(),"Please grant permission",Toast.LENGTH_LONG).show();
            }
        }

    }

    public void selectAddress(View v)
    {
        if(!latLng.equalsIgnoreCase(""))
        {
            Intent output = new Intent();
            output.putExtra("location", latLng);
            setResult(RESULT_OK, output);
            finish();
        }else {
            Toast.makeText(getApplicationContext(),"Please pick a place",Toast.LENGTH_LONG).show();
        }
    }
}
