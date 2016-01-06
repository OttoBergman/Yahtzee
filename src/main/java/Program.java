import controller.GameController;
import model.Game;
import view.Console;

import java.io.IOException;


/**
 * Created by Otto on 01/05/2016.
 */
public class Program {
    public static void main(String[] args) {
        Game game = new Game();
        Console console = new Console();
        GameController gameController = new GameController();

        while(gameController.Play(game, console));
    }
}