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
    private int[][] tiles;
    private SpriteSheet spriteSheet;
    private int tileSize;
    private boolean gridOn;

    private BufferedImage image;
    private int[] pixels;

    public MapViewPanel(int heightInTiles, int widthInTiles) {
        this.heightInTiles = heightInTiles;
        this.widthInTiles = widthInTiles;

        spriteSheet = SpriteSheet.overWorld;
        tileSize = spriteSheet.tilesize;

        tiles = new int[2][widthInTiles * heightInTiles];

        for (int i = 0; i < tiles[0].length ; i++)
            tiles[0][i] = 70;

        for (int i = 0; i < tiles[1].length ; i++)
            tiles[1][i] = -1;

        image = new BufferedImage(widthInTiles * tileSize, heightInTiles * tileSize, BufferedImage.TYPE_INT_ARGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    }

    public void setTile(int xTile, int yTile, int tileIndex){
        if(MainWin.inputMode != 2 ) {
            if (xTile + yTile * widthInTiles >= tiles[MainWin.inputMode].length) return;
            tiles[MainWin.inputMode][xTile + yTile * widthInTiles] = tileIndex;
        }
    }

    public void paintFill(int xTile, int yTile, int tileIndex){
        if(MainWin.inputMode != 2) {
            if (xTile + yTile * widthInTiles >= tiles[MainWin.inputMode].length) return;
            if (tiles[MainWin.inputMode][xTile + yTile * widthInTiles] == tileIndex) return;

            paintFillRecourse(xTile, yTile, tileIndex, tiles[MainWin.inputMode][xTile + yTile * widthInTiles]);
        }
    }

    private void paintFillRecourse(int x, int y, int targetTile, int sourceTile){
        if((x<0) || (y<0) || (y==heightInTiles) || (x==widthInTiles)) return;
        if ((tiles[MainWin.inputMode][x + y * widthInTiles] != sourceTile) || (tiles[MainWin.inputMode][x + y * widthInTiles] == targetTile)) return;

        tiles[MainWin.inputMode][x + y * widthInTiles] = targetTile;

        paintFillRecourse(x+1,y,targetTile,sourceTile);
        paintFillRecourse(x-1,y,targetTile,sourceTile);
        paintFillRecourse(x,y+1,targetTile,sourceTile);
        paintFillRecourse(x,y-1,targetTile,sourceTile);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int yTile = 0; yTile < heightInTiles; yTile++) {
            for (int xTile = 0; xTile < widthInTiles; xTile++) {
               Tile.copyTile(pixels,xTile,yTile,widthInTiles* tileSize,spriteSheet.getTile(tiles[0][xTile + yTile*heightInTiles]),spriteSheet.alphacolor);
            }
        }

        for (int yTile = 0; yTile < heightInTiles; yTile++) {
            for (int xTile = 0; xTile < widthInTiles; xTile++) {
                Tile.copyTile(pixels,xTile,yTile,widthInTiles* tileSize,spriteSheet.getTile(tiles[1][xTile + yTile*heightInTiles]),spriteSheet.alphacolor);
            }
        }
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);

        if(gridOn){
            g.setColor(Color.black);
            for(int x=0; x<widthInTiles; x++) {
                for(int y=0; y<heightInTiles; y++) {
                    g.drawRect(Tile.tileToPixel(x), Tile.tileToPixel(y), Tile.tileToPixel(1)-1, Tile.tileToPixel(1)-1);
                }
            }
        }
        //remove current graphics that we are done with
        g.dispose();
    }

    public ByteBuffer tilesToByteBuffer()
    {
        ByteBuffer bb = ByteBuffer.allocate(tiles.length*tiles[0].length * Integer.SIZE/Byte.SIZE);
        for(int j =0;  j<tiles.length ; j++){
           for (int i = 0; i < tiles[0].length; i++) {
                bb.putInt(tiles[j][i]);
            }
        }
        return bb;
    }

    public void toggleGrid(){ gridOn = !gridOn;}


}
