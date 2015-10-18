package Serveur.DATA;

import java.util.EventObject;

/**
 * Created by User on 2015-10-16.
 */
public interface IEventClassListener {
    public void traiterButEvent(EventObject e);
    public void traiterPenaliteEvent(EventObject e);
   // public void traiterTempsUpdateEvent(EventObject e);
}
