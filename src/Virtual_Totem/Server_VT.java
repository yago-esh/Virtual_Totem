package Virtual_Totem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


import Virtual_Totem.Server_VT;

class Server_VT {

    public static void main(String[] args) {
        Server_VT servidor = new Server_VT();
        servidor.ejecutar();
    }

    private ListaConexiones listaConexiones;
    private ServerSocket socketServidor;
    private Socket socketConexion;
    private String linea;

    public Server_VT() {
        listaConexiones = new ListaConexiones();
    }

    private void ejecutar() {
        try {
            // Crear socket servidor
            socketServidor = new ServerSocket(2029);

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
            listaConexiones = new LinkedList<Socket>();
        }

        private synchronized void anadir(Socket socketConexion) {
            listaConexiones.add(socketConexion);
        }

        private synchronized void eliminar(Socket socketConexion) {
            listaConexiones.remove(socketConexion);
        }

        private synchronized void enviar(String texto) {
            
            Iterator<Socket> iter = listaConexiones.iterator();
            PrintWriter out = null;
            while (iter.hasNext()) {
                try {
                    out = new PrintWriter(iter.next().getOutputStream());
                } catch (IOException e) {
                    e.getMessage();
                }
                out.println(texto);
                out.flush();;
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
                            boolean salir = false;
                            while (!salir && (linea = in.readLine()) != null) {
                                System.out.println("Servidor> Recibida linea = "
                                        + linea);
                                if (linea.trim().equals("adios")) {
                                    salir = true;
                                } else {
                                   
                                    // todas las conexiones de la lista
                                    // --------------------------------
                                    listaConexiones.enviar(linea);
                                    // --------------------------------
                                }
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
