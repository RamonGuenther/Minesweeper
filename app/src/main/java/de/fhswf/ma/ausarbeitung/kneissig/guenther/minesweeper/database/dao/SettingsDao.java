package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.Settings;

/**
 * Die Klasse SettingsDao definiert die Datenbankoperatonen für die Klasse {@link Settings}.
 *
 * @author Ramon Günther
 */
@Dao
public interface SettingsDao {
    
    @Query("SELECT * FROM settings WHERE id = 1")
    Settings getSettings();

    @Insert()
    void insert(Settings settings);

    @Update
    void update(Settings settings);
}
