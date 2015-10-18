package Serveur.Serveurs;

import java.io.IOException;
import java.net.DatagramSocket;
import java.sql.SQLException;


public class ServeurApp {
    public enum Requete {
        LISTE_DES_MATCHS, MATCH, PARIS, PENALTIES, NONE
    }

    public enum Reponse {
        CONFIRMATION_PARI, ERREUR_PARI, PARI_GAGNE, PARI_PERDU, DETAILS_MATCH
    }
    private DatagramSocket srv;
    //private SalleManager mgr;


    static public void main(String[] args){
        System.out.println("Start server.");
        try {
            new ServeurApp().serveurReceive();
        }catch(Exception ex){
            ex.printStackTrace();
        }



    }
    public ServeurApp()throws IOException, SQLException {
        srv = new DatagramSocket(6549);
      // mgr = new SalleManager();
    }
    public void serveurReceive() throws IOException{
        while(true){

            new Thread(new serveur()).start();
        }
    }
}
