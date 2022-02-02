package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views;

import static de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame.GAME_LOST;
import static de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame.GAME_WON;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.activities.MainActivity;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame;

/**
 * Die Klasse GameEndDialog erzeugt einen Dialog, der am Spielende auf dem Bildschirm erscheint.
 * Er zeigt eine Zusammenfassung des Spiels und fragt den Spieler, ob er ein weiteres Spiel
 * machen, oder zum Hauptmenü zurückkehren möchte.
 *
 * @author Ivonne Kneißig
 */
public class GameEndDialog extends AlertDialog {

    /**
     * Der Konstruktor von GameEndDialog erzeugt einen AlertDialog, der über die Methode show
     * einen speziell für das Minesweeper-Spiel angepassten Dialog zum Spielende anzeigt.
     *
     * @param context               Aktueller Zustand der Applikation
     */
    protected GameEndDialog(Context context) {
        super(context);
    }

    /**
     * Die Methode show setzt einen speziell für das Ende eine Minesweeper-Spiels angepassten
     * Dialog zusammen, welcher dem Spieler eine Spielzusammenfassung zeigt und die Option bietet
     * ein neues Spiel zu starten, oder zum Hauptmenü zurückzukehren.
     *
     * @param context               Aktueller Zustand der Applikation
     * @param message               Nachricht, ob der Spieler gewonnen oder verloren hat
     * @param lostOrWon             Zustandswert, für Spiel gewonnen oder verloren
     */
    public static void show(Context context, String message, int lostOrWon){
        AlertDialog gameEndDialog = new AlertDialog.Builder(context).create();
        LayoutInflater inflater = gameEndDialog.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.game_end_dialog, null);

        gameEndDialog.setView(alertLayout);
        gameEndDialog.setCancelable(false);

        // Text gewonnen oder verloren
        TextView gameMessage = alertLayout.findViewById(R.id.dialog_messageTop);
        gameMessage.setText(message);

        // Spielergebnisse
        setLevelText(context, alertLayout);

        TextView minesLeft = alertLayout.findViewById(R.id.dialog_minesLeft);
        int minesfound = 0;
        switch (lostOrWon){
            case GAME_WON:
                minesfound = MinesweeperGame.getInstance().getGameSettings().getNumberOfMines();
                break;
            case GAME_LOST:
                minesfound = MinesweeperGame.getInstance().getGameSettings().getNumberOfMines()
                        - MinesweeperGame.getInstance().getMineCounter().getMineCount();
                break;
        }
        String mines = minesfound + " / " + MinesweeperGame.getInstance().getGameSettings().getNumberOfMines();
        minesLeft.setText(mines);

        TextView fieldSize = alertLayout.findViewById(R.id.dialog_fieldSize);
        String field = MinesweeperGame.getInstance().getColumnsX() + " x " + MinesweeperGame.getInstance().getRowsY();
        fieldSize.setText(field);

        TextView playedTime = alertLayout.findViewById(R.id.dialog_playedTime);
        String time = MinesweeperGame.getInstance().getTimer().getSecondsPassed() + " " + context.getString(R.string.sekunden);
        playedTime.setText(time);

        // Button - Listener
        gameEndDialog.setButton(AlertDialog.BUTTON_POSITIVE, context.getString(R.string.neues_spiel_ja),
                (dialog, which) -> {
                    MinesweeperGame.getInstance().resetGame();
                    dialog.dismiss();
                });

        gameEndDialog.setButton(AlertDialog.BUTTON_NEGATIVE, context.getString(R.string.neues_spiel_nein),
                (dialog, which) -> {
                    context.startActivity(new Intent(context, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
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

    /**
     * Die Methode setLevelText ordnet das vom Spieler gespielte Level einer String-Resource
     * zur Anzeige des Levels auf dem Dialog zu.
     *
     * @param context                   Aktueller Zustand der Applikation
     * @param alertLayout               View, auf dem sich die zu setzende TextView befindet
     */
    private static void setLevelText(Context context, View alertLayout){
        TextView level = alertLayout.findViewById(R.id.dialog_level);
        switch (MinesweeperGame.getInstance().getGameSettings().getLevel()){
            case BEGINNER:
                level.setText(context.getString(R.string.level_anfänger));
                break;
            case ADVANCED:
                level.setText(context.getString(R.string.level_fortgeschritten));
                break;
            case PROFESSIONAL:
                level.setText(context.getString(R.string.level_profi));
                break;
            case CUSTOM:
                level.setText(context.getString(R.string.level_benutzedefiniert));
                break;
        }
    }
}
