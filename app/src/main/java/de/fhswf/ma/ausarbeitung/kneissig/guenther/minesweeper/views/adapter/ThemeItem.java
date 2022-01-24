package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views.adapter;

/**
 * Die Klasse ThemeItem wird benutzt, um den Spinner in der SettingsActivity
 * mit einem Image und einem String optisch zu versehen.
 *
 * @author Ramon Günther
 */
public class ThemeItem {

    private final String themeName;
    private final int themeImage;

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