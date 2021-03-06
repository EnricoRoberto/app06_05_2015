package com.example.xpmuser.app06_05_2015.UdpFunction;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.xpmuser.app06_05_2015.BroadcastReceiver.BroadcastRx;
import com.example.xpmuser.app06_05_2015.MainActivity;
import com.example.xpmuser.app06_05_2015.R;
import com.example.xpmuser.app06_05_2015.Services.ServiceRtx;
import com.example.xpmuser.app06_05_2015.SqliteStore.DBManager;
import com.example.xpmuser.app06_05_2015.SqliteStore.DatabaseStrings;
import com.example.xpmuser.app06_05_2015.ToastFuntion.ToastReady;
import com.example.xpmuser.app06_05_2015.UtilityClass.Utility_1;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.xpmuser.app06_05_2015.SqliteStore.DatabaseStrings.TBL_NAME;
import static com.example.xpmuser.app06_05_2015.SqliteStore.DatabaseStrings.TBL_NAME_RX;

public class ServerUdpActivityRx extends Activity{
	public Integer dbPosition= 0;
	public Integer dbPositionrx= 0;
	public String datafromrx=null;
	private IntentFilter filter =new IntentFilter("Ricevi");

	private BroadcastRx receiver =new BroadcastRx();
    private static final int DIALOG_ERROR_ID = 1;
    private static final int DIALOG_CONFIRM_ID = 2;



	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // imposta layout corrente
		setContentView(R.layout.activity_udp_server_rx);

		// istanza di oggetti da layout
        final Button btnReceiveMode=(Button)findViewById(R.id.Button11);
        final Button btnHome=(Button)findViewById(R.id.Button13);
		final Button btnPurge=(Button)findViewById(R.id.Button3);
		final Button btnEraseAll=(Button)findViewById(R.id.Button4);
		final Button btnRxDataRecall=(Button)findViewById(R.id.Button22);
		final Button btnToTx=(Button)findViewById(R.id.Button6);

        final EditText udpSendDataText = (EditText)findViewById(R.id.editText1);
        final EditText hostText = (EditText)findViewById(R.id.editText2);
        final EditText srcPortText = (EditText)findViewById(R.id.editText4);
        final EditText dstPortText = (EditText)findViewById(R.id.editText3);
		final EditText summaryText = (EditText)findViewById(R.id.editText5);
		final TextView textReceived =(TextView)findViewById(R.id.editText1);


      //  final ListView hostList = (ListView) findViewById(R.id.listView);
		final Button btnsearch_plus=(Button)findViewById(R.id.button);
		final Button btnsearch_minus=(Button)findViewById(R.id.button2);
        final CheckBox dt_insert = (CheckBox)findViewById(R.id.checkBox1);

		// settaggi delle caselle di testo
       // srcPortText.setText("2000");
       // dstPortText.setText("2000");
        // istanza di classi
        final ToastReady toast = new ToastReady();
        final SendUdp sendUdp = new SendUdp();
        final ReceiveUdp receiverUdp = new ReceiveUdp();
		final Utility_1 utility = new Utility_1();

      	//Inizializzazione dell�oggetto Date
       	final Date dat = new Date(0);

        // variabili varie
        final Context context = getApplicationContext();


        // DB
        final DBManager dbmanager = new DBManager(getApplicationContext());


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

    /*
    *FINE DELLE INIZIALIZZAZIONE DI OGGETTI E VARIABILI
    ********************************************************
    */

        btnHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent cambioPaginaHome = new Intent(ServerUdpActivityRx.this, MainActivity.class);
                startActivity(cambioPaginaHome);
                setContentView(R.layout.activity_main);
                stopservicerx();

            }
        });

        btnToTx.setOnClickListener(new View.OnClickListener() {
           public void onClick(View arg0) {
               Intent cambioPaginaTx = new Intent(ServerUdpActivityRx.this, ServerUdpActivity.class);
               startActivity(cambioPaginaTx);
               setContentView(R.layout.activity_udp_server_tx);
               stopservicerx();

           }
        });

        btnReceiveMode.setOnClickListener(new View.OnClickListener(){
		@Override
		public void onClick(View arg0) {
				startservicerx();
                String rTxt = null;

				/*int srcPort = Integer.parseInt(srcPortText.getText().toString());
				int dstPort = Integer.parseInt(dstPortText.getText().toString());
                View layoutPrincipale = findViewById(R.id.layout_contenitore);
                layoutPrincipale.setBackgroundResource(R.color.link_text_material_dark);
                // btnReceiveMode.setBackgroundResource(R.color.dim_foreground_material_dark);
				//	rTxt=receiverUdp.runUdpServer(srcPort); /// non si puo' usare con il simulatore
				//	toast.upShort(context, "Received data: " + rTxt);

				//String[] col = {DatabaseStrings.FIELD_ID, DatabaseStrings.FIELD_HOSTNAME, DatabaseStrings.FIELD_DATE, DatabaseStrings.FIELD_SRCPORT, DatabaseStrings.FIELD_DATE};
				String[] col = {DatabaseStrings.FIELD_ID};
				String sel = DatabaseStrings.FIELD_ID;
				final Cursor cq = dbmanager.queryAll(TBL_NAME, col, sel, null, null, null, null);
				cq.moveToFirst();
				listupdate(dbPositionrx);*/

        }
		
        });
	//

		btnsearch_plus.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String[] col = {DatabaseStrings.FIELD_ID, DatabaseStrings.FIELD_HOSTNAME, DatabaseStrings.FIELD_SRCPORT, DatabaseStrings.FIELD_DSTPORT, DatabaseStrings.FIELD_DATE};
				String sel = DatabaseStrings.FIELD_ID;
				//  Cursor queryAll(String table, String[] columns,String selection,String[] selectionArgs, String groupBy, String having, String orderBy)
				final Cursor cq = dbmanager.queryAll(TBL_NAME_RX, col, sel, null, null, null, null);
				cq.getCount();
				if (utility.isFilled(cq)) {
					if (dbPosition == cq.getCount()) {
                        dbPosition = cq.getCount() - 1;
					}
                    dbPosition = dbPosition + 1;
					cq.move(dbPosition);
					hostText.setText(cq.getString(cq.getColumnIndex(DatabaseStrings.FIELD_HOSTNAME)));
					srcPortText.setText(cq.getString(cq.getColumnIndex(DatabaseStrings.FIELD_SRCPORT)));
					dstPortText.setText(cq.getString(cq.getColumnIndex(DatabaseStrings.FIELD_DSTPORT)));
					udpSendDataText.setText(cq.getString(cq.getColumnIndex(DatabaseStrings.FIELD_DATE)));

					summaryText.setText("Pos" + dbPosition + " /" + cq.getCount());
					cq.close();

					// prova di una query custom da fare sulla tabella, restituisce un Cursor con i campi indicati e si posiziona sulla selezione Sel
					//final Cursor cursore = utility.customQuery(context, DatabaseStrings.TBL_NAME_RX, DatabaseStrings.FIELD_ID, DatabaseStrings.FIELD_HOSTNAME, DatabaseStrings.FIELD_SRCPORT, DatabaseStrings.FIELD_DSTPORT, DatabaseStrings.FIELD_DATE, DatabaseStrings.FIELD_HOSTNAME);
					//cursore.close();
				} else {
					return;
				}
			}
		});

		btnsearch_minus.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
                String[] col = {DatabaseStrings.FIELD_ID, DatabaseStrings.FIELD_HOSTNAME, DatabaseStrings.FIELD_SRCPORT, DatabaseStrings.FIELD_DSTPORT, DatabaseStrings.FIELD_DATE};
				String sel = DatabaseStrings.FIELD_ID;
				//  Cursor queryAll(String table, String[] columns,String selection,String[] selectionArgs, String groupBy, String having, String orderBy)
				final Cursor cq = dbmanager.queryAll(TBL_NAME_RX, col, null, null, null, null, null);
				if (utility.isFilled(cq)) {
					if (dbPosition <= 1){
                        dbPosition = 2;
					}
                    dbPosition = dbPosition - 1 ;
					cq.move(dbPosition);
                	hostText.setText(cq.getString(cq.getColumnIndex(DatabaseStrings.FIELD_HOSTNAME)));
                	srcPortText.setText(cq.getString(cq.getColumnIndex(DatabaseStrings.FIELD_SRCPORT)));
                	dstPortText.setText(cq.getString(cq.getColumnIndex(DatabaseStrings.FIELD_DSTPORT)));
                	udpSendDataText.setText(cq.getString(cq.getColumnIndex(DatabaseStrings.FIELD_DATE)));

					summaryText.setText("Pos" + dbPosition + " /" + cq.getCount());
					//toast.upShort(context, "Pos" + dbPosition + " /" + cq.getCount());
					cq.close();
				}
			}



		});

		btnPurge.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
				int val;
                if (dbPosition >0){
                    val=dbmanager.purge(DatabaseStrings.TBL_NAME_RX);
					toast.upShort(context, "Rimossi n° " + val + " record");
					listupdate(dbPosition);
					}
            }
        });

        btnEraseAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                int val;
                if (dbPosition >0){
                    val=dbmanager.delete(DatabaseStrings.TBL_NAME_RX);
                    toast.upShort(context, "Rimossi n° "+ val + " record");
                    listupdate(dbPosition);
                }
            }
        });

		btnRxDataRecall.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				String[] col = {DatabaseStrings.FIELD_ID, DatabaseStrings.FIELD_HOSTNAME, DatabaseStrings.FIELD_SRCPORT, DatabaseStrings.FIELD_DSTPORT, DatabaseStrings.FIELD_DATE};
				String sel = DatabaseStrings.FIELD_ID;
				//  Cursor queryAll(String table, String[] columns,String selection,String[] selectionArgs, String groupBy, String having, String orderBy)
				final Cursor cq = dbmanager.queryAll(TBL_NAME_RX, col, null, null, null, null, null);

				cq.moveToLast(); // ultimo dato inserito

                Utility_1.valore1=cq.getString(cq.getColumnIndex(DatabaseStrings.FIELD_ID));
                Utility_1.valore2=cq.getString(cq.getColumnIndex(DatabaseStrings.FIELD_HOSTNAME));
                Utility_1.valore3=cq.getString(cq.getColumnIndex(DatabaseStrings.FIELD_SRCPORT));
                Utility_1.valore4=cq.getString(cq.getColumnIndex(DatabaseStrings.FIELD_DATE));

                hostText.setText(cq.getString(cq.getColumnIndex(DatabaseStrings.FIELD_HOSTNAME)));
                textReceived.setText(cq.getString(cq.getColumnIndex(DatabaseStrings.FIELD_ID)));
			}
		});

    }


	@Override
	protected void onResume() {
		super.onResume();
		// DB
		final DBManager dbmanager = new DBManager(getApplicationContext());

		listupdate(dbPosition);
		// Register the broadcast receiver.
		registerReceiver(receiver, filter);

	}

	protected  void onStart() {
		super.onStart();
		final Context context = getApplicationContext();
		final ToastReady toast = new ToastReady();
		 //  messaggio toast se dati ricevuti dal servizio rx
		datafromrx=Utility_1.serviceRxText;
		if (datafromrx!=null) {
			toast.upShort(context, "Il servizio ha ricevuto: " + datafromrx);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		// Unregister the receiver
		unregisterReceiver(receiver);

	}

	protected void listupdate(Integer dbPos) {
			final DBManager dbmanager = new DBManager(getApplicationContext());
            // istanza di oggetti da layout
            final EditText hostText = (EditText)findViewById(R.id.editText2);
            final EditText srcPortText = (EditText)findViewById(R.id.editText4);
            final EditText dstPortText = (EditText)findViewById(R.id.editText3);
            final EditText udpSendDataText = (EditText)findViewById(R.id.editText1);
            final EditText summaryText = (EditText)findViewById(R.id.editText5);

			dbmanager.saverx(hostText.getText().toString(), srcPortText.getText().toString(), dstPortText.getText().toString(), udpSendDataText.getText().toString());

			String[] col = {DatabaseStrings.FIELD_ID, DatabaseStrings.FIELD_HOSTNAME, DatabaseStrings.FIELD_SRCPORT, DatabaseStrings.FIELD_DSTPORT, DatabaseStrings.FIELD_DATE};
            String sel = DatabaseStrings.FIELD_ID;
            // settaggi delle caselle di testo
            //srcPortText.setText("2000");
            //dstPortText.setText("2000");
        	final Cursor cqrx = dbmanager.queryAll(TBL_NAME_RX,col,null,null,null,null,null);
		    cqrx.moveToLast();
            cqrx.moveToFirst();
            dbPosition= cqrx.getCount();

           	hostText.setText(cqrx.getString(cqrx.getColumnIndex(DatabaseStrings.FIELD_HOSTNAME)));
           	srcPortText.setText(cqrx.getString(cqrx.getColumnIndex(DatabaseStrings.FIELD_SRCPORT)));
           	dstPortText.setText(cqrx.getString(cqrx.getColumnIndex(DatabaseStrings.FIELD_DSTPORT)));
           	udpSendDataText.setText(cqrx.getString(cqrx.getColumnIndex(DatabaseStrings.FIELD_DATE)));
            summaryText.setText("Pos" + dbPosition + " /" + cqrx.getCount());
			System.out.println("Update eseguito");
			cqrx.close();
	}


    protected void startservicerx()   {
        final Context context = getApplicationContext();
        final ToastReady toast = new ToastReady();

        startService(new Intent(this, ServiceRtx.class));
        toast.upShort(context, "Servizio ha ricevuto il comando di START");
    }

    protected void stopservicerx() {
        final Context context = getApplicationContext();
        final ToastReady toast = new ToastReady();

        stopService(new Intent(context, ServiceRtx.class));
        toast.upShort(context, "Servizio ha ricevuto il comando di STOP");

    }



}


