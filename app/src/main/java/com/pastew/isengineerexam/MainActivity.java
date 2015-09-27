package com.pastew.isengineerexam;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
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

    private ImageView questionView, answerAView, answerBView, answerCView;
    private View ANSWERS[];

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
        currentQuestion = 315;
    }

    private void setupUI() {
        ((TextView)findViewById(R.id.questionID)).setText("id: " + currentQuestion);

        questionView = (ImageView) findViewById(R.id.question);
        answerAView = (ImageView) findViewById(R.id.answer_a);
        answerBView = (ImageView) findViewById(R.id.answer_b);
        answerCView = (ImageView) findViewById(R.id.answer_c);

        answerAView.setTag(Answer.A);
        answerBView.setTag(Answer.B);
        answerCView.setTag(Answer.C);

        answerAView.setOnClickListener(answerClickListener);
        answerBView.setOnClickListener(answerClickListener);
        answerCView.setOnClickListener(answerClickListener);

        ANSWERS = new View[3];
        ANSWERS[0] = answerAView;
        ANSWERS[1] = answerBView;
        ANSWERS[2] = answerCView;

        // next question after click anywhere
        findViewById(R.id.main_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQuestion(currentQuestion);
            }
        });
    }


    View.OnClickListener answerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ImageView userAnswer = (ImageView)v;

            // disable answers buttons
            for(View view : ANSWERS)
                view.setClickable(false);


            // user answer is correct
            if(v.getTag().equals(answers.get(currentQuestion))) {
                userAnswer.setBackgroundColor(getResources().getColor(R.color.correct));
            }
            // correct answer is unknown
            else if(answers.get(currentQuestion).equals(Answer.UNKNOWN)) {
                userAnswer.setBackgroundColor(getResources().getColor(R.color.unknown));

                //user answer is wrong
            }else{
                // show correct answer
                for(View view : ANSWERS)
                    if(view.getTag().equals(answers.get(currentQuestion))) {
                        view.setBackgroundColor(getResources().getColor(R.color.correct));
                    }

                // highlight users wrong answer
                userAnswer.setBackgroundColor(getResources().getColor(R.color.wrong));
            }

            ++currentQuestion;
            findViewById(R.id.main_layout).setClickable(true);

            /* // delay instead of click for next question
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showQuestion(++currentQuestion);
                }
            }, 1000);
            */
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
        currentQuestion++;
        showQuestion(currentQuestion);
    }

    /**
     * Loads images for questionNumber and shows it on screen
     */
    private void showQuestion(int questionNumber) {
        findViewById(R.id.main_layout).setClickable(false);

        ((TextView)findViewById(R.id.questionID)).setText("id: " + questionNumber);

        questionView.setImageResource(getDrawableId(questionNumber, 'p'));
        answerAView.setImageResource(getDrawableId(questionNumber, 'a'));
        answerBView.setImageResource(getDrawableId(questionNumber, 'b'));
        answerCView.setImageResource(getDrawableId(questionNumber, 'c'));

        for(View view : ANSWERS) {
            view.setBackgroundColor(getResources().getColor(R.color.inactive));
            view.setClickable(true);
        }
    }

    /**
     *
     * @param questionNumber number of the question
     * @param type p question <br/>
     *             a A answer <br/>
     *             b B answer <br/>
     *             c C answer <br/>
     * @return id of the drawable (for example R.drawable.img1_p
     */
    private int getDrawableId(int questionNumber, char type) {
        StringBuilder fileName = new StringBuilder();
        fileName.append("img");
        fileName.append(questionNumber);
        fileName.append("_");
        fileName.append(type);

        return getResources().getIdentifier(fileName.toString() , "drawable", getPackageName());
    }
}
