package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.MinesweeperApplication;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.callback.GameVibrationsCallback;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.callback.MinesweeperCallback;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.GameMode;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.GameResult;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.gamecomponents.GameBoardBuilder;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views.GameEndDialog;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.gamecomponents.GameSettings;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.gamecomponents.MineCounter;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.gamecomponents.Timer;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views.Field;

/**
 * Die Klasse MinesweeperGame bildet die Instanz des Spiels und
 * bietet die notwendigen Funktionen für den Spielverlauf.
 *
 * @author Ivonne Kneißig
 */
public class MinesweeperGame {

    public static final int GAME_LOST = 0;
    public static final int GAME_WON = 1;

    @SuppressLint("StaticFieldLeak")
    private static MinesweeperGame instance;

    private Context context;
    private MinesweeperCallback minesweeperCallback;
    private GameVibrationsCallback gameVibrationsCallback;
    private final GameSettings gameSettings = new GameSettings();

    private int[][] bombPlacementField = new int[getColumnsX()][getRowsY()];
    private Field[][] minesweeperBoard = new Field[getColumnsX()][getRowsY()];

    private boolean firstClick = false;

    private GameMode gameMode = GameMode.MINE_MODE;
    private final MineCounter mineCounter = new MineCounter(gameSettings.getNumberOfMines());
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
     * Die Methode setGameBoardSize passt die Array-Felder für das Spiel entsprechend des vom
     * Spieler ausgewählten Schwierigkeitsgrades an.
     */
    public void setGameBoardSize(){
        this.bombPlacementField = new int[gameSettings.getColumnsX()][gameSettings.getRowsY()];
        this.minesweeperBoard = new Field[gameSettings.getColumnsX()][gameSettings.getRowsY()];
    }

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
        bombPlacementField = GameBoardBuilder.buildEmptyBoard(getColumnsX(), getRowsY());

        setGameBoard(context,bombPlacementField);
    }

    /**
     * Die Methode createBoardWithMines verteilt die Anzahl der gegebenen Minen auf dem
     * Spielfeld und berechnet für jedes Feld die Anzahl der benachbarten Minen. Dabei wird keine
     * Mine auf dem Feld platziert, dass der Spieler als erstes angeklickt hat.
     *
     * @param context               Aktueller Zustand der Applikation
     * @param startX                X-Position des ersten vom Spieler angeklickten Feldes
     * @param startY                Y-Position des ersten vom Spieler angeklickten Feldes
     */
    public void createBoardWithMines(Context context, int startX, int startY){

        this.context = context;
        GameBoardBuilder.generateBoardWithMines(gameSettings.getNumberOfMines(), getColumnsX(), getRowsY(), startX, startY, bombPlacementField);
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
    private void setGameBoard(Context context, final int[][] bombPlacementField ){

        for(int x = 0; x < getColumnsX(); x++ ){
            for(int y = 0; y < getRowsY(); y++ ){
                if( minesweeperBoard[x][y] == null ){
                    minesweeperBoard[x][y] = new Field(context,x,y);
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
        int x = position % getColumnsX();
        int y = position / getColumnsX();

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
    public void discoverField(int xPos, int yPos){

        if(xPos >= 0 && yPos >= 0 && xPos < getColumnsX() && yPos < getRowsY() && !getFieldAt(xPos,yPos).isTouched()){
            getFieldAt(xPos,yPos).setTouched();

            // Damit Flaggen entfernt werden, wenn Felder aufgedeckt werden, die keine Mine enthalten
            if(getFieldAt(xPos,yPos).isFlagged() || getFieldAt(xPos,yPos).isMarked()){
                getFieldAt(xPos,yPos).setFlagged(false);
                getFieldAt(xPos,yPos).setMarked(false);
                MinesweeperGame.getInstance().getMineCounter().increaseMineCount();
                minesweeperCallback.updateMineCounter(MinesweeperGame.getInstance().getMineCounter().getMineCount());
            }

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
                if(MinesweeperGame.getInstance().getGameSettings().isVibration()){
                    gameVibrationsCallback.bombExplosionVibration();
                }
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

        if(mineCounter.getMineCount() == 0 && !getFieldAt(xPos, yPos).isFlagged()){
            if(gameSettings.isHints()){
                Toast.makeText(context,context.getString(R.string.maximale_minen_makiert), Toast.LENGTH_SHORT).show();
            }
            return;
        }
        if(getFieldAt(xPos, yPos).isDiscovered()){
            if(gameSettings.isHints()){
                Toast.makeText(context,context.getString(R.string.keine_flagge_aufgedeckt_feld), Toast.LENGTH_SHORT).show();
            }
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

        if(getFieldAt(xPos, yPos).isDiscovered()){
            if(gameSettings.isHints()){
                Toast.makeText(context,context.getString(R.string.kein_fragezeiche_aufgedeckt_feld), Toast.LENGTH_SHORT).show();
            }
            return;
        }

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
     * unaufgedeckt, ist das Spiel zuende und der Spieler hat gewonnen. Die aktuellen Spielwerte
     * werden in der Highscore-Datenbank gespeichert.
     */
    private void checkGameWon(){

        int mineNotFound = gameSettings.getNumberOfMines();
        int notDiscovered = getColumnsX() * getRowsY();

        for (int x = 0; x < getColumnsX(); x++ ){
            for(int y = 0; y < getRowsY(); y++ ){

                if( getFieldAt(x,y).isDiscovered() || getFieldAt(x,y).isFlagged()
                        || !getFieldAt(x,y).isDiscovered() && getFieldAt(x,y).isMine()){            // Für Sieg, auch wenn Felder mit Bomben unberührt, aber nicht markiert
                    notDiscovered--;
                }

                if(getFieldAt(x,y).isFlagged() && getFieldAt(x,y).isMine()
                        || !getFieldAt(x,y).isDiscovered() && getFieldAt(x,y).isMine()){            // Für Sieg, auch wenn Felder mit Bomben unberührt, aber nicht markiert
                    mineNotFound--;
                }
            }
        }
        if(mineNotFound == 0 && notDiscovered == 0){
            GameEndDialog.show(context, context.getString(R.string.message_spiel_gewonnen), GAME_WON);
            timer.stopTimer();

            minesweeperCallback.updateTimer(0);

            MinesweeperApplication application = (MinesweeperApplication) context.getApplicationContext();

            application.createGameSummaryItem(
                    timer.getSecondsPassed(),
                    gameSettings.getLevel(),
                    GameResult.WON,
                    gameSettings.getNumberOfMines() + "/" + gameSettings.getNumberOfMines(),
                    gameSettings.getColumnsX() + " x " + gameSettings.getRowsY()
            );
        }
    }

    /**
     * Die Methode gameLost deckt in dem Fall, dass der Spiele das Spiel verloren
     * hat alle bisher nicht aufgedeckten Felder auf. Der Spieler hat das Spiel
     * verloren, sobald er eine Mine aufgedeckt hat, oder der Timer abgelaufen ist.
     *
     * Die aktuellen Spielwerte werden in der Highscore-Datenbank gespeichert?!?!?
     */
    public void gameLost(){
        GameEndDialog.show(context, context.getString(R.string.message_spiel_verloren), GAME_LOST);
        timer.stopTimer();

        MinesweeperApplication application = (MinesweeperApplication) context.getApplicationContext();

        application.createGameSummaryItem(
                timer.getSecondsPassed(),
                gameSettings.getLevel(),
                GameResult.LOST,
                gameSettings.getNumberOfMines() - mineCounter.getMineCount() + "/" + gameSettings.getNumberOfMines(),
                gameSettings.getColumnsX() + " x " + gameSettings.getRowsY()
        );

        for (int x = 0; x < getColumnsX(); x++ ) {
            for (int y = 0; y < getRowsY(); y++) {
                getFieldAt(x,y).setDiscovered();
                if(!getFieldAt(x,y).isMine()){                                                      // Deckt auch nicht aufgedeckte Felder ohne Mine auf
                    if(getFieldAt(x,y).isFlagged()){
                        getFieldAt(x,y).setFlagged(false);
                        getFieldAt(x,y).setFlagFalse(true);
                        mineCounter.increaseMineCount();
                        minesweeperCallback.updateMineCounter(mineCounter.getMineCount());
                    }
                    else{
                        getFieldAt(x,y).setTouched();
                    }
                }
            }
        }
    }

    /**
     * Die Methode resetGame setzt das Spielfeld zurück, sodass der Spieler direkt eine
     * neue Spielrunde auf dem gleichen Schwierigkeitsgrad beginnen kann.
     */
    public void resetGame(){
        setFirstClick(false);
        setGameMode(GameMode.MINE_MODE);

        createEmptyBoard(context);

        timer.stopTimer();
        timer.resetTimer();
        minesweeperCallback.updateTimer(0);

        minesweeperCallback.updateMineCounter(gameSettings.getNumberOfMines());
        mineCounter.setMineCount(gameSettings.getNumberOfMines());
    }

    /**
     * Die Methode newGame ist dafür zuständig, das Spielfeld zurückzusetzen, wenn man bereits ein
     * Spiel gespielt hat, ins Hauptmenü zurückkehrt und dann ein neues Spiel startet.
     */
    public void newGame(){
        resetGame();
        resetFields();
    }

    /**
     * Die Methode resetField setzt das gezeichnete Feld auf null zurück, damit die Felder bei einem
     * neuen Spiel neu gezeichnet werden können, ohne das dabei aus dem vorherigen Spiel noch
     * Werte in den Feldern vorhanden sind.
     */
    public void resetFields(){
        for(int x = 0; x < getColumnsX(); x++ ){
            for(int y = 0; y < getRowsY(); y++ ){
                minesweeperBoard[x][y] = null;
            }
        }
    }

    /**
     * Zeichnet das Spielfeld neu, wenn der Spieler während des laufenden Spiels das Theme wechselt
     * oder das der Bildschrim vom Portrait- in den Landscape-Modus wechselt und umgekehrt.
     */
    public void invalidateBoard(){
        for(int x = 0; x < getColumnsX(); x++ ){
            for(int y = 0; y < getRowsY(); y++ ){
                minesweeperBoard[x][y].invalidate();
            }
        }
    }

    /**
     * Die Methode removeFlagsAndQuestionMarks entfernt alle Flaggen und Fragezeichen, wenn der
     * Spieler in den Einstellungen die Funktion Flaggen setzen zu können ausgeschaltet hat.
     */
    public void removeFlagsAndQuestionMarks(){
        if(!getGameSettings().isFlagsPossible()){
            for(int x = 0; x < getColumnsX(); x++ ){
                for(int y = 0; y < getRowsY(); y++ ){
                    minesweeperBoard[x][y].setFlagged(false);
                    minesweeperBoard[x][y].setMarked(false);
                    minesweeperBoard[x][y].invalidate();
                }
            }
        }
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
        return gameSettings.getColumnsX();
    }

    public int getRowsY() {
        return gameSettings.getRowsY();
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }

    public GameVibrationsCallback getGameVibrationCallback() {
        return gameVibrationsCallback;
    }

    public void setGameVibrationCallback(GameVibrationsCallback gameVibrationsCallback) {
        this.gameVibrationsCallback = gameVibrationsCallback;
    }
}
