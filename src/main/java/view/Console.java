package view;


import model.Dice;
import model.Player;
import model.ScoreSheet;
import rules.IGameMode;
import rules.NormalRules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by otto on 2016-01-04.
 */
public class Console implements IView {

    private ArrayList<String> scoreOptions = new ArrayList<String>(Arrays.asList("Ones (1)", "Twos (2)", "Threes (3)",
            "Fours (4)", "Fives (5)", "Sixes (6)", "Three of a kind (7)", "Four of a kind (8)", "Full house (9)",
            "Small straight (10)", "Large straight (11)", "Yachtzee (12)", "Chance (13)"));

    Scanner scan;
    BufferedReader input;
    private int rollCounter;


    public  Console(){
        input = new BufferedReader(new InputStreamReader(System.in));
        scan = new Scanner(System.in);
    }

    public void WelcomeMessage() {
        System.out.println("WELCOME TO DA YAHTZY CLUB");
        System.out.println("Would you like to start a new game or load one? (n for new game, l for load game).");
    }

    public int GetHumanPlayersNumber(){
        System.out.println("How many human players are you? (1 - 5 players)");
        return scan.nextInt();
    }

    public int GetComputerPlayersNumber(int humanPlayers){
        System.out.println("Cool. Now, how many computer players would you like to play? You can add " + (5 - humanPlayers));
        return scan.nextInt();
    }

    /*
    Takes from the users the name of each player (according to the number of players they have chosen).
     */
    public String[] PlayersInfo(int playerNumber){

        String[] names = new String[playerNumber];

        for(int i = 1; i <= playerNumber; i++) {
            System.out.println("What is Player " + i + "'s " + "name?");
            try {
                names[i - 1] = input.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return names;
    }

    public void DisplayPlayerOptions(Player player){
        rollCounter++;
        if (rollCounter == 4){
            rollCounter = 0;
            System.out.println("That was your last roll. " + player.getName() + "Type 's' to Score or 'q' to Quit/Save");
        }
        else
            System.out.println( player.getName() + ": Type 'r' to roll, 's' to Score, 'z' to Skip your turn or 'q' to Quit/Save");
    }


    public void DisplayScoreSheet( Player player) {
        System.out.println("Which catagory do you wish to place your score?");
        int i = 0;
        for (ScoreSheet.value v : ScoreSheet.value.values()) {

            if (v == ScoreSheet.value.BONUS || v == ScoreSheet.value.SUM || v == ScoreSheet.value.TOTAL_SCORE) {
                continue;
            }
            int value = player.getScoreSheet().getScoreValue(v);
            if (value == 0) {
                System.out.println(scoreOptions.get(i));
            }
            i++;
        }
    }


    public String GetInput()
    {
        String choice = "" ;
        try {
            choice = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return choice;
    }

    public InputValue CheckInput(String input) {

        if (input.equals("r"))
        {
            return InputValue.Roll;
        }
        else if (input.equals("s"))
        {
            return InputValue.Score;
        }
        else if (input.equals("l")){
            return InputValue.Load;
        }
        else if(input.equals("z")){
            return InputValue.Skip;
        }
        else if (input.equals("q"))
        {
            return InputValue.Quit;
        }
        else if (input.equals("n"))
        {
            return InputValue.New;
        }
        return InputValue.Nothing;
    }


    public int[] DisplayHold() {
        int[] diceToHold = new int[0];
        System.out.println("Which die would you like to hold?");
        System.out.println("Press 1-5 according to which die in the sequence you want to Hold separated be spaces.");
        try {
            String userChoice = input.readLine().replaceAll("\\s+","");
            int[] temp = new int[userChoice.length()];

            for (int i = 0; i < userChoice.length(); i++)
                temp[i] = Character.getNumericValue(userChoice.charAt(i) - 1);
            Arrays.sort(temp);
            diceToHold = temp;
        } catch (IOException e) {
            e.printStackTrace();

        }
        return diceToHold;
        // returns an array of the slots of which dice need to be frozen
        //need to freeze the slots in the roll due to the choice
    }


    public void DisplayDiceRoll(ArrayList<Dice> dice) {
        System.out.println("the dice roll is "+ toString(dice));

    }

    /*
    Asks the user whether s/he wants to save the game before exiting.
     */
    public boolean DisplaySave() {
        System.out.println("Would you like to save the game before leaving? (Y) for Yes, any other key for (no).");
        String answer = " ";
        try {
            answer = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer.equalsIgnoreCase("Y");
    }

    public void DisplayGameOver(Player player){
        System.out.println("The winner is" + player);
    }

    public boolean AskToHold(){
        System.out.println("Do you want to hold any of the dice? (Y) for Yes, any other key for (no).");
        String answer = null;
        try {
            answer = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer.equalsIgnoreCase("y");
    }

    public void ShowHeldDice(ArrayList<Dice> dice){
        System.out.println("Your held dice " + toString(dice));
    }

    private String toString(ArrayList<Dice> array) {
        StringBuilder sb = new StringBuilder();
        for(Dice i : array){
            sb.append(i.getDiceValue()).append(" ");
        }
        return sb.toString();
    }

    public void DisplayBye(){
        System.out.println("Good bye!");
    }

    public void PrintScoreSheet(Player player) {

        for (ScoreSheet.value v : ScoreSheet.value.values()) {
            System.out.println(v + " " + player.getScoreSheet().getScoreValue(v));
        }
    }
}
