package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper;

import android.app.Application;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.MinesweeperDatabase;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.CustomGame;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.GameSummary;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.Settings;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.GameResult;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.Level;

/**
 * TODO HIGHSCORE INSERT LÖSCHEN
 */
public class MinesweeperApplication extends Application {

    private final static String TAG = "MinesweeperApplication";

    private Settings settings;
    private CustomGame customGame;

    private MinesweeperDatabase db;


    @Override
    public void onCreate() {
        super.onCreate();
        db = MinesweeperDatabase.createDatabase(this);

        settings = db.settingsDao().getSettings();

        //Damit immer ein Settingsobjekt in der Datenbank gespeichert ist
        if (settings == null) {
            settings = new Settings();
            db.settingsDao().insert(settings);
        }


        createGameSummaryItem((int) Math.floor(Math.random() * (999 - 1 + 1) + 1), Level.BEGINNER, GameResult.WON, "10/10", "8 x 8");
        createGameSummaryItem((int) Math.floor(Math.random() * (999 - 1 + 1) + 1), Level.ADVANCED, GameResult.WON, "20/20", "16 x 16");
        createGameSummaryItem((int) Math.floor(Math.random() * (999 - 1 + 1) + 1), Level.PROFESSIONAL, GameResult.WON, "99/99", "16 x 30");

        MinesweeperGame.getInstance().getGameSettings().setTheme(settings.getTheme());
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

        customGame = db.customGameDao().getCustomGame();

        if (customGame == null) {
            customGame = new CustomGame();
            db.customGameDao().insert(customGame);
        }

//        db.close();

    }

    public Settings getSettings() {
        return settings;
    }

    public CustomGame getCustomGame() {
        return customGame;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
        db.settingsDao().update(settings);
        Log.i(TAG, "Einstellungen wurden gespeichert");
    }

    public void setCustomGame(CustomGame customGame) {
        this.customGame = customGame;
        db.customGameDao().update(customGame);
        Log.i(TAG, "Benutzerdefiniertes Spiel wurde gespeichert");
    }


    public void createGameSummaryItem(int playedTime, Level level, GameResult gameResult, String minesLeft, String fieldSize) {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(new Locale("de"));
        String gamePlayedOn = LocalDateTime.now().format(formatter);
        GameSummary gameSummary = new GameSummary(gamePlayedOn, playedTime, level, gameResult, minesLeft, fieldSize);
        db.gameSummaryDao().insert(gameSummary);
        Log.i(TAG, "Spielzusammenfassung wurde gespeichert");

    }

    public List<GameSummary> getMatchHistory(){
        return db.gameSummaryDao().getAllGameSummariesDesc();
    }

    public List<GameSummary> getHighScoreListByLevel (String level){
        return db.gameSummaryDao().getGameSummaryByGameResultAndLevel(GameResult.WON.label, level);
    }

    public void deleteAllGameSummaries(){
        db.gameSummaryDao().deleteAll(db.gameSummaryDao().getAllGameSummariesDesc());
        Log.i(TAG, "Spielzusammenfassungen wurden gelöscht");
    }
}
