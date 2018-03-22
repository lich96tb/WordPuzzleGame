package com.example.iui.wordpuzzlegamemock2.module1;

/**
 * Created by IUI on 3/1/2018.
 */

public class ModelItemRecyclerViewCrossWord {
    private int imgTable,mNum;
    private String mSuggestion;

    public ModelItemRecyclerViewCrossWord(int imgTable, int mNum) {
        this.imgTable = imgTable;
        this.mNum = mNum;
    }

    public ModelItemRecyclerViewCrossWord(int imgTable, int mNum, String mSuggestion) {
        this.imgTable = imgTable;
        this.mNum = mNum;
        this.mSuggestion = mSuggestion;
    }

    public int getImgTable() {
        return imgTable;
    }

    public void setImgTable(int imgTable) {
        this.imgTable = imgTable;
    }

    public int getmNum() {
        return mNum;
    }

    public void setmNum(int mNum) {
        this.mNum = mNum;
    }

    public String getmSuggestion() {
        return mSuggestion;
    }

    public void setmSuggestion(String mSuggestion) {
        this.mSuggestion = mSuggestion;
    }
}
