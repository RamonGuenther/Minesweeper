package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.activities;

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
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.textfield.TextInputLayout;

import java.math.BigDecimal;
import java.math.RoundingMode;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame;

/**
 * TODO: - Wahrscheinlich nochmal in ein Constraintlayout (oder relative) packen damit alles richtig liegt
 *       -oben die % immer aktualisieren wenn sich in mine was ändert
 *      - unter minen feld steht die maximale Menge (70-80%)
 */
public class ExampleDialog extends AppCompatDialogFragment {
    private EditText numberOfMinesInput;
    private EditText widthTextInput;
    private EditText heightTextInput;
    private TextInputLayout mineQuantityTextInputLayout;
    private int fields;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_create_game_dialog, null);

        widthTextInput = view.findViewById(R.id.widthTextInput);
        heightTextInput = view.findViewById(R.id.heightTextInput);
        numberOfMinesInput = view.findViewById(R.id.mineQuantityTextInput);

        widthTextInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        heightTextInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        numberOfMinesInput.setInputType(InputType.TYPE_CLASS_NUMBER);

        mineQuantityTextInputLayout = view.findViewById(R.id.mineQuantityTextInputLayout);


        widthTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        heightTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        numberOfMinesInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                int width = Integer.parseInt(widthTextInput.getText().toString());
                int height = Integer.parseInt(heightTextInput.getText().toString());
                fields = width * height;

                int mines = Integer.parseInt(numberOfMinesInput.getText().toString());
                double minesPercent = (double) mines/fields *100;

                Double rounded= new BigDecimal(minesPercent).setScale(2, RoundingMode.HALF_UP).doubleValue();
                mineQuantityTextInputLayout.setHelperText(rounded + "%"); //mit jedem machen
//                apfel();
            }
        });


        Button startCustomGameButton = view.findViewById(R.id.startCustomGame);

//        startCustomGameButton.setEnabled(false);

        startCustomGameButton.setOnClickListener(e->{

            MinesweeperGame.getInstance().getGameSettings().setCustomBoardValues(
                    Integer.parseInt(numberOfMinesInput.getText().toString()),
                    Integer.parseInt(widthTextInput.getText().toString()),
                    Integer.parseInt(heightTextInput.getText().toString()));

            if (MinesweeperGame.getInstance().isFirstClick()) {
                MinesweeperGame.getInstance().newGame();
            }
            startActivity(new Intent(this.getContext(), GameActivity.class));
        });



        builder.setView(view)
                .setTitle("Spiel erstellen")
                .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
//                .setPositiveButton("Speichern", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                      //Hier drin wird dann die EInstellung gespeichert und  im select in der Main ist benutzerdefiniert ausgewählt ?
//
//                    }
//                });



//        editTextUsername = view.findViewById(R.id.edit_username);
//        editTextPassword = view.findViewById(R.id.edit_password);

        return builder.create();
    }

    private void apfel(){
        if(widthTextInput.getText().toString().isEmpty() || heightTextInput.getText().toString().isEmpty()){
            mineQuantityTextInputLayout.setHelperText("Bitte zuerst Höhe und Breite angeben!"); //mit jedem machen
            return;
        }
       int width = Integer.parseInt(widthTextInput.getText().toString());
       int height = Integer.parseInt(heightTextInput.getText().toString());

       fields = width * height;

       mineQuantityTextInputLayout.setHelperText("Maximal: " + fields * 0.8); //mit jedem machen

    }


}

