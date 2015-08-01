package com.lthien.hoclichsu.pojo;

import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lthien.hoclichsu.sqlite.DBAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lthien.
 */
public class Chapter implements Serializable {
    private int id;
    private String chapterImage;
    private String chapterQuestion;
    private String chapterAnswer;
    private String chapterDescription;
    private List<Question> questions;

    public Chapter() {
        questions = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChapterImage() {
        return chapterImage;
    }

    public void setChapterImage(String chapterImage) {
        this.chapterImage = chapterImage;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getChapterQuestion() {
        return chapterQuestion;
    }

    public void setChapterQuestion(String chapterQuestion) {
        this.chapterQuestion = chapterQuestion;
    }

    public String getChapterAnswer() {
        return chapterAnswer;
    }

    public void setChapterAnswer(String chapterAnswer) {
        this.chapterAnswer = chapterAnswer;
    }

    public String getChapterDescription() {
        return chapterDescription;
    }

    public void setChapterDescription(String chapterDescription) {
        this.chapterDescription = chapterDescription;
    }

    public static Chapter createFromCursor(Cursor cursor) {
        Chapter chapter = new Chapter();
        chapter.setId(cursor.getInt(cursor.getColumnIndex(DBAdapter.CHAPTER_ID)));
        chapter.setChapterImage(cursor.getString(cursor.getColumnIndex(DBAdapter.CHAPTER_IMAGE)));
        chapter.setChapterQuestion(cursor.getString(cursor.getColumnIndex(DBAdapter.CHAPTER_IMAGE_QUESTIONS)));
        chapter.setChapterAnswer(cursor.getString(cursor.getColumnIndex(DBAdapter.CHAPTER_IMAGE_ANSWER)));
        chapter.setChapterDescription(cursor.getString(cursor.getColumnIndex(DBAdapter.CHAPTER_IMAGE_DESCRIPTION)));

        String json = cursor.getString(cursor.getColumnIndex(DBAdapter.CHAPTER_QUESTIONS));
        Gson gson = new Gson();
        List<Question> questions = gson.fromJson(json, new TypeToken<List<Question>>() {
        }.getType());

        chapter.setQuestions(questions);

        return chapter;
    }
}
