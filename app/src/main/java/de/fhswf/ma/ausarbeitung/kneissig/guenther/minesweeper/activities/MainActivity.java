package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.MinesweeperDatabase;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.CustomGame;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.Settings;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.Level;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views.CustomGameDialog;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views.HorizontalStringPicker;


public class MainActivity extends AppCompatActivity {

    private HorizontalStringPicker horizontalStringPicker;
    private TextView heightTextView;
    private TextView widthTextView;
    private TextView minesTextView;
    private CustomGame customGame;
    private MinesweeperDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //checken was es ist und dann die andere xml nehmen
        setContentView(R.layout.activity_main);

//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            MinesweeperGame.getInstance().changeTheme();
//        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
//            MinesweeperGame.getInstance().changeTheme();
//        }
//

        ImageButton settingsButton = findViewById(R.id.settingsButton);

        settingsButton.setOnClickListener(e -> {
//            startActivity(new Intent(this, SettingsActivity.class));
//            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            startActivity(new Intent(this, SettingsActivity.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        });


        ImageButton highScoreButton = findViewById(R.id.highScoreButton);
        highScoreButton.setOnClickListener(e -> {
            startActivity(new Intent(this, GameHistoryActivity.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        Button createGameButton = findViewById(R.id.createGameButton);
        createGameButton.setOnClickListener(e -> {
            openDialog();
        });


        heightTextView = findViewById(R.id.heightTextView);
        widthTextView = findViewById(R.id.widthTextView);
        minesTextView = findViewById(R.id.minesTextView);

        db = MinesweeperDatabase.createDatabase(this);
        Settings settings = db.settingsDao().getSettings();
        customGame = db.customGameDao().getCustomGame();


        List<String> items = new ArrayList<>();
        Arrays.asList(Level.values()).forEach(e -> items.add(e.label));

        horizontalStringPicker = findViewById(R.id.horizontalStringPicker);
        horizontalStringPicker.setItems(items);
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
                db.settingsDao().update(settings);

                if (level.equals(Level.CUSTOM.label)) {
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
        gameInstructionButton.setOnClickListener(e -> {
            startActivity(new Intent(this, GameInstructionActivity.class));
        });


        Button playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(e -> {
            if (MinesweeperGame.getInstance().getGameSettings().isVibration()) {

                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                long[] vibrationPattern = new long[]{200, 50, 200 };
                int[] vibrationAmplitudes = new int[]{255, 0, 255 };

                if (vibrator.hasAmplitudeControl()) {
                    VibrationEffect effect = VibrationEffect.createWaveform(vibrationPattern, vibrationAmplitudes, -1);
                    vibrator.vibrate(effect);
                }
            }
            if(MinesweeperGame.getInstance().isFirstClick()){
                MinesweeperGame.getInstance().newGame();
            }
            startActivity(new Intent(this, GameActivity.class));

        });

    }

    public void openDialog() {
        CustomGameDialog customGameDialog = new CustomGameDialog(horizontalStringPicker);
        customGameDialog.show(getSupportFragmentManager(), "Create Custom Game Dialog");
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (horizontalStringPicker.getValue().equals(Level.CUSTOM.label)) {
            customGame = db.customGameDao().getCustomGame();
            heightTextView.setText(customGame.getHeight());
            widthTextView.setText(customGame.getWidth());
            minesTextView.setText(customGame.getMines());
        }
    }

}
