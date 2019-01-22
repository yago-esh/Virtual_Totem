package Clients;

import javax.swing.ImageIcon;

import Virtual_Totem.Client;
import Virtual_Totem.Window;

public class Client_Axa extends Generic_client {
	
	static final long serialVersionUID = 42L;
	private static String[] totemNames = {"Top","Bot"};

	public Client_Axa(Client client, Window window) {
		super(client,window,"AXA",totemNames);
		clientIcon.setIcon(new ImageIcon(Generic_client.class.getResource("/Img/axa.png")));
	}
}
