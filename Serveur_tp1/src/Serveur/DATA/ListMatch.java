package Serveur.DATA;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Sylvain on 2015-09-26.
 */
public class ListMatch implements Serializable {
    //public ArrayList<Match> listeDesMatch = new ArrayList<>();
    private static ListMatch _instance = null;
    private static final long serialVersionUID = 1L;
    private Map<String, Match> _listeMatchs;
    private final Integer NBRE_MAX_MATCHS;

    public ListMatch(){
        this._listeMatchs = new HashMap<String, Match>();
        this.NBRE_MAX_MATCHS = 10; // 10 Matchs par jour
        this.initialiserListeDesMatchs();
    }
    public static synchronized ListMatch getInstance() {
        if (_instance == null) {
            _instance = new ListMatch();
        }
        return _instance;
    }

    public Map<String, Match> getMatchsCourants() {
        Map<String, Match> matchsCourants = new HashMap<String, Match>();
        for (Match match : _listeMatchs.values()) {
            if (!match.partieTerminee()) {
                matchsCourants.put(match.getId(), match);
            }
        }
        return matchsCourants;
    }
    public Map<String, Match> getTousLesMatchs() {
        Map<String, Match> tousLesMatchs = new HashMap<String, Match>();
        for (Match match : _listeMatchs.values()) {
            tousLesMatchs.put(match.getId(), match);
        }
        return tousLesMatchs;
    }
  /*  public ArrayList<Match> initMatch(){
        Match PremMatch = new Match(1,"Montreal","Toronto",0,0,0,"");
        Match DeuxMatch = new Match(2,"Sherbrooke","Quebec",0,0,0,"");

        listeDesMatch.add(PremMatch);
        listeDesMatch.add(DeuxMatch);
        return listeDesMatch;
    }

    public void affiche(ListMatch laliste){
        for(Iterator<Match> i = laliste._listeMatchs.iterator(); i.hasNext(); ) {
            Match item = i.next();
            item.affiche(item);
            System.out.println("Fin AFFICHAGE Match " + item);
        }

    }

    public void miseAjour(ListMatch laliste) {
        for (Iterator<Match> i = laliste.listeDesMatch.iterator(); i.hasNext(); ) {
            Match item = i.next();
            item.miseAjourChrono(item);
            System.out.println("Fin Mise a jour Match " + item);
        }

    }

    public void fonctionTestChrono(){
        for (Iterator<Match> i = listeDesMatch.iterator(); i.hasNext(); ){
            Match item = i.next();
            long time = item.getTime();
            long end = System.currentTimeMillis();
            long diff = end - time;

            while(diff<30000){
                System.out.println(new Date( ) + "\n");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                end = System.currentTimeMillis( );
                diff = end - time;
                System.out.println(diff);
            }
            item.miseAjourChrono(item);
            item.setTime(end);
            System.out.println("chrono");
            System.out.println(item.chrono);

        }

*/


    public void initialiserListeDesMatchs() {

        List<String> matchs = new ArrayList<String>();
        matchs.add("MONTREAL;BOSTON;2014-10-03T21:30:00Z");
        matchs.add("NEW JERSEY;BUFFALO;2014-10-04T18:30:00Z");
        matchs.add("PHILADELPHIA;TORONTO;2014-11-25T19:15:00Z");
        matchs.add("SAN JOSE;EDMONTON;2014-10-23T20:30:00Z");
        matchs.add("DALLAS;WINNIPEG;2014-10-24T20:30:00Z");
        matchs.add("VANCOUVER;CAROLINA;2014-10-26T19:30:00Z");
        matchs.add("ARIZONA;CALGARY;2014-10-27T19:30:00Z");
        matchs.add("NEW YORK R;PITTSBURGH;2014-10-27T19:30:00Z");
        matchs.add("DETROIT;TAMPA BAY;2014-10-27T19:30:00Z");
        matchs.add("COLUMBUS;LOS ANGELES;2014-10-27T19:30:00Z");

        Calendar cal;
        Date date;

        for (String m : matchs) {
            String[] attributes = m.split(";");
            Equipe local = new Equipe(attributes[0]);
            Equipe visiteur = new Equipe(attributes[1]);
            cal = javax.xml.bind.DatatypeConverter.parseDateTime(attributes[2]);
            date = cal.getTime();

            Match match = new Match(local, visiteur, date);
            this._listeMatchs.put(match.getId(), match);
        }
    }

    public String toString() {
        StringBuilder match = new StringBuilder();
        match.append("\n");
        for (Match m : this._listeMatchs.values()) {
            match.append(m + "\n");
        }
        return match.toString();
    }


    public Map<String, Match> getListeMatchs() {
        return _listeMatchs;
    }

    public void setListeMatchs(HashMap<String, Match> listeMatchs) {
        this._listeMatchs = listeMatchs;
    }



}
