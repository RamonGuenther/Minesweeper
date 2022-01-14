package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.callback;

/**
 * Das Interface GameVibrationsCallback dient als Schnittstelle zwischen der GameActivity
 * und dem MineSweeper-Spiel. Es stellt Methoden zur Verfügung, die das Vibrationsverhalten
 * für das Spiel steuern.
 *
 * @author Ivonne Kneißig
 */
public interface GameVibrationsCallback {

    void bombExlposionVibration();

    void onLongClickVibration();

    void onResetGameVibration();
}
