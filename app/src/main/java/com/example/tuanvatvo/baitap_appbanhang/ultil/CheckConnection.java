package com.example.tuanvatvo.baitap_appbanhang.ultil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class CheckConnection {
    public static boolean isConnected(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null) {
            if (ni.getType() == ConnectivityManager.TYPE_WIFI)
                if (ni.isConnected())
                    return true;
            if (ni.getType() == ConnectivityManager.TYPE_MOBILE)
                if (ni.isConnected())
                    return true;
            if (ni.getType() == ConnectivityManager.TYPE_ETHERNET)
                if (ni.isConnected())
                    return true;
        }
        return false; //none of connections available
    }
    public static void ShowToast_Short(Context context,String thongbao){
        Toast.makeText(context, thongbao, Toast.LENGTH_SHORT).show();
    }
}
