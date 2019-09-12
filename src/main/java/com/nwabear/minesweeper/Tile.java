package com.nwabear.minesweeper;

import java.awt.image.BufferedImage;

public class Tile {
    private boolean isBomb;
    private boolean isHidden;
    private boolean isFlag;
    private int neighborBombs;
    private BufferedImage tile;
    private BufferedImage hidden;
    private BufferedImage flag;

    public Tile(boolean isBomb, int neighborBombs, Textures getTex) throws Exception {
        // assign used variables
        this.isBomb = isBomb;
        this.neighborBombs = neighborBombs;
        this.tile = getTex.getTexture(this.isBomb, this.neighborBombs);
        this.hidden = getTex.getTexture(false, 10);
        this.flag = getTex.getTexture(false, 11);
        this.isHidden = true;
        this.isFlag = false;
    }

    // lots of getters to interface with the tile
    public boolean isBomb() {
        return this.isBomb;
    }

    public boolean isHidden() {
        return this.isHidden;
    }

    public boolean isFlagged() {
        return this.isFlag;
    }

    public int getNeighbors() {
        return this.neighborBombs;
    }

    public BufferedImage getImage() {
        if(!this.isHidden) {
            return this.tile;
        }

        if(this.isFlag) {
            return this.flag;
        }

        return this.hidden;
    }

    // process revealing and flagging
    public void reveal() {
        this.isHidden = false;
    }

    public void toggleFlag() {
        this.isFlag = !this.isFlag;
    }
}
