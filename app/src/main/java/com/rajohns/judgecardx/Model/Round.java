package com.rajohns.judgecardx.Model;

/**
 * Created by rajohns on 1/8/15.
 *
 */
public class Round {
    private int leftScore;
    private int rightScore;

    public Round(int leftScore, int rightScore) {
        this.leftScore = leftScore;
        this.rightScore = rightScore;
    }

    public int getLeftScore() {
        return leftScore;
    }

    public void setLeftScore(int leftScore) {
        this.leftScore = leftScore;
    }

    public int getRightScore() {
        return rightScore;
    }

    public void setRightScore(int rightScore) {
        this.rightScore = rightScore;
    }

    public String getLeftScoreString() {
        return String.valueOf(leftScore);
    }

    public String getRightScoreString() {
        return String.valueOf(rightScore);
    }
}
