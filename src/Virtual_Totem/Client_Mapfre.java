package Virtual_Totem;

import javax.swing.ImageIcon;

public class Client_Mapfre extends Generic_client {
	
	static final long serialVersionUID = 42L;

	public Client_Mapfre(Client_VT client, Window_VT window) {
		super(client,window,"Mapfre");
		clientIcon.setIcon(new ImageIcon(Generic_client.class.getResource("/Img/mapfre.png")));
	}
}
