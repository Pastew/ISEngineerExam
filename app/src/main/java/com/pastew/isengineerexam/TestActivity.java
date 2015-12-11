package com.pastew.isengineerexam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pastew.isengineerexam.data.Answer;
import com.pastew.isengineerexam.data.Answers;
import com.pastew.isengineerexam.data.FileParser;
import com.pastew.isengineerexam.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TestActivity extends Activity {

    private ImageView questionView, answerAView, answerBView, answerCView;
    private List<View> answersViewList;

    private int[] questionsIds;
    private int currentQuestion;
    private Answers answers;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadAllAnswers();

        setContentView(R.layout.activity_test);
        setupUI();

        Intent intent = getIntent();
        String mode = intent.getStringExtra(MenuActivity.MODE);

        if(mode.equals(MenuActivity.RANDOM_TEST_MODE))
            startRandomTest(intent.getIntExtra(MenuActivity.QUESTIONS_NUMBER, 10));

        if(mode.equals(MenuActivity.RANDOM_RANGE_TEST_MODE)) {
            int questionsNumber = intent.getIntExtra(MenuActivity.QUESTIONS_NUMBER, 10);
            int startQuestionID = intent.getIntExtra(MenuActivity.START_QUESTION_ID, 1);
            int endQuestionID = intent.getIntExtra(MenuActivity.END_QUESTION_ID, 20);

            startRandomRangeTest(questionsNumber, startQuestionID, endQuestionID);
            currentQuestion = 0;
            score = 0;
        }

        showQuestion(questionsIds[currentQuestion]);
    }

    private void startRandomRangeTest(int questionsNumber, int startQuestionID, int endQuestionID) {
        questionsIds = Utils.getRandomArray(questionsNumber, startQuestionID, endQuestionID);
    }

    private void startRandomTest(int questionsNumber) {
        questionsIds = Utils.getRandomArray(questionsNumber, 1, answers.size());
    }

    private void loadAllAnswers() {
        try {
            answers = FileParser.readAnswers(this.getAssets().open("answers.txt"));
        } catch (IOException e) {
            Toast.makeText(this, getString(R.string.cant_load_answers), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void setupUI() {
        ((TextView)findViewById(R.id.questionID)).setText("id: " + Integer.toString(currentQuestion));

        questionView = (ImageView) findViewById(R.id.question);
        answerAView = (ImageView) findViewById(R.id.answer_a);
        answerBView = (ImageView) findViewById(R.id.answer_b);
        answerCView = (ImageView) findViewById(R.id.answer_c);

        answerAView.setTag(Answer.A);
        answerBView.setTag(Answer.B);
        answerCView.setTag(Answer.C);

        answersViewList = new ArrayList<>();
        answersViewList.add(answerAView);
        answersViewList.add(answerBView);
        answersViewList.add(answerCView);

        Collections.shuffle(answersViewList);

        for(View v : answersViewList)
            v.setOnClickListener(answerClickListener);

        // next question after click anywhere
        findViewById(R.id.main_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQuestion(questionsIds[currentQuestion]);
            }
        });

    }

    View.OnClickListener answerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ImageView userAnswer = (ImageView)v;

            // disable answers buttons
            for(View view : answersViewList)
                view.setClickable(false);

            // user answer is correct
            if(v.getTag().equals(answers.get(questionsIds[currentQuestion]))) {
                userAnswer.setBackgroundColor(getResources().getColor(R.color.correct));
                ++score;
                ((TextView)findViewById(R.id.score_tv)).setText(Integer.toString(score));
            }
            // correct answer is unknown
            else if(answers.get(questionsIds[currentQuestion]).equals(Answer.UNKNOWN)) {
                userAnswer.setBackgroundColor(getResources().getColor(R.color.unknown));

                //user answer is wrong
            }else{
                // show correct answer
                for(View view : answersViewList)
                    if(view.getTag().equals(answers.get(questionsIds[currentQuestion]))) {
                        view.setBackgroundColor(getResources().getColor(R.color.correct));
                    }

                // highlight users wrong answer
                userAnswer.setBackgroundColor(getResources().getColor(R.color.wrong));
            }

            ++currentQuestion;
            if(currentQuestion >= questionsIds.length)
                endTest();

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

    private void endTest() {
        finish();
    }

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
        ((TextView)findViewById(R.id.current_question_tv)).setText(currentQuestion+1 + "/" + questionsIds.length);


        questionView.setImageResource(getDrawableId(questionNumber, 'p'));

        answerAView.setImageResource(getDrawableId(questionNumber, 'a'));
        answerBView.setImageResource(getDrawableId(questionNumber, 'b'));
        answerCView.setImageResource(getDrawableId(questionNumber, 'c'));

        LinearLayout questionsContainer = ((LinearLayout) findViewById(R.id.questions_containter));
        questionsContainer.removeAllViews();

        Collections.shuffle(answersViewList);

        for(View v : answersViewList) {
            questionsContainer.addView(v);
            v.setBackgroundColor(getResources().getColor(R.color.inactive));
            v.setClickable(true);
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
