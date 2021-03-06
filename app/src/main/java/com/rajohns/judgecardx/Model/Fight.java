package com.rajohns.judgecardx.Model;

import com.rajohns.judgecardx.Utils.StringUtils;

/**
 * Created by rajohns on 11/22/14.
 *
 */
public class Fight {
    public String fighter1;
    public String fighter2;
    public String fighter2NoNum;
    public String subtext;
    public String privateCard;
    public int rounds;

    public Fight(String fighter1, String fighter2, String subtext, int rounds, String privateCard) {
        this.fighter1 = fighter1.toUpperCase();
        this.fighter2 = fighter2.toUpperCase();
        this.fighter2NoNum = StringUtils.removeNumbers(fighter2);
        this.subtext = subtext;
        this.rounds = rounds;
        this.privateCard = privateCard;
    }

    public String getFighter1() {
        return fighter1;
    }

    public String getFighter2() {
        return fighter2;
    }

    public String getSubtext() {
        return subtext;
    }

    public int getRounds() {
        return rounds;
    }

    public String getFullSearchText() {
        return fighter1 + " " + fighter2NoNum + " " + fighter1 + " - " + fighter2NoNum + " - " + fighter1;
    }
}
