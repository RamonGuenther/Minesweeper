package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Die Klasse CustomGame speichert die Einstellungen zu einem Benutzerdefinierten
 * Spiel.
 *
 * @author Ramon Günther
 */
@Entity
public class CustomGame {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "height")
    private String height;

    @ColumnInfo(name = "width")
    private String width;

    @ColumnInfo(name = "mines")
    private String mines;

    public CustomGame(){
        id=1;
        this.height = "4";
        this.width = "4";
        this.mines = "4";
    }

    public int getId() {
        return id;
    }

    public String getHeight() {
        return height;
    }

    public String getWidth() {
        return width;
    }

    public String getMines() {
        return mines;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public void setMines(String mines) {
        this.mines = mines;
    }
}
