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
	private JButton Lobo_bt;
	private boolean lobo_taken;
	
	public Panel_VT(Client_VT cliente) {
		
		cliente.asociar(this);
		
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setForeground(Color.BLACK);
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		
		Lobo_bt = new JButton("Coger el Lobo");
		Lobo_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lobo_taken=true;
				cliente.enviar("coger_lobo");
			}
		});
		Lobo_bt.setBounds(74, 38, 150, 50);
		add(Lobo_bt);
		
		JButton Dragon_bt = new JButton("Coger el Dragon");
		Dragon_bt.setBounds(74, 116, 150, 50);
		add(Dragon_bt);

	}
	
	public void mostrarMensaje(final String mensaje) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if(!lobo_taken) Lobo_bt.setEnabled(false);
			}
		});
	}
}
