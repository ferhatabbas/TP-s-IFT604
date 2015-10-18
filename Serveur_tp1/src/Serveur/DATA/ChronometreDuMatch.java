package Serveur.DATA;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by User on 2015-10-16.
 */
public class ChronometreDuMatch implements Serializable{
    private static final long serialVersionUID = 1L;
    private long _interval;
    private long _tempsDebut;
    private Timer _minuteur;
    TimerTask _task;
    private Match _match;
    private long _secondes;

    public ChronometreDuMatch(long secondes, Match match) {
        _minuteur = new Timer();
        _task = new Update();
        this._match = match;
        this._secondes = secondes;
        this._tempsDebut = TimeUnit.SECONDS.toMillis(0);
        this._interval = TimeUnit.SECONDS.toMillis(30);
        _minuteur.scheduleAtFixedRate(new Update(), _tempsDebut, _interval);
    }

    class Update extends TimerTask {
        public void run() {
            _match.setChronometre(_match.getChronometre()
                    + TimeUnit.SECONDS.toMillis(ChronometreDuMatch.this._secondes));

            // A la fin du match on arrete le minuteur
            if (_match.partieTerminee()) {
                _minuteur.cancel();
            }
         //   else
           //     _match.updaterTemps();
        }
    }

}
