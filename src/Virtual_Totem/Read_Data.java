package Virtual_Totem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Read_Data {
	
	private File archivo = null;
	private FileReader fr = null;
	private BufferedReader br = null;
	private FileWriter fichero = null;
	private PrintWriter pw = null;
	private int size;
	
	public Read_Data(Integer size) {
		this.size=size;
	}
	
	public String[] read() {
		
		String[] data = new String[size];
		try {
	        archivo = new File ("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Virtual_Totem\\Data_Option.txt");
	        fr = new FileReader (archivo);
	        br = new BufferedReader(fr);

	        String linea;
	        int x=0;
	        while((linea=br.readLine())!=null) {
	        	if(x>=size) {
	        		//Insertar mensaje de error
	        		System.out.println("Error, demasiados datos");
	        	}
	        	data[x]=linea;
	        	x++;
	        }
	      }
	      catch(FileNotFoundException e){
	    	  System.out.println("ARCHIVO NO ENCONTRADO");
	    	  return null;
	      } catch (IOException e) {
			e.printStackTrace();
		}finally{
	         try{                    
	            if( null != fr ){   
	               fr.close();     
	            }                  
	         }catch (Exception e2){ 
	            e2.printStackTrace();
	         }
	      }
		return data;
	}
	
	public void write(String[] options) {
		try
        {
			new File("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Virtual_Totem").mkdirs();
            fichero = new FileWriter("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Virtual_Totem\\Data_Option.txt");
            pw = new PrintWriter(fichero);
            
            for (int x=0; x<options.length; x++) {
                pw.println(options[x]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {

           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
	}

}
