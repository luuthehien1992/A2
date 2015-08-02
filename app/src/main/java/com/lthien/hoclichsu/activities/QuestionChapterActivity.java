package com.lthien.hoclichsu.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lthien.hoclichsu.pojo.Chapter;

public class QuestionChapterActivity extends ActionBarActivity implements View.OnClickListener {
    private Chapter chapter;
    private Button btnAnswer;
    private Button btnBack;

    private TextView txtQuestion;

    private EditText edtAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_question);

        chapter = (Chapter) getIntent().getSerializableExtra("chapter");

        btnAnswer = (Button) findViewById(R.id.btnAnswer);
        btnAnswer.setOnClickListener(this);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        txtQuestion.setText(chapter.getChapterQuestion());

        edtAnswer = (EditText) findViewById(R.id.edtAnswer);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAnswer:
                btnAnswer_OnClick();
                break;
            case R.id.btnBack:
                finish();
                break;
        }
    }

    private void btnAnswer_OnClick() {
        String answer = edtAnswer.getText().toString();

        if (answer.equals(chapter.getChapterAnswer())) {
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.magic_chime_01);
            mp.start();

            Intent intent = getIntent();
            intent.putExtra("answerResult", true);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.fail_trombone_03);
            mp.start();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Bạn trả lời chưa đúng")
                    .setTitle("Thông báo")
                    .setPositiveButton("Đóng", null);

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
