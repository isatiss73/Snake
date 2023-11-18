package main;

import controler.GameRunnable;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Cell;
import model.Game;


/**
 * main application class
 */
public class Main extends Application
{
	public static Game game;
	
	
	/**
	 * main application entry point
	 * @param args program's arguments (nothing special here)
	 */
	public static void main(String[] args)
	{
		System.out.println("- = MAIN THREAD START = -");
		game = Game.getInstance();
		game.reset(8, 8, 1);
		System.out.println(game);
		game.createSnake(0, 2, 2, 3, 1, 0);
		System.out.println(game);
		game.createApple(Cell.A_LENGTH_ONLY);
		launch(args);
		System.out.println("- = MAIN THREAD END = -");
	}
	
	@Override
	public void start(Stage stage) throws Exception
	{
		stage.setTitle("PolySnake");
		Button button = new Button();
		button.setText("Play in terminal");
		button.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				/*game.evolve();
				System.out.println(game);*/
			}
		});
		StackPane root = new StackPane();
		root.getChildren().add(button);
		Scene scene = new Scene(root, 300, 250);
		stage.setScene(scene);
		scene.setOnKeyReleased(event -> {
			KeyCode keyCode = event.getCode();
            String keyText = keyCode.getName();
            System.out.println("_" + keyText + "_");
            switch(keyText)
            {
            case "D":
            	game.getPlayer(0).setDirection(1, 0);
            	break;
            case "Q":
            	game.getPlayer(0).setDirection(-1, 0);
            	break;
            case "S":
            	game.getPlayer(0).setDirection(0, 1);
            	break;
            case "Z":
            	game.getPlayer(0).setDirection(0, -1);
            	break;
            }
		});
		Thread gameThread = new Thread(new GameRunnable());
		gameThread.start();
		stage.show();
	}
}
