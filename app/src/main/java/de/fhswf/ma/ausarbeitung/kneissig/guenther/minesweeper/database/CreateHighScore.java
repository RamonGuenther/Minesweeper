package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database;

import android.content.Context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.HighScore;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.GameResult;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.Level;

public class CreateHighScore {

    CreateHighScore(){

    }

    //Eigentlich böse weil prozedural
    public static void createHighScore(Context context, int playedTime, Level level, GameResult gameResult, String minesLeft, String fieldSize){
        MinesweeperDatabase db = MinesweeperDatabase.createDatabase(context);
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(new Locale("de"));
        String gamePlayedOn = LocalDateTime.now().format(formatter);
        HighScore highScore = new HighScore(gamePlayedOn,playedTime + " Sekunden",level, gameResult,  minesLeft,fieldSize);
        db.highscoreDao().insert(highScore);
        db.close();
    }

}
