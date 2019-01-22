package Clients;

import javax.swing.ImageIcon;

import Virtual_Totem.Client;
import Virtual_Totem.Window;

public class Client_Mapfre extends Generic_client {
	
	static final long serialVersionUID = 42L;

	public Client_Mapfre(Client client, Window window) {
		super(client,window,"Mapfre");
		clientIcon.setIcon(new ImageIcon(Generic_client.class.getResource("/Img/mapfre.png")));
	}
}
