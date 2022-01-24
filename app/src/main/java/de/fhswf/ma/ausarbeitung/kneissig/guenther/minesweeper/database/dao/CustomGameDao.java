package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.CustomGame;

/**
 * Die Klasse CustomGameDao definiert die Datenbankoperatonen für die Klasse {@link CustomGame}.
 *
 * @author Ramon Günther
 */
@Dao
public interface CustomGameDao {

    @Query("SELECT * FROM CustomGame WHERE id = 1")
    CustomGame getCustomGame();

    @Insert()
    void insert(CustomGame settings);

    @Update
    void update(CustomGame settings);
}
