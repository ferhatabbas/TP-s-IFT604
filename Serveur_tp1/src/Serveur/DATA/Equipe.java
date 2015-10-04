package Serveur.DATA;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by ferhat on 2015-09-30.
 */
public class Equipe {
    protected ArrayList<Joueur> joueur;
    protected Integer pointageEquipe;


    public Equipe(ArrayList<Joueur> joueurs,Integer Pointage){
        joueur=joueurs;
        pointageEquipe=Pointage;
    }
}
