package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.Settings;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.Level;


//TODO: Benutzerdefiniert reiter binhaltet immer das letzte erstellte spiel
//TODO beim start der APP muss der letzte Spieltyp wieder ausgewählt sein


//TODO: Auf pixel XL passt es nicht !!!!!!!!!!!!!!!!!!!!!

/**
 * windowBackground only affects the main window's background.
 * <p>
 * colorBackground affects not only the background of the main window but also of all components e.g. dialogs unless you override it in the component layout.
 * <p>
 * So both of them change the activity's background, but the colorBackground changes many more things as well.
 */
public class MainActivity extends AppCompatActivity {

    private HorizontalStringPIcker horizontalStringPicker;
    private TextView heightTextView;
    private TextView widthTextView;
    private TextView minesTextView;

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

        Button createGameButton = findViewById(R.id.createGameButton);
        createGameButton.setOnClickListener(e -> {
            openDialog();
        });


        heightTextView = findViewById(R.id.heightTextView);
        widthTextView = findViewById(R.id.widthTextView);
        minesTextView = findViewById(R.id.minesTextView);

        List<String> items = new ArrayList<>();
        Arrays.asList(Level.values()).forEach(e -> items.add(e.label));
//        items.remove(items.size() - 1);

        horizontalStringPicker = findViewById(R.id.horizontalStringPicker);
        horizontalStringPicker.setItems(items);
        horizontalStringPicker.setValue(Level.PROFESSIONAL.label);
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
                heightTextView.setText(String.valueOf(MinesweeperGame.getInstance().getGameSettings().getRowsY()));
                widthTextView.setText(String.valueOf(MinesweeperGame.getInstance().getGameSettings().getColumnsX()));
                minesTextView.setText(String.valueOf(MinesweeperGame.getInstance().getGameSettings().getNumberOfMines()));
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
            if (MinesweeperGame.getInstance().isFirstClick()) {
                MinesweeperGame.getInstance().newGame();
            }
            startActivity(new Intent(this, GameActivity.class));

        });

    }

    public void openDialog() {
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

//    @Override
//    public void applyTexts(String username, String password) {
//        textViewUsername.setText(username);
//        textViewPassword.setText(password);
//    }

}
