package com.lthien.hoclichsu.activities;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lthien.hoclichsu.pojo.Chapter;
import com.lthien.hoclichsu.pojo.GameState;
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
    private Button btnEndGame;
    private TextView txtPoint;
    private int point = 0;

    private ViewGroup gameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameLayout = (ViewGroup) findViewById(R.id.gameLayout);

        txtPoint = (TextView) findViewById(R.id.txtPoint);

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

        btnEndGame = (Button) findViewById(R.id.btnEndGame);
        btnEndGame.setOnClickListener(this);

        for (int i = 0; i < btnQuestionList.size(); i++) {
            btnQuestionList.get(i).setOnClickListener(this);
        }

        SharedPreferences sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);

        if (sharedPreferences.contains("state")) {
            String json = sharedPreferences.getString("state", "");
            GameState gameState = new Gson().fromJson(json, GameState.class);

            resumeGame(gameState);
        } else {
            SQLiteAdapter sqLiteAdapter = SQLiteAdapter.createInstance(this);
            chapters = sqLiteAdapter.getAll();

            chaptersInit();

            initGame();
        }
    }

    private List<Chapter> chaptersInit() {

        for (int i = 0; i < chapters.size(); i++) {
            List<Question> temp = questionsInit(chapters.get(i).getQuestions());
            chapters.get(i).setQuestions(temp);
        }

        return chapters;
    }

    private List<Question> questionsInit(List<Question> questions) {
        List<Question> list = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < 9; i++) {
            int idx = random.nextInt(questions.size());
            Log.d("AA", String.valueOf(idx));
            Question question = questions.get(idx);
            list.add(question);
            questions.remove(question);
        }

        return list;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
            updateGameState();
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
                            startNewGame();
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        }

        if (requestCode == 2) {
            startNewGame();
        }
    }

    private void showEndGameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Bạn chịu thua à?")
                .setTitle("Thông báo")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        endGame();
                    }
                })
                .setNegativeButton("Không", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void startNewGame() {
        calculatePoint();
        initGame();
        updateGameState();
    }

    private void calculatePoint() {
        int count = 0;
        for (int i = 0; i < btnQuestionList.size(); i++) {
            Button btn = btnQuestionList.get(i);
            if (btn.isEnabled() && !btn.isSelected()) {
                count++;
            }
        }
        point += count + 1;

        txtPoint.setText(String.valueOf(point));
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnEndGame) {
            showEndGameDialog();
            return;
        }

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
        SharedPreferences sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("state");
        editor.commit();

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

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    private void updateGameState() {
        GameState gameState = getGameState();

        SharedPreferences sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("state", new Gson().toJson(gameState));
        editor.commit();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void resumeGame(GameState gameState) {
        point = gameState.getPoint();
        chapterIdx = gameState.getChapterIdx();
        chapters = gameState.getChapters();

        for (int i = 0; i < btnQuestionList.size(); i++) {
            int state = gameState.getBtnState().get(i);

            if (state == GameState.WRONG) {
                btnQuestionList.get(i).setEnabled(false);
                btnQuestionList.get(i).setText("");
            } else if (state == GameState.RIGHT) {
                btnQuestionList.get(i).setSelected(true);
                btnQuestionList.get(i).setText("");
            }
        }

        txtPoint.setText(String.valueOf(point));

        int resID = getResources().getIdentifier(chapters.get(chapterIdx).getChapterImage(), "drawable", getPackageName());

        gameLayout.setBackground(getResources().getDrawable(resID));
    }

    private GameState getGameState() {
        GameState gameState = new GameState();
        gameState.setChapterIdx(chapterIdx);
        gameState.setChapters(chapters);
        gameState.setPoint(point);

        List<Integer> btnStates = new ArrayList<>();
        for (int i = 0; i < btnQuestionList.size(); i++) {
            Button btn = btnQuestionList.get(i);

            if (!btn.isEnabled()) {
                btnStates.add(GameState.WRONG);
            } else if (btn.isSelected()) {
                btnStates.add(GameState.RIGHT);
            } else {
                btnStates.add(GameState.NONE);
            }
        }
        gameState.setBtnState(btnStates);

        return gameState;
    }

    @Override
    public void onBackPressed() {
        showEndGameDialog();
    }
}