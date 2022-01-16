package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views;

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

import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.textfield.TextInputLayout;

import java.math.BigDecimal;
import java.math.RoundingMode;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.MinesweeperApplication;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.activities.GameActivity;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.CustomGame;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame;


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
    private CustomGame customGame;
    private HorizontalStringPicker horizontalStringPicker;


    public CustomGameDialog() {

    }

    public CustomGameDialog(HorizontalStringPicker horizontalStringPicker) {
        this.horizontalStringPicker = horizontalStringPicker;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
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
        minesPercentTextView.setText("0%");


        numberOfMinesInput.setEnabled(false);
        mineQuantityTextInputLayout.setHelperText(getString(R.string.mine_helper_text_1));


        heightTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

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
                    return;
                } else {
                    checkHeight = true;
                    heightTextInputLayout.setError(null);
                    heightTextInputLayout.setEndIconVisible(true);
                }

                activateNumberOfMinesInput();
            }
        });


        widthTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                activateNumberOfMinesInput();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (widthTextInput.getText().toString().isEmpty() || heightTextInput.getText().toString().isEmpty()) {
                    numberOfMinesInput.setEnabled(false);
                    numberOfMinesInput.setText("");
                    mineQuantityTextInputLayout.setHelperText(getString(R.string.mine_helper_text_1));
                }

                if (widthTextInput.getText().toString().isEmpty()) {
                    checkWidth = false;
                    widthTextInputLayout.setEndIconVisible(false);
                    return;
                }

                int widthValue = Integer.parseInt(widthTextInput.getText().toString());

                if (widthValue < 4 || widthValue > 16) {
                    widthTextInputLayout.setError(getString(R.string.width_error_text));
                    checkWidth = false;
                    return;
                } else {
                    checkWidth = true;
                    widthTextInputLayout.setError(null);
                    widthTextInputLayout.setEndIconVisible(true);
                }


                if (checkHeight && checkWidth) {
                    numberOfMinesInput.setEnabled(true);
                    activateNumberOfMinesInput();
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

                int width = Integer.parseInt(widthTextInput.getText().toString());
                int height = Integer.parseInt(heightTextInput.getText().toString());
                fields = width * height;

                int mines = Integer.parseInt(numberOfMinesInput.getText().toString());
                double minesPercent = (double) mines / fields * 100;

                Double rounded = new BigDecimal(minesPercent).setScale(2, RoundingMode.HALF_UP).doubleValue(); //TODO Nochmal überarbeiten vllt
                minesPercentTextView.setText(rounded + "%");
            }
        });

        MinesweeperApplication application = (MinesweeperApplication) requireActivity().getApplication();
        customGame = application.getCustomGame();

        widthTextInput.setText(customGame.getWidth());
        heightTextInput.setText(customGame.getHeight());
        numberOfMinesInput.setText(customGame.getMines());

        Button startCustomGameButton = view.findViewById(R.id.startCustomGame);

        startCustomGameButton.setOnClickListener(e -> {
            if (checkHeight && checkWidth && checkNumberOfMines) {

                customGame.setHeight(heightTextInput.getText().toString());
                customGame.setWidth(widthTextInput.getText().toString());
                customGame.setMines(numberOfMinesInput.getText().toString());

                application.updateCustomGame(customGame);

                //dadurch wird das Level auch in der Datenbank gespeichert, da der Listener greift
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

    private void activateNumberOfMinesInput() {
        if(checkHeight && checkWidth) {
            numberOfMinesInput.setEnabled(true);
            numberOfMinesInput.setText("");
            int width = Integer.parseInt(widthTextInput.getText().toString());
            int height = Integer.parseInt(heightTextInput.getText().toString());
            fields = width * height;
            maxMines = (int) (fields * 0.6);
            mineQuantityTextInputLayout.setHelperText(getString(R.string.mine_helper_text_2) + " " + maxMines); //mit jedem machen
        }
    }


}

