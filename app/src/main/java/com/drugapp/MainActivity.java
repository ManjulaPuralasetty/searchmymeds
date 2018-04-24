package com.drugapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    Handler handler =new Handler();
    DBController db=new DBController(this);
    HashMap<String, String> queryValues;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this,SearchMyMedicine.class);
                startActivity(i);
                finish();
            }
        }, 3*1000);

    }
}
