package com.pastew.isengineerexam;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pastew.isengineerexam.answers.Answer;
import com.pastew.isengineerexam.answers.Answers;
import com.pastew.isengineerexam.answers.AnswersParser;

import java.io.IOException;


public class MainActivity extends Activity {

    private ImageView question, answerA, answerB, answerC;
    private int currentQuestion;
    private Answers answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            answers = AnswersParser.readAnswers(this.getAssets().open("answers.txt"));
        } catch (IOException e) {
            Toast.makeText(this, getString(R.string.cant_load_answers), Toast.LENGTH_LONG).show();
            finish();
        }

        setContentView(R.layout.activity_main);
        setupUI();
        currentQuestion = 0;
    }

    private void setupUI() {
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

    public void debug(View v) {
        TextView debugTV = (TextView) v;

        currentQuestion++;

        Answer answer = answers.get(currentQuestion);
        debugTV.setText(currentQuestion + answer.toString());
    }
}
