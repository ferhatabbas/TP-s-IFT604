package Serveur.Serveurs;


import Serveur.DATA.HockeyLiveService;
import Serveur.DATA.Message;
import Serveur.DATA.Paris;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

public class ThreadDeReponse implements Runnable, Callable<String> {
	private HockeyLiveService _hockeyLiveService;

	private String _requeteClient;
	private Socket _socketClient;
	private InetAddress _host;
	private int _port;

	public ThreadDeReponse(String requeteClient, Socket socketClient) {
		this._requeteClient = requeteClient;
		this._socketClient = socketClient;
		this._host = socketClient.getInetAddress(); // On recupere l'adress ip
													// du client
		this._port = 7777; // Le client recoit les reponses sur le port 6790
		this._hockeyLiveService = new HockeyLiveService();
	}

	public void executerRequete(Message requete) throws InterruptedException {

		if (requete.getRequete().equals(ServeurApp.Requete.LISTE_DES_MATCHS)) {
			_hockeyLiveService.envoyerListeMatchs(this);
		}
		if (requete.getRequete().equals(ServeurApp.Requete.MATCH)) {
			envoyerMiseAJourAuxClients(requete);
			_hockeyLiveService.sendMatch(this, requete.getMatchId());
		}
		if (requete.getRequete().equals(ServeurApp.Requete.PARIS)) {
			requete.setHost(_host);
			Paris.getInstance().addMessage(requete);
		}
	}

	private void envoyerMiseAJourAuxClients(Message request) {
		if (!serveur.getInstance().getClientsParMatch()
				.containsKey(request.getMatchId())) {
			Set<String> l = new HashSet<String>();
			l.add(_host.getHostAddress());
			serveur.getInstance().getClientsParMatch()
					.put(request.getMatchId(), l);
		} else {
			serveur.getInstance().getClientsParMatch()
					.get(request.getMatchId()).add(_host.getHostAddress());
		}
	}

	public void repondreRequete(String requete) throws InterruptedException {
		Message messageRequete = _hockeyLiveService.getRequestMessage(requete);
		this.executerRequete(messageRequete);
	}

	public void envoyerReponse(Object objet) {
		try {
			Socket socket = new Socket(_host, _port);
			OutputStream os = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(objet);
			oos.close();
			os.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			repondreRequete(_requeteClient);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String call() throws Exception {
		run();
		return null;
	}
}
