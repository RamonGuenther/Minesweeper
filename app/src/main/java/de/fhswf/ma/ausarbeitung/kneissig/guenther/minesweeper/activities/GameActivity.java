package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.callback.MinesweeperCallback;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.callback.GameVibrationsCallback;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.GameMode;

/**
 * Die Klasse GameActivity bildet die Aktivität für das Minesweeper-Spiel.
 *
 * @author Ivonne Kneißig
 */
public class GameActivity extends AppCompatActivity implements MinesweeperCallback, GameVibrationsCallback {

    SwitchMaterial gameMode;
    TextView flagMode;
    TextView mineMode;

    TextView mineCounter;
    TextView timer;

    ImageView mine;
    ImageView clock;

    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        View view = findViewById(R.id.gameLayout);
        view.setKeepScreenOn(true);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        //Switch zum Wechseln zwischen Minen-Modus und Flaggen-Modus
        gameMode = findViewById(R.id.gameView_gameMode);
        gameMode.setOnClickListener(e -> {
            if (gameMode.isChecked()) {
                MinesweeperGame.getInstance().setGameMode(GameMode.FLAG_MODE);
            } else {
                MinesweeperGame.getInstance().setGameMode(GameMode.MINE_MODE);
            }
        });
        mineMode = findViewById(R.id.gameView_mineMode);
        flagMode = findViewById(R.id.gameView_flagMode);

        // Button zum Zurücksetzen des Spiels
        ImageButton resetButton = findViewById(R.id.gameView_resetGame);
        resetButton.setOnClickListener(e -> MinesweeperGame.getInstance().resetGame());

        //Minen-Zähler
        mineCounter = findViewById(R.id.gameView_mineCounter);
        mineCounter.setText(Integer.toString(MinesweeperGame.getInstance().getMineCounter().getMineCount()));
        mine = findViewById(R.id.gameView_iconMine);

        // Timer
        timer = findViewById(R.id.gameView_timer);
        timer.setText(("0"));
        clock = findViewById(R.id.gameView_iconTimer);

        // Callbacks
        MinesweeperGame.getInstance().setMinesweeperCallback(this);
        MinesweeperGame.getInstance().getTimer().setMinesweeperCallback(this);
        MinesweeperGame.getInstance().setGameVibrationCallback(this);

        // Einstellungen
        ImageButton gameSetting = findViewById(R.id.gameView_gameSettings);
        gameSetting.setOnClickListener(event -> {
            startActivity(new Intent(this, SettingsActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        // Zurück zum Hauptmenü
        ImageButton backToMenue = findViewById(R.id.gameView_backToMenue);
        backToMenue.setOnClickListener(event -> {
//            startActivity(new Intent(this, MainActivity.class));
            MinesweeperGame.getInstance().resetFields();
            finish();
        });

        updateView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MinesweeperGame.getInstance().getTimer().stopTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MinesweeperGame.getInstance().isFirstClick()) {
            MinesweeperGame.getInstance().getTimer().restartTimer();
        }
        updateView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateView();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    /*----------------------------------------------------------------------------------------------
                                            METHODEN
    ----------------------------------------------------------------------------------------------*/

    /**
     * Die Methode updateMineCounter setzt das TextField für den Minenzähler auf den aktuellen
     * Wert verbleibender Minen.
     *
     * @param mineCount         Aktuelle Anzahl an verbleibenden Minen auf dem Feld
     */
    @Override
    public void updateMineCounter(int mineCount) {
        mineCounter.setText(Integer.toString(mineCount));
    }

    /**
     * Die Methode update Timer setzt das TextField für die Zeit auf die aktuell verstrichene Anzahl
     * von Sekunden.
     *
     * @param time              Aktuelle Zahl der verstrichenen Sekunden seit Spielbeginn
     */
    @Override
    public void updateTimer(int time) {
        timer.setText(Integer.toString(time));
    }

    /**
     * Die Methode updateView blendet Elemente auf der GameView anhand der vom Spieler gewählten
     * Einstellungen ein oder aus.
     */
    private void updateView() {
        if (MinesweeperGame.getInstance().getGameSettings().isGameModeVisible()) {
            gameMode.setVisibility(View.VISIBLE);
            mineMode.setVisibility(View.VISIBLE);
            flagMode.setVisibility(View.VISIBLE);
        } else {
            gameMode.setVisibility(View.INVISIBLE);
            mineMode.setVisibility(View.INVISIBLE);
            flagMode.setVisibility(View.INVISIBLE);
        }

        if (MinesweeperGame.getInstance().getGameSettings().isMineCounterVisible()) {
            mineCounter.setVisibility(View.VISIBLE);
            mine.setVisibility(View.VISIBLE);
        } else {
            mineCounter.setVisibility(View.INVISIBLE);
            mine.setVisibility(View.INVISIBLE);
        }

        if (MinesweeperGame.getInstance().getGameSettings().isTimerVisible()) {
            timer.setVisibility(View.VISIBLE);
            clock.setVisibility(View.VISIBLE);
        } else {
            timer.setVisibility(View.INVISIBLE);
            clock.setVisibility(View.INVISIBLE);
        }

        Log.d("Mode Visible", Boolean.toString(MinesweeperGame.getInstance().getGameSettings().isGameModeVisible()));
        if (MinesweeperGame.getInstance().getGameSettings().isGameModeVisible()) {
            gameMode.setVisibility(View.VISIBLE);
            mineMode.setVisibility(View.VISIBLE);
            flagMode.setVisibility(View.VISIBLE);
        } else {
            gameMode.setVisibility(View.GONE);
            mineMode.setVisibility(View.GONE);
            flagMode.setVisibility(View.GONE);
        }

        MinesweeperGame.getInstance().invalidateBoard();
    }

    /**
     * Die Methode onConfiguationChanged sorgt dafür, dass das aktuelle Spielfeld beim Drehen
     * des Bildschirms vorhanden bleibt.
     *
     * @param newConfig             Aktuelle Einstellung (hier Bildschirm-Orientierung)
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            MinesweeperGame.getInstance().invalidateBoard();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            MinesweeperGame.getInstance().invalidateBoard();
        }
    }

    @Override
    public void bombExlposionVibration() {

        long[] vibrationPattern = new long[]{0, 300, 100, 100, 200, 300, 200, 500};
        int[] vibrationAmplitudes = new int[]{255, 100, 255, 100, 255, 100, 255, 100};

        // repeat: -1 führt das Pattern genau einmal aus
        if (vibrator.hasAmplitudeControl()) {
            VibrationEffect effect = VibrationEffect.createWaveform(vibrationPattern, vibrationAmplitudes, -1);
            vibrator.vibrate(effect);
        }
    }

    @Override
    public void onLongClickVibration() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK));
        }
        else{
            vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
        }
    }
}