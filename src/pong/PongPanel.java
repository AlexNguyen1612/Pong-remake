/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import java.awt.event.*;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Random;
import javax.swing.JOptionPane;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import java.awt.Font;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 *
 * @author admin
 */
public class PongPanel extends JPanel implements ActionListener, KeyListener {
    private String username = System.getProperty("user.name");
    private Pong pong;
    public int GameStatus = 0;
    private int begin = 0;

    public Pong getPong() {
        return pong;
    }

    public void setPong(Pong pong) {
        this.pong = pong;
    }

    Image img;
    //add object to panel:
    PongBall ball = new PongBall(this);
    PongPlayer player = new PongPlayer();
    Item item = new Item();
    //a loop: after 12 milisecs,execute actionPerformed:
    Timer clock = new Timer(12, this);

    public PongPanel(Pong pong) {
        this.pong = pong;

    }

    private void update() {
        //execute update() method in PongBall.java and PongPlayer.java

        ball.update();
        player.update();

        //check if ball and player collide or not 
        ball.check(player);
        item.check(ball);
    }

    public void paintComponent(Graphics g) {
        //set background
        super.paintComponent(g);
        switch (GameStatus) {

            case 0: {
                ImageIcon i = new ImageIcon("C:\\Users\\" + username + "\\Documents\\NetBeansProjects\\Pong\\src\\images\\background.png");
                img = i.getImage();
                g.drawImage(img, 0, 0, null);
                //execute paint() method in PongBall.java and PongPlayer.java
                player.paint(g);
                ball.paint(g);
                item.paint(g);
            }
            break;
            case 1: {
                ImageIcon i = new ImageIcon("C:\\Users\\" + username + "\\Documents\\NetBeansProjects\\Pong\\src\\images\\Result.png");
                img = i.getImage();
                g.drawImage(img, 0, 0, null);
                g.setColor(Color.YELLOW);
                Font font = g.getFont().deriveFont(40.0f);
                g.setFont(font);
                g.drawString(getPong().getMenu().getTextField1().getText() + " WINS!", 700, 100);
                g.setColor(Color.YELLOW);
                g.drawString("Click anywhere to go back to Menu", 350, 650);

                this.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        restart();
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                    }
                });
            }
            break;
            case 2: {
                ImageIcon i = new ImageIcon("C:\\Users\\" + username + "\\Documents\\NetBeansProjects\\Pong\\src\\images\\Result.png");
                img = i.getImage();
                g.drawImage(img, 0, 0, null);
                g.setColor(Color.YELLOW);
                Font font = g.getFont().deriveFont(40.0f);
                g.setFont(font);
                g.drawString(getPong().getMenu().getTextField2().getText() + " WINS!", 700, 100);
                g.setColor(Color.YELLOW);
                g.drawString("Click anywhere to go back to Menu", 350, 650);
                this.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        restart();
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                    }
                });
            }

            break;

        }
        if (begin == 0) {
            g.setColor(Color.WHITE);
            Font font = g.getFont().deriveFont(20.0f);
            g.setFont(font);
            g.drawString("Press ENTER to play!", 420, 200);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == clock) {
            update();
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //User input
    @Override
    public void keyPressed(KeyEvent e) {
        //press keys to set players Velocity
        int keyevent = e.getKeyCode();
        if(keyevent==KeyEvent.VK_ENTER){
         clock.start();
         begin++;
         item.item_draw = 0;
        }
        if (keyevent == KeyEvent.VK_UP) {
            player.sety1Velocity(-3);
            if ((item.updownswitch == 1)) {
                player.sety1Velocity(3);
            }
            if ((ball.updownswitch == 0)) {
                player.sety1Velocity(-3);

            }
        }
        if (keyevent == KeyEvent.VK_DOWN) {
            player.sety1Velocity(3);
            if ((item.updownswitch == 1)) {
                player.sety1Velocity(-3);
            }
            if ((ball.updownswitch == 0)) {
                player.sety1Velocity(3);
            }
        }
        if (keyevent == KeyEvent.VK_W) {
            player.sety2Velocity(-3);
            if ((item.updownswitch == 2)) {
                player.sety2Velocity(3);
            }
            if ((ball.updownswitch == 0)) {
                player.sety2Velocity(-3);
            }
        }
        if (keyevent == KeyEvent.VK_S) {
            player.sety2Velocity(3);
            if ((item.updownswitch == 2)) {
                player.sety2Velocity(-3);
            }
            if ((ball.updownswitch == 0)) {
                player.sety2Velocity(3);
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyevent = e.getKeyCode();
        if ((keyevent == KeyEvent.VK_UP) || (keyevent == KeyEvent.VK_DOWN)) {
            player.sety1Velocity(0);
        }
        if ((keyevent == KeyEvent.VK_W) || (keyevent == KeyEvent.VK_S)) {
            player.sety2Velocity(0);
        }
    }


    public void resetgame() {

        ball.setX(500);
        ball.setY(350);
        player.setY1(300);
        player.setY2(300);
        ball.setyVelocity();
        ball.setxVelocity();
        ball.speedup_mark=0;
        ball.updownswitch=0;
        ball.teleport =0;
    }

    public void restart() {
//      AudioPlayer.player.stop(audios);
       pong.dispose();
       
    }
}
