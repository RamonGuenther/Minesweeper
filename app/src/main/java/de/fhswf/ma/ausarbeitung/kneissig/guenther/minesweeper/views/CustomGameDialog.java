package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.textfield.TextInputLayout;

import java.math.BigDecimal;
import java.math.RoundingMode;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.MinesweeperApplication;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.activities.GameActivity;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.CustomGame;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame;


/**
 * Die Klasse CustomGameDialog erzeugt einen Dialog der für das Erstellen
 * eines Benutzerdefinierten Spiels angezeigt wird.
 *
 * @author Ramon Günther
 */
public class CustomGameDialog extends AppCompatDialogFragment {

    private EditText numberOfMinesInput;
    private EditText widthTextInput;
    private EditText heightTextInput;

    private TextView minesPercentTextView;
    private TextInputLayout widthTextInputLayout;
    private TextInputLayout heightTextInputLayout;
    private TextInputLayout mineQuantityTextInputLayout;

    private CustomGame customGame;
    private LevelHorizontalStringPicker horizontalStringPicker;

    private boolean checkWidth;
    private boolean checkHeight;
    private boolean checkNumberOfMines;

    private int fields;
    private int maxMines;


    /**
     * Der Konstruktor wird beim ersten öffnen des Dialogs ausgelöst.
     *
     * @param horizontalStringPicker Level-Picker aus dem Hauptmenü
     */
    public CustomGameDialog(LevelHorizontalStringPicker horizontalStringPicker) {
        this.horizontalStringPicker = horizontalStringPicker;
    }


    /**
     * Der Default Konstruktor dient wird nur aufgerufen, wenn der Bildschirm beim geöffneten
     * Dialog gedreht wird.
     */
    public CustomGameDialog() {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_custom_game_dialog, null);

        widthTextInput = view.findViewById(R.id.widthTextInput);
        widthTextInput.setInputType(InputType.TYPE_CLASS_NUMBER);

        heightTextInput = view.findViewById(R.id.heightTextInput);
        heightTextInput.setInputType(InputType.TYPE_CLASS_NUMBER);

        numberOfMinesInput = view.findViewById(R.id.mineQuantityTextInput);
        numberOfMinesInput.setInputType(InputType.TYPE_CLASS_NUMBER);

        widthTextInputLayout = view.findViewById(R.id.widthTextInputLayout);
        widthTextInputLayout.setEndIconVisible(false);

        heightTextInputLayout = view.findViewById(R.id.heightTextInputLayout);
        heightTextInputLayout.setEndIconVisible(false);

        mineQuantityTextInputLayout = view.findViewById(R.id.mineQuantityTextInputLayout);
        mineQuantityTextInputLayout.setEndIconVisible(false);

        minesPercentTextView = view.findViewById(R.id.minesPercentTextView);
        minesPercentTextView.setText("0%");

        numberOfMinesInput.setEnabled(false);
        mineQuantityTextInputLayout.setHelperText(getString(R.string.mine_helper_text_1));

        //Listener für die Breite
        widthTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) { //TODO Wahrscheinlich kann man widt und height auslagern in Funktion oder so
                if (widthTextInput.getText().toString().isEmpty() || heightTextInput.getText().toString().isEmpty()) {
                    numberOfMinesInput.setEnabled(false);
                    numberOfMinesInput.setText("");
                    mineQuantityTextInputLayout.setHelperText(getString(R.string.mine_helper_text_1));
                }

                if (widthTextInput.getText().toString().isEmpty()) {
                    widthTextInputLayout.setEndIconVisible(false);
                    checkWidth = false;
                    return;
                }

                int widthValue = Integer.parseInt(widthTextInput.getText().toString());

                if (widthValue < 4 || widthValue > 16) {
                    widthTextInputLayout.setError(getString(R.string.width_error_text));
                    checkWidth = false;

                    numberOfMinesInput.setEnabled(false);
                    numberOfMinesInput.setText("");
                    mineQuantityTextInputLayout.setHelperText(getString(R.string.mine_helper_text_1));
                    return;
                } else {
                    checkWidth = true;
                    widthTextInputLayout.setError(null);
                    widthTextInputLayout.setEndIconVisible(true);
                }

                activateNumberOfMinesEditText();
            }
        });

        //Listener für die Höhe
        heightTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (widthTextInput.getText().toString().isEmpty() || heightTextInput.getText().toString().isEmpty()) {
                    numberOfMinesInput.setEnabled(false);
                    numberOfMinesInput.setText("");
                    mineQuantityTextInputLayout.setHelperText(getString(R.string.mine_helper_text_1));
                }

                if (heightTextInput.getText().toString().isEmpty()) {
                    heightTextInputLayout.setEndIconVisible(false);
                    checkHeight = false;
                    return;
                }

                int heightValue = Integer.parseInt(heightTextInput.getText().toString());

                if (heightValue < 4 || heightValue > 100) {
                    heightTextInputLayout.setError(getString(R.string.height_error_text));
                    checkHeight = false;

                    numberOfMinesInput.setEnabled(false);
                    numberOfMinesInput.setText("");
                    mineQuantityTextInputLayout.setHelperText(getString(R.string.mine_helper_text_1));
                    return;
                } else {
                    checkHeight = true;
                    heightTextInputLayout.setError(null);
                    heightTextInputLayout.setEndIconVisible(true);
                }

                activateNumberOfMinesEditText();
            }
        });

        //Listener für die Minenanzahl
        numberOfMinesInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable editable) {
                if (numberOfMinesInput.getText().toString().isEmpty()) {
                    minesPercentTextView.setText("0.0%");
                    mineQuantityTextInputLayout.setEndIconVisible(false);
                    checkNumberOfMines = false;
                    return;
                }

                int minesInputValue = Integer.parseInt(numberOfMinesInput.getText().toString());
                if (minesInputValue < 1 || minesInputValue > maxMines) {
                    mineQuantityTextInputLayout.setError(getString(R.string.mine_error_text_1_1) + " " + maxMines + getString(R.string.mine_error_text_1_2));
                    checkNumberOfMines = false;
                    return;
                }

                checkNumberOfMines = true;
                mineQuantityTextInputLayout.setEndIconVisible(true);
                mineQuantityTextInputLayout.setError(null);

                int currentMineCount = Integer.parseInt(numberOfMinesInput.getText().toString());

                Double minesPercent = new BigDecimal((double) currentMineCount / fields * 100)
                        .setScale(2, RoundingMode.HALF_UP)
                        .doubleValue();

                minesPercentTextView.setText(minesPercent + "%");
            }
        });

        //Hier werden die letzten gespeicherten Einstellungen eines Benutzerdefinierten Spiels gesetzt
        MinesweeperApplication application = (MinesweeperApplication) requireActivity().getApplication();
        customGame = application.getCustomGame();

        widthTextInput.setText(customGame.getWidth());
        heightTextInput.setText(customGame.getHeight());
        numberOfMinesInput.setText(customGame.getMines());


        //Spiel starten Button
        Button startCustomGameButton = view.findViewById(R.id.startCustomGame);
        startCustomGameButton.setOnClickListener(e -> {
            if (checkHeight && checkWidth && checkNumberOfMines) {

                customGame.setHeight(heightTextInput.getText().toString());
                customGame.setWidth(widthTextInput.getText().toString());
                customGame.setMines(numberOfMinesInput.getText().toString());

                //Speichern der Einstellungen des Benutzerdefinierten Spiels
                application.updateCustomGame(customGame);

                //Dadurch wird das Level auch in der Datenbank gespeichert, da der Listener greift
                horizontalStringPicker.setValue(getString(R.string.level_benutzedefiniert));

                MinesweeperGame.getInstance().getGameSettings().setCustomBoardValues(
                        Integer.parseInt(numberOfMinesInput.getText().toString()),
                        Integer.parseInt(widthTextInput.getText().toString()),
                        Integer.parseInt(heightTextInput.getText().toString()));

                if (MinesweeperGame.getInstance().isFirstClick()) {
                    MinesweeperGame.getInstance().newGame();
                }
                startActivity(new Intent(this.getContext(), GameActivity.class));
                dismiss();
            }
        });


        builder.setView(view)
                .setTitle(getString(R.string.spiel_erstellen))
                .setNegativeButton(getString(R.string.abbrechen), (dialogInterface, i) -> {
                });

        return builder.create();
    }

    /**
     * Die Methode aktiviert das Eingabefeld für die Minenanzahl, wenn in den Eingabefeldern
     * für die Höhe und Breite, die Werte im vorgegebenen Bereich liegen.
     */
    private void activateNumberOfMinesEditText() {
        if (checkHeight && checkWidth) {
            numberOfMinesInput.setEnabled(true);
            numberOfMinesInput.setText("");
            int width = Integer.parseInt(widthTextInput.getText().toString());
            int height = Integer.parseInt(heightTextInput.getText().toString());
            fields = width * height;
            maxMines = (int) (fields * 0.6);
            mineQuantityTextInputLayout.setHelperText(getString(R.string.mine_helper_text_2) + " " + maxMines);
        }
    }

}

