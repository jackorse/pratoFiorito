/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pratofiorito;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

/**
 *ciao
 * @author orsenigo_giacomo
 */
public class MyToggleButton extends JToggleButton implements ImageObserver {

    private static Image flagImage;
    private static Image bombImage;
    private static Image redBombImage;
    private static Image notBombImage;

    private int r, c;
    private boolean bomba;
    private boolean bombaTrovata;

    static {
        try {
            bombImage = ImageIO.read(ClassLoader.getSystemResource("pratofiorito/images/bomba.png"));
            flagImage = ImageIO.read(ClassLoader.getSystemResource("pratofiorito/images/bandiera.png"));
            redBombImage = ImageIO.read(ClassLoader.getSystemResource("pratofiorito/images/redBomba.jpg"));
            notBombImage = ImageIO.read(ClassLoader.getSystemResource("pratofiorito/images/notBomba.png"));
        } catch (IOException ex) {
            System.err.println("Errore immagini!!!");
        } catch (Exception e) {
            System.err.println("Immagini non caricate!!!!");
        }
    }

    public MyToggleButton(int r, int c) {
        this.c = c;
        this.r = r;
        this.bomba = false;
        this.bombaTrovata = false;
        super.setPreferredSize(new Dimension(40, 40));
    }

    public void setBomba(boolean bomba) {
        this.bomba = bomba;
//        if (bomba) {
//           this.setBackground(Color.yellow);
//        }
    }

    public int getR() {
        return r;
    }

    public int getC() {
        return c;
    }

    public boolean isBomba() {
        return bomba;
    }

    public boolean isBombaTrovata() {
        return bombaTrovata;
    }

    public void drawBandiera() {
        setImageSize();
        setIcon(new ImageIcon(flagImage));
        bombaTrovata = true;

    }

    public void removeBandiera() {
        setIcon(null);
        bombaTrovata = false;
    }

    public void drawBomba() {
        setImageSize();
        setIcon(new ImageIcon(bombImage));
    }

    public void drawRedBomba() {
        setBackground(Color.red);
        setImageSize();
        setIcon(new ImageIcon(redBombImage));
    }

    public void drawNotBomba() {
        setImageSize();
        setIcon(new ImageIcon(notBombImage));
    }

    private void setImageSize() {
        if (bombImage.getWidth(this) != this.getWidth() && bombImage.getHeight(this) != this.getHeight()) {
            bombImage = bombImage.getScaledInstance(getWidth(), getHeight(), 0);
            flagImage = flagImage.getScaledInstance(getWidth(), getHeight(), 0);
            redBombImage = redBombImage.getScaledInstance(getWidth(), getHeight(), 0);
            notBombImage = notBombImage.getScaledInstance(getWidth(), getHeight(), 0);
        }
    }

    public boolean isQuestionMark() {
        return getText().equals("?");
    }

    public void setQuestionMark() {
        setText("?");
        setFont(new Font("Arial", Font.BOLD, 20));
        setForeground(Color.YELLOW);
    }

    public void removeQuestionMark() {
        if (isQuestionMark()) {
            setText("");
        }
    }

    public void reset() {
        this.setIcon(null);
        this.bomba = false;
        this.setSelected(false);
        this.setEnabled(true);
        this.setText("");
        this.setBackground(null);
        this.bombaTrovata = false;
    }

}
