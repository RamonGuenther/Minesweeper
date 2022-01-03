package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.gameComponents;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.Level;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.Theme;

public class GameSettings  {

    private boolean timerVisible;
    private boolean mineCounterVisible;
    private boolean gameModeVisible;

    private Theme theme;
    private Level level;

    private int numberOfMines;
    private int columnsX;
    private int rowsY;

    public GameSettings(){
        timerVisible = true;
        mineCounterVisible = true;
        gameModeVisible = true;

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

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
        setBoardValuesByLevel();
        MinesweeperGame.getInstance().setGameBoardSize();
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
                numberOfMines = 40;
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

    public void setCustomBoardValues(int numberOfMines, int columnsX, int rowsY){

        level = Level.CUSTOM;

        if(MinesweeperGame.getInstance().getGameSettings().level.equals(Level.CUSTOM)){
            this.numberOfMines = numberOfMines;
            this.columnsX = columnsX;
            this.rowsY = rowsY;
        }
    }
}
