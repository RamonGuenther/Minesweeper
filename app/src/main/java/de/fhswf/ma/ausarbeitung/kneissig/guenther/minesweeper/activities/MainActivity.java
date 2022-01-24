package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.MinesweeperApplication;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.CustomGame;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.Settings;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.Level;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views.CustomGameDialog;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views.LevelHorizontalStringPicker;

/**
 * Die Klasse GameActivity bildet die Aktivität für das Hauptmenü der Applikation.
 *
 * @author Ramon Günther
 */
public class MainActivity extends AppCompatActivity {

    private LevelHorizontalStringPicker horizontalStringPicker;
    private TextView heightTextView;
    private TextView widthTextView;
    private TextView minesTextView;
    private CustomGame customGame;

    private MinesweeperApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        application = (MinesweeperApplication) getApplication();
        Settings settings = application.getSettings();

        //Dunkler / Heller Modus
        ImageButton lightDarkMode = findViewById(R.id.LightDarkModeButton);
        if (!settings.isDarkMode()) {
            lightDarkMode.setImageResource(R.drawable.ic_sun);
        } else {
            lightDarkMode.setImageResource(R.drawable.ic_moon);
        }

        lightDarkMode.setOnClickListener(event -> {
            if (!settings.isDarkMode()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                settings.setDarkMode(true);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                settings.setDarkMode(false);
            }
            application.updateSettings(settings);
        });

        //Spielen Button
        Button playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(e -> {
            if (MinesweeperGame.getInstance().getGameSettings().isVibration()) {

                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                long[] vibrationPattern = new long[]{200, 50, 200};
                int[] vibrationAmplitudes = new int[]{255, 0, 255};

                if (vibrator.hasAmplitudeControl()) {
                    VibrationEffect effect = VibrationEffect.createWaveform(vibrationPattern, vibrationAmplitudes, -1);
                    vibrator.vibrate(effect);
                }
            }
            if (MinesweeperGame.getInstance().isFirstClick()) {
                MinesweeperGame.getInstance().newGame();
            }
            startActivity(new Intent(this, GameActivity.class));
        });


        //Spielinformations Layout Komponenten
        heightTextView = findViewById(R.id.heightTextView);
        widthTextView = findViewById(R.id.widthTextView);
        minesTextView = findViewById(R.id.minesTextView);

        customGame = application.getCustomGame();

        //Level-Picker
        horizontalStringPicker = findViewById(R.id.horizontalStringPicker);
        horizontalStringPicker.getTextView().addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String level = horizontalStringPicker.getValue();
                MinesweeperGame.getInstance().getGameSettings().setLevel(level);
                settings.setLastLevel(level);
                application.updateSettings(settings);

                if (level.equals(getString(R.string.level_benutzedefiniert))) {
                    MinesweeperGame.getInstance().getGameSettings().setCustomBoardValues(
                            Integer.parseInt(customGame.getMines()),
                            Integer.parseInt(customGame.getWidth()),
                            Integer.parseInt(customGame.getHeight())
                    );
                }
                heightTextView.setText(String.valueOf(MinesweeperGame.getInstance().getGameSettings().getRowsY()));
                widthTextView.setText(String.valueOf(MinesweeperGame.getInstance().getGameSettings().getColumnsX()));
                minesTextView.setText(String.valueOf(MinesweeperGame.getInstance().getGameSettings().getNumberOfMines()));
            }
        });

        horizontalStringPicker.setValue(settings.getLastLevel());

        //Eigenes Spiel erstellen
        Button createGameButton = findViewById(R.id.createGameButton);
        createGameButton.setOnClickListener(e -> openDialog());

        //Spielanleitung
        Button gameInstructionButton = findViewById(R.id.gameInstructionButton);
        gameInstructionButton.setOnClickListener(e -> startActivity(new Intent(this, GameInstructionActivity.class)));

        //Einstellungen
        ImageButton settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(e -> {
            startActivity(new Intent(this, SettingsActivity.class));
            overridePendingTransition(R.anim.slide_in_right, 0);
            settingsButton.animate().rotation(settingsButton.getRotation() + 360).start();
        });

        //Spielstatistiken
        ImageButton highScoreButton = findViewById(R.id.highScoreButton);
        highScoreButton.setOnClickListener(e -> {
            startActivity(new Intent(this, GameHistoryActivity.class));
            overridePendingTransition(R.anim.slide_in_left, 0);
            highScoreButton.animate().rotation(highScoreButton.getRotation() - 360).start();
        });

    }

    /**
     * Öffnet den Dialog zum erstellen eines Benutzerdefinierten Spiels.
     */
    private void openDialog() {
        CustomGameDialog customGameDialog = new CustomGameDialog(horizontalStringPicker);
        customGameDialog.show(getSupportFragmentManager(), "Create Custom Game Dialog");
    }

    /**
     * Um die Informationen unter dem Level-Picker zu aktualisieren, nachdem ein eigenes Spiel gestartet wurde.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (horizontalStringPicker.getValue().equals(Level.CUSTOM.label)) {
            heightTextView.setText(customGame.getHeight());
            widthTextView.setText(customGame.getWidth());
            minesTextView.setText(customGame.getMines());
        }
    }
}
