package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.MinesweeperCallback;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.GameMode;

public class GameActivity extends AppCompatActivity implements MinesweeperCallback {

    TextView mineCounter;
    TextView timer;

    //R & I - Minesweeper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        SwitchMaterial switchMaterial = findViewById(R.id.gameMode);

        switchMaterial.setOnClickListener(e -> {
            if(switchMaterial.isChecked()){
                MinesweeperGame.getInstance().setGameMode(GameMode.FLAG_MODE);
            }
            else{
                MinesweeperGame.getInstance().setGameMode(GameMode.MINE_MODE);
            }
        });

        ImageButton resetButton = findViewById(R.id.resetGame);

        resetButton.setOnClickListener(e -> MinesweeperGame.getInstance().resetGame());

        mineCounter = findViewById(R.id.mineCounter);
        mineCounter.setText(Integer.toString(MinesweeperGame.getInstance().getMineCounter().getMineCount()));

        timer = findViewById(R.id.timer);
        timer.setText(("0"));

        MinesweeperGame.getInstance().setMinesweeperCallback(this);
        MinesweeperGame.getInstance().getTimer().setMinesweeperCallback(this);

        //MinesweeperGame.getInstance().createGrid(this);
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
}