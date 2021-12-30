package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums;

public enum GameMode {

    MINE_MODE("Minenmodus"),
    FLAG_MODE("Flaggenmodus");

    public final String label;

    GameMode(String label) {
        this.label = label;
    }
}
