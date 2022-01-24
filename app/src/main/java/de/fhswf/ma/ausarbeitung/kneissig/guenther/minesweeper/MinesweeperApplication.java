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
 * Die Klasse MinesweeperApplication überschreibt die Methode onCreate um beim Starten
 * der Applikation die Spieleinstellungen aus der Datenbank zu laden. Außerdem wird sie benutzt
 * um die Datenbank mithilfe zugehöriger Methoden global verfügbar zu machen.
 *
 * @author Ramon Günther
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

    }


    /**
     * Holt das aktuelle Einstellungsobjekt aus der Datenbank, und
     * gibt dieses zurück.
     *
     * @return  Die gespeicherten Einstellungen des Nutzers
     */
    public Settings getSettings() {
        return settings;
    }

    /**
     * Aktualisiert die Einstellungen in der Datenbank.
     *
     * @param settings Aktualisierte Einstellungen
     */
    public void updateSettings(Settings settings) {
        this.settings = settings;
        db.settingsDao().update(settings);
        Log.i(TAG, "Einstellungen wurden gespeichert");
    }


    /**
     * Holt die zuletzt definierten Einstellungen für ein benutzerdefiniertes Spiel
     * aus der Datenbank, und gibt diese zurück.
     *
     * @return  CustomGame Objekt
     */
    public CustomGame getCustomGame() {
        return customGame;
    }

    /**
     * Aktualisiert die Einstellungen des benutzerdefinierten Spiels
     * des Nutzers.
     *
     * @param customGame Aktualisierte Einstellungen
     */
    public void updateCustomGame(CustomGame customGame) {
        this.customGame = customGame;
        db.customGameDao().update(customGame);
        Log.i(TAG, "Benutzerdefiniertes Spiel wurde gespeichert");
    }


    /**
     * Speichert die Informationen eines abgeschlossenen Spiels (Gewonnen/Veloren) in der
     * Datenbank, um sie in den Spielstatistiken zur Verfügung zu stellen.
     *
     * @param playedTime      Zeit die vergangen ist bis zum Beenden des Spiels
     * @param level           Eingestellter Schwierigkeitsgrad
     * @param gameResult      Spielergebnis
     * @param minesLeft       Offen gebliebene Minen
     * @param fieldSize       Spielfeldgröße
     */
    public void createGameSummaryItem(int playedTime, Level level, GameResult gameResult, String minesLeft, String fieldSize) {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(new Locale("de"));
        String gamePlayedOn = LocalDateTime.now().format(formatter);
        GameSummary gameSummary = new GameSummary(gamePlayedOn, playedTime, level, gameResult, minesLeft, fieldSize);
        db.gameSummaryDao().insert(gameSummary);
        Log.i(TAG, "Spielzusammenfassung wurde gespeichert");
    }

    /**
     * Holt alle gespeicherten Spiele, unabhägig vom Schwierigkeitsgrad und Spielresultat, aus der
     * Datenbank und gibt diese als Liste zurück.
     *
     * @return Liste aller gespielten Spiele in absteigender Sortierung
     */
    public List<GameSummary> getMatchHistory() {
        return db.gameSummaryDao().getAllGameSummariesDesc();
    }

    /**
     * Holt alle gewonnenen Spiele mit dem gesuchten Schwierigkeitsgrad aus der Datenbank und
     * gibt diese als Liste zurück.
     *
     * @param level Schwierigkeitsgrad der Spiele
     *
     * @return Liste aller gewonnenen Spiele mit angegebenen Schwierigekeitsgrad
     */
    public List<GameSummary> getHighScoreListByLevel(String level) {
        return db.gameSummaryDao().getGameSummaryByGameResultAndLevel(GameResult.WON.label, level);
    }

    /**
     * Löscht alle gesammelten Spielstatistiken des Nutzers.
     */
    public void deleteAllGameSummaries() {
        db.gameSummaryDao().deleteAll(db.gameSummaryDao().getAllGameSummariesDesc());
        Log.i(TAG, "Spielzusammenfassungen wurden gelöscht");
    }
}
