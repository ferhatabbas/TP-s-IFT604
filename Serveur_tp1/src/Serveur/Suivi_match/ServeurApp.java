/*
package Serveur.Suivi_match;

import java.io.IOException;
import java.net.DatagramSocket;
import java.sql.SQLException;

*/
/**
 * Created by User on 2015-09-23.
 *//*

public class ServeurApp {
    private DatagramSocket srv;
    //private SalleManager mgr;


    static public void main(String[] args){
        System.out.println("Start server.");
        try {
            new ServeurApp().serveurReceive();
        }catch(Exception ex){
            ex.printStackTrace();
        }*/
/**//*


    }
    public ServeurApp()throws IOException, SQLException {
        srv = new DatagramSocket(7777);
      // mgr = new SalleManager();
    }
    public void serveurReceive() throws IOException{
        while(true){

            new Thread(new serveur(srv)).start();
        }
    }
}
*/
