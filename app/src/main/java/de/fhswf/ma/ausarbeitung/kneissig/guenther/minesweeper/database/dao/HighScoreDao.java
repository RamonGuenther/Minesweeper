package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.HighScore;

@Dao
public interface HighScoreDao {
    @Query("SELECT * FROM HighScore")
    List<HighScore> getHighscores();

    @Insert()
    void insert(HighScore highscore);

    @Update
    void update(HighScore highscore);
}
