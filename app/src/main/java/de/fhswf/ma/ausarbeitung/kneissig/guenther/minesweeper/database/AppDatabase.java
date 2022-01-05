package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.dao.SettingsDao;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.Settings;

@Database(entities = {Settings.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SettingsDao settingsDao();
}
