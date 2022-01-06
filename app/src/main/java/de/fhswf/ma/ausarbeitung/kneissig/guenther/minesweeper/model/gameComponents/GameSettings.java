package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.gameComponents;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.Level;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.Theme;

public class GameSettings  {

    private boolean timerVisible;
    private boolean mineCounterVisible;
    private boolean gameModeVisible;
    private boolean vibration;
    private boolean hints;

    private Theme theme;
    private Level level;

    private int numberOfMines;
    private int columnsX;
    private int rowsY;

    public GameSettings(){
        timerVisible = true;
        mineCounterVisible = true;
        gameModeVisible = true;
        hints = false;

        theme = Theme.BLUE;
        level = Level.BEGINNER;

        setBoardValuesByLevel();
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

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

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

    public Level getLevel() {
        return level;
    }

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

    public void setCustomBoardValues(int numberOfMines, int columnsX, int rowsY){

        this.numberOfMines = numberOfMines;
        this.columnsX = columnsX;
        this.rowsY = rowsY;
        MinesweeperGame.getInstance().setGameBoardSize();
        MinesweeperGame.getInstance().getMineCounter().setMineCount(numberOfMines);
    }


    public int getNumberOfMines() {
        return numberOfMines;
    }

    public void setNumberOfMines(int numberOfMines) {
        this.numberOfMines = numberOfMines;
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
}
