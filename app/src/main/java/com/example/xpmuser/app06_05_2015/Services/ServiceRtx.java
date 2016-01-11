package com.example.xpmuser.app06_05_2015.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.example.xpmuser.app06_05_2015.MainActivity;
import com.example.xpmuser.app06_05_2015.ToastFuntion.ToastReady;
import com.example.xpmuser.app06_05_2015.UdpFunction.ReceiveUdp;
import com.example.xpmuser.app06_05_2015.UtilityClass.Utility_1;

import java.util.Timer;
import java.util.TimerTask;

public class ServiceRtx extends Service {
    final ToastReady toast = new ToastReady();
    final ReceiveUdp receiverUdp = new ReceiveUdp();
    Timer timer  = new Timer();
    Context context;
    Intent intent = new Intent();

    public IBinder onBind(Intent intent) {
        return null;
    }
    public void onCreate() {

            TimerTask timerTask= new TimerTask() {
            @Override
                public void run() {
                System.out.println("Servizio RX in corso,live signal");
                //System.out.println("DATI RICEVUTI: " + receivetxt());
                System.out.println("Ricevuto: " + receivetxt());

                }
            };
        // timer.schedule(timerTask, 0,3000); // inizo a 0 ms, esegue ogni 3000ms non è preciso
        timer.scheduleAtFixedRate(timerTask, 0,1); // è preciso
    }
    public void onDestroy() {
        final ToastReady toast = new ToastReady();
        timer.cancel();
        super.onDestroy();
        System.out.println("************************* onDestroy eseguito ******************************************");
        System.out.println("***************************************************************************************");
    }
    public String receivetxt(){
        Utility_1.serviceRxText=receiverUdp.runUdpServer(2000);
        if (Utility_1.serviceRxText!=null) {
            intent.setAction(Utility_1.serviceRxText);
            Intent mIntent = new Intent();
            mIntent.setAction("Ricevi");
            mIntent.putExtra("Ricevi", "Additional info");
            sendBroadcast(mIntent);

        }

        return Utility_1.serviceRxText;
    }


}

