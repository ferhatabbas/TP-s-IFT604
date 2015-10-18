package Serveur.DATA;

import Serveur.Serveurs.ThreadDeReponse;
import Serveur.Serveurs.serveur;
import com.google.gson.Gson;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;



public class HockeyLiveService implements IEventClassListener {

	private Gson _jSonBuilder;
	
	public HockeyLiveService() {
		this._jSonBuilder = new Gson();
	}

	public void envoyerListeMatchs(ThreadDeReponse threadReponse)
			throws InterruptedException {
		List<String> matches =  new ArrayList<String>();
		serveur serveur = Serveur.Serveurs.serveur.getInstance();
		for(Match m :serveur.getMatchs().getMatchsCourants().values()){
			matches.add(m.toString());
		}
		String matchListToJson =  _jSonBuilder.toJson(matches);
		threadReponse.envoyerReponse(matchListToJson);
	}

	public void sendMatch(ThreadDeReponse responseWorker, String matchId)
			throws InterruptedException {
		serveur serveur = Serveur.Serveurs.serveur.getInstance();
		Map<String,Match> matches = serveur.getMatchs().getMatchsCourants();
		Match match = matches.get(matchId);
		String matchToJson;
		if(match!=null){
			matchToJson =  _jSonBuilder.toJson(match);
			match.addEventListener(this);
		}else{
			match =serveur.getMatchs().getListeMatchs().get(matchId);
			matchToJson =  "PARTIE_TERMINEE" + _jSonBuilder.toJson(match);
		}
		
		responseWorker.envoyerReponse(matchToJson);
//		Serveur serveur = Serveur.getInstance();
//		Map<String, Match> matches = serveur.getMatchs().getMatchsCourants();
//		Match match = matches.get(matchId);
//		
//		Message majMatch = new Message(TypeDeMessage.REPONSE, Reponse.DETAILS_MATCH);
//		majMatch.setMatch(match);
//		String matchToJson;
//		if (match != null) {
//			matchToJson = _jSonBuilder.toJson(majMatch);
//			match.addEventListener(this);
//		} else {
//			match = serveur.getMatchs().getListeMatchs().get(matchId);
//			matchToJson = "MATCH_ENDED" + _jSonBuilder.toJson(match);
//		}
//
//		responseWorker.envoyerReponse(matchToJson);
	}

	public void sendResponse(Object object, InetAddress host, int port) {
		try {
			System.out.println("Enssai d'envoi de reponse au client : "
					+ host.getHostAddress() + " - port : " + port);
			Gson jSonBuilder = new Gson();
			String jsonMsg = jSonBuilder.toJson(object);
			Socket socket = new Socket(host, port);
			OutputStream os = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(jsonMsg);
			oos.close();
			os.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Message getRequestMessage(String requete) {
		Message message = _jSonBuilder.fromJson(requete, Message.class);
		return message;
	}

	@Override
	public synchronized void traiterButEvent(EventObject e) {
//		MatchEvent event = (MatchEvent) e;
//		Map<String, Set<String>> hostsByMatch = Serveur.getInstance()
//				.getClientsParMatch();
//		if (hostsByMatch != null
//				&& hostsByMatch.get(event.getMatchId()) != null) {
//			for (String ip : hostsByMatch.get(event.getMatchId())) {
//				InetAddress ipAddress;
//				try {
//					Message majMatch = new Message(TypeDeMessage.REPONSE, Reponse.DETAILS_MATCH);			
//
//					Serveur serveur = Serveur.getInstance();
//					Map<String, Match> matches = serveur.getMatchs()
//							.getMatchsCourants();
//					Match match = matches.get(event.getMatchId());
//					ipAddress = InetAddress.getByName(ip);
//					majMatch.setMatch(match);
//
//					System.out.println("SEND EVENT GOAL TO "
//							+ ipAddress.getHostAddress());
//
//					this.sendResponse(majMatch, ipAddress, /*6790*/6792);
//				} catch (UnknownHostException e1) {
//					e1.printStackTrace();
//				}
//			}
//		}
		
		MatchEvent event = (MatchEvent)e;
		Map<String,Set<String>> hostsByMatch = serveur.getInstance().getClientsParMatch();
		if(hostsByMatch!=null && hostsByMatch.get(event.getMatchId())!=null){
			for (String ip : hostsByMatch.get(event.getMatchId())) {
				System.out.println("But de " + event.getButeur() + " " + event.getMatchId());
				Message m =  new Message(Message.TypeDeMessage.REPONSE, "GOALBut de " + event.getButeur() + "dans le match "+ event.getMatchId());
				m.setMatchId(event.getMatchId());
				InetAddress ipAddress;
				try {
					ipAddress = InetAddress.getByName(ip);
					System.out.println("SEND EVENT GOAL TO " + ipAddress.getHostAddress());
					this.sendResponse(m, ipAddress, 6549);
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
			}
		    
		}
	}

	@Override
	public synchronized void traiterPenaliteEvent(EventObject e) {
//		MatchEvent event = (MatchEvent) e;
//		Map<String, Set<String>> hostsByMatch = Serveur.getInstance()
//				.getClientsParMatch();
//		if (hostsByMatch != null
//				&& hostsByMatch.get(event.getMatchId()) != null) {
//			for (String ip : hostsByMatch.get(event.getMatchId())) {
//				InetAddress ipAddress;
//				try {
//					Serveur serveur = Serveur.getInstance();
//					Map<String, Match> matches = serveur.getMatchs()
//							.getMatchsCourants();
//					Message majMatch = new Message(TypeDeMessage.REPONSE, Reponse.DETAILS_MATCH);			
//				
//					Match match = matches.get(event.getMatchId());
//					majMatch.setMatch(match);
//					ipAddress = InetAddress.getByName(ip);
//
//					this.sendResponse(majMatch, ipAddress, 6790);
//				} catch (UnknownHostException e1) {
//					e1.printStackTrace();
//				}
//			}
//
//		}
		
		MatchEvent event = (MatchEvent)e;
		Map<String,Set<String>> hostsByMatch = serveur.getInstance().getClientsParMatch();
		if(hostsByMatch!=null && hostsByMatch.get(event.getMatchId())!=null){
			for (String ip : hostsByMatch.get(event.getMatchId())) {
				System.out.println(event.getPenalite() + " in match " + event.getMatchId());
				Message m =  new Message(Message.TypeDeMessage.REPONSE, "PENA"+event.getPenalite() + event.getMatchId());
				m.setMatchId(event.getMatchId());
				InetAddress ipAddress;
				try {
					ipAddress = InetAddress.getByName(ip);
					System.out.println("SEND EVENT PENALTY TO " + ipAddress.getHostAddress());
					this.sendResponse(m, ipAddress, 6549);
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
			}
		    
		}
	}

//	@Override
//	public void traiterTempsUpdateEvent(EventObject e) {
//		Semaphore sem = new Semaphore(1);
//		try {
//			sem.acquire();
//		} catch (InterruptedException e2) {
//			e2.printStackTrace();
//		}
//		MatchEvent event = (MatchEvent) e;
//		Map<String, Set<String>> hostsByMatch = Serveur.getInstance()
//				.getClientsParMatch();
//		if (hostsByMatch != null
//				&& hostsByMatch.get(event.getMatchId()) != null) {
//			for (String ip : hostsByMatch.get(event.getMatchId())) {
//				InetAddress ipAddress;
//				try {
//					Serveur serveur = Serveur.getInstance();
//					Map<String, Match> matches = serveur.getMatchs()
//							.getMatchsCourants();
//					Message majMatch = new Message(TypeDeMessage.REPONSE, Reponse.DETAILS_MATCH);			
//				
//					Match match = matches.get(event.getMatchId());
//					majMatch.setMatch(match);
//					ipAddress = InetAddress.getByName(ip);
//
//					this.sendResponse(majMatch, ipAddress, 6790);
//				} catch (UnknownHostException e1) {
//					e1.printStackTrace();
//				}
//			}
//
//		}
//		sem.release();
//	}
}
