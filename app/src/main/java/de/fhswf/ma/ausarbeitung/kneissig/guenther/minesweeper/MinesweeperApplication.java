package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Random;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.CreateHighScore;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.MinesweeperDatabase;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.CustomGame;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.HighScore;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.Settings;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.GameResult;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.Level;

/**
 * TODO HIGHSCORE INSERT LÖSCHEN
 */
public class MinesweeperApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MinesweeperDatabase db = MinesweeperDatabase.createDatabase(this);

        Settings settings = db.settingsDao().getSettings();

        //Damit immer ein Settingsobjekt in der Datenbank gespeichert ist
        if (settings == null) {
            settings = new Settings();
            db.settingsDao().insert(settings);
        }


        CreateHighScore.createHighScore(this,(int) Math.floor(Math.random()*(999-1+1)+1), Level.BEGINNER, GameResult.WON, "10/10", "8 x 8" );
        CreateHighScore.createHighScore(this, (int) Math.floor(Math.random()*(999-1+1)+1), Level.ADVANCED, GameResult.WON, "20/20", "16 x 16" );
        CreateHighScore.createHighScore(this, (int) Math.floor(Math.random()*(999-1+1)+1), Level.PROFESSIONAL, GameResult.WON, "99/99", "16 x 30");

        MinesweeperGame.getInstance().getGameSettings().setVibration(settings.isVibration());
        MinesweeperGame.getInstance().getGameSettings().setTimerVisible(settings.isShowTimer());
        MinesweeperGame.getInstance().getGameSettings().setMineCounterVisible(settings.isShowMineCounter());
        MinesweeperGame.getInstance().getGameSettings().setGameModeVisible(settings.isShowModeSwitch());
        MinesweeperGame.getInstance().getGameSettings().setFlagsPossible(settings.isUseFlags());
        MinesweeperGame.getInstance().getGameSettings().setHints(settings.isShowHints());

        if (settings.isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            settings.setDarkMode(true);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            settings.setDarkMode(false);
        }

        CustomGame customGame = db.customGameDao().getCustomGame();

        if(customGame == null){
            customGame = new CustomGame();
            db.customGameDao().insert(customGame);
        }

        db.close();

        Log.i("Minesweeper Applikation", "Einstellungen wurden übernommen");
    }
}
