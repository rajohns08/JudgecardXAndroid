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

    public void updateLeftScore() {
        updateScore(true);
    }

    public void updateRightScore() {
        updateScore(false);
    }

    private void updateScore(Boolean isLeftScore) {
        int scoreInt = isLeftScore ? Integer.parseInt(leftScore) : Integer.parseInt(rightScore);

        scoreInt--;
        if (scoreInt < 0) {
            scoreInt = 10;
        }
        else if (scoreInt < 6) {
            scoreInt = 0;
        }

        if (isLeftScore) {
            leftScore = Integer.toString(scoreInt);
        }
        else {
            rightScore = Integer.toString(scoreInt);
        }
    }
}
