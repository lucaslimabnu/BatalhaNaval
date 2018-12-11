/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;
import java.io.IOException;
import java.util.Scanner;
/**
 *
 * @author lucas
 */
public class Jogador {
    
    String name;
    Cenario field_ship;
    Cenario field_attack;
    Navio fleet[];
    
    Jogador (String name){
        this.name = name;
        field_ship = new Cenario();
        field_attack = new Cenario();
        fleet = field_ship.loadFleet();
    }
    
    //    #####                                     
    //   #     # ###### #####  #    # ###### #####  
    //   #       #      #    # #    # #      #    # 
    //    #####  #####  #    # #    # #####  #    # 
    //         # #      #####  #    # #      #####  
    //   #     # #      #   #   #  #  #      #   #  
    //    #####  ###### #    #   ##   ###### #    # 
    //    
    
    public boolean attackServerToClient(TCPServer host) throws IOException{
        
        //Método de ataque do servidor

        int x = -1;
        int y = -1;
        boolean success;
        do{
            
            // Mostra os campos
            showFieldShip();
            showFieldAttack();
            System.out.println("\rSua Vez");
            
            Scanner scan = new Scanner(System.in);
            do{
                System.out.println("Digite uma coordenada:");
                String target = scan.next();
                x = -1;
                y = Integer.parseInt(target.substring(1,target.length())) ;
                
                //Converte o X para inteiro
                x = xConverter(target);
                
            }while(!isValidAttack(x, y - 1));
            
            y--;
            //Envia o ataque ao cliente
            host.send(x+","+y);            
            
            String ataque;
            String response;

            // pega o retorno do ataque
            response = host.inFromClient.readLine();
            
            if(response.contains("ganhou")){
                System.out.println("Você ganhou!");
                System.exit(0);
                success = true;
            }else if(response.contains("Hit")){
                int id_ship = Integer.parseInt(response.split(":")[1]);
                field_attack.area[x][y] = ""+id_ship;
                System.out.println("Você acertou uma embarcação de tamanho "+ id_ship);
                success = true;
            }else{
                field_attack.area[x][y] = "X";
                System.out.println("Você errou o tiro.");
                success = false;
            }
            
        // método de sucesso 
        }while(success);
        
        return false;
        
    }
    
    public boolean ServerTakeAttack(TCPServer host) throws IOException{
        
        //Método de recebimento de ataque do servidor
        
        //Mostra os campos
        showFieldShip();
        showFieldAttack();

        String ataque;
        String response;
        boolean success;

        do{
            ataque = host.inFromClient.readLine();

            //retorna para ele o resultado
            response = receive_attack(ataque);
            host.send(response);
            if( response.contains("ganhou") ){
                System.out.println("Você perdeu.");
                System.exit(0);
                return false;
            }else if( response.contains("Hit") ){
                success = true; //tomou tiro
                System.out.println("Tomou tiro");
                return true;
            }else{
                success = false;
                System.out.println("Escapou");
                return false;
            }

        }while(success);
    }
    

    //    #####                               
    //   #     # #      # ###### #    # ##### 
    //   #       #      # #      ##   #   #   
    //   #       #      # #####  # #  #   #   
    //   #       #      # #      #  # #   #   
    //   #     # #      # #      #   ##   #   
    //    #####  ###### # ###### #    #   #   
    //                                        

    
    public boolean attackClientToServer(TCPClient host) throws IOException{
        
        int x = -1;
        int y = -1;
        boolean success;
        
        do{
            showFieldShip();
            showFieldAttack();
            
            Scanner scan = new Scanner(System.in);
            do{
                System.out.println("Digite uma coordenada:");
                String target = scan.next();
                x = -1;
                y = Integer.parseInt(target.substring(1,target.length()));

                x = xConverter(target);
                
            }while(!isValidAttack(x, y - 1));
            y--;
            
            //Envia o ataque e espera o retorno do resultado
            host.send(x+","+ y);
            
            String ataque;
            String response;
            
            response = host.inFromServer.readLine();
            
            if(response.contains("ganhou")){
                System.out.println(response);
                System.exit(0);
                success = true;
            }else if(response.contains("Hit")){
                int id_ship = Integer.parseInt(response.split(":")[1]);
                field_attack.area[x][y] = ""+id_ship;
                System.out.println("Você acertou uma embarcação de tamanho "+ id_ship);
                success = true;
            }else{
                field_attack.area[x][y] = "X";
                System.out.println("Você errou o tiro.");
                success = false;
            }            

        // método de sucesso 
        }while(success);
        
        return false;
        
    }
    
    public boolean ClientTakeAttack(TCPClient host) throws IOException{
        
        //recebe o ataque do servidor
            showFieldShip();
            showFieldAttack();
            
            String ataque;
            String response;
            boolean success;
            
            do{
                ataque = host.inFromServer.readLine();
            
                //retorna para ele o resultado
                response = receive_attack(ataque);
                host.send(response);
                if( response.contains("ganhou") ){
                    System.out.println("Você perdeu.");
                    System.exit(0);
                    return false;
                }else if( response.contains("Hit") ){
                    success = true; //tomou tiro
                    System.out.println("Tomou tiro");
                    return true;
                }else{
                    success = false;
                    System.out.println("Escapou");
                    return false;
                }
                
            }while(success);
    }
    
    
    public boolean isValidAttack(int x, int y){
        
        // validar se o ataque está no range
        if(x >= 0 && x <= Configuracao.HEIGHT && 
           y >= 0 && y < Configuracao.WIDTH){
            // validar se é ataque repetido
                return true;
        }else{
            return false;
        }
        
    }
    
    public String receive_attack(String attack){
        
        int x = Integer.parseInt(attack.substring(0,1));
        int y = Integer.parseInt(attack.substring(2,attack.length()));
        
        // se acertou navio
        if(this.field_ship.area[x][y] != "."  && this.field_ship.area[x][y] != "%"){
            // identificar o navio
            System.out.println(this.field_ship.area[x][y]);
            int id_ship = Integer.parseInt(this.field_ship.area[x][y]);
            System.out.println("Seu navio "+ id_ship +" foi acertado.");
            
            // chamar o método de verificação do Navio
//            field_attack.area[x][y] = ""+ id_ship;
            this.field_ship.area[x][y] = "X";
            this.fleet[id_ship - 1].receiveAttack(this);
            boolean jogo = false;
            for(int i = 0; i < fleet.length; i++){
                if(fleet[i].life > 0){
                    jogo = true;
                    break;
                }
            }
            if(!jogo){
                return "Você ganhou!";
            }
            return "Hit no navio:" + id_ship;
        }else{
            this.field_ship.area[x][y] = "X";
            return "Miss";
        }
            
            
        
    }
    
    public int xConverter(String target){
        switch(target.substring(0,1).toUpperCase()){
            case "A":
                return 0;
            case "B":
                return 1;
            case "C":
                return 2;
            case "D":
                return 3;
            case "E":
                return 4;
            case "F":
                return 5;
            case "G":
                return 6;
            case "H":
                return 7;
            case "I":
                return 8;
            case "J":
                return 9;
            case "K":
                return 10;
            case "L":
                return 11;
        }
        return 0;
    }
    
    public void showFieldShip(){
        System.out.println();
        System.out.println("Seu campo:");
        Imprimir.Matriz(field_ship.area);
    }
    
    public void showFieldAttack(){
        System.out.println();
        System.out.println("Seus ataques:");
        Imprimir.Matriz(field_attack.area);
    }
    
   
    
}
