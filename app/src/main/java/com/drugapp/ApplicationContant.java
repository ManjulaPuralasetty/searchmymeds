package com.drugapp;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ApplicationContant {

    //Login User Details
    public static String myUid = ""; //login and spalash
    public static String myName = "User";
    public static String myEmail;
    public static String myNumber;


    public static final String NOTIFICATIONTABLE="NOTIFICATIONTABLE";




    //who asked question his fcmlic static String fornotificationId;
    public static String fornotificationId;


    //gettng user fcm key from data base
   public static String myfcm="NoKey";

    //for getting posted question userid
    public static String QueUserId;
    public static String attachment;
    public static String latlang;


    public static String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Calendar cal = Calendar.getInstance();

        return dateFormat.format(cal.getTime());
    }

    //on click particular question for answer
    public static String quid;
    public static String question;


    public static AlertDialog.Builder getAlertDialog(final Activity activity, String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getResources().getString(R.string.app_name));
        builder.setMessage(msg);
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        };


        builder.setPositiveButton("OK", listener);
        return builder;

    }



    public static boolean haveNetworkConnection(Activity context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++){
                    if (info[i].getState()==NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static int getResponseCode() throws MalformedURLException, IOException {
        URL u = new URL("www.google.com");
        HttpURLConnection huc = (HttpURLConnection) u.openConnection();
        huc.setRequestMethod("GET");
        huc.connect();

        return huc.getResponseCode();

    }




    public static void notificationDelete(Context context)
    {
        SharedPreferences.Editor editor;
        SharedPreferences sharedPreferences;

        sharedPreferences=context.getSharedPreferences(NOTIFICATIONTABLE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();


    }

}
