package Serveur.DATA;

import java.util.Date;

/**
 * Created by Sylvain on 2015-09-28.
 */
public class TestData {


    static public void main(String[] args){


       Thread MAJchrono = new Thread();
       Thread MAJevt = new Thread();

        System.out.println("Start Test");
        ListMatch Liste1 = new ListMatch();

        Liste1.affiche(Liste1);





        while(true){
           Liste1.fonctionTestChrono();


        }

      /*  try {
            long start = System.currentTimeMillis( );
            System.out.println(new Date() + "\n");

            Thread.sleep(5*60*10);
            System.out.println(new Date( ) + "\n");
            long end = System.currentTimeMillis( );
            long diff = end - start;

            while(diff<30000){
                System.out.println(new Date( ) + "\n");
                Thread.sleep(5 * 60 * 10);
                end = System.currentTimeMillis( );
                diff = end - start;
                System.out.println(diff);
            }
            System.out.println("Difference is : " + diff);
        } catch (Exception e) {
            System.out.println("Got an exception!");
        }*/

    }

    }

