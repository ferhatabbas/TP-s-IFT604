package Serveur.Suivi_match;

/**
 * Created by Ferhat on 2015-09-13.  c'est une version en tcp , je vais la changer en UDP prochainement
 */
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.lang.String;

import static Serveur.Suivi_match.udp_client.echo;

public class serveur implements Runnable{

    protected int          serverPort   = 8080;
    protected DatagramSocket serverSocket = null;
    protected boolean      isStopped    = false;
    protected Thread       runningThread= null;
    protected ExecutorService threadPool =
            Executors.newFixedThreadPool(10);

    public serveur(int port){
        this.serverPort = port;
    }
    public  void main(String args[]){

        run();
    }

    public void run(){
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }

        while(! isStopped()){
            DatagramSocket sock = null;
            try {
                //1. creating a server socket, parameter is local port number
                sock = new DatagramSocket(7777);

                //buffer to receive incoming data
                byte[] buffer = new byte[65536];
                DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
                //2. Wait for an incoming data
                    echo("Server socket created. Waiting for incoming data...");
                //communication loop
                while(true)
                {
                    sock.receive(incoming);
                    byte[] data = incoming.getData();
                    String s = new String(data, 0, incoming.getLength());

                    //echo the details of incoming data - client ip : client port - client message
                    echo(incoming.getAddress().getHostAddress() + " : " + incoming.getPort() + " - " + s);

                    s = "OK : " + s;
                    DatagramPacket dp = new DatagramPacket(s.getBytes() , s.getBytes().length , incoming.getAddress() , incoming.getPort());
                    sock.send(dp);
                }

            } catch (IOException e) {

                System.err.println(
                        "Error accepting client connection"+ e);
            }
            this.threadPool.execute(
                    new WorkerRunnable(sock,
                            "Thread Pooled Server"));
        }
        this.threadPool.shutdown();
        System.out.println("Server Stopped.") ;
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }
    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (Exception e) {
            throw new RuntimeException("Error closing server", e);
        }
    }



}