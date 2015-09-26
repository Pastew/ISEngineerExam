package com.pastew.isengineerexam.answers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AnswersParser {


    public static Answers readAnswers(InputStream in) throws IOException {
        ArrayList<Answer> answers = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;

        while( (line = reader.readLine()) != null){
            answers.add(getAnswer(line));
        }

        return new Answers(answers);
    }

    private static Answer getAnswer(String line) {
        char answerChar = line.charAt(line.length()-1);

        switch(answerChar){
            case 'A':
                return Answer.A;
            case 'B':
                return Answer.B;
            case 'C':
                return Answer.C;
            default:
                return Answer.UNKNOWN;
        }
    }

}
