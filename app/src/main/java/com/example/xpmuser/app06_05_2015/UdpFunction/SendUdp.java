package com.example.xpmuser.app06_05_2015.UdpFunction;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SendUdp {

        public void avvio(String arg, int srcPort, int dstPort, String host) throws IOException {

            InetAddress addr = InetAddress.getByName(host);
            byte [] msg = arg.getBytes();
            //Creazione della Socket per l'invio del Datagramma con porta Client dinamica
            DatagramSocket s = new DatagramSocket(srcPort);
            //Creazione del pacchetto da inviare al Server
            DatagramPacket p = new DatagramPacket(msg, msg.length, addr, dstPort);
            p.setData(msg);
            p.setLength(msg.length);
            //Invio del pacchetto sul Socket
            s.send(p);
            s.close();
     }

}
