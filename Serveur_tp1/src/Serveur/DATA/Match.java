package Serveur.DATA;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 Java ECHO server with UDP sockets example
 Silver Moon (m00n.silv3r@gmail.com)
 */

public class Match implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String _id;
    private Equipe _local;
    private Equipe _visiteur;
    private Date _date;
    private List<Penalite> _prenalites;
    private List<String> _buteurs;
    private long _chronometre;
    private static ChronometreDuMatch _chronometreUpdater;
    private static MatchUpdater _matchUpdater;
    private static List _listeners = new ArrayList();

    public synchronized void addEventListener(IEventClassListener listener) {
        _listeners.add(listener);
    }

    public synchronized void removeEventListener(IEventClassListener listener) {
        _listeners.remove(listener);
    }

    public Match(Equipe local, Equipe visiteur, Date date) {
        this._local = local;
        this._visiteur = visiteur;
        this._date = date;
        this._prenalites = new ArrayList<Penalite>();
        this._buteurs = new ArrayList<String>();
        this._chronometre = 0;
        this._chronometreUpdater = new ChronometreDuMatch(30, this);
        this._matchUpdater = new MatchUpdater(this);
        this.initId();

    }

    private void initId() {
        StringBuilder builder = new StringBuilder();
        builder.append(_local.toString().subSequence(0, 3));
        builder.append("VS");
        builder.append(_visiteur.toString().subSequence(0, 3));
        this._id = builder.toString();
    }

    public void ajouterBut(String nomEquipe, String nomJoueur) {
        if (nomEquipe.equals(_local.toString())) {
            this.getLocal().setButs(this.getLocal().getButs() + 1);
        } else {
            this.getVisiteur().setButs(this.getVisiteur().getButs() + 1);
        }

        _buteurs.add(nomJoueur);
        lancerButEvent(nomJoueur);
    }

    public void ajouterPenalite(String nomEquipe, Long startTime,
                                Long msecDuration, String nomJoueur) {
        Penalite penalite = new Penalite(startTime, msecDuration, nomJoueur);
        if (nomEquipe == _local.toString()) {
            this.getLocal().setPenaliteCourante(nomJoueur);
        } else {
            this.getVisiteur().setPenaliteCourante(nomJoueur);
        }

        _prenalites.add(penalite);
        lancerPenaliteEvent(penalite);
    }

 /*   public void updaterTemps() {
        lancerTempUpdaterEvent();
    }*/

    private synchronized void lancerPenaliteEvent(Penalite penalite) {
        MatchEvent event = new MatchEvent(this, _id);
        event.setPenalite(penalite);
        Iterator i = _listeners.iterator();
        while (i.hasNext()) {
            ((IEventClassListener) i.next()).traiterPenaliteEvent(event);
        }
    }

    private synchronized void lancerButEvent(String buteur) {
        MatchEvent event = new MatchEvent(this, _id);
        event.setButeur(buteur);
        Iterator i = _listeners.iterator();
        while (i.hasNext()) {
            ((IEventClassListener) i.next()).traiterButEvent(event);
        }
    }

/*    private synchronized void lancerTempUpdaterEvent()
    {
        MatchEvent event = new MatchEvent(this, _id);
        Iterator i = _listeners.iterator();
        while (i.hasNext()) {
            ((IEventClassListener) i.next()).traiterTempsUpdateEvent(event);
        }
    }*/

    public boolean partieTerminee() {
        if(this._chronometre > TimeUnit.MINUTES.toMillis(60))
        {
            this._chronometreUpdater._task.cancel();
        }
        return this._chronometre > TimeUnit.MINUTES.toMillis(60);
    }

    public List<Penalite> getPenalitesCourantes() {
        List<Penalite> penalitesCourantes = new ArrayList<Penalite>();

        for (Penalite penalite : _prenalites) {
            if (penalite.estEnCours()) {
                penalitesCourantes.add(penalite);
            }
        }

        return penalitesCourantes;
    }

    public String listPenalties(List<Penalite> penaltiesToList) {
        StringBuilder listing = new StringBuilder();
        listing.append("\n");

        for (Penalite pen : penaltiesToList) {
            listing.append(pen + "\n");
        }

        return listing.toString();
    }

    public String getVinqueur() {
        if (!this.partieTerminee()) {
            return null;
        }
        if (_local.getButs() == _visiteur.getButs()) {
            return null;
        }
        Equipe equipeGagnante = (_local.getButs() > _visiteur.getButs()) ? _local
                : _visiteur;
        return equipeGagnante.toString();
    }

    public int getPeriodeMatch() {

        if (this._chronometre > 0
                && this._chronometre <= TimeUnit.MINUTES.toMillis(20)) {
            return 1; // 1ere periode du match
        } else if (this._chronometre > TimeUnit.MINUTES.toMillis(20)
                && this._chronometre <= TimeUnit.MINUTES.toMillis(40)) {
            return 2; // 2e periode du match
        } else if (this._chronometre > TimeUnit.MINUTES.toMillis(40)
                && this._chronometre < TimeUnit.MINUTES.toMillis(60)) {
            return 3; // 3e periode du match
        } else {
            return 0;
        }
    }

    public String toString() {
        return _local + " - " + _visiteur;
    }

    public String getDetailsMatch() {
        StringBuilder str = new StringBuilder();

        if(_prenalites != null)
        {
            for (Penalite penalite : _prenalites) {
                str.append(penalite.toString()+"\r\n");
            }
        }

        long secondes = (_chronometre / 1000) % 60;
        long minutes = (_chronometre / (1000 * 60)) % 60;
        long heures = (_chronometre / (1000 * 60 * 60)) % 24;

        String temps = String.format("%02d:%02d:%02d", heures, minutes,
                secondes);

        return _local + " " + _local.getButs() + " VS " + _visiteur.getButs()
                + " " + _visiteur + "\r\n - Temps : " + temps + "\r\n"+str.toString();
    }

    public Equipe getLocal() {
        return _local;
    }

    public void setLocal(Equipe local) {
        this._local = local;
    }

    public Equipe getVisiteur() {
        return _visiteur;
    }

    public void setVisiteur(Equipe visiteur) {
        this._visiteur = visiteur;
    }

    public Date getDate() {
        return _date;
    }

    public List<Penalite> getPenalites() {
        return _prenalites;
    }

    public void setPenalites(List<Penalite> penalites) {
        this._prenalites = penalites;
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public long getChronometre() {
        return _chronometre;
    }

    public void setChronometre(long chronometre) {
        this._chronometre = chronometre;
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
