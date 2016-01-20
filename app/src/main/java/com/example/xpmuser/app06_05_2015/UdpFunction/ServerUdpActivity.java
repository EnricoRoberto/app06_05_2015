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

public class ServerUdpActivity extends Activity{
	public Integer dbPosition= 0;
	public String datafromrx=null;
	private IntentFilter filter =new IntentFilter("Ricevi");

	private BroadcastRx receiver =new BroadcastRx();
    private static final int DIALOG_ERROR_ID = 1;
    private static final int DIALOG_CONFIRM_ID = 2;



	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // imposta layout corrente
		setContentView(R.layout.activity_udp_server_tx);

		// istanza di oggetti da layout
        final Button btnSend=(Button)findViewById(R.id.Button12);
        final Button btnHome=(Button)findViewById(R.id.Button13);
		final Button btnPurge=(Button)findViewById(R.id.Button3);
		final Button btnEraseAll=(Button)findViewById(R.id.Button4);
		final Button btnToRx=(Button)findViewById(R.id.Button6);

        final EditText udpSendDataText = (EditText)findViewById(R.id.editText1);
        final EditText hostText = (EditText)findViewById(R.id.editText2);
        final EditText srcPortText = (EditText)findViewById(R.id.editText4);
        final EditText dstPortText = (EditText)findViewById(R.id.editText3);
		final EditText summaryText = (EditText)findViewById(R.id.editText5);


      //  final ListView hostList = (ListView) findViewById(R.id.listView);
		final Button btnsearch_plus=(Button)findViewById(R.id.button);
		final Button btnsearch_minus=(Button)findViewById(R.id.button2);
        final CheckBox dt_insert = (CheckBox)findViewById(R.id.checkBox1);

		// settaggi delle caselle di testo
        srcPortText.setText("2000");
        dstPortText.setText("2000");
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
        if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

    /*
    *FINE DELLE INIZIALIZZAZIONE DI OGGETTI E VARIABILI
    ********************************************************
    */

        btnHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent cambioPaginaHome = new Intent(ServerUdpActivity.this, MainActivity.class);
                startActivity(cambioPaginaHome);
                setContentView(R.layout.activity_main);
                stopservicerx();

            }
        });

        btnToRx.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent cambioPaginaRx = new Intent(ServerUdpActivity.this, ServerUdpActivityRx.class);
                startActivity(cambioPaginaRx);
                setContentView(R.layout.activity_udp_server_rx);
                startservicerx();

            }
        });

		btnSend.setOnClickListener(new View.OnClickListener() {

			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
			public void onClick(View arg0) {

                stopservicerx();
                String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());

				if (dt_insert.isChecked()!=true){
					//udpSendDataText.setText(udpSendDataText.getText()+" ** D&T activated");

					timeStamp = "# No D&T selected #";
				}

                if (udpSendDataText.getText().toString().length() >= 19 ){
                    udpSendDataText.setText(udpSendDataText.getText().toString().substring(udpSendDataText.getText().toString().length()-19, udpSendDataText.getText().toString().length()));
                }

                else {
                    udpSendDataText.setText(udpSendDataText.getText() + timeStamp);
                }

                String txt;
				txt = udpSendDataText.getText().toString();
				int lentxt = txt.length();
				int difflen ;

				String pippoEstratto = new String();

				difflen = lentxt - timeStamp.length();
                // se c'è già del testo nella casella
				if (difflen!= 0){
					pippoEstratto= txt.substring(0,lentxt - timeStamp.length());
                    udpSendDataText.setText(pippoEstratto + timeStamp);
				}

				String host = "";
				int srcPort = Integer.parseInt(srcPortText.getText().toString());
				int dstPort = Integer.parseInt(dstPortText.getText().toString());
				host = hostText.getText().toString();


				//               /* non invia udp con simulatore
				try {
					sendUdp.avvio(udpSendDataText.getText().toString(), srcPort, dstPort, host);
					System.out.println("Dati UDP inviati");
					//	Toast
					toast.upShort(context, "Dati UDP inviati");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Testo IOException generato = " + e.getMessage());
					System.out.println("Dati NON inviati causa eccezione");
					toast.upShort(context, "Dati NON inviati causa eccezione: " + e.getMessage());
				}
				//////////////////////////////////////////////     */
				dbmanager.savetx(hostText.getText().toString(), srcPortText.getText().toString(), dstPortText.getText().toString(), udpSendDataText.getText().toString());

				String[] col = {DatabaseStrings.FIELD_ID, DatabaseStrings.FIELD_HOSTNAME, DatabaseStrings.FIELD_DSTPORT, DatabaseStrings.FIELD_ID, DatabaseStrings.FIELD_SRCPORT, DatabaseStrings.FIELD_DATE};
				String sel = DatabaseStrings.FIELD_ID;
				//  Cursor queryAll(String table, String[] columns,String selection,String[] selectionArgs, String groupBy, String having, String orderBy)
				final Cursor cq = dbmanager.queryAll(TBL_NAME, col, sel, null, null, null, null);
				cq.moveToLast();
				String[] str_id = new String[]{cq.getString(cq.getColumnIndex(DatabaseStrings.FIELD_ID))};
				String[] str_hostname = new String[]{cq.getString(cq.getColumnIndex(DatabaseStrings.FIELD_HOSTNAME))};
				String[] str_srcport = new String[]{cq.getString(cq.getColumnIndex(DatabaseStrings.FIELD_SRCPORT))};
				String[] str_dstport = new String[]{cq.getString(cq.getColumnIndex(DatabaseStrings.FIELD_DSTPORT))};

				String[] str_dati = new String[]{cq.getString(cq.getColumnIndex(DatabaseStrings.FIELD_DATE))};
				//;;
				String[] str = new String[0];
			    dbPosition = cq.getCount();
				summaryText.setText("Pos" + dbPosition + " /" + cq.getCount());
				cq.close();

                View layoutPrincipale = findViewById(R.id.layout_contenitore);
                layoutPrincipale.setBackgroundResource(R.color.Verde_Tx);
                colorupdate(layoutPrincipale,R.color.Verde_Tx_Eseguito);

			}
		});

		btnsearch_plus.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String[] col = {DatabaseStrings.FIELD_ID, DatabaseStrings.FIELD_HOSTNAME, DatabaseStrings.FIELD_SRCPORT, DatabaseStrings.FIELD_DSTPORT, DatabaseStrings.FIELD_DATE};
				String sel = DatabaseStrings.FIELD_ID;
				//  Cursor queryAll(String table, String[] columns,String selection,String[] selectionArgs, String groupBy, String having, String orderBy)
				final Cursor cq = dbmanager.queryAll(TBL_NAME, col, sel, null, null, null, null);
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
					final Cursor cursore = utility.customQuery(context, DatabaseStrings.TBL_NAME, DatabaseStrings.FIELD_ID, DatabaseStrings.FIELD_HOSTNAME, DatabaseStrings.FIELD_SRCPORT, DatabaseStrings.FIELD_DSTPORT, DatabaseStrings.FIELD_DATE, DatabaseStrings.FIELD_HOSTNAME);
					cursore.close();
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
				final Cursor cq = dbmanager.queryAll(TBL_NAME, col, null, null, null, null, null);
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
                    val=dbmanager.purge(DatabaseStrings.TBL_NAME);
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
                    val=dbmanager.delete(DatabaseStrings.TBL_NAME);
                    toast.upShort(context, "Rimossi n° "+ val + " record");
                    listupdate(dbPosition);
                }
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

            dbmanager.savetx(hostText.getText().toString(), srcPortText.getText().toString(), dstPortText.getText().toString(), udpSendDataText.getText().toString());
			String[] col = {DatabaseStrings.FIELD_ID, DatabaseStrings.FIELD_HOSTNAME, DatabaseStrings.FIELD_SRCPORT, DatabaseStrings.FIELD_DSTPORT, DatabaseStrings.FIELD_DATE};
            String sel = DatabaseStrings.FIELD_ID;
            // settaggi delle caselle di testo
            srcPortText.setText("2000");
            dstPortText.setText("2000");
           	final Cursor cq = dbmanager.queryAll(TBL_NAME, col, null, null, null, null, null);
			cq.moveToLast();
			dbPosition= cq.getCount();

           	hostText.setText(cq.getString(cq.getColumnIndex(DatabaseStrings.FIELD_HOSTNAME)));
           	srcPortText.setText(cq.getString(cq.getColumnIndex(DatabaseStrings.FIELD_SRCPORT)));
           	dstPortText.setText(cq.getString(cq.getColumnIndex(DatabaseStrings.FIELD_DSTPORT)));
           	udpSendDataText.setText(cq.getString(cq.getColumnIndex(DatabaseStrings.FIELD_DATE)));
            summaryText.setText("Pos" + dbPosition + " /" + cq.getCount());
			System.out.println("Update eseguito");
			cq.close();
	}

    protected void colorupdate(View view, int color1)    {
                view.setBackgroundResource(color1);
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

