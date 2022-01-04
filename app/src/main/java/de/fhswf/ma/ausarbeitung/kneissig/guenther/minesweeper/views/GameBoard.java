package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import androidx.annotation.NonNull;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame;

/**
 * Die Klasse GameBoard erstellt die GridView zur Darstellung des Spielfeldes mit den
 * einzelnen Feldern.
 *
 * @author Ivonne Kneißig
 */
public class GameBoard extends GridView {

    private static final int INVALID_POINTER_ID = -1;
    private int mActivePointerId = INVALID_POINTER_ID;
    private ScaleGestureDetector mScaleDetector;

    private float mScaleFactor = 1.f;
    private float maxWidth = 0.0f;
    private float maxHeight = 0.0f;
    private float mLastTouchX;
    private float mLastTouchY;
    private float mPosX;
    private float mPosY;
    private float width;
    private float height;

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
        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());

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
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
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

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent ev) {
        super.onTouchEvent(ev);
        final int action = ev.getAction();
        mScaleDetector.onTouchEvent(ev);
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                final float x = ev.getX();
                final float y = ev.getY();

                mLastTouchX = x;
                mLastTouchY = y;

                mActivePointerId = ev.getPointerId(0);
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                final int pointerIndex = ev.findPointerIndex(mActivePointerId);
                final float x = ev.getX(pointerIndex);
                final float y = ev.getY(pointerIndex);
                final float dx = x - mLastTouchX;
                final float dy = y - mLastTouchY;

                mPosX += dx;
                mPosY += dy;

                if (mPosX > 0.0f)
                    mPosX = 0.0f;
                else if (mPosX < maxWidth)
                    mPosX = maxWidth;

                if (mPosY > 0.0f)
                    mPosY = 0.0f;
                else if (mPosY < maxHeight)
                    mPosY = maxHeight;

                mLastTouchX = x;
                mLastTouchY = y;

                invalidate();
                break;
            }

            case MotionEvent.ACTION_UP: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_POINTER_UP: {
                final int pointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int pointerId = ev.getPointerId(pointerIndex);
                if (pointerId == mActivePointerId) {
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mLastTouchX = ev.getX(newPointerIndex);
                    mLastTouchY = ev.getY(newPointerIndex);
                    mActivePointerId = ev.getPointerId(newPointerIndex);
                }
                break;
            }
        }

        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(mPosX, mPosY);
        canvas.scale(mScaleFactor, mScaleFactor);
        canvas.restore();
    }

    @Override
    protected void dispatchDraw(@NonNull Canvas canvas) {
        canvas.save();
        if (mScaleFactor == 1.0f) {
            mPosX = 0.0f;
            mPosY = 0.0f;
        }
        canvas.translate(mPosX, mPosY);
        canvas.scale(mScaleFactor, mScaleFactor);
        super.dispatchDraw(canvas);
        canvas.restore();
        invalidate();
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(1.0f, Math.min(mScaleFactor, 3.0f));
            maxWidth = width - (width * mScaleFactor);
            maxHeight = height - (height * mScaleFactor);
            invalidate();
            return true;
        }
    }
}