package com.Components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

public class ImagePanel extends JPanel{

    private BufferedImage Image;
    private int[] Pixels;

    public ImagePanel(int width, int height){
        super();

        Image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Pixels = ((DataBufferInt)Image.getRaster().getDataBuffer()).getData();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(Image, 0, 0, getWidth(), getHeight(), null);
        //remove current graphics that we are done with
        g.dispose();
    }

    public void setImage(int[] inputPixels){

        if(inputPixels.length != Pixels.length){
            return; // TODO: This should also print some error or throw exception or W/E.
        }
        for(int i=0; i < inputPixels.length; i++){
            Pixels[i] = inputPixels[i];
        }
    }

}
