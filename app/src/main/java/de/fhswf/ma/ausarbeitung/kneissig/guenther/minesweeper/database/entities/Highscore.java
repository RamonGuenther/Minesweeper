package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.GameResult;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.Level;

@Entity
public class HighScore {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "game_played_on")
    private String gamePlayedOn;

    @ColumnInfo(name = "played_time")
    private String playedTime;

    private String level;

    @ColumnInfo(name = "game_result")
    private String gameResult;

    @ColumnInfo(name = "mines_left")
    private String minesLeft;

    @ColumnInfo(name = "field_size")
    private String fieldSize;

    public HighScore(){

    }
    public HighScore(String gamePlayedOn, String playedTime, Level level, GameResult gameResult, String minesLeft, String fieldSize) {
        this.gamePlayedOn = gamePlayedOn;
        this.playedTime = playedTime;
        this.level = level.label;
        this.gameResult = gameResult.label;
        this.minesLeft = minesLeft;
        this.fieldSize = fieldSize;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGamePlayedOn(String gamePlayedOn) {
        this.gamePlayedOn = gamePlayedOn;
    }

    public void setPlayedTime(String playedTime) {
        this.playedTime = playedTime;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setGameResult(String gameResult) {
        this.gameResult = gameResult;
    }

    public void setMinesLeft(String minesLeft) {
        this.minesLeft = minesLeft;
    }

    public void setFieldSize(String fieldSize) {
        this.fieldSize = fieldSize;
    }

    public Long getId() {
        return id;
    }

    public String getGamePlayedOn() {
        return gamePlayedOn;
    }

    public String getPlayedTime() {
        return playedTime;
    }

    public String getLevel() {
        return level;
    }

    public String getGameResult() {
        return gameResult;
    }

    public String getMinesLeft() {
        return minesLeft;
    }

    public String getFieldSize() {
        return fieldSize;
    }
}
