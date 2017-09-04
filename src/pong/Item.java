/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import java.awt.Image;
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
public class Item {
    private String username = System.getProperty("user.name");
    Image img;
    PongPlayer player = new PongPlayer();
    private int x = (int) (Math.random()*200)+450  ;
    private int y = y_generate();
    public int item_draw = -1;
    int[] item_index = {0,1,2,3};
    //generate random item in store:
    int n = (int) (Math.random()*4);
    int m = (int) (Math.random()*4);
    int k = (int) (Math.random()*4);
    int p = (int) (Math.random()*4);
    int[] items = {item_index[n],item_index[m],item_index[k],item_index[p]};
    private int generate_nextItem = 0;
    public int updownswitch = 0;
    public void Item(){
    
    }
    
    public int y_generate(){
     int random = (int) (Math.random()*2);
     int random_y =0;
     switch (random) {
         case 0: random_y = (int) (Math.random()*200); break;
         case 1: random_y = (int) (Math.random()*150) + 400; break;
     }
     return random_y;
    }
    
    public void Item_sound() {
        InputStream music;
        try {
            music = new FileInputStream(new File("C:\\Users\\" + username + "\\Documents\\NetBeansProjects\\Pong\\src\\musics\\Item.wav"));

            AudioStream audios = new AudioStream(music);
            AudioPlayer.player.start(audios);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }
    }
    
    
    public void paint(Graphics g){ 
        for(int i = 0;i<4;i++){
         if(item_draw==i){
          switch (items[i]){
              case 0: reverse(g); break;
              case 1: ghost(g); break;
              case 2: block(g);break;
              case 3: protection(g);break;
          }
        }
        }
    }
    
    // drawing items: 
    public void reverse(Graphics g){
    ImageIcon i2 = new ImageIcon("C:\\Users\\" + username + "\\Documents\\NetBeansProjects\\Pong\\src\\images\\reverse.png");
    img = i2.getImage();
    g.drawImage(img,x,y,70,70,null);
    }
    
    public void ghost(Graphics g){
     ImageIcon i2 = new ImageIcon("C:\\Users\\" + username + "\\Documents\\NetBeansProjects\\Pong\\src\\images\\ghost.png");
        img = i2.getImage();
        g.drawImage(img,x,y,70,70,null);
    }
    
    public void block(Graphics g){
     ImageIcon i2 = new ImageIcon("C:\\Users\\" + username + "\\Documents\\NetBeansProjects\\Pong\\src\\images\\block.png");
     img = i2.getImage();
     g.drawImage(img,x,y,30,120,null);
    }
    
    public void protection(Graphics g){
     ImageIcon i2 = new ImageIcon("C:\\Users\\" + username + "\\Documents\\NetBeansProjects\\Pong\\src\\images\\shield.png");
     img = i2.getImage();
     g.drawImage(img,x,y,70,70,null);
    }
     
    // ball detects item: 
    public void detect_block(PongBall ball,int xVelocity){
     if(xVelocity > 0){
     if((ball.getX()<x+80)&&(ball.getX()+30>x)&&(ball.getY()>y-10)&&(ball.getY()<y+120)){
         Item_sound();
                Item.this.block(ball);
         item_draw++;
    if(generate_nextItem < 4) {
    x = (int) (Math.random()*200)+400 ;
    y = y_generate();
    generate_nextItem++;
    } 
      }
     }
     if(xVelocity < 0){
     if((ball.getX()<x+25)&&(ball.getX()>x)&&(ball.getY()>y-10)&&(ball.getY()<y+120)){
     Item_sound();
                Item.this.block(ball);
     item_draw++;
    if(generate_nextItem < 4) {
    x = (int) (Math.random()*200)+400 ;
    y = y_generate();
    generate_nextItem++;
    } 
      }
     }
    }
    
    public void detect_others(PongBall ball,int xVelocity,String item_name){
     if(xVelocity > 0){
     if((ball.getX()<x+70)&&(ball.getX()+20>x-10)&&(ball.getY()>y-10)&&(ball.getY()<y+70)){
         Item_sound();
         switch(item_name){
             case "reverse":  reverse(ball); break; // updownswitch
             case "ghost":  ball.teleport++; break; // ball teleport
             case "shield":  protection(ball); break; // shield 
         }
         item_draw++;
    if(generate_nextItem < 4) {
    x = (int) (Math.random()*200)+400 ;
    y = y_generate();
    generate_nextItem++;
    } 
      }
     }
     if(xVelocity < 0){
     if((ball.getX()<x+70)&&(ball.getX()>x)&&(ball.getY()>y-10)&&(ball.getY()<y+70)){
     Item_sound();
     switch(item_name){
             case "reverse":  reverse(ball); break; // updownswitch
             case "ghost":  ball.teleport++; break; // ball teleport
             case "shield":  protection(ball); break; // shield 
     }
     item_draw++;
    if(generate_nextItem < 4) {
    x = (int) (Math.random()*200)+400 ;
    y = y_generate();
    generate_nextItem++;
    } 
      }
     }
    }
    
    public void check(PongBall ball){
       for(int i =0;i<4;i++){
       if((item_draw==i)){
       switch (items[i]){
              case 0: detect_others(ball,ball.xVelocity,"reverse"); break;
              case 1: detect_others(ball,ball.xVelocity,"ghost"); break;
              case 2: detect_block(ball,ball.xVelocity);break;
              case 3: detect_others(ball,ball.xVelocity,"shield");break;
          }
    }
     if(item_draw==3){
     item_draw=0;
     generate_nextItem = 0;
     x = (int) (Math.random()*200)+400;
     y = y_generate();
     n = (int) (Math.random()*4);
     m = (int) (Math.random()*4);
     k = (int) (Math.random()*4);
     p = (int) (Math.random()*4); 
     items[0] = item_index[n];
     items[1] = item_index[m];
     items[2] = item_index[k];
     items[3] = item_index[p];
       }
       }
     }
    
    //items' functions: 
    //reverse opponent's direction:
    public void reverse(PongBall ball) {
     ball.updownswitch++;
       if(ball.updownswitch>0){
      if(ball.xVelocity>0){
          updownswitch=1;
      }
      if(ball.xVelocity<0){
          updownswitch=2;
      }
       }
    }
    
    // ball bounces back and increase the velocity:
    public void block(PongBall ball) {
      ball.xVelocity = - ball.xVelocity ;
          if(ball.xVelocity>0){
          ball.xVelocity = ball.xVelocity +2;
          }
          if(ball.xVelocity<0){
          ball.xVelocity = ball.xVelocity -2;
          }
          ball.yVelocity = ball.yVelocity + (int) (Math.random()*6)-2;
    }
    
    // protect you from losing 1 score in the next opponent's attack:
    public void protection(PongBall ball) {
     if(ball.xVelocity>0){
       ball.protection =1;
       }
       if(ball.xVelocity<0){
       ball.protection =2;
       }
    }
    
}
