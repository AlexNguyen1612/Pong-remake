/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import javax.swing.JFrame;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.swing.JOptionPane;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author admin
 */
public class Pong extends JFrame {

    public Pong(Menu menu) {
        this.menu = menu;
        PongPanel box = new PongPanel(this);
        setTitle("Pong_GengarEdition");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        add(box);
        box.addKeyListener(box);
        box.requestFocusInWindow();

        box.setFocusable(true);

    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
    private Menu menu;
    
    

}
