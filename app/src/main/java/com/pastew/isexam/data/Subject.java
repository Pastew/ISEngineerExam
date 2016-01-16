package com.pastew.isexam.data;

/**
 * Created by Pastew on 2015-12-10.
 */
public class Subject {

    private String name;
    private int[] questionsIDs;

    public int[] getQuestionsIDs(){ return questionsIDs; };

    public Subject(String name, int[] questionsIDs){
        this.name = name;
        this.questionsIDs = questionsIDs;
    }

    public Subject(String name, int firstQuestionId, int lastQuestionId) {
        this.name = name;

        questionsIDs = new int[lastQuestionId-firstQuestionId+1];
        for(int id = firstQuestionId, i=0 ; i < questionsIDs.length ; ++id, ++i)
            questionsIDs[i] = id;
    }

    public String getName() {
        return name;
    }

    public int getQuestionsNumber(){
        return questionsIDs.length;
    }
}