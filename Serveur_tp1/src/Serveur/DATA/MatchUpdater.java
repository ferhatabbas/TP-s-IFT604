package Serveur.DATA;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by User on 2015-10-16.
 */
public class MatchUpdater implements Serializable {
    private static final long serialVersionUID = 1L;
    private long _interval;
    private long _tempsDebut;
    private ArrayList<String> _nomJoueurs;

    public Timer _minuteur;
    TimerTask _task;
    private Match _match;


    public MatchUpdater(Match match) {
        _nomJoueurs = new ArrayList<>();
        getNomsJoueurs();
        _minuteur = new Timer();
        _task = new MiseAJour();
        this._match = match;
        this._tempsDebut = TimeUnit.SECONDS.toMillis(0);
        this._interval = TimeUnit.SECONDS.toMillis(30); // On update toutes les 30 sec
        _minuteur.scheduleAtFixedRate(_task, _tempsDebut, _interval);
    }


    class MiseAJour extends TimerTask {
        public void run() {
            if(_match.partieTerminee()){
                _minuteur.cancel(); // Si la partie est terminee, on arrete le minuteur
            }

            int valeur = new Random().nextInt(6);
            int indexJoueur = randomize();
            String nomJoueur = _nomJoueurs.get(indexJoueur);

            switch (valeur) {
                case 0:
                    _match.ajouterBut(_match.getLocal().toString(), nomJoueur);
                    break;
                case 1:
                    _match.ajouterBut(_match.getVisiteur().toString(), nomJoueur);
                    break;
                case 2:
                    _match.ajouterPenalite(_match.getLocal().toString(), System.currentTimeMillis(), TimeUnit.MINUTES.toMillis(2), nomJoueur);
                    break;
                case 3:
                    _match.ajouterPenalite(_match.getVisiteur().toString(), System.currentTimeMillis(), TimeUnit.MINUTES.toMillis(2), nomJoueur);
                    break;

                default:
                    break;
            }
        }
    }

    private void getNomsJoueurs() {
        _nomJoueurs.add("Jack Arbour");
        _nomJoueurs.add("Jason Allison");
        _nomJoueurs.add("Antti Aalto");
        _nomJoueurs.add("Juhamatti Aaltonen");
        _nomJoueurs.add("Mike Allison");
        _nomJoueurs.add("Miro Aaltonen");
        _nomJoueurs.add("Petri Aaltonen");
        _nomJoueurs.add("Bruce Abbey");
        _nomJoueurs.add("George Abbott");
        _nomJoueurs.add("Justin Abdelkader");
        _nomJoueurs.add("Clarence Abel");
        _nomJoueurs.add("Gerry Abel");
        _nomJoueurs.add("Konrad Abeltshauser");
        _nomJoueurs.add("Pontus Aberg");
        _nomJoueurs.add("Bruce Aberhart");
        _nomJoueurs.add("Dennis Abgrall");
        _nomJoueurs.add("Ramzi Abid");
        _nomJoueurs.add("Jeff Ablett");
        _nomJoueurs.add("Cameron Abney");
        _nomJoueurs.add("Elias Abrahamsson");
        _nomJoueurs.add("Thommy Abrahamsson");
        _nomJoueurs.add("Marty Abrams");
        _nomJoueurs.add("Cliff Abrecht");
        _nomJoueurs.add("Peter Abric");
        _nomJoueurs.add("Darren Acheson");
        _nomJoueurs.add("Gene Achtymichuk");
        _nomJoueurs.add("Doug Acomb");
        _nomJoueurs.add("Allan Acton");
        _nomJoueurs.add("Keith Acton");
        _nomJoueurs.add("Will Acton");
        _nomJoueurs.add("Doug Adam");
        _nomJoueurs.add("Jeremy Adduono");
        _nomJoueurs.add("David Aebischer");
        _nomJoueurs.add("Dmitry Afanasenkov");
        _nomJoueurs.add("Evgeny Afanasiev");
        _nomJoueurs.add("Bruce Affleck");
        _nomJoueurs.add("Maxim Afinogenov");
        _nomJoueurs.add("Greg Agar");
        _nomJoueurs.add("Pavel Agarkov");
        _nomJoueurs.add("Jim Agnew");
        _nomJoueurs.add("Kenneth Agostino");
        _nomJoueurs.add("Brian Ahern");
        _nomJoueurs.add("Thomas Ahlen");
        _nomJoueurs.add("Hakan Ahlund");
        _nomJoueurs.add("Timo Ahmaoja");
        _nomJoueurs.add("Jonas Ahnelov");
        _nomJoueurs.add("Peter Ahola");
        _nomJoueurs.add("Ari Ahonen");
        _nomJoueurs.add("Henrik Andersson");
        _nomJoueurs.add("Anthony Aiello");
    }

    private int randomize() {
        return new Random().nextInt(_nomJoueurs.size() - 1);
    }
}
