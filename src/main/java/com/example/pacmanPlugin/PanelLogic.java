package com.example.pacmanPlugin;

import com.intellij.ui.components.JBPanel;

public class PanelLogic extends JBPanel<PanelLogic> {
    private int pacX=14;
    private int pacY=4;

    private int dx=0;
    private int dy=0;

    private final int[][] gameMap;
    private boolean isWon = false;
    private int dotsLeft=0;//?????????????

    public PanelLogic(){
        this.gameMap=new int[Map.MaP.length][Map.MaP[0].length];
        for ( int i=0; i<Map.MaP.length; i++){
            for(int j=0;j<Map.MaP[i].length;j++){
                gameMap[i][j]=Map.MaP[i][j];
                if (gameMap[i][j]==0) dotsLeft++;
            }
        }
    }

}
