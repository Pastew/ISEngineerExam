package com.pastew.isengineerexam.data;

import java.util.ArrayList;

public class Answers {

    private ArrayList<Answer> answers;

    public Answers(ArrayList<Answer> answers) {
        this.answers = answers;
    }


    public Answer get(int index){
        return answers.get(index - 1);
    }

    public int size() {
        return answers.size();
    }
}
