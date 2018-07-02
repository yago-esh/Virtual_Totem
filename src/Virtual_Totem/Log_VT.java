package Virtual_Totem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class Log_VT {

	private FileWriter archivo; //nuestro archivo log
	private String path;

	public Log_VT() {
		new File("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Virtual_Totem").mkdirs();
		path = "C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Virtual_Totem\\log_virtual.txt";
	}
	
    public void crearLog(String Operacion) {
    	
    	if(!Operacion.equals("ACK")) {
	    	try {
	    		if (new File(path).exists()==false){archivo=new FileWriter(new File(path),false);}
	            archivo = new FileWriter(new File(path), true);
	            Calendar fechaActual = Calendar.getInstance();
	            
					archivo.write("["+(String.valueOf(fechaActual.get(Calendar.DAY_OF_MONTH))
					     +"/"+String.valueOf(fechaActual.get(Calendar.MONTH)+1)
					     +"/"+String.valueOf(fechaActual.get(Calendar.YEAR))
					     +" - "+String.valueOf(fechaActual.get(Calendar.HOUR_OF_DAY))
					     +":"+String.valueOf(fechaActual.get(Calendar.MINUTE))
					     +":"+String.valueOf(fechaActual.get(Calendar.SECOND)))+"]  "+Operacion+"\r\n");
				
	            archivo.close(); 
	   		} catch (IOException e) {
				e.printStackTrace();
			}
	    }
    }

}
