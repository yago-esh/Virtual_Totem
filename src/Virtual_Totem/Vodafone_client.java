package Virtual_Totem;

import javax.swing.ImageIcon;

public class Vodafone_client extends Generic_client {
	

	static final long serialVersionUID = 42L;

	public Vodafone_client(Client_VT client, Window_VT window) {
		super(client,window);
		clientIcon.setIcon(new ImageIcon(Generic_client.class.getResource("/Img/Vodafone4.png")));
	}
	
}
