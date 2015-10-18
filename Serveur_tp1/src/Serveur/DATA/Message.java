package Serveur.DATA;

import Serveur.Serveurs.ServeurApp.Requete;
import Serveur.Serveurs.ServeurApp.Reponse;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * Created by User on 2015-10-16.
 */
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    public enum TypeDeMessage {
        REQUETE, REPONSE
    }
    private TypeDeMessage _type;
    private String _contenu;
    private String _matchId;
    private Requete _requete;
    private Reponse _reponse;
    private InetAddress _host;
    private Double _montantGagne;
    private Match _match;

    public Message(TypeDeMessage typeMessage, Requete requete){
        this._type = typeMessage;
        this._requete = requete;
        this._contenu = new String();
    }

    public Message(TypeDeMessage typeMessage, Reponse reponse){
        this._type = typeMessage;
        this._reponse = reponse;
    }

    public Message(TypeDeMessage typeMessage, String contenu){
        this._type = typeMessage;
        this._contenu = contenu;
    }

    public String toString(){
        return _type + " " + _requete;
    }

    public TypeDeMessage getType() {
        return _type;
    }

    public String getContenu() {
        return _contenu;
    }

    public void setType(TypeDeMessage type) {
        this._type = type;
    }

    public void setContenu(String contenu) {
        this._contenu = contenu;
    }

    public String getMatchId() {
        return _matchId;
    }

    public void setMatchId(String matchId) {
        this._matchId = matchId;
    }

    public Requete getRequete() {
        return _requete;
    }

    public void setRequete(Requete requete) {
        this._requete = requete;
    }

    public InetAddress getHost() {
        return _host;
    }

    public void setHost(InetAddress host) {
        this._host = host;
    }

    public Double getMontantGagne() {
        return _montantGagne;
    }

    public void setMontantGagne(Double montantGagne) {
        this._montantGagne = montantGagne;
    }

    public Reponse getReponse() {
        return _reponse;
    }

    public void setReponse(Reponse reponse) {
        this._reponse = reponse;
    }

    public Match getMatch() {
        return _match;
    }

    public void setMatch(Match match) {
        this._match = match;
    }
}
