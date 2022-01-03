package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.MinesweeperCallback;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.GameMode;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views.GameBoard;

public class GameActivity extends AppCompatActivity implements MinesweeperCallback{

    SwitchMaterial gameMode;
    TextView mineCounter;
    TextView timer;

    ImageView mine;
    ImageView clock;

    //R & I - Minesweeper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameMode = findViewById(R.id.gameMode);

        gameMode.setOnClickListener(e -> {
            if(gameMode.isChecked()){
                MinesweeperGame.getInstance().setGameMode(GameMode.FLAG_MODE);
            }
            else{
                MinesweeperGame.getInstance().setGameMode(GameMode.MINE_MODE);
            }
        });

        ImageButton resetButton = findViewById(R.id.resetGame);

        resetButton.setOnClickListener(e -> {
            MinesweeperGame.getInstance().resetGame();
        });

        mineCounter = findViewById(R.id.mineCounter);
        mineCounter.setText(Integer.toString(MinesweeperGame.getInstance().getMineCounter().getMineCount()));

        timer = findViewById(R.id.timer);
        timer.setText(("0"));

        clock = findViewById(R.id.iconTimer);
        mine = findViewById(R.id.iconMine);

        MinesweeperGame.getInstance().setMinesweeperCallback(this);
        MinesweeperGame.getInstance().getTimer().setMinesweeperCallback(this);




        if(MinesweeperGame.getInstance().getGameSettings().isMineCounterVisible()){
            mineCounter.setVisibility(View.VISIBLE);
            mine.setVisibility(View.VISIBLE);
        }else{
            mineCounter.setVisibility(View.INVISIBLE);
            mine.setVisibility(View.INVISIBLE);
        }

        if(MinesweeperGame.getInstance().getGameSettings().isTimerVisible()){
            timer.setVisibility(View.VISIBLE);
            clock.setVisibility(View.VISIBLE);
        }else{
            timer.setVisibility(View.INVISIBLE);
            clock.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void updateMineCounter(int mineCount) {
        Log.d("MineCounter", Integer.toString(mineCount));
        mineCounter.setText(Integer.toString(mineCount));
    }

    @Override
    public void updateTimer(int time) {
        Log.d("Timer", Integer.toString(time));
        timer.setText(Integer.toString(time));
    }

    @Override
    protected void onPause() {
        super.onPause();
        MinesweeperGame.getInstance().getTimer().stopTimer();
    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        MinesweeperGame.getInstance().getTimer().startTimer();
//    }

    @Override
    protected void onResume() {
        super.onResume();
        if(MinesweeperGame.getInstance().isFirstClick()){
            MinesweeperGame.getInstance().getTimer().restartTimer();
        }





        Log.d("Resume", "onResume wurde aufgerufen");
        // Wenn zurück Button und dann wieder auf Spielen, wird der hier aufgerufen

        // Und wenn man raustabt und wieder reingeht
    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        MinesweeperGame.getInstance().resetGame();
//        Log.d("Start", "onStart wurde aufgerufen");
//
//        // wird auch aufgerufen, wenn rausgetabt wurde
//    }
}