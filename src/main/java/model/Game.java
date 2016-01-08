package model;

import rules.IGameMode;
import rules.RulesFactory;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by otto on 2015-11-18.
 */
public class Game extends SaveGame implements Serializable{

    private static final long serialVersionUID = 1L;
    private IGameMode gameMode;
    private int playerTurn = 0;
    private int currentRound;
    private ArrayList<Player> players;
    private final int FULL_DICE_NUMBER = 5;
    private SaveGame saveGame = new SaveGame();

    public Game(){
        gameMode = new RulesFactory().getGameMode();
        players = new ArrayList<Player>();
    }

    public void CreatePlayer(int playerNumber, boolean isBot){
        for(int i = 1; i <= playerNumber; i++) {
            String name = "";
            if (isBot) {       //Names the computer player automatically
                name = "Computer " + 1;
            }
            Player p = new Player(name);
            players.add(p);
        }
    }

    public boolean PlayersNumberIsValid(int playerNumbers){
        return 1 <= playerNumbers && playerNumbers <= 5;
    }


    public Player StartRound(){
        currentRound++;
        if(players.size() == 1) {
            return players.get(playerTurn);
        }else if(players.size() ==playerTurn){
            playerTurn =0;
            return players.get(playerTurn);
        }
        playerTurn++;
        return players.get(playerTurn - 1);
    }

    public ArrayList<Dice> rollDice(Player p) {
        int numberOfDice = FULL_DICE_NUMBER - p.getTurnScore().size();
        return p.rollDice(numberOfDice);
    }

    public ArrayList<Dice> holdDice(Player player, int[] diceToHold, ArrayList<Dice> rolledDice) {return player.holdDice(diceToHold, rolledDice);}

    public boolean IsGameOver(){  return (currentRound == 13); }

    public Player getWinner(){
        Player winner = players.get(0);
        for(Player p:players){
            if(p.getScoreSheet().getScoreValue(ScoreSheet.value.TOTAL_SCORE) >
                    winner.getScoreSheet().getScoreValue(ScoreSheet.value.TOTAL_SCORE));
            winner = p;
        }
        return winner;
    }

    public int GetPlayersNumber(){
        return players.size();
    }

    /*
    Takes in a string array of names of players, then goes through 'allPlayers' to name each player in this arrayList
     */
    public void setPlayerNames(String[] playerNames) {
        for (int i = 0; i < playerNames.length; i++){
            Player player = players.get(i);
            player.setName(playerNames[i]);
        }
    }
    public SaveGame getSaveGame() {
        return saveGame;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public IGameMode getGameMode() {
        return gameMode;
    }
}
