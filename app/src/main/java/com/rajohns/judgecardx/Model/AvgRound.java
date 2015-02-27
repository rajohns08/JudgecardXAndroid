package com.rajohns.judgecardx.Model;

/**
 * Created by rajohns on 2/23/15.
 *
 */
public class AvgRound extends Round {
    private double leftConfidence;
    private double rightConfidence;

    public AvgRound(String leftScore, String leftConfidence, String rightScore, String rightConfidence) {
        super(leftScore, rightScore);
        this.leftConfidence = Double.parseDouble(leftConfidence);
        this.rightConfidence = Double.parseDouble(rightConfidence);
    }

    public double getLeftConfidence() {
        return leftConfidence;
    }

    public double getRightConfidence() {
        return rightConfidence;
    }
}
