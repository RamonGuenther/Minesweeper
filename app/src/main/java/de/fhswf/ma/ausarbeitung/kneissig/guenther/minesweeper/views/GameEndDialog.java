package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.MinesweeperCallback;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.activities.MainActivity;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame;

public class GameEndDialog extends AlertDialog {

    protected GameEndDialog(Context context) {
        super(context);
    }

    public static void show(Context context, String message){
        AlertDialog gameEndDialog = new AlertDialog.Builder(context).create();
        LayoutInflater inflater = gameEndDialog.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_layout, null);

        gameEndDialog.setView(alertLayout);
        gameEndDialog.setCancelable(false);

        // Text gewonnen oder verloren
        TextView gameMessage = alertLayout.findViewById(R.id.dialog_messageTop);
        gameMessage.setText(message);

        // Spielergebnisse
        TextView level = alertLayout.findViewById(R.id.dialog_level);
        level.setText(MinesweeperGame.getInstance().getGameSettings().getLevel().label);

        TextView minesLeft = alertLayout.findViewById(R.id.dialog_minesLeft);
        int minesfound = MinesweeperGame.getInstance().getGameSettings().getNumberOfMines() - MinesweeperGame.getInstance().getMineCounter().getMineCount();
        String mines = minesfound + " / " + MinesweeperGame.getInstance().getGameSettings().getNumberOfMines();
        minesLeft.setText(mines);

        TextView fieldSize = alertLayout.findViewById(R.id.dialog_fieldSize);
        String field = MinesweeperGame.getInstance().getColumnsX() + " x " + MinesweeperGame.getInstance().getRowsY();
        fieldSize.setText(field);

        TextView playedTime = alertLayout.findViewById(R.id.dialog_playedTime);
        String time = MinesweeperGame.getInstance().getTimer().getSecondsPassed() + " Sekunden";
        playedTime.setText(time);

        // Button - Listener
        gameEndDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Nein",
                (dialog, which) -> {
                    context.startActivity(new Intent(context, MainActivity.class));
                    dialog.dismiss();
                });

        gameEndDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Ja",
                (dialog, which) -> {
                    MinesweeperGame.getInstance().resetGame();
                    dialog.dismiss();
                });

        gameEndDialog.show();

        // Button Layout
        Button btnPositive = gameEndDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button btnNegative = gameEndDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
        layoutParams.weight = 10;
        btnPositive.setLayoutParams(layoutParams);
        btnNegative.setLayoutParams(layoutParams);

        gameEndDialog.getWindow().setGravity(Gravity.TOP);
    }
}
