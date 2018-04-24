package com.drugapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MedicineNumberPage extends AppCompatActivity {

    EditText et_number;
    Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_number_page);
        et_number= (EditText) findViewById(R.id.et_number);
        btn_submit= (Button) findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_number.getText().toString().equalsIgnoreCase("")){
                    et_number.setError("Please entyer the number of medicines");
                }
                else{
                   Intent i=new Intent(MedicineNumberPage.this,SecondMedicineActivity.class);
                    i.putExtra("NUMBER",et_number.getText().toString());
                    startActivity(i);
                }
            }
        });
    }
}
