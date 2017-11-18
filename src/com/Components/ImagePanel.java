package com.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class ImagePanel extends JPanel{

    private BufferedImage image;
    private int[] pixels;

    public ImagePanel(int width, int height){
        super();

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        //remove current graphics that we are done with
        g.dispose();
    }

    public void setImage(int[] inputPixels){

        if(inputPixels.length != pixels.length){
            return; // TODO: This should also print some error or throw exception or W/E.
        }
        for(int i=0; i < inputPixels.length; i++){
            pixels[i] = inputPixels[i];
        }
    }

}
