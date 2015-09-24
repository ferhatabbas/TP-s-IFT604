package Serveur.DATA;

import javafx.util.Pair;

import java.sql.Time;
import java.time.Clock;
import java.util.concurrent.Callable;

/**
 Java ECHO server with UDP sockets example
 Silver Moon (m00n.silv3r@gmail.com)
 */

public class Match implements Callable<String>
{
    protected String equipe1;
    protected String equipe2;
    protected Time chrono;
    protected Pair<Integer,Integer> pointage;
    //Pointage et compteur
    protected String penalite;


    private String cmd;



    public Match(String Commande){
        cmd=Commande;

    }

    //simple function to echo data to terminal
    public static void echo(String msg)
    {
        System.out.println(msg);
    }

    @Override
    public String call() throws Exception {
        if(cmd.equals("/raf")){
            Match.getMatch();
        }
        else if(cmd.equals(("/choose"))){
            System.out.println("Choisissez votre match");
            Affichermatch();

        }
        else
            return "Invalid command";
    }

    private getMatch(String idEquipe){



    }
    private String AfficherMatch(){
        String lesMatch;


        return lesMatch;
    }
}