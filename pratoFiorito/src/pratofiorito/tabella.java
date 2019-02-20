/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pratofiorito;

import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import static java.awt.event.MouseEvent.BUTTON1;
import static java.awt.event.MouseEvent.BUTTON3;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import javax.swing.JPanel;

/**
 *
 * @author Giacomo Orsenigo
 */
public class tabella extends JPanel implements MouseListener {

    private prato gioco;

    public tabella(int numRighe, int numColonne, int numBombe) {
        super();
        gioco = new prato(numRighe, numColonne, numBombe);
        drawButton();
    }

    public tabella(LayoutManager lm, int numRighe, int numColonne, int numBombe) {
        super(lm);
        gioco = new prato(numRighe, numColonne, numBombe);
        drawButton();
    }

    public tabella() {
        super(new squareGridLayout());
        gioco = new prato();
        drawButton();
    }

    public void change(int righe, int colonne, int numBombe) {
        gioco = new prato(righe, colonne, numBombe);
        drawButton();
    }

    void drawButton() {
        removeAll();
        setLayout(new squareGridLayout(gioco.getRighe(), gioco.getColonne(), 0, 0));
        for (int r = 0; r < gioco.getRighe(); r++) {
            for (int c = 0; c < gioco.getColonne(); c++) {
                add(gioco.getBottone(r, c));
                gioco.getBottone(r, c).addMouseListener(this);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        System.out.println(me.getClickCount());
        int ris = 0;
        if (me.getButton() == BUTTON1) {
            if (me.getClickCount() == 1) {
                ris = gioco.premuto((MyToggleButton) me.getSource());
            } else if (me.getClickCount() == 2) {
                ris = gioco.doppioClick((MyToggleButton) me.getSource());
            }
        } else if (me.getButton() == BUTTON3) {
            if (me.getClickCount() == 1) {
                gioco.setBandiera((MyToggleButton) me.getSource());
            } else if (me.getClickCount() == 2) {
                gioco.setBandiera((MyToggleButton) me.getSource());
                gioco.setQuestionMark((MyToggleButton) me.getSource());
            }
        }
        if (ris == 1) {
            vinto();
        } else if (ris == -1) {
            perso();
        }
    }

    public void perso() {
        int scelta = JOptionPane.showConfirmDialog(this, "Hai perso!! Vuoi ricominciare?", "Perso", ERROR_MESSAGE);
        if (scelta == 0) {
            gioco.restart();
        } else {
            System.exit(0);
        }
    }

    public void vinto() {
        int scelta = JOptionPane.showConfirmDialog(this, "Hai vinto!! Vuoi ricominciare?", "Vinto", YES_NO_OPTION);
        if (scelta == 0) {
            gioco.restart();
        } else {
            System.exit(0);
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
