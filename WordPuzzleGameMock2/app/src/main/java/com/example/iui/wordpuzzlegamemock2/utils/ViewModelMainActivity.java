package com.example.iui.wordpuzzlegamemock2.utils;

import android.arch.lifecycle.ViewModel;

/**
 * Created by IUI on 2/28/2018.
 */

public class ViewModelMainActivity extends ViewModel {
    public long getIdGame() {
        return idGame;
    }

    public void setIdGame(long idGame) {
        this.idGame = idGame;
    }

    private long idGame;
    private int colum,row;

    public int getColum() {
        return colum;
    }

    public void setColum(int colum) {
        this.colum = colum;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
