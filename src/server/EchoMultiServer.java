package server;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EchoMultiServer {
    private static ServerSocket serverSocket = null;

    public static void main(String[] args) {

      try {
           serverSocket = new ServerSocket(4500);
      } catch (IOException e) {
           System.out.println("EchoMultiServer: could not listen on port: 4500, " + e.toString());
           System.exit(1);
      }
      System.out.println("Multiservidor Echo escuchando en el puerto: 4500");

      boolean listening = true;
      while (listening) {
          try {               
                    var cs = serverSocket.accept();              
                EchoMultiServerThread clientSocket = new EchoMultiServerThread(cs);
                clientSocket.run();
          } catch (IOException ex) {
              Logger.getLogger(EchoMultiServer.class.getName()).log(Level.SEVERE, null, ex);
          }	
     }
     try {
          serverSocket.close();
     } catch (IOException e) {
          System.err.println("Could not close server socket." + e.getMessage());
     }
   }
}

//----------------------------------------------------------------------------
//  class EchoMultiServerThread
//----------------------------------------------------------------------------

class EchoMultiServerThread extends Thread {
    private EchoObject eo;
    private Socket clientSocket = null;
    private String myURL = "localhost";
    private BufferedReader is = null;
    private PrintWriter os = null;
    private String inputline = new String(); 
    
    EchoMultiServerThread(Socket socket) {
        super("EchoMultiServerThread");
        clientSocket = socket;
        eo = new EchoObject();
        try {
        	is = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
        	os = new PrintWriter(clientSocket.getOutputStream());			
        } catch (IOException e) {
            System.err.println("Error sending/receiving" + e.getMessage());
            e.printStackTrace();
        }
        try {
           myURL=InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
           System.out.println("Unknown Host :" + e.toString());
           System.exit(1);
       }
    }

    public void run() {        
       try {
            while ((inputline = is.readLine()) != null) {
                System.out.println("Mensaje=> " + inputline);
           	String res=eo.echo(inputline);           	
            	os.println(res);
                os.flush();	
            }          
            os.close();
            is.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error sending/receiving: " + e.getMessage());
            e.printStackTrace();
        }
    }
}