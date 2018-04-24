package com.drugapp;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.drugapp.Configuration.Config;
import com.drugapp.Pojos.RegistrationPojo;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import static android.content.ContentValues.TAG;


public class Signup extends AppCompatActivity {

    private ProgressBar pbbar;

    ImageView imgMap;
    Spinner type;
    EditText name,contact,email,password,etlatlang,etAddress;
    Button button;
    String strType,strname,strcontact,stremail,strpassword,strlatlang,Strfcmkey,address;

    Geocoder geocoder;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    Firebase ref;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        Firebase.setAndroidContext(getApplicationContext());

        init();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strname=name.getText().toString().trim();
                strcontact=contact.getText().toString().trim();
                stremail=email.getText().toString().trim();
                strpassword=password.getText().toString().trim();
                strlatlang=etlatlang.getText().toString().trim();
                strType=type.getSelectedItem().toString();
                address=etAddress.getText().toString();

                if (validationdata()) {
                    if (ApplicationContant.haveNetworkConnection(Signup.this)) {
                        progressDialog.show();
                        mAuth.createUserWithEmailAndPassword(stremail, strpassword)
                                .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        //   Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                                        // If sign in fails, display a message to the user. If sign in succeeds
                                        // the auth state listener will be notified and logic to handle the
                                        // signed in user can be handled in the listener.
                                        if (!task.isSuccessful()) {
                                            progressDialog.dismiss();
                                            String err=task.getException().getMessage();
                                            if(err.equalsIgnoreCase("The email address is already in use by another account.")){
                                                err="Email Id Already Registered";
                                                email.setError(err);

                                            }
                                            ApplicationContant.getAlertDialog(Signup.this,err+"").show();
                                        } else {

                                            registerUserData();
                                        }

                                    }
                                });
                    } else {

                        AlertDialog.Builder builder = ApplicationContant.getAlertDialog(Signup.this, "Please Check Internet Connection....");
                        builder.show();
                    }
                } else {

                     AlertDialog.Builder builder = ApplicationContant.getAlertDialog(Signup.this, "Pleaase Fill All Required Feilds");
                     builder.show();
                }

            }
        });


    }

    private boolean validationdata()
    {

        boolean isValid=true;

        if(etlatlang.getText().toString().equalsIgnoreCase(""))
        {
            isValid=false;
        }else
        if(name.getText().toString().equalsIgnoreCase(""))
        {
            isValid=false;
        }else
        if(contact.getText().toString().equalsIgnoreCase(""))
        {
            isValid=false;
        }else
        if(email.getText().toString().equalsIgnoreCase(""))
        {
            isValid=false;
        }else
        if(password.getText().toString().equalsIgnoreCase(""))
        {
            isValid=false;
        }else

        if(etlatlang.getText().toString().equalsIgnoreCase(""))
        {
            isValid=false;
        }else

        if(etAddress.getText().toString().equalsIgnoreCase(""))
        {
            isValid=false;
        }


        return true;
    }

    private void init() {
        type=(Spinner)findViewById(R.id.type);
        name=(EditText)findViewById(R.id.name);
        contact=(EditText)findViewById(R.id.contact);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        etlatlang=(EditText)findViewById(R.id.latlang);
        button=(Button)findViewById(R.id.button);
        progressDialog = new ProgressDialog(Signup.this);
        imgMap=(ImageView)findViewById(R.id.imgMap);
        etAddress=(EditText)findViewById(R.id.etAddress);

        imgMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(Signup.this,MapsActivity.class);
                startActivityForResult(intent,2);
            }
        });

        ref = new Firebase(Config.FirebaseURl);


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                   // Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    //Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

    }

    private void registerUserData()
    {
        {
            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null) {
                // User is signed in


                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MYFCM", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Strfcmkey = sharedPreferences.getString("key", "NoKey");

                RegistrationPojo rpojo = new RegistrationPojo(strType, strname, strcontact, stremail, strpassword,strlatlang,Strfcmkey, user.getUid(),address);
                ref.child("RegistredUsers/" + mAuth.getCurrentUser().getUid() + "/UserDetails").setValue(rpojo);

                ref.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                    @Override
                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {

                        progressDialog.dismiss();
                        try {
                            ApplicationContant.getAlertDialog(Signup.this, "Registration Successfull").show();
                           // activity.replaceFragment(1);
                            startActivity(new Intent(getApplicationContext(),Login.class));
                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        progressDialog.dismiss();
                    }
                });

            } else {
                // User is signed out
                progressDialog.dismiss();
                Log.d(TAG, "onAuthStateChanged:signed_out");
                ///Toast.makeText(getActivity(),"ggdgdgd",Toast.LENGTH_SHORT).show();

            }
        }
    }



    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if(requestCode==2)
            {
                if(resultCode==RESULT_OK)
                {
        etlatlang.setText(data.getStringExtra("location"));


                    String latLng=data.getStringExtra("location");
                    StringTokenizer tokenizer=new StringTokenizer(latLng,",");

                    Double lati= Double.valueOf(tokenizer.nextToken());
                    Double longi=Double.valueOf(tokenizer.nextToken());
                    getMyLocationAddress(lati,longi);
                   // LatlgnToAddress(lati,longi);
                }
            }
    }


    public void getMyLocationAddress(Double lati,Double longi) {

        Geocoder geocoder= new Geocoder(this, Locale.ENGLISH);

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
            Toast.makeText(getApplicationContext(),"Could not get address..!", Toast.LENGTH_LONG).show();
        }
    }




    public void LatlgnToAddress(Double lati,Double longi) {
        try {
            geocoder = new Geocoder(Signup.this, Locale.ENGLISH);

          List<Address> addressList=new ArrayList<>();
            addressList = geocoder.getFromLocation(lati, longi, 1);
            StringBuilder str = new StringBuilder();
            if (geocoder.isPresent()) {
                Toast.makeText(getApplicationContext(),
                        "geocoder present", Toast.LENGTH_SHORT).show();
                Address returnAddress = addressList.get(0);

                String localityString = returnAddress.getLocality();
                String city = returnAddress.getCountryName();
                String region_code = returnAddress.getAddressLine(0);
                String zipcode = returnAddress.getPostalCode();

                str.append(localityString + " ");
                str.append(city + " " + region_code + " ");
                str.append(zipcode + " ");

               // et_location.setText("Latitude:" + lat + "\n" + "Longitude:" + lon + "Your Current Address:" + str);
                Toast.makeText(getApplicationContext(), str,
                        Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getApplicationContext(),
                        "geocoder not present", Toast.LENGTH_SHORT).show();
            }


        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }
    }


}