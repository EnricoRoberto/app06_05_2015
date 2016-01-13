package com.example.xpmuser.app06_05_2015.UtilityClass;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import com.example.xpmuser.app06_05_2015.R;
import com.example.xpmuser.app06_05_2015.SqliteStore.DBManager;
import com.example.xpmuser.app06_05_2015.SqliteStore.DatabaseStrings;

import static com.example.xpmuser.app06_05_2015.SqliteStore.DatabaseStrings.TBL_NAME;


public class Utility_1 extends Activity {

    public static String serviceRxText=null;

    public static String valore1;
    public static String valore2;
    public static String valore3;
    public static String valore4;
    public static String valore5;
    public static String valore;

    public Cursor customQuery(Context c, String tblName, String fieldId, String fieldHostname,String fieldSrcport, String fieldDstport, String  fieldDate,String fieldSel ){
        final DBManager dbmanager = new DBManager(c);

        String[] col = {fieldId, fieldHostname,fieldSrcport,fieldDstport,  fieldDate};
        String sel = DatabaseStrings.FIELD_ID;
        //  Cursor queryAll(String table, String[] columns,String selection,String[] selectionArgs, String groupBy, String having, String orderBy)
        final Cursor cq = dbmanager.queryAll(tblName, col, sel, null, null, null, null);
        return cq;

    }
    public boolean isFilled (Cursor cq){
       boolean isfilled = false;
         if (cq.getCount() >1) {
          isfilled = true;
      }
      return  isfilled;
    }
    Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mContext = Utility_1.this;
    }





}
