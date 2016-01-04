package model;

import java.util.HashMap;

/**
 * Created by otto on 2015-11-24.
 */
public class ScoreSheet {

    public enum value {
        ONES, TWOS, THREES, FOURS, FIVES, SIXES, THREE_OF_A_KIND, FOUR_OF_A_KIND, FULL_HOUSE, SMALL_STRAIGHT,
        LARGE_STRAIGHT, CHANCE, YAHTZEE, TOTAL_SCORE
    }

    private HashMap<String, Integer> scoreSheet;

    public ScoreSheet() {
        setupScoresheet();
    }

    private void setupScoresheet() {
        scoreSheet = new HashMap<String, Integer>();
        for (value v: value.values()) {
            scoreSheet.put(v.toString(), null);
        }
    }

    public void setScoreValue(value scoreType, int value) {
        scoreSheet.put(scoreType.toString(), value);
    }


}
