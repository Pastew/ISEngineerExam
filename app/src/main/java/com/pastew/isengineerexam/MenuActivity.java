package com.pastew.isengineerexam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pastew.isengineerexam.data.FileParser;
import com.pastew.isengineerexam.data.Subject;
import com.pastew.isengineerexam.data.Subjects;
import com.pastew.isengineerexam.utils.Utils;

import java.io.IOException;
import java.util.List;

public class MenuActivity extends Activity {

    private SharedPreferences sharedPreferences;

    public final int QUESTION_INCREMENT = 5;

    private Subjects subjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ((TextView) findViewById(R.id.app_version_tv)).setText("v" + BuildConfig.VERSION_NAME);
        populateSubjectsSpinner();
        addButtonsListeners();
        addCheckBoxListener();

        sharedPreferences = getSharedPreferences(FinalStrings.ONLINE_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        setOnline(((CheckBox) findViewById(R.id.online_checkbox)).isChecked());
    }

    private void addCheckBoxListener() {
        CheckBox onlineCheckbox = ( CheckBox ) findViewById( R.id.online_checkbox );
        onlineCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setOnline(isChecked);
            }
        });
    }

    private void setOnline(boolean online) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getString(R.string.online_pref), online);
        editor.commit();
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
                AnalyticsApplication.getInstance().trackEvent("MainMenuButtons", "AllAnswers", "click");
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


                String order = ((CheckBox) findViewById(R.id.random_question_order_cb)).isChecked()
                        ? "random" : "ordered";

                String online = ((CheckBox) findViewById(R.id.online_checkbox)).isChecked() ? "online" : "offline";

                AnalyticsApplication.getInstance().trackEvent("MainMenuButtons", "Start: " + order + ", " + online, subject.getName());

                startTest(subject.getQuestionsIDs());
            }
        });

        (findViewById(R.id.stats_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://gcweb.drl.pl/is_exam/results.php")));
                AnalyticsApplication.getInstance().trackEvent("MainMenuButtons", "Stats", "click");
            }
        });

        (findViewById(R.id.user_answers_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link =
                        "http://gcweb.drl.pl/is_exam/results.php?user_id="
                                + Settings.Secure.getString(getApplicationContext().getContentResolver(),
                                Settings.Secure.ANDROID_ID);

                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(link)));
            }
        });
    }

    private void startTest(int[] subjectQuestionsIDs) {
        int questionsNumber = Integer.parseInt( ((TextView)findViewById(R.id.questions_number)).getText().toString() );
        if(questionsNumber < 1 || questionsNumber > 711) {
            Toast.makeText(getApplicationContext(), "Podaj normalną ilość pytań", Toast.LENGTH_SHORT).show();
            return;
        }

        if(questionsNumber > subjectQuestionsIDs.length)
            questionsNumber = subjectQuestionsIDs.length;

        boolean randomOrder = ((CheckBox) findViewById(R.id.random_question_order_cb)).isChecked();
        if(randomOrder)
            subjectQuestionsIDs = Utils.shuffleArray(subjectQuestionsIDs);


        int[] questionsIDs = new int[questionsNumber];
        System.arraycopy(subjectQuestionsIDs, 0, questionsIDs, 0, questionsNumber);

        Intent intent = new Intent(this, TestActivity.class);
        intent.putExtra(FinalStrings.QUESTIONS_IDS, questionsIDs);
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
