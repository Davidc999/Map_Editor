package com;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {
    private String path;
    public int width;
    public int widthintiles;
    public int height;
    public int heightintiles;
    public int tilesize;
    public int[] pixels;

    public static SpriteSheet tiles = new SpriteSheet("/textures/spritesheet.png",32,32,32);
    public static SpriteSheet maleWizard = new SpriteSheet("/textures/malewizard.png",128,128,32);
    public static SpriteSheet overWorld = new SpriteSheet("/textures/overworld.png",336,144,16);

    public SpriteSheet(String path, int width, int height, int tilesize){
        this.path = path;
        this.height = height;
        this.width = width;
        this.tilesize = tilesize;
        heightintiles = height / this.tilesize;
        widthintiles = width / this.tilesize;
        pixels = new int[width * height];
        load();
    }

    public void changeSprite(String path, int width, int height, int tilesize){
        this.path = path;
        this.height = height;
        this.width = width;
        this.tilesize = tilesize;
        heightintiles = height / this.tilesize;
        widthintiles = width / this.tilesize;
        pixels = new int[width * height];
        load();
    }

    private void load(){
        try {
            BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0,0, w, h, pixels, 0 , w);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Get a tile by (x,y)
    public int[] getTile(int xInTiles, int yInTiles){
        int tilePixels[] = new int[tilesize * tilesize];

        for(int scanY = 0; scanY < tilesize; scanY++){
            for(int scanX = 0; scanX < tilesize; scanX++){
                tilePixels[scanX + scanY * tilesize] = pixels[(scanX+xInTiles* tilesize) + (scanY+yInTiles* tilesize) * width];
            }
        }
        return tilePixels;
    }

    //Get a tile by serialnumber
    public int[] getTile(int serialNumber){
        int tilePixels[] = new int[tilesize * tilesize];

        for(int scanY = 0; scanY < tilesize; scanY++){
            for(int scanX = 0; scanX < tilesize; scanX++){
                tilePixels[scanX + scanY * tilesize] = pixels[(scanX+ (serialNumber % widthintiles) * tilesize) + (scanY+ (serialNumber/ widthintiles) * tilesize) * width];
            }
        }
        return tilePixels;
    }

    //Get tile X by serialNumber

}
