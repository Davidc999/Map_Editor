package com.Listeners;

import com.Components.TilePalette;
import com.MainWin;
import com.SpriteSheet;
import com.Tile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RadioButtonListener  implements ActionListener{

    private TilePalette palette;

    public RadioButtonListener(TilePalette palette){
        this.palette = palette;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Layer 1")){
            MainWin.inputMode = 0;
            palette.repaint();
        }
        if (e.getActionCommand().equals("Layer 2")){
            MainWin.inputMode = 1;
            palette.repaint();
        }
        if (e.getActionCommand().equals("Assign Solid")){
            MainWin.inputMode = 2;
            palette.repaint();
        }

    }
}
