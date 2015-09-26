package com.pastew.isengineerexam;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends Activity {

    ImageView question, answerA, answerB, answerC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        question = (ImageView) findViewById(R.id.question);
        answerA = (ImageView) findViewById(R.id.answer_a);
        answerB = (ImageView) findViewById(R.id.answer_b);
        answerC = (ImageView) findViewById(R.id.answer_c);

        answerA.setOnClickListener(questionClickListener);
        answerB.setOnClickListener(questionClickListener);
        answerC.setOnClickListener(questionClickListener);
    }

    View.OnClickListener questionClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ImageView answer = (ImageView) v;
            answer.setBackgroundColor(getResources().getColor(R.color.ok));
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
