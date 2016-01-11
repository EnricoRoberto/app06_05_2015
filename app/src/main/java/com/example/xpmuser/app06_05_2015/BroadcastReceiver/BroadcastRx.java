package com.example.xpmuser.app06_05_2015.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.xpmuser.app06_05_2015.SqliteStore.DBManager;
import com.example.xpmuser.app06_05_2015.ToastFuntion.ToastReady;
import com.example.xpmuser.app06_05_2015.UtilityClass.Utility_1;

public class BroadcastRx extends BroadcastReceiver {

    final ToastReady toast = new ToastReady();

    protected void onCreate(Bundle savedInstanceState) {


    }

    @Override
    public void onReceive(Context context, Intent intent) {
        toast.upLong(context, "broadcast onReceive ha ricevuto:  " + Utility_1.serviceRxText);
        final DBManager dbmanager = new DBManager(context);
        dbmanager.saverx("testo1", "testo2", "testo3", Utility_1.serviceRxText);
        Utility_1.serviceRxText=null;

    }


}

