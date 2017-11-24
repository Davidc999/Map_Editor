package com.Components;

import com.MainWin;
import com.SpriteSheet;
import com.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.nio.ByteBuffer;

import static java.lang.Math.abs;

public class TilePalette extends JPanel {

    public boolean selected = false;
    public int xSelected;
    public int ySelected;
    public static int selectedIndex;

    public SpriteSheet spriteSheet;
    private BufferedImage image;
    private int[] pixels;

    private int[] solid;
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

        solid = new int[widthInTiles * heightInTIles];

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

    public void toggleSolidity(int xTile, int yTile){
        solid[getTileIndex(xTile,yTile)] = 1 - solid[getTileIndex(xTile,yTile)];
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
                Tile.copyTile(pixels,(xss+yss*spriteSheet.widthintiles) % widthInTiles,(xss+yss*spriteSheet.widthintiles) / widthInTiles,Tile.tileToPixel(widthInTiles),spriteSheet.getTile(xss,yss),spriteSheet.alphacolor);
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

        if(MainWin.inputMode == 2){
            for (int i=0; i<solid.length; i++){
                if(solid[i] == 0)
                    ditherTile(spriteSheet.getTileX(i), spriteSheet.getTileY(i),Color.red,g);
                else
                    ditherTile(spriteSheet.getTileX(i), spriteSheet.getTileY(i),Color.green,g);
            }
        }

        //remove current graphics that we are done with
        g.dispose();
    }
    
    private void ditherTile(int xTile, int yTile, Color color, Graphics g) // Color format is 0xAARRGGBB (I think ;) )
    {
        int xOffset = Tile.tileToPixel(xTile);
        int yOffset = Tile.tileToPixel(yTile);
        int x1 = 0;
        int x2 = x1;
        int y1 = Tile.tileToPixel(1)-1; // Bottom left corner
        int y2 = y1;
        
        g.setColor(color);
        for(;y1 >= 0 ; y1-=2, x2+= 2){
                g.drawLine(xOffset+x1,yOffset+y1,xOffset+x2,yOffset+y2);
        }
        
        x1 = abs(y1 % 2);
        y1 = 0;
        y2 = 2*(Tile.tileToPixel(1)-1) - x2;
        x2 = Tile.tileToPixel(1)-1; // Top left to bottom right
        
        for(;y2 >= 0 ; y2-=2, x1+= 2){
                g.drawLine(xOffset+x1,yOffset+y1,xOffset+x2,yOffset+y2);
        }
    }

    public ByteBuffer solidToByteBuffer()
    {
        ByteBuffer bb = ByteBuffer.allocate(solid.length * Integer.SIZE/Byte.SIZE);
        for(int i = 0; i < solid.length; i++){
            bb.putInt(solid[i]);
        }
        return bb;
    }

}
