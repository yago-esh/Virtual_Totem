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

public class Client {

	private Socket socket;
	private PrintWriter printWriter;
	private BufferedReader bufferedReader;
	private Generic_client client;
	private int version;
	private String myId;

	public Client() {
		//Initialize the variables
		socket = null;
		printWriter = null;
		bufferedReader = null;
		client = null;
		version=208;
	}

	public void associate(Generic_client client) {
		//Associate the clients
		this.client = client;
	}

	public void execute() throws IOException {
		//------------------------------Development mode--------------------------------
			socket = new Socket("localhost", 2030);
		//---------------------------------------------------------------------------
		
		
//		socket = new Socket("10.0.1.95", 2030);

		System.out.println("Cliente> Establecida conexion");

		// Get the streams
		OutputStream outStream = socket.getOutputStream();
		InputStream inStream = socket.getInputStream();

		// Create the flows
		printWriter = new PrintWriter(outStream);
		System.out.println("Cliente> Obtenido flujo de escritura");
		bufferedReader = new BufferedReader(new InputStreamReader(inStream));
		System.out.println("Cliente> Obtenido flujo de lectura");

		//Create new thread to receive the messages
		new ReceiverThread(bufferedReader).start();
		//Creates a thread to send ACK messages to the server to know if it is alive
		this.new ConnectionCheckACK();
	}

	public void finish() {

		//Close the streams
		if (printWriter != null) {
			printWriter.close();
			printWriter = null;
		}
		if (bufferedReader != null) {
			try {
				bufferedReader.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			bufferedReader = null;
		}

		//Close the socket
		if (socket != null) {
			try {
				socket.close();
				System.out.println("Cliente> Fin de conexion");
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			socket = null;
		}
		//Exit the app
		System.exit(-1);
	}

	public void send(String message) {
		//Send the message to the server
		printWriter.println(message);
		printWriter.flush();
	}
	
	public String getMyId() {
		return myId;
	}

	public void setMyId(String myId) {
		this.myId = myId;
	}

	private class ReceiverThread extends Thread {

		private BufferedReader bufferedReader;
		
		private ReceiverThread(BufferedReader bufferedReader) {
			this.bufferedReader = bufferedReader;
		}

		public void run() {
			try {
				//Read the first line the server always sends. It is the versions of the app
				checkVersion(bufferedReader.readLine());
				//Read the second line the server always sends. It is the id associated with this client
				parseId(bufferedReader.readLine());
				
				String receivedLine;
				//If the server sends any line, the client processes it and change the status
				while ((receivedLine = bufferedReader.readLine()) != null) {
					client.changeClientStatus(receivedLine);
				}
			} catch (IOException ex) {
				//If the app finished not properly, shows a message error
					JOptionPane.showMessageDialog(null,
							"Se ha perdido la conexión con el servidor",
							"ERROR", JOptionPane.ERROR_MESSAGE);
			}
			finally {
				//Close the app
				Client.this.finish();
			}
		}
		
		private void checkVersion(String receivedLine) {
			//Separate the message into the 2 numbers
			String[] parts = receivedLine.split(",");
			
			//If the first number received is bigger than the version of the client
			if(Integer.parseInt(parts[0])>version) {
				//The client is incompatible and the app closes
				JOptionPane.showMessageDialog(null,
						"Su versión de Virtual Totem es incompatible. Por favor, actualícela.",
						"ERROR", JOptionPane.ERROR_MESSAGE);
				System.exit(-1);
			}
			//If the second number received is bigger than the version of the client
			if(Integer.parseInt(parts[1])>version) {
				//There is an update available so the app shows a message to inform the client
				JOptionPane.showMessageDialog(null,
						"Existe una nueva versión del Virtual Totem. Por favor, actualícela.",
						"Nueva versión disponible", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
		private void parseId(String receivedLine) {
			//Set the id sent by the server
			myId = receivedLine;
			System.out.println("Mi Id es: "+myId);
			client.setId(myId);
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
                		client.send("ACK");
                	}
                }
            }.start();
        }
    }
}
