package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums;

public enum GameResult {

    WON("Gewonnen"),
    LOST("Verloren");;

    public final String label;

    GameResult(String label) {
        this.label = label;
    }
}
