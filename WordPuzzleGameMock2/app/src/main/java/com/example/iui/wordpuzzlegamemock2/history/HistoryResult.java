package com.example.iui.wordpuzzlegamemock2.history;

/**
 * Created by Admin on 06/03/2018.
 */

public class HistoryResult {
    private long gameId;
    private String datePlayed;
    private long playedTime;
    private int correctRow;
    private int correctColumn;

    public HistoryResult() {
    }

    public HistoryResult(long gameId, String datePlayed, long playedTime, int correctRow, int correctColumn) {
        this.gameId = gameId;
        this.datePlayed = datePlayed;
        this.playedTime = playedTime;
        this.correctRow = correctRow;
        this.correctColumn = correctColumn;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public String getDatePlayed() {
        return datePlayed;
    }

    public void setDatePlayed(String datePlayed) {
        this.datePlayed = datePlayed;
    }

    public long getPlayedTime() {
        return playedTime;
    }

    public void setPlayedTime(long playedTime) {
        this.playedTime = playedTime;
    }

    public int getCorrectRow() {
        return correctRow;
    }

    public void setCorrectRow(int correctRow) {
        this.correctRow = correctRow;
    }

    public int getCorrectColumn() {
        return correctColumn;
    }

    public void setCorrectColumn(int correctColumn) {
        this.correctColumn = correctColumn;
    }
}
