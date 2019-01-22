package Clients;

import javax.swing.ImageIcon;

import Virtual_Totem.Client;
import Virtual_Totem.Window;

public class Client_Vodafone extends Generic_client {
	
	static final long serialVersionUID = 42L;
	private static String[] totemNames = {"Lobo","Dragón"};
	
	public Client_Vodafone(Client client, Window window) {
		super(client,window,"Vodafone",totemNames);
		
		clientIcon.setIcon(new ImageIcon(Generic_client.class.getResource("/Img/vodafone.png")));
	}
}
