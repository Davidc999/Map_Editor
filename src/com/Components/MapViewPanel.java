package com.Components;

import com.SpriteSheet;
import com.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class MapViewPanel extends JPanel {

    private int heightInTiles;
    private int widthInTiles;
    private int[] tiles;
    private SpriteSheet spriteSheet;
    private int tilesize;

    private BufferedImage image;
    private int[] pixels;

    public MapViewPanel(int heightInTiles, int widthInTiles) {
        this.heightInTiles = heightInTiles;
        this.widthInTiles = widthInTiles;

        spriteSheet = SpriteSheet.overWorld;
        tilesize = spriteSheet.TILESIZE;

        tiles = new int[widthInTiles * heightInTiles];

        image = new BufferedImage(widthInTiles * tilesize, heightInTiles * tilesize, BufferedImage.TYPE_INT_ARGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    }

    public void setTile(int xTile, int yTile, int tileIndex){
        tiles[xTile + yTile*widthInTiles] = tileIndex;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int yTile = 0; yTile < heightInTiles; yTile++) {
            for (int xTile = 0; xTile < widthInTiles; xTile++) {
               Tile.copyTile(pixels,xTile,yTile,widthInTiles*tilesize,spriteSheet.getTile(tiles[xTile + yTile*heightInTiles]));
            }
        }
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        //remove current graphics that we are done with
        g.dispose();
    }
}