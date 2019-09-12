package com.nwabear.minesweeper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Textures {
    private ArrayList<BufferedImage> textures = new ArrayList<>();
    public Textures() throws Exception {
        // load textures into arraylist
        this.textures.add(this.resize(ImageIO.read(getClass().getResourceAsStream("/textures/blank.png"))));
        this.textures.add(this.resize(ImageIO.read(getClass().getResourceAsStream("/textures/1.png"))));
        this.textures.add(this.resize(ImageIO.read(getClass().getResourceAsStream("/textures/2.png"))));
        this.textures.add(this.resize(ImageIO.read(getClass().getResourceAsStream("/textures/3.png"))));
        this.textures.add(this.resize(ImageIO.read(getClass().getResourceAsStream("/textures/4.png"))));
        this.textures.add(this.resize(ImageIO.read(getClass().getResourceAsStream("/textures/5.png"))));
        this.textures.add(this.resize(ImageIO.read(getClass().getResourceAsStream("/textures/6.png"))));
        this.textures.add(this.resize(ImageIO.read(getClass().getResourceAsStream("/textures/7.png"))));
        this.textures.add(this.resize(ImageIO.read(getClass().getResourceAsStream("/textures/8.png"))));
        this.textures.add(this.resize(ImageIO.read(getClass().getResourceAsStream("/textures/mine.png"))));
        this.textures.add(this.resize(ImageIO.read(getClass().getResourceAsStream("/textures/dark.png"))));
        this.textures.add(this.resize(ImageIO.read(getClass().getResourceAsStream("/textures/flag.png"))));
    }

    public BufferedImage getTexture(boolean isBomb, int index) {
        // return the texture needed based on neighboring bombs
        if(isBomb) {
            return this.textures.get(9);
        } else {
            return this.textures.get(index);
        }
    }

    private BufferedImage resize(BufferedImage image) {
        // resize the image to fit to the screen correctly
        Image temp = image.getScaledInstance(AppContext.TILE_WIDTH, AppContext.TILE_WIDTH, Image.SCALE_REPLICATE);
        BufferedImage dimg = new BufferedImage(AppContext.TILE_WIDTH, AppContext.TILE_WIDTH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(temp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}
