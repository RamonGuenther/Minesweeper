package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views;

import android.content.Context;

import android.util.AttributeSet;
import android.widget.GridView;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views.adapter.GameBoardAdapter;

/**
 * Die Klasse GameBoard erstellt die GridView zur Darstellung des Spielfeldes mit den
 * einzelnen Feldern.
 *
 * @author Ivonne Kneißig
 */
public class GameBoard extends GridView {

    /**
     * Der Konstruktor von GameBoard initialisiert die GridView und erstellt
     * zunächst ein leeres Spielfeld ohne Minen mit unaufgedeckten Feldern.
     *
     * @param context               Aktueller Status der Applikation
     * @param attrs                 Attribute, die der GridView übergeben werden
     */
    public GameBoard(Context context , AttributeSet attrs){
        super(context,attrs);

        MinesweeperGame.getInstance().createEmptyBoard(context);

        setNumColumns(MinesweeperGame.getInstance().getColumnsX());
        setAdapter(new GameBoardAdapter());
    }

    /**
     * Die Methode onMeasure bestimmt wie die Höhe und Breite der View gemessen wird.
     *
     * @param widthMeasureSpec          Breite der View
     * @param heightMeasureSpec         Höhe der View
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}