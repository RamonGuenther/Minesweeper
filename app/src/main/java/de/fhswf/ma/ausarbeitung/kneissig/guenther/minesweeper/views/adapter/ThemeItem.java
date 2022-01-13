package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views.adapter;

public class ThemeItem {
    private String themeName;
    private int themeImage;

    public ThemeItem(String themeName, int themeImage) {
        this.themeName = themeName;
        this.themeImage = themeImage;
    }

    public String getThemeName() {
        return themeName;
    }

    public int getThemeImageId() {
        return themeImage;
    }
}