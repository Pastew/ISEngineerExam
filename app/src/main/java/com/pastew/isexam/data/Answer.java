package com.pastew.isexam.data;

public enum Answer {
    A("a"),
    B("b"),
    C("c"),
    UNKNOWN("unknown");

    private final String text;

    Answer(final String text){
        this.text = text;
    }

    public final String getString(){
        return text;
    }
    @Override
    public String toString(){
        return text;
    }
}
