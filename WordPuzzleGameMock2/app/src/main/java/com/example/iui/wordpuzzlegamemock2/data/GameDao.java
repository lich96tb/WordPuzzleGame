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
public interface GameDao {
    @Query("SELECT * FROM game")
    List<Game> getAllGame();

    @Query("SELECT game_size FROM game GROUP BY game_size")
    List<String> getGameSize();

    @Query("SELECT * FROM game g WHERE NOT EXISTS(SELECT * FROM result r WHERE r.result_game_id = g.game_id)")
    List<Game> getNotPlayedGame();

    @Query("SELECT * FROM game WHERE game_size = (:gameSize)")
    List<Game> getGameBySize(String gameSize);

    @Query("SELECT * FROM game WHERE game_id = (:gameId)")
    Game getGameById(long gameId);

    //lich viet
    @Query("select * from game where game_id =:id")
    List<Game> getContentGame(long id);

    @Insert
    void insertGame(Game game);

    @Update
    void updateGame(Game game);

    @Delete
    void deleteGame(Game game);

    @Insert
    long insertObj(Game game);

    @Query("SELECT * FROM game WHERE game_title LIKE :nameGame")
    Game findByName(String nameGame);

    @Query("SELECT * FROM game WHERE game_id = :idGame")
    Game findById(long idGame);
}
