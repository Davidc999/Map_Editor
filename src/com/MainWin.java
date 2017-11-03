package com;

import com.Components.ImagePanel;
import com.Components.MapViewPanel;
import com.Components.TilePalette;
import com.Listeners.MapPanelMouseListener;
import com.Listeners.TilePaletteMouseListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MainWin {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private TilePalette tilePalette1;
    private ImagePanel imagePanel1;
    private MapViewPanel mapViewPanel1;
    private JMenuBar menubar;

    public MainWin()
    {
        tilePalette1.addMouseListener(new TilePaletteMouseListener(tilePalette1, imagePanel1));
        mapViewPanel1.addMouseListener(new MapPanelMouseListener(mapViewPanel1));
        createMenus();
    }

    private void createUIComponents() {
        imagePanel1 = new ImagePanel(16,16);
        mapViewPanel1 = new MapViewPanel(5,5);
        mapViewPanel1.setPreferredSize(new Dimension(5*16,5*16));
        tilePalette1 = new TilePalette(192,336);
        tilePalette1.repackImage();
    }

    private void createMenus(){
        menubar = new JMenuBar();

        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);

        JMenuItem eMenuItem = new JMenuItem("Exit");
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit application");
        eMenuItem.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });

        file.add(eMenuItem);

        menubar.add(file);



    }
    public static void main(String[] args)
    {
        MainWin mainWin = new MainWin();

        JFrame frame = new JFrame("Map Editor");
        frame.setContentPane(new MainWin().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setJMenuBar(mainWin.menubar);




    }

}
