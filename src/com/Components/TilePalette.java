package com.Components;

import com.SpriteSheet;
import com.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class TilePalette extends JPanel {

    public boolean selected = false;
    public int xSelected;
    public int ySelected;
    public static int selectedIndex;

    public SpriteSheet spriteSheet;
    private BufferedImage image;
    private int[] pixels;

    private int widthInTiles;
    private int heightInTIles;
    private int width;
    private int height;

    public TilePalette(int width, int height){
        super();

        spriteSheet = SpriteSheet.overWorld;
        this.width = width;
        this.height = height;
        widthInTiles = Tile.pixelToTile(width);
        heightInTIles = Tile.pixelToTile(height);
        image = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        load();
        //image =  ImageIO.read(TilePalette.class.getResource("/textures/malewizard.png"));

    }

    public void select(int xSelected, int ySelected){
        selected = true;
        this.xSelected = xSelected;
        this.ySelected = ySelected;
        selectedIndex = xSelected + ySelected * Tile.pixelToTile(getWidth());
    }

    public void deselect(){
        selected = false;
    }

    private void load()
    {
        for (int i=0; i < pixels.length; i++){
            pixels[i] = spriteSheet.pixels[i];
        }
    }

    public void repackImage()
    {
        widthInTiles = Tile.pixelToTile(width);
        heightInTIles = Tile.pixelToTile(height);
        for(int yss = 0; yss < spriteSheet.heightintiles; yss++){
            for(int xss = 0; xss < spriteSheet.widthintiles; xss++){
                Tile.copyTile(pixels,(xss+yss*spriteSheet.widthintiles) % widthInTiles,(xss+yss*spriteSheet.widthintiles) / widthInTiles,Tile.tileToPixel(widthInTiles),spriteSheet.getTile(xss,yss));
            }
        }
    }

    public int getTileIndex(int xTile,int yTile){
        return xTile + yTile * widthInTiles;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        g.setColor(Color.black);
        for(int x=0; x<widthInTiles; x++) {
            for(int y=0; y<heightInTIles; y++) {
                g.drawRect(Tile.tileToPixel(x), Tile.tileToPixel(y), Tile.tileToPixel(1)-1, Tile.tileToPixel(1)-1);
            }
        }

        if(selected){
            g.setColor(Color.red);
            g.drawRect(Tile.tileToPixel(xSelected),Tile.tileToPixel(ySelected),Tile.tileToPixel(1)-1,Tile.tileToPixel(1)-1);
        }
        //remove current graphics that we are done with
        g.dispose();
    }

}
