package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.polyak.iconswitch.IconSwitch;

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
public class GameActivity extends AppCompatActivity implements MinesweeperCallback, GameVibrationsCallback
{
    IconSwitch gameMode;

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

        gameMode = findViewById(R.id.gameView_iconSwitch);
        gameMode.setCheckedChangeListener(current -> {
            switch (current){
                case LEFT:
                    MinesweeperGame.getInstance().setGameMode(GameMode.MINE_MODE);
                    break;
                case RIGHT:
                    MinesweeperGame.getInstance().setGameMode(GameMode.FLAG_MODE);
                    break;
            }
        });

        // Button zum Zurücksetzen des Spiels
        ImageButton resetButton = findViewById(R.id.gameView_resetGame);
        resetButton.setOnClickListener(e -> {
            MinesweeperGame.getInstance().resetGame();
            resetButton.animate().rotation(resetButton.getRotation() + 360).start();
            if(MinesweeperGame.getInstance().getGameSettings().isVibration()){
                onResetGameVibration();
            }
        });

        //Minen-Zähler
        mineCounter = findViewById(R.id.gameView_mineCounter);
        mineCounter.setText(String.valueOf(MinesweeperGame.getInstance().getMineCounter().getMineCount()));
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
        mineCounter.setText(String.valueOf(mineCount));
    }

    /**
     * Die Methode update Timer setzt das TextField für die Zeit auf die aktuell verstrichene Anzahl
     * von Sekunden.
     *
     * @param time              Aktuelle Zahl der verstrichenen Sekunden seit Spielbeginn
     */
    @Override
    public void updateTimer(int time) {
        timer.setText(String.valueOf(time));
    }

    /**
     * Die Methode updateView blendet Elemente auf der GameView anhand der vom Spieler gewählten
     * Einstellungen ein oder aus.
     */
    private void updateView() {
        if (MinesweeperGame.getInstance().getGameSettings().isGameModeVisible()) {
            gameMode.setVisibility(View.VISIBLE);
        } else {
            gameMode.setVisibility(View.INVISIBLE);
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
        } else {
            gameMode.setVisibility(View.GONE);
        }
        MinesweeperGame.getInstance().invalidateBoard();
    }

    /**
     * Die Methode onConfiguationChanged sorgt dafür, dass das aktuelle Spielfeld beim Drehen
     * des Bildschirms vorhanden bleibt und das Layout der Activity an die Orientation angepasst
     * wird.
     *
     * @param newConfig             Aktuelle Einstellung (hier Bildschirm-Orientierung)
     */
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        MinesweeperGame.getInstance().invalidateBoard();
    }

    /**
     * Die Methode bombExplosionVibration erzeugt ein Vibrationspattern für den Fall, dass
     * der Spieler eine Mine aufgedeckt hat. Der Spieler soll so den Eindruck erhalten,
     * dass durch das Aufdecken der Mine eine Kettenreaktion ausgelöst wurde und viele Minen
     * explodieren.
     */
    @Override
    public void bombExplosionVibration() {

        long[] vibrationPattern = new long[]{50, 300, 100, 100, 200, 300, 200, 500};
        int[] vibrationAmplitudes = new int[]{255, 100, 255, 100, 255, 100, 255, 100};

        // repeat: -1 führt das Pattern genau einmal aus
//        if (vibrator.hasAmplitudeControl()) {
            VibrationEffect effect = VibrationEffect.createWaveform(vibrationPattern, vibrationAmplitudes, -1);
            vibrator.vibrate(effect);
//        }
    }

    /**
     * Die Methode onLongClickVibration dient dazu, dem Spieler bei einem langen Click auf ein
     * Feld zum Platzieren einer Flagge ein haptisches Feedback zu geben. Dadurch soll besser
     * erkennbar sein, ob man lange genug geklickt hat, um die Flagge zu platzieren.
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onLongClickVibration() {
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
//            vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK));
//        }
//        else{
            vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
//        }
    }

    /**
     * Die Methode onResetGameVibration erzeugt ein haptisches Feedback, wenn der Spieler
     * über den Reset-Button ein neues Spiel startet.
     */
    @Override
    public void onResetGameVibration() {
        vibrator.vibrate(VibrationEffect.createOneShot(200, 50));
    }
}