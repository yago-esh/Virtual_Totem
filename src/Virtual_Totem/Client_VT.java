package Virtual_Totem;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Virtual_Totem.Client_VT;

class Client_VT {

	private Socket socketConexion;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Panel_VT Panel_VT;
	private boolean finish;
	private Totem_VT totem;

	public Client_VT() {
		socketConexion = null;
		out = null;
		in = null;
		Panel_VT = null;
		finish = false;
		totem = new Totem_VT();
	}

	public void associate(Panel_VT Panel_VT) {
		this.Panel_VT = Panel_VT;
	}

	public void execute() throws IOException {
		// Create socket conection and establishes the connection
		socketConexion = new Socket("localhost", 2029);

		// Get outbound and inbound flows
		OutputStream outStream = socketConexion.getOutputStream();
		InputStream inStream = socketConexion.getInputStream();

		// Create outbound and inbound flows
		out = new ObjectOutputStream(outStream);
		in = new ObjectInputStream(inStream);

		// Recibir en un hilo independiente
		new ReceiveThread(in).start();
	}

	public void finish() {
		finish = true;

		// Close flwos
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			out = null;
		}
		if (in != null) {
			try {
				in.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			in = null;
		}

		// Close socket
		if (socketConexion != null) {
			try {
				socketConexion.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			socketConexion = null;
		}
	}

	public void send(String msg) {
		try {
			out.writeObject(msg);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void refresh(Totem_VT totem) {
		if(totem.isWolf()) {
			
		}
	}
	private class ReceiveThread extends Thread {

		private ObjectInputStream in;

		private ReceiveThread(ObjectInputStream in) {
			this.in = in;
		}

		public void run() {
			try {
				Totem_VT totem_rec;
				while (true) {
					totem_rec = (Totem_VT)in.readObject();
					totem.copy_Totem(totem_rec);
									
				}
			} catch (IOException | ClassNotFoundException ex) {
				if (!finish) {
					ex.printStackTrace();
				}
			}
		}
	}
}
