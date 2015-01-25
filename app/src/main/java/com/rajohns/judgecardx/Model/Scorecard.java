package com.rajohns.judgecardx.Model;

import java.util.List;

/**
 * Created by rajohns on 1/7/15.
 *
 */
public class Scorecard {
    private List<Round> scorecard;

    public Scorecard(List<Round> scorecard) {
        this.scorecard = scorecard;
    }

    public List<Round> getScorecard() {
        return scorecard;
    }

    public void setScorecard(List<Round> scorecard) {
        this.scorecard = scorecard;
    }
}
