package Serveur.DATA;

/**
 * Created by User on 2015-10-16.
 */
public class MatchEvent extends java.util.EventObject {
    private static final long serialVersionUID = 1L;
    String _matchId;
    String _buteur;
    private Penalite _penalite;

    public MatchEvent(Object source, String matchId) {
        super(source);
        this._matchId = matchId;
    }

    public String getButeur() {
        return _buteur;
    }

    public void setMatchId(String matchId) {
        this._matchId = matchId;
    }

    public void setButeur(String buteur) {
        this._buteur = buteur;
    }

    public String getMatchId() {
        return _matchId;
    }

    public Penalite getPenalite() {
        return _penalite;
    }

    public void setPenalite(Penalite penalite) {
        this._penalite = penalite;
    }
}
