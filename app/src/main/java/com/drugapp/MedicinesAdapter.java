package com.drugapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.drugapp.Pojos.PoJoAll;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Shiva on 16-07-2017.
 */

public class MedicinesAdapter extends RecyclerView.Adapter<MedicinesAdapter.MyViewHolder>
{

    MyPharmacyMedList myPharmacyMedList;
    ArrayList<PoJoAll> poJoAllsData;
    LayoutInflater inflater;
    DatabaseReference dbref, getDbref2;
    FirebaseDatabase db;
    public MedicinesAdapter(MyPharmacyMedList mActivity, ArrayList<PoJoAll> poJoAllsData)
    {
        inflater=LayoutInflater.from(mActivity);
        this.poJoAllsData=poJoAllsData;
        this.myPharmacyMedList=mActivity;
        notifyDataSetChanged();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view=inflater.inflate(R.layout.medicines_adapter,parent,false);
        MyViewHolder viewHolder=new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position)
    {

        holder.txtMedicineName.setText("Medicine Name: "+poJoAllsData.get(position).getAlpojo().getMatarialName());
        holder.txtQuantity.setText("Quantity: "+poJoAllsData.get(position).getAlpojo().getQuantity());
        holder.txtMrp.setText("MRP : ₹ "+poJoAllsData.get(position).getAlpojo().getMrp());

        db = FirebaseDatabase.getInstance();
      // dbref = db.getReference("RegistredUsers/" + ApplicationContant.myUid + "/MyMedTrack");
        getDbref2=db.getReference("DrugAppData");


        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder aDialog=new AlertDialog.Builder(myPharmacyMedList);
                aDialog.setMessage("Are you sure want to delete ?");
                aDialog.setTitle(R.string.app_name);
                aDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Delete
                        //String key=;
                        // dbref.child(poJoAllsData.get(position).getQuid()).removeValue();

                        getDbref2.child(poJoAllsData.get(position).getQuid()).removeValue();
                       //Toast.makeText(myPharmacyMedList.getApplicationContext(),poJoAllsData.get(position).getQuid()+"",Toast.LENGTH_LONG).show();
                       // myPharmacyMedList.getData();



                        //notifyDataSetChanged();

                    }
                });

                aDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                });
                aDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return poJoAllsData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtQuantity,txtMrp,txtMedicineName;

        ImageView imgDelete;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            txtQuantity=(TextView)itemView.findViewById(R.id.txtQuantity);
            txtMrp=(TextView)itemView.findViewById(R.id.txtMrp);
            txtMedicineName=(TextView)itemView.findViewById(R.id.txtMedicineName);
            imgDelete=(ImageView)itemView.findViewById(R.id.imgDelete);
        }
    }

}
