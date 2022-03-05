package org.csu.mypetstore.util;

import java.awt.*;
import java.util.Random;

public class AuthCodeUtil {
    public static final long serialVersionID = 1L;
    public static final int WIDTH = 130;
    public static final int HEIGHT = 30;
    Random ran = new Random();

    public String  setWriteDate(Graphics g){
        g.setFont(new Font("楷体", Font.BOLD,20));
        int x = 10;
        int y = 20;
        String result = "";
        for(int i = 0;i < 4;i++){
            String str = String.valueOf(ran.nextInt(10));
            result += str;
            g.setColor(new Color(ran.nextInt(255),ran.nextInt(255),ran.nextInt(255)));

            int degree = ran.nextInt()%40;
            ((Graphics2D)g).rotate(degree*Math.PI/180,x,y);
            g.drawString(str,x,y);
            ((Graphics2D)g).rotate(-degree*Math.PI/180,x,y);
            x = x + 30;
        }
        return result;
    }

    public void setRandomLine(Graphics g){
        g.setColor(Color.white);
        for(int i = 0;i < 20;i++){
            int x1 = ran.nextInt(WIDTH);
            int y1 = ran.nextInt(HEIGHT);
            int x2 = ran.nextInt(WIDTH);
            int y2 = ran.nextInt(HEIGHT);
            g.drawLine(x1,y1,x2,y2);
        }
    }

    public void setBorder(Graphics g){
        g.setColor(Color.lightGray);
        g.drawRect(1,1,WIDTH-2,HEIGHT-2);
    }

    public void setBackground(Graphics g){
        g.setColor(Color.lightGray);
        g.fillRect(0,0,WIDTH,HEIGHT);
    }

}
