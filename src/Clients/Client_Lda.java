package Clients;

import javax.swing.ImageIcon;

import Virtual_Totem.Client;
import Virtual_Totem.Window;

public class Client_Lda extends Generic_client {
	
	static final long serialVersionUID = 42L;
	private static String[] totemNames = {"Top","Bot"};

	public Client_Lda(Client client, Window window) {
		super(client,window,"LDA",totemNames);
		clientIcon.setIcon(new ImageIcon(Generic_client.class.getResource("/Img/lda.png")));
	}
}
