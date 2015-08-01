package com.lthien.hoclichsu.activities;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lthien.hoclichsu.pojo.Chapter;
import com.lthien.hoclichsu.pojo.Question;
import com.lthien.hoclichsu.sqlite.SQLiteAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameActivity extends ActionBarActivity implements View.OnClickListener {

    private List<Button> btnQuestionList = new ArrayList<>();
    private List<Chapter> chapters;
    private int chapterIdx;
    private int questionIdx;
    private Button btnAnswer;
    private LinearLayout gameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameLayout = (LinearLayout) findViewById(R.id.gameLayout);

        btnQuestionList.add((Button) findViewById(R.id.btn_1));
        btnQuestionList.add((Button) findViewById(R.id.btn_2));
        btnQuestionList.add((Button) findViewById(R.id.btn_3));
        btnQuestionList.add((Button) findViewById(R.id.btn_4));
        btnQuestionList.add((Button) findViewById(R.id.btn_5));
        btnQuestionList.add((Button) findViewById(R.id.btn_6));
        btnQuestionList.add((Button) findViewById(R.id.btn_7));
        btnQuestionList.add((Button) findViewById(R.id.btn_8));
        btnQuestionList.add((Button) findViewById(R.id.btn_9));

        btnAnswer = (Button) findViewById(R.id.btnAnswer);
        btnAnswer.setOnClickListener(this);

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK) {
            boolean answerResult = data.getBooleanExtra("answerResult", false);
            if (answerResult) {
                btnQuestionList.get(questionIdx).setSelected(true);
                btnQuestionList.get(questionIdx).setText("");
            } else {
                btnQuestionList.get(questionIdx).setEnabled(false);
                btnQuestionList.get(questionIdx).setText("");
            }
        }

        if (requestCode == 1 && resultCode == RESULT_OK) {
            final Chapter chapter = chapters.get(chapterIdx);
            chapters.remove(chapter);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Bạn đã trả lời đúng câu hỏi lớn. Bạn có muốn xem thông tin ?")
                    .setTitle("Thông báo")
                    .setPositiveButton("Xem", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getBaseContext(), ChapterDetailActivity.class);
                            intent.putExtra("chapter", chapter);
                            startActivityForResult(intent, 2);
                        }
                    })
                    .setNegativeButton("Chơi tiếp", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            initGame();
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        }

        if (requestCode == 2) {
            initGame();
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnAnswer) {
            Intent intent = new Intent(this, QuestionChapterActivity.class);
            intent.putExtra("chapter", chapters.get(chapterIdx));
            startActivityForResult(intent, 1);
            return;
        }

        if (v.isSelected()) {
            return;
        }

        questionIdx = btnQuestionList.indexOf(v);

        Question question = chapters.get(chapterIdx).getQuestions().get(questionIdx);

        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra("question", question);
        startActivityForResult(intent, 0);
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initGame() {
        initBtnQuestionList();

        if (chapters.size() <= 0) {
            Toast.makeText(this, "Hết câu hỏi", Toast.LENGTH_LONG).show();
            endGame();
            return;
        }
        chapterIdx = randomChapter(chapters);

        int resID = getResources().getIdentifier(chapters.get(chapterIdx).getChapterImage(), "drawable", getPackageName());

        gameLayout.setBackground(getResources().getDrawable(resID));
    }

    private void endGame() {
        finish();
    }

    private int randomChapter(List<Chapter> chapters) {
        Random rand = new Random();

        int randomNum = rand.nextInt(chapters.size());

        return randomNum;
    }

    private void initBtnQuestionList() {
        for (int i = 0; i < btnQuestionList.size(); i++) {
            btnQuestionList.get(i).setSelected(false);
            btnQuestionList.get(i).setEnabled(true);
            btnQuestionList.get(i).setText(String.valueOf(i + 1));
        }
    }
}
