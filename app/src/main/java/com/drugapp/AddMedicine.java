package com.drugapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.drugapp.Pojos.AddMedPojo;
import com.drugapp.Pojos.TrackMyMe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

/**
 * Created by Shiva on 14-07-2017.
 */

public class AddMedicine extends Activity {

    EditText ed_MaterialName,ed_Molecule,ed_Batch,ed_expDate,ed_Quantity,ed_name,ed_mrp;
    Button btn_update;
    String matarialName,molecule,batch,expDate,quantity,name,mrp;
    FirebaseDatabase db;
    DatabaseReference dbref, getDbref2;
    String key;
    ProgressDialog pd;
    AddMedPojo AddMedicinePojo;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_medicine);
        init();
        pd=new ProgressDialog(AddMedicine.this);
        db = FirebaseDatabase.getInstance();
        dbref = db.getReference("DrugAppData");
        getDbref2 = db.getReference("RegistredUsers");

        btn_update=(Button)findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validationData())
                {
                addMed(AddMedicinePojo);

                }else {
                    Toast.makeText(getApplicationContext(),"Please Fill All Fields",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void init() {

        ed_MaterialName=(EditText)findViewById(R.id.ed_MaterialName);
        ed_Molecule=(EditText)findViewById(R.id.ed_Molecule);
        ed_Batch=(EditText)findViewById(R.id.ed_Batch);
        ed_expDate=(EditText)findViewById(R.id.ed_expDate);
        ed_Quantity=(EditText)findViewById(R.id.ed_Quantity);
        ed_name=(EditText)findViewById(R.id.ed_name);
        ed_mrp=(EditText)findViewById(R.id.ed_mrp);
        btn_update=(Button)findViewById(R.id.btn_update);

        ed_name.setEnabled(false);
        ed_name.setText(ApplicationContant.myName);

        ed_expDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                {
                    showDatePicker(ed_expDate);
                }
            }
        });

        ed_expDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(ed_expDate);
            }
        });

    }



    private void showDatePicker(final EditText editTextId)
    {

        Calendar calendar= Calendar.getInstance();

        DatePickerDialog dpd=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int mnth, int day)
            {
                int orgnalMnth=mnth+1;
                editTextId.setText(day+"/"+orgnalMnth+"/"+year);

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        dpd.show();
    }


    private void addMed(AddMedPojo rpojo) {
        String key2 = dbref.push().getKey();
        key = dbref.push().getKey();
        dbref.child(key + "/" + "AllMed").setValue(rpojo);
        TrackMyMe qt = new TrackMyMe(key);
        //qt.setKeyQuestion(key);
        if(!ApplicationContant.myUid.equalsIgnoreCase("")) {
            getDbref2.child(ApplicationContant.myUid + "/" + "MyMedTrack").child(key2).setValue(key);
        }

        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if (pd.isShowing()) {
                    pd.dismiss();
                }


                /*txtattach.setText("");
                edtQuestion.setText("");
                spnType.setSelection(0);*/
                ApplicationContant.getAlertDialog(AddMedicine.this, "Thank You..Medicine Added").show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                if (pd.isShowing()) {
                    pd.dismiss();
                }

            }
        });


    }

    public boolean validationData()
    {

        matarialName=ed_MaterialName.getText().toString().trim();
        molecule=ed_Molecule.getText().toString().trim();
                batch=ed_Batch.getText().toString().trim();
        expDate=ed_expDate.getText().toString().trim();
        quantity=ed_Quantity.getText().toString().trim();
        name=ed_name.getText().toString().trim();
        mrp=ed_mrp.getText().toString().trim();

        if(TextUtils.isEmpty(matarialName)||TextUtils.isEmpty(molecule)||TextUtils.isEmpty(batch)||
                TextUtils.isEmpty(expDate) || TextUtils.isEmpty(quantity)|| TextUtils.isEmpty(name)||
                TextUtils.isEmpty(mrp))

            return false;
        else

            AddMedicinePojo=new AddMedPojo(matarialName,molecule,batch,expDate,quantity,name,mrp,ApplicationContant.myUid,ApplicationContant.myfcm,ApplicationContant.latlang);
            return true;


    }
}
