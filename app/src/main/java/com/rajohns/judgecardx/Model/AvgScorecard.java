package com.rajohns.judgecardx.Model;

import java.util.List;

/**
 * Created by rajohns on 2/23/15.
 *
 */
public class AvgScorecard extends Scorecard {
    private List<AvgRound> scorecard;

    public AvgScorecard(List<AvgRound> scorecard) {
        super();
        this.scorecard = scorecard;
    }

    public List<AvgRound> getAvgScorecard() {
        return scorecard;
    }
}
