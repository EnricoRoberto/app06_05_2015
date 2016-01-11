package com.example.xpmuser.app06_05_2015.SqliteStore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.provider.ContactsContract;

import java.io.IOException;

import static com.example.xpmuser.app06_05_2015.SqliteStore.DatabaseStrings.TBL_NAME;

public class DBManager {
	
	private DBhelper dbhelper;
	// variabili varie

    public DBManager(Context ctx)
    {
        dbhelper=new DBhelper(ctx);
    }

    public void savetx(String host, String srcport, String dstport, String data )
    {
        SQLiteDatabase db=dbhelper.getWritableDatabase();
 
        ContentValues cv=new ContentValues();
        cv.put(DatabaseStrings.FIELD_HOSTNAME, host);
        cv.put(DatabaseStrings.FIELD_SRCPORT, srcport);
        cv.put(DatabaseStrings.FIELD_DSTPORT, dstport);
        cv.put(DatabaseStrings.FIELD_DATE, data);
        try
        {
            db.insert(DatabaseStrings.TBL_NAME, null,cv);
        }
        catch (SQLiteException sqle)
        {
            // Gestione delle eccezioni
        	System.out.println("Eccezione metodo 'Save' di 'DBManager'");
         	
        }
    }

    public void saverx(String host, String srcport, String dstport, String data )
    {
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(DatabaseStrings.FIELD_HOSTNAME, host);
        cv.put(DatabaseStrings.FIELD_SRCPORT, srcport);
        cv.put(DatabaseStrings.FIELD_DSTPORT, dstport);
        cv.put(DatabaseStrings.FIELD_DATE, data);
        try
        {
            db.insert(DatabaseStrings.TBL_NAME_RX, null,cv);

        }
        catch (SQLiteException sqle)
        {
            // Gestione delle eccezioni
            System.out.println("Eccezione metodo 'Save' di 'DBManager'");

        }
    }

    public int purge()
    {
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        // funziona cosi': cancella tutte le righe che hanno nel campo "Hostname" un valore vuoto ""
        String whereClause = "Hostname = ?";
        String[] whereArgs = {""};
        int val = 0;
        try
        {
            val = db.delete(DatabaseStrings.TBL_NAME, whereClause,whereArgs);
            System.out.println("Cancellazione eseguita di: " + val + "record");
        }
        catch (SQLiteException sqle)
        {
            System.out.println("Cancellazione NON eseguita causa: " + sqle );
        }
        return val;
    }

    public int delete()
    {
        // cancella tutto
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        String whereClause = null;
        String[] whereArgs = null;
        int val = 0;
        try
        {
            val = db.delete(DatabaseStrings.TBL_NAME, whereClause,whereArgs);
            System.out.println("Cancellazione eseguita di: "+ val + "record");
        }
        catch (SQLiteException sqle)
        {
            System.out.println("Cancellazione NON eseguita causa: " + sqle );
        }
        return val;
    }

    public Cursor query()
    {
        Cursor crs=null;
        try
        {
            SQLiteDatabase db=dbhelper.getReadableDatabase();
            crs=db.query(DatabaseStrings.TBL_NAME, null, null, null, null, null, null, null);
           // crs=db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
            System.out.println("query di tutto fatta = " + DatabaseStrings.TBL_NAME);

        }
        catch(SQLiteException sqle)
        {
            return null;
        }
        return crs;
    }

    public Cursor queryAll(String table, String[] columns,String selection,String[] selectionArgs, String groupBy, String having, String orderBy)
    {
        Cursor crs=null;
        try
        {
            SQLiteDatabase db=dbhelper.getReadableDatabase();
            crs=db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
            System.out.println("query personalizzata fatta = " + DatabaseStrings.TBL_NAME + table+ columns+ selection+ selectionArgs+ groupBy+ having+ orderBy );

        }
        catch(SQLiteException sqle)
        {
            return null;
        }

        return crs;

    }

    public boolean dbExist(String tblName){
        boolean exist=false;



        return exist;
    }

    public void updatetx(String host, String srcport, String dstport, String data )
    {
        SQLiteDatabase db=dbhelper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(DatabaseStrings.FIELD_HOSTNAME, host);
        cv.put(DatabaseStrings.FIELD_SRCPORT, srcport);
        cv.put(DatabaseStrings.FIELD_DSTPORT, dstport);
        cv.put(DatabaseStrings.FIELD_DATE, data);
        try
        {
             db.update(DatabaseStrings.TBL_NAME,cv,DatabaseStrings.FIELD_ID,null);

        }
        catch (SQLiteException sqle)
        {
            // Gestione delle eccezioni
            System.out.println("Eccezione metodo 'Save' di 'DBManager'");

        }
    }








}