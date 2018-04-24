package com.drugapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Shiva on 16-07-2017.
 */

public class User_Home_Activity extends AppCompatActivity
{

    TextView txtSearch,txtLogout,txtAboutus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_home);
        txtSearch=(TextView)findViewById(R.id.txtSearch);
        txtLogout=(TextView)findViewById(R.id.txtLogout);
        txtAboutus=(TextView)findViewById(R.id.txtAboutus);

        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
            finish();
            }
        });


        txtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(User_Home_Activity.this,Search_Activity.class));
            }
        });

        txtAboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(User_Home_Activity.this,ContactUsActivity.class));

            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
