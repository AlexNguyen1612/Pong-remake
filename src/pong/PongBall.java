/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Panel;
import java.awt.TextField;
import static java.lang.System.gc;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import sun.awt.RepaintArea;
import java.awt.TextField;
import java.awt.TextField;
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
public class PongBall {
    private String username = System.getProperty("user.name");
    Item item = new Item();
    Image img;
    PongPlayer player = new PongPlayer();
    int[] two_sides ={2,3,3,2};
    int[] middle ={-2,-2,2,2};
    private int Score1 = 0;
    private int Score2 = 0;
    public int x = 500;
    public int y = 350;
    int[] random_xV ={4,-4};
    int[] random_yV ={2,-2};
    int[] teleport_rand = {5,-5};
    private int k = (int) (Math.random()*2);
    private int p = (int) (Math.random()*2);
    public int xVelocity = random_xV[k];
    public int yVelocity = random_yV[p];
    private PongPanel game;
    public int speedup_mark = 0;
    //variables for item detection: 
    public int updownswitch = 0;
    public int teleport = 0;
    private int c = 0;
    public int protection = 0; 
    
    public PongBall(PongPanel game) {
        this.game = game;
    }
    
    PongBall() {
    }
    
    public void Pong_sound() {
        InputStream music;
        try {
            music = new FileInputStream(new File("C:\\Users\\" + username + "\\Documents\\NetBeansProjects\\Pong\\src\\musics\\Pong_sounds.wav"));

            AudioStream audios = new AudioStream(music);
            AudioPlayer.player.start(audios);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }
    }
    
    public void Score_sound(){
     InputStream music;
        try {
            music = new FileInputStream(new File("C:\\Users\\" + username + "\\Documents\\NetBeansProjects\\Pong\\src\\musics\\Score.wav"));

            AudioStream audios = new AudioStream(music);
            AudioPlayer.player.start(audios);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }
    }
    
    public void update() {
        
        x = x + xVelocity;
        y = y + yVelocity;
        Score();
        //Teleport:
        if(teleport>=1){
        if(xVelocity>0){
        xVelocity=xVelocity+1;
        x=x+200;
        yVelocity = teleport_rand[p];
        }
        if(xVelocity<0){
        xVelocity=xVelocity-1;
        x=x-200;
        yVelocity = teleport_rand[p];
        } 
        c++;
        if(c==1){
        teleport =0;
        c = 0;
        }
        }
        
       //protection:
     
        if((protection == 1)&&((x<10))){
         xVelocity = -xVelocity;
         protection=0;
        }
        
      
        if((protection == 2)&&((x+30>990))){
            xVelocity = -xVelocity;
         protection=0;
        }
        
        //When ball hit the wall => bounce back
        if (x < 0) {
            xVelocity = -xVelocity;
        }
        if (x + 30 > 1000) {
            xVelocity = -xVelocity;
      
        }
        if (y + 50 > 700) {
            yVelocity = -yVelocity;
  
        }
        if (y < 0) {
            yVelocity = -yVelocity;
            
        }
        speedup1();
        speedup2();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void paint(Graphics g) {
        if(protection==1){
        ImageIcon i2 = new ImageIcon("C:\\Users\\" + username + "\\Documents\\NetBeansProjects\\Pong\\src\\images\\shield.png");
        img = i2.getImage();
        g.drawImage(img,50,50,30,30,null);
        }
        if(protection==2){
        ImageIcon i2 = new ImageIcon("C:\\Users\\" + username + "\\Documents\\NetBeansProjects\\Pong\\src\\images\\shield.png");
        img = i2.getImage();
        g.drawImage(img,950,50,30,30,null);
        }
        //Different ball level:
        ImageIcon i1 = new ImageIcon("C:\\Users\\" + username + "\\Documents\\NetBeansProjects\\Pong\\src\\images\\pokeball.png");
        img = i1.getImage();
        if((xVelocity>=-6)&&(xVelocity<=6)){
        g.drawImage(img,x,y,30,30,null);
        }
        if((xVelocity<-6)||(xVelocity>6)){
       ImageIcon i2 = new ImageIcon("C:\\Users\\" + username + "\\Documents\\NetBeansProjects\\Pong\\src\\images\\Fastball.png");
        img = i2.getImage();
        g.drawImage(img,x,y,27,30,null);
        }
        if((xVelocity<-10)||(xVelocity>10)){
       ImageIcon i3 = new ImageIcon("C:\\Users\\" + username + "\\Documents\\NetBeansProjects\\Pong\\src\\images\\ultraball.png");
        img = i3.getImage();
        g.drawImage(img,x,y,27,30,null);
        }
        //Player's name and score in Panel:
        g.setColor(Color.WHITE);
        Font font = g.getFont().deriveFont(40.0f);
        g.setFont(font);
        g.drawString(getScore1() + "", 100, 100);
        g.drawString(getScore2() + "", 850, 100);
        g.drawString(game.getPong().getMenu().getTextField1().getText() + "", 60, 50);
        g.drawString(game.getPong().getMenu().getTextField2().getText() + "", 780, 50);

    }

    //collide with player
    public void check(PongPlayer player){
    int i = (int) (Math.random()*4);
    int j= (int) (Math.random()*4);  
    if((x+20>950)&&(x+20<970)){
        //ball hit paddle's upper-part:
        if((y>player.gety1()-15)&&(y<player.gety1()+30)){
         xVelocity = -xVelocity;   
         yVelocity = -two_sides[i];
         Pong_sound();
         speedup_mark++;
        }
         //ball hit paddle's middle-part:
        if((y>=player.gety1()+30)&&(y<player.gety1()+80)){
         xVelocity = -xVelocity;
         yVelocity = middle[j];
         Pong_sound();
         speedup_mark++;
        }
        //ball hit paddle's lower-part:
        if((y>=player.gety1()+80)&&(y-10<player.gety1()+130)){
         xVelocity = -xVelocity;
         yVelocity = two_sides[i];
         Pong_sound();
         speedup_mark++;
        }
        
    }
    if((x<50)&&(x>30)){
         if((y>player.gety2()-15)&&(y<player.gety2()+30)){
         xVelocity = -xVelocity;
         yVelocity = -two_sides[i];
         Pong_sound();
         speedup_mark++;
        }
         if((y>=player.gety2()+30)&&(y<player.gety2()+80)){
         xVelocity = -xVelocity;
         yVelocity = middle[j];
         Pong_sound();    
         speedup_mark++;
         }
         if((y>=player.gety2()+80)&&(y-10<player.gety2()+130)){
         xVelocity = -xVelocity;
         yVelocity = two_sides[i];
         Pong_sound();
         speedup_mark++;
         }
    }
    }

    
    public void Score() {

        if (x < 0) {
            Score2++;
            if (Score2 < 5 ) {
             Score_sound();
            }
            game.resetgame();
        }
        if (x + 30 > 1000) { 
            Score1++;
            if (Score1 < 5 ) {
             Score_sound();
            }
            game.resetgame();
        }

        if (Score1 == 5) {
            game.GameStatus = 1;
            x=500;
            y=350;
        }
        if (Score2 == 5) {
            game.GameStatus = 2;
            x=500;
            y=350;
        }

    }

    public void setxVelocity() {
        int k = (int) (Math.random()*2);
        this.xVelocity = random_xV[k];
    }

    public void setyVelocity() {
        int k = (int) (Math.random()*2);
        this.yVelocity = random_yV[k];
    }

    public int getScore1() {
        return Score1;
    }

    public void setScore1(int Score1) {
        this.Score1 = Score1;
    }

    public int getScore2() {
        return Score2;
    }

    public void setScore2(int Score2) {
        this.Score2 = Score2;
    }
    //Ball transforms:
    public void speedup1(){
     if(speedup_mark==5){
      if(xVelocity>0){
      xVelocity = xVelocity + 4;
      }
      if(xVelocity<0){
      xVelocity = xVelocity - 4;
      }
      speedup_mark++;
     }
    }
     
    public void speedup2(){
    if(speedup_mark==11){
      if(xVelocity>0){
      xVelocity = xVelocity + 5;
      }
      if(xVelocity<0){
      xVelocity = xVelocity - 5;
      }
      speedup_mark++;
    }
    }
   
}
