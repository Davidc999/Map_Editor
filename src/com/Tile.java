package com;

public final class Tile {

    static SpriteSheet spriteSheet = SpriteSheet.overWorld;

    private Tile(){

    }

    public static int pixelToTile(int xp){
        return xp/spriteSheet.tilesize;
    }

    public static int tileToPixel(int xt){
        return xt*spriteSheet.tilesize;
    }

    public static void copyTile(int[] targetPixels, int targetTileX, int targetTileY,int targetWidth,int[] tilePixels){
        for(int y = 0; y < spriteSheet.tilesize; y++){
            for(int x = 0; x < spriteSheet.tilesize; x++){
                targetPixels[(targetTileX*spriteSheet.tilesize + x) + (targetTileY*spriteSheet.tilesize + y) * targetWidth] = tilePixels[ x +  y * spriteSheet.tilesize];
            }
        }

    }
}
