package Virtual_Totem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

import Clients.Generic_client;
import Virtual_Totem.Client_VT;

public class Client_VT {

	private Socket socketConexion;
	private PrintWriter out;
	private BufferedReader in;
	private Generic_client client;
	private int version;
	private boolean terminar,check_version,parse_id;
	private String myId;

	public Client_VT() {
		socketConexion = null;
		out = null;
		in = null;
		client = null;
		terminar = false;
		check_version=false;
		parse_id=false;
		version=187;
	}

	public void associate(Generic_client client) {
		this.client = client;
	}

	public void execute() throws IOException {
		//------------------------------Develop mode--------------------------------
			socketConexion = new Socket("localhost", 2029);
//			socketConexion = new Socket("10.1.2.34", 2029);
		//---------------------------------------------------------------------------
		// Crear socket cliente y establecer conexion
		
//		socketConexion = new Socket("10.0.1.95", 2029);

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
		this.new CheckConexiones();
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
										"Su versión de Virtual Totem es incompatible. Por favor, actualícela.",
										"ERROR", JOptionPane.ERROR_MESSAGE);
								System.exit(-1);
								salir=true;
							}
						}
						else if(!parse_id){
							parse_id=true;
							myId = lineaRecibir;
							System.out.println("Mi Id es: "+myId);
							client.setId(myId);
						}
						client.ParseMsg(lineaRecibir);
					}
				}
			} catch (IOException ex) {
				if (!terminar) {
					JOptionPane.showMessageDialog(null,
							"Se ha perdido la conexión con el servidor",
							"ERROR", JOptionPane.ERROR_MESSAGE);
					System.exit(-1);
				}
			}
			finally {
				Client_VT.this.terminar();
			}
		}
	}
	
	private class CheckConexiones{
    	
    	public CheckConexiones() {
            new Thread() {
                public void run() {
                	while(true) {
                		try {
							Thread.sleep(60000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
                		System.out.println("COMPROBANDO CONEXIONES");
                		client.send("ACK");
                	}
                }
     
            }.start();
        }
    }

	
	public String getMyId() {
		return myId;
	}

	public void setMyId(String myId) {
		this.myId = myId;
	}
	
	
}
