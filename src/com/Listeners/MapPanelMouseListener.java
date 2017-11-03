package com.Listeners;

import com.Components.MapViewPanel;
import com.Components.TilePalette;
import com.Tile;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MapPanelMouseListener extends MouseAdapter {

    private MapViewPanel mapViewPanel;

    public MapPanelMouseListener(MapViewPanel mapViewPanel)
    {
        super();
        this.mapViewPanel = mapViewPanel;
    }

    public void mouseClicked(MouseEvent e)
    {
        if(e.getButton() == 1 ) {
            mapViewPanel.setTile(Tile.pixelToTile(e.getX()),Tile.pixelToTile(e.getY()),TilePalette.selectedIndex);
        }
        else
        {
            mapViewPanel.setTile(Tile.pixelToTile(e.getX()),Tile.pixelToTile(e.getY()),0);
        }

        mapViewPanel.repaint();
    }

}
