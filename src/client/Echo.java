package client;

import java.io.*;
import java.net.UnknownHostException;

import rmi.EchoInt;

public class Echo {
  private static EchoInt eo;
  
  public static void main(String[] args) throws UnknownHostException {
    eo =  new EchoObjectStub();
	  
    BufferedReader stdIn = new BufferedReader( new InputStreamReader(System.in));
    PrintWriter stdOut = new PrintWriter(System.out);
    String input,output,fin;
    
    try {
    	//Bucle que lee de teclado, invoca el eco y escribe respuesta en la pantalla:
    	input="";
    	fin="fin";
    	while(!input.equals(fin)) {
    		stdOut.println("Escriba cadena para invocar su eco...");
    		stdOut.flush();
        	input = stdIn.readLine(); //Lee cadena introducida por teclado			
        	output = eo.echo(input);
        	stdOut.println(output); //Escribe la respuesta del eco en la pantalla
    		stdOut.flush();
        }  	
    } catch (IOException e) {
    	System.err.println("I/O failed for connection to host: "+args[0]);
    }
  }
}