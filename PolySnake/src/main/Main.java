package main;

import controler.GameControler;
import controler.GameRunnable;
import javafx.application.Application;
import javafx.scene.Scene;
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
		launch(args);
		System.out.println("- = MAIN THREAD END = -");
	}
	
	@Override
	public void start(Stage stage) throws Exception
	{
		game = Game.getInstance();
		game.reset(8, 8, 2);
		System.out.println(game.smoothString());
		game.createSnake(0, 2, 2, 3, 1, 0);
		game.createSnake(1, 2, 4, 3, 1, 0);
		System.out.println(game);
		game.createApple(Cell.A_LENGTH_ONLY);
		
		stage.setTitle("PolySnake");
		
		StackPane root = new StackPane();
		/*FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scene_menu.fxml"));
        Parent root = loader.load();*/
		Scene scene = new Scene(root);
		stage.setScene(scene);
		
		GameControler gameControler = new GameControler(0, 1);
		
		scene.setOnKeyReleased(event -> gameControler.keyReleased(event));
		
		Thread gameThread = new Thread(new GameRunnable());
		gameThread.start();
		stage.setOnCloseRequest(event -> {stage.close(); gameThread.interrupt();});
		stage.show();
	}
}
