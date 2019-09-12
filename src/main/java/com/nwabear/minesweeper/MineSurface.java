package com.nwabear.minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

public class MineSurface extends JPanel {
    private MineFrame frame;
    private Tile[][] grid;
    private int bombs = AppContext.BOMBS;
    private Point point;
    private boolean wasClick = false;
    private boolean gameOver = false;
    private boolean gameWin = false;

    private int cols = AppContext.COLS;
    private int rows = AppContext.ROWS;
    private int width = AppContext.TILE_WIDTH;

    private ArrayList<Point> bombLocs = new ArrayList<>();

    public MineSurface(MineFrame frame) throws Exception {
        this.frame = frame;
        grid = new Tile[this.cols][this.rows];

        // place bombs in random locations, non overlapping
        for(int i = 0; i < this.bombs;) {
            int x = new Random().nextInt(this.cols);
            int y = new Random().nextInt(this.rows);

            if(!this.bombLocs.contains(new Point(x, y))) {
                this.bombLocs.add(new Point(x, y));
                i++;
            }
        }

        // create tiles on board/get neighbors for textures
        Textures tex = new Textures();
        for(int x = 0; x < cols; x++) {
            for(int y = 0; y < rows; y++) {
                this.grid[x][y] = new Tile(this.bombLocs.contains(new Point(x, y)), this.getNeighbors(x, y), tex);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g2d = (Graphics2D) (graphics);
        g2d.setFont(new Font("font", Font.PLAIN, this.width));

        // making sure it needs to be repainted
        if(this.wasClick) {
            this.point = this.frame.getPoint();
            // get point on grid, instead of the point on the display
            int x = this.getPoint(this.point.x) / this.width;
            int y = this.getPoint(this.point.y) / this.width;

            if(!this.gameOver && !this.gameWin) {
                if (this.frame.wasLeftClick()) {
                    // process clicking
                    if (!this.grid[x][y].isFlagged()) {
                        if (this.grid[x][y].isBomb()) {
                            this.gameOver(g2d);
                        } else {
                            this.reveal(x, y);
                            this.gameWin = this.didWin();
                        }
                    }
                } else {
                    // if its a right click, place/remove flag
                    this.grid[x][y].toggleFlag();
                }
            }
            this.wasClick = false;
        }

        // draw the updated grid onto the window
        this.drawMap(g2d);
    }

    private void gameOver(Graphics2D g2d) {
        // reveal all spaces if the game is lost
        for(int x = 0; x < this.cols; x++) {
            for(int y = 0; y < this.rows; y++) {
                if(this.grid[x][y].isHidden()) {
                    this.grid[x][y].reveal();
                }
            }
        }
        this.gameOver = true;
        this.drawMap(g2d);
    }

    private boolean didWin() {
        // see if the user had won the game
        for(int x = 0; x < 16; x++) {
            for(int y = 0; y < 16; y++) {
                if(this.grid[x][y].isHidden() && !this.grid[x][y].isBomb()) {
                    return false;
                }
            }
        }
        return true;
    }

    private int getPoint(int num) {
        // return the point on the grid, based on the x or y of the mouse
        int toReturn = num / this.width;
        return toReturn * this.width;
    }

    private void drawMap(Graphics2D g2d) {
        // draw the board
        for(int x = 0; x < this.cols; x++) {
            for(int y = 0; y < this.rows; y++) {
                g2d.drawImage(grid[x][y].getImage(), x * this.width, y * this.width, null);
            }
        }

        // if the game was lost or won, write text to the user to signify
        if(this.gameOver) {
            g2d.setColor(Color.RED);
            Rectangle2D bounds = g2d.getFont().getStringBounds("Game Over...", g2d.getFontRenderContext());
            g2d.drawString("Game Over...", (int)((15 * this.cols) - bounds.getWidth()), (int)((this.width * this.width) - bounds.getCenterY()));
        } else if(this.gameWin) {
            g2d.setColor(Color.YELLOW.brighter());
            Rectangle2D bounds = g2d.getFont().getStringBounds("You Win!!!", g2d.getFontRenderContext());
            g2d.drawString("You Win!!!", (int)((13 * this.cols) - bounds.getWidth()), (int)((this.width * this.width) - bounds.getCenterY()));
        }
    }

    private int getNeighbors(int x, int y) {
        int neighbors = 0;
        // find the amount of neighboring bombs in the 8 surrounding tiles
        for(int x2 = x - 1; x2 <= x + 1; x2++) {
            for(int y2 = y - 1; y2 <= y + 1; y2++) {
                try {
                    if(this.bombLocs.contains(new Point(x2, y2)) && (x2 != x || y2 != y)) {
                        neighbors++;
                    }
                } catch(NullPointerException e) {
                    // do nothing
                }
            }
        }
        return neighbors;
    }

    private void reveal(int x, int y) {
        // reveal a point on the grid
        if(grid[x][y].isHidden()) {
            this.grid[x][y].reveal();
        }

        // if it is a blank square, do the cascade to reveal multiple spots
        if(grid[x][y].getNeighbors() == 0) {
            try { if(this.grid[x + 1][y].isHidden()) this.reveal(x + 1, y); } catch(Exception e) { }
            try { if(this.grid[x - 1][y].isHidden()) this.reveal(x - 1, y); } catch(Exception e) { }
            try { if(this.grid[x][y + 1].isHidden()) this.reveal(x, y + 1); } catch(Exception e) { }
            try { if(this.grid[x][y - 1].isHidden()) this.reveal(x, y - 1); } catch(Exception e) { }

            try { if(this.grid[x + 1][y + 1].isHidden()) this.reveal(x + 1, y + 1); } catch(Exception e) { }
            try { if(this.grid[x + 1][y - 1].isHidden()) this.reveal(x + 1, y - 1); } catch(Exception e) { }
            try { if(this.grid[x - 1][y + 1].isHidden()) this.reveal(x - 1, y + 1); } catch(Exception e) { }
            try { if(this.grid[x - 1][y - 1].isHidden()) this.reveal(x - 1, y - 1); } catch(Exception e) { }
        }
    }

    public void tick() {
        this.wasClick = true;
        repaint();
    }
}
