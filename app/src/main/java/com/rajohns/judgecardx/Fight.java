package com.rajohns.judgecardx;

/**
 * Created by rajohns on 11/22/14.
 *
 */
public class Fight {
    public String fighter1;
    public String fighter2;
    public String fightDate;

    public Fight(String fighter1, String fighter2, String fightDate) {
        this.fighter1 = fighter1;
        this.fighter2 = fighter2;
        this.fightDate = fightDate;
    }

    public String getFighter1() {
        return fighter1;
    }

    public void setFighter1(String fighter1) {
        this.fighter1 = fighter1;
    }

    public String getFighter2() {
        return fighter2;
    }

    public void setFighter2(String fighter2) {
        this.fighter2 = fighter2;
    }

    public String getFightDate() {
        return fightDate;
    }

    public void setFightDate(String fightDate) {
        this.fightDate = fightDate;
    }
}
