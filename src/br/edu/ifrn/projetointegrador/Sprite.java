package br.edu.ifrn.projetointegrador;

import javax.swing.ImageIcon;

/**
 *
 * @author Marcell Guilherme
 */
public class Sprite {

    ImageIcon cenas[]; 
    int x;
    int y; 
    int cena = 0;
    int controlaVelocidade = 0;
    int velocidade = 3;
    
    public Sprite(int numeroDeCenas, int x, int y){
        this.cenas = new ImageIcon[numeroDeCenas];
        this.x = x;
        this.y = y;
    }
    
    public void animar(){
        controlaVelocidade += 1;
        if (controlaVelocidade > velocidade){
            cena += 1;
            controlaVelocidade = 0;
            if (cena == cenas.length){
                cena = 0;
            }
        }
        
    }
    
}