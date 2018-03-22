package com.example.iui.wordpuzzlegamemock2.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Admin on 28/02/2018.
 */
@Entity(tableName = "clue",
        foreignKeys = {@ForeignKey(entity = Game.class,
                parentColumns = "game_id",
                childColumns = "clue_game_id")})
public class Clue {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "clue_id")
    private long clueId;

    @ColumnInfo(name = "clue_game_id")
    private long gameId;

    @ColumnInfo(name = "clue_content")
    private String clueContent;

    @ColumnInfo(name = "clue_row")
    private int clueRow;

    @ColumnInfo(name = "clue_column")
    private int clueColumn;

    public Clue() {
    }

    public long getClueId() {
        return clueId;
    }

    public void setClueId(long clueId) {
        this.clueId = clueId;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public String getClueContent() {
        return clueContent;
    }

    public void setClueContent(String clueContent) {
        this.clueContent = clueContent;
    }

    public int getClueRow() {
        return clueRow;
    }

    public void setClueRow(int clueRow) {
        this.clueRow = clueRow;
    }

    public int getClueColumn() {
        return clueColumn;
    }

    public void setClueColumn(int clueColumn) {
        this.clueColumn = clueColumn;
    }
}
