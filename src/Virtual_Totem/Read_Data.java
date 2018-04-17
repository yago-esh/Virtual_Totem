package Virtual_Totem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Read_Data {
	
	private File archivo = null;
	private FileReader fr = null;
	private BufferedReader br = null;
	private FileWriter fichero = null;
	private PrintWriter pw = null;
	
	public Read_Data() {
		
	}
	
	public void read() {
		try {
	         archivo = new File ("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Virtual_Totem\\prueba.txt");
	         fr = new FileReader (archivo);
	         br = new BufferedReader(fr);

	         String linea;
	         while((linea=br.readLine())!=null)
	            System.out.println(linea);
	      }
	      catch(Exception e){
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
	}
	
	public void write() {
		try
        {
			new File("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Virtual_Totem").mkdirs();
            fichero = new FileWriter("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Virtual_Totem\\prueba.txt");
            pw = new PrintWriter(fichero);
            
            for (int i = 0; i < 10; i++)
                pw.println("Linea " + i);

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
