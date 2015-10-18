package Serveur.DATA;

import java.io.Serializable;

/**
 * Created by ferhat on 2015-09-30.
 */
public class Equipe implements Serializable{
    private static final long serialVersionUID = 1L;
    private String _nom;
    private Integer _buts;
    private String _prenalite;

    public Equipe(String nom) {
        this._nom = nom;
        this._buts = new Integer(0);
        this._prenalite = new String("");
    }

    public String getNom() {
        return _nom;
    }

    public Integer getButs() {
        return _buts;
    }

    public String getPenaliteCourante() {
        return _prenalite;
    }

    public void setNom(String nom) {
        this._nom = nom;
    }

    public void setButs(Integer buts) {
        this._buts = buts;
    }

    public void setPenaliteCourante(String joueur) {
        this._prenalite = joueur;
    }

    public String toString() {
        return this._nom;
    }
}
