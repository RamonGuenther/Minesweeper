package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.callback;


/**
 * Das Interface MinesweeperCallback dient als Verbindung zwischen der Activity,
 * welche Elemente besitzt und steuert, die das Minesweeper-Spiel betreffen und
 * dem Minesweeper-Spiel. Die Activity implementiert die Methoden, sodass diese
 * vom Minesweeper-Spiel aus aufgerufen werden können.
 *
 * @author Ivonne Kneißig
 */
public interface MinesweeperCallback {

    /**
     * Die Methode updateMineCounter ist dafür zuständig, dass immer der aktuelle Wert
     * des MineCounters in dem entprechenden Element der View angezeigt wird.
     *
     * @param mineCount         Aktuelle Anzahl an verbleibenden Minen auf dem Feld
     */
    void updateMineCounter(int mineCount);

    /**
     * Die Methode updateTimer ist dafür zuständig, dass immer der aktuelle Wert
     * des Timers in dem entprechenden Element der View angezeigt wird.
     *
     * @param time              Aktuelle Zahl der verstrichenen Sekunden seit Spielbeginn
     */
    void updateTimer(int time);

}
