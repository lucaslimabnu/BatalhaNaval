/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;
/**
 *
 * @author lucas
 */
public class Cenario {
    
    String area[][];
    
    Cenario(){
        area = new String[Configuracao.WIDTH][Configuracao.HEIGHT];
//      Popula com ondas ("");
        for(int i = 0; i < Configuracao.WIDTH; i++){
            for(int j = 0; j < Configuracao.HEIGHT; j++){
                area[i][j] = ".";
            }
        }
    }
    
    
    public Navio[] loadFleet(){
        Navio fleet[] = new Navio[Configuracao.SHIPS];
        for(int id_ship = 1; id_ship <= Configuracao.SHIPS; id_ship++ ){
            fleet[id_ship - 1] = new Navio(id_ship, this);
            
        }
        return fleet;
        
    }
    
    public String[][] backup(){
        
        String response[][] = 
            new String[Configuracao.WIDTH][Configuracao.HEIGHT];
        
        for(int i = 0; i < Configuracao.WIDTH; i++){
            for(int j = 0; j < Configuracao.HEIGHT; j++){
                response[i][j] = area[i][j];
            }
        }
        
        return response;
        
        
    }
    
    
}
