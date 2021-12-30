package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums;

public enum Level {

    BEGINNER("Anfänger"),
    ADVANCED("Fortgeschritten"),
    PROFESSIONAL("Profi");

    public final String label;

    Level(String label) {
        this.label = label;
    }
}
