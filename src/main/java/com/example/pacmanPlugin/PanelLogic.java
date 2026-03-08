package com.example.pacmanPlugin;

import com.intellij.ui.components.JBPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.event.KeyListener;

public class PanelLogic extends JBPanel<PanelLogic> {
    private int pacX = 13;
    private int pacY = 7;

    private int dx = 0;
    private int dy = 0;

    private ImageIcon currentPacman = Icons.PacRight;

    private final int[][] gameMap;
    private boolean isWon = false;
    private int dotsLeft = 0;//?????????????

    public PanelLogic() {
        this.gameMap = new int[Map.MaP.length][Map.MaP[0].length];
        for (int i = 0; i < Map.MaP.length; i++) {
            for (int j = 0; j < Map.MaP[i].length; j++) {
                gameMap[i][j] = Map.MaP[i][j];
                if (gameMap[i][j] == 0) dotsLeft++;
            }
        }


        setFocusable(true);//??? get keybord focus

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (isWon) return;

                int key = e.getKeyCode();
                if (key == KeyEvent.VK_UP && camMove(0, -1)) {
                    dx = 0; dy = -1;
                    currentPacman = Icons.PacUp;
                } else if (key == KeyEvent.VK_DOWN && camMove(0, 1)) {
                    dx = 0; dy = 1;
                    currentPacman = Icons.PacDown;
                } else if (key == KeyEvent.VK_LEFT && camMove(-1, 0)) {
                    dx = -1; dy = 0;
                    currentPacman = Icons.PacLeft;
                } else if (key == KeyEvent.VK_RIGHT && camMove(1, 0)) {
                    dx = 1; dy = 0;
                    currentPacman = Icons.PacRight;
                }
            }
        });


        Timer timer = new Timer(150, e -> {
            if (!isWon) {
                movePAcMAn();
                repaint();
            }

        });
        timer.start();
    }

    private boolean camMove(int nextDx, int nextDy){
        int nextX=pacX+nextDx;
        int nextY=pacY+nextDy;

        return nextY>=0 &&
                nextY<gameMap.length &&
                nextX>=0 &&
                nextX< gameMap[0].length &&
                gameMap[nextY][nextX]!=1;
    }
    private void movePAcMAn(){
        if(camMove(dx,dy)) {
            pacX += dx;
            pacY += dy;

            if(gameMap[pacY][pacX]==0){
                gameMap[pacY][pacX]=2;
                dotsLeft--;
                if(dotsLeft<=0)isWon=true;
            }

        }
    }
    @Override
        protected void paintComponent(Graphics G){
        super.paintComponent(G);
        Graphics2D G2=(Graphics2D) G;
        for (int i = 0; i < Map.MaP.length; i++) {
            for (int j = 0; j < Map.MaP[i].length; j++) {
                int X=j*Map.CellSize;
                int Y=i*Map.CellSize;

                if(gameMap[i][j]==1){
                    G2.setColor(new Color(13, 13, 130));
                    G2.fillRect(X+2,Y+2,Map.CellSize-4,Map.CellSize-4);
                } else if (gameMap[i][j]==0){
                    G2.setColor(new Color(241, 186, 235));
                    G2.fillOval(X+8,Y+8,4,4);
                }
            }
        }
        //Icons.PacMan.paintIcon(this,G2,pacX*Map.CellSize,pacY*Map.CellSize);
        //G2.drawImage(((ImageIcon)Icons.PacMan).getImage(),pacX*Map.CellSize,pacY*Map.CellSize, Map.CellSize,Map.CellSize,this);
        G2.drawImage(currentPacman.getImage(), pacX * Map.CellSize, pacY * Map.CellSize, Map.CellSize, Map.CellSize, this);
        if(isWon){
            G2.setColor(Color.YELLOW);
            G2.setFont(new Font("Monospaced",Font.BOLD,30));
            G2.drawString("You Win!",150,20);
        }

    }

    }