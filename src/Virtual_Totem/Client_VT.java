package Virtual_Totem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

import Virtual_Totem.Client_VT;

class Client_VT {

	private Socket socketConexion;
	private PrintWriter out;
	private BufferedReader in;
	private Panel_VT Panel_VT;
	private int version;
	private boolean terminar,check_version;

	public Client_VT() {
		socketConexion = null;
		out = null;
		in = null;
		Panel_VT = null;
		terminar = false;
		check_version=false;
		version=135;
	}

	public void associate(Panel_VT Panel_VT) {
		this.Panel_VT = Panel_VT;
	}

	public void execute() throws IOException {
		// Crear socket cliente y establecer conexion
		socketConexion = new Socket("10.1.2.34", 2029);

		System.out.println("Cliente> Establecida conexion");

		// Obtener flujos de salida y entrada
		OutputStream outStream = socketConexion.getOutputStream();
		InputStream inStream = socketConexion.getInputStream();

		// Crear flujos de escritura y lectura
		out = new PrintWriter(outStream);
		System.out.println("Cliente> Obtenido flujo de escritura");
		in = new BufferedReader(new InputStreamReader(inStream));
		System.out.println("Cliente> Obtenido flujo de lectura");

		// Recibir en un hilo independiente
		new HiloRecibir(in).start();
	}

	public void terminar() {
		terminar = true;

		// Cerrar flujos
		// Primero OUT, si no se queda colgado
		if (out != null) {
			out.close();
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

		// Cerrar socket de la conexion
		if (socketConexion != null) {
			try {
				socketConexion.close();
				System.out.println("Cliente> Fin de conexion");
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			socketConexion = null;
		}
	}

	public void enviar(String linea) {
		out.println(linea);
		out.flush();
	}

	private class HiloRecibir extends Thread {

		private BufferedReader in;

		private HiloRecibir(BufferedReader in) {
			this.in = in;
		}

		public void run() {
			try {
				boolean salir = false;
				while (!salir) {
					String lineaRecibir = in.readLine();
					if (lineaRecibir == null) {
						salir = true;
						Client_VT.this.terminar();
						System.exit(0);
					} else {
						if(!check_version) {
							check_version=true;
							if(Integer.parseInt(lineaRecibir)>version) {
								JOptionPane.showMessageDialog(null,
										"Su versi�n de Virtual Totem es incompatible. Por favor, actualicela.",
										"ERROR", JOptionPane.ERROR_MESSAGE);
								System.exit(-1);
								salir=true;
							}
						}
						Panel_VT.showMsg(lineaRecibir);
					}
				}
			} catch (IOException ex) {
				if (!terminar) {
					ex.printStackTrace();
					Panel_VT.showMsg(ex.getMessage());
				}
			}
			finally {
				Client_VT.this.terminar();
			}
		}
	}
}
