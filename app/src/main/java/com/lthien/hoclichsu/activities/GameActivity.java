package com.lthien.hoclichsu.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.lthien.hoclichsu.pojo.Chapter;
import com.lthien.hoclichsu.pojo.Question;
import com.lthien.hoclichsu.sqlite.SQLiteAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameActivity extends ActionBarActivity implements View.OnClickListener {

    private List<Button> btnQuestionList = new ArrayList<>();
    private List<Chapter> chapters;
    private Chapter chapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        btnQuestionList.add((Button) findViewById(R.id.btn_1));
        btnQuestionList.add((Button) findViewById(R.id.btn_2));
        btnQuestionList.add((Button) findViewById(R.id.btn_3));
        btnQuestionList.add((Button) findViewById(R.id.btn_4));
        btnQuestionList.add((Button) findViewById(R.id.btn_5));
        btnQuestionList.add((Button) findViewById(R.id.btn_6));
        btnQuestionList.add((Button) findViewById(R.id.btn_7));
        btnQuestionList.add((Button) findViewById(R.id.btn_8));
        btnQuestionList.add((Button) findViewById(R.id.btn_9));

        for (int i = 0; i < btnQuestionList.size(); i++) {
            btnQuestionList.get(i).setOnClickListener(this);
        }

        SQLiteAdapter sqLiteAdapter = SQLiteAdapter.createInstance(this);
        chapters = sqLiteAdapter.getAll();

        initGame();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int idx = btnQuestionList.indexOf(v);

        Question question = chapter.getQuestions().get(idx);

        Intent intent = new Intent(this, QuestionActivity.class);
        startActivityForResult(intent, 0);
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

    private void initGame() {
        initBtnQuestionList();

        chapter = randomChapter(chapters);
    }

    private Chapter randomChapter(List<Chapter> chapters) {
        Random rand = new Random();

        int randomNum = rand.nextInt(chapters.size());

        return chapters.get(randomNum);
    }

    private void initBtnQuestionList() {
        for (int i = 0; i < btnQuestionList.size(); i++) {
            btnQuestionList.get(i).setSelected(false);
            btnQuestionList.get(i).setEnabled(true);
        }
    }
}
