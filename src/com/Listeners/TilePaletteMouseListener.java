package com.Listeners;

import com.Components.ImagePanel;
import com.Components.TilePalette;
import com.MainWin;
import com.SpriteSheet;
import com.Tile;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TilePaletteMouseListener extends MouseAdapter{

    private TilePalette tilePalette;
    private ImagePanel imagePanel;

    public TilePaletteMouseListener(TilePalette tilePalette, ImagePanel imagePanel)
    {
        super();
        this.tilePalette = tilePalette;
        this.imagePanel = imagePanel;
    }

    public void mouseClicked(MouseEvent e)
    {
        if(e.getButton() == 1 && MainWin.inputMode != 2) {
            tilePalette.select(Tile.pixelToTile(e.getX()),Tile.pixelToTile(e.getY()));

            imagePanel.setImage(SpriteSheet.overWorld.getTile(tilePalette.getTileIndex(Tile.pixelToTile( e.getX() ),Tile.pixelToTile( e.getY() ))));

            tilePalette.repaint();
            imagePanel.repaint();
        }
        else if(e.getButton() == 1){ // Solidify mode
            tilePalette.deselect();
            tilePalette.toggleSolidity(Tile.pixelToTile(e.getX()),Tile.pixelToTile(e.getY()));

            tilePalette.repaint();
        }
        else if(e.getButton() != 1)
        {
            tilePalette.deselect();
            tilePalette.repaint();
        }
    }
}
