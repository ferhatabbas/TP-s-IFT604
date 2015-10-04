package Serveur.DATA;

import java.util.ArrayList;

/**
 * Created by ferhat on 2015-09-30.
 */
public class Joueur {
    protected String penalite;
    protected ArrayList<Integer> compteur;
    protected String NometPrenom;
    protected int numjoueur;
    protected Score score;

    public Joueur(String pena,ArrayList<Integer> cpt, String NomPrenom, int numJ){
        penalite=pena;
        compteur=cpt;
        NometPrenom=NomPrenom;
        numjoueur=numJ;
    }
}
