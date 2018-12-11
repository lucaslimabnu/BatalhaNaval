package game;

import java.io.*;
import java.net.*;
public class TCPServer {
    
    String clientSentence;
    String serverSentence;
    ServerSocket welcomeSocket;
    Socket serverSocket;
    BufferedReader inFromUser;

    
    //from client
    BufferedReader inFromClient;

    //out to client the hit or miss message
    DataOutputStream outToClient1;
    
    TCPServer() throws IOException{
        inFromUser = new BufferedReader(
                    new InputStreamReader(System.in));
        
        welcomeSocket = new ServerSocket(6789);
        serverSocket  = welcomeSocket.accept();
        
        inFromClient = new BufferedReader(
                    new InputStreamReader(serverSocket.getInputStream()));
        
        outToClient1 = new DataOutputStream(serverSocket.getOutputStream());
        
    }
    
    public void send(String sentence) throws IOException{
        
        outToClient1.writeBytes(sentence+"\n");
        outToClient1.flush();    
    }
 

}