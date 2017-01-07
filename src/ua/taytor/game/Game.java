package ua.taytor.game;


import java.awt.event.KeyEvent;
import ua.taytor.control.Input;
import ua.taytor.display.Window;
import ua.taytor.graphics.AtlasLoader;
import ua.taytor.utils.Time;

import java.awt.*;

public class Game implements Runnable {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final String TITLE = "TANKS";
    private static final int CLEAR_COLOR = 0xff000000;
    private static final int NUM_BUFFERS = 3;

    private static final float UPDATE_RATE = 60.0f;
    private static final float UPDATE_INTERVAL = Time.SECOND / UPDATE_RATE;
    private static final long IDLE_TIME = 1;
    private static final String FILE_NAME = "texture_atlas.png";

    private boolean running;
    private Thread gameThread;
    private Graphics2D graphics2D;
    private Input input;
    private AtlasLoader atlas;

    //tmp
    float delta = 0;
    float x = 250;
    float y = 350;
    private int dim = 100;
    float speed = 3;
    //tmp

    public Game() {
        running=false;
        Window.create(WIDTH,HEIGHT,TITLE,CLEAR_COLOR,NUM_BUFFERS);
        graphics2D = Window.getGraphics();
        input = new Input();
        Window.addInputListener(input);
        atlas = new AtlasLoader(FILE_NAME);
    }

    public synchronized void start(){

        if(running) return;
        running = true;

        gameThread = new Thread(this);
        gameThread.start();

    }

    public  synchronized void stop(){
        if(!running) return;

        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        cleanUp();
    }

    private void update(){

        if(input.getKey(KeyEvent.VK_UP)) x-=speed;
        if(input.getKey(KeyEvent.VK_DOWN)) x+=speed;
        if(input.getKey(KeyEvent.VK_LEFT)) y-=speed;
        if(input.getKey(KeyEvent.VK_RIGHT)) y+=speed;


    }

    private void render(){
        Window.clear();
        graphics2D.setColor(new Color(0xff00));
        graphics2D.drawImage(atlas.cut(0,0,16,16),(int)y,(int)x,null);
        Window.swapBuffers();
    }

    @Override
    public void run() {

        int fps=0;
        int upd = 0;
        int updl=0;

        long count = 0;

        float delta = 0;
        long lastTime = Time.getCurrentTime();

        while (running){
            long now = Time.getCurrentTime();
            long elapsedTime = now - lastTime;
            lastTime = now;

            count+=elapsedTime;

            boolean render = false;
            delta+= (elapsedTime/UPDATE_INTERVAL);

            while (delta>1){
                update();
                upd++;
                delta--;
                if(render){
                    updl++;
                }else render = true;
            }

            if(render){
                render();
                fps++;
            } else{
                try {
                    Thread.sleep(IDLE_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if(count>=Time.SECOND){
                Window.setTitle(TITLE+"|| FPS:"+fps+" | UPD:"+upd+"| UPDL:"+updl);
                fps = 0;
                upd = 0;
                updl = 0;
                count = 0;
            }

        }
    }

    private void cleanUp(){
        Window.destroy();
    }
}
