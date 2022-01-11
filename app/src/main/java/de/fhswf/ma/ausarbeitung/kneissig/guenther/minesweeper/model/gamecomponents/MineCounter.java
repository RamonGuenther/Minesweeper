package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.gamecomponents;

/**
 * Die Klasse MineCounter speichert die Anzahl noch verbleibender
 * Minen auf dem Spielfeld.
 *
 * @author Ivonne Kneißig
 */
public class MineCounter {

    private int mineCount;

    /**
     * Der Konstruktor von MineCounter setzt die Anzahl der
     * verbleibenden Minen auf die Anzahl der GesamtMinen des
     * Spiels.
     *
     * @param numberOfMines     Gesamtzahl der Minen des
     *                          aktuellen Spiels
     */
    public MineCounter(int numberOfMines){
        this.mineCount = numberOfMines;
    }

    /*----------------------------------------------------------------------------------------------
                                            METHODEN
    ----------------------------------------------------------------------------------------------*/

    /**
     * Die Methode reduceMineCount reduziert den Zähler für
     * die verbleibenden Minen im Spiel um 1
     */
    public void reduceMineCount(){
        mineCount--;
    }

    /**
     * Die Methode increaseMineCount erhöht den Zähler für
     * die verbleibenden Minen im Spiel um 1
     */
    public void increaseMineCount(){
        mineCount++;
    }


    /*----------------------------------------------------------------------------------------------
                                         GETTER & SETTER
    ----------------------------------------------------------------------------------------------*/

    public int getMineCount() {
        return mineCount;
    }

    public void setMineCount(int mineCount) {
        this.mineCount = mineCount;
    }
}
