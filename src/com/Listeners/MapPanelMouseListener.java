package com.Listeners;

import com.Components.MapViewPanel;
import com.Components.TilePalette;
import com.Tile;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MapPanelMouseListener extends MouseAdapter implements MouseMotionListener{

    private MapViewPanel mapViewPanel;

    public MapPanelMouseListener(MapViewPanel mapViewPanel)
    {
        super();
        this.mapViewPanel = mapViewPanel;
    }

    public void mouseMoved(MouseEvent e){}

    public void mouseDragged(MouseEvent e){
        if(SwingUtilities.isLeftMouseButton(e)) {
            mapViewPanel.setTile(Tile.pixelToTile(e.getX()), Tile.pixelToTile(e.getY()), TilePalette.selectedIndex);
        }
        else if(SwingUtilities.isRightMouseButton(e)){
            mapViewPanel.setTile(Tile.pixelToTile(e.getX()), Tile.pixelToTile(e.getY()), 0);
        }


        mapViewPanel.repaint();
    }

    public void mouseClicked(MouseEvent e)
    {
        int mod = e.getModifiersEx();
        if (mod == MouseEvent.SHIFT_DOWN_MASK){
            //TODO: Fill!
            mapViewPanel.paintFill(Tile.pixelToTile(e.getX()), Tile.pixelToTile(e.getY()), TilePalette.selectedIndex);
        }
        else {
            if (e.getButton() == 1) {
                mapViewPanel.setTile(Tile.pixelToTile(e.getX()), Tile.pixelToTile(e.getY()), TilePalette.selectedIndex);
            } else {
                mapViewPanel.setTile(Tile.pixelToTile(e.getX()), Tile.pixelToTile(e.getY()), 0);
            }
        }

        mapViewPanel.repaint();
    }

}
