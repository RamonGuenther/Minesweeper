package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.Level;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.Theme;

/**
 * Die Klasse Settings, speichert die gesetzten Einstellungen der Applikation.
 *
 * @author Ramon Günther
 */
@Entity
public class Settings {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "dark_mode")
    private boolean darkMode;

    @ColumnInfo(name = "vibration")
    private boolean vibration;

    @ColumnInfo(name = "show_timer")
    private boolean showTimer;

    @ColumnInfo(name = "show_mine_counter")
    private boolean showMineCounter;

    @ColumnInfo(name = "show_mode_switch")
    private boolean showModeSwitch;

    @ColumnInfo(name = "use_flags")
    private boolean useFlags;

    @ColumnInfo(name = "show_hints")
    private boolean showHints;

    @ColumnInfo(name = "last_level")
    private String lastLevel;

    private String theme;

    public Settings() {
        id = 1;
        darkMode = false;
        vibration = true;
        showTimer = true;
        showMineCounter = true;
        showModeSwitch = true;
        useFlags = true;
        showHints = true;
        theme = Theme.BLUE.label;
        lastLevel = Level.BEGINNER.label;
    }

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

    public boolean isUseFlags() {
        return useFlags;
    }

    public boolean isShowHints() {
        return showHints;
    }

    public String getTheme() {
        return theme;
    }

    public String getLastLevel() {
        return lastLevel;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setUseFlags(boolean useFlags) {
        this.useFlags = useFlags;
    }

    public void setShowHints(boolean showHints) {
        this.showHints = showHints;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setLastLevel(String lastLevel) {
        this.lastLevel = lastLevel;
    }
}
