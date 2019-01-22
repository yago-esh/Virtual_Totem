package Virtual_Totem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ReadData {
	
	private File file = null;
	private FileReader fileReader = null;
	private BufferedReader bufferedReader = null;
	private FileWriter fileWriter = null;
	private PrintWriter printWriter = null;
	private int size;
	
	public ReadData(Integer size) {
		this.size=size;
	}
	
	public String[] read() {
		
		String[] data = new String[size];
		try {
			//Select the file
	        file = new File ("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Virtual_Totem\\Data_Option.txt");
	        fileReader = new FileReader (file);
	        bufferedReader = new BufferedReader(fileReader);

	        String line;
	        int x=0;
	        //Start reading
	        while((line=bufferedReader.readLine())!=null) {
	        	if(x>=size) {
	        		//There was a problem
	        		System.out.println("Error, demasiados datos");
	        	}
	        	else {
		        	data[x]=line;
		        	x++;
	        	}
	        }
	      }
	      catch(FileNotFoundException e){
	    	  //File not found
	    	  System.out.println("ARCHIVO NO ENCONTRADO");
	    	  return null;
	      } catch (IOException e) {
			e.printStackTrace();
		}finally{
	         try{
	        	 //Close the fileReader
	            if( null != fileReader ){   
	               fileReader.close();     
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
			//Select the file and if the paths doesn't exist, the app create the directories
			new File("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Virtual_Totem").mkdirs();
            fileWriter = new FileWriter("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Virtual_Totem\\Data_Option.txt");
            printWriter = new PrintWriter(fileWriter);
            
            //Writes the options in the file
            for (int x=0; x<options.length; x++) {
                printWriter.println(options[x]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
        	   //Close the fileWriter
	           if (null != fileWriter)
	              fileWriter.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
	}

}
