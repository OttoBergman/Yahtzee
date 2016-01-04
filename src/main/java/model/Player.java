package model;


/**
 * Created by otto on 2015-11-18.
 */

public class Player {

    private String playerName;
    private ScoreSheet scoreSheet;

    public Player(String playerName) {
        this.playerName = playerName;
        scoreSheet = new ScoreSheet();
    }

    public String getName() {
        return playerName;
    }
    public void setName(String name) {
        playerName = name;
    }

}
