package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.GameResult;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.Level;

/**
 * Die Klasse GameSummary speichert das Spielergebnis eines Spiel.
 *
 * @author Ramon Günther
 */
@Entity
public class GameSummary {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "game_played_on")
    private String gamePlayedOn;

    @ColumnInfo(name = "played_time")
    private int playedTime;

    private String level;

    @ColumnInfo(name = "game_result")
    private String gameResult;

    @ColumnInfo(name = "mines_found")
    private String minesFound;

    @ColumnInfo(name = "field_size")
    private String fieldSize;

    public GameSummary(){

    }
    public GameSummary(String gamePlayedOn, int playedTime, Level level, GameResult gameResult, String minesFound, String fieldSize) {
        this.gamePlayedOn = gamePlayedOn;
        this.playedTime = playedTime;
        this.level = level.label;
        this.gameResult = gameResult.label;
        this.minesFound = minesFound;
        this.fieldSize = fieldSize;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGamePlayedOn(String gamePlayedOn) {
        this.gamePlayedOn = gamePlayedOn;
    }

    public void setPlayedTime(int playedTime) {
        this.playedTime = playedTime;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setGameResult(String gameResult) {
        this.gameResult = gameResult;
    }

    public void setMinesFound(String minesFound) {
        this.minesFound = minesFound;
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

    public int getPlayedTime() {
        return playedTime;
    }

    public String getLevel() {
        return level;
    }

    public String getGameResult() {
        return gameResult;
    }

    public String getMinesFound() {
        return minesFound;
    }

    public String getFieldSize() {
        return fieldSize;
    }
}
