package Serveur.DATA;

import javafx.util.Pair;

import java.sql.Time;
import java.time.Clock;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.Callable;

/**
 Java ECHO server with UDP sockets example
 Silver Moon (m00n.silv3r@gmail.com)
 */

public class Match //implements Callable<String>
{
    protected int id;
    protected String equipe1;
    protected String equipe2;
    protected int chrono;
    protected long start = System.currentTimeMillis();
    protected Integer pointageEquipe1;
    protected Integer pointageEquipe2;
    //Pointage et compteur
    protected String penalite;


    private String cmd;


    public Match(int idM,String equipe1M,String equipe2M,int chronoM,Integer pointageEquipe1M,Integer pointageEquipe2M,String penaliteM){
        id = idM;
        equipe1=equipe1M;
        equipe2=equipe2M;
        chrono=chronoM;
        pointageEquipe1 = pointageEquipe1M;
        pointageEquipe2=pointageEquipe2M;
        penalite=penaliteM;
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
