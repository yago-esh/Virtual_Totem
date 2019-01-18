package Virtual_Totem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
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
    private ArrayList<Server_Client> serverClient;
	private int id;
	private int compatible_version = 187;
	private Log_VT log;
	
    public Server_VT() {
        listaConexiones = new ListaConexiones();
        id=1;
        log = new Log_VT();
        
        serverClient = new ArrayList<Server_Client>();
        serverClient.add(new Server_Client("Vodafone"));
        serverClient.add(new Server_Client("Caser"));
        serverClient.add(new Server_Client("Mapfre"));
    }

    private void ejecutar() {
    	try {
			socketServidor = new ServerSocket(2029);
			this.start();
			this.new CheckConexiones();
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
                 this.new HiloConexiones(id);
                 id++;
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
        
        private synchronized void change_totem(String state, int id_client) {
        	
        	boolean change=true;
        	String name = "";
        	Server_Client clientSelected = null;
        	if(state.indexOf(",") != -1) {
    			
    			String[] parts = state.split(",");
    			for (Server_Client client : serverClient) {
					if(client.getName().equals(parts[parts.length-1])) {
						clientSelected = client;
						break;
					}
				}
    			switch(parts[0]) {
    			
    			case "list":
    				if(parts[1].equals("coger_lobo")) {
    					clientSelected.getTotemTopControlList().add(id_client);
    					clientSelected.getTotemTopList().add(parts[2]);
    				}
    				if(parts[1].equals("coger_dragon")) {
    					clientSelected.getTotemBotControlList().add(id_client);
    					clientSelected.getTotemBotList().add(parts[2]);
    				}
    				change=false;
    				break;
    			
    			case "totem":
    				state=parts[1];
    				name=parts[2];
    				break;
    				
    			case "CleanList": case "CleanServer":
    				change=false;
    				if(parts[1].equals("wolf")) {
    					clientSelected.getTotemTopList().remove(Integer.parseInt(parts[2]));
    					clientSelected.getTotemTopControlList().remove(Integer.parseInt(parts[2]));
    				}
    				else if (parts[1].equals("dragon")) {
    					clientSelected.getTotemBotList().remove(Integer.parseInt(parts[2]));
    					clientSelected.getTotemBotControlList().remove(Integer.parseInt(parts[2]));
    				}
    				break;
    				
    			case "freedom":
    				change=false;
    				if(parts[1].equals("soltar_lobo")) {
    					clientSelected.getTotemTopControlList().clear();
    					clientSelected.getTotemTopList().clear();
    					clientSelected.setTotemTopTaken(false);
    				}
    				else if (parts[1].equals("soltar_dragon")) {
    					clientSelected.getTotemBotControlList().clear();
    					clientSelected.getTotemBotList().clear();
    					clientSelected.setTotemBotTaken(false);
    				}
    				break;
    			case "update":
    				compatible_version = Integer.parseInt(parts[1]);
    				break;
    				
    			case "freeTotem":
    				if(parts[1].equals("soltar_dragon")){
    					change=false;
        				clientSelected.setTotemBotTime();
    	        		if(clientSelected.getTotemBotList().isEmpty()) {
    	        			clientSelected.setTotemBotTaken(false);
    	        		}
    				}
    				else if(parts[1].equals("soltar_lobo")) {
    					change=false;
    	        		clientSelected.setTotemTopTime();
    	        		if(clientSelected.getTotemTopList().isEmpty()) {
    	        			clientSelected.setTotemTopTaken(false);
    	        		}
    				}
	        		break;
    			}
    			
    		}
        	if(change) {
	        	switch (state){
	        	case "coger_dragon":
	        		clientSelected.setTotemBotTaken(true);
	        		if(clientSelected.getTotemBotTime() == 1) { //If the time is not already running
	        			time("dragon",clientSelected);
	        		}
	        		else {
	        			clientSelected.setTotemBotTime();
	        		}
	        		clientSelected.setTotemBotUser(name);
	        		
	        		break;
	        	case "coger_lobo":
	        		clientSelected.setTotemTopTaken(true);
	        		if(clientSelected.getTotemTopTime() == 1) { //If the time is not already running
	        			time("wolf",clientSelected);
	        		}
	        		else {
	        			clientSelected.setTotemTopTime();
	        		}
	        		clientSelected.setTotemTopUser(name);
	        		break;
	        	}
        	}
        }
        
        private synchronized void enviar(String texto, int id_client) {
            
        	change_totem(texto,id_client);
            Iterator<Socket> iter = listaConexiones.iterator();
            PrintWriter out = null;
            if(!(texto.indexOf("ACK")!=-1)) {
            	while (iter.hasNext()) {
                	if (iter != null) {
    	                try {
    	                    out = new PrintWriter(iter.next().getOutputStream());
    	                } catch (IOException e) {
    	                    e.getMessage();
    	                }
    	                if(out!=null) {
    		                out.println(texto);
    		                out.flush();;
    	                }
                	}
                }
            }
            
        }
        
        private synchronized void checkConexiones() {
        	Iterator<Socket> iter = listaConexiones.iterator();
            PrintWriter out = null;
            while (iter.hasNext()) {
            	if (iter != null) {
	                try {
	                    out = new PrintWriter(iter.next().getOutputStream());
	                } catch (IOException e) {
	                    e.getMessage();
	                }
	                if(out!=null) {
		                out.println("ACK");
		                out.flush();;
	                }
            	}
            }
        }
        
        private synchronized void break_free(int id_client) {
        	System.out.println("comprobacion_break_free");
        	/*if(dragon_taken_id == id_client) {
        		enviar("soltar_dragon",id_client);
        	}
        	if (lobo_taken_id == id_client) {
        		enviar("soltar_lobo",id_client);
        	}
        	*/
        	for (Server_Client server_Client : serverClient) {
        		for (int x=0 ; x<server_Client.getTotemTopControlList().size() ; x++) {
            		if(id_client == server_Client.getTotemTopControlList().get(x)) {
            			enviar("CleanList,wolf,"+x,id_client);
            		}
            	}
            	
            	for (int x=0 ; x<server_Client.getTotemBotControlList().size() ; x++) {
            		if(id_client == server_Client.getTotemBotControlList().get(x)) {
            			enviar("CleanList,dragon,"+x,id_client);
            		}
            	}
			}
        	  
        }
    }

    private class HiloConexiones {

        public HiloConexiones(int id) {
            new Thread() {
                public void run() {
                    try {
                    	int id_client=id;
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
                            out.println(compatible_version);
                            out.flush();
                            for (Server_Client server_Client : serverClient) {
								if(server_Client.isTotemTopTaken()) {
									out.println("totem,coger_lobo,"+server_Client.getTotemTopUser()+","+server_Client.getName());
	                            	out.flush();
	                            	out.println("time,wolf,"+server_Client.getTotemTopTimeParse()+","+server_Client.getName());
	                            	out.flush();
								}
								if(server_Client.isTotemBotTaken()) {
									out.println("totem,coger_dragon,"+server_Client.getTotemBotUser()+","+server_Client.getName());
	                            	out.flush();
	                            	out.println("time,dragon,"+server_Client.getTotemBotTimeParse()+","+server_Client.getName());
	                            	out.flush();
								}
							}
                            for (Server_Client server_Client : serverClient) {
                            	for(String user_list: server_Client.getTotemTopList()) {
                                	out.println("list,coger_lobo,"+user_list+","+server_Client.getName());
                                	out.flush();
                                }
                                for(String user_list: server_Client.getTotemBotList()) {
                                	out.println("list,coger_dragon,"+user_list+","+server_Client.getName());
                                	out.flush();
                                }
							}

                            // Leer y escribir en los flujos
                            while ((linea = in.readLine()) != null) {
                                System.out.println("Servidor> Recibida linea = "
                                        + linea);
                                   
                                    // todas las conexiones de la lista
                                    // --------------------------------
                                	log.crearLog(linea);
                                    listaConexiones.enviar(linea,id_client);
                                    // --------------------------------
                            }
                        } finally {
                        	System.out.println("comprobacion_finally");
                        	listaConexiones.break_free(id_client);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }.start();
        }
    }

    public void time(String totem, Server_Client clientSelected) {
		Thread t1 = new Thread(new Runnable() {
	         public void run() {
	        	 try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
	        	 if(totem.equals("dragon")) {
	        		 while(clientSelected.isTotemBotTaken()) {
	        			 clientSelected.totemBotTimeIncrement();
			        	 try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
		        	 }
	        		 clientSelected.setTotemBotTime();
	        	 }
	        	 else if (totem.equals("wolf")) {
	        		 while(clientSelected.isTotemTopTaken()) {
	        			 clientSelected.totemTopTimeIncrement();
			        	 try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
		        	 }
	        		 clientSelected.setTotemTopTime();
	        	 }
	         }
	    });  
	    t1.start();
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
                		listaConexiones.checkConexiones();
                	}
                }
     
            }.start();
        }
    }
}
