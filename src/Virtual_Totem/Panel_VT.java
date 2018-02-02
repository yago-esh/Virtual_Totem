package Virtual_Totem;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import Virtual_Totem.Client_VT;
import javax.swing.SwingUtilities;


public class Panel_VT extends JPanel {
	
	static final long serialVersionUID = 42L;
	private JButton wolf_bt;
	private boolean lobo_taken;
	
	public Panel_VT(Client_VT client) {
		
		client.associate(this);
		
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setForeground(Color.BLACK);
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		
		wolf_bt = new JButton("Take the Wolf");
		wolf_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lobo_taken=true;
				client.send("coger_lobo");
			}
		});
		wolf_bt.setBounds(74, 38, 150, 50);
		add(wolf_bt);
		
		JButton dragon_bt = new JButton("Take the dragon");
		dragon_bt.setBounds(74, 116, 150, 50);
		add(dragon_bt);

	}
	
	public void mostrarMensaje(final String mensaje) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if(!lobo_taken) wolf_bt.setEnabled(false);
			}
		});
	}
}
