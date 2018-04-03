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
    private boolean lobo_taken;
	private boolean dragon_taken;
	private int lobo_taken_id;
	private int dragon_taken_id;
	private int id;
	private boolean save_lobo;
	private boolean save_dragon;
	private String lobo_user;
	private String dragon_user;
	private int compatible_version = 154;
	private ArrayList<String> List_wolf, List_dragon;
	private ArrayList<Integer> Control_List_wolf, Control_List_dragon;
	
    public Server_VT() {
        listaConexiones = new ListaConexiones();
        id=1;
        save_dragon=false;
        save_lobo=false;
        List_wolf = new ArrayList<String>();
        List_dragon = new ArrayList<String>();
        Control_List_wolf = new ArrayList<Integer>();
        Control_List_dragon = new ArrayList<Integer>();
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
        	if(state.indexOf(",") != -1) {
    			
    			String[] parts = state.split(",");
    			switch(parts[0]) {
    			
    			case "list":
    				if(parts[1].equals("coger_lobo")) {
    					Control_List_wolf.add(id_client);
    					List_wolf.add(parts[2]);
    				}
    				if(parts[1].equals("coger_dragon")) {
    					Control_List_dragon.add(id_client);
    					List_dragon.add(parts[2]);
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
    					List_wolf.remove(Integer.parseInt(parts[2]));
    					Control_List_wolf.remove(Integer.parseInt(parts[2]));
    				}
    				else if (parts[1].equals("dragon")) {
    					List_dragon.remove(Integer.parseInt(parts[2]));
    					Control_List_dragon.remove(Integer.parseInt(parts[2]));
    				}
    				break;
    				
    			case "freedom":
    				change=false;
    				if(parts[1].equals("soltar_lobo")) {
    					Control_List_wolf.clear();
    					List_wolf.clear();
    					lobo_taken=false;
    				}
    				else if (parts[1].equals("soltar_dragon")) {
    					Control_List_dragon.clear();
    					List_dragon.clear();
    					dragon_taken=false;
    				}
    				break;
    			case "update":
    				compatible_version = Integer.parseInt(parts[1]);
    				break;
    			}
    			
    		}
        	if(change) {
	        	switch (state){
	        	case "coger_dragon":
	        		dragon_taken=true;
	        		dragon_user=name;
	        		dragon_taken_id=id_client;
	        		break;
	        	case "coger_lobo":
	        		lobo_taken=true;
	        		lobo_user=name;
	        		lobo_taken_id=id_client;
	        		break;
	        	case "soltar_dragon":
	        		if(List_dragon.isEmpty()) {
	        			dragon_taken=false;
	        		}
	        		break;
	        	case "soltar_lobo":
	        		if(List_wolf.isEmpty()) {
	        			lobo_taken=false;
	        		}
	        		break;
	        	}
        	}
        }

        private synchronized void enviar(String texto, int id_client) {
            
        	change_totem(texto,id_client);
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
		                out.println(texto);
		                out.flush();;
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
		                out.println("Are you there?");
		                out.flush();;
	                }
            	}
            }
        }
        
        private synchronized void break_free(int id_client) {
        	System.out.println("comprobacion_brak_free");
        	if(dragon_taken_id == id_client) {
        		enviar("soltar_dragon",id_client);
        	}
        	if (lobo_taken_id == id_client) {
        		enviar("soltar_lobo",id_client);
        	}
        	
        	for (int x=0 ; x<Control_List_wolf.size() ; x++) {
        		if(id_client == Control_List_wolf.get(x)) {
        			enviar("CleanList,wolf,"+x,id_client);
        		}
        	}
        	
        	for (int x=0 ; x<Control_List_dragon.size() ; x++) {
        		if(id_client == Control_List_dragon.get(x)) {
        			enviar("CleanList,dragon,"+List_dragon.get(x),id_client);
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
                            if(lobo_taken) {
                            	out.println("totem,coger_lobo,"+lobo_user);
                            	out.flush();
                            }
                            if(dragon_taken) {
                            	out.println("totem,coger_dragon,"+dragon_user);
                            	out.flush();
                            }
                            System.out.println("La lista contiene: " + List_wolf);
        					System.out.println();
                            for(String user_list: List_wolf) {
                            	out.println("list,coger_lobo,"+user_list);
                            	out.flush();
                            }
                            for(String user_list: List_dragon) {
                            	out.println("list,coger_dragon,"+user_list);
                            	out.flush();
                            }
                            // Leer y escribir en los flujos
                            while ((linea = in.readLine()) != null) {
                                System.out.println("Servidor> Recibida linea = "
                                        + linea);
                                   
                                    // todas las conexiones de la lista
                                    // --------------------------------
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

    private class CheckConexiones{
    	
    	public CheckConexiones() {
            new Thread() {
                public void run() {
                	while(true) {
                		try {
							this.sleep(60000);
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
