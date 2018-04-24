package com.drugapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;



public class ConnectFragment extends Fragment {
    Button btn_search;
    EditText et_medicine;
    String str_med = "";

    public ConnectFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_connect, container, false);
        btn_search = (Button) rootView.findViewById(R.id.btn_search);
        et_medicine = (EditText) rootView.findViewById(R.id.et_medicine);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_med = et_medicine.getText().toString();
                Intent i =  new Intent(getActivity(),UpdateQuant.class);
                i.putExtra("MED",str_med);
                startActivity(i);

            }
        });


        return rootView;
    }

}
