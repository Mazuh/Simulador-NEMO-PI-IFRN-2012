package br.edu.ifrn.projetointegrador;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 *
 * @author Marcell Guilherme
 */

public class Menu {
    int cenario = -1;
    int itemSelecionado = 0;
    String itens[];
    Graphics bbg;
    boolean ativo; 
    int x;
    int y;
    int tamanhoDaFonte = 40;
    int distanciaEntreItens;
    Font fonte = new Font("Times New Roman", Font.BOLD, tamanhoDaFonte);
    Color corSelecionado = Color.RED;
    Color corNaoSelecionado = Color.WHITE;

    public Menu(int numeroDeItens, int x, int y, boolean ativo) {
        itens = new String[numeroDeItens];
        this.x = x;
        this.y = y;
        this.ativo = ativo;
    }
    
    public void controlar(KeyEvent e) {
        if (ativo) {
            controlarMenu(e);
        }

    }

    public void voltarAoMenu(KeyEvent e) {
        if (e.getKeyCode() == e.VK_ESCAPE) {
            cenario = -1;
            ativo = true;
        }

    }

    public void controlarMenu(KeyEvent e) {
        if (e.getKeyCode() == e.VK_UP) {
            itemSelecionado -= 1;
        }
        if (e.getKeyCode() == e.VK_DOWN) {
            itemSelecionado += 1;
        }
        if (itemSelecionado >= itens.length) {
            itemSelecionado = 0;
        }
        if (itemSelecionado < 0) {
            itemSelecionado = itens.length - 1;
        }
        if (e.getKeyCode() == e.VK_ENTER) {
            cenario = itemSelecionado;
            ativo = false;
        }

    }

    public void desenharMenu() {
        bbg.setFont(fonte);
        for (int i = 0; i < itens.length; i++) {
            if (itemSelecionado == i) {
                bbg.setColor(corSelecionado);
                bbg.drawString(itens[i], x, y + (i * (tamanhoDaFonte + distanciaEntreItens)));
            } else {
                bbg.setColor(corNaoSelecionado);
                bbg.drawString(itens[i], x, y + i * (tamanhoDaFonte + distanciaEntreItens));
            }    
        }
        
    }
    
}