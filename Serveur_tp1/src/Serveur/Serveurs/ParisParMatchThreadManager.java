package Serveur.Serveurs;

import Serveur.DATA.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;

/***
 * 
 * @author SAID ABED 10 004 577
 * @author LUCIEN BENIE 09 135 982
 * @author GABRIEL COTE ST-LOUIS 11 066 062
 *
 */

public class ParisParMatchThreadManager extends Thread {
	private Match _match;
	private Set<Pari> _paris;
	private List<Message> _requetes;
	private Map<String, InetAddress> _hotes;
	private int _port;

	public ParisParMatchThreadManager(Match match, Set<Pari> paris) {
		this._match = match;
		this._paris = paris;
		this._port = 6549;
		this._hotes = new HashMap<String, InetAddress>();
		_requetes = new ArrayList<Message>();
		this.start();
	}

	public void run() {
		while (!_match.partieTerminee()) {
			if (!_requetes.isEmpty()) {
				process(_requetes.remove(0));
			}
		}
		// Envoi des resultats des paris aux parieurs
		acheminementDesResultatsAuxParieurs();
	}

	private void acheminementDesResultatsAuxParieurs() {
		for (Pari pari : _paris) {
			Double gain = 5.0;
			if (pari.getVainqueurAttendu().equals(_match.getVinqueur())) {
				Message messageSucces = new Message(Message.TypeDeMessage.REPONSE,
						ServeurApp.Reponse.PARI_GAGNE);
				messageSucces.setMontantGagne(gain);
				messageSucces.setContenu(_match.getId());
				this.envoiReponse(messageSucces, _hotes.get(pari.getId()), 6791);
			} else {
				Message messagePerte = new Message(Message.TypeDeMessage.REPONSE,
						ServeurApp.Reponse.PARI_PERDU);
				messagePerte.setMontantGagne(Double.valueOf(0));
				this.envoiReponse(messagePerte, _hotes.get(pari.getId()), 6792);
			}
		}
	}

	public void add(Message message) {
		this._requetes.add(message);
	}

	private void process(Message message) {
		ServeurApp.Requete requete = message.getRequete();
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = jsonParser.parse(message.getContenu())
				.getAsJsonObject();
		String requestType = jo.get("typeRequete").getAsString();
		switch (requestType) {
		case "lancerPari":
			miser(jo, message.getHost());
			break;
		default:
		}
	}

	private void miser(JsonObject jo, InetAddress hote) {
		Parieur parieur = Paris.getInstance().getParieur(
				jo.get("parieur").getAsString());
		if (parieur == null) {
			parieur = new Parieur(jo.get("parieur").getAsString());
			Paris.getInstance().addParieur(parieur);
		}
		Pari p = new Pari(this._match, parieur, jo.get("vainqueurAttendu")
				.getAsString(), jo.get("montant").getAsDouble());
		if (this._match.getPeriodeMatch() < 3
				&& this._match.getPeriodeMatch() > 0) {
			this._paris.add(p);
			this._hotes.put(p.getId(), hote);
			Message confirmation = new Message(Message.TypeDeMessage.REPONSE,
					ServeurApp.Reponse.CONFIRMATION_PARI);
			this.envoiReponse(confirmation, hote, _port);
			System.out.println("Le serveur envoie une confirmation de pari :"
					+ hote);
		} else {
			Message erreur = new Message(Message.TypeDeMessage.REPONSE,
					ServeurApp.Reponse.ERREUR_PARI);
			this.envoiReponse(erreur, hote, _port);
		}
	}

	public void envoiReponse(Object objet, InetAddress hote, int port) {
		try {
			Gson jSonBuilder = new Gson();
			String message = jSonBuilder.toJson(objet);
			Socket socket = new Socket(hote, port);
			OutputStream os = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(message);
			oos.close();
			os.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map<String, InetAddress> getHotes() {
		return _hotes;
	}
}
