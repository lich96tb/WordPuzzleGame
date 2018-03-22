package com.example.iui.wordpuzzlegamemock2.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Admin on 28/02/2018.
 */
@Entity(tableName = "game")
public class Game {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "game_id")
    private long gameId;

    @ColumnInfo(name = "game_title")
    private String gameTitle;

    @ColumnInfo(name = "game_size")
    private String gameSize;

    @ColumnInfo(name = "game_duration")
    private long gameDuration;

    @ColumnInfo(name = "game_answer")
    private String gameAnswer;

    public Game() {
    }

    public Game(String gameTitle, String gameSize, long gameDuration, String gameAnswer) {
        this.gameTitle = gameTitle;
        this.gameSize = gameSize;
        this.gameDuration = gameDuration;
        this.gameAnswer = gameAnswer;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public String getGameSize() {
        return gameSize;
    }

    public void setGameSize(String gameSize) {
        this.gameSize = gameSize;
    }

    public long getGameDuration() {
        return gameDuration;
    }

    public void setGameDuration(long gameDuration) {
        this.gameDuration = gameDuration;
    }

    public String getGameAnswer() {
        return gameAnswer;
    }

    public void setGameAnswer(String gameAnswer) {
        this.gameAnswer = gameAnswer;
    }
}
