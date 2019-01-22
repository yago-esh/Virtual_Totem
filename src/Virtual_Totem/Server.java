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

class Server extends Thread{

    public static void main(String[] args) {
        new Server().execute();
    }

    private ConnectionList connectionList;
    private ServerSocket serverSocket;
    private Socket connectionSocket;
    private String line;
    private Log_VT log;
    private ArrayList<ServerCompany> serverCompany;
	private int id;
	private int compatibleVersion = 208;
	private int currentVersion = 208;
	
	
    public Server() {
        connectionList = new ConnectionList();
        log = new Log_VT();
        
        //The server creates a Id to have a control of the different clients
        id=0;
        
        //Initialize the different companies
        serverCompany = new ArrayList<ServerCompany>();
        serverCompany.add(new ServerCompany("Vodafone"));
        serverCompany.add(new ServerCompany("Caser"));
        serverCompany.add(new ServerCompany("Mapfre"));
        serverCompany.add(new ServerCompany("Multiasistencia"));
        serverCompany.add(new ServerCompany("LDA"));
        serverCompany.add(new ServerCompany("AXA"));
    }

    private void execute() {
    	try {
			serverSocket = new ServerSocket(2030);
			this.start();
			//The server creates a thread to send ACK messages to the clients to know if someone was disconnected
			this.new ConnectionCheckACK();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void run(){
    	 try {
             // Create client sockets
             while (true) {
                 System.out.println("Servidor> Esperando conexion...");
                 
                 // The server waits to the client connection
                 connectionSocket = serverSocket.accept();
                 
                 System.out.println("Servidor> Recibida conexion de "
                         + connectionSocket.getInetAddress().getHostAddress() + ":"
                         + connectionSocket.getPort());

                 // Add the new connection to the list
                 connectionList.addSocket(connectionSocket);

                 // Create a new thread for the connection
                 this.new ConnectionThread(id);
                 
                 // Increment the id to set a unique id for each client
                 id++;
             }
         } catch (IOException ex) {
             ex.printStackTrace();
         }
    }

    private class ConnectionList {

        private List<Socket> connectionList;

        private ConnectionList() {
            connectionList = new ArrayList<Socket>();
        }

        private synchronized void addSocket(Socket socketConexion) {
            connectionList.add(socketConexion);
        }
        
        private synchronized void changeServerStatus(String text, int id_client) {
        	
        	ServerCompany clientSelected = null;
    		
        	//The message is separated by commas to separate the different levels of the functions
			String[] parts = text.split(",");
			
			//The server reads the text and select the company which is involved in
			//Always the company is the last word of the message
			for (ServerCompany client : serverCompany) {
				if(client.getName().equals(parts[parts.length-1])) {
					clientSelected = client;
					break;
				}
			}
			//Now the server is going to make changes in function of the content of the message
			//To do this, the server read the first word of the message that is the reference word
			switch(parts[0]) {
			
    			//Structure: "totem,function,user,company"
    			//This is used to notify the server that a users take a totem
    			case "totem":
    				
    				//Difference between top and bot totem
    				switch (parts[1]){
    				
	    				case "takeTotemTop":
	    					//Mark the totem taken 
	    	        		clientSelected.setTotemTopTaken(true);
	    	        		
	    	        		//If the timer is not already running, start the timer
	    	        		if(clientSelected.isTotemTopTimerStoped()) { 
	    	        			setTimer("totemTop",clientSelected);
	    	        		}
	    	        		//If is running, set the current timer
	    	        		else {
	    	        			clientSelected.setTotemTopTimerToCero();
	    	        		}
	    	        		//Mark the user that has taken the totem
	    	        		clientSelected.setTotemTopUser(parts[2]);
	    	        		break;
	    	        		
	    	        	case "takeTotemBot":
	    	        		clientSelected.setTotemBotTaken(true);
	    	        		if(clientSelected.isTotemBotTimerStoped()) {
	    	        			setTimer("totemBot",clientSelected);
	    	        		}
	    	        		else {
	    	        			clientSelected.setTotemBotTimerToCero();
	    	        		}
	    	        		clientSelected.setTotemBotUser(parts[2]);
	    	        		break;
    	        	}
    				break;
    			
    			//Structure: "freeTotem,function,user,company"
        		//This is used to notify the server that a users take a totem
    			case "freeTotem":
    				//Difference between top and bot totem
    				if(parts[1].equals("freeTotemBot")){
    					//Set the timer to 0
        				clientSelected.setTotemBotTimerToCero();
        				//Looks if some is in the waiting list, if not set the totem free
    	        		if(clientSelected.getTotemBotList().isEmpty()) {
    	        			clientSelected.setTotemBotTaken(false);
    	        		}
    				}
    				else if(parts[1].equals("freeTotemTop")) {
    	        		clientSelected.setTotemTopTimerToCero();
    	        		if(clientSelected.getTotemTopList().isEmpty()) {
    	        			clientSelected.setTotemTopTaken(false);
    	        		}
    				}
	        		break;
	        	
        		//Structure: "list,function,user,company"
				//This is used to notify the server that a user signs up in a waiting list 
    			case "list":
    				//Difference between top and bot totem
    				if(parts[1].equals("takeTotemTop")) {
    					//The server adds the client to his lists
    					clientSelected.getTotemTopControlList().add(id_client);
    					clientSelected.getTotemTopList().add(parts[2]);
    				}
    				if(parts[1].equals("takeTotemBot")) {
    					clientSelected.getTotemBotControlList().add(id_client);
    					clientSelected.getTotemBotList().add(parts[2]);
    				}
    				break;
    				
    			//Structure: "CleanList,function,id from the list = user,company"
        		//This is used to notify the server that a users take a totem
    			case "CleanList":
    				//Difference between top and bot totem
    				if(parts[1].equals("totemTop")) {
    					//The server remove the client from his lists
    					clientSelected.getTotemTopList().remove(Integer.parseInt(parts[2]));
    					clientSelected.getTotemTopControlList().remove(Integer.parseInt(parts[2]));
    				}
    				else if (parts[1].equals("totemBot")) {
    					clientSelected.getTotemBotList().remove(Integer.parseInt(parts[2]));
    					clientSelected.getTotemBotControlList().remove(Integer.parseInt(parts[2]));
    				}
    				break;
    			
    			//Structure: "freedom,function,user,company"
            	//This is used to notify the server that a users take a totem
    			case "freedom":
    				//Difference between top and bot totem
    				if(parts[1].equals("freeTotemTop")) {
    					//Clear the lists and set the totem free
    					clientSelected.getTotemTopControlList().clear();
    					clientSelected.getTotemTopList().clear();
    					clientSelected.setTotemTopTaken(false);
    				}
    				else if (parts[1].equals("freeTotemBot")) {
    					clientSelected.getTotemBotControlList().clear();
    					clientSelected.getTotemBotList().clear();
    					clientSelected.setTotemBotTaken(false);
    				}
    				break;
    				
    			//Structure: "update,compatibleVersion,currentVersion,x"
                //This is used to change the compatible and current version of the server
    			//This is a development option, is only used for the engineering to set the values without restart or modify the server	
    			case "update":
    				compatibleVersion = Integer.parseInt(parts[1]);
    				currentVersion = Integer.parseInt(parts[2]);
    				break;
    				
    			//Structure: "clientChanged,id,user,company"
                //This is used to force the server to send the information of the company to the client
    			case "clientChanged":
    				
    				int id = Integer.parseInt(parts[1]);
    				
    				//Send the info of the totem if is taken
    				if(clientSelected.isTotemTopTaken()) {
    					sendMsgtoOneClient("totem,takeTotemTop,"+clientSelected.getTotemTopUser()+","+clientSelected.getName(), id);
    					sendMsgtoOneClient("time,wolf,"+clientSelected.getTotemTopTimer()+","+clientSelected.getName(), id);
    				}
    				if(clientSelected.isTotemBotTaken()) {
    					sendMsgtoOneClient("totem,takeTotemBot,"+clientSelected.getTotemBotUser()+","+clientSelected.getName(), id);
    					sendMsgtoOneClient("time,dragon,"+clientSelected.getTotemBotTimer()+","+clientSelected.getName(), id);
    				}
    				//Send the info of the lists if someone is on it
		        	for(String user_list: clientSelected.getTotemTopList()) {
		        		sendMsgtoOneClient("list,takeTotemTop,"+user_list+","+clientSelected.getName(), id);
		            }
		            for(String user_list: clientSelected.getTotemBotList()) {
		            	sendMsgtoOneClient("list,takeTotemBot,"+user_list+","+clientSelected.getName(), id);
		            }
			}
        }
        
        private synchronized void processLine(String text, int id_client) {
        	//The server processes the line to make changes on it
        	changeServerStatus(text,id_client);
        	//The server sends the line to the others clients
            sendMsgtoClients(text);
        }
        
        private synchronized void sendMsgtoClients(String text) {
            
            Iterator<Socket> iter = connectionList.iterator();
            PrintWriter out = null;
            //The server avoid to send the ACK message from the others clients
            if(!(text.indexOf("ACK")!=-1)) {
            	
            	//Send the text to all the clients
            	while (iter.hasNext()) {
                	if (iter != null) {
    	                try {
    	                    out = new PrintWriter(iter.next().getOutputStream());
    	                } catch (IOException e) {
    	                    e.getMessage();
    	                }
    	                if(out!=null) {
    		                out.println(text);
    		                out.flush();;
    	                }
                	}
                }
            }
        }
        
        private synchronized void sendMsgtoOneClient(String text, Integer id) {
        	PrintWriter out = null;
            try {
            	//The server open the stream only for the client selected by the id
				out = new PrintWriter(connectionList.get(id).getOutputStream());
			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
            out.println(text);
            out.flush();
        }
        
        private synchronized void sendACK() {
        	Iterator<Socket> iter = connectionList.iterator();
            PrintWriter out = null;
            //Send the ACK message to all the clients
            //This is used to know if some client has been disconnected and if does, remove it from the server automatically
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
        
        private synchronized void controlDisconnectedClient(int id_client) {
        	
        	// The Server looks if the client that has disconnected was on some list and if was, it sends a message to remove him from the others client list
        	for (ServerCompany serverCompany : serverCompany) {
        		for (int x=0 ; x<serverCompany.getTotemTopControlList().size() ; x++) {
            		if(id_client == serverCompany.getTotemTopControlList().get(x)) {
            			processLine("CleanList,totemTop,"+x,id_client);
            		}
            	}
            	
            	for (int x=0 ; x<serverCompany.getTotemBotControlList().size() ; x++) {
            		if(id_client == serverCompany.getTotemBotControlList().get(x)) {
            			processLine("CleanList,totemBot,"+x,id_client);
            		}
            	}
			}
        }
    
        public void setTimer(String totem, ServerCompany clientSelected) {
    		Thread t1 = new Thread(new Runnable() {
    	         public void run() {
    	        	 try {
    	        		 //Wait 1 second to have a good synchronization
    					Thread.sleep(1000);
    				} catch (InterruptedException e1) {
    					e1.printStackTrace();
    				}
    	        	//Difference between top and bot totem
    	        	 if (totem.equals("totemTop")) {
    	        		 //Check if totem is taken
    	        		 while(clientSelected.isTotemTopTaken()) {
    	        			 //If is, increment the timer 1 second
    	        			 clientSelected.totemTopTimeIncrement();
    			        	 try {
    			        		//Wait 1 second
    							Thread.sleep(1000);
    						} catch (InterruptedException e) {
    							e.printStackTrace();
    						}
    		        	 }
    	        		 //If not, restart the timer
    	        		 clientSelected.setTotemTopTimerToCero();
    	        	 }
    	        	 else if(totem.equals("totemBot")) {
    	        		 while(clientSelected.isTotemBotTaken()) {
    	        			 clientSelected.totemBotTimeIncrement();
    			        	 try {
    							Thread.sleep(1000);
    						} catch (InterruptedException e) {
    							e.printStackTrace();
    						}
    		        	 }
    	        		 clientSelected.setTotemBotTimerToCero();
    	        	 }
    	         }
    	    });  
    	    t1.start();
    	}
    }
    
    private class ConnectionThread {

        private ConnectionThread(int id) {
            new Thread() {
                public void run() {
                    try {
                    	int clientId = id;
                        PrintWriter out = null;
                        BufferedReader in = null;
                        try {
                            OutputStream outStream = Server.this.connectionSocket.getOutputStream();
                            InputStream inStream = Server.this.connectionSocket.getInputStream();
                            out = new PrintWriter(outStream);
                            
                            System.out.println("Servidor> Obtenido flujo de escritura");
                            in = new BufferedReader(new InputStreamReader(inStream));
                            System.out.println("Servidor> Obtenido flujo de lectura");
                            
                            sendInfoServer(out,clientId);
                            
                            // Read from the flow
                            while ((line = in.readLine()) != null) {
                            	
                                System.out.println("Servidor> Recibida linea = " + line);
                                //Write the file in the log   
                            	log.createLog(line);
                            	//The server sends the same line to all the clients
                                connectionList.processLine(line,clientId);
  
                            }
                        } finally {
                        	//If the client is disconnected the app try to control the consequences
                        	connectionList.controlDisconnectedClient(clientId);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }.start();
        }
        
        private void sendInfoServer(PrintWriter out, Integer clientId) {
        	
        	//The server sends this 2 numbers to notify the client if he has to update the app
            out.println(compatibleVersion+","+currentVersion);
            out.flush();
            
            //The server sends the id to let know the app which id is associated whit him
            out.println(clientId);
            out.flush();
            
            //The server sends the info of the taken totems to the client
        	for (ServerCompany serverCompany : serverCompany) {
    			if(serverCompany.isTotemTopTaken()) {
    				out.println("totem,takeTotemTop,"+serverCompany.getTotemTopUser()+","+serverCompany.getName());
                	out.flush();
                	out.println("time,wolf,"+serverCompany.getTotemTopTimer()+","+serverCompany.getName());
                	out.flush();
    			}
    			if(serverCompany.isTotemBotTaken()) {
    				out.println("totem,takeTotemBot,"+serverCompany.getTotemBotUser()+","+serverCompany.getName());
                	out.flush();
                	out.println("time,dragon,"+serverCompany.getTotemBotTimer()+","+serverCompany.getName());
                	out.flush();
    			}
    		}
        	
        	//The server sends the info of the users that are in a list
            for (ServerCompany serverCompany : serverCompany) {
            	for(String user_list: serverCompany.getTotemTopList()) {
                	out.println("list,takeTotemTop,"+user_list+","+serverCompany.getName());
                	out.flush();
                }
                for(String user_list: serverCompany.getTotemBotList()) {
                	out.println("list,takeTotemBot,"+user_list+","+serverCompany.getName());
                	out.flush();
                }
    		}
        }
    }

    private class ConnectionCheckACK{
    	
    	public ConnectionCheckACK() {
            new Thread() {
                public void run() {
                	while(true) {
                		try {
                			//Wait 1 minute to send the new ACK
							Thread.sleep(60000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
                		//Send the ACK message
                		connectionList.sendACK();
                	}
                }
            }.start();
        }
    }
}
