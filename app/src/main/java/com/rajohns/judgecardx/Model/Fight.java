package com.rajohns.judgecardx.Model;

/**
 * Created by rajohns on 11/22/14.
 *
 */
public class Fight {
    public String fighter1;
    public String fighter2;
    public String subtext;

    public Fight(String fighter1, String fighter2, String subtext) {
        this.fighter1 = fighter1;
        this.fighter2 = fighter2;
        this.subtext = subtext;
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
}
