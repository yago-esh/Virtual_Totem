package Clients;

import javax.swing.ImageIcon;

import Virtual_Totem.Client_VT;
import Virtual_Totem.Window_VT;

public class Client_Axa extends Generic_client {
	
	static final long serialVersionUID = 42L;

	public Client_Axa(Client_VT client, Window_VT window) {
		super(client,window,"AXA");
		clientIcon.setIcon(new ImageIcon(Generic_client.class.getResource("/Img/axa.png")));
	}
}
