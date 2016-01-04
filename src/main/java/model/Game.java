package model;

import rules.IGameMode;

import java.util.ArrayList;

/**
 * Created by otto on 2015-11-18.
 */
public class Game {

    private IGameMode gameMode;
    private int playerTurn = 0;
    private ArrayList<Player> players;

    public Game() {
        players = new ArrayList<Player>();

    }
}
