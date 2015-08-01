package com.lthien.hoclichsu.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lthien.
 */
public class Question implements Serializable {
    private String content;
    private String image;
    private List<String> answers;
    private int answersIdx;

    public Question() {
        answers = new ArrayList<>();
        answersIdx = -1;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public int getAnswersIdx() {
        return answersIdx;
    }

    public void setAnswersIdx(int answersIdx) {
        this.answersIdx = answersIdx;
    }
}
