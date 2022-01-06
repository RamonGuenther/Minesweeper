package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.gameComponents;

import android.os.Handler;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.MinesweeperCallback;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame;

/**
 * Die Klasse Timer erstellt einen Timer für das Minesweeper-Spiel.
 * Der Timer wird in Sekunden dargestellt.
 *
 * @author Ivonne Kneißig
 */
public class Timer {

    private final Handler timer = new Handler();
    private int secondsPassed = 0;
    private MinesweeperCallback minesweeperCallback;

    /**
     * Die Konstruktor von Timer erstellt einen neuen Timer
     * für das Minesweeper-Spiel.
     */
    public Timer (){
    }

    /*----------------------------------------------------------------------------------------------
                                            METHODEN
    ----------------------------------------------------------------------------------------------*/

    /**
     * Die Methode startTimer startet den Timer bei 0 Sekunden.
     */
    public void startTimer()
    {
        if (secondsPassed == 0)
        {
            timer.removeCallbacks(updateTime);
            timer.postDelayed(updateTime, 1000);
        }
    }

    /**
     * Die Methode stopTimer hält den Timer bei der aktuellen
     * Anzahl von Sekunden an.
     */
    public void stopTimer()
    {
        timer.removeCallbacks(updateTime);
    }

    public void restartTimer(){
        timer.removeCallbacks(updateTime);
        timer.postDelayed(updateTime, 1000);
    }

    /**
     * Das Runnable-Object updateTime sorgt dafür, dass der Timer hochgezählt wird.
     * Sobald der Timer 999 Sekunden erreicht hat, hat der Spieler das aktuelle Spiel
     * verloren.
     */
    private final Runnable updateTime = new Runnable()
    {
        public void run()
        {
            long currentMilliseconds = System.currentTimeMillis();
            ++secondsPassed;
            minesweeperCallback.updateTimer(secondsPassed);
            timer.postAtTime(this, currentMilliseconds);
            timer.postDelayed(updateTime, 1000);

            if(secondsPassed == 999){
                MinesweeperGame.getInstance().timeIsOver();
            }
        }
    };

    /**
     * Die Methode resetTimer sorgt dafür, dass der Timer im Falle eines
     * neuen Spiels wieder urückgesetzt wird.
     */
    public void resetTimer(){
        secondsPassed = 0;
    }

    /*----------------------------------------------------------------------------------------------
                                         GETTER & SETTER
    ----------------------------------------------------------------------------------------------*/

    public void setMinesweeperCallback(MinesweeperCallback minesweeperCallback) {
        this.minesweeperCallback = minesweeperCallback;
    }

    public int getSecondsPassed() {
        return secondsPassed;
    }
}
