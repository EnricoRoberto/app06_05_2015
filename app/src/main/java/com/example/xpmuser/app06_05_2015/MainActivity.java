package com.example.xpmuser.app06_05_2015;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.xpmuser.app06_05_2015.UdpFunction.ServerUdpActivity;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn1=(Button)findViewById(R.id.button1);
         
        btn1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {

	        Intent cambioPaginaUDP = new Intent(MainActivity.this, ServerUdpActivity.class);
			startActivity(cambioPaginaUDP);
			setContentView(R.layout.activity_udp_server_tx);

			
            }
  
        });
    }

    
  }
    
	
      

