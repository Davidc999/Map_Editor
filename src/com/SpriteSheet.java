package com;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {
    private String path;
    public final int WIDTH;
    public final int WIDTHINTILES;
    public final int HEIGHT;
    public final int HEIGHTINTILES;
    public final int TILESIZE;
    public int[] pixels;

    public static SpriteSheet tiles = new SpriteSheet("/textures/spritesheet.png",32,32,32);
    public static SpriteSheet maleWizard = new SpriteSheet("/textures/malewizard.png",128,128,32);
    public static SpriteSheet overWorld = new SpriteSheet("/textures/overworld.png",336,144,16);

    public SpriteSheet(String path, int width, int height, int tilesize){
        this.path = path;
        HEIGHT = height;
        WIDTH = width;
        TILESIZE = tilesize;
        HEIGHTINTILES = height / TILESIZE;
        WIDTHINTILES = width / TILESIZE;
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
        int tilePixels[] = new int[TILESIZE*TILESIZE];

        for(int scanY = 0; scanY < TILESIZE; scanY++){
            for(int scanX = 0; scanX < TILESIZE; scanX++){
                tilePixels[scanX + scanY * TILESIZE] = pixels[(scanX+xInTiles*TILESIZE) + (scanY+yInTiles*TILESIZE) * WIDTH];
            }
        }
        return tilePixels;
    }

    //Get a tile by serialnumber
    public int[] getTile(int serialNumber){
        int tilePixels[] = new int[TILESIZE*TILESIZE];

        for(int scanY = 0; scanY < TILESIZE; scanY++){
            for(int scanX = 0; scanX < TILESIZE; scanX++){
                tilePixels[scanX + scanY * TILESIZE] = pixels[(scanX+ (serialNumber % WIDTHINTILES) *TILESIZE) + (scanY+ (serialNumber/WIDTHINTILES) *TILESIZE) * WIDTH];
            }
        }
        return tilePixels;
    }
}
