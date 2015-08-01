package com.lthien.hoclichsu.pojo;

import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lthien.hoclichsu.sqlite.DBAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lthien.
 */
public class Chapter {
    private int id;
    private String chapterImage;
    private String chapterquestion;
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

    public String getChapterquestion() {
        return chapterquestion;
    }

    public void setChapterquestion(String chapterquestion) {
        this.chapterquestion = chapterquestion;
    }

    public static Chapter createFromCursor(Cursor cursor) {
        Chapter chapter = new Chapter();
        chapter.setId(cursor.getInt(cursor.getColumnIndex(DBAdapter.CHAPTER_ID)));
        chapter.setChapterImage(cursor.getString(cursor.getColumnIndex(DBAdapter.CHAPTER_IMAGE)));
        chapter.setChapterquestion(cursor.getString(cursor.getColumnIndex(DBAdapter.CHAPTER_IMAGE_QUESTIONS)));

        String json = cursor.getString(cursor.getColumnIndex(DBAdapter.CHAPTER_QUESTIONS));
        Gson gson = new Gson();
        List<Question> questions = gson.fromJson(json, new TypeToken<List<Question>>() {
        }.getType());

        chapter.setQuestions(questions);

        return chapter;
    }
}
