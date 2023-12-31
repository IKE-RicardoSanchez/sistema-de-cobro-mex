/*
 * UtlFiles.java
 *
 * Created on 15 de octubre de 2004, 09:48 AM
 */
package Utilerias;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.sql.ResultSet;
import java.nio.channels.FileChannel;
import java.io.IOException;
import java.io.RandomAccessFile;

//import java.io.File;
//import java.io.FilenameFilter;
import java.util.*;
import java.util.TreeSet.*;


/**
 *
 * @author  Jaime Colín Morales
 */
public class UtlFiles {
    
        FileChannel entrada = null;
        FileChannel salida = null;
        public FileWriter salida_ = null;
        String dirIn = "";
	      String dirOut = "";
        String pattern = null;
        public static RandomAccessFile raf = null;
        
        
	public void abreArchivo(String archEntrada ,String archSalida )	{
		try {
		  // Abre el archivo origen
		  entrada = new FileInputStream(archEntrada).getChannel();
		  // Crea el archivo destino
                  salida = new FileOutputStream(archSalida).getChannel();
		} catch( Exception e ) {
			  e.printStackTrace();
		} 
	}
	
	public void copiaArchivo(){
	    try {
   	   		salida.transferFrom( entrada,0L,(int)entrada.size() );
		} catch( Exception e ) {
			  e.printStackTrace();
		} finally {
		     cierraArchivo();
		}
	}
	  
      	public void borraArchivo(String miArchivo){
	    try {
   	   		File salida = new File(miArchivo);
                        salida.delete();
		} catch( Exception e ) {
			  e.printStackTrace();
		} 
	}

	public void cierraArchivo()
	{
		try {
			if( entrada != null )
			  entrada.close();
			if( salida != null )
			  salida.close();
               } catch( IOException e ) {
                       e.printStackTrace();
               }
	}
        public void cierraArchivo(FileWriter salida_){
            try{
                salida_.close();
            }catch(Exception e ){}
        }
        
        public String creaPath (String strIn){
            strIn.replace('/', File.separatorChar);
            dirIn = System.getProperty( "user.home" ) + strIn;
	    return dirIn;
        }
        
        public String leeArchivo(String miArchivo){
            try{  	   	
                  File archEntrada = new File(miArchivo);
                  FileReader entrada = new FileReader( archEntrada );
                  
		  char c[] = new char[(char)archEntrada.length()];
                  
                  
		  int numBytes = entrada.read( c );
		  String cadena = new String( c );
                  
                  return cadena; 
            }catch(IOException e){
                 e.printStackTrace(); 
            }
            return null;
        }
        
        public int numLineas(String miArchivo){
            int i = 0;
            try{
               raf = new RandomAccessFile(miArchivo, "rw");
               
         
            }catch(Exception e){}
            
            try{
                while (raf.readLine() != null){
                    i += 1;
                }
                raf.close();
            }catch(Exception ex){  }
            
            return i;
        }
        
/*        public void escribeContenido(String miArchivo , String cadena){
            try{
                  File archSalida = new File(creaPath(miArchivo) );
                  FileWriter salida = new FileWriter( archSalida );
            	  salida.write( cadena );
		  salida.close();
             }catch(IOException e){
                 e.printStackTrace(); 
            }     
        }	*/

        public RandomAccessFile abreArchivoRAF(String miArchivo,String opt1){
           try{
               return new RandomAccessFile(miArchivo, opt1);
            }catch(Exception e){} 
           return null;
        }
        
        public void appendContenido(String cadena){
            try{
               	  salida_.write( cadena ); 
	
             }catch(Exception e){
                 e.printStackTrace(); 
            }     
        }
        public void escribeContenido(String miArchivo , String cadena){
            try{
                  File archSalida = new File(miArchivo);
                  FileWriter salida = new FileWriter( archSalida );
            	  salida.write( cadena );
		  salida.close();
             }catch(IOException e){
                 e.printStackTrace(); 
            }     
        }	

        public File[] listaArchivos(String dirIn){
            dirIn = creaPath(dirIn);
            File[] archEntrada = new File(dirIn).listFiles();
            return archEntrada;
        }
        
         public File[]  listaArchivos(String dirIn, String filtro){
  
            Filter nf = new Filter (filtro);
    
            File dir = new File (dirIn);
            String[] strs = dir.list(nf);
    
            dirIn = creaPath(dirIn);
            File[] archEntrada = dir.listFiles(nf);
            return archEntrada;
        }
        
        
        
        
/*        public String creaNombreFecha (){
            ResultSet rs = null;
            String strFecha = "";
           try{
               
               rs = bd.rsSQL("set dateformat ymd  select rtrim(ltrim(convert(varchar(26),getdate(),126))) fecha");
               strFecha = rs.getString("Fecha").replace(':', '.');
           }catch(Exception e)  {
               e.printStackTrace();
           }
           return strFecha;
        }*/
        
        
        
    
}

  class Filter implements FilenameFilter {
      protected String pattern;
      public Filter (String str) {
        pattern = str;
      }
      public boolean accept (File dir, String name) {
        //return name.toLowerCase().endsWith(pattern.toLowerCase());
        String nombre = pattern.substring(0, pattern.indexOf("*"));
        String extension = pattern.substring( pattern.indexOf(".")+1 );
        if (extension.indexOf("*") != -1 )
            extension= extension.substring(0, extension.indexOf("*"));
     //   System.out.println(extension);
        //return (name.toLowerCase().indexOf(pattern.toLowerCase()) != -1);
        return ((name.toLowerCase().indexOf(nombre.toLowerCase()) != -1) && (name.toLowerCase().indexOf(extension.toLowerCase()) != -1));
      }
  
  }
  
  
  
 class FilterMul implements FilenameFilter {

  protected Set extensionsSet; 

  public FilterMul (String [] extensions) {
    extensionsSet = new TreeSet();

    for (Iterator ext=Arrays.asList(extensions).iterator(); ext.hasNext();) {
      extensionsSet.add(ext.next().toString().toLowerCase().trim());
    }
    extensionsSet.remove("");
  }


  public boolean accept (File dir, String name) {
    final Iterator exts = extensionsSet.iterator();
    while (exts.hasNext()) {
      if (name.toLowerCase().endsWith(exts.next().toString())) {
          return true;
      }
    }
    return false;
  }

}



