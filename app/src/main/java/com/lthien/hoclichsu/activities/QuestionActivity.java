package com.lthien.hoclichsu.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lthien.hoclichsu.pojo.Question;

public class QuestionActivity extends ActionBarActivity implements View.OnClickListener {

    private TextView txtQuestion;
    private Question question;
    private RadioButton radA;
    private RadioButton radB;
    private RadioButton radC;
    private RadioButton radD;
    private RadioButton radOld;
    private RadioGroup radgAnswer;
    private Button btnAnswer;
    private int questionIdx = 0;

    private ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        question = (Question) getIntent().getSerializableExtra("question");

        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        txtQuestion.setText(Html.fromHtml(question.getContent()));

        radA = (RadioButton) findViewById(R.id.radA);
        radA.setText(Html.fromHtml(question.getAnswers().get(0)));
        radA.setOnClickListener(this);

        radB = (RadioButton) findViewById(R.id.radB);
        radB.setText(Html.fromHtml(question.getAnswers().get(1)));
        radB.setOnClickListener(this);

        radC = (RadioButton) findViewById(R.id.radC);
        radC.setText(Html.fromHtml(question.getAnswers().get(2)));
        radC.setOnClickListener(this);

        radD = (RadioButton) findViewById(R.id.radD);
        radD.setText(Html.fromHtml(question.getAnswers().get(3)));
        radD.setOnClickListener(this);

        radgAnswer = (RadioGroup) findViewById(R.id.radgAnswer);

        btnAnswer = (Button) findViewById(R.id.btnAnswer);
        btnAnswer.setOnClickListener(this);

        imgView = (ImageView) findViewById(R.id.imgView);
        if (question.getImage() != null) {
            int resID = getResources().getIdentifier(question.getImage(), "drawable", getPackageName());

            imgView.setImageDrawable(getResources().getDrawable(resID));
        }
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
        if (radOld == null) {
            radOld = radA;
        }
        radOld.setChecked(false);

        switch (v.getId()) {
            case R.id.btnAnswer:
                btnAnswer_OnClick();
                break;
            case R.id.radD:
                radOld = radD;
                questionIdx = 3;
                break;
            case R.id.radC:
                radOld = radC;
                questionIdx = 2;
                break;
            case R.id.radB:
                radOld = radB;
                questionIdx = 1;
                break;
            case R.id.radA:
                radOld = radA;
                questionIdx = 0;
                break;
        }

        radOld.setChecked(true);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Bạn có muốn bỏ qua câu hỏi ?")
                .setTitle("Thông báo")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = getIntent();
                        intent.putExtra("answerResult", false);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .setNegativeButton("Không", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void btnAnswer_OnClick() {
        String msg = "Sai rồi bạn ơi! :'(";
        boolean answerResult = false;
        if (questionIdx == question.getAnswersIdx()) {
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.magic_chime_01);
            mp.start();

            msg = "Chính xác! Bạn giỏi quá đi";
            answerResult = true;
        } else {
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.fail_trombone_03);
            mp.start();
        }

        showResultAndFinish(msg, answerResult);
    }


    private void showResultAndFinish(String msg, final boolean answerResult) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        builder.setMessage(msg)
//                .setTitle("Thông báo")
//                .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = getIntent();
//                        intent.putExtra("answerResult", answerResult);
//                        setResult(RESULT_OK, intent);
//                        finish();
//                    }
//                });
//
//        AlertDialog dialog = builder.create();
//        dialog.show();
        Intent intent = getIntent();
        intent.putExtra("answerResult", answerResult);
        setResult(RESULT_OK, intent);
        finish();
    }
}
