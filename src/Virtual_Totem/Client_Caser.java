package Virtual_Totem;

import javax.swing.ImageIcon;

public class Client_Caser extends Generic_client {
	
	static final long serialVersionUID = 42L;

	public Client_Caser(Client_VT client, Window_VT window) {
		super(client,window);
		clientIcon.setIcon(new ImageIcon(Generic_client.class.getResource("/Img/caser.png")));
	}
	
}
