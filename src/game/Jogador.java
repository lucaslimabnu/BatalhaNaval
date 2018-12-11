/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;
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
    
    public boolean attackEnemy(Jogador player_attacked){
        
        int x = -1;
        int y = -1;
        do{
            showFieldShip();
            showFieldAttack();
            System.out.println("Vez de "+ name + ".");
            
            Scanner scan = new Scanner(System.in);
            do{
                System.out.println("Digite uma coordenada:");
                String target = scan.next();
                x = -1;
                y = Integer.parseInt(target.substring(1,target.length())) ;
                System.out.println("O jogador " + name + " atacou a coordenada "+ target);

                switch(target.substring(0,1).toUpperCase()){
                    case "A":
                        x = 0;
                        break;
                    case "B":
                        x = 1;
                        break;
                    case "C":
                        x = 2;
                        break;
                    case "D":
                        x = 3;
                        break;
                    case "E":
                        x = 4;
                        break;
                    case "F":
                        x = 5;
                        break;
                    case "G":
                        x = 6;
                        break;
                    case "H":
                        x = 7;
                        break;
                    case "I":
                        x = 8;
                        break;
                    case "J":
                        x = 9;
                        break;
                    case "K":
                        x = 10;
                        break;
                    case "L":
                        x = 11;
                        break;

                }
                System.out.println("Convertido para: "+ x +", "+ y);

            }while(!isValidAttack(x, y - 1, player_attacked.field_ship));

        // método de sucesso 
        }while(attack_success(x, y - 1, player_attacked));
        // caso seja, verificar se acertou algo
            // identificar o navio e verificar se ele foi afundado totalmente
           
        // colocar no mapa de ataques
        
        return true;
    }
    
    public boolean isValidAttack(int x, int y, Cenario field_attacked){
        
        // validar se o ataque está no range
        if(x >= 0 && x <= Configuracao.HEIGHT && 
           y >= 0 && y < Configuracao.WIDTH){
            // validar se é ataque repetido
            if(field_attack.area[x][y] != "."){
                return false;
            }
            else{
                return true;
            }
        }else{
            return false;
        }
        
    }
    
    public boolean attack_success(int x, int y, Jogador player_attacked){
        
        // se acertou navio
        if(player_attacked.field_ship.area[x][y] != "."){
            // identificar o navio
            int id_ship = Integer.parseInt(player_attacked.field_ship.area[x][y]);
            System.out.println("Você acertou o navio: "+ id_ship);
            
            // chamar o método de verificação do Navio
            field_attack.area[x][y] = ""+ id_ship;
            player_attacked.field_ship.area[x][y] = "X";
            player_attacked.fleet[id_ship - 1].receiveAttack(this, player_attacked);
            System.out.println(""+ name + " deveria jogar de novo.");
            return true;
        }
        player_attacked.field_ship.area[x][y] = "X";
        field_attack.area[x][y] = "X";
        return false;
            
            
        
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
