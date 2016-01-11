package com.example.xpmuser.app06_05_2015.SqliteStore;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {
   
	public static final String DBNAME="STORICO";
 
    public DBhelper(Context context) {
        super(context, DBNAME, null, 1);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db)
        {
        String q="CREATE TABLE "+DatabaseStrings.TBL_NAME+
                " ( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                DatabaseStrings.FIELD_HOSTNAME+" TEXT," +
                DatabaseStrings.FIELD_SRCPORT+" TEXT," +
                DatabaseStrings.FIELD_DSTPORT+" TEXT," +
                DatabaseStrings.FIELD_DATE+" TEXT)";
        db.execSQL(q);

        String qrx="CREATE TABLE "+DatabaseStrings.TBL_NAME_RX+
                " ( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                DatabaseStrings.FIELD_HOSTNAME+" TEXT," +
                DatabaseStrings.FIELD_SRCPORT+" TEXT," +
                DatabaseStrings.FIELD_DSTPORT+" TEXT," +
                DatabaseStrings.FIELD_DATE+" TEXT)";
        db.execSQL(qrx);

        }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {  }
 
}
