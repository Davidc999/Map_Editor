package com;

public final class Tile {

    static SpriteSheet spriteSheet = SpriteSheet.overWorld;

    private Tile(){

    }

    public static int pixelToTile(int xp){
        return xp/spriteSheet.TILESIZE;
    }

    public static int tileToPixel(int xt){
        return xt*spriteSheet.TILESIZE;
    }

    public static void copyTile(int[] targetPixels, int targetTileX, int targetTileY,int targetWidth,int[] tilePixels){
        for(int y =0; y < spriteSheet.TILESIZE ; y++){
            for(int x =0; x < spriteSheet.TILESIZE ; x++){
                targetPixels[(targetTileX*spriteSheet.TILESIZE + x) + (targetTileY*spriteSheet.TILESIZE + y) * targetWidth] = tilePixels[ x +  y * spriteSheet.TILESIZE];
            }
        }

    }
}
