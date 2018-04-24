package com.drugapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


//Class extending FirebaseInstanceIdService
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {

        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        sendRegistrationToServer(refreshedToken);
        //Displaying token on logcat 
        Log.d(TAG, "Refreshed token: " + refreshedToken);


        System.out.println(refreshedToken);
        ApplicationContant.myfcm = refreshedToken;

    }

    private void sendRegistrationToServer(String token) {

        if (!TextUtils.isEmpty(token)) {
            SharedPreferences sharedPreferences = getSharedPreferences("MYFCM", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("key", token);
            editor.commit();

            Log.d("SHRI",token);


        }


    }
}