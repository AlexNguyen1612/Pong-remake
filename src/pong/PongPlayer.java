/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
/**
 *
 * @author admin
 */
public class PongPlayer {
    private String username = System.getProperty("user.name");
    
    Image img;
   private int y1 = 300;
   private int y2 = 300;
 private int y1Velocity =0;
 private int y2Velocity=0;
 private int Width = 15;
 private int Height=120;
 
   public void update(){
       //update player position when press keys
       y1 = y1 + y1Velocity;
       y2 = y2 + y2Velocity;
       //When player hit the wall => stop
       if(y1<0){
       y1 = 0;
       }
       if(y2<0){
       y2=0;
       }
       if(y1+Height+20>700){
       y1 = 700-Height-20 ;
       }
       if(y2+Height+20>700){
       y2 = 700 - Height-20;
       }
   }
   public void paint(Graphics g){
        ImageIcon i1 = new ImageIcon("C:\\Users\\" + username + "\\Documents\\NetBeansProjects\\Pong\\src\\images\\player1.png");
        img =i1.getImage();
        g.drawImage(img, 950, y1, null);
        ImageIcon i2 = new ImageIcon("C:\\Users\\" + username + "\\Documents\\NetBeansProjects\\Pong\\src\\images\\player2.png");
        img =i2.getImage();
        g.drawImage(img, 30, y2, null);
       
      
   }
   //method:set speed for players 
   public void sety1Velocity(int speed){
       y1Velocity = speed;
   }
   public void sety2Velocity(int speed) {
       y2Velocity = speed;
   }

   public int gety1(){
   return y1;
   }
   public int gety2() {
   return y2;
   }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }
   
}
