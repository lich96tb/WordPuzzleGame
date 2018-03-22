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
public interface ClueDao {
    @Query("SELECT * FROM clue WHERE clue_game_id = (:gameId)")
    List<Clue> getClueByGameId(long gameId);

    @Query("SELECT * FROM clue WHERE clue_game_id = (:gameId) and clue_column =:colum")
    List<Clue> getListColum(long gameId, int colum);

    @Query("SELECT * FROM clue WHERE clue_game_id = (:gameId) and clue_row =:row")
    List<Clue> getListRow(long gameId, int row);

    @Query("DELETE FROM clue WHERE clue_game_id = (:gameId)")
    void deleteClueByGameId(long gameId);

    @Update
    void updateClue(Clue clue);

    @Delete
    void deleteClue(Clue clue);

    @Insert
    void insertClue(Clue clue);
}
