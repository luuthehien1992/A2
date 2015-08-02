package com.lthien.hoclichsu.pojo;

import java.util.List;

/**
 * Created by lthien.
 */
public class GameState {
    public static final int NONE = 0;
    public static final int WRONG = 1;
    public static final int RIGHT = 2;

    private List<Chapter> chapters;
    private int point;
    private List<Integer> btnState;
    private int chapterIdx;

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public List<Integer> getBtnState() {
        return btnState;
    }

    public void setBtnState(List<Integer> btnState) {
        this.btnState = btnState;
    }

    public int getChapterIdx() {
        return chapterIdx;
    }

    public void setChapterIdx(int chapterIdx) {
        this.chapterIdx = chapterIdx;
    }
}
