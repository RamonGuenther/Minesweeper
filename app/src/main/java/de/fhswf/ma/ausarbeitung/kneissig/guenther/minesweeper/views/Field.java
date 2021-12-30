package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import androidx.core.content.ContextCompat;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.GameMode;

@SuppressLint("ViewConstructor")
public class Field extends View implements View.OnClickListener , View.OnLongClickListener{

    private final Context context;

    private int fieldValue;
    private boolean isMine;
    private boolean isDiscovered;
    private boolean isTouched;
    private boolean isFlagged;
    private boolean isMarked; // für Fragezeichen

    private int x;
    private int y;
    private int position;



    public Field(Context context, int x, int y){
        super(context);
        this.context = context;

        setPosition(x,y);
        setOnClickListener(this);
        setOnLongClickListener(this);
    }


    // Click Listener

    @Override
    public void onClick(View view) {
        if(!MinesweeperGame.getInstance().isFirstClick()){
            // zweite if, damit keine Flagge gesetzt werden kann, wenn first Click noch nicht gemacht
            if(MinesweeperGame.getInstance().getGameMode().equals(GameMode.FLAG_MODE)){
                return;
            }
            MinesweeperGame.getInstance().setFirstClick(true);
            MinesweeperGame.getInstance().setStartX(getXPos());
            MinesweeperGame.getInstance().setStartY(getYPos());

            Log.d("VALUE X", Integer.toString(getXPos()));
            Log.d("VALUE Y", Integer.toString(getYPos()));

            MinesweeperGame.getInstance().getTimer().startTimer();
            MinesweeperGame.getInstance().createBoardWithMines(context);
        }

        switch (MinesweeperGame.getInstance().getGameMode()){
            case MINE_MODE:
                // if, damit Feld nicht klickbar wenn Flagge gesetzt
                if(isFlagged()){
                    break;
                }
                else{
                    MinesweeperGame.getInstance().click(getXPos(), getYPos());
                }

            break;
            case FLAG_MODE: MinesweeperGame.getInstance().flag(getXPos() , getYPos());
            break;
        }

    }

    @Override
    public boolean onLongClick(View view) {
        if(!MinesweeperGame.getInstance().isFirstClick()){
            return true;
        }
        switch (MinesweeperGame.getInstance().getGameMode()){
            case MINE_MODE: MinesweeperGame.getInstance().flag(getXPos() , getYPos());;
                break;
            case FLAG_MODE: MinesweeperGame.getInstance().mark(getXPos() , getYPos());
                break;
            default: MinesweeperGame.getInstance().flag(getXPos(), getYPos());
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawButton(canvas);

        if( isFlagged() ){
            drawFlag(canvas);
        }
        else if(isMarked()){
            drawQuestionMark(canvas);
        }
        else if( isDiscovered() && isMine() && !isTouched() ){
            drawNormalBomb(canvas);
        }
        else {
            if( isTouched() ){
                if( getFieldValue() == -1 ){
                    drawBombExploded(canvas);
                }
                else {
                    drawNumber(canvas);
                }
            }
            else{
                drawButton(canvas);
            }
        }
    }

    private void drawBombExploded(Canvas canvas ){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.bd_bombexpl);
        if (drawable != null) {
            drawable.setBounds(0,0,getWidth(),getHeight());
            drawable.draw(canvas);
        }
    }

    private void drawFlag( Canvas canvas ){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.bd_flag);
        if (drawable != null) {
            drawable.setBounds(0,0,getWidth(),getHeight());
            drawable.draw(canvas);
        }

    }

    private void drawButton(Canvas canvas ){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.bd_button);
        if (drawable != null) {
            drawable.setBounds(0,0,getWidth(),getHeight());
            drawable.draw(canvas);
        }

    }

    private void drawNormalBomb(Canvas canvas ){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.bd_bomb);
        if (drawable != null) {
            drawable.setBounds(0,0,getWidth(),getHeight());
            drawable.draw(canvas);
        }

    }

    private void drawNumber( Canvas canvas ){
        Drawable drawable = null;

        switch (getFieldValue() ){
            case 0:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.bd_empty);
                break;
            case 1:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.bd_num1);
                break;
            case 2:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.bd_num2);
                break;
            case 3:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.bd_num3);
                break;
            case 4:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.bd_num4);
                break;
            case 5:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.bd_num5);
                break;
            case 6:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.bd_num6);
                break;
            case 7:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.bd_num7);
                break;
            case 8:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.bd_num8);
                break;
        }

        if (drawable != null) {
            drawable.setBounds(0,0,getWidth(),getHeight());
            drawable.draw(canvas);
        }

    }

    private void drawQuestionMark( Canvas canvas ){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.bd_question);
        if (drawable != null) {
            drawable.setBounds(0,0,getWidth(),getHeight());
            drawable.draw(canvas);
        }

    }

    // Getter und Setter

    public int getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(int fieldValue) {
        this.fieldValue = fieldValue;

        this.isMine = false;
        this.isDiscovered = false;
        this.isTouched = false;
        this.isFlagged = false;
        this.isMarked = false;

        if(fieldValue == -1){
            this.isMine = true;
        }
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine() {
        this.isMine = true;
    }

    public boolean isDiscovered() {
        return isDiscovered;
    }

    public void setDiscovered() {
        isDiscovered = true;
        invalidate();
        /*Generally, invalidate() means 'redraw on screen' and results
        to a call of the view's onDraw() method. So if something changes
        and it needs to be reflected on screen, you need to call invalidate(). */
    }

    public boolean isTouched() {
        return isTouched;
    }

    public void setTouched() {
        this.isTouched = true;
        this.isDiscovered = true;

        invalidate();
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }

    @Override //???
    public float getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override //???
    public float getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getPosition() {
        return position;
    }

    public int getXPos(){
        return x;
    }

    public int getYPos(){
        return y;
    }

    //?????? Berechnung wtf
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;

        this.position = y * MinesweeperGame.COLUMNS_X + x;

        invalidate();
    }


}
