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
        Server_VT server = new Server_VT();
        server.execute();
    }

    private ConectionList ConectionList;
    private ServerSocket socketserver;
    private Socket socketConexion;
    private String msg;

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

        private synchronized void send(String msg) {
            
            Iterator<Socket> iter = ConectionList.iterator();
            PrintWriter out = null;
            while (iter.hasNext()) {
                try {
                    out = new PrintWriter(iter.next().getOutputStream());
                } catch (IOException e) {
                    e.getMessage();
                }
                out.println(msg);
                out.flush();;
            }
        }
    }

    private class ConectionThread {

        public ConectionThread() {
            new Thread() {
                public void run() {
                    try {
                        PrintWriter out = null;
                        BufferedReader in = null;
                        try {
                            // Get outbound and inbound flows
                            OutputStream outStream = Server_VT.this.
                                    socketConexion.getOutputStream();
                            InputStream inStream = Server_VT.this.
                                    socketConexion.getInputStream();

                            // Create outbound and inbound flows
                            out = new PrintWriter(outStream);
                            in = new BufferedReader(
                                    new InputStreamReader(inStream));

                            // Read and write in inbound flows
                            while ((msg = in.readLine()) != null) {
                                    // All conections in the list
                                    ConectionList.send(msg);
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
