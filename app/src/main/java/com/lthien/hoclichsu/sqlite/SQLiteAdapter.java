package com.lthien.hoclichsu.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lthien.hoclichsu.activities.R;
import com.lthien.hoclichsu.pojo.Chapter;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lthien.
 */
public class SQLiteAdapter extends DBAdapter {
    public static final String TAG = "DBAdapter";

    private static SQLiteHelper sqLiteHelper = null;
    private static SQLiteAdapter sqliteAdapter = null;

    public static SQLiteAdapter createInstance(Context context) {
        if (sqliteAdapter == null)
            sqliteAdapter = new SQLiteAdapter(context);

        return sqliteAdapter;
    }

    private SQLiteAdapter(Context context) {
        sqLiteHelper = new SQLiteHelper(context);
    }

    private static synchronized SQLiteDatabase open() throws SQLException {
        return sqLiteHelper.getWritableDatabase();
    }

    public synchronized void add(String table, ContentValues contentValues) {
        SQLiteDatabase db = open();
        db.insert(table, null, contentValues);
        db.close();
    }

    public synchronized List<Chapter> getAll() {
        SQLiteDatabase db = open();

        Cursor cursor = db.query(CHAPTER_TABLE,
                new String[]
                        {
                                CHAPTER_ID,
                                CHAPTER_IMAGE,
                                CHAPTER_IMAGE_QUESTIONS,
                                CHAPTER_QUESTIONS,
                                CHAPTER_IMAGE_ANSWER,
                                CHAPTER_IMAGE_DESCRIPTION
                        },
                null,
                new String[]
                        {

                        },
                null,
                null,
                null
        );

        List<Chapter> chapters = new ArrayList<>();

        while (cursor.moveToNext()) {
            Chapter chapter = Chapter.createFromCursor(cursor);
            chapters.add(chapter);
        }

        db.close();

        return chapters;
    }


    public class SQLiteHelper extends SQLiteOpenHelper {
        private Context context;

        public SQLiteHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            Log.d(TAG, "onCreate");

//            for (String sql : ALL_TABLES) {
//                sqLiteDatabase.execSQL(sql);
//            }
            Resources res = context.getResources();
            InputStream in_s = res.openRawResource(R.raw.db);
            String query_s = "";
            try {
                query_s = IOUtils.toString(in_s, "UTF-8");
                IOUtils.closeQuietly(in_s);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }

            String[] queries = query_s.split(";");

            for (String query : queries) {
                sqLiteDatabase.execSQL(query);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
            Log.d(TAG, "onUpgrade");
        }
    }
}