package com.pastew.isengineerexam.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileParser {

    public static Answers readAnswers(InputStream inputStream) throws IOException {
        ArrayList<Answer> answers = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;

        while( (line = reader.readLine()) != null){
            answers.add(getAnswer(line));
        }

        return new Answers(answers);
    }

    public static Subjects readSubjects(InputStream inputStream) throws IOException {
        Subjects subjects = new Subjects();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        String[] splitLine;

        while( (line = reader.readLine()) != null){
            splitLine = line.split("-");

            String name = splitLine[0];
            int firstQuestionId = Integer.parseInt(splitLine[1]);
            int lastQuestionId = Integer.parseInt(splitLine[2]);

            subjects.add(new Subject(name, firstQuestionId, lastQuestionId));
        }

        return subjects;
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
