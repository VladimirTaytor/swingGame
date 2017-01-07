package ua.taytor.graphics;


import ua.taytor.utils.ResursesLoader;

import java.awt.image.BufferedImage;


public class AtlasLoader {

    BufferedImage image;

    public AtlasLoader(String name){
        image= ResursesLoader.loadImage(name);
    }

    public BufferedImage cut(int x,int y,int w,int h){
            return image.getSubimage(x,y,w,h);
    }

}
