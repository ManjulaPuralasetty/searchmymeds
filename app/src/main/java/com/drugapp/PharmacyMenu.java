package com.drugapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Shiva on 14-07-2017.
 */

public class PharmacyMenu extends Activity {

    TextView btnAddMed,btnView,txtLogout,txtAboutus;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pharmacy_menu);
        btnAddMed=(TextView)findViewById(R.id.btnAddMed);
        btnView=(TextView)findViewById(R.id.btnView);
        txtLogout=(TextView)findViewById(R.id.txtLogout);
        txtAboutus=(TextView)findViewById(R.id.txtAboutus);

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MyPharmacyMedList.class));

            }
        });

        txtAboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PharmacyMenu.this,ContactUsActivity.class));

            }
        });


        btnAddMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),AddMedicine.class));
            }
        });


        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
