package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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

import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.textfield.TextInputLayout;

import java.math.BigDecimal;
import java.math.RoundingMode;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame;

/**
 * TODO: - Wahrscheinlich nochmal in ein Constraintlayout (oder relative) packen damit alles richtig liegt
 * -oben die % immer aktualisieren wenn sich in mine was ändert
 * - unter minen feld steht die maximale Menge (70-80%)
 */
public class CustomGameDialog extends AppCompatDialogFragment {
    private EditText numberOfMinesInput;
    private EditText widthTextInput;
    private EditText heightTextInput;
    private int fields;
    private int maxMines;
    private TextView minesPercentTextView;
    private TextInputLayout widthTextInputLayout;
    private TextInputLayout heightTextInputLayout;
    private TextInputLayout mineQuantityTextInputLayout;

    private boolean checkWidth;
    private boolean checkHeight;
    private boolean checkNumberOfMines;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_custom_game_dialog, null);

        widthTextInput = view.findViewById(R.id.widthTextInput);
        heightTextInput = view.findViewById(R.id.heightTextInput);
        numberOfMinesInput = view.findViewById(R.id.mineQuantityTextInput);

        widthTextInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        heightTextInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        numberOfMinesInput.setInputType(InputType.TYPE_CLASS_NUMBER);

        widthTextInputLayout = view.findViewById(R.id.widthTextInputLayout);
        widthTextInputLayout.setEndIconVisible(false);

        heightTextInputLayout = view.findViewById(R.id.heightTextInputLayout);
        heightTextInputLayout.setEndIconVisible(false);

        mineQuantityTextInputLayout = view.findViewById(R.id.mineQuantityTextInputLayout);
        mineQuantityTextInputLayout.setEndIconVisible(false);

        minesPercentTextView = view.findViewById(R.id.minesPercentTextView);
        minesPercentTextView.setText("0%"); //mit jedem machen


        numberOfMinesInput.setEnabled(false);
        mineQuantityTextInputLayout.setHelperText("Bitte zuerst Höhe und Breite angeben!");


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
                    mineQuantityTextInputLayout.setHelperText("Bitte zuerst Höhe und Breite angeben!"); //mit jedem machen
                }

                if (heightTextInput.getText().toString().isEmpty()) {
                    heightTextInputLayout.setEndIconVisible(false);
                    checkHeight = false;
                    return;
                }

                int heightValue = Integer.parseInt(heightTextInput.getText().toString());
                if (heightValue < 4 || heightValue > 100) {
                    heightTextInputLayout.setError("Die Höhe muss zwichen 4 - 100 liegen!)");
                    checkHeight = false;
                    return;
                } else {
                    checkHeight = true;
                    heightTextInputLayout.setError(null);
                    heightTextInputLayout.setEndIconVisible(true);
                }


                if (checkHeight && checkWidth) {
                    numberOfMinesInput.setEnabled(true);
                    apfel();
                }
            }
        });


        widthTextInput.addTextChangedListener(new TextWatcher() {
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
                    mineQuantityTextInputLayout.setHelperText("Bitte zuerst Höhe und Breite angeben!"); //mit jedem machen
                }

                if (widthTextInput.getText().toString().isEmpty()) {
                    checkWidth = false;
                    widthTextInputLayout.setEndIconVisible(false);
                    return;
                }

                int widthValue = Integer.parseInt(widthTextInput.getText().toString());

                if (widthValue < 4 || widthValue > 16) {
                    widthTextInputLayout.setError("Die Breite muss zwichen 4 - 16 liegen!");
                    checkWidth = false;
                    return;
                } else {
                    checkWidth = true;
                    widthTextInputLayout.setError(null);
                    widthTextInputLayout.setEndIconVisible(true);
                }


                if (checkHeight && checkWidth) {
                    numberOfMinesInput.setEnabled(true);
                    apfel();
                }
            }
        });


        numberOfMinesInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (numberOfMinesInput.getText().toString().isEmpty()) {
                    minesPercentTextView.setText("0.0%"); //mit jedem machen
                    mineQuantityTextInputLayout.setEndIconVisible(false);
                    checkNumberOfMines = false;
                    return;
                }

                int minesInputValue = Integer.parseInt(numberOfMinesInput.getText().toString());
                if (minesInputValue < 1 || minesInputValue > maxMines) {
                    mineQuantityTextInputLayout.setError("Die Anzahl der Minen muss zwischen 1-" + maxMines + " liegen!");
                    checkNumberOfMines = false;
                    return;
                }

                checkNumberOfMines = true;
                mineQuantityTextInputLayout.setEndIconVisible(true);
                mineQuantityTextInputLayout.setError(null);

                int width = Integer.parseInt(widthTextInput.getText().toString());
                int height = Integer.parseInt(heightTextInput.getText().toString());
                fields = width * height;

                int mines = Integer.parseInt(numberOfMinesInput.getText().toString());
                double minesPercent = (double) mines / fields * 100;

                Double rounded = new BigDecimal(minesPercent).setScale(2, RoundingMode.HALF_UP).doubleValue();
                minesPercentTextView.setText(rounded + "%"); //mit jedem machen
            }
        });


        Button startCustomGameButton = view.findViewById(R.id.startCustomGame);

//        startCustomGameButton.setEnabled(false);

        startCustomGameButton.setOnClickListener(e -> {

            if (checkHeight && checkWidth && checkNumberOfMines) {
                MinesweeperGame.getInstance().getGameSettings().setCustomBoardValues(
                        Integer.parseInt(numberOfMinesInput.getText().toString()),
                        Integer.parseInt(widthTextInput.getText().toString()),
                        Integer.parseInt(heightTextInput.getText().toString()));

                if (MinesweeperGame.getInstance().isFirstClick()) {
                    MinesweeperGame.getInstance().newGame();
                }
                startActivity(new Intent(this.getContext(), GameActivity.class));
            }
        });


        builder.setView(view)
                .setTitle("Spiel erstellen")
                .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

        return builder.create();
    }

    private void apfel() {
        int width = Integer.parseInt(widthTextInput.getText().toString());
        int height = Integer.parseInt(heightTextInput.getText().toString());
        fields = width * height;
        maxMines = (int) (fields * 0.6);
        mineQuantityTextInputLayout.setHelperText("Mindestens 1 | Maximal " + maxMines); //mit jedem machen
    }


}

