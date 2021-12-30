package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums;

/**
 * Das Enum Level enthält die möglichen Schwierigkeitsgrade für
 * das Minesweeper Spiel. Vor Beginn des Spiels kann der Benutzer den
 * gewünschten Schwierigkeitsgrad auswählen.
 *
 * @author Ivonne Kneißig
 */
public enum Level {

    BEGINNER("Anfänger"),
    ADVANCED("Fortgeschritten"),
    PROFESSIONAL("Profi");

    public final String label;

    Level(String label) {
        this.label = label;
    }
}
