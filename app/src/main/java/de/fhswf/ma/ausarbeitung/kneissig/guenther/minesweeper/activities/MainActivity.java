package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.Level;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.Theme;

//TODO: Auf pixel XL passt es nicht !!!!!!!!!!!!!!!!!!!!! FIXXXEN

/**
 * windowBackground only affects the main window's background.
 * <p>
 * colorBackground affects not only the background of the main window but also of all components e.g. dialogs unless you override it in the component layout.
 * <p>
 * So both of them change the activity's background, but the colorBackground changes many more things as well.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ImageButton settingsButton = findViewById(R.id.settingsButton);

        settingsButton.setOnClickListener(e -> {

            startActivity(new Intent(this, SettingsActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//            startActivityForResult(new Intent(MainActivity.this, Pop.class)); //finishActivity() um es zu übergeben
        });


        ImageButton highScoreButton = findViewById(R.id.highScoreButton);
        highScoreButton.setOnClickListener(e -> {
            startActivity(new Intent(this, HighScoreActivity.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });


        List<String> items = new ArrayList<>();
        Arrays.asList(Level.values()).forEach(e -> items.add(e.label));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, items);
        Spinner spinner = findViewById(R.id.selectDifficultySpinner);
        spinner.setAdapter(adapter);


        EditText heightTextInput = findViewById(R.id.heightTextInput);
        EditText widthTextInput = findViewById(R.id.widthTextInput);
        EditText numberOfMinesInput = findViewById(R.id.numberOfMinesInput);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Man müsste das ganze nochmal iwie bestätigen können oder über den Playbutton der nochmal alles checkt

                String level = parent.getItemAtPosition(position).toString();

                MinesweeperGame.getInstance().getGameSettings().setLevel(level);

                if (level.equals(Level.CUSTOM.label)) {
                    heightTextInput.setText("");
                    widthTextInput.setText("");
                    numberOfMinesInput.setText("");
                    //Toast

                } else {
                    heightTextInput.setText(String.valueOf(MinesweeperGame.getInstance().getGameSettings().getColumnsX()));
                    widthTextInput.setText(String.valueOf(MinesweeperGame.getInstance().getGameSettings().getRowsY()));
                    numberOfMinesInput.setText(String.valueOf(MinesweeperGame.getInstance().getGameSettings().getNumberOfMines()));
                }

                String text = "Das Level \"" + level + "\" wurde ausgewählt!";
                Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button gameInstructionButton = findViewById(R.id.gameInstructionButton);
        gameInstructionButton.setOnClickListener(e -> {
            startActivity(new Intent(this, GameInstructionActivity.class));
        });


        Button playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(e -> {
            boolean isVibrate = true;
            if (isVibrate) {
                Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vib.vibrate(500);
            } else {


            }
            if (MinesweeperGame.getInstance().getGameSettings().getLevel().equals(Level.CUSTOM)) {
                MinesweeperGame.getInstance().getGameSettings().setCustomBoardValues(
                        Integer.parseInt(numberOfMinesInput.getText().toString()),
                        Integer.parseInt(widthTextInput.getText().toString()),
                        Integer.parseInt(heightTextInput.getText().toString())
                );

            }

            if (MinesweeperGame.getInstance().isFirstClick()) {
                MinesweeperGame.getInstance().newGame();
            }
            startActivity(new Intent(this, GameActivity.class));

        });


        //setCustomBoardValues für benutzerdefniert in gamesettings


    }

}
