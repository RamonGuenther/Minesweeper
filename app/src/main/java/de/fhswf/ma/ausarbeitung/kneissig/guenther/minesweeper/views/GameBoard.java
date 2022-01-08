package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views;

import android.content.Context;

import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame;

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
        setAdapter(new GridAdapter());
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


    /**
     * Die Klasse GridAdapter dient als Verbindung zwischen der View und den Daten
     * des Minesweeper-Spiels.
     *
     * @author Ivonne Kneißig
     */
    private static class GridAdapter extends BaseAdapter {

        /**
         * Die Methode getCount gibt die Anzahl der Felder auf dem Spielfeld zurück
         *
         * @return              Anzahl der einzelnen Felder des Spielfeldes
         */
        @Override
        public int getCount() {
            MinesweeperGame.getInstance();
            return MinesweeperGame.getInstance().getColumnsX() * MinesweeperGame.getInstance().getRowsY();
        }

        /**
         * Die Methode getView gibt das Feld an einer bestimmten Position auf der GridView zurück.
         *
         * @param position       Position auf der GridView
         * @param convertView    View zum wiederverwenden
         * @param parent         ParentView der GridView
         *
         * @return               Feld an einer bestimmten Position auf der GridView
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           return MinesweeperGame.getInstance().getFieldAt(position);
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }
    }
}