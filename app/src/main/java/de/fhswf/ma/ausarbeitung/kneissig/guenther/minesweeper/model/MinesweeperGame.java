package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model;

import android.content.Context;
import android.widget.Toast;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.MinesweeperCallback;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.GameMode;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.gameComponents.GameBoardBuilder;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.gameComponents.MineCounter;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.gameComponents.Timer;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views.Field;

/**
 * Die Klasse MinesweeperGame bildet die Instanz des Spiels und
 * bietet die notwendigen Funktionen für den Spielverlauf.
 *
 * @author Ivonne Kneißig
 */
public class MinesweeperGame {

    private static MinesweeperGame instance;
    private Context context;
    MinesweeperCallback minesweeperCallback;

    public int numberOfMines = 10;
    public int columnsX = 16;
    public int rowsY = 16;

    private int[][] bombPlacementField = new int[columnsX][rowsY];
    private final Field[][] minesweeperBoard = new Field[columnsX][rowsY];

    private boolean firstClick = false;

    private GameMode gameMode = GameMode.MINE_MODE;
    private final MineCounter mineCounter = new MineCounter(numberOfMines);
    private final Timer timer = new Timer();


    /**
     * Die Methode getInstance gibt die aktuelle Spielinstanz zurück,
     * sofern bereits eine existiert. Wenn nicht wird eine neue
     * Instanz des Spiels erstellt.
     *
     * @return                  Instanz des Minesweeper-Spiels
     */
    public static MinesweeperGame getInstance() {
        if( instance == null ){
            instance = new MinesweeperGame();
        }
        return instance;
    }


    /*----------------------------------------------------------------------------------------------
                                            METHODEN
    ----------------------------------------------------------------------------------------------*/

    /**
     * Die Methode createEmptyBoard erstellt ein neues Spielfeld, welches zunächst für
     * jedes Feld den Wert 0 hat, also keine Mine enthält. So wird kannt gewährleistet
     * werden, dass der Spieler bei seinem ersten Zug keine Mine aufdecken kann und das
     * Spiel sofort verloren ist.
     *
     * @param context               Aktueller Zustand der Applikation
     */
    public void createEmptyBoard(Context context){
        this.context = context;
        bombPlacementField = GameBoardBuilder.buildEmptyBoard(columnsX, rowsY);
        setGameBoard(context,bombPlacementField);
    }

    /**
     * Die Methode createBoardWithMines verteilt die Anzahl der gegebenen Minen auf dem
     * Spielfeld und berechnet für jedes Feld die Anzahl der benachbarten Minen.
     *
     * @param context               Aktueller Zustand der Applikation
     */
    public void createBoardWithMines(Context context, int startX, int startY){
        this.context = context;
        GameBoardBuilder.generateBoardWithMines(numberOfMines, columnsX, rowsY, startX, startY, bombPlacementField);
        setGameBoard(context,bombPlacementField);
    }

    /**
     * Die Methode setGameBoard erstellt das für den Spieler sichtbare Spielfeld auf der View,
     * anhand des integer-Arrays mit den Werten der Minenpositionen und der Anzahl benachbarter Minen.
     *
     * @param context               Aktueller Zustand der Applikation
     * @param bombPlacementField    integer-Feld, welches die Minenpositionen und die berechneten
     *                              Werte für Nachbarminen der leeren Felder enhtält.
     */
    private void setGameBoard(final Context context, final int[][] bombPlacementField ){
        for(int x = 0; x < columnsX; x++ ){
            for(int y = 0; y < rowsY; y++ ){
                if( minesweeperBoard[x][y] == null ){
                    minesweeperBoard[x][y] = new Field( context , x,y);
                }
                minesweeperBoard[x][y].setFieldValue(bombPlacementField[x][y]);
                minesweeperBoard[x][y].invalidate();
            }
        }
    }

    /**
     * Die Methode getFieldAt bestimmt das Feld anhand der vom Spieler geklickten
     * Position auf der View. Sie wird in der Klasse GameBoardAdapter (innere Klasse
     * von GameBoard) für das Überschreiben der Methode getView benötigt.
     *
     * @param position           Vom Spieler angeklickte Position auf der View
     * @return                   Feld, dass der Spieler ausgewählt hat
     */
    public Field getFieldAt(int position) {
        int x = position % columnsX;
        int y = position / columnsX; //??? warum nicht Rows?

        return minesweeperBoard[x][y];
    }

    /**
     * Die Methode getFieldAt bestimmt das Feld anhand des Feldes (Spalte und Zeile) auf dem
     * Spielfeldraster, welches der Spieler angeklickt hat.
     *
     * @param xPos              Spalte des Feldes, dass der Spieler angeklickt hat
     * @param yPos              Zeile des Felder, dass der Spieler angeklickt hat
     * @return                  Feld, dass der Spieler angeklickt hat
     */
    public Field getFieldAt(int xPos, int yPos ){
        return minesweeperBoard[xPos][yPos];
    }


    /**
     * Die Methode discoverField deckt ein Feld an der gegebenen Position auf dem Spielfeld
     * auf. Wenn ein komplett leeres Feld, ohne benachbarte Minen aufgedeckt wurde, werden
     * alle weiteren Felder drumherum aufgdeckt, bis Felder erreicht werden, die benachbarte
     * Deckt der Spieler eine Mine auf, ist das Spiel verloren. Bei jedem Aufdecken eines
     * Feldes, wird geprüft, ob das Spiel dadurch beendet wurde.
     *
     * @param xPos              Spalte des Feldes, dass der Spieler aufdeckt
     * @param yPos              Zeile des Feldes, dass der Spieler aufdeckt
     */
    public void discoverField(int xPos, int yPos ){

        if(xPos >= 0 && yPos >= 0 && xPos < columnsX && yPos < rowsY && !getFieldAt(xPos,yPos).isTouched()){
            getFieldAt(xPos,yPos).setTouched();

            if(getFieldAt(xPos,yPos).getFieldValue() == 0){
                for(int x = -1 ; x <= 1 ; x++ ){
                    for( int y = -1 ; y <= 1 ; y++){
                        if( x != y ){
                            discoverField(xPos + x , yPos + y);
                        }
                    }
                }
            }
            if(getFieldAt(xPos,yPos).isMine()){
                gameLost();
            }
        }
        checkGameWon();
    }

    /**
     * Die Methode placeFlag setzt eine Flagge auf dem Spielfeld, um eine
     * mögliche Mine zu markieren. Wurden bereits so viele Flaggen gesetzt, wie sich
     * Minen auf dem Spielfeld befinden, kann keine weitere Flagge gesetzt werden.
     *
     * @param xPos              Spalte des Feldes, dass der Spieler aufdeckt
     * @param yPos              Zeile des Feldes, dass der Spieler aufdeckt
     */
    public void placeFlag(int xPos , int yPos ){

        if(mineCounter.getMineCount() == 0){
            Toast.makeText(context,"Die maximale Anzahl an Minen wurde markiert", Toast.LENGTH_SHORT).show();
            return;
        }

        getFieldAt(xPos,yPos).setFlagged(!getFieldAt(xPos,yPos).isFlagged());
        getFieldAt(xPos,yPos).invalidate();

        if(getFieldAt(xPos,yPos).isFlagged()){
            getFieldAt(xPos,yPos).setMarked(false);
            mineCounter.reduceMineCount();
        }
        else{
            mineCounter.increaseMineCount();
        }
        minesweeperCallback.updateMineCounter(mineCounter.getMineCount());
    }

    /**
     * Die Methode placeQuestionMark setzt ein Fragezeichen auf dem Spielfeld, mit dem
     * der Spieler ein Feld markieren kann, bei dem er sich unsicher ist, ob dort
     * eine Mine ist, oder nicht.
     *
     * @param xPos              Spalte des Feldes, dass der Spieler aufdeckt
     * @param yPos              Zeile des Feldes, dass der Spieler aufdeckt
     */
    public void placeQuestionMark(int xPos , int yPos ){

        if (getFieldAt(xPos,yPos).isFlagged()) {
            getFieldAt(xPos,yPos).setFlagged(false);
            mineCounter.increaseMineCount();
            minesweeperCallback.updateMineCounter(mineCounter.getMineCount());
        }
        getFieldAt(xPos,yPos).setMarked(!getFieldAt(xPos,yPos).isMarked());
        getFieldAt(xPos,yPos).invalidate();
    }

    /**
     * Die Methode checkGameWon prüft, ob die Bedingungen für das Ende des Spiels gegeben sind.
     * Sind alle Felder ohne Minen aufgedeckt und die Felder mit Minen markiert oder noch
     * unaufgedeckt, ist das Spiel zuende und der Spieler hat gewonnen.
     *
     * @return                  Gibt zurück, ob das Spiel beendet wurde.
     */
    private boolean checkGameWon(){

        int mineNotFound = numberOfMines;
        int notDiscovered = columnsX * rowsY;

        for (int x = 0; x < columnsX; x++ ){
            for(int y = 0; y < rowsY; y++ ){

                if( getFieldAt(x,y).isDiscovered() || getFieldAt(x,y).isFlagged()
                        || !getFieldAt(x,y).isDiscovered() && getFieldAt(x,y).isMine()){            // Für Sieg, auch wenn Felder mit Bomben unberührt, aber nicht markiert
                    notDiscovered--;
                }

                if( getFieldAt(x,y).isFlagged() && getFieldAt(x,y).isMine()
                        || !getFieldAt(x,y).isDiscovered() && getFieldAt(x,y).isMine()){            // Für Sieg, auch wenn Felder mit Bomben unberührt, aber nicht markiert
                    mineNotFound--;
                }
            }
        }
        if( mineNotFound == 0 && notDiscovered == 0 ){
            Toast.makeText(context,"Game won", Toast.LENGTH_SHORT).show();                      // Hier ein Modal bauen!!!
            timer.stopTimer();
            minesweeperCallback.updateTimer(0);
        }
        return false;
    }


    /**
     * Die Methode gameLost deckt in dem Fall, dass der Spiele das Spiel verloren
     * hat alle bisher nicht aufgedeckten Felder auf. Der Spieler hat das Spiel
     * verloren, sobald er eine Mine aufgedeckt hat, oder der Timer abgelaufen ist.
     */
    private void gameLost(){
        Toast.makeText(context,"Game lost", Toast.LENGTH_SHORT).show();                         // Hier ein Modal bauen!!!
        timer.stopTimer();

        for (int x = 0; x < columnsX; x++ ) {
            for (int y = 0; y < rowsY; y++) {
                getFieldAt(x,y).setDiscovered();
                if(!getFieldAt(x,y).isMine()){                                                      // Deckt auch nicht aufgedeckte Felder ohne Mine auf
                    getFieldAt(x,y).setTouched();
                }
                mineCounter.setMineCount(0);
                minesweeperCallback.updateMineCounter(0);
            }
        }
    }

    /**
     * Die Methode timeIsOver sorgt dafür, dass das Spiel verloren ist, sobald
     * der Timer abgelaufen ist, also die 999 Sekunden erreicht hat.
     */
    public void timeIsOver(){
        gameLost();
    }

    /**
     * Die Methode resetGame setzt das Spielfeld zurück, sodass der Spieler direkt eine
     * neue Spielrunde auf dem gleichen Schwierigkeitsgrad beginnen kann.
     */
    public void resetGame(){
        createEmptyBoard(context);
        setFirstClick(false);

        timer.stopTimer();
        timer.resetTimer();
        minesweeperCallback.updateTimer(0);

        minesweeperCallback.updateMineCounter(numberOfMines);
        mineCounter.setMineCount(numberOfMines);
    }

    /*----------------------------------------------------------------------------------------------
                                         GETTER & SETTER
    ----------------------------------------------------------------------------------------------*/

    public boolean isFirstClick() {
        return firstClick;
    }

    public void setFirstClick(boolean value) {
        this.firstClick = value;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public MineCounter getMineCounter() {
        return mineCounter;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setMinesweeperCallback(MinesweeperCallback minesweeperCallback) {
        this.minesweeperCallback = minesweeperCallback;
    }

    public int getColumnsX() {
        return columnsX;
    }

    public void setColumnsX(int columnsX) {
        this.columnsX = columnsX;
    }

    public int getRowsY() {
        return rowsY;
    }

    public void setRowsY(int rowsY) {
        this.rowsY = rowsY;
    }
}
