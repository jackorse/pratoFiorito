/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pratofiorito;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

/**
 *
 * @author orsenigo_giacomo
 */
public class prato {

    private final int righe;
    private final int colonne;
    private final int numBombe;
    private MyToggleButton[][] prato;

    public prato() {
        this(9, 9, 10);
    }

    public prato(int righe, int colonne, int numBombe) {
        this.righe = righe;
        this.colonne = colonne;
        this.numBombe = numBombe;
        prato = new MyToggleButton[righe][colonne];
        for (int r = 0; r < righe; r++) {
            for (int c = 0; c < colonne; c++) {
                prato[r][c] = new MyToggleButton(r, c);
            }
        }
        spawnBombe();
    }

    public MyToggleButton getBottone(int riga, int colonna) {
        return prato[riga][colonna];
    }

    public int getRighe() {
        return righe;
    }

    public int getColonne() {
        return colonne;
    }

    private int contaBombe(MyToggleButton premuto) {
        int conta = 0;
        int r = premuto.getR();
        int c = premuto.getC();
        if (r > 0 && c > 0) {   //in alto a sinistra
            if (prato[r - 1][c - 1].isBomba()) {
                conta++;
            }
        }
        if (r > 0) {            //in alto 
            if (prato[r - 1][c].isBomba()) {
                conta++;
            }
        }
        if (c > 0) {            //a sinistra
            if (prato[r][c - 1].isBomba()) {
                conta++;
            }
        }
        if (r > 0 && c < colonne - 1) { //in alto a destra
            if (prato[r - 1][c + 1].isBomba()) {
                conta++;
            }
        }

        if (r < righe - 1 && c < colonne - 1) { //in basso a destra
            if (prato[r + 1][c + 1].isBomba()) {
                conta++;
            }
        }
        if (r < righe - 1) {        //in basso
            if (prato[r + 1][c].isBomba()) {
                conta++;
            }
        }
        if (c < colonne - 1) {      //a destra
            if (prato[r][c + 1].isBomba()) {
                conta++;
            }
        }
        if (r < righe - 1 && c > 0) {       //in basso a sinistra
            if (prato[r + 1][c - 1].isBomba()) {
                conta++;
            }
        }

        return conta;
    }

//    public void resetBombe() {
//        for (int r = 0; r < righe; r++) {
//            for (int c = 0; c < colonne; c++) {
//                prato[r][c].setBomba(false);
//            }
//        }
//    }
    private void spawnBombe() {
        Random rn = new Random();
        for (int i = 0; i < Math.min(numBombe, righe * colonne); ) {
            int riga = rn.nextInt(righe);
            int colonna = rn.nextInt(colonne);
			if(!prato[riga][colonna].isBomba()) {
				prato[riga][colonna].setBomba(true);
				i++;
			}
		}
    }

    public void restart() {
        for (int r = 0; r < righe; r++) {
            for (int c = 0; c < colonne; c++) {
                prato[r][c].reset();
            }
        }
        spawnBombe();
    }

    private void writeBombeVicine(MyToggleButton premuto, int n) {
        Font font = new Font("Arial", Font.BOLD, 20);
        premuto.setFont(font);
        Color colore = null;
        switch (n) {
            case 1:
                colore = new Color(0, 0, 255);
                break;
            case 2:
                colore = new Color(0, 128, 0);
                break;
            case 3:
                colore = Color.RED;
                break;
            case 4:
                colore = new Color(0, 0, 128);
                break;
            case 5:
                colore = new Color(128, 0, 0);
                break;
            case 6:
                colore = new Color(0, 128, 128);
                break;
            default:
                colore = Color.BLACK;
                break;
        }
        premuto.setText(Integer.toString(n));
        premuto.setForeground(colore);
    }

    /**
     *
     * @param b bottone premuto
     * @return 1 se ha vinto, -1 se ha perso, 0 negli altri casi
     */
    public int premuto(MyToggleButton b) {
        if (!b.isBombaTrovata()) {
            if (b.isBomba()) {
                showBombe(b);
                checkBandiere();
                return -1;
            } else {
                show(b);
                if (vinto()) {
                    return 1;
                } else {
                    return 0;

                }
            }
        } else {
            b.setSelected(false);
        }
        return 0;
    }

    private void show(MyToggleButton b) {
        b.setSelected(true);
        b.setEnabled(false);
        int bombe = contaBombe(b);
        if (bombe == 0) {
            int r = b.getR();
            int c = b.getC();
            if (r > 0 && c > 0) {   //in alto a sinistra
                if (prato[r - 1][c - 1].isEnabled() && !prato[r - 1][c - 1].isBomba() && !prato[r - 1][c - 1].isBombaTrovata()) {
                    show(prato[r - 1][c - 1]);
                }
            }
            if (r > 0) {            //in alto 
                if (prato[r - 1][c].isEnabled() && !prato[r - 1][c].isBomba() && !prato[r - 1][c].isBombaTrovata()) {
                    show(prato[r - 1][c]);
                }
            }
            if (c > 0) {            //a sinistra
                if (prato[r][c - 1].isEnabled() && !prato[r][c - 1].isBomba() && !prato[r][c - 1].isBombaTrovata()) {
                    show(prato[r][c - 1]);
                }
            }
            if (r > 0 && c < colonne - 1) { //in alto a destra
                if (prato[r - 1][c + 1].isEnabled() && !prato[r - 1][c + 1].isBomba() && !prato[r - 1][c + 1].isBombaTrovata()) {
                    show(prato[r - 1][c + 1]);
                }
            }

            if (r < righe - 1 && c < colonne - 1) { //in basso a destra
                if (prato[r + 1][c + 1].isEnabled() && !prato[r + 1][c + 1].isBomba() && !prato[r + 1][c + 1].isBombaTrovata()) {
                    show(prato[r + 1][c + 1]);
                }
            }
            if (r < righe - 1) {        //in basso
                if (prato[r + 1][c].isEnabled() && !prato[r + 1][c].isBomba() && !prato[r + 1][c].isBombaTrovata()) {
                    show(prato[r + 1][c]);
                }
            }
            if (c < colonne - 1) {      //a destra
                if (prato[r][c + 1].isEnabled() && !prato[r][c + 1].isBomba() && !prato[r][c + 1].isBombaTrovata()) {
                    show(prato[r][c + 1]);
                }
            }
            if (r < righe - 1 && c > 0) {       //in basso a sinistra
                if (prato[r + 1][c - 1].isEnabled() && !prato[r + 1][c - 1].isBomba() && !prato[r + 1][c - 1].isBombaTrovata()) {
                    show(prato[r + 1][c - 1]);
                }
            }

        } else {
            writeBombeVicine(b, bombe);
        }
    }

    public void setBandiera(MyToggleButton b) {
        if (b.isEnabled() && !b.isQuestionMark()) {
            if (!b.isBombaTrovata()) {
                b.drawBandiera();
            } else {
                b.removeBandiera();
            }
        }
    }

    private void showBombe(MyToggleButton premuto) {
        for (int r = 0; r < righe; r++) {
            for (int c = 0; c < colonne; c++) {
                if (prato[r][c].isBomba()) {
                    prato[r][c].drawBomba();
                }
            }
        }
        premuto.drawRedBomba();
    }

    private void checkBandiere() {
        for (int r = 0; r < righe; r++) {
            for (int c = 0; c < colonne; c++) {
                if (prato[r][c].isBombaTrovata() && !prato[r][c].isBomba()) {
                    prato[r][c].drawNotBomba();
                }
            }
        }
    }

    public void setQuestionMark(MyToggleButton premuto) {
        if (!premuto.isBombaTrovata()) {
            if (!premuto.isQuestionMark()) {
                premuto.setQuestionMark();
            } else {
                premuto.removeQuestionMark();
            }
        }
    }

    /**
     *
     * @param premuto
     * @return 1 se ha vinto, -1 se ha perso, 0 negli altri casi
     */
    public int doppioClick(MyToggleButton premuto) {
        if (showVicine(premuto)) {
            return -1;
        } else if (vinto()) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     *
     * @param premuto
     * @return true in caso di perdita
     */
    public boolean showVicine(MyToggleButton premuto) {
        boolean perso = false;
        if (contaBombe(premuto) == contaBandiere(premuto)) {
            int r = premuto.getR();
            int c = premuto.getC();
            if (r > 0 && c > 0) {   //in alto a sinistra
                if (prato[r - 1][c - 1].isEnabled() && prato[r - 1][c - 1].isBomba() && !prato[r - 1][c - 1].isBombaTrovata()) {
                    System.out.println("Sei mmmmmorto");
                    perso = true;
                    showBombe(prato[r - 1][c - 1]);
                } else if (prato[r - 1][c - 1].isEnabled() && !prato[r - 1][c - 1].isBomba() && prato[r - 1][c - 1].isBombaTrovata()) {
                    prato[r - 1][c - 1].drawNotBomba();
                } else if (prato[r - 1][c - 1].isEnabled() && !prato[r - 1][c - 1].isBomba() && !prato[r - 1][c - 1].isBombaTrovata()) {
                    show(prato[r - 1][c - 1]);
                }
            }
            if (r > 0) {            //in alto 
                if (prato[r - 1][c].isEnabled() && prato[r - 1][c].isBomba() && !prato[r - 1][c].isBombaTrovata()) {
                    System.out.println("Sei mmmmmorto");
                    perso = true;
                    showBombe(prato[r - 1][c]);
                } else if (prato[r - 1][c].isEnabled() && !prato[r - 1][c].isBomba() && prato[r - 1][c].isBombaTrovata()) {
                    prato[r - 1][c].drawNotBomba();
                } else if (prato[r - 1][c].isEnabled() && !prato[r - 1][c].isBomba() && !prato[r - 1][c].isBombaTrovata()) {
                    show(prato[r - 1][c]);
                }
            }
            if (c > 0) {            //a sinistra
                if (prato[r][c - 1].isEnabled() && prato[r][c - 1].isBomba() && !prato[r][c - 1].isBombaTrovata()) {
                    System.out.println("Sei mmmmmorto");
                    perso = true;
                    showBombe(prato[r][c - 1]);
                } else if (prato[r][c - 1].isEnabled() && !prato[r][c - 1].isBomba() && prato[r][c - 1].isBombaTrovata()) {
                    prato[r][c - 1].drawNotBomba();
                } else if (prato[r][c - 1].isEnabled() && !prato[r][c - 1].isBomba() && !prato[r][c - 1].isBombaTrovata()) {
                    show(prato[r][c - 1]);
                }
            }
            if (r > 0 && c < colonne - 1) { //in alto a destra
                if (prato[r - 1][c + 1].isEnabled() && prato[r - 1][c + 1].isBomba() && !prato[r - 1][c + 1].isBombaTrovata()) {
                    System.out.println("Sei mmmmmorto");
                    perso = true;
                    showBombe(prato[r - 1][c + 1]);
                } else if (prato[r - 1][c + 1].isEnabled() && !prato[r - 1][c + 1].isBomba() && prato[r - 1][c + 1].isBombaTrovata()) {
                    prato[r - 1][c + 1].drawNotBomba();
                } else if (prato[r - 1][c + 1].isEnabled() && !prato[r - 1][c + 1].isBomba() && !prato[r - 1][c + 1].isBombaTrovata()) {
                    show(prato[r - 1][c + 1]);
                }
            }

            if (r < righe - 1 && c < colonne - 1) { //in basso a destra
                if (prato[r + 1][c + 1].isEnabled() && prato[r + 1][c + 1].isBomba() && !prato[r + 1][c + 1].isBombaTrovata()) {
                    System.out.println("Sei mmmmmorto");
                    perso = true;
                    showBombe(prato[r + 1][c + 1]);
                } else if (prato[r + 1][c + 1].isEnabled() && !prato[r + 1][c + 1].isBomba() && prato[r + 1][c + 1].isBombaTrovata()) {
                    prato[r + 1][c + 1].drawNotBomba();
                } else if (prato[r + 1][c + 1].isEnabled() && !prato[r + 1][c + 1].isBomba() && !prato[r + 1][c + 1].isBombaTrovata()) {
                    show(prato[r + 1][c + 1]);
                }
            }
            if (r < righe - 1) {        //in basso
                if (prato[r + 1][c].isEnabled() && prato[r + 1][c].isBomba() && !prato[r + 1][c].isBombaTrovata()) {
                    System.out.println("Sei mmmmmorto");
                    perso = true;
                    showBombe(prato[r + 1][c]);
                } else if (prato[r + 1][c].isEnabled() && !prato[r + 1][c].isBomba() && prato[r + 1][c].isBombaTrovata()) {
                    prato[r + 1][c].drawNotBomba();
                } else if (prato[r + 1][c].isEnabled() && !prato[r + 1][c].isBomba() && !prato[r + 1][c].isBombaTrovata()) {
                    show(prato[r + 1][c]);
                }
            }
            if (c < colonne - 1) {      //a destra
                if (prato[r][c + 1].isEnabled() && prato[r][c + 1].isBomba() && !prato[r][c + 1].isBombaTrovata()) {
                    System.out.println("Sei mmmmmorto");
                    perso = true;
                    showBombe(prato[r][c + 1]);
                } else if (prato[r][c + 1].isEnabled() && !prato[r][c + 1].isBomba() && prato[r][c + 1].isBombaTrovata()) {
                    prato[r][c + 1].drawNotBomba();
                } else if (prato[r][c + 1].isEnabled() && !prato[r][c + 1].isBomba() && !prato[r][c + 1].isBombaTrovata()) {
                    show(prato[r][c + 1]);
                }
            }
            if (r < righe - 1 && c > 0) {       //in basso a sinistra
                if (prato[r + 1][c - 1].isEnabled() && prato[r + 1][c - 1].isBomba() && !prato[r + 1][c - 1].isBombaTrovata()) {
                    System.out.println("Sei mmmmmorto");
                    perso = true;
                    showBombe(prato[r + 1][c - 1]);
                } else if (prato[r + 1][c - 1].isEnabled() && !prato[r + 1][c - 1].isBomba() && prato[r + 1][c - 1].isBombaTrovata()) {
                    prato[r + 1][c - 1].drawNotBomba();
                } else if (prato[r + 1][c - 1].isEnabled() && !prato[r + 1][c - 1].isBomba() && !prato[r + 1][c - 1].isBombaTrovata()) {
                    show(prato[r + 1][c - 1]);
                }
            }
        }
        return perso;
    }

    private int contaBandiere(MyToggleButton premuto) {
        int conta = 0;
        int r = premuto.getR();
        int c = premuto.getC();
        if (r > 0 && c > 0) {   //in alto a sinistra
            if (prato[r - 1][c - 1].isBombaTrovata()) {
                conta++;
            }
        }
        if (r > 0) {            //in alto 
            if (prato[r - 1][c].isBombaTrovata()) {
                conta++;
            }
        }
        if (c > 0) {            //a sinistra
            if (prato[r][c - 1].isBombaTrovata()) {
                conta++;
            }
        }
        if (r > 0 && c < colonne - 1) { //in alto a destra
            if (prato[r - 1][c + 1].isBombaTrovata()) {
                conta++;
            }
        }

        if (r < righe - 1 && c < colonne - 1) { //in basso a destra
            if (prato[r + 1][c + 1].isBombaTrovata()) {
                conta++;
            }
        }
        if (r < righe - 1) {        //in basso
            if (prato[r + 1][c].isBombaTrovata()) {
                conta++;
            }
        }
        if (c < colonne - 1) {      //a destra
            if (prato[r][c + 1].isBombaTrovata()) {
                conta++;
            }
        }
        if (r < righe - 1 && c > 0) {       //in basso a sinistra
            if (prato[r + 1][c - 1].isBombaTrovata()) {
                conta++;
            }
        }
        return conta;
    }

    public boolean vinto() {
        boolean vinto = true;
        for (int r = 0; r < righe; r++) {
            for (int c = 0; c < colonne; c++) {
                if (!prato[r][c].isSelected() && !prato[r][c].isBomba()) {
                    vinto = false;
                }
            }
        }
        return vinto;
    }
}
