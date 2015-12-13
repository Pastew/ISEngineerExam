package com.pastew.isengineerexam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pastew.isengineerexam.data.FileParser;
import com.pastew.isengineerexam.data.Subject;
import com.pastew.isengineerexam.data.Subjects;

import java.io.IOException;
import java.util.List;

public class MenuActivity extends Activity {

    public final static String QUESTIONS_NUMBER = "QUESTIONS_NUMBER";
    public final static String MODE = "MODE";
    public static final String ONLINE = "ONLINE";

    public final static String START_QUESTION_ID = "START_QUESTION_ID";
    public final static String END_QUESTION_ID = "END_QUESTION_ID";

    public final static String RANDOM_RANGE_TEST_MODE = "RANDOM_RANGE_TEST_MODE";
    public static final String ORDER_RANGE_TEST_MODE = "ORDER_RANGE_TEST_MODE";
    public static final int QUESTION_INCREMENT = 5;

    private Subjects subjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ((TextView) findViewById(R.id.app_version_tv)).setText("v" + BuildConfig.VERSION_NAME);
        populateSubjectsSpinner();
        addButtonsListeners();
    }

    private void addButtonsListeners() {
        (findViewById(R.id.minus_questions_number)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQuestionsNumber(-5);
            }
        });

        (findViewById(R.id.plus_questions_number)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQuestionsNumber(QUESTION_INCREMENT);
            }
        });

        (findViewById(R.id.select_all_questions_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner subjectSpinner = (Spinner) findViewById(R.id.subject_spinner);
                String subjectName = subjectSpinner.getSelectedItem().toString();
                int questionsNumberForSelectedSubject = subjects.getQuestionsNumber(subjectName);

                ((TextView) findViewById(R.id.questions_number)).setText(Integer.toString(questionsNumberForSelectedSubject));
            }
        });

        (findViewById(R.id.start_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Spinner subjectSpinner = (Spinner) findViewById(R.id.subject_spinner);
                String selectedSubject = subjectSpinner.getSelectedItem().toString();
                Subject subject = subjects.getSubject(selectedSubject);

                if (subject == null) {
                    Toast.makeText(getApplicationContext(), "Coś poszło nie tak...", Toast.LENGTH_LONG).show(); // This should not happen.
                    return;
                }

                int startQuestionId = subject.getFirstQuestionId();
                int endQuestionId = subject.getLastQuestionId();

                startTest(startQuestionId, endQuestionId);
            }
        });
    }

    private void startTest(int startQuestionId, int endQuestionId) {
        Intent intent = new Intent(this, TestActivity.class);
        int questionsNumber = Integer.parseInt( ((TextView)findViewById(R.id.questions_number)).getText().toString() );
        if(questionsNumber < 1 || questionsNumber > 711) {
            Toast.makeText(getApplicationContext(), "Podaj normalną ilość pytań", Toast.LENGTH_SHORT).show();
            return;
        }

        if (((CheckBox) findViewById(R.id.random_question_order_cb)).isChecked())
            intent.putExtra(MODE, RANDOM_RANGE_TEST_MODE);
        else
            intent.putExtra(MODE, ORDER_RANGE_TEST_MODE);

        intent.putExtra(QUESTIONS_NUMBER, questionsNumber);
        intent.putExtra(START_QUESTION_ID, startQuestionId);
        intent.putExtra(END_QUESTION_ID, endQuestionId);
        intent.putExtra(ONLINE, ((CheckBox) findViewById(R.id.online)).isChecked());

        startActivity(intent);
    }

    private void addQuestionsNumber(int number) {
        TextView questionsNumberTV = (TextView) findViewById(R.id.questions_number);

        int questionsNumber;
        try {
            String questionsNumberString = questionsNumberTV.getText().toString();
            questionsNumber = Integer.parseInt(questionsNumberString);
        } catch( Exception e){
            questionsNumber = 0;
        }

        if (questionsNumber + number > 0 && questionsNumber + number < 711) //TODO hardcode
            questionsNumber += number;

        questionsNumberTV.setText(Integer.toString(questionsNumber));
    }

    private void populateSubjectsSpinner() {
        try {
            subjects = FileParser.readSubjects(this.getAssets().open("subjects.txt"));
        } catch (IOException e) {
            Toast.makeText(this, getString(R.string.cant_load_answers), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        List<String> subjectsNameList = subjects.getNamesList();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, subjectsNameList);
        ((Spinner) findViewById(R.id.subject_spinner)).setAdapter(adapter);
    }
}
