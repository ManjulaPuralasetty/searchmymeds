package com.drugapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
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
 * Created by Shiva on 16-07-2017.
 */

public class Search_Activity extends AppCompatActivity
{
    RecyclerView recycleShops;
    TextView txtSearch;
    EditText etMedicineName;
    FirebaseDatabase db;
    DatabaseReference dbref;
    ArrayList<String> myMedTrack;

    int num = 0;
    String qkey;

    ArrayList<PoJoAll> al;


    //ArrayList<PoJoAll> testSearch;
    ProgressDialog progressDialog;
    PharmaciesAdapter adapter;

    EditText search;
    ArrayList<PoJoAll> testSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        progressDialog=new ProgressDialog(Search_Activity.this);
        txtSearch=(TextView)findViewById(R.id.txtSearch);
        etMedicineName=(EditText)findViewById(R.id.etMedicineName);
        recycleShops=(RecyclerView)findViewById(R.id.recycleShops);

        search=(EditText)findViewById(R.id.etMedicineName);
        testSearch=new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(Search_Activity.this);
        recycleShops.setLayoutManager(layoutManager);
        db = FirebaseDatabase.getInstance();
        progressDialog.show();
        progressDialog.setCancelable(false);

        dbref = db.getReference("DrugAppData");

       loadData();

        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                //  Toast.makeText(getActivity(),cs+"",Toast.LENGTH_SHORT).show();
                AlterAdapter();

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
    }



   public void loadData()
   {

       dbref.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {

               if (progressDialog.isShowing()) {
                   progressDialog.dismiss();
               }
               if(dataSnapshot.getChildrenCount()>0){
                   al = new ArrayList<>();
                   String keyuuid;
                   for (DataSnapshot dataSnapshotObj : dataSnapshot.getChildren()) {

                       keyuuid = dataSnapshotObj.getKey();

                       for (DataSnapshot ds3 : dataSnapshotObj.getChildren()) {

                           if (ds3.getKey().equalsIgnoreCase("AllMed")) {
                               PoJoAll poJoAll = new PoJoAll();
                               poJoAll.setKey(ds3.getKey());
                               poJoAll.setQuid(keyuuid);
                               poJoAll.setAlpojo(ds3.getValue(AddMedPojo.class));

                               al.add(poJoAll);

                           }

                           if (ds3.getKey().equalsIgnoreCase("OtAnswer")) {


                           }
                           keyuuid = "";
                       }


                       PharmaciesAdapter adapter=new PharmaciesAdapter(Search_Activity.this,al);

                    //   PharmaciesAdapter adapter = new PharmaciesAdapter(Search_Activity.this,al);

                       recycleShops.setAdapter(adapter);
                       adapter.notifyDataSetChanged();


                    //  Toast.makeText(getApplicationContext(),al.get(0).getAlpojo().getName(),Toast.LENGTH_LONG).show();
                   }

               }else {
                   ApplicationContant.getAlertDialog(Search_Activity.this,"No shops Found").show();
               }
//
           }


           @Override
           public void onCancelled(DatabaseError databaseError) {
               if(progressDialog.isShowing())
               {
                   progressDialog.dismiss();
               }
               //Toast.makeText(getActivity(),"Cancel",Toast.LENGTH_SHORT).show();
           }
       });


   }
    public void AlterAdapter() {
        if (search.getText().toString().isEmpty()) {
            testSearch.clear();
            adapter=new PharmaciesAdapter(Search_Activity.this,al);
            recycleShops.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        else {
            testSearch.clear();
            for (int i = 0; i < al.size(); i++) {
                if (al.get(i).getAlpojo().getMatarialName().toLowerCase().startsWith(search.getText().toString().trim().toLowerCase())) {
                    testSearch.add(al.get(i));
                    adapter=new PharmaciesAdapter(Search_Activity.this,testSearch);
                    recycleShops.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }


            }
            if(testSearch.size()==0)
            {
                adapter=new PharmaciesAdapter(Search_Activity.this,testSearch);
                recycleShops.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }

}
