package controller;

import model.Dice;
import model.Game;
import model.Player;
import view.Console;
import view.IView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;


/**
 * Created by otto on 2015-11-18.
 */
public class GameController {

    private Game game;
    private Console view;

    private boolean isGameOver = false;
    private boolean startedPlaying = false;
    ArrayList<Dice> rolledDice = new ArrayList<Dice>();
    ArrayList<Dice> heldDices = new ArrayList<Dice>();

    private final boolean isComputer = true;
    private final boolean isHuman = !isComputer;

    public boolean Play(Game a_game, Console a_view) {
        game = a_game;
        view = a_view;

        if (game.IsGameOver())
        {
            for (Player p : game.getPlayers()) {
                p.getScoreSheet().scoreTotalPoints();
            }
            view.DisplayGameOver(game.getWinner());
            return isGameOver;
        }

        if(!startedPlaying) {
            view.WelcomeMessage();
            String input = view.GetInput();
            IView.InputValue inputValue = view.CheckInput(input);

            if(inputValue == IView.InputValue.LOAD){
                String gameName = view.askForSaveName();
                this.game = game.getSaveGame().loadGame(new Game(), gameName);
            }
            else if (inputValue == IView.InputValue.NEW) {
                int players = 0;

                while (!game.PlayersNumberIsValid(players))
                    players = view.GetHumanPlayersNumber();
                game.CreatePlayer(players, isHuman);

                String[] playerNames = view.PlayersInfo(game.GetPlayersNumber());
                game.setPlayerNames(playerNames);

                int computerPlayers = view.GetComputerPlayersNumber(players);
                game.CreatePlayer(computerPlayers, isComputer);

                startedPlaying = true;
            }
        }

        Player player = game.StartRound();
        if (player.isBot()){

        }
        else{ playRound(player); }
        return !isGameOver;
    }

    public void playRound(Player player) {

        for(int i = 0; i < 3; i++) {
            view.DisplayPlayerOptions(player);
            String input = view.GetInput();
            IView.InputValue inputValue = view.CheckInput(input);

            if (inputValue == IView.InputValue.ROLL) {
                rolledDice = ((game.rollDice(player)));
                view.DisplayDiceRoll(rolledDice);

                if (view.AskToHold()) {
                    int[] diceToHold = view.DisplayHold();
                    heldDices.addAll(game.holdDice(player, diceToHold, rolledDice));
                    view.ShowHeldDice(heldDices);
                }
            } else if (inputValue == IView.InputValue.SKIP) {
                break;
            } else if (inputValue == IView.InputValue.SCORE) {
                view.ShowHeldDice(heldDices);
                view.DisplayScoreSheet(player, game.getGameMode(), game.getCurrentRound());


                HashSet<Dice> allDices = new HashSet<Dice>();
                allDices.addAll(rolledDice);
                allDices.addAll(heldDices);
                ArrayList<Dice> all = new ArrayList<Dice>();
                all.addAll(allDices);
                int choice = 0;
                do {
                    choice = Integer.valueOf(view.GetInput());
                }
                while (player.getScoreSheet().scorePoints(choice, all));
                view.PrintScoreSheet(player);
                heldDices.clear();
                player.resetTurnScore();
                playRound(game.StartRound());
            } else if (inputValue == IView.InputValue.QUIT) {
                if (view.DisplaySave()) {
                    String name = view.askForSaveName();
                    game.getSaveGame().saveGame(game, name);
                }
                view.DisplayBye();
                System.exit(0);
            } else if (inputValue == IView.InputValue.NOTHING || inputValue != IView.InputValue.QUIT) {
                playRound(player);
            }
        }
    }
}