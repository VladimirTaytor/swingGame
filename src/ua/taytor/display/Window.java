package ua.taytor.display;


import ua.taytor.control.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public abstract class Window {

    private static boolean created = false;
    private static JFrame window;
    private static Canvas content;

    private static BufferedImage buffer;
    private static int [] bufferData;
    private static Graphics bufferGraphics;
    private static int clearColor;


    private static BufferStrategy bufferStrategy;


    public static void create(int width,int height,String name,int _clearColor,int buffers){

        if(created) return;

        window = new JFrame(name);
        content = new Canvas();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);



        content.setPreferredSize(new Dimension(width,height));
        window.getContentPane().add(content);
        window.setResizable(false);
        window.setVisible(true);
        window.pack();

        window.setLocationRelativeTo(null);

        buffer = new BufferedImage(800,600,BufferedImage.TYPE_INT_ARGB);
        bufferData = ((DataBufferInt) buffer.getRaster().getDataBuffer()).getData();
        bufferGraphics = buffer.getGraphics();

        ((Graphics2D) bufferGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        clearColor = _clearColor;

        content.createBufferStrategy(buffers);
        bufferStrategy = content.getBufferStrategy();

        created = true;
    }

    public static void clear(){
        Arrays.fill(bufferData,clearColor);
    }

    public static void swapBuffers(){
        Graphics g = bufferStrategy.getDrawGraphics();
        g.drawImage(buffer,0,0,null);
        bufferStrategy.show();
    }
    public static void destroy(){
        window.dispose();
    }

    public static void setTitle(String title){
        window.setTitle(title);
    }
    public static Graphics2D getGraphics(){
        return (Graphics2D) bufferGraphics;
    }
    public static void addInputListener(Input inputListener) {
        window.add(inputListener);
        	}

}
