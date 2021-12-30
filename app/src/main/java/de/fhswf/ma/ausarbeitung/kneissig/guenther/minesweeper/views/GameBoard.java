package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame;

public class GameBoard extends GridView {

    public GameBoard(Context context , AttributeSet attrs){
        super(context,attrs);

        MinesweeperGame.getInstance().createEmptyBoard(context);

        setNumColumns(MinesweeperGame.COLUMNS_X);
        setAdapter(new GridAdapter());
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private static class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            MinesweeperGame.getInstance();
            return MinesweeperGame.COLUMNS_X * MinesweeperGame.ROWS_Y;
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
        public View getView(int position, View convertView, ViewGroup parent) {
           return MinesweeperGame.getInstance().getFieldAt(position);
        }
    }
}