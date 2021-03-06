package com;

import com.Components.ImagePanel;
import com.Components.MapViewPanel;
import com.Components.TilePalette;
import com.Listeners.MapPanelMouseListener;
import com.Listeners.RadioButtonListener;
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
    private JRadioButton radioButtonLayer1;
    private JRadioButton radioButtonLayer2;
    private JRadioButton radioButtonSolid;
    private MapPanelMouseListener listen;
    private RadioButtonListener radListen;

    final JFileChooser fc = new JFileChooser();

    public static int inputMode;


    public MainWin()
    {
        tilePalette1.addMouseListener(new TilePaletteMouseListener(tilePalette1, imagePanel1));
        mapViewPanel1.addMouseListener(listen = new MapPanelMouseListener(mapViewPanel1));
        mapViewPanel1.addMouseMotionListener(listen);

        radioButtonLayer1.addActionListener(radListen = new RadioButtonListener(tilePalette1));
        radioButtonLayer2.addActionListener(radListen );
        radioButtonSolid.addActionListener(radListen );
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
        JMenuItem sMenuItem = new JMenuItem("Save");
        sMenuItem.setMnemonic(KeyEvent.VK_S);
        sMenuItem.setToolTipText("Save map");
        sMenuItem.addActionListener((ActionEvent event) -> {
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

        //Save Solidity
        JMenuItem oMenuItem = new JMenuItem("Save Solidity");
        oMenuItem.setMnemonic(KeyEvent.VK_O);
        oMenuItem.setToolTipText("Save tile solidity");
        oMenuItem.addActionListener((ActionEvent event) -> {
            int returnVal = fc.showSaveDialog(menubar);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                try(ByteChannel sbc = Files.newByteChannel(file.toPath(), StandardOpenOption.CREATE,StandardOpenOption.WRITE)) {
                    ByteBuffer mapData =  tilePalette1.solidToByteBuffer();
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

        fileMenu.add(sMenuItem);
        fileMenu.add(oMenuItem);
        fileMenu.add(eMenuItem);

        JMenu viewMenu = new JMenu("View");
        viewMenu.setMnemonic(KeyEvent.VK_V);

        //Exit
        JMenuItem gMenuItem = new JMenuItem("Grid On");
        gMenuItem.setMnemonic(KeyEvent.VK_G);
        gMenuItem.setToolTipText("Display grid on map viewer");
        gMenuItem.addActionListener((ActionEvent event) -> {
            mapViewPanel1.toggleGrid();
            mapViewPanel1.repaint();
        });

        viewMenu.add(gMenuItem);

        menubar.add(fileMenu);
        menubar.add(viewMenu);




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
