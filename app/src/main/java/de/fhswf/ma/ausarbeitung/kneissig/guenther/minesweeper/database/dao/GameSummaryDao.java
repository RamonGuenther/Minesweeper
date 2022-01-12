package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.GameSummary;

@Dao
public interface GameSummaryDao {
    @Query("SELECT * FROM GameSummary")
    List<GameSummary> getMatchHistory();

    @Query("SELECT * FROM GameSummary h WHERE h.game_result = (:gameResult) order by h.played_time asc")
    List<GameSummary> getHighscores(String gameResult);

    @Query("SELECT * FROM GameSummary h WHERE h.game_result = (:gameResult) and h.level = (:level) order by h.played_time asc")
    List<GameSummary> getHighScoresByGameMode(String gameResult, String level);

    @Insert()
    void insert(GameSummary highscore);

    @Update
    void update(GameSummary highscore);

    @Delete
    void deleteAll(List<GameSummary> gameSummaryList);
}
