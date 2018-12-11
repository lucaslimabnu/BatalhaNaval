 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
/**
 *
 * @author lucas
 */
public class Game {
    
    boolean on_game = false;
    Jogador player1;
    Jogador player2;
    
    
    Game () throws UnknownHostException, IOException{
        letsPlay();
    }
    
    public static void main (String args[]) throws UnknownHostException, IOException{
        
        Game jogo = new Game();
        
    }
    
    @SuppressWarnings("empty-statement")
    public void letsPlay() throws UnknownHostException, IOException{
        on_game = true;
        introduction();
                
        while(on_game){
            clearScreen();
            
            // Ataque de um player
            while(!player1.attackEnemy(player2));
            
            // Ataque de um player
            while(!player2.attackEnemy(player1));
              
        }
        
    }
    
    public void introduction() throws UnknownHostException, IOException{
        
        Scanner scan = new Scanner(System.in);
        System.out.println("Este é o Batalha Naval (Versão TADS 01-2018)");
        System.out.println("Vamos começar...");
        System.out.println("Você deseja jogar como: \n1 - Server\n2 - Client");
        
        String ans = scan.next();
        
        if("1".equals(ans)){
            
            System.out.println("Beleza! Vou iniciar seu servidor.");
            System.out.println("Vamos aguardar seu oponente conectar...");
            TCPServer host = new TCPServer();
            System.out.println("Servidor inicializado com sucesso.\n ");
            System.out.println("Qual o seu nome?");
            player1 = new Jogador(scan.next());
            System.out.println("Ótimo, " + player1.name+"!");
            System.out.println("Você vai jogar contra: ");
            System.out.println(host.inFromClient.readLine());
            
            
        }else if("2".equals(ans)){
            
            System.out.println("Tenha certeza de que seu oponente já criou o servidor ;)");
            System.out.println("Beleza! Vou iniciar seu client.");
            System.out.println("Qual o IP do servidor? ");
            TCPClient host = new TCPClient(scan.next());
            System.out.println("Qual o seu nome?");
            player2 = new Jogador(scan.next());
            System.out.println("Ótimo, " + player1.name+"!");
            System.out.println("Você vai jogar contra: ");
            System.out.println(host.inFromServer.readLine());
            
        }
        
        System.out.println("Agora vamos ao que interessa...");
        System.out.println("Me permita somente conectar com o computador de seu oponente antes...");
        System.out.println("Conexão estabelecida!");
        System.out.println("Que vença o melhor capitão ;)");
        System.out.println("Você começa.");
        
    }
    
    public void clearScreen(){
        System.out.print("\033[2J");
        System.out.print("\033[1;1H");
    }
    
}
