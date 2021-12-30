package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model;

import android.content.Context;
import android.widget.Toast;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.GameMode;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views.Field;

public class MinesweeperGame {

    private static MinesweeperGame instance;
    private Context context;
    private int[][] bombPlacementField = new int[COLUMNS_X][ROWS_Y];
    private final Field[][] minesweeperBoard = new Field[COLUMNS_X][ROWS_Y];

    public static final int numberOfMines = 10;
    public static final int COLUMNS_X = 16;
    public static final int ROWS_Y = 16;

    private int startX;
    private int startY;
    private boolean firstClick = false;

    private GameMode gameMode = GameMode.MINE_MODE;

    public static MinesweeperGame getInstance() {
        if( instance == null ){
            instance = new MinesweeperGame();
        }
        return instance;
    }

    //Erstellt ein zunächst leeres Spielfeld mit 0 gefüllt, damit 1 Klick keine Mine sein kann
    public void createEmptyBoard(Context context){
        this.context = context;
        bombPlacementField = GameBoardBuilder.buildEmptyBoard(COLUMNS_X, ROWS_Y);
        setGrid(context,bombPlacementField);
    }

    //Füllt das Spielfeld nach dem ersten Klick mit Minen
    public void createBoardWithMines(Context context){
        this.context = context;
        GameBoardBuilder.generateBoardWithMines(numberOfMines, COLUMNS_X, ROWS_Y, startX, startY, bombPlacementField);
        setGrid(context,bombPlacementField);
    }

    private void setGrid( final Context context, final int[][] grid ){
        for(int x = 0; x < COLUMNS_X; x++ ){
            for(int y = 0; y < ROWS_Y; y++ ){
                if( minesweeperBoard[x][y] == null ){
                    minesweeperBoard[x][y] = new Field( context , x,y);
                }
                minesweeperBoard[x][y].setFieldValue(grid[x][y]);
                minesweeperBoard[x][y].invalidate();
            }
        }
    }


    public Field getFieldAt(int position) {
        int x = position % COLUMNS_X;
        int y = position / COLUMNS_X; //??? warum nicht Rows?

        return minesweeperBoard[x][y];
    }

    public Field getCellAt( int x , int y ){
        return minesweeperBoard[x][y];
    }

    public void click( int x , int y ){
        if( x >= 0 && y >= 0 && x < COLUMNS_X && y < ROWS_Y && !getCellAt(x,y).isTouched()){
            getCellAt(x,y).setTouched();

            if( getCellAt(x,y).getFieldValue() == 0 ){
                for( int xt = -1 ; xt <= 1 ; xt++ ){
                    for( int yt = -1 ; yt <= 1 ; yt++){
                        if( xt != yt ){
                            click(x + xt , y + yt);
                        }
                    }
                }
            }

            if( getCellAt(x,y).isMine() ){
                onGameLost();
            }
        }

        checkEnd();
    }

    private boolean checkEnd(){
        int bombNotFound = numberOfMines;
        int notRevealed = COLUMNS_X * ROWS_Y;
        for (int x = 0; x < COLUMNS_X; x++ ){
            for(int y = 0; y < ROWS_Y; y++ ){
                if( getCellAt(x,y).isDiscovered() || getCellAt(x,y).isFlagged() ){
                    notRevealed--;
                }

                if( getCellAt(x,y).isFlagged() && getCellAt(x,y).isMine() ){
                    bombNotFound--;
                }
            }
        }

        if( bombNotFound == 0 && notRevealed == 0 ){
            Toast.makeText(context,"Game won", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void flag( int x , int y ){
        boolean isFlagged = getCellAt(x,y).isFlagged();
        getCellAt(x,y).setFlagged(!isFlagged);
        getCellAt(x,y).invalidate();
    }

    public void mark( int x , int y ){
        boolean isMarked = getCellAt(x,y).isMarked();
        getCellAt(x,y).setMarked(!isMarked);
        getCellAt(x,y).invalidate();
    }

    private void onGameLost(){
        // handle lost game
        Toast.makeText(context,"Game lost", Toast.LENGTH_SHORT).show();

        for (int x = 0; x < COLUMNS_X; x++ ) {
            for (int y = 0; y < ROWS_Y; y++) {
                getCellAt(x,y).setDiscovered();
            }
        }
    }

    public void resetGame(){
        createEmptyBoard(context);
        setFirstClick(false);
    }


    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public boolean isFirstClick() {
        return firstClick;
    }

    public void setFirstClick(boolean value) {
        this.firstClick = value;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }
}
