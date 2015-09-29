package Serveur.DATA;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by Sylvain on 2015-09-26.
 */
public class ListMatch {
    public ArrayList<Match> listeDesMatch = new ArrayList<>();


    public ListMatch(){
        initMatch();
    }

    public ArrayList<Match> initMatch(){
        Match PremMatch = new Match(1,"Montreal","Toronto",0,0,0,"");
        Match DeuxMatch = new Match(2,"Sherbrooke","Quebec",0,0,0,"");

        listeDesMatch.add(PremMatch);
        listeDesMatch.add(DeuxMatch);
        return listeDesMatch;
    }

    public void affiche(ListMatch laliste){
        for(Iterator<Match> i = laliste.listeDesMatch.iterator(); i.hasNext(); ) {
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



    }





}
