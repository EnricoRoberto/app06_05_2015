package com.example.xpmuser.app06_05_2015.ResumeFunction;


import android.content.Context;
import android.database.Cursor;
import com.example.xpmuser.app06_05_2015.SqliteStore.DBManager;
import com.example.xpmuser.app06_05_2015.ToastFuntion.ToastReady;
import com.example.xpmuser.app06_05_2015.SqliteStore.DatabaseStrings;

import static com.example.xpmuser.app06_05_2015.SqliteStore.DatabaseStrings.TBL_NAME;

public class ResumeFunction {

    Context context;


    public String[]  resumeScreen(Context ctx) {

        // DB
        final DBManager dbmanager = new DBManager(ctx);
        // istanza di classi
        final ToastReady toast = new ToastReady();
        // sorgente DB, query per ottenere la tabella File: "STORICO", Tabella: "Dati_Trasmissione"
        final Cursor c =dbmanager.query();
        c.moveToFirst();
        c.moveToPosition(c.getColumnIndex(DatabaseStrings.FIELD_HOSTNAME));

        int rownumber=c.getCount();
        String[] str= new String[rownumber];
                toast.upShort(ctx, "numero da contare: " + c.getCount());
                for (int i = 0; i < c.getCount(); i++) {
                    str[i]=c.getString(c.getColumnIndex(DatabaseStrings.FIELD_HOSTNAME));
                    if (i == (c.getCount()-1)) {
                        break;
                    }
                    c.moveToNext();
             }
        c.close();
        return str;
    }

    public String[]  readOrder(Context ctx, String orderBy) {

        // DB
        final DBManager dbmanager = new DBManager(ctx);
        // istanza di classi
        final ToastReady toast = new ToastReady();
        // sorgente DB, query per ottenere la tabella File: "STORICO", Tabella: "Dati_Trasmissione"


        String ordBY= DatabaseStrings.FIELD_HOSTNAME;
        final Cursor c=dbmanager.queryAll(TBL_NAME,null, null, null, null, null, ordBY);
        //final Cursor c =dbmanager.query();

        c.moveToFirst();
        int rownumber=c.getCount();
        String[] str= new String[rownumber];
        toast.upShort(ctx, "numero da contare: " + c.getCount());
        for (int i = 0; i < c.getCount(); i++) {
            //str[i]=c.getString(c.getColumnIndex(DatabaseStrings.FIELD_HOSTNAME));
            str[i]=c.getString(c.getColumnIndex(orderBy));
            if (i == (c.getCount()-1)) {
                break;
            }
            c.moveToNext();
        }
        c.close();
        return str;
    }


}


