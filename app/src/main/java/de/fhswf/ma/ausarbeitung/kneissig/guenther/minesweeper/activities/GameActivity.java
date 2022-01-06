package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.MinesweeperCallback;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.GameMode;

public class GameActivity extends AppCompatActivity implements MinesweeperCallback{

    SwitchMaterial gameMode;
    TextView flagMode;
    TextView mineMode;

    TextView mineCounter;
    TextView timer;

    ImageView mine;
    ImageView clock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);



        //Switch zum Wechseln zwischen Minen-Modus und Flaggen-Modus
        gameMode = findViewById(R.id.gameView_gameMode);
        gameMode.setOnClickListener(e -> {
            if(gameMode.isChecked()){
                MinesweeperGame.getInstance().setGameMode(GameMode.FLAG_MODE);
            }
            else{
                MinesweeperGame.getInstance().setGameMode(GameMode.MINE_MODE);
            }
        });
        mineMode = findViewById(R.id.gameView_mineMode);
        flagMode = findViewById(R.id.gameView_flagMode);

        if(MinesweeperGame.getInstance().getGameSettings().isGameModeVisible()){
            gameMode.setVisibility(View.VISIBLE);
            mineMode.setVisibility(View.VISIBLE);
            flagMode.setVisibility(View.VISIBLE);
        }else{
            gameMode.setVisibility(View.INVISIBLE);
            mineMode.setVisibility(View.INVISIBLE);
            flagMode.setVisibility(View.INVISIBLE);
        }

        // Button zum Zurücksetzen des Spiels
        ImageButton resetButton = findViewById(R.id.gameView_resetGame);
        resetButton.setOnClickListener(e -> {
            MinesweeperGame.getInstance().resetGame();
        });

        //Minen-Zähler
        mineCounter = findViewById(R.id.gameView_mineCounter);
        mineCounter.setText(Integer.toString(MinesweeperGame.getInstance().getMineCounter().getMineCount()));
        mine = findViewById(R.id.gameView_iconMine);

        if(MinesweeperGame.getInstance().getGameSettings().isMineCounterVisible()){
            mineCounter.setVisibility(View.VISIBLE);
            mine.setVisibility(View.VISIBLE);
        }else{
            mineCounter.setVisibility(View.INVISIBLE);
            mine.setVisibility(View.INVISIBLE);
        }

        // Timer
        timer = findViewById(R.id.gameView_timer);
        timer.setText(("0"));
        clock = findViewById(R.id.gameView_iconTimer);

        if(MinesweeperGame.getInstance().getGameSettings().isTimerVisible()){
            timer.setVisibility(View.VISIBLE);
            clock.setVisibility(View.VISIBLE);
        }else{
            timer.setVisibility(View.INVISIBLE);
            clock.setVisibility(View.INVISIBLE);
        }

        // Callbacks
        MinesweeperGame.getInstance().setMinesweeperCallback(this);
        MinesweeperGame.getInstance().getTimer().setMinesweeperCallback(this);

        // Einstellungen
        ImageButton gameSetting = findViewById(R.id.gameView_gameSettings);
        gameSetting.setOnClickListener(event -> {
            startActivity(new Intent(this, SettingsActivity.class));
        });

        // Zurück zum Hauptmenü
        ImageButton backToMenue = findViewById(R.id.gameView_backToMenue);
        backToMenue.setOnClickListener(event -> {
            startActivity(new Intent(this, MainActivity.class));
            MinesweeperGame.getInstance().resetFields();
        });
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

    @Override
    protected void onResume() {
        super.onResume();
        if(MinesweeperGame.getInstance().isFirstClick()){
            MinesweeperGame.getInstance().getTimer().restartTimer();
        }
        updateView();
    }

    private void updateView(){
        if(MinesweeperGame.getInstance().getGameSettings().isGameModeVisible()){
            gameMode.setVisibility(View.VISIBLE);
            mineMode.setVisibility(View.VISIBLE);
            flagMode.setVisibility(View.VISIBLE);
        }else{
            gameMode.setVisibility(View.INVISIBLE);
            mineMode.setVisibility(View.INVISIBLE);
            flagMode.setVisibility(View.INVISIBLE);
        }

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

        if(MinesweeperGame.getInstance().getGameSettings().isGameModeVisible()){
            gameMode.setVisibility(View.VISIBLE);
            mineMode.setVisibility(View.VISIBLE);
            flagMode.setVisibility(View.VISIBLE);
        }else{
            gameMode.setVisibility(View.INVISIBLE);
            mineMode.setVisibility(View.INVISIBLE);
            flagMode.setVisibility(View.INVISIBLE);
        }

        MinesweeperGame.getInstance().changeTheme();
    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        MinesweeperGame.getInstance().newGame();
//    }

    //    @Override
//    protected void onRestart() {
//        super.onRestart();
//        MinesweeperGame.getInstance().getTimer().startTimer();
//    }
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