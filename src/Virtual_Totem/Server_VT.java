package Virtual_Totem;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


import Virtual_Totem.Server_VT;

class Server_VT {

    public static void main(String[] args) {
        Server_VT server = new Server_VT();
        server.execute();
    }

    private ConectionList ConectionList;
    private ServerSocket socketserver;
    private Socket socketConexion;
    private Object msg;

    public Server_VT() {
        ConectionList = new ConectionList();
    }

    private void execute() {
        try {
            // Create socket server
            socketserver = new ServerSocket(2025);

            while (true) {
                // Wait client conection
                socketConexion = socketserver.accept();

                // Add conection to the list
                ConectionList.add(socketConexion);

                //Call ConectionThreatd 
                this.new ConectionThread();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private class ConectionList {

        private List<Socket> ConectionList;

        private ConectionList() {
            ConectionList = new LinkedList<Socket>();
        }

        private synchronized void add(Socket socketConexion) {
            ConectionList.add(socketConexion);
        }

        private synchronized void remove(Socket socketConexion) {
            ConectionList.remove(socketConexion);
        }

        private synchronized void send(Object msg) {
            
            Iterator<Socket> iter = ConectionList.iterator();
            ObjectOutputStream out = null;
            while (iter.hasNext()) {
                try {
                    out = new ObjectOutputStream(iter.next().getOutputStream());
                    out.writeObject(msg);
                    out.flush();
                    
                } catch (IOException e) {
                    e.getMessage();
                }
                
            }
        }
    }

    private class ConectionThread {

        public ConectionThread() {
            new Thread() {
                public void run() {
                    try {
                        ObjectOutputStream out = null;
                        ObjectInputStream in = null;
                        try {
                            // Get outbound and inbound flows
                            OutputStream outStream = Server_VT.this.
                                    socketConexion.getOutputStream();
                            InputStream inStream = Server_VT.this.
                                    socketConexion.getInputStream();

                            // Create outbound and inbound flows
                            out = new ObjectOutputStream(outStream);
                            in = new ObjectInputStream(inStream);

                            // Read and write in inbound flows
                            try {
								while ((msg = in.readObject()) != null) {
								        // All conections in the list
								        ConectionList.send(msg);
								}
							} catch (ClassNotFoundException e) {
								e.printStackTrace();
							}
                            
                        } finally {
                            // remove conection from the list
                            ConectionList.remove(socketConexion);
                            // Close flows
                            if (out != null) {
                                out.close();
                            }
                            if (in != null) {
                                in.close();
                            }
                            // Close socket conection
                            socketConexion.close();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }.start();
        }
    }
}
