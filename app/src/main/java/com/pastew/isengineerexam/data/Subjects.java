package com.pastew.isengineerexam.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pastew on 2015-12-10.
 */
public class Subjects {

    ArrayList<Subject> subjects;

    public Subjects() {
        this.subjects = new ArrayList<>();
    }

    public void add(Subject subject) {
        this.subjects.add(subject);
    }

    public List<String> getNamesList() {
        List<String> subjectNamesList = new ArrayList<>();
        for(Subject s : subjects)
            subjectNamesList.add(s.getName());

        return subjectNamesList;
    }

    public Subject getSubject(String subjectName) {
        for(Subject s : subjects){
            if(s.getName().equals(subjectName))
                return s;
        }
        return null;
    }

    public int getQuestionsNumber(String subjectName) {
        for(Subject s : subjects){
            if(s.getName().equals(subjectName))
                return s.getLastQuestionId() - s.getFirstQuestionId() + 1;
        }

        return 10; // That's not a good idea (?)
    }
}
