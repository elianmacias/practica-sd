package client;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import rmi.*;

public class EchoObjectStub implements EchoInt {

  private Socket echoSocket = null;
  private PrintWriter os = null;
  private BufferedReader is = null;
  private String host = "localhost";
  private int port=4500;
  private String output = "Error";

  public EchoObjectStub() {
	  }
  
  public EchoObjectStub(String host, int port) {
    this.host= host; this.port =port;
  }

  public String echo(String input)
  {
    connect(); 
    if (echoSocket != null && os != null && is != null) {
  	try {
             os.println(input);
             os.flush();
             output= is.readLine();
      } catch (IOException e) {
        System.err.println("I/O failed in reading/writing socket");
      }
    }
    disconnect();
    return output;
  }

  private synchronized void connect()
  {
      try {                
          echoSocket = new Socket(host,port);
           is = new BufferedReader( new InputStreamReader(echoSocket.getInputStream()));
            os = new PrintWriter(echoSocket.getOutputStream());    
      } catch (IOException ex) {
          Logger.getLogger(EchoObjectStub.class.getName()).log(Level.SEVERE, null, ex);
      }     
  }

  private synchronized void disconnect(){ 
      try 
      {
        echoSocket.close();
      } catch (IOException ex) {
          Logger.getLogger(EchoObjectStub.class.getName()).log(Level.SEVERE, null, ex);
      }    
  }
}
