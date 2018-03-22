package com.example.iui.wordpuzzlegamemock2.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Admin on 28/02/2018.
 */
@Entity(tableName = "result",
        foreignKeys = {@ForeignKey(entity = Game.class,
                        parentColumns = "game_id",
                        childColumns = "result_game_id"),
                       @ForeignKey(entity = Clue.class,
                        parentColumns = "clue_id",
                        childColumns = "result_clue_id")})
public class Result {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "result_id")
    private long resultId;

    @ColumnInfo(name = "result_game_id")
    private long gameId;

    @ColumnInfo(name = "result_clue_id")
    private long clueId;

    @ColumnInfo(name = "play_result")
    private boolean playResult;

    @ColumnInfo(name = "date_play")
    private String datePlay;

    @ColumnInfo(name = "played_time")
    private long playedTime;

    @ColumnInfo(name = "players_answer")
    private String playersAnswer;
@Ignore
    public Result(long resultId, long gameId, long clueId, boolean playResult, String datePlay, long playedTime) {
        this.resultId = resultId;
        this.gameId = gameId;
        this.clueId = clueId;
        this.playResult = playResult;
        this.datePlay = datePlay;
        this.playedTime = playedTime;
    }

    @Ignore
    public Result(long clueId, boolean playResult, String datePlay, long playedTime) {
        this.clueId = clueId;
        this.playResult = playResult;
        this.datePlay = datePlay;
        this.playedTime = playedTime;
    }

    @Ignore
    public Result(long gameId, long clueId, boolean playResult, String datePlay, long playedTime) {
        this.gameId = gameId;
        this.clueId = clueId;
        this.playResult = playResult;
        this.datePlay = datePlay;
        this.playedTime = playedTime;
    }

    public Result(long gameId, long clueId, boolean playResult, String datePlay, long playedTime, String playersAnswer) {
        this.gameId = gameId;
        this.clueId = clueId;
        this.playResult = playResult;
        this.datePlay = datePlay;
        this.playedTime = playedTime;
        this.playersAnswer = playersAnswer;
    }

    public long getResultId() {
        return resultId;
    }

    public void setResultId(long resultId) {
        this.resultId = resultId;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public long getClueId() {
        return clueId;
    }

    public void setClueId(long clueId) {
        this.clueId = clueId;
    }

    public boolean getPlayResult() {
        return playResult;
    }

    public void setPlayResult(boolean playResult) {
        this.playResult = playResult;
    }

    public String getDatePlay() {
        return datePlay;
    }

    public void setDatePlay(String datePlay) {
        this.datePlay = datePlay;
    }

    public long getPlayedTime() {
        return playedTime;
    }

    public void setPlayedTime(long playedTime) {
        this.playedTime = playedTime;
    }

    public String getPlayersAnswer() {
        return playersAnswer;
    }

    public void setPlayersAnswer(String playersAnswer) {
        this.playersAnswer = playersAnswer;
    }
}
