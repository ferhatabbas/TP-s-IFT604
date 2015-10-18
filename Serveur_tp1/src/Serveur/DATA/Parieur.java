package Serveur.DATA;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2015-10-16.
 */
public class Parieur implements Serializable {

    private static final long serialVersionUID = 1L;
    private String _id;
    private Double _portefeuille;
    private List<Pari> _paris;

    public Parieur(String id) {
        this._id = id;
        this._portefeuille = new Double(0);
        this._paris = new ArrayList<Pari>();
    }

    public void ajouterPari(Pari pari) {
        _paris.add(pari);
    }

    public String getId() {
        return _id;
    }

    public Double getPortefeuille() {
        return _portefeuille;
    }

    public List<Pari> getParis() {
        return _paris;
    }

    public void setId(String id) {
        this._id = id;
    }

    public void setPortefeuille(Double portefeuille) {
        this._portefeuille = portefeuille;
    }

    public void setParis(ArrayList<Pari> paris) {
        this._paris = paris;
    }

}
