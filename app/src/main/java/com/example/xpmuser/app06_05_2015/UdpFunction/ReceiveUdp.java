package com.example.xpmuser.app06_05_2015.UdpFunction;

import android.content.Context;
import android.util.Log;

import com.example.xpmuser.app06_05_2015.ToastFuntion.ToastReady;
import com.example.xpmuser.app06_05_2015.UtilityClass.Utility_1;
import android.content.Context;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ReceiveUdp {

    Context ctx;
	public String runUdpServer(int rcvport) {
		String resText= null; // text received

		final ToastReady toast = new ToastReady();

	//	System.out.println("modo RICEZIONE impostato");
		String lText;
	    int MAX_UDP_DATAGRAM_LEN = 50;
		byte[] lMsg = new byte[MAX_UDP_DATAGRAM_LEN];
	    DatagramPacket dp = new DatagramPacket(lMsg, lMsg.length);
	   	DatagramSocket ds = null;
	    try {
	       	ds = new DatagramSocket(rcvport);
	        //disable timeout for testing
			ds.setSoTimeout(3000);
            ds.receive(dp);
            lText = new String(lMsg, 0, dp.getLength());
			resText = lText;
	        Log.i("UDP packet received", lText);
//	        textView.setText(lText);
	    } catch (SocketException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if (ds != null) {
	            ds.close();
	        }
	    }
	  //  System.out.println("modo RICEZIONE concluso");

	    return  resText;
	}




// end class
}
