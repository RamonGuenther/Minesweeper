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
    @Query("select * from GameSummary order by id desc")
    List<GameSummary> getAllGameSummariesDesc();

    @Query("select * from GameSummary where game_result = (:gameResult) and level = (:level) order by played_time asc")
    List<GameSummary> getGameSummaryByGameResultAndLevel(String gameResult, String level);

    @Insert()
    void insert(GameSummary highscore);

    @Delete
    void deleteAll(List<GameSummary> gameSummaryList);
}
