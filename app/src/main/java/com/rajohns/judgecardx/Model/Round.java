package com.rajohns.judgecardx.Model;

/**
 * Created by rajohns on 1/8/15.
 *
 */
public class Round {
    private String leftScore;
    private String rightScore;

    public Round(String leftScore, String rightScore) {
        this.leftScore = leftScore;
        this.rightScore = rightScore;
    }

    public String getLeftScore() {
        return leftScore;
    }

    public void setLeftScore(String leftScore) {
        this.leftScore = leftScore;
    }

    public String getRightScore() {
        return rightScore;
    }

    public void setRightScore(String rightScore) {
        this.rightScore = rightScore;
    }
}
