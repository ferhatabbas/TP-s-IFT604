package Serveur.Suivi_match;

/**
 * Created by Sylvain on 2015-09-21.
 */
public class Main {
    public static void main(String args[]) throws Exception {

        serveur server = new serveur(9000);
        new Thread(server).start();

        try {
            Thread.sleep(2000 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Stopping Server");
        server.stop();

    }
}