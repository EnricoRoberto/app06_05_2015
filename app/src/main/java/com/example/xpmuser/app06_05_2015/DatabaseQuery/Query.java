package com.example.xpmuser.app06_05_2015.DatabaseQuery;


import android.content.Context;
import android.database.Cursor;

import com.example.xpmuser.app06_05_2015.SqliteStore.DBManager;
import com.example.xpmuser.app06_05_2015.SqliteStore.DatabaseStrings;

import static com.example.xpmuser.app06_05_2015.SqliteStore.DatabaseStrings.TBL_NAME;

public class Query {

    public Cursor query1(Context c){
    final DBManager dbmanager = new DBManager(c);

    String[] coloumn = {DatabaseStrings.FIELD_ID, DatabaseStrings.FIELD_HOSTNAME};
    String selection = DatabaseStrings.FIELD_ID;
    //  Cursor queryAll(String table, String[] columns,String selection,String[] selectionArgs, String groupBy, String having, String orderBy)

        final Cursor cq=dbmanager.queryAll(TBL_NAME, coloumn, null, null, null, null, null);
        int numberColumn = cq.getColumnCount();
        cq.moveToFirst();

        String str= cq.getString(0);
        System.out.println(str);


    return cq;
    }

}
