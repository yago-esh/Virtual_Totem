package Virtual_Totem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import Virtual_Totem.Client_VT;

class Client_VT {

	private Socket socketConexion;
	private PrintWriter out;
	private BufferedReader in;
	private Panel_VT Panel_VT;
	private boolean terminar;

	public Client_VT() {
		socketConexion = null;
		out = null;
		in = null;
		Panel_VT = null;
		terminar = false;
	}

	public void asociar(Panel_VT Panel_VT) {
		this.Panel_VT = Panel_VT;
	}

	public void ejecutar() throws IOException {
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
						Panel_VT.mostrarMensaje(lineaRecibir);
					}
				}
			} catch (IOException ex) {
				if (!terminar) {
					ex.printStackTrace();
					Panel_VT.mostrarMensaje(ex.getMessage());
				}
			}
			finally {
				Client_VT.this.terminar();
			}
		}
	}
}
