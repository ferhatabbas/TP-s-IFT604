package Serveur.DATA;

import java.io.Serializable;

/**
 * Created by User on 2015-10-16.
 */
public class Pari implements Serializable {

    private static final long serialVersionUID = 1L;
    private String _id;
    private Match _match;
    private Parieur _parieur;
    private String _vainqueurAttendu;
    private Double _montant;

    public Pari(Match match, Parieur parieur, String vainqueurAttendu,
                Double montant) {
        this._match = match;
        this._parieur = parieur;
        this._vainqueurAttendu = vainqueurAttendu;
        this._montant = montant;
        initId();
    }

    public void ajouterMontant(Double montant) {
        this._montant += montant;
    }

    private void initId() {
        this._id = this._match.getId() + this._parieur.getId();
    }

    public Match getMatch() {
        return _match;
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public void setMatch(Match match) {
        this._match = match;
    }

    public Parieur getParieur() {
        return _parieur;
    }

    public String getVainqueurAttendu() {
        return _vainqueurAttendu;
    }

    public Double getMontant() {
        return _montant;
    }

    public void setParieur(Parieur parieur) {
        this._parieur = parieur;
    }

    public void setVainqueurAttendu(String vainqueurAttendu) {
        this._vainqueurAttendu = vainqueurAttendu;
    }

    public void setMontant(Double montant) {
        this._montant = montant;
    }

    public Double getMontantGagne() {
        if (_match.getVinqueur().equalsIgnoreCase(_vainqueurAttendu)) {
            Double total = Paris.getInstance().getParisSurMatch(this._match);
            Double totalEquipe = Paris.getInstance().getParisSurMatchPourEquipe(
                    this._match, this._vainqueurAttendu);

            return (total * 0.75) * (this._montant / totalEquipe);
        } else {
            return 0.0 - _montant;
        }
    }
}
