package Clients;

import javax.swing.ImageIcon;

import Virtual_Totem.Client_VT;
import Virtual_Totem.Window;

public class Client_Multiasistencia extends Generic_client {
	
	static final long serialVersionUID = 42L;

	public Client_Multiasistencia(Client_VT client, Window window) {
		super(client,window,"Multiasistencia");
		clientIcon.setIcon(new ImageIcon(Generic_client.class.getResource("/Img/multiasistencia.png")));
	}
}
