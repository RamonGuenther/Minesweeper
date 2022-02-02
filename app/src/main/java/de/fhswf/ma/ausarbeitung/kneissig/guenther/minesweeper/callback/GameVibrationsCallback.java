package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.callback;

/**
 * Das Interface GameVibrationsCallback dient als Schnittstelle zwischen der GameActivity
 * und dem MineSweeper-Spiel. Es stellt Methoden zur Verfügung, die das Vibrationsverhalten
 * für das Spiel steuern.
 *
 * @author Ivonne Kneißig
 */
public interface GameVibrationsCallback {

    /**
     * Die Methode bombExplosionVibration soll das Vibrationsverhalten beim Explodieren
     * der Bomben festelegen, wenn der Spieler eine Mine aufgedeckt hat.
     */
    void bombExplosionVibration();

    /**
     * Die Methode onLongClickVibration soll das Vibrationsverhalten beim einem langen
     * Klick auf ein Feld des Spielfeldes festlegen.
     */
    void onLongClickVibration();

    /**
     * Die Methode onResetGameVibration legt das Vibrationsmuster für den Fall fest, dass
     * der Spieler den Button zum Zurücksetzen / Neustarten des Spiels anklickt.
     */
    void onResetGameVibration();
}
