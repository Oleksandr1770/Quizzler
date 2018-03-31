package com.londonappbrewery.quizzler;

public class TrueFalse {
    private int id;
    private boolean answer;

    public TrueFalse(int id, boolean answer) {
        this.id = id;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getAnswer() {
        return answer;
    }

    public void setAnswer(boolean answers) {
        this.answer = answer;
    }
}
