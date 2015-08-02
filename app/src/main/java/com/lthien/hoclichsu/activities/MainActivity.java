package com.lthien.hoclichsu.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.lthien.hoclichsu.sqlite.SQLiteAdapter;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);

        SharedPreferences sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);

        if (sharedPreferences.contains("state")) {
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
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
        int id = v.getId();

        switch (id) {
            case R.id.btnStart:
                btnStart_OnClick();
                break;
        }
    }

    private void btnStart_OnClick() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);

        SharedPreferences sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("state");
        editor.commit();
    }
}
