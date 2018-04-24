package com.drugapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class SearchResultPage extends AppCompatActivity {
TextView tv_name1,tv_distance1,tv_place1;
    TextView tv_name2,tv_distance2,tv_place2;
    TextView tv_name3,tv_distance3,tv_place3;
    TextView tv_name4,tv_distance4,tv_place4;
    TextView tv_name5,tv_distance5,tv_place5;
    Button btn_go1,btn_go2,btn_go3,btn_go4,btn_go5;
    ArrayList<Pharmacy> phlist;
    DBController db=new DBController(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_page);

        tv_name1= (TextView) findViewById(R.id.tv_name1);
        tv_name2= (TextView) findViewById(R.id.tv_name2);
        tv_name3= (TextView) findViewById(R.id.tv_name3);
        tv_name4= (TextView) findViewById(R.id.tv_name4);
        tv_name5= (TextView) findViewById(R.id.tv_name5);

        tv_distance1= (TextView) findViewById(R.id.tv_distance1);
        tv_distance2= (TextView) findViewById(R.id.tv_distance2);
        tv_distance3= (TextView) findViewById(R.id.tv_distance3);
        tv_distance4= (TextView) findViewById(R.id.tv_distance4);
        tv_distance5= (TextView) findViewById(R.id.tv_distance5);

        tv_place1= (TextView) findViewById(R.id.tv_place1);
        tv_place2= (TextView) findViewById(R.id.tv_place2);
        tv_place3= (TextView) findViewById(R.id.tv_place3);
        tv_place4= (TextView) findViewById(R.id.tv_place4);
        tv_place5= (TextView) findViewById(R.id.tv_place5);

        btn_go1= (Button) findViewById(R.id.btn_go1);
        btn_go2= (Button) findViewById(R.id.btn_go2);
        btn_go3= (Button) findViewById(R.id.btn_go3);
        btn_go4= (Button) findViewById(R.id.btn_go4);
        btn_go5= (Button) findViewById(R.id.btn_go5);
        btn_go1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double latitude = 17.407721;
                double longitude = 78.393018;
                String label = "I'm Here!";
                String uriBegin = "geo:" + latitude + "," + longitude;
                String query = latitude + "," + longitude + "(" + label + ")";
                String encodedQuery = Uri.encode(query);
                String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                Uri uri = Uri.parse(uriString);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(mapIntent);
                finish();
            }
        });
        btn_go2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double latitude = 17.4114;
                double longitude = 78.3952;
                String label = "I'm Here!";
                String uriBegin = "geo:" + latitude + "," + longitude;
                String query = latitude + "," + longitude + "(" + label + ")";
                String encodedQuery = Uri.encode(query);
                String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                Uri uri = Uri.parse(uriString);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(mapIntent);
                finish();
            }
        });
        btn_go3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double latitude = 17.407767;
                double longitude = 78.392875;
                String label = "I'm Here!";
                String uriBegin = "geo:" + latitude + "," + longitude;
                String query = latitude + "," + longitude + "(" + label + ")";
                String encodedQuery = Uri.encode(query);
                String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                Uri uri = Uri.parse(uriString);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(mapIntent);
                finish();
            }
        });
        btn_go4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double latitude = 17.406702;
                double longitude = 78.392295;
                String label = "I'm Here!";
                String uriBegin = "geo:" + latitude + "," + longitude;
                String query = latitude + "," + longitude + "(" + label + ")";
                String encodedQuery = Uri.encode(query);
                String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                Uri uri = Uri.parse(uriString);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(mapIntent);
                finish();
            }
        });
        btn_go5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double latitude = 17.408676;
                double longitude = 78.394261;
                String label = "I'm Here!";
                String uriBegin = "geo:" + latitude + "," + longitude;
                String query = latitude + "," + longitude + "(" + label + ")";
                String encodedQuery = Uri.encode(query);
                String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                Uri uri = Uri.parse(uriString);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(mapIntent);
                finish();
            }
        });

      phlist=db.getPharmacies();
        tv_name1.setText(phlist.get(0).getName());
        tv_name2.setText(phlist.get(1).getName());
        tv_name3.setText(phlist.get(2).getName());
        tv_name4.setText(phlist.get(3).getName());
        tv_name5.setText(phlist.get(4).getName());

        tv_place1.setText(phlist.get(0).getLocation());
        tv_place2.setText(phlist.get(1).getLocation());
        tv_place3.setText(phlist.get(2).getLocation());
        tv_place4.setText(phlist.get(3).getLocation());
        tv_place5.setText(phlist.get(4).getLocation());

        tv_distance1.setText("6km");
        tv_distance2.setText("3km");
        tv_distance3.setText("2km");
        tv_distance4.setText("7km");
        tv_distance5.setText("1km");



    }
}
