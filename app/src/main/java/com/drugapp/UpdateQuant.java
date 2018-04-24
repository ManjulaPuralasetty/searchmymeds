package com.drugapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class UpdateQuant extends AppCompatActivity {
    String str = "";
    Button btn_update;
    TextView tv_name;
    EditText et_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_quant);
        str = getIntent().getStringExtra("MED");
        tv_name = (TextView) findViewById(R.id.tv_name);
        et_update = (EditText) findViewById(R.id.et_update);
        tv_name.append(str);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UpdateQuant.this, "Updated " + str + "Quantity : " + et_update.getText().toString(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
