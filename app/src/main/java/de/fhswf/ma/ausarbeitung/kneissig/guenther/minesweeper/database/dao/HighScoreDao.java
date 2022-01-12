package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.HighScore;

@Dao
public interface HighScoreDao {
    @Query("SELECT * FROM HighScore")
    List<HighScore> getMatchHistory();

    @Query("SELECT * FROM HighScore h WHERE h.game_result = (:gameResult) order by h.played_time asc")
    List<HighScore> getHighscores(String gameResult);

    @Query("SELECT * FROM HighScore h WHERE h.game_result = (:gameResult) and h.level = (:level) order by h.played_time asc")
    List<HighScore> getHighScoresByGameMode(String gameResult, String level);

    @Insert()
    void insert(HighScore highscore);

    @Update
    void update(HighScore highscore);

    @Delete
    void deleteAll(List<HighScore> highScoreList);
}
