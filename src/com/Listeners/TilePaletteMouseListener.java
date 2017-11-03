package com.Listeners;

import com.Components.ImagePanel;
import com.Components.TilePalette;
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
        if(e.getButton() == 1 ) {
            tilePalette.select(Tile.pixelToTile(e.getX()),Tile.pixelToTile(e.getY()));

            imagePanel.setImage(SpriteSheet.overWorld.getTile(tilePalette.getTileIndex(Tile.pixelToTile( e.getX() ),Tile.pixelToTile( e.getY() ))));

            tilePalette.repaint();
            imagePanel.repaint();
        }
        else
        {
            tilePalette.deselect();
            tilePalette.repaint();
        }
    }
}
