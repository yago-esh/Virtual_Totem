package Clients;

import javax.swing.ImageIcon;

import Virtual_Totem.Client;
import Virtual_Totem.Window;

public class Client_Caser extends Generic_client {
	
	static final long serialVersionUID = 42L;
	private static String[] totemNames = {"Top","Bot"};

	public Client_Caser(Client client, Window window) {
		super(client,window,"Caser",totemNames);
		clientIcon.setIcon(new ImageIcon(Generic_client.class.getResource("/Img/caser.png")));
	}
	
}
