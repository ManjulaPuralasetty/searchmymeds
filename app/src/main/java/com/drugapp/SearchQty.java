package com.drugapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.vistrav.ask.Ask;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class SearchQty extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    TextView tv_loc;
    EditText et_location,et_qty,et_med;

    List<Address> addresses;
Button search;
    private Context context;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    String lat = "", lon = "";
    double lati, longi;
    DBController db=new DBController(this);
    HashMap<String, String> queryValues;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_qty);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedpreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        if(sharedpreferences.getString("db","0").equalsIgnoreCase("0")){

            queryValues=new HashMap<String, String>();
            queryValues.put("name","Lakshmi Medical Stores");
            queryValues.put("location","Shaikpet");
            queryValues.put("latitude","17.407721");
            queryValues.put("longitude","78.393018");
            db.insertPharmacy(queryValues);

            queryValues=new HashMap<String, String>();
            queryValues.put("name","Sri Balaji Medical Stores");
            queryValues.put("location","Shaikpet");
            queryValues.put("latitude","17.4114");
            queryValues.put("longitude","78.3952");
            db.insertPharmacy(queryValues);

            queryValues=new HashMap<String, String>();
            queryValues.put("name","Lakshmi Medical Stores");
            queryValues.put("location","Shaikpet");
            queryValues.put("latitude","17.407767");
            queryValues.put("longitude","78.392875");
            db.insertPharmacy(queryValues);

            queryValues=new HashMap<String, String>();
            queryValues.put("name","Apollo Pharmacy");
            queryValues.put("location","Shaikpett");
            queryValues.put("latitude","17.406702");
            queryValues.put("longitude","78.392295");
            db.insertPharmacy(queryValues);

            queryValues=new HashMap<String, String>();
            queryValues.put("name","People's Pharmacy ");
            queryValues.put("location","Manikonda");
            queryValues.put("latitude","17.408676");
            queryValues.put("longitude","78.394261");
            db.insertPharmacy(queryValues);

            queryValues=new HashMap<String, String>();
            queryValues.put("name","Medis Pharmacy ");
            queryValues.put("location","OU Colony");
            queryValues.put("latitude","17.40818");
            queryValues.put("longitude","78.39389");
            db.insertPharmacy(queryValues);

            queryValues=new HashMap<String, String>();
            queryValues.put("name","Hetero Pharmacy");
            queryValues.put("location","Shaikpet");
            queryValues.put("latitude","17.410926");
            queryValues.put("longitude","778.396619");
            db.insertPharmacy(queryValues);

            queryValues=new HashMap<String, String>();
            queryValues.put("name","Medplus pharmacy");
            queryValues.put("location","Shaikpet");
            queryValues.put("latitude","17.410519");
            queryValues.put("longitude","8.3973017");
            db.insertPharmacy(queryValues);

            queryValues=new HashMap<String, String>();
            queryValues.put("name","LHussain Medical stores");
            queryValues.put("location","Shaikpet");
            queryValues.put("latitude","17.403007");
            queryValues.put("longitude","78.410479");
            db.insertPharmacy(queryValues);

            queryValues=new HashMap<String, String>();
            queryValues.put("name","Mawa Medical stores");
            queryValues.put("location","Tolichowki");
            queryValues.put("latitude","17.39805");
            queryValues.put("longitude","78.4151");
            db.insertPharmacy(queryValues);

            queryValues=new HashMap<String, String>();
            queryValues.put("name"," Adam Medical Stores");
            queryValues.put("location","Tolichowki");
            queryValues.put("latitude","17.403046");
            queryValues.put("longitude","78.41025");
            db.insertPharmacy(queryValues);

            queryValues=new HashMap<String, String>();
            queryValues.put("name","  A M Medical stores");
            queryValues.put("location","Tolichowki");
            queryValues.put("latitude","17.402974");
            queryValues.put("longitude","78.410491");
            db.insertPharmacy(queryValues);

        }


        sharedpreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("db", "1");



        Ask.on(SearchQty.this)
                .forPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                .withRationales("Need Location Permission") //optional
                .go();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mLocationRequest = LocationRequest.create();
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        et_location = (EditText) findViewById(R.id.et_location);
        et_qty=(EditText) findViewById(R.id.et_qty);
        et_med=(EditText) findViewById(R.id.et_med);
        search= (Button) findViewById(R.id.btn_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (et_med.getText().toString().equalsIgnoreCase("") ) {

                    et_med.setError("Please enter the medicine name");

                }
                else if(et_qty.getText().toString().equalsIgnoreCase("")){

                    et_qty.setError("Please enter the qty");
                }
                else {*/


                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    Intent i=new Intent(SearchQty.this,MedicineNumberPage.class);
                                    startActivity(i);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    Intent intent=new Intent(SearchQty.this,SearchResultPage.class);
                                    startActivity(intent);
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(SearchQty.this);
                    builder.setMessage("Do you want to order more medicine?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
            //}
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(SearchQty.this, Login.class);
                        startActivity(i);
                        finish();
                    }
                }).setNegativeButton("No", null).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_qty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       /* if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else*/

        if (id == R.id.nav_share) {
            Intent i = new Intent(SearchQty.this, HistoryActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_send) {
            Intent i = new Intent(SearchQty.this, ContactUs.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

            Toast.makeText(SearchQty.this, "Loc is " + lat + "," + lon, Toast.LENGTH_SHORT).show();
            // tv_address.setText("Latitude:" + lat + "\n" + "Longitude:" + lon);
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

        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);

        try {

            //Place your latitude and longitude
            List<Address> addresses = geocoder.getFromLocation(lati, longi, 1);

            if (addresses != null) {

                Address fetchedAddress = addresses.get(0);
                StringBuilder strAddress = new StringBuilder();

                for (int i = 0; i < fetchedAddress.getMaxAddressLineIndex(); i++) {
                    strAddress.append(fetchedAddress.getAddressLine(i)).append("\n");
                }

                et_location.setText("You are now at: " + strAddress.toString());

            } else
                et_location.setText("No location found..!");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Could not get address..!", Toast.LENGTH_LONG).show();
        }
    }

}