package com.drugapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


public class SecondMedicineActivity extends AppCompatActivity {
  String number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_medicine);
        number=getIntent().getStringExtra("NUMBER");
        LinearLayout layout= (LinearLayout) findViewById(R.id.layout_second);
        int num=Integer.parseInt(number);
        layout.setPadding(10,10,10,10);
        for(int i=1;i<num+1;i++){
            TextView tv=new TextView(this);
            tv.setText("Medicine"+"-"+i);
            tv.setTextColor(Color.BLACK);

            layout.addView(tv);
            LinearLayout.LayoutParams etLayoutParams=new LinearLayout.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT,
                    100
            );
            etLayoutParams.setMargins(5, 5, 0, 5);

            EditText et_med=new EditText(this);
            et_med.setHint("Enter Medicine");
            et_med.setBackgroundResource(R.drawable.border);
            et_med.setLayoutParams(etLayoutParams);
            layout.addView(et_med);
            TextView tv1=new TextView(this);
            tv1.setText("Qty");
            layout.addView(tv1);
            EditText et_qty=new EditText(this);
            et_qty.setHint("Enter qty");
            et_qty.setInputType(InputType.TYPE_CLASS_NUMBER);
            et_qty.setBackgroundResource(R.drawable.border);
            et_qty.setLayoutParams(etLayoutParams);
            View v = new View(this);
            LinearLayout.LayoutParams bLayoutParams=new LinearLayout.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT,
                    5
            );
            bLayoutParams.setMargins(5, 20, 0, 5);
            v.setLayoutParams(bLayoutParams);
            v.setBackgroundColor(Color.parseColor("#B3B3B3"));




            layout.addView(et_qty);
            layout.addView(v);
           // layout.addView(layout1);
        }

        Button b=new Button(this);
        b.setText("Search");
        LinearLayout.LayoutParams bLayoutParams=new LinearLayout.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                50
        );
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SecondMedicineActivity.this,SearchResultPage.class);
                startActivity(i);
            }
        });

        bLayoutParams.setMargins(5, 20, 0, 5);
        layout.addView(b);


    }
}
