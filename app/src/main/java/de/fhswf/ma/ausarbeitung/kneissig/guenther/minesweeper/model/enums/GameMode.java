package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums;

/**
 * Das Enum GameMode enthält die möglichen Modi für das
 * Minesweeper Spiel. Der Spieler kann während des laufenden
 * Spiels zwischen den Modi wechseln, um das Verhalten beim
 * Anklicken eines Feldes entsprechend des Modus zu verändern.
 *
 * @author Ivonne Kneißig
 */
public enum GameMode {

    MINE_MODE("Minenmodus"),
    FLAG_MODE("Flaggenmodus");

    public final String label;

    GameMode(String label) {
        this.label = label;
    }
}
