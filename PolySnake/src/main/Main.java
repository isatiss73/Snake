package main;

import java.io.File;
import java.net.MalformedURLException;

import controler.GameControler;
import controler.GameRunnable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Cell;
import model.Game;


/**
 * main application class
 */
public class Main extends Application
{    
	public static Game game;
	//public static String scenePathQuentin = "/home/quentin/git/Snake/PolySnake/scenes/";
	public static String scenePath = "scenes/";
	
	/**
	 * create a FXMLLoader without headache
	 * @param fileName just the name of the file without extension
	 * @return the beautiful FXMLLoader if you are lucky
	 * @throws MalformedURLException a huge error if you are unlucky
	 */
	public static FXMLLoader FXLoad(String fileName) throws MalformedURLException
	{
		String path = scenePath + fileName + ".fxml";
		return new FXMLLoader(new File(path).toURL());
	}
	
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
		
		// StackPane root = new StackPane();
		FXMLLoader loader = FXLoad("Scene_menu");
        Parent root = loader.load();
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
