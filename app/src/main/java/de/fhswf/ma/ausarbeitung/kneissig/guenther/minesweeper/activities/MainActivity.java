package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

        //checken was es ist und dann die andere xml nehmen
        setContentView(R.layout.activity_main);

        application = (MinesweeperApplication) getApplication();
        Settings settings = application.getSettings();

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
            Log.e("APFEL", "DB Instanz erstellt");

        });

        ImageButton settingsButton = findViewById(R.id.settingsButton);

        settingsButton.setOnClickListener(e -> {
            startActivity(new Intent(this, SettingsActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            settingsButton.animate().rotation(settingsButton.getRotation() + 360).start();
        });

        ImageButton highScoreButton = findViewById(R.id.highScoreButton);
        highScoreButton.setOnClickListener(e -> {
            startActivity(new Intent(this, GameHistoryActivity.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            highScoreButton.animate().rotation(highScoreButton.getRotation() - 360).start();
        });

        Button createGameButton = findViewById(R.id.createGameButton);
        createGameButton.setOnClickListener(e -> openDialog());

        heightTextView = findViewById(R.id.heightTextView);
        widthTextView = findViewById(R.id.widthTextView);
        minesTextView = findViewById(R.id.minesTextView);


        customGame = application.getCustomGame();


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

                if (level.equals(getString(R.string.level_benutzedefiniert))){
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

        Button gameInstructionButton = findViewById(R.id.gameInstructionButton);
        gameInstructionButton.setOnClickListener(e -> startActivity(new Intent(this, GameInstructionActivity.class)));

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
    }

    public void openDialog() {
        CustomGameDialog customGameDialog = new CustomGameDialog(horizontalStringPicker);
        customGameDialog.show(getSupportFragmentManager(), "Create Custom Game Dialog");
    }

    /**
     * Um den Picker zu aktualisieren nach der Dialog erstellung
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

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }
}
