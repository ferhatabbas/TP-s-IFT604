package Serveur.DATA;

import javafx.util.Pair;

import java.sql.Time;
import java.time.Clock;
import java.util.*;
import java.util.concurrent.Callable;

/**
 Java ECHO server with UDP sockets example
 Silver Moon (m00n.silv3r@gmail.com)
 */

public class Match //implements Callable<String>
{
    protected int id;
    protected Equipe equipe1;
    protected Equipe equipe2;
    protected int chrono;
    protected long start = System.currentTimeMillis();



    private String cmd;


    public Match(int idM,Equipe equipe1M,Equipe equipe2M,int chronoM){
        id = idM;
        equipe1=equipe1M;
        equipe2=equipe2M;
        chrono=chronoM;
    }

    public Match(String Commande){
        cmd=Commande;

    }

    //simple function to echo data to terminal
    public static void echo(String msg)
    {
        System.out.println(msg);
    }
    protected void affiche(Match leMatch) {
        System.out.println("id match ->" + leMatch.id);
        System.out.println("equipe1 ->" + leMatch.equipe1);
        System.out.println("equipe2 ->" + leMatch.equipe2);
        System.out.println("Point equipe 1 ->" + leMatch.pointageEquipe1);
        System.out.println("Point equipe 2 ->" + leMatch.pointageEquipe2);
        System.out.println("chrono ->" + leMatch.chrono);
        System.out.println("Penalite match ->" + leMatch.penalite);

    }

    protected void miseAjourChrono(Match leMatch){
        leMatch.chrono = chrono+30;
    }

    public long getTime(){
        return start;
    }

    protected  void setTime(long letemps){
        start = letemps;
    }

}

   /* @Override
    public String call() throws Exception {
        Scanner scanner = new Scanner(System.in);

        if(cmd.equals("/raf")){
           // Match.getMatch();
        }
        else if(cmd.equals(("/choose"))){
           // AfficherMatch();
            System.out.println("Renseigner l'id du math");
            int id = scanner.nextInt();
            //match_choisi(id);

        }
        else
            return  "Invalid command";

        return  "Commande termine";
    }






    */
