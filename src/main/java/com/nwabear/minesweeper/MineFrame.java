package com.nwabear.minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MineFrame extends JFrame {
    private MineSurface surface;
    private boolean wasLeftClick;

    private Point point;

    public MineFrame() throws Exception {
        this.initUI();
    }

    private void initUI() throws Exception {
        this.surface = new MineSurface(this);

        this.add(this.surface);

        // initialize window
        this.setTitle("Minesweeper");

        int width = AppContext.WIDTH;
        int height = AppContext.HEIGHT;
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { // perform on click actions
                MineFrame.this.point = e.getPoint();
                if(e.getButton() == 1) {
                    MineFrame.this.wasLeftClick = true;
                } else {
                    MineFrame.this.wasLeftClick = false;
                }
                MineFrame.this.surface.tick();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        } );

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getExtendedKeyCode() == 27) { // esc
                    MineFrame.this.dispose();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    // getters for other threads
    public Point getPoint() {
        return this.point;
    }

    public boolean wasLeftClick() {
        return this.wasLeftClick;
    }

    public static void main(String[] args) throws Exception {
        MineFrame mf = new MineFrame();
        EventQueue.invokeLater( () -> {
            mf.setVisible( true );
        } );
    }
}
