package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.gameComponents;

import java.util.Random;

/**
 * Die Klasse GameBoardBuilder baut das Spielfeld auf. Es kann ein leeres
 * Spielfeld erstellt werden, bei dem alle Felder den Wert 0 haben, damit der
 * Spieler bei seinem ersten Zug keine Mine treffen kann. Es kann außerdem
 * ein gegebenes Spielfeld überarbeitet werden, sodass zufällig Minen darauf
 * verteilt werden.
 *
 * @author Ivonne Kneißig
 */

public class GameBoardBuilder {

    /**
     * Die Methode buildEmptyBoard erstellt ein neues Integer-Array
     * entsprechend der Spielfeldgröße und weißt allen Feldern zunächst
     * den Wert 0 zu, wobei 0 dafür steht, dass sich dort keine Mine
     * befindet.
     *
     * @param columnsX      Anzahl Spalten des Spielfeldrasters
     * @param rowsY         Anzahl der Zeilen des Spielfeldrasters
     * @return              Spielfeld ohne Minen
     */
   public static int[][] buildEmptyBoard(int columnsX, int rowsY){

        int [][] startBoard = new int[columnsX][rowsY];

        for(int i = 0; i<columnsX; i++){
            for(int j = 0; j<rowsY; j++){
                startBoard[i][j] = 0;
            }
        }
        return startBoard;
    }

    /**
     * Die Methode generateBoardWithMines verteilt auf einem gegebenen Spielfeld zufällig
     * eine gegebene Anzahl von Minen. Dabei wird das Feld, welches der Spieler in seinem
     * ersten Zug angeklickt hat, ignoriert. Zusätzlich wird für jedes Feld berechnet,
     * wie viele Minen an dieses Feld angrenzen.
     *
     * @param numberOfMines Anzahl der Minen auf dem Spielfeld
     * @param columnsX      Anzahl Spalten des Spielfeldrasters
     * @param rowsY         Anzahl der Zeilen des Spielfeldrasters
     * @param startX        X-Position auf dem Spielfeldraster, an dem der Spieler das
     *                      erste Feld aufgedeckt hat
     * @param startY        Y-Position auf dem Spielfeldraster, an dem der Spieler das
     *      *               erste Feld aufgedeckt hat
     * @param board         Spielfeld, auf dem die Minen verteilt werden
     */
    public static void generateBoardWithMines(int numberOfMines , int columnsX , int rowsY, int startX, int startY, int[][] board){

        Random random = new Random();

        int x,y;
        boolean minePossible = false;

        while( numberOfMines > 0 ){
            while(!minePossible){
                x = random.nextInt(columnsX);
                y = random.nextInt(rowsY);

                if(x != startX && y != startY ){
                    minePossible = true;

                    if( board[x][y] != -1 ){
                        board[x][y] = -1;
                        numberOfMines--;
                    }
                }
            }
            minePossible = false;
        }
        calculateNeighbourMines(board, columnsX, rowsY);
    }

    /**
     * Die Methode calculateNeighbourMines berechnet für jedes Feld eines gegebenen
     * Spielfeldes die Anzahl der benachbarten Minen.
     *
     * @param board         Spielfeld, bei dem die Anzahl der benachbarten Minen für
     *                      jedes Feld ohne Mine berechnet werden soll.
     * @param columnsX      Anzahl der Spalten des Spielfeldrasters
     * @param rowsY         Anzahl der Zeilen des Spielfeldrasters
     */
    private static void calculateNeighbourMines(int[][] board , int columnsX , int rowsY){
        for( int posX = 0 ; posX < columnsX ; posX++){
            for( int posY = 0 ; posY < rowsY ; posY++){
                if( board[posX][posY] != -1 ){
                    int count = 0;

                    if(isMineAt(board,posX-1,posY+1, columnsX, rowsY)) count++;                 //Prüft das Feld oben links
                    if(isMineAt(board, posX,posY+1, columnsX, rowsY)) count++;                       //Prüft das Feld oben
                    if(isMineAt(board,posX+1 ,posY+1, columnsX, rowsY)) count++;                //Prüft das Feld oben rechts
                    if(isMineAt(board,posX - 1 ,posY, columnsX, rowsY)) count++;                     //Prüft das Feld links
                    if(isMineAt(board,posX + 1 ,posY, columnsX, rowsY)) count++;                     //Prüft das Feld rechts
                    if(isMineAt(board,posX - 1 ,posY - 1, columnsX, rowsY)) count++;            //Prüft das Feld unten links
                    if(isMineAt(board,posX,posY - 1,columnsX,rowsY)) count++;                        //Prüft das Feld unten
                    if(isMineAt(board,posX + 1 ,posY - 1,columnsX,rowsY)) count++;              //Prüft dasd Feld unten rechts

                    board[posX][posY] = count;
                }
            }
        }
    }

    /**
     * Methode isMineAt prüft für eine gegebene Position, ob sich dort eine Mine befindet, sofern
     * diese auf dem Spielfeldraster liegt.
     *
     * @param board         Spielfeld, dessen Feld überprüft werden soll, ob sich dort eine Mine
     *                      befindet.
     * @param xPos          X-Position (Spalte) in der geprüft wird
     * @param yPos          Y-Position (Zeile) in der geprüft wird
     * @param columnsX      Anzahl der Spalten des Spielfeldrasters
     * @param rowsY         Anzahl der Zeilen des Spielfeldrasters
     * @return              Gibt zurück, ob sich an der gegebenen X-/Y-Position eine Mine befindet.
     */
    private static boolean isMineAt( final int [][] board, int xPos, int yPos, int columnsX , int rowsY){
        // Die if-Bedingung prüft, ob sich die gegebene Position überhaupt auf dem Spielfeld befindet,
        // denn bei Randfeldern werden Positionen übergeben, die außerhalb liegen.
        if( xPos >= 0 && yPos >= 0 && xPos < columnsX && yPos < rowsY ){
            return board[xPos][yPos] == -1;
        }
        return false;
    }
}
