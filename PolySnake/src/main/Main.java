package main;

import java.io.File;
import java.net.MalformedURLException;

import controler.GameControler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Game;
import network.TCPClientMessage;
import network.TCPServerMessage;


/**
 * main application class
 */
public class Main extends Application
{    
	//public static Game game;
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
		launch(args);
	}
	
	/**
	 * standard application start method
	 * @param stage application stage
	 * @throws Exception we don't really know
	 */
	@Override
	public void start(Stage stage) throws Exception
	{
		stage.setTitle("PolySnake");
		FXMLLoader loader = FXLoad("Scene_menu");
        Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		
		GameControler gameControler = new GameControler(0, 1);
		
		scene.setOnKeyReleased(event -> gameControler.keyReleased(event));
		
		Game game = Game.getInstance();
		game.reset(8, 8, 1);
		game.createSnake(0, 2, 1, 3, 1, 0);
		// game.createSnake(1, 2, 3, 3, 1, 0);
		
		// Thread gameThread = new Thread(new GameRunnable());
		// gameThread.start();
		stage.setOnCloseRequest(event -> {
			stage.close();
		});
		
		stage.show();
		
	}
}
