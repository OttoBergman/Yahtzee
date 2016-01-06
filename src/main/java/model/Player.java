package model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by otto on 2015-11-18.
 */

public class Player implements Serializable{

    private String playerName;
    private ScoreSheet scoreSheet;
    private boolean isBot;
    private ArrayList<Dice> rolledDice;
    private ArrayList<Dice> heldDice;
    private ArrayList<Dice> turnScore;

    public Player(String playerName) {
        isBot = false;
        this.playerName = playerName;
        scoreSheet = new ScoreSheet();
        turnScore = new ArrayList<Dice>();
    }

    public Player() {
        this.playerName = playerName;
        scoreSheet = new ScoreSheet();
        turnScore = new ArrayList<Dice>();
    }

    public String getName() {
        return playerName;
    }
    public void setName(String name) {
        playerName = name;
    }

    public ArrayList<Dice> rollDice(int numberOfDice){
        ArrayList<Dice> dice = new ArrayList<Dice>(numberOfDice);
        Random r = new Random();

        for(int i=0; i < numberOfDice ;i++){
            Dice d = new Dice();
            d.setDiceValue(r.nextInt(6) + 1);
            dice.add(d);
        }
        setRolledDice(dice);
        return dice;
    }

    public ArrayList<Dice> holdDice(int[] indices, ArrayList<Dice> resultDices){
        ArrayList<Dice> heldDices = new ArrayList<Dice>(indices.length);

        for(int i=0; i < indices.length; i++){
            System.out.println("YOLO");
            heldDices.add(resultDices.get(indices[i]));
            //turnScore.add(heldDices[i]);
        }
        setHeldDice(heldDices);
        setTurnScore(heldDices);
        return heldDices;
    }

    public ArrayList<Dice> getRolledDice() {
        return rolledDice;
    }

    public void setRolledDice(ArrayList<Dice> rolledDice) {
        this.rolledDice = rolledDice;
    }

    public ArrayList<Dice> getHeldDice() {
        return heldDice;
    }

    public void setHeldDice(ArrayList<Dice> heldDice) {
        this.heldDice = heldDice;
    }

    public ArrayList<Dice> getTurnScore() {
        return turnScore;
    }

    public void resetTurnScore(){
        turnScore = new ArrayList<Dice>();
    }

    public void setTurnScore(ArrayList<Dice> roundScore) {
        for(int i = 0; i < roundScore.size(); i++)
            this.turnScore.add(roundScore.get(i));
    }

    public boolean isBot() {
        return isBot;
    }

    public void setBot(boolean bot) {
        isBot = bot;
    }

    public ScoreSheet getScoreSheet() {
        return scoreSheet;
    }

    public void setScoreSheet(ScoreSheet scoreSheet) {
        this.scoreSheet = scoreSheet;
    }
}
