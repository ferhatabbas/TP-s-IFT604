package Serveur.ui;

import Serveur.DATA.Match;
import Serveur.DATA.Message;
import Serveur.Serveurs.ClientSendThread;
import Serveur.Serveurs.ServeurApp;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Set;

/***
 * 
 * @author SAID ABED 10 004 577
 * @author LUCIEN BENIE 09 135 982
 * @author GABRIEL COTE ST-LOUIS 11 066 062
 *
 */

public class ClientUI extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	Gson _jSonBuilder;
	ServeurApp.Requete _requete;
	Set<String> indexes = null;
	String _matchEnCours = null;
	Match _match = null;
	int _nombreParis = 0;
	InetAddress _ip = null;
	public static Socket _socket = null;

	JList _listeDesMatchs = null;
	JButton _updateButton = null;
	JButton _parierButton = null;
	JButton _suivreMatch = null;
	JTextArea _detailMatch = null;
	JPanel _listeMatchsPanel = null;
	JPanel _detailsMatchPanel = null;
	JPanel _boutonsPanel = null;
	JPanel _resultatsPanel = null;
	JLabel _confirmationPari = null;
	JLabel _introduction = null;

	public ClientUI() {
		try {
			_ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		_jSonBuilder = new Gson();

		initClientUI();

		_suivreMatch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_suivreMatch.hide();
				_updateButton.show();
				_parierButton.show();
				_confirmationPari.show();
				_introduction.hide();

				int i = _listeDesMatchs.getSelectedIndex();
				Object[] ed = indexes.toArray();
				_matchEnCours = (String) ed[i];
				Message requestMessage = requestMatch(_matchEnCours);

				String request = _jSonBuilder.toJson(requestMessage);
				ClientSendThread sendThread = new ClientSendThread(
						request, _ip, 6789);
				sendThread.start();
				_listeDesMatchs.disable();

			}
		});

		_updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Message requestMessage = requestMatch(_matchEnCours);

				String request = _jSonBuilder.toJson(requestMessage);
				ClientSendThread sendThread = new ClientSendThread(
						request, _ip, 6789);
				sendThread.start();
			}
		});

		_parierButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Message requestMessage = requestPari(_matchEnCours);

				String request = _jSonBuilder.toJson(requestMessage);
				ClientSendThread sendThread = new ClientSendThread(
						request, _ip, 6789);
				sendThread.start();
			}
		});

		_boutonsPanel.add(_suivreMatch);
		_resultatsPanel.add(_introduction);
		_detailsMatchPanel.add(_detailMatch);
		_boutonsPanel.add(_updateButton);
		_boutonsPanel.add(_parierButton);
		_resultatsPanel.add(_confirmationPari);
	}

	private void initClientUI() {
		setTitle("LNH : Suivi des matchs en direct");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		_listeMatchsPanel = new JPanel();
		_detailsMatchPanel = new JPanel();
		_boutonsPanel = new JPanel();
		_resultatsPanel = new JPanel();
		_confirmationPari = new JLabel("Vous avez fait " + _nombreParis
				+ " paris");
		_introduction = new JLabel(
				"Veuillez selectionner un match et cliquz sur Suivre Match pour le regarder en direct!!");

		this.setLayout(new BorderLayout());
		this.getContentPane().add(_detailsMatchPanel, BorderLayout.CENTER);
		this.getContentPane().add(_boutonsPanel, BorderLayout.NORTH);
		this.getContentPane().add(_listeMatchsPanel, BorderLayout.WEST);
		this.getContentPane().add(_resultatsPanel, BorderLayout.SOUTH);

		_detailMatch = new JTextArea();
		_detailMatch.setEditable(false);

		_suivreMatch = new JButton("Suivre match...");
		_updateButton = new JButton("Mise a jour");
		_parierButton = new JButton("Pariez");
		_updateButton.hide();
		_parierButton.hide();
		_confirmationPari.hide();
	}

	public Message requestMatchList() {
		_requete = ServeurApp.Requete.LISTE_DES_MATCHS;
		return new Message(Message.TypeDeMessage.REQUETE, _requete);
	}

	public Message requestMatch(String matchId) {
		_requete = ServeurApp.Requete.MATCH;
		Message requestMessage = new Message(Message.TypeDeMessage.REQUETE, _requete);
		requestMessage.setMatchId(matchId);
		return requestMessage;
	}

	public Message requestPari(String matchId) {
		_requete = ServeurApp.Requete.PARIS;
		Message requestMessage = new Message(Message.TypeDeMessage.REQUETE, _requete);
		requestMessage.setMatchId(matchId);
		JsonObject objetJson = new JsonObject();
		objetJson.addProperty("typeRequete", "lancerPari");
		objetJson.addProperty("parieur", _ip.toString());
		objetJson.addProperty("vainqueurAttendu", _match.getLocal().toString());
		objetJson.addProperty("montant", 50);
		requestMessage.setContenu(objetJson.toString());
		return requestMessage;
	}

	@Override
	public void run() {
		try {
			Message requestMessage = this.requestMatchList();

			String request = _jSonBuilder.toJson(requestMessage);
			ClientSendThread sendThread = new ClientSendThread(request,
					_ip, 6789);
			sendThread.start();

			ServerSocket welcomeSocket = new ServerSocket(6790);

			while (true) {
				
				Socket connectionSocket = welcomeSocket.accept();

				InputStream is = connectionSocket.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(is);
				String readObject = (String) ois.readObject();
				afficherDonnees(readObject);

				is.close();
				ois.close();
				connectionSocket.close();

			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void afficherDonnees(String readObject) {
		if (readObject == null)
			return;
		Message message = _jSonBuilder.fromJson(readObject, Message.class);
		if (message != null) {
			if (message.getReponse() == ServeurApp.Reponse.CONFIRMATION_PARI) {
				_nombreParis++;
				_confirmationPari.setText("Vous avez fait " + _nombreParis
						+ " paris");
			} else if (message.getReponse() == ServeurApp.Reponse.PARI_PERDU) {
				_confirmationPari.setForeground(Color.RED);
				_confirmationPari
						.setText("Desole vous n'avez pas gagne le pari");
			} else if (message.getReponse() == ServeurApp.Reponse.PARI_GAGNE) {
				_confirmationPari.setForeground(Color.GREEN);
				_confirmationPari.setText("Felicitations vous venez de gagner "
						+ message.getMontantGagne());
			} else if (message.getReponse() == ServeurApp.Reponse.ERREUR_PARI) {
				_confirmationPari
						.setText("Vous ne pouvez pas parier a la 3e periode d'un match!!! ");
			} else if (message.getReponse() == ServeurApp.Reponse.DETAILS_MATCH) {
				_match = message.getMatch();
				if (_match != null)
					_detailMatch.setText(_match.getDetailsMatch());

			}
		}
		if (_requete.equals(ServeurApp.Requete.LISTE_DES_MATCHS)) {
			java.lang.reflect.Type collectionType = new TypeToken<Map<String, Match>>() {
			}.getType();

			Map<String, Match> matchs = _jSonBuilder.fromJson(readObject,
					collectionType);
			_listeDesMatchs = new JList(matchs.entrySet().toArray());
			indexes = matchs.keySet();
			_listeDesMatchs
					.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			_listeDesMatchs.setLayoutOrientation(JList.HORIZONTAL_WRAP);
			_listeDesMatchs.setVisibleRowCount(-1);
			JScrollPane listScroller = new JScrollPane(_listeDesMatchs);
			listScroller.setPreferredSize(new Dimension(250, 80));
			_listeMatchsPanel.add(_listeDesMatchs);
		}
		repaint();
		revalidate();
	}
}
