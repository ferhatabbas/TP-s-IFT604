package Serveur.DATA;

/**
 * Created by User on 2015-10-16.
 */
public class Penalite {
    private Long _tempsDebut;
    private Long _dureeEnMilliSec;
    private String _nomJoueur;

    public Penalite(Long debut, Long duree, String nonJoueur) {
        this._tempsDebut = debut;
        this._dureeEnMilliSec = duree;
        this._nomJoueur = nonJoueur;
    }

    public boolean estEnCours() {
        long msecCurrentTime = System.currentTimeMillis();
        long msecElapsedTime = msecCurrentTime - _tempsDebut;

        // Si la penaltie est encours return true
        if (msecElapsedTime < _dureeEnMilliSec) {
            return true;
        }

        return false;
    }

    public String toString() {
        return "Penalite a: " + _nomJoueur;
    }
}
