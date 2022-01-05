package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.Theme;

@Entity
public class Settings {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "dark_mode")
    public boolean darkMode;

    @ColumnInfo(name = "vibration")
    public boolean vibration;

    public boolean showTimer;

    public boolean showMineCounter;
    public boolean showModeSwitch;

    public String theme;

    public Settings() {
        this.id = 1;
        this.darkMode = false;
        this.vibration = true;
        this.showTimer = true;
        this.showMineCounter = true;
        this.showModeSwitch = true;
        this.theme = Theme.BLUE.label;
    }

    //Getter & Setter nur zur besseren Lesbarkeit im Code

    public int getId() {
        return id;
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public boolean isVibration() {
        return vibration;
    }

    public boolean isShowTimer() {
        return showTimer;
    }

    public boolean isShowMineCounter() {
        return showMineCounter;
    }

    public boolean isShowModeSwitch() {
        return showModeSwitch;
    }

    public String getTheme() {
        return theme;
    }

    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
    }

    public void setVibration(boolean vibration) {
        this.vibration = vibration;
    }

    public void setShowTimer(boolean showTimer) {
        this.showTimer = showTimer;
    }

    public void setShowMineCounter(boolean showMineCounter) {
        this.showMineCounter = showMineCounter;
    }

    public void setShowModeSwitch(boolean showModeSwitch) {
        this.showModeSwitch = showModeSwitch;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
