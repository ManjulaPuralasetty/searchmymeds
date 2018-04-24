package com.drugapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.drugapp.Configuration.Config;
import com.drugapp.Pojos.AddMedPojo;
import com.drugapp.Pojos.PoJoAll;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONObject;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Shiva on 17-07-2017.
 */

public class PharmaciesAdapter extends RecyclerView.Adapter<PharmaciesAdapter.MyViewHolder>
{

    Activity myPharmacyMedList;
    ArrayList<PoJoAll> poJoAllsData;
    LayoutInflater inflater;

    public PharmaciesAdapter(Activity mActivity, ArrayList<PoJoAll> poJoAllsData)
    {
        inflater=LayoutInflater.from(mActivity);
        this.poJoAllsData=poJoAllsData;
        this.myPharmacyMedList=mActivity;
    }

    @Override
    public PharmaciesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view=inflater.inflate(R.layout.pharmacies_adapter,parent,false);
        PharmaciesAdapter.MyViewHolder viewHolder=new PharmaciesAdapter.MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PharmaciesAdapter.MyViewHolder holder, final int position)
    {

        holder.txtMedicineName.setText("Medicine Name: "+poJoAllsData.get(position).getAlpojo().getMatarialName()+"\n Qty:"+poJoAllsData.get(position).getAlpojo().getQuantity());
        holder.txtShopName.setText("Shop Name: "+poJoAllsData.get(position).getAlpojo().getName());


        holder.txtAddress.setText("MRP : ₹ "+poJoAllsData.get(position));




        String latlng=poJoAllsData.get(position).getAlpojo().getLatlang();

        StringTokenizer tokenizer=new StringTokenizer(latlng,",");
        final Double latt=Double.parseDouble(tokenizer.nextToken());
        final Double longt=Double.parseDouble(tokenizer.nextToken());

        getMyLocationAddress(latt,longt,holder.txtAddress);


                holder.imgMap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        String label = "I'm Here!";
                        String uriBegin = "geo:" + latt + "," + longt;
                        String query = latt + "," + longt + "(" + label + ")";
                        String encodedQuery = Uri.encode(query);
                        String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                        Uri uri = Uri.parse(uriString);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                        myPharmacyMedList.startActivity(mapIntent);

                    }
                });



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {




                Dialog dialog=new Dialog(myPharmacyMedList);
                dialog.setContentView(R.layout.dialog_order);

               // dialog.show();
                TextView txtMedName=(TextView)dialog.findViewById(R.id.txtMedicineName);
                TextView txtConfirm=(TextView)dialog.findViewById(R.id.txtConfirmOrder);
                final Spinner spi=(Spinner)dialog.findViewById(R.id.spinQuantity);


                txtMedName.setText(poJoAllsData.get(position).getAlpojo().getMatarialName());
                ArrayList<String> data=new ArrayList<String>();
                for(int i=0;i<Integer.parseInt(poJoAllsData.get(position).getAlpojo().getQuantity().trim());i++)
                {
                    data.add(i+"");
                }
    spi.setAdapter(new ArrayAdapter<String>(myPharmacyMedList,R.layout.spnnerxml,data));

                txtConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        if(spi.getSelectedItemPosition()!=0)
                        {
                           post(poJoAllsData.get(position).getAlpojo().getFcmid(),ApplicationContant.myName+" requested for "+poJoAllsData.get(position).getAlpojo().getMatarialName()+" Quantity is "+spi.getSelectedItem().toString());
                        }else {
                            Toast.makeText(myPharmacyMedList,"Select Quantity",Toast.LENGTH_LONG).show();
                        }
                    //Send Notification
                    }
                });



                dialog.show();
            }
        });

//

    }

    @Override
    public int getItemCount() {
        return poJoAllsData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtShopName,txtAddress,txtMedicineName;
        ImageView imgMap;


        public MyViewHolder(View itemView)
        {
            super(itemView);
            txtShopName=(TextView)itemView.findViewById(R.id.txtShopName);
            txtAddress=(TextView)itemView.findViewById(R.id.txtAddress);
            txtMedicineName=(TextView)itemView.findViewById(R.id.txtMedicineName);
            imgMap=(ImageView)itemView.findViewById(R.id.imgMap);

        }
    }
    public void post(String fcm,String data) {

        JSONObject jsonObjec = null;
       // String bodydata ="REPLIED FOR YOUR QUESTION";
        try {



            ArrayList<String> al = new ArrayList<>();
            al.add(fcm);
            jsonObjec = new JSONObject();

            JSONArray jsonArray = new JSONArray(al);
            jsonObjec.put("registration_ids", jsonArray);
            JSONObject jsonObjec2 = new JSONObject();
            jsonObjec2.put("body", data);
            jsonObjec2.put("title", ApplicationContant.myName);
            jsonObjec.put("notification", jsonObjec2);

            jsonObjec.put("time_to_live", 172800);
            jsonObjec.put("priority", "HIGH");

            System.out.println("______________________");
            System.out.println(jsonObjec);
            System.out.println("______________________");
        } catch (Exception e) {
          //  Toast.makeText(getActivity(), "Exception", Toast.LENGTH_SHORT).show();
        }






        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObjec.toString());
        Request request = new Request.Builder()
                .addHeader("Content-Type","application/json")
                .addHeader("Authorization", Config.serverKey)
                .url("https://fcm.googleapis.com/fcm/send")
                .post(body)
                .build();

        okhttp3.Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {

            }
        });

    }



    public void getMyLocationAddress(Double lati,Double longi,TextView etAddress) {

        Geocoder geocoder= new Geocoder(myPharmacyMedList, Locale.ENGLISH);

        try {

            //Place your latitude and longitude
            List<Address> addresses = geocoder.getFromLocation(lati,longi, 1);

            if(addresses != null) {

                Address fetchedAddress = addresses.get(0);
                StringBuilder strAddress = new StringBuilder();

                for(int i=0; i<fetchedAddress.getMaxAddressLineIndex(); i++) {
                    strAddress.append(fetchedAddress.getAddressLine(i)).append("\n");
                }
                etAddress.setText(strAddress.toString());

            }

            else
                etAddress.setText("cant found.! Enter manually");

        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(myPharmacyMedList,"Could not get address..!", Toast.LENGTH_LONG).show();
        }
    }




}

