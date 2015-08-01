package com.lthien.hoclichsu.sqlite;

/**
 * Created by lthien.
 */
public class DBAdapter {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "data";

    public static final String CHAPTER_TABLE = "CHAPTER_TABLE";
    public static final String CHAPTER_ID = "CHAPTER_ID";
    public static final String CHAPTER_IMAGE = "CHAPTER_IMAGE";
    public static final String CHAPTER_IMAGE_QUESTIONS = "CHAPTER_IMAGE_QUESTIONS";
    public static final String CHAPTER_IMAGE_ANSWER = "CHAPTER_IMAGE_ANSWER";
    public static final String CHAPTER_QUESTIONS = "CHAPTER_QUESTIONS";
    public static final String CHAPTER_IMAGE_DESCRIPTION = "CHAPTER_IMAGE_DESCRIPTION";

    public static final String CHAPTER_TABLE_CREATE =
            "CREATE TABLE " + CHAPTER_TABLE + "(" +
                    CHAPTER_ID + " INTEGER," +
                    CHAPTER_IMAGE + " TEXT," +
                    CHAPTER_IMAGE_QUESTIONS + " TEXT," +
                    CHAPTER_IMAGE_ANSWER + " TEXT," +
                    CHAPTER_IMAGE_DESCRIPTION + " TEXT," +
                    CHAPTER_QUESTIONS + " TEXT" +
                    ")";
    public static final String[] ALL_TABLES =
            {
                    CHAPTER_TABLE_CREATE
            };
}
