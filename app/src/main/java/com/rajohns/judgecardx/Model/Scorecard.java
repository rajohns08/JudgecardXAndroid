package com.rajohns.judgecardx.Model;

import java.util.List;

/**
 * Created by rajohns on 1/7/15.
 *
 */
public class Scorecard {
    private List<Round> scorecard;
    private int leftTotal;
    private int rightTotal;

    public Scorecard(List<Round> scorecard) {
        this.scorecard = scorecard;
    }

    public List<Round> getScorecard() {
        return scorecard;
    }

    public void setScorecard(List<Round> scorecard) {
        this.scorecard = scorecard;
    }

    public int getLeftTotal() {
        leftTotal = 0;

        for (Round round : scorecard) {
            leftTotal += Integer.parseInt(round.getLeftScore());
        }

        return leftTotal;
    }

    public int getRightTotal() {
        rightTotal = 0;

        for (Round round : scorecard) {
            rightTotal += Integer.parseInt(round.getRightScore());
        }

        return rightTotal;
    }
}
