package com.Components;

import com.MainWin;
import com.SpriteSheet;
import com.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.nio.ByteBuffer;

public class MapViewPanel extends JPanel {

    private int heightInTiles;
    private int widthInTiles;
    private int[] tilesL1;
    private int[] tilesL2;
    private SpriteSheet spriteSheet;
    private int tilesize;

    private BufferedImage image;
    private int[] pixels;

    public MapViewPanel(int heightInTiles, int widthInTiles) {
        this.heightInTiles = heightInTiles;
        this.widthInTiles = widthInTiles;

        spriteSheet = SpriteSheet.overWorld;
        tilesize = spriteSheet.tilesize;

        tilesL1 = new int[widthInTiles * heightInTiles];

        for (int i = 0; i < tilesL1.length ; i++)
            tilesL1[i] = 70;

        tilesL2 = new int[widthInTiles * heightInTiles];

        for (int i = 0; i < tilesL2.length ; i++)
            tilesL2[i] = -1;

        image = new BufferedImage(widthInTiles * tilesize, heightInTiles * tilesize, BufferedImage.TYPE_INT_ARGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    }

    public void setTile(int xTile, int yTile, int tileIndex){
        if(xTile + yTile*widthInTiles >= tilesL1.length) return;
        if(MainWin.inputMode == 0 )
            tilesL1[xTile + yTile*widthInTiles] = tileIndex;
        if(MainWin.inputMode == 1)
            tilesL2[xTile + yTile*widthInTiles] = tileIndex;
    }

    public void paintFill(int xTile, int yTile, int tileIndex){
        if(MainWin.inputMode == 0) {
            if (xTile + yTile * widthInTiles >= tilesL1.length) return;
            if (tilesL1[xTile + yTile * widthInTiles] == tileIndex) return;

            paintFillRecourse(xTile, yTile, tileIndex, tilesL1[xTile + yTile * widthInTiles]);
        }
        if(MainWin.inputMode == 1) {
            if (xTile + yTile * widthInTiles >= tilesL2.length) return;
            if (tilesL2[xTile + yTile * widthInTiles] == tileIndex) return;

            paintFillRecourse(xTile, yTile, tileIndex, tilesL2[xTile + yTile * widthInTiles]);
        }
    }

    private void paintFillRecourse(int x, int y, int targetTile, int sourceTile){
        if((x<0) || (y<0) || (y==heightInTiles) || (x==widthInTiles)) return;
        if(MainWin.inputMode == 0) {
            if ((tilesL1[x + y * widthInTiles] != sourceTile) || (tilesL1[x + y * widthInTiles] == targetTile)) return;

            tilesL1[x + y * widthInTiles] = targetTile;
        }
        if(MainWin.inputMode == 1) {
            if ((tilesL2[x + y * widthInTiles] != sourceTile) || (tilesL1[x + y * widthInTiles] == targetTile)) return;

            tilesL2[x + y * widthInTiles] = targetTile;
        }
        paintFillRecourse(x+1,y,targetTile,sourceTile);
        paintFillRecourse(x-1,y,targetTile,sourceTile);
        paintFillRecourse(x,y+1,targetTile,sourceTile);
        paintFillRecourse(x,y-1,targetTile,sourceTile);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int yTile = 0; yTile < heightInTiles; yTile++) {
            for (int xTile = 0; xTile < widthInTiles; xTile++) {
               Tile.copyTile(pixels,xTile,yTile,widthInTiles*tilesize,spriteSheet.getTile(tilesL1[xTile + yTile*heightInTiles]),spriteSheet.alphacolor);
            }
        }

        for (int yTile = 0; yTile < heightInTiles; yTile++) {
            for (int xTile = 0; xTile < widthInTiles; xTile++) {
                Tile.copyTile(pixels,xTile,yTile,widthInTiles*tilesize,spriteSheet.getTile(tilesL2[xTile + yTile*heightInTiles]),spriteSheet.alphacolor);
            }
        }
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        //remove current graphics that we are done with
        g.dispose();
    }

    public ByteBuffer tilesToByteBuffer()
    {
        ByteBuffer bb = ByteBuffer.allocate(tilesL1.length * Integer.SIZE/Byte.SIZE);
        for(int i = 0; i < tilesL1.length; i++){
            bb.putInt(tilesL1[i]);
        }
        return bb;
    }
}
