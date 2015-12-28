package com.pastew.isengineerexam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pastew.isengineerexam.utils.Utils;

public class ResultsActivity extends Activity {

    private int[] questionsIDs;
    private int[] scores;
    private int score, questionsNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        getExtras();
        score = Utils.getScore(scores);
        questionsNumber = questionsIDs.length;

        initUI();
        addButtonListeners();
    }

    private void addButtonListeners() {
        findViewById(R.id.retake_test_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent testIntent = new Intent(ResultsActivity.this, TestActivity.class);
                testIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                testIntent.putExtra(MenuActivity.QUESTIONS_IDS, getWrongAnsweredQuestionsIDs());
                startActivity(testIntent);
                finish();
            }
        });

        findViewById(R.id.exit_results_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private int[] getWrongAnsweredQuestionsIDs() {
        int[] retakeQuestionsIDs = new int[questionsNumber - Utils.getScore(scores)];
        int j = 0;
        for(int i = 0 ; i < questionsIDs.length ; ++i)
            if(scores[i] == 0)
                retakeQuestionsIDs[j++] = questionsIDs[i];

        return retakeQuestionsIDs;
    }

    private void initUI() {
        ((TextView)findViewById(R.id.result_text_view)).setText(score + " / " + questionsNumber);

        // hide RetakeTest button if all answers are correct
        if (questionsNumber == score)
            findViewById(R.id.retake_test_button).setVisibility(View.GONE);
    }

    private void getExtras() {
        Intent intent = getIntent();
        scores = intent.getIntArrayExtra(TestActivity.SCORES);
        questionsIDs = intent.getIntArrayExtra(MenuActivity.QUESTIONS_IDS);
    }
}
