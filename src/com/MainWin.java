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
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;

public class MainWin {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private TilePalette tilePalette1;
    private ImagePanel imagePanel1;
    private MapViewPanel mapViewPanel1;
    private JMenuBar menubar;
    private JScrollPane scrollPane;
    private MapPanelMouseListener listen;

    final JFileChooser fc = new JFileChooser();


    public MainWin()
    {
        tilePalette1.addMouseListener(new TilePaletteMouseListener(tilePalette1, imagePanel1));
        mapViewPanel1.addMouseListener(listen = new MapPanelMouseListener(mapViewPanel1));
        mapViewPanel1.addMouseMotionListener(listen);
        createMenus();
    }

    private void createUIComponents() {
        imagePanel1 = new ImagePanel(16,16);
        mapViewPanel1 = new MapViewPanel(50,50);
        tilePalette1 = new TilePalette(SpriteSheet.overWorld.width,SpriteSheet.overWorld.height);
        tilePalette1.setPreferredSize(new Dimension(336,144));
        scrollPane = new JScrollPane(mapViewPanel1);
        mapViewPanel1.setPreferredSize(new Dimension(50*16,50*16));

    }

    private void createMenus(){
        menubar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        //Exit
        JMenuItem eMenuItem = new JMenuItem("Exit");
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit application");
        eMenuItem.addActionListener((ActionEvent event) -> {
            //System.exit(0);
            SpriteSheet.overWorld.changeSprite("/textures/malewizard.png",128,128,16);
        });

        //Save
        JMenuItem oMenuItem = new JMenuItem("Save");
        oMenuItem.setMnemonic(KeyEvent.VK_S);
        oMenuItem.setToolTipText("Save map");
        oMenuItem.addActionListener((ActionEvent event) -> {
            //System.exit(0);
            int returnVal = fc.showSaveDialog(menubar);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                try(ByteChannel sbc = Files.newByteChannel(file.toPath(), StandardOpenOption.CREATE,StandardOpenOption.WRITE)) {
                    ByteBuffer mapData =  mapViewPanel1.tilesToByteBuffer();
                    mapData.rewind();
                    sbc.write(mapData);
                    sbc.close();
                }
                catch (IOException x){
                    System.out.println("Can't save for some reason!");
                }
            } else {
                // Do nothing, I guess
            }
        });

        fileMenu.add(oMenuItem);
        fileMenu.add(eMenuItem);


        menubar.add(fileMenu);



    }
    public static void main(String[] args)
    {
        MainWin mainWin = new MainWin();

        JFrame frame = new JFrame("Map Editor");
        frame.setContentPane(mainWin.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setJMenuBar(mainWin.menubar);




    }

}
