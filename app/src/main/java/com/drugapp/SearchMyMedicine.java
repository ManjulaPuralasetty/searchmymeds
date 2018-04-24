package com.drugapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.vistrav.ask.Ask;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class SearchMyMedicine extends Activity implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    static String test;
    double latitude;
    double longitude;
    Geocoder geocoder;
    List<Address> addresses;
    Button search;
    private Context context;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    String lat = "", lon = "";
    EditText et_location,et_qty,et_med;
    double lati, longi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_my_medicine);
        et_location = (EditText) findViewById(R.id.et_location);
        et_qty=(EditText) findViewById(R.id.et_qty);
        et_med=(EditText) findViewById(R.id.et_med);
        search= (Button) findViewById(R.id.btn_search);
        search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_med.getText().toString().equalsIgnoreCase("") ) {

                    et_med.setError("Please enter the medicine name");

                }
                else if(et_qty.getText().toString().equalsIgnoreCase("")){

                    et_qty.setError("Please enter the qty");
                }
                else {


                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    Intent i=new Intent(SearchMyMedicine.this,MedicineNumberPage.class);
                                    startActivity(i);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    Intent intent=new Intent(SearchMyMedicine.this,SearchResultPage.class);
                                    startActivity(intent);
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Do you want to order more medicine?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
            }
        });
        Ask.on(SearchMyMedicine.this)
                .forPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .withRationales("Need Location Permission") //optional
                .go();

        mLocationRequest = LocationRequest.create();
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();


    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, new com.google.android.gms.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }
        });
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            lat = String.valueOf(mLastLocation.getLatitude());
            lon = String.valueOf(mLastLocation.getLongitude());

            lati = mLastLocation.getLatitude();
            longi = mLastLocation.getLongitude();

            Toast.makeText(SearchMyMedicine.this, "Loc is " + lat + "," + lon, Toast.LENGTH_SHORT).show();
            // et_location.setText("Latitude:" + lat + "\n" + "Longitude:" + lon);
            //LatlgnToAddress();
            getMyLocationAddress();

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    public void getMyLocationAddress() {

        Geocoder geocoder= new Geocoder(this, Locale.ENGLISH);

        try {

            //Place your latitude and longitude
            List<Address> addresses = geocoder.getFromLocation(lati,longi, 1);

            if(addresses != null) {

                Address fetchedAddress = addresses.get(0);
                StringBuilder strAddress = new StringBuilder();

                for(int i=0; i<fetchedAddress.getMaxAddressLineIndex(); i++) {
                    strAddress.append(fetchedAddress.getAddressLine(i)).append("\n");
                }

                et_location.setText(strAddress.toString());

            }

            else
                et_location.setText("No location found..!");

        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Could not get address..!", Toast.LENGTH_LONG).show();
        }
    }
    public void LatlgnToAddress() {
        try {
            geocoder = new Geocoder(SearchMyMedicine.this, Locale.ENGLISH);
            addresses = geocoder.getFromLocation(lati, longi, 1);
            StringBuilder str = new StringBuilder();
            if (geocoder.isPresent()) {
                Toast.makeText(getApplicationContext(),
                        "geocoder present", Toast.LENGTH_SHORT).show();
                Address returnAddress = addresses.get(0);

                String localityString = returnAddress.getLocality();
                String city = returnAddress.getCountryName();
                String region_code = returnAddress.getAddressLine(0);
                String zipcode = returnAddress.getPostalCode();

                str.append(localityString + " ");
                str.append(city + " " + region_code + " ");
                str.append(zipcode + " ");

                et_location.setText("Latitude:" + lat + "\n" + "Longitude:" + lon + "Your Current Address:" + str);
                Toast.makeText(getApplicationContext(), str,
                        Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getApplicationContext(),
                        "geocoder not present", Toast.LENGTH_SHORT).show();
            }


        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }
    }
}
