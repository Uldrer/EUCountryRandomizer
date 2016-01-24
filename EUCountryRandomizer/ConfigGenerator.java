package Helpers;

import java.util.Properties;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

public class ConfigGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//Reading properties files in Java example
        Properties props = new Properties();
        
        try
        {
	        FileOutputStream fos = new FileOutputStream("resources/config/test.xml");
	      
	        props.setProperty("key1", "value1");
	        props.setProperty("key2", "value2");
	      
	        //writing properties into properties file from Java
	        props.storeToXML(fos, "Properties file in xml format generated from Java program");
	      
	        fos.close();
        }
        catch(FileNotFoundException ex)
        {
        	System.out.println("Could not create file");
        }
        catch(IOException ex)
        {
        	System.out.println("Error creating xml-file content");
        }

	}

}
