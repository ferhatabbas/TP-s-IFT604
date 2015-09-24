package Serveur.Suivi_match;

/**
 * Created by Ferhat on 2015-09-13.  c'est une version en tcp , je vais la changer en UDP prochainement
 */

import Serveur.DATA.Match;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.*;

import static Serveur.Suivi_match.udp_client.echo;

public class serveur implements Runnable {

    protected DatagramPacket paquet;
    protected DatagramSocket serverSocket = null;
    protected boolean      isStopped    = false;
    protected ExecutorService threadPool =
            Executors.newFixedThreadPool(10);
    protected CompletionService<String> pool = new ExecutorCompletionService<String>(threadPool);



    public serveur(DatagramSocket srv){
        serverSocket= srv;
    }
    /**
     * Méthode démarré par le thread.
     */
    @Override
    public void run(){

        dispatcherequest();
    }


    private void dispatcherequest(){
        while(true) {

            try {

                //buffer to receive incoming data
                byte[] buffer = new byte[65536];
                DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
                //2. Wait for an incoming data
                echo("Server socket created. Waiting for incoming data...");
                //communication loop
                while (true) {
                    serverSocket.receive(incoming);
                    String request = new String(incoming.getData());
                    InetAddress IPadress = incoming.getAddress();
                    int port = incoming.getPort();
                    pool.submit(new Match(request));
                    String out = pool.take().get();

                    //echo the details of incoming data - client ip : client port - client message
                    echo(incoming.getAddress().getHostAddress() + " : " + incoming.getPort() + " - " + out);
                    DatagramPacket dp = new DatagramPacket(out.getBytes(), out.getBytes().length, IPadress, port);
                    serverSocket.send(dp);
                }

            } catch (IOException e) {

                System.err.println(
                        "Error accepting client connection" + e);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            threadPool.shutdown();
            System.out.println("thread Stopped.") ;
        }

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