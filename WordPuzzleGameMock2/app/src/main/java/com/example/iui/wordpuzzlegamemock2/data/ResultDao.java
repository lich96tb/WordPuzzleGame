package com.example.iui.wordpuzzlegamemock2.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Admin on 28/02/2018.
 */
@Dao
public interface ResultDao {

    @Query("DELETE FROM result WHERE result_game_id = (:gameId)")
    void deleteResultByGameId(long gameId);

    //Lấy những ngày đã chơi game
    @Query("SELECT date_play FROM result WHERE result_game_id = (:gameId) GROUP BY date_play")
    List<String> getDatePlay(long gameId);

    //Đếm tổng số lần chơi của game
    @Query("SELECT COUNT(date_play) FROM (SELECT date_play FROM result WHERE result_game_id = (:gameId) GROUP BY date_play)")
    int timePlayedCount(long gameId);

    @Query("SELECT * FROM result WHERE date_play = (:datePlayed)")
    List<Result> getResultByDatePlayed(String datePlayed);

    @Insert
    void insertResult(Result result);

    @Update
    void updateResult(Result result);

    @Delete
    void deleteResult(Result result);
}
