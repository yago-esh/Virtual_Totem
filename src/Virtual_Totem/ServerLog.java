package Virtual_Totem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class ServerLog {

	private FileWriter fileWriter;
	private String path;

	public ServerLog() {
		//Create the directory
		new File("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Virtual_Totem").mkdirs();
		path = "C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Virtual_Totem\\log_virtual.txt";
	}
	
    public void writeLog(String line) {
    	
    	//If the line is not an ACK message
    	if(line.indexOf("ACK") == -1) {
	    	try {
	    		if (new File(path).exists()==false){fileWriter=new FileWriter(new File(path),false);}
	            fileWriter = new FileWriter(new File(path), true);
	            Calendar fechaActual = Calendar.getInstance();
	            
					fileWriter.write("["+(String.valueOf(fechaActual.get(Calendar.DAY_OF_MONTH))
					     +"/"+String.valueOf(fechaActual.get(Calendar.MONTH)+1)
					     +"/"+String.valueOf(fechaActual.get(Calendar.YEAR))
					     +" - "+String.valueOf(fechaActual.get(Calendar.HOUR_OF_DAY))
					     +":"+String.valueOf(fechaActual.get(Calendar.MINUTE))
					     +":"+String.valueOf(fechaActual.get(Calendar.SECOND)))+"]  "+line+"\r\n");
				
	            fileWriter.close(); 
	   		} catch (IOException e) {
				e.printStackTrace();
			}
	    }
    }

}
