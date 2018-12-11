 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    Jogador player;
    
    
    Game () throws UnknownHostException, IOException{
        introduction();
    }
    
    public static void main (String args[]) throws UnknownHostException, IOException{
        
        Game jogo = new Game();
        
    }
    
    @SuppressWarnings("empty-statement")
    public void letsPlayServer(TCPServer host) throws UnknownHostException, IOException{
        on_game = true;
            //Servidor
        while(on_game){
            clearScreen();
            
            System.out.println("Ataca!!");
            do{
                clearScreen();
            }while(player.attackServerToClient(host));
            
            System.out.println("Recebe!!");
            do{
                clearScreen();
            }while(player.ServerTakeAttack(host));

        }

    }
    @SuppressWarnings("empty-statement")
    public void letsPlayClient(TCPClient host) throws UnknownHostException, IOException{
        on_game = true;
            //Servidor
        while(on_game){
            clearScreen();
            
            System.out.println("Recebe!!");
            do{
                clearScreen();
            }while(player.ClientTakeAttack(host));
            
            System.out.println("Ataca!!");
            do{
                clearScreen();
            }while(player.attackClientToServer(host));

        }

    }
    
    public void introduction() throws UnknownHostException, IOException{
        
        int type = 0;
        Scanner scan = new Scanner(System.in);
        System.out.println("Este é o Batalha Naval (Versão TADS 01-2018)");
        System.out.println("Vamos começar...");
        System.out.println("Você deseja jogar como: \n1 - Server\n2 - Client");
        
        String ans = scan.next();
        
        if("1".equals(ans)){
            
            System.out.println("Beleza! Vou iniciar seu servidor.");
            System.out.println("Vamos aguardar seu oponente conectar...");
            final TCPServer host = new TCPServer();
            System.out.println("Servidor inicializado com sucesso.\n ");
            System.out.println("Qual o seu nome?");
            player = new Jogador(scan.next());
            System.out.println("Ótimo, " + player.name+"!");
            host.send(player.name);
            System.out.println("Você vai jogar contra: ");
            System.out.print(host.inFromClient.readLine());
            
            System.out.println("Agora vamos ao que interessa...");
            System.out.println("Que vença o melhor capitão ;)");
            System.out.println("Você começa.");
            
            letsPlayServer(host);
            
            
            
            
        }else if("2".equals(ans)){
            
            System.out.println("Tenha certeza de que seu oponente já criou o servidor ;)");
            System.out.println("Beleza! Vou iniciar seu client.");
            System.out.println("Qual o IP do servidor? ");
            TCPClient host = new TCPClient(scan.next());
            System.out.println("Qual o seu nome?");
            player = new Jogador(scan.next());
            System.out.println("Ótimo, " + player.name+"!");
            host.send(player.name);
            System.out.println("Você vai jogar contra: ");
            System.out.print(host.inFromServer.readLine());
            
            System.out.println("Agora vamos ao que interessa...");
            System.out.println("Que vença o melhor capitão ;)");
            System.out.println("O adversário começa.");
            
            letsPlayClient(host);
            
            
        }else{
            System.exit(0);
        }
        
        
    }
    
    public static void clearScreen(){
        System.out.print("\033[2J");
        System.out.print("\033[1;1H");
    }
    
}
