package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model;

public class MineCounter {

    private int mineCount;

    public MineCounter(int numberOfMines){
        this.mineCount = numberOfMines;
    }

    public int getMineCount() {
        return mineCount;
    }

    public void setMineCount(int mineCount) {
        this.mineCount = mineCount;
    }

    public void reduceMineCount(){
        mineCount--;
    }

    public void increaseMineCount(){
        mineCount++;
    }

    public void explosionMineCount(){
        mineCount = 0;
    }
}
