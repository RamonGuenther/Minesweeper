package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.GameMode;

/**
 * Die Klasse Field stellt die View für die einzelnen Felder des Minesweeper-Spiels
 * dar. Sie bestimmt wie die einzelnen Felder je nach Spielfeldaufbau und Spieleraktion
 * dargestellt werden.
 *
 * @author Ivonne Kneißig
 */
@SuppressLint("ViewConstructor")
public class Field extends View implements View.OnClickListener , View.OnLongClickListener{

    private final Context context;

    private int fieldValue;
    private boolean isMine;
    private boolean isDiscovered;
    private boolean isTouched;
    private boolean isFlagged;
    private boolean isMarked;

    private int xPos;
    private int yPos;
    private int position;

    /**
     * Der Konstruktor von Field speichert die Position des Feldes auf dem
     * Spielfeld und weißt ihm sein Verhalten zu, je nachdem wie es angeklickt
     * wird.
     *
     * @param context               Aktueller Zustand der Applikation
     * @param xPos                  x-Position des Feldes auf dem Spielfeld
     * @param yPos                  y-Position des Feldes auf dem Spielfeld
     */
    public Field(Context context, int xPos, int yPos){
        super(context);
        this.context = context;

        setPosition(xPos,yPos);
        setOnClickListener(this);
        setOnLongClickListener(this);
    }


    /*----------------------------------------------------------------------------------------------
                                             LISTENER
    ----------------------------------------------------------------------------------------------*/

    /**
     * Die Methode onClick prüft zunächst, ob in dem Spiel bereits das erste Feld aufgedeckt wurde.
     * Wenn nicht, wird eins aufgedeckt und das Spielfeld mit Minen gefüllt.
     * Handelt es sich nicht um den ersten Klick, ist die Aktion abhängig vom aktuellen Spielmodus.
     * Im Minenmodus wird das angeklickte Feld aufgedeckt, im Flaggenmodus wird eine Flagge platziert.
     *
     * @param view                  Field-Objekt, das angeklickt wird.
     */
    @Override
    public void onClick(View view) {

        if(!MinesweeperGame.getInstance().isFirstClick()){
            if(MinesweeperGame.getInstance().getGameMode().equals(GameMode.FLAG_MODE)){             // damit keine Flagge gesetzt werden kann, solange das erste Feld nicht angeklickt wurde
                Toast.makeText(context,"Es mus erst ein Feld aufgedeckt werden",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            MinesweeperGame.getInstance().setFirstClick(true);
            MinesweeperGame.getInstance().getTimer().startTimer();
            MinesweeperGame.getInstance().createBoardWithMines(context, getXPos(), getYPos());
        }
        switch (MinesweeperGame.getInstance().getGameMode()){
            case MINE_MODE:
                if(isFlagged() || isMarked()){                                                      // damit ein Feld nicht aufdeckbar ist, solange dort eine Flagge oder ein Fragezeichen platziert ist
                    Toast.makeText(context,"Kann nicht aufgdeckt werden, wenn markiert",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    MinesweeperGame.getInstance().discoverField(getXPos(), getYPos());
                }
                break;
            case FLAG_MODE:
                MinesweeperGame.getInstance().placeFlag(getXPos() , getYPos());
                break;
        }
    }

    /**
     * Die Methode onLonClick prüft zunächst, ob bereits das erste Feld auf dem Spielfeld aufgedeckt
     * wurde. Wenn ja, kann der Spieler im Minenmodus eine Flagge setzen, oder im Flaggenmodus ein
     * Fragezeichen setzen. Wurde noch kein Feld aufgedeckt, wird nichts gesetzt.
     *
     * @param view                Field-Objekt, das angeklickt wird.
     * @return                    Gib zurück, ob Feld lange angeklickt wurde
     */
    @Override
    public boolean onLongClick(View view) {
        if(!MinesweeperGame.getInstance().isFirstClick()){
            Toast.makeText(context,"Es mus erst ein Feld aufgedeckt werden",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        switch (MinesweeperGame.getInstance().getGameMode()){
            case MINE_MODE: MinesweeperGame.getInstance().placeFlag(getXPos() , getYPos());
                break;
            case FLAG_MODE: MinesweeperGame.getInstance().placeQuestionMark(getXPos() , getYPos());
                break;

        }
        return true;
    }

    /*----------------------------------------------------------------------------------------------
                                             METHODEN
    ----------------------------------------------------------------------------------------------*/

    /**
     * Die Methode onMeasure bestimmt wie die Höhe und Breite der View gemessen wird.
     *
     * @param widthMeasureSpec          Breite der View
     * @param heightMeasureSpec         Höhe der View
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    /**
     * Die Methode onDraw bestimmt das Aussehen des Feldes je nach seinen
     * Attributwerten.
     *
     * @param canvas            Enthält die Draw-Aufrufe
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawButton(canvas);

        if(isFlagged()){
            drawFlag(canvas);
        }
        else if(isMarked()){
            drawQuestionMark(canvas);
        }
        else if(isDiscovered() && isMine() && !isTouched()){
            drawMine(canvas);
        }
        else {
            if(isTouched()){
                if(getFieldValue() == -1 ){
                    drawMineExploded(canvas);
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

    /**
     * Die Methode drawBombExploded zeichnet das Symbol für eine explodierte Mine auf dem
     * Spielfeld.
     *
     * @param canvas            Erhält die Draw-Aufruf für die explodierte Mine.
     */
    private void drawMineExploded(Canvas canvas ){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.bd_bombexpl);
        if (drawable != null) {
            drawable.setBounds(0,0,getWidth(),getHeight());
            drawable.draw(canvas);
        }
    }


    /**
     * Die Methode drawFlag zeichnet das Symbol für eine Flagge auf dem
     * Spielfeld.
     *
     * @param canvas            Erhält die Draw-Aufruf für die Flagge.
     */
    private void drawFlag( Canvas canvas ){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.bd_flag);
        if (drawable != null) {
            drawable.setBounds(0,0,getWidth(),getHeight());
            drawable.draw(canvas);
        }
    }

    /**
     * Die Methode drawButton zeichnet das Symbol für einen Button, also ein unaufgedecktes
     * Feld auf dem Spielfeld.
     *
     * @param canvas            Erhält die Draw-Aufruf für ein unaufgedecktes Feld.
     */
    private void drawButton(Canvas canvas){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.bd_button);
        if (drawable != null) {
            drawable.setBounds(0,0,getWidth(),getHeight());
            drawable.draw(canvas);
        }
    }

    /**
     * Die Methode drawMine zeichnet das Symbol für eine nicht explodierte Mine auf dem
     * Spielfeld.
     *
     * @param canvas            Erhält die Draw-Aufruf für eine Mine.
     */
    private void drawMine(Canvas canvas ){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.bd_bomb);
        if (drawable != null) {
            drawable.setBounds(0,0,getWidth(),getHeight());
            drawable.draw(canvas);
        }

    }

    /**
     * Die Methode drawNumber zeichnet das Symbol für die Anzahl der benachbarten Minen auf
     * dem Spielfeld. Die dargestellte Ziffer richtet sich nach der jeweiligen angrenzenden
     * Minen an das Feld.
     *
     * @param canvas            Erhält die Draw-Aufruf für eine Zahl, welche die Anzahl der
     *                          benachbarten Minen darstellt.
     */
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

    /**
     * Die Methode drawQuestionMark zeichnet das Symbol für ein Fragezeichen auf dem
     * Spielfeld.
     *
     * @param canvas            Erhält die Draw-Aufruf für ein Fragezeichen.
     */
    private void drawQuestionMark( Canvas canvas ){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.bd_question);
        if (drawable != null) {
            drawable.setBounds(0,0,getWidth(),getHeight());
            drawable.draw(canvas);
        }

    }

    /*----------------------------------------------------------------------------------------------
                                           GETTER & SETTER
    ----------------------------------------------------------------------------------------------*/

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

    public boolean isDiscovered() {
        return isDiscovered;
    }

    public void setDiscovered() {
        isDiscovered = true;
        invalidate();

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

    public int getXPos(){
        return xPos;
    }

    public int getYPos(){
        return yPos;
    }

    public void setPosition(int x, int y) {
        this.xPos = x;
        this.yPos = y;

        this.position = y * MinesweeperGame.getInstance().getColumnsX() + x;

        invalidate();
    }
}
