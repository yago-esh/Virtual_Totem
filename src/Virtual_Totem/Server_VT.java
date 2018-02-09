package Virtual_Totem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


import Virtual_Totem.Server_VT;

class Server_VT extends Thread{

    public static void main(String[] args) {
        Server_VT servidor = new Server_VT();
        servidor.ejecutar();
    }

    private ListaConexiones listaConexiones;
    private ServerSocket socketServidor;
    private Socket socketConexion;
    private String linea;
    private boolean lobo_taken;
	private boolean dragon_taken;

    public Server_VT() {
        listaConexiones = new ListaConexiones();
    }

    private void ejecutar() {
    	try {
			socketServidor = new ServerSocket(2029);
			this.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    
    public void run(){
    	 try {
             // Crear socket servidor
             while (true) {
                 System.out.println("Servidor> Esperando conexion...");

                 // Esperar conexion del cliente
                 socketConexion = socketServidor.accept();

                 System.out.println("Servidor> Recibida conexion de "
                         + socketConexion.getInetAddress().getHostAddress() + ":"
                         + socketConexion.getPort());

                 // Anadir conexion a la lista
                 listaConexiones.anadir(socketConexion);

                 
                 //         de una clase interna CON AMBITO DE MIEMBRO
                 //         llamada HiloConexion
                 // ------------------------------------------------------------
                 this.new HiloConexiones();
                 // ------------------------------------------------------------
             }
         } catch (IOException ex) {
             ex.printStackTrace();
         }
    }

    private class ListaConexiones {

        private List<Socket> listaConexiones;

        private ListaConexiones() {
            listaConexiones = new ArrayList<Socket>();
        }

        private synchronized void anadir(Socket socketConexion) {
            listaConexiones.add(socketConexion);
        }

        private synchronized void eliminar(Socket socketConexion) {
            listaConexiones.remove(socketConexion);
        }
        
        private synchronized void change_totem(String state) {
        	switch (state){
        	case "coger_dragon":
        		dragon_taken=true;
        		break;
        	case "coger_lobo":
        		lobo_taken=true;
        		break;
        	case "soltar_dragon":
        		dragon_taken=false;
        		break;
        	case "soltar_lobo":
        		lobo_taken=false;
        		break;
        	}
        }

        private synchronized void enviar(String texto) {
            
        	change_totem(texto);
            Iterator<Socket> iter = listaConexiones.iterator();
            PrintWriter out = null;
            int x=0;
            while (iter.hasNext()) {
            	if (iter != null) {
	            	x++;
	                try {
	                    out = new PrintWriter(iter.next().getOutputStream());
	                } catch (IOException e) {
	                    e.getMessage();
	                }
	                if(out!=null) {
		                out.println(texto);
		                out.flush();;
	                }
	                System.out.println(x);
            	}
            }
        }
    }

    private class HiloConexiones {

        public HiloConexiones() {
            new Thread() {
                public void run() {
                    try {
                        PrintWriter out = null;
                        BufferedReader in = null;
                        try {
                            // Obtener flujos de salida y entrada
                            OutputStream outStream = Server_VT.this.
                                    socketConexion.getOutputStream();
                            InputStream inStream = Server_VT.this.
                                    socketConexion.getInputStream();

                            // Crear flujos de escritura y lectura
                            out = new PrintWriter(outStream);
                            System.out.println(
                                    "Servidor> Obtenido flujo de escritura");
                            in = new BufferedReader(
                                    new InputStreamReader(inStream));
                            System.out.println(
                                    "Servidor> Obtenido flujo de lectura");

                            // Leer y escribir en los flujos
                            while ((linea = in.readLine()) != null) {
                                System.out.println("Servidor> Recibida linea = "
                                        + linea);
                                   
                                    // todas las conexiones de la lista
                                    // --------------------------------
                                    listaConexiones.enviar(linea);
                                    // --------------------------------
                            }
                        } finally {
                            // Eliminar conexion de la lista
                            listaConexiones.eliminar(socketConexion);
                            // Cerrar flujos
                            if (out != null) {
                                out.close();
                            }
                            if (in != null) {
                                in.close();
                            }
                            // Cerrar socket de la conexion
                            socketConexion.close();
                            System.out.println("Servidor> Fin de conexion");
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        
                    }
                }
            }.start();
        }
    }
}
