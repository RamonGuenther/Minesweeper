package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.highscorecomponents;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.HighScore;

public class HighScoreItem {
    private int mImageResource;
    private String mText1;
    private String mText2;

    public HighScoreItem(int mImageResource, String mText1, String mText2) {
        this.mImageResource = mImageResource;
        this.mText1 = mText1;
        this.mText2 = mText2;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public String getmText1() {
        return mText1;
    }

    public String getmText2() {
        return mText2;
    }
}
