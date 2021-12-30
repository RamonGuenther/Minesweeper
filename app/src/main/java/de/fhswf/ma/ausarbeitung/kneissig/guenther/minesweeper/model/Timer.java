package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model;

import android.os.Handler;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.MinesweeperCallback;

public class Timer {

    private Handler timer = new Handler();
    private int secondsPassed = 0;
    private MinesweeperCallback minesweeperCallback;

    public Timer (){
    }

    public void startTimer()
    {
        if (secondsPassed == 0)
        {
            timer.removeCallbacks(updateTimeElasped);
            // tell timer to run call back after 1 second
            timer.postDelayed(updateTimeElasped, 1000);
        }
    }

    public void stopTimer()
    {
        // disable call backs
        timer.removeCallbacks(updateTimeElasped);
    }

    // timer call back when timer is ticked
    private Runnable updateTimeElasped = new Runnable()
    {
        public void run()
        {
            long currentMilliseconds = System.currentTimeMillis();
            ++secondsPassed;
            minesweeperCallback.updateTimer(secondsPassed);

            // add notification
            timer.postAtTime(this, currentMilliseconds);
            // notify to call back after 1 seconds
            // basically to remain in the timer loop
            timer.postDelayed(updateTimeElasped, 1000);

            if(secondsPassed == 999){
                MinesweeperGame.getInstance().timeIsOver();
            }
        }
    };

    public MinesweeperCallback getMinesweeperCallback() {
        return minesweeperCallback;
    }

    public void setMinesweeperCallback(MinesweeperCallback minesweeperCallback) {
        this.minesweeperCallback = minesweeperCallback;
    }

    public void resetTimer(){
        secondsPassed = 0;
    }
}
