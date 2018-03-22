package com.example.iui.wordpuzzlegamemock2.playgame;

/**
 * Created by CNTT on 2/28/2018.
 */

public class Play {
   private int img;
    private String locate;
    private String content;

    public Play() {
    }

    public Play(int img, String locate, String content) {
        this.img = img;
        this.locate = locate;
        this.content = content;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getLocate() {
        return locate;
    }

    public void setLocate(String locate) {
        this.locate = locate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
