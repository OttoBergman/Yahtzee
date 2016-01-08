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

            if(inputValue == IView.InputValue.Load){
                this.game = game.getSaveGame().loadGame(new Game());
            }
            else if (inputValue == IView.InputValue.New) {
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

            if (inputValue == IView.InputValue.Roll) {
                rolledDice = ((game.rollDice(player)));
                view.DisplayDiceRoll(rolledDice);

                if (view.AskToHold()) {
                    int[] diceToHold = view.DisplayHold();
                    heldDices.addAll(game.holdDice(player, diceToHold, rolledDice));
                    view.ShowHeldDice(heldDices);
                }
            } else if (inputValue == IView.InputValue.Skip) {
                break;
            } else if (inputValue == IView.InputValue.Score) {
                view.ShowHeldDice(heldDices);
                view.DisplayScoreSheet(player);

                int choice = Integer.valueOf(view.GetInput());
                HashSet<Dice> allDices = new HashSet<Dice>();
                allDices.addAll(rolledDice);
                allDices.addAll(heldDices);
                ArrayList<Dice> all = new ArrayList<Dice>();
                all.addAll(allDices);
                player.getScoreSheet().scorePoints(choice, all);
                view.PrintScoreSheet(player);
                heldDices.clear();
                player.resetTurnScore();
                playRound(game.StartRound());
            } else if (inputValue == IView.InputValue.Quit) {
                if (view.DisplaySave()) {
                    game.getSaveGame().saveGame(game);
                }
                view.DisplayBye();
                System.exit(0);
            } else if (inputValue == IView.InputValue.Nothing || inputValue != IView.InputValue.Quit) {
                playRound(player);
            }
        }
    }
}