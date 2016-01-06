package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by otto on 2015-11-24.
 */
public class ScoreSheet implements Serializable {

    public enum value {
        ONES, TWOS, THREES, FOURS, FIVES, SIXES, SUM, BONUS, THREE_OF_A_KIND, FOUR_OF_A_KIND, FULL_HOUSE, SMALL_STRAIGHT,
        LARGE_STRAIGHT, CHANCE, YAHTZEE, TOTAL_SCORE
    }

    private HashMap<String, Integer> scoreSheet;

    public ScoreSheet() {
        setupScoresheet();
    }

    private void setupScoresheet() {
        scoreSheet = new HashMap<String, Integer>();
        for (value v: value.values()) {
            scoreSheet.put(v.toString(), 0);
        }
    }

    public void setScoreValue(value scoreType, int value) {
        scoreSheet.put(scoreType.toString(), value);
    }

    public int getScoreValue(value scoreType) {
        return scoreSheet.get(scoreType.toString());
    }

    public boolean isThreeOfAKind(ArrayList<Dice> h){
        int[] oneToSix = new int[]{1,2,3,4,5,6};
        for (int j = 0; j < oneToSix.length; j++) {
            int count = 0;

            for(int i = 0;i < h.size() ;i++) {
                if (h.get(i).getDiceValue() == oneToSix[j]) {
                    count++;
                }
                if (count >= 3) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isFourOfAKind(ArrayList<Dice> h){
        int[] oneToSix = new int[]{1,2,3,4,5,6};
        for (int j = 0; j < oneToSix.length; j++) {
            int count = 0;

            for(int i = 0;i < h.size() ;i++) {
                if (h.get(i).getDiceValue() == oneToSix[j]) {
                    count++;
                }
                if (count >= 4) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isYahtzee(ArrayList<Dice> h){
        int[] check = new int[h.size()];
        int[] original = new int[h.size()];

        for (int i = 0; i < 5; i++) {
            Arrays.fill(original, h.get(i).getDiceValue());
        }

        Arrays.fill(check, h.get(0).getDiceValue());
        return Arrays.equals(original, check);
    }

    public boolean isFullHouse(ArrayList<Dice> h ){
        boolean a1, a2;

        if ( h.size() != 5 )
            return(false);

        sortByRank(h);

        a1 = h.get(0).getDiceValue()== h.get(1).getDiceValue() &&
                h.get(1).getDiceValue() == h.get(2).getDiceValue() &&
                h.get(3).getDiceValue() == h.get(4).getDiceValue();


        a2 = h.get(0).getDiceValue() == h.get(1).getDiceValue() &&
                h.get(2).getDiceValue() == h.get(3).getDiceValue() &&
                h.get(3).getDiceValue() == h.get(4).getDiceValue();

        return( a1 || a2 );
    }

    public boolean isSmallStraight(ArrayList<Dice> h ){
        int i, testRank;

        sortByRank(h);

        testRank = h.get(0).getDiceValue() + 1;

        for ( i = 1; i < 4; i++ ){
            if ( h.get(i).getDiceValue()!= testRank )
                return(false);
            testRank++;
        }

        return(true);
    }

    public boolean isLargeStraight(ArrayList<Dice> h ){
        int i, testRank;

        sortByRank(h);

        testRank = h.get(0).getDiceValue() + 1;

        for ( i = 1; i < 5; i++ ){
            if ( h.get(i).getDiceValue()!= testRank )
                return(false);
            testRank++;
        }

        return(true);
    }

    public void sortByRank(ArrayList<Dice> h ){
        Collections.sort(h);
        for (Dice d : h) {
            System.out.println(d.getDiceValue());
        }
    }

    public boolean isBonusValid() {
        return (getScoreValue(value.SUM) >= 63);
    }

    public void scorePoints(int a, ArrayList<Dice> dices){
        switch(a) {
            case 1:
                for (Dice i : dices) {
                    if (i.getDiceValue() == 1) {
                        this.setScoreValue(value.ONES, getScoreValue(value.ONES) + 1);
                        this.setScoreValue(value.SUM, getScoreValue(value.SUM) + 1);
                    }
                }
                break;

            case 2: for(Dice i:dices){
                if(i.getDiceValue()==2) {
                    this.setScoreValue(value.TWOS, getScoreValue(value.TWOS) + 2);
                    this.setScoreValue(value.SUM, getScoreValue(value.SUM) + 2);
                }
            }
                break;

            case 3: for(Dice i:dices){
                if(i.getDiceValue()==3) {
                    this.setScoreValue(value.THREES, getScoreValue(value.THREES) + 3);
                    this.setScoreValue(value.SUM, getScoreValue(value.SUM) + 3);
                }
            }
                break;

            case 4: for(Dice i:dices){
                if(i.getDiceValue()==4) {
                    this.setScoreValue(value.FOURS, getScoreValue(value.FOURS) + 4);
                    this.setScoreValue(value.SUM, getScoreValue(value.SUM) + 4);
                }

            }
                break;

            case 5: for(Dice i:dices){
                if(i.getDiceValue()==5) {
                    this.setScoreValue(value.FIVES, getScoreValue(value.FIVES) + 5);
                    this.setScoreValue(value.SUM, getScoreValue(value.SUM) + 5);
                }
            }
                break;

            case 6: for(Dice i:dices){
                if(i.getDiceValue()==6) {
                    this.setScoreValue(value.SIXES, getScoreValue(value.SIXES) + 6);
                    this.setScoreValue(value.SUM, getScoreValue(value.SUM) + 6);
                }
            }
                break;
            //Three of a kind
            case 7:
                if(isThreeOfAKind(dices)) {
                    int diceValue = 0;
                    for (Dice d : dices) {
                        diceValue += d.getDiceValue();
                    }
                    this.setScoreValue(value.THREE_OF_A_KIND, getScoreValue(value.THREE_OF_A_KIND) + diceValue);
                }
                break;
            //four of a kind
            case 8:
                if(isFourOfAKind(dices)) {
                    int diceValue = 0;
                    for (Dice d : dices) {
                        diceValue += d.getDiceValue();
                    }
                    this.setScoreValue(value.FOUR_OF_A_KIND, getScoreValue(value.FOUR_OF_A_KIND) + diceValue);
                }
                break;

            case 9:
                if(isFullHouse(dices)){
                    int fullHouse = 25;
                    this.setScoreValue(value.FULL_HOUSE, getScoreValue(value.FULL_HOUSE) + fullHouse);
                }
                break;

            //There is no difference between the straights yet
            case 10:
                if(isSmallStraight(dices)) {
                    int sStraight = 30;
                    this.setScoreValue(value.SMALL_STRAIGHT, getScoreValue(value.SMALL_STRAIGHT) + sStraight);
                }
                break;

            case 11:
                if(isLargeStraight(dices)) {
                    int lStraight = 40;
                    this.setScoreValue(value.LARGE_STRAIGHT, getScoreValue(value.LARGE_STRAIGHT) + lStraight);
                }
                break;

            case 12:
                if(isYahtzee(dices)) {
                    int yahtzee = 50;
                    this.setScoreValue(value.YAHTZEE, getScoreValue(value.YAHTZEE) + yahtzee);
                }
                break;

            case 13:
                int chance = 0;
                for(Dice d:dices) {
                    chance += d.getDiceValue();
                }
                this.setScoreValue(value.CHANCE, getScoreValue(value.CHANCE) + chance);
                break;
        }

        if (isBonusValid() && getScoreValue(value.BONUS) == 0) {
            this.setScoreValue(value.BONUS, getScoreValue(value.BONUS) + 35);
        }
    }

    public void scoreTotalPoints() {
        int totalSum = 0;

        for (int i = 6; i < value.values().length; i++) {
            totalSum += getScoreValue(value.values()[i]);
        }
        setScoreValue(value.TOTAL_SCORE, totalSum);
    }

}
