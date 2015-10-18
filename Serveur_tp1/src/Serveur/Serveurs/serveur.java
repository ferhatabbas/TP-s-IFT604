package Serveur.Serveurs;


import Serveur.DATA.ListMatch;
import Serveur.DATA.Paris;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;




public class serveur implements Runnable {

    private final Paris _paris;
    private final ListMatch _matchs;
    private static serveur _instance = null;
    private static final int PORT = 6549;
    private static ServerSocket _serveurSocket;
    private Map<String, Set<String>> _clientsParMatch;
    protected boolean      isStopped    = false;
    protected ExecutorService threadPool =
            Executors.newFixedThreadPool(10);
    protected CompletionService<String> pool = new ExecutorCompletionService<String>(threadPool);



    public serveur(){
        this._paris = Paris.getInstance(); // On initialise la liste des paris
        this._matchs = ListMatch.getInstance(); // On recupere la liste des
        // matchs
        _clientsParMatch = Collections
                .synchronizedMap(new HashMap<String, Set<String>>());
    }


    @Override
    public void run(){

        try {
            dispatcherequest();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static synchronized serveur getInstance() {
        if (_instance == null) {
            _instance = new serveur();
        }

        return _instance;
    }


    private void dispatcherequest() throws IOException {
        serveur serveur ;

        // On ne lance qu'une seule instance du serveur (Singleton)
        serveur = getInstance();
        System.out.println(Inet4Address.getLocalHost().getHostAddress());
         _serveurSocket = new ServerSocket(PORT/*,0, InetAddress.getByName(null)*/);

        while(true) {

            try {

                //buffer to receive incoming data
                byte[] buffer = new byte[65536];
                DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
                //2. Wait for an incoming data

                //communication loop
                while (true) {
                    System.out.println("En attente des requetes des clients!!");
                    Socket clientSocket = _serveurSocket.accept();
                    //System.out.println("Reception de la requete du client!" + clientSocket.getInetAddress().getHostAddress());
                    InputStream is = clientSocket.getInputStream();
                    ObjectInputStream ois = new ObjectInputStream(is);
                    // Stocker la requete dans un objet de type String
                    String requeteClient = (String) ois.readObject();
                    pool.submit(new ThreadDeReponse(requeteClient,clientSocket));


                }

            } catch (IOException e) {

                System.err.println(
                        "Error accepting client connection" + e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            threadPool.shutdown();

            System.out.println("thread Stopped.") ;
        }

    }

    public Map<String, Set<String>> getClientsParMatch() {
        return _clientsParMatch;
    }
    public ListMatch getMatchs() {
        return _matchs;
    }

}
