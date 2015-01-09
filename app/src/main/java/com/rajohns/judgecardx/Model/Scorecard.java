package com.rajohns.judgecardx.Model;

import java.util.ArrayList;

/**
 * Created by rajohns on 1/7/15.
 *
 */
public class Scorecard {
    private ArrayList<Round> scorecard;

    public Scorecard(ArrayList<Round> scorecard) {
        this.scorecard = scorecard;
    }

    public ArrayList<Round> getScorecard() {
        return scorecard;
    }

    public void setScorecard(ArrayList<Round> scorecard) {
        this.scorecard = scorecard;
    }
}
