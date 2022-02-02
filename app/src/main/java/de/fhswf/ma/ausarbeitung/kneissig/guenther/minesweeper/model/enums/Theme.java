package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums;

/**
 * Das Enum Theme enthält die Themes, welche der Spieler für das Spielfeld
 * auswählen kann. Anhand des Themes ändern sich die Farben für die einzelnen
 * Felder des Spielfeldes.
 *
 * @author Ivonne Kneißig
 */
public enum Theme {

    BORDEAUX("Bordeaux"),
    BLUE("Blau"),
    GREEN("Grün"),
    GREY("Grau");

    public final String label;

    Theme(String label) {
        this.label = label;
    }
}
