/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Utilerias;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author rarodriguez
 */
public class LeeArchivo {
    
    public void Lee(String archivo)
    {
         File f = new File( "D:/rarodriguez/Documents/semaforo.html" );
                    BufferedReader entrada;

                    System.out.println(f.toString());
                    System.out.println("Prueba de texto");
                        try {
                                entrada = new BufferedReader( new FileReader( f ) );
                                String linea;

                                    while(entrada.ready())
                                    {
                                        linea = entrada.readLine();
                                        System.out.println(linea);
                                    }
                        }catch (IOException e) {  e.printStackTrace(); }
              // return linea;
    }
        

}//CLASS
