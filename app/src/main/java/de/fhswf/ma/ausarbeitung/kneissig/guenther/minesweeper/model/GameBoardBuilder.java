package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model;

import java.util.Random;

public class GameBoardBuilder {

    // 0 leeres Feld
    // -1 Mine

   public static int[][] buildEmptyBoard(int width, int height){

        int [][] startBoard = new int[width][height];

        for(int i = 0; i<width; i++){
            for(int j = 0; j<height; j++){
                startBoard[i][j] = 0;
            }
        }

        return startBoard;
    }

    public static void generateBoardWithMines(int numberOfMines , int columnsX , int rowsY, int startX, int startY, int[][] board){

        Random random = new Random();

        int x = 0;
        int y = 0;
        boolean minePossible = false;

        while( numberOfMines > 0 ){

            while(!minePossible){
                x = random.nextInt(columnsX);
                y = random.nextInt(rowsY);

                if(x != startX && y != startY ){
                    minePossible = true;
                }
            }
            if( board[x][y] != -1 ){
                board[x][y] = -1;
                numberOfMines--;
            }
            minePossible = false;
        }
        calculateMineNeighbourNumbers(board, columnsX, rowsY);
    }

    private static void calculateMineNeighbourNumbers(int[][] board , int columnsX , int rowsY){
        for( int posX = 0 ; posX < columnsX ; posX++){
            for( int posY = 0 ; posY < rowsY ; posY++){
                if( board[posX][posY] != -1 ){
                    int count = 0;

                    if( isMineAt(board,posX-1,posY+1, columnsX, rowsY)) count++; // top-left
                    if( isMineAt(board, posX,posY+1, columnsX, rowsY)) count++; // top
                    if( isMineAt(board,posX+1 ,posY+1, columnsX, rowsY)) count++; // top-right
                    if( isMineAt(board,posX - 1 ,posY, columnsX, rowsY)) count++; // left
                    if( isMineAt(board,posX + 1 ,posY, columnsX, rowsY)) count++; // right
                    if( isMineAt(board,posX - 1 ,posY - 1, columnsX, rowsY)) count++; // bottom-left
                    if( isMineAt(board,posX,posY - 1,columnsX,rowsY)) count++; // bottom
                    if( isMineAt(board,posX + 1 ,posY - 1,columnsX,rowsY)) count++; // bottom-right

                    board[posX][posY] =  count;
                }
            }
        }
    }

    private static boolean isMineAt( final int [][] board, final int x , final int y , final int width , final int height){
        if( x >= 0 && y >= 0 && x < width && y < height ){
            return board[x][y] == -1;
        }
        return false;
    }

}
