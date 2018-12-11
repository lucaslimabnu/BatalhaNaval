package game;

import java.io.*;
import java.net.*;

public class TCPClient {
    String ip = null;
    String sentence;
    String modifiedSentence;
    Socket clientSocket; // cria o websocket
    BufferedReader inFromUser; // input do usuário
    BufferedReader inFromServer; //lê do websocket
    DataOutputStream outToServer; // manda pro websocket
    
    TCPClient(String ip) throws IOException{
        this.ip = ip;
        
        inFromUser = new BufferedReader(new InputStreamReader(System.in));
        
        clientSocket = new Socket(ip, 6789);
        
        inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        outToServer = new DataOutputStream(clientSocket.getOutputStream());
        
    }

    TCPClient() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void send(String sentence) throws IOException{
        
        outToServer.writeBytes(sentence+"\n");
        outToServer.flush();
        
    }
    
	
//	public static void main(String argv[]) throws Exception
//	{
//		
//		
//		//from server	
//		
//		//out to server the hit or miss message
//		
//		while(true){
//			//the client sends a hit
//			
//			//the client receives a hit from the server
////			modifiedSentence = inFromServer.readLine();
////			System.out.println("Result from server: " + modifiedSentence);
////			
////			modifiedSentence = inFromServer.readLine();
////			System.out.println("Hit from server: " + modifiedSentence);
//			
//			// if(gameBoard.testHit(hitRow, hitCol)){
//			// 	gameBoard.testLoss();
//			// 	if(gameBoard.testLoss() == false){
//			// 		hit = miss = "You won!";
//			// 		System.out.println("Sorry, you lost!");
//			// 	}
//			// 	outToServer.writeBytes(hit+"\n");
//			// 	outToServer.flush();
//			// }
//			// else{
//			// 	outToServer.writeBytes(miss+"\n");
//			// 	outToServer.flush();
//			// }
//		}
//		
//	}
	
}