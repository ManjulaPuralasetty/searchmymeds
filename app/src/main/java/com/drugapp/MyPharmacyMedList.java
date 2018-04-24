package com.drugapp;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.drugapp.Pojos.AddMedPojo;
import com.drugapp.Pojos.PoJoAll;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Shiva on 15-07-2017.
 */

public class MyPharmacyMedList extends AppCompatActivity {

    FirebaseDatabase db;
    DatabaseReference dbref, getDbref2;
    ArrayList<String> myMedTrack;
    ArrayList<PoJoAll> poJoAllsData;
    int num = 0;
    RecyclerView recycleMedicines;
    String qkey;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypharmacymedlist);
        recycleMedicines = (RecyclerView) findViewById(R.id.recycleMedicines);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Medicines list");
        setSupportActionBar(toolbar);




        db = FirebaseDatabase.getInstance();
        myMedTrack = new ArrayList<>();
        poJoAllsData = new ArrayList<>();
        dbref = db.getReference("RegistredUsers/" + ApplicationContant.myUid + "/MyMedTrack");
        getData();


    }

    public void getData() {
        poJoAllsData.clear();
        num = 0;
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyPharmacyMedList.this);
        recycleMedicines.setLayoutManager(layoutManager);

        // layoutManager.setReverseLayout(true);
    //    layoutManager.setStackFromEnd(true);

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myMedTrack.clear();
                num = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    myMedTrack.add(ds.getValue().toString());

                 //   Toast.makeText(getApplicationContext(),ds.getValue().toString(),Toast.LENGTH_LONG).show();
                }

            //    Toast.makeText(getApplicationContext(), myMedTrack.size() + "", Toast.LENGTH_SHORT).show();


                if (myMedTrack.size() != 0) {
                    for (int i = 0; i < myMedTrack.size(); i++) {
                        getDbref2 = db.getReference("DrugAppData/" + myMedTrack.get(i));
                        qkey = myMedTrack.get(i);
                        //Toast.makeText(getActivity(),qkey+"------FIRST",Toast.LENGTH_SHORT).show();

                        getDbref2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot ds4 : dataSnapshot.getChildren()) {
                                    if (ds4.getKey().equalsIgnoreCase("AllMed")) {

                                        PoJoAll poJoAll = new PoJoAll();
                                        poJoAll.setKey(ds4.getKey());
                                        poJoAll.setQuid(myMedTrack.get(num));
                                        num = (num + 1);
                                        if (myMedTrack.size() == num) {
                                            num = 0;
                                        }
                                        //Toast.makeText(getActivity(),num+"",Toast.LENGTH_SHORT).show();
                                        if (ds4.getValue(AddMedPojo.class) != null)
                                        {
                                        // Toast.makeText(getApplicationContext(),ds4.getValue(AddMedPojo.class).getUid()+"="+ApplicationContant.myUid,Toast.LENGTH_LONG).show();
                                            if (ds4.getValue(AddMedPojo.class).getUid().equalsIgnoreCase(ApplicationContant.myUid)) {
                                                poJoAll.setAlpojo(ds4.getValue(AddMedPojo.class));

                                                poJoAllsData.add(poJoAll);
                                            }
                                        }

                                        //Toast.makeText(getActivity(),poJoAllsData+"",Toast.LENGTH_SHORT).show();
                                    }
                                }
                                //here set Adapter, we will get who login pharmaccy hi particular medicine
                                //  setData();
                                //Toast.makeText(getApplicationContext(), poJoAllsData.size() + ":", Toast.LENGTH_SHORT).show();

                                if (poJoAllsData.size() > 0)
                                {
                                    MedicinesAdapter adapter=new MedicinesAdapter(MyPharmacyMedList.this,poJoAllsData);
                                    recycleMedicines.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();

                                   // Toast.makeText(getApplicationContext(),"Deleted",Toast.LENGTH_SHORT).show();

                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        // qkey="";
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
