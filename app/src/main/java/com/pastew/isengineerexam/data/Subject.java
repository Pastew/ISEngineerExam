package com.pastew.isengineerexam.data;

/**
 * Created by Pastew on 2015-12-10.
 */
public class Subject {

    private String name;
    private int firstQuestionId, lastQuestionId;

    public int getFirstQuestionId() {
        return firstQuestionId;
    }

    public int getLastQuestionId() {
        return lastQuestionId;
    }

    public Subject(String name, int firstQuestionId, int lastQuestionId) {
        this.name = name;
        this.firstQuestionId = firstQuestionId;
        this.lastQuestionId = lastQuestionId;
    }

    public String getName() {
        return name;
    }
}
