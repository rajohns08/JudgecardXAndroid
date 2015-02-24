package com.rajohns.judgecardx.Model;

import java.util.List;

/**
 * Created by rajohns on 1/7/15.
 *
 */
public class Scorecard {
    protected List<Round> scorecard;
    protected int leftTotal;
    protected int rightTotal;
    protected String id;

    public Scorecard() {
        this.id = null;
    }

    public Scorecard(List<Round> scorecard, String id) {
        this.scorecard = scorecard;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
