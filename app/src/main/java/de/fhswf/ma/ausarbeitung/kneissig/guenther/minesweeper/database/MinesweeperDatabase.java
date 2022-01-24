package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.dao.CustomGameDao;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.dao.GameSummaryDao;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.dao.SettingsDao;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.CustomGame;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.GameSummary;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.Settings;

/**
 * Die abstrakte Klasse MineSweeperDatabase definiert die Datenbankkonfiguration und dient als
 * Hauptzugriffspunkt auf die persistenten Daten.
 *
 * @author Ramon Günther
 */
@Database(entities = {Settings.class, GameSummary.class, CustomGame.class}, version = 1, exportSchema = false)
public abstract class MinesweeperDatabase extends RoomDatabase {

    private static final String DB_NAME = "MinesweeperDatabase";

    public static MinesweeperDatabase createDatabase(Context context) {
        Log.i(DB_NAME, "Datenbank Instanz erstellt");

        return Room.databaseBuilder(
                context,
                MinesweeperDatabase.class,
                DB_NAME)
                .allowMainThreadQueries()
                .build();
    }

    public abstract SettingsDao settingsDao();

    public abstract GameSummaryDao gameSummaryDao();

    public abstract CustomGameDao customGameDao();

}

