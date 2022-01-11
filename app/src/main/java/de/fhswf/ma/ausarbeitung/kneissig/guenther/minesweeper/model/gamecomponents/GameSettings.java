package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.gamecomponents;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.Level;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.Theme;

/**
 * Die Klasse GameSettings enthält die aktuellen Einstellungen für das Minesweeper
 * Spiel.
 *
 * @author Ivonne Kneißig
 */
public class GameSettings  {

    private boolean timerVisible;
    private boolean mineCounterVisible;
    private boolean gameModeVisible;
    private boolean vibration;
    private boolean hints;
    private boolean flagsPossible;

    private Theme theme;
    private Level level;

    private int numberOfMines;
    private int columnsX;
    private int rowsY;

    /**
     * Im Konstruktor von GameSettings werden für das Spiel vorerst
     * Standardwerte eingegeben. Diese können vom Spieler jederzeit
     * geändert werden.
     */
    public GameSettings(){
        timerVisible = true;
        mineCounterVisible = true;
        gameModeVisible = true;
        hints = false;
        flagsPossible = true;

        theme = Theme.BLUE;
        level = Level.BEGINNER;

        setBoardValuesByLevel();
    }

    /*----------------------------------------------------------------------------------------------
                                            METHODEN
    ----------------------------------------------------------------------------------------------*/

    /**
     * Die Methode setBoardValuesByLevel legt die Spielfeldgröße und die Minenanzahl für alle
     * Schwierigkeitsgrade, außer dem Benutzerdediniferten Schwierigkeitsgrad fest.
     */
    private void setBoardValuesByLevel(){
        switch (level){
            case BEGINNER:
                numberOfMines = 10;
                columnsX = 8;
                rowsY = 8;
                break;
            case ADVANCED:
                numberOfMines = 20;
                columnsX = 16;
                rowsY = 16;
                break;
            case PROFESSIONAL:
                numberOfMines = 99;
                columnsX = 16;
                rowsY = 30;
                break;
        }
    }

    /**
     * Die Methode setCustomBoardValues setzt die Spielfeldgröße und die Anzahl der Minen auf die
     * vom Spieler gewünschte Anzahl fest.
     *
     * @param numberOfMines         Vom Spieler gewählte Anzahl von Minen auf dem Spielfeld
     * @param columnsX              Vom Spieler gewählte Breite des Spielfeldes
     * @param rowsY                 Vom Spieler gewählte Höhe des Spielfeldes
     */
    public void setCustomBoardValues(int numberOfMines, int columnsX, int rowsY){

        this.numberOfMines = numberOfMines;
        this.columnsX = columnsX;
        this.rowsY = rowsY;
        MinesweeperGame.getInstance().setGameBoardSize();
        MinesweeperGame.getInstance().getMineCounter().setMineCount(numberOfMines);
    }

    /*----------------------------------------------------------------------------------------------
                                         GETTER & SETTER
    ----------------------------------------------------------------------------------------------*/

    public Level getLevel() {
        return level;
    }

    /**
     * Der Setter setLevel setzt das Level auf den vom Spieler gewählten Schwierigkeitsgrad. Für
     * alle Schwierigkeitsgrade, außer dem Benutzerdefinierten Schwierigkeitsgrad, wird auch direkt
     * die Spielfeldgröße und die Minenanzahl angepasst.
     *
     * @param level                 Vom Spieler gewählter Schwierigkeitsgrad
     */
    public void setLevel(String level) {
        switch (level){
            case "Anfänger":
                this.level = Level.BEGINNER;
                break;
            case "Fortgeschritten":
                this.level = Level.ADVANCED;
                break;
            case "Profi":
                this.level = Level.PROFESSIONAL;
                break;
            case "Benutzerdefiniert":
                this.level = Level.CUSTOM;
                return;
        }
        setBoardValuesByLevel();
        MinesweeperGame.getInstance().setGameBoardSize();
        MinesweeperGame.getInstance().getMineCounter().setMineCount(numberOfMines);
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    /**
     * Setzt das Theme für das Aussehen des Spielfeldes anhand eines gegebenen Theme-Namens.
     *
     * @param theme                 Name des gewählten Themes
     */
    public void setTheme(String theme) {
        switch (theme){
            case "Bordeaux":
                this.theme = Theme.BORDEAUX;
                break;
            case "Blau":
                this.theme = Theme.BLUE;
                break;
            case "Gruen":
                this.theme = Theme.GREEN;
                break;
            case "Grau":
                this.theme = Theme.GREY;
                break;
            case "Classic":
                this.theme = Theme.CLASSIC;
                break;
        }
    }

    public boolean isTimerVisible() {
        return timerVisible;
    }

    public void setTimerVisible(boolean timerVisible) {
        this.timerVisible = timerVisible;
    }

    public boolean isMineCounterVisible() {
        return mineCounterVisible;
    }

    public void setMineCounterVisible(boolean mineCounterVisible) {
        this.mineCounterVisible = mineCounterVisible;
    }

    public boolean isGameModeVisible() {
        return gameModeVisible;
    }

    public void setGameModeVisible(boolean gameModeVisible) {
        this.gameModeVisible = gameModeVisible;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }

    public int getColumnsX() {
        return columnsX;
    }

    public int getRowsY() {
        return rowsY;
    }

    public boolean isVibration() {
        return vibration;
    }

    public void setVibration(boolean vibration) {
        this.vibration = vibration;
    }

    public boolean isHints() {
        return hints;
    }

    public void setHints(boolean hints) {
        this.hints = hints;
    }

    public boolean isFlagsPossible() {
        return flagsPossible;
    }

    public void setFlagsPossible(boolean flagsPossible) {
        gameModeVisible = flagsPossible;
        this.flagsPossible = flagsPossible;
    }
}
