package br.edu.ifrn.projetointegrador;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcell Guilherme
 *
 */

public class AplicativoExe extends JFrame implements KeyListener {

    BufferedImage backBuffer;
    int FPS = 24;
    int janelaW = 800;
    int janelaH = 500;
    
    boolean tabletAtivo = false;
    int xLinha = 400;
    int contador = 0;
    int posicao1 = 0;
    int posicao2 = 0;
    boolean andando = false;
    String direcao = "direita";
    boolean jaAvisouCima = false;
    boolean jaAvisouBaixo = false;
    
    SimpleDateFormat relogio = new SimpleDateFormat("HH:mm");
   
    Menu menuPrincipal = new Menu(3, 25, 360, true);
    
    int xCartman = 400;
    int yCartman = 150;
    Sprite cartmanEsquerda = new Sprite(4, xCartman, yCartman);
    Sprite cartmanDireita = new Sprite(4, xCartman, yCartman);
    
    ImageIcon tablet = new ImageIcon("lib/imagens/cenario/tablet.gif");
    String distancia = "OK";
    double aux = 0;
    ImageIcon fundoCidade = new ImageIcon("lib/imagens/cenario/cidadegrande.png");
    int xFundoCidade = -800;
    
    ImageIcon fundoMenu = new ImageIcon("lib/imagens/cenario/menu.png");
    ImageIcon creditos = new ImageIcon("lib/imagens/cenario/creditos.png");
    
    ImageIcon carro1 = new ImageIcon("lib/imagens/cenario/carro1.gif");
    int xCarro1 = 500;
    int yCarro1 = 275;
    ImageIcon carro2 = new ImageIcon("lib/imagens/cenario/carro2.gif");
    int xCarro2 = 300;
    int yCarro2 = 180;
    
    public void transito(){
        xCarro1 += 10;
        xCarro2 -= 5;
        
        if (xCarro1 > 900)
            xCarro1 = -100;
        
        if (xCarro2 < -200)
            xCarro2 = 900;
        
    }
    
    Sequencer player;
    String musicaFundo = "lib/sons/fundo.mid";
    
    public void tocarMusica(String nome, int repetir) {
        try {
            player = MidiSystem.getSequencer();
            Sequence musica = MidiSystem.getSequence(new File(nome));
            player.open();
            player.setSequence(musica);
            player.setLoopCount(repetir);
            player.start();
        } catch (Exception e){
            System.out.println("Erro ao tocar: " + nome);
        }
    }
    
    public void cenarios() {
        Graphics bbg = backBuffer.getGraphics();
        bbg.setFont(new Font("Times New Roman", Font.BOLD, 14));
        bbg.setColor(Color.BLACK);
        switch (menuPrincipal.cenario) {
            case 0: // Cena 1
                bbg.drawImage(fundoCidade.getImage(), xFundoCidade, 0, this);
                if (direcao.equalsIgnoreCase("direita") && andando == true){
                    bbg.drawImage(cartmanDireita.cenas[cartmanDireita.cena].getImage(), xCartman, yCartman, this);
                    cartmanDireita.animar();
                }
                if (direcao.equalsIgnoreCase("esquerda") && andando == true){
                    bbg.drawImage(cartmanEsquerda.cenas[cartmanEsquerda.cena].getImage(), xCartman, yCartman, this);
                    cartmanEsquerda.animar();
                }
                if (andando == false){
                    ImageIcon estatica = new ImageIcon("lib/imagens/sprite/cartman" + direcao + "2.gif");
                    bbg.drawImage(estatica.getImage(), xCartman, yCartman, this);
                }
                if (!distancia.equalsIgnoreCase("OK")){
                    bbg.setColor(Color.RED);
                    bbg.drawLine(xLinha, 0, xLinha, 215);
                    bbg.setColor(Color.BLACK);
                }
                bbg.drawImage(carro2.getImage(), xCarro2, yCarro2, this);
                bbg.drawImage(carro1.getImage(), xCarro1, yCarro1, this);
                if (tabletAtivo == true) {
                    bbg.drawImage(tablet.getImage(), 0, 0, this);
                    bbg.drawString(relogio.format(new Date()), 300, 258);
                    bbg.setFont(new Font("Arial", Font.BOLD, 20));
                    bbg.drawString(distancia, 385, 443);
                }
                break;
            case 1: // Créditos
                bbg.drawImage(creditos.getImage(), 0, 20, this);
                break;
            case 2: // Sair
                JOptionPane.showMessageDialog(null, "Você selecionou \"Sair\".\nTchau!");
                System.exit(0);
        }

    }

    public void ativarTablet() {
        if (tabletAtivo == false) {
            tabletAtivo = true;
        } else {
            tabletAtivo = false;
        }
    }

    public void atualizar() {
        transito();
        
        contador++;
        if (contador % 2 == 0)
            posicao1 = xCartman + yCartman + xFundoCidade;
        else
            posicao2 = xCartman + yCartman + xFundoCidade;
        
        if (posicao1 == posicao2)
            andando = false;
        else
            andando = true;
        
        if (xFundoCidade > 0)
            xFundoCidade = 0;
        if (xFundoCidade < -1595)
            xFundoCidade = -1595;
            
        if (yCartman < 140){
            yCartman = 140;
            if (jaAvisouCima == false){
                JOptionPane.showMessageDialog(null, "Os vizinhos não vão gostar"
                    + " dessa invasão de privacidade,\nmantenha distância das casas"
                    + " e permaneça na calçada. =)");
                jaAvisouCima = true;
            }
        }
        
        if (yCartman > 160){
            yCartman = 160;
            if (jaAvisouBaixo == false) {
                JOptionPane.showMessageDialog(null, "Opa! Crianças não devem atravessar"
                    + " a rua sozinhas, \nolhe quantos carros"
                    + " perigosos, sua criatura imprudente!");
                jaAvisouBaixo = true;
            }
        }
        
        if (xCartman <= -5)
            xCartman = 5;
        if (xCartman >= 765)
            xCartman = 765;
        
        if (xCartman != 400){
            if (xCartman < 400){
                aux = (xCartman - 400);
                if (aux < 0)
                    aux = -aux;
                distancia = String.valueOf((int) ((aux / 10) / 5) + 18) + " m";
            }
            if (xCartman > 400){
                aux = (xCartman - 400);
                distancia = String.valueOf((int) ((aux / 10) / 5) + 20) + " m";
            }
        } else {
            distancia = "OK";
        }
    }

    public void desenharGraficos() {
        Graphics g = getGraphics();
        Graphics bbg = backBuffer.getGraphics();

        bbg.drawImage(fundoMenu.getImage(), 0, 20, this);

        menuPrincipal.desenharMenu();
        cenarios();
       
        g.drawImage(backBuffer, 0, 0, this);

    }

    public void inicializar() {
        setTitle("Projeto Integrador 2012 - Simulação da Pulseira Inteligente [REMAKE]");
        setSize(janelaW, janelaH);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);
        setLocationRelativeTo(null);
        backBuffer = new BufferedImage(janelaW, janelaH, BufferedImage.TYPE_INT_RGB);

        
        addKeyListener(this);
        
        for (int i = 0; i < cartmanDireita.cenas.length; i++){
            cartmanDireita.cenas[i] = new ImageIcon("lib/imagens/sprite/cartmandireita" + (i+1) + ".gif");
        }
        
        for (int i = 0; i < cartmanEsquerda.cenas.length; i++){
            cartmanEsquerda.cenas[i] = new ImageIcon("lib/imagens/sprite/cartmanesquerda" + (i+1) + ".gif");
        }
        
        menuPrincipal.itens[0] = "Simulação";
        menuPrincipal.itens[1] = "Créditos";
        menuPrincipal.itens[2] = "Sair";

        menuPrincipal.bbg = backBuffer.getGraphics();
        
        tocarMusica(musicaFundo, 999);
        
        JOptionPane.showMessageDialog(null, "Olá, usuário. :)\n\n"
                + "Este é o simulador do uso da Pulseira Inteligente,\n"
                + "um dos Projetos Integradores de 2012. Inicialmente, esta\n"
                + "humilde aplicação foi desenvolvida na plataforma Flash,\n"
                + "no entanto neste momento você estará usando a versão\n"
                + "em Java, a mais atual.\n\n"
                + "A nova função \"AJUDA\" pode ser ativada a qualquer\n"
                + "momento apertando a tecla H do seu teclado. Então caso\n"
                + "esteja com dúvidas sobre o que fazer em determinado\n"
                + "instante, aperte o botão H no seu computador para que\n"
                + "instruções e explicações surjam na sua tela.\n"
                + "\nAtt,\nDesenvolvedores.");
        
    }

    public void run() {
        inicializar();
        while (true) {
            atualizar();
            desenharGraficos();
            try {
                Thread.sleep(1000 / FPS);
            } catch (Exception e) {
                System.out.println("Thread interrompida.");
            }
        }

    }

    public static void main(String[] args) {
        AplicativoExe app = new AplicativoExe();
        app.run();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        menuPrincipal.controlar(e);
        menuPrincipal.voltarAoMenu(e);

        if (e.getKeyCode() == e.VK_SPACE && menuPrincipal.cenario != -1) {
            ativarTablet();
        }
        
        if (menuPrincipal.cenario == 0){
            if (e.getKeyCode() == e.VK_LEFT){
                direcao = "esquerda";
                if (xFundoCidade >= 0){
                    xCartman -= 5;
                }else {
                    xFundoCidade += 5;
                }
            }
            if (e.getKeyCode() == e.VK_RIGHT){
                direcao = "direita";
                if (xFundoCidade <= -1595){
                    xCartman += 5;
                }else{
                    xFundoCidade -= 5;
                }
            }
            if (e.getKeyCode() == e.VK_DOWN){
                yCartman += 5;
            }
            if (e.getKeyCode() == e.VK_UP){
                yCartman -= 5;
            }
        }
        
        if (e.getKeyCode() == e.VK_H){
            switch(menuPrincipal.cenario){
                case -1:
                    JOptionPane.showMessageDialog(null, "AJUDA\n\n"
                            + "Você está no Menu Inicial, é a partir dele que\n"
                            + "as outras janelas podem ser acessadas.\n\n"
                            + "Selecione a opção desejada através das setas\n"
                            + "do seu teclado (a de cima e a de baixo) e quando\n"
                            + "o que você quiser fazer estiver marcado de vermelho,\n"
                            + "aperte a tecla Enter.");
                    break;
                case 0:
                    JOptionPane.showMessageDialog(null, "AJUDA\n\n"
                            + "Você está dentro da simulação, aqui você controla\n"
                            + "o personagem virtual que anda através da calçada\n"
                            + "da rua dele. Ele está usando a Pulseira Inteligente\n"
                            + "e o pai dele possui um display que monitora a distância\n"
                            + "em que ele se afasta. Caso o personagem tente ultrapassar\n"
                            + "esse limite, o aparelho irá emitir um aviso sonoro e\n"
                            + "exibirá a distância entre o display e a pulseira.\n\n"
                            + "Para controlar o personagem, use as setas do seu\n"
                            + "teclado (baixo, cima, esquerda, direita).\n"
                            + "Para ver o display, aperte a barra de Espaço.\n"
                            + "E caso queira voltar ao menu inicial, tecle Esc.");
                    break;
                case 1:
                    JOptionPane.showMessageDialog(null, "AJUDA\n\n"
                            + "Opa. Somos \"tímidos\", por isso colocamos nossos nomes\n"
                            + "em letras garrafais ao lado de nossas respectivas\n"
                            + "funções aqui.\n"
                            + "Somos estudantes do Instituto Federal de Educação,\n"
                            + "Ciência e Tecnologia do Rio Grande do Norte, mais\n"
                            + "especificamente do campus da zona norte de Natal.\n\n"
                            + "Para voltar ao menu inicial, pressione Esc aí\n"
                            + "no seu teclado.");
                    break;
            }
        }

        if (direcao.equalsIgnoreCase("direita") && xCartman < 400){
            xCartman += 5;
            xFundoCidade += 5;
        }
        if (direcao.equalsIgnoreCase("esquerda") && xCartman > 400){
            xCartman -= 5;
            xFundoCidade -= 5;
        }
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}