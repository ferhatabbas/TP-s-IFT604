package Serveur.DATA;

import Serveur.Serveurs.ParisParMatchThreadManager;

import java.io.Serializable;
import java.util.*;

/**
 * Created by User on 2015-10-16.
 */
public class Paris implements Serializable {

    private static Paris _instance = null;
    private static final long serialVersionUID = 1L;
    private Map<Match, Set<Pari>> _paris;
    private Map<Match, ParisParMatchThreadManager> _parisThread;
    private Map<String, Parieur> _parieurs;

    private Paris() {
        _parisThread = new HashMap<Match, ParisParMatchThreadManager>();
        _paris = new HashMap<Match, Set<Pari>>();
        _parieurs = new HashMap<String, Parieur>();
    }

    public static synchronized Paris getInstance() {
        if (_instance == null) {
            _instance = new Paris();
        }
        return _instance;
    }

    public synchronized void addMessage(Message m) {
        String mid = m.getMatchId();
        Match key = ListMatch.getInstance().getMatchsCourants().get(mid);
        Set<Pari> p;

        if (!this._parisThread.containsKey(key)) {
            p = Collections.synchronizedSet(new HashSet<Pari>());
            this._parisThread.put(key, new ParisParMatchThreadManager(key, p));
        }

        this._parisThread.get(key).add(m);
    }

    public synchronized Parieur getParieur(String id) {
        if (!_parieurs.containsKey(id)) {
            return null;
        } else {
            return _parieurs.get(id);
        }
    }

    public void addParieur(Parieur p) {
        _parieurs.put(p.getId(), p);
    }

    public synchronized Double getParisSurMatch(Match match) {
        Double somme = 0.0;
        for (Pari pari : _paris.get(match)) {
            somme += pari.getMontant();
        }
        return somme;
    }

    public synchronized Double getParisSurMatchPourEquipe(Match match, String equipe) {
        Double somme = 0.0;
        for (Pari pari : _paris.get(match)) {
            if (pari.getVainqueurAttendu().equalsIgnoreCase(equipe)) {
                somme += pari.getMontant();
            }
        }
        return somme;
    }

    public Map<Match, Set<Pari>> getParis() {
        return _paris;
    }

    public Set<Pari> getParis(Match match) {
        return _paris.get(match);
    }

    public Set<Pari> getParis(Parieur parieur) {
        Set<Pari> paris = new HashSet<Pari>();

        return paris;
    }

    public void setParis(Map<Match, Set<Pari>> paris) {
        this._paris = paris;
    }
}
