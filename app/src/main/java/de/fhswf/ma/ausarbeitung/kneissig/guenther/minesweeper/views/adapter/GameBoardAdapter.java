package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views.adapter;


import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame;

/**
 * Die Klasse GridAdapter dient als Verbindung zwischen der View und den Daten
 * des Minesweeper-Spiels.
 *
 * @author Ivonne Kneißig
 */
public class GameBoardAdapter extends BaseAdapter {

    /**
     * Die Methode getCount gibt die Anzahl der Felder auf dem Spielfeld zurück
     *
     * @return Anzahl der einzelnen Felder des Spielfeldes
     */
    @Override
    public int getCount() {
        MinesweeperGame.getInstance();
        return MinesweeperGame.getInstance().getColumnsX() * MinesweeperGame.getInstance().getRowsY();
    }

    /**
     * Die Methode getView gibt das Feld an einer bestimmten Position auf der GridView zurück.
     *
     * @param position    Position auf der GridView
     * @param convertView View zum wiederverwenden
     * @param parent      ParentView der GridView
     * @return Feld an einer bestimmten Position auf der GridView
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