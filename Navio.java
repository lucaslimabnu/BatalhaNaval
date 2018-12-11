/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Random;
/**
 *
 * @author lucas
 */
public class Navio {
    
    int id_ship;
    int life;
    int position[][];
    int direction;
    
    Navio(int id_ship, Cenario field){
        this.id_ship = id_ship;
        life = id_ship;
        position = new int[id_ship][2];
        positionIt(field);
    }
    
    public boolean hasCollide(int x, int y, String[][] field){
        try{
            if("." == field[x][y]){
                return false;
            }else{
                return true;
            }
        }catch(Exception e){
            return true;
        }
    }
    
    public void positionIt(Cenario field){
        
        Random random = new Random();
        
        direction = random.nextInt(2);
        
        if(direction == 0){
            // Navio na Horizontal
            // i se mantém, j cresce
            boolean invalid = false;
            int random_x, random_y;
            do{
                String backup[][] = field.backup();
                random_x = random.nextInt(Configuracao.WIDTH - id_ship -1);
                random_y = random.nextInt(Configuracao.HEIGHT - 1);
		invalid = hasCollide(random_x, random_y, backup);
                
                if(!invalid){
                    for(int i = 0; i < id_ship; i++){
                        invalid = hasCollide(random_x, random_y+i, backup);
                        if(!invalid){
                            
                            backup[random_x][random_y+i] = ""+id_ship;
                            position[i][0] = random_x;
                            position[i][1] = random_y+i;
                        }else{
                            position = new int[id_ship][2];
                            break;
                        }
                    }
                    if(!invalid){
                        for(int i = 0; i < backup.length; i++){
                            for(int j = 0; j < backup[i].length; j++){
                                field.area[i][j] = backup[i][j];
                            }
                        }
                    }
                }
                
            }while(invalid);
        }else{
            // Navio na Vertical
            // j se mantém, i cresce
            boolean invalid = false;
            int random_x, random_y;
            do{
                String backup[][] = field.backup();
                random_x = random.nextInt(Configuracao.WIDTH -1);
                random_y = random.nextInt(Configuracao.HEIGHT- id_ship - 1);
		invalid = hasCollide(random_x, random_y, backup);
                
                if(!invalid){
                    for(int i = 0; i < id_ship; i++){
                        invalid = hasCollide(random_x+i, random_y, backup);
                        if(!invalid){
                            
                            backup[random_x+i][random_y] = ""+id_ship;
                            position[i][0] = random_x+i;
                            position[i][1] = random_y;
                        }else{
                            position = new int[id_ship][2];
                            break;
                        }
                    }
                    if(!invalid){
                        for(int i = 0; i < backup.length; i++){
                            for(int j = 0; j < backup[i].length; j++){
                                field.area[i][j] = backup[i][j];
                            }
                        }
                    }
                }
                
            }while(invalid);
        }
           
                
        
    }
    
    
    public void receiveAttack(Jogador player_attacked){
        life -= 1;
        if(life == 0){
            for(int i = 0; i < position.length; i++){
                    player_attacked.field_ship.area[position[i][0]][position[i][1]] = "%";
            }
        }
    }  
}
