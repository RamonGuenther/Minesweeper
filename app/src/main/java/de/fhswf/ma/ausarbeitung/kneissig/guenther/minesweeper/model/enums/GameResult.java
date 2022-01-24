package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums;


/**
 * Das Enum GameResult enthält die möglichen Resultate eines gespielten Spiels.
 *
 * @author Ramon Günther
 */
public enum GameResult {

    WON("Gewonnen"),
    LOST("Verloren");;

    public final String label;

    GameResult(String label) {
        this.label = label;
    }
}
