package Serveur.Serveurs;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/***
 * 
 * @author SAID ABED 10 004 577
 * @author LUCIEN BENIE 09 135 982
 * @author GABRIEL COTE ST-LOUIS 11 066 062
 *
 */

public class ClientSendThread extends Thread {
	InetAddress _IPAddressDest;
	int _port;
	Object _objet;

	public ClientSendThread(Object objet, InetAddress hote, int port) {
		this._IPAddressDest = hote;
		this._port = port;
		this._objet = objet;
	}

	public void run() {
		try {
			Socket socket = new Socket(_IPAddressDest, _port);
			OutputStream os = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(_objet);
			oos.close();
			os.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public InetAddress getIPAddressDest() {
		return _IPAddressDest;
	}

	public int getPort() {
		return _port;
	}

	public Object getObject() {
		return _objet;
	}

	public void setIPAddressDest(InetAddress iPAddressDest) {
		_IPAddressDest = iPAddressDest;
	}

	public void setPort(int port) {
		this._port = port;
	}

	public void setObject(Object objet) {
		this._objet = objet;
	}
}
