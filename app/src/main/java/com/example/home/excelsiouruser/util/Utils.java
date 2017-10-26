package com.example.home.excelsiouruser.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.home.excelsiouruser.infrastructure.MyApplication;

/**
 * Created by Home on 26-10-2017.
 */

public class Utils {
    public static boolean isNetworkAvailable(){
        ConnectivityManager manager = (ConnectivityManager) MyApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable()
                && networkInfo.isConnected();
    }
}

