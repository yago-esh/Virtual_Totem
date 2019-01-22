package Clients;

import javax.swing.ImageIcon;

import Virtual_Totem.Client_VT;
import Virtual_Totem.Window;

public class Client_Lda extends Generic_client {
	
	static final long serialVersionUID = 42L;

	public Client_Lda(Client_VT client, Window window) {
		super(client,window,"LDA");
		clientIcon.setIcon(new ImageIcon(Generic_client.class.getResource("/Img/lda.png")));
	}
}
