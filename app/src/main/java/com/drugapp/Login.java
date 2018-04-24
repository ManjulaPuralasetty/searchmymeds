package com.drugapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.drugapp.Pojos.RegistrationPojo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class Login extends Activity {
    DBController controller = new DBController(this);
    EditText edtuserid, edtpass;
    String email,password;
    ProgressBar pbbar;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    FirebaseDatabase db;
    DatabaseReference dbref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        edtuserid = (EditText) findViewById(R.id.edtuserid);// This is the first edittext for entering input userids.

        edtpass = (EditText) findViewById(R.id.edtpass);
        progressDialog=new ProgressDialog(Login.this);
        pbbar = (ProgressBar) findViewById(R.id.pbbar);
        pbbar.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();
        Button login = (Button) findViewById(R.id.btnlogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                {

                    email=edtuserid.getText().toString().trim();
                    password=edtpass.getText().toString().trim();


                    if(validationEmailPassword())
                    {
                        if(ApplicationContant.haveNetworkConnection(Login.this)) {

                            progressDialog.show();
                            mAuth.signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                            // If sign in fails, display a message to the user. If sign in succeeds
                                            // the auth state listener will be notified and logic to handle the
                                            // signed in user can be handled in the listener.
                                            if (!task.isSuccessful()) {
                                                if(progressDialog.isShowing()){
                                                    progressDialog.dismiss();}
                                                // Log.w(TAG, "signInWithEmail", task.getException());
                                                String er=task.getException().getMessage();
                                                if(er.trim().equalsIgnoreCase("There is no user record corresponding to this identifier. The user may have been deleted."))
                                                {
                                                    er="Please provide valid registered Email id";
                                                    edtuserid.setError(er);
                                                }
                                                if(er.trim().equalsIgnoreCase("The password is invalid or the user does not have a password."))
                                                {
                                                    er="Provide valid Password";
                                                    edtpass.setError(er);
                                                }

                                          /*  Toast.makeText(getActivity(),  er+"",
                                                    Toast.LENGTH_SHORT).show();*/
                                                ApplicationContant.getAlertDialog(Login.this,er).show();


                                            } else {

                                                //getDataUser();
                                                edtuserid.setText("");
                                                edtpass.setText("");

                                                //for checking fcm updation key
                                                //checkfcm();


                                                ApplicationContant.myUid = task.getResult().getUser().getUid();

                                                if(ApplicationContant.myUid.equalsIgnoreCase("")){

                                                    ApplicationContant.getAlertDialog(Login.this,"Please try once again").show();

                                                }else {

                                                    /*startActivity(new Intent(Login.this, OpenTextMainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                                    finish();*/

                                                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                                                    getDataUser();
                                                }



                                            }


                                        }
                                    });
                        }
                        else
                        {
                            ApplicationContant.getAlertDialog(Login.this,"Please Check Internet Connection").show();
                        }
                    }


                }





            }
        });


        TextView signup = (TextView) findViewById(R.id.btnsignup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
            }
        });


    }

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    private boolean validationEmailPassword() {

        if(TextUtils.isEmpty(email)||!isValidEmail(email)||TextUtils.isEmpty(password)) {
            if (TextUtils.isEmpty(email)) {
                edtuserid.setError("Provide Email id");

            }
            if (!TextUtils.isEmpty(email)) {
                if(!isValidEmail(email)){
                    edtpass.setError("Provide valid Email id");}

            }
            if (TextUtils.isEmpty(password)) {
                edtpass.setError("Provide Password");

            }
            return false;
        }
        return true;
    }




    public void getDataUser()
    {

        db = FirebaseDatabase.getInstance();
        dbref = db.getReference("RegistredUsers/"+ApplicationContant.myUid+"/UserDetails");

        SharedPreferences sharedPreferences=getApplicationContext()
                .getSharedPreferences("MYFCM", Context.MODE_PRIVATE);
        SharedPreferences.Editor   editor = sharedPreferences.edit();
        String Strfcmkey=sharedPreferences.getString("key","NoKey");


        if(!Strfcmkey.equalsIgnoreCase("NoKey")&&!ApplicationContant.myUid.equals("")){
            Map<String,Object> taskMap = new HashMap<String,Object>();
            taskMap.put("fcmKey", Strfcmkey);
            dbref.updateChildren(taskMap);}









        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();}
                // Toast.makeText(getActivity(),dataSnapshot.getChildrenCount()+"",Toast.LENGTH_SHORT).show();
                RegistrationPojo registerUserPojo=dataSnapshot.getValue(RegistrationPojo.class);
                if(registerUserPojo!=null) {
                    ApplicationContant.myName = registerUserPojo.getName();
                    ApplicationContant.myNumber = registerUserPojo.getContact();
                    ApplicationContant.myEmail = registerUserPojo.getEmail();
                    ApplicationContant.myfcm=registerUserPojo.getFcmKey();
                    ApplicationContant.latlang=registerUserPojo.getLatlang();

                    if(registerUserPojo.getType().equalsIgnoreCase("User"))
                    {
                       Toast.makeText(getApplicationContext(),registerUserPojo.getType(),Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),User_Home_Activity.class));
                    }else {
                        Toast.makeText(getApplicationContext(),registerUserPojo.getType(),Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),PharmacyMenu.class));

                    }
                }
                //optActivity.setText();
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }
}
