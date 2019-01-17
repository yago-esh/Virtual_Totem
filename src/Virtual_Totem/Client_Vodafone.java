package Virtual_Totem;

import javax.swing.ImageIcon;

public class Client_Vodafone extends Generic_client {
	
	static final long serialVersionUID = 42L;

	public Client_Vodafone(Client_VT client, Window_VT window) {
		super(client,window,"Vodafone");
		clientIcon.setIcon(new ImageIcon(Generic_client.class.getResource("/Img/Vodafone4.png")));
	}
}
