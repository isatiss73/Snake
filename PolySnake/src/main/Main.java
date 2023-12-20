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
	
	@Override
	public void start(Stage stage) throws Exception
	{
		stage.setTitle("PolySnake");
		FXMLLoader loader = FXLoad("Scene_menu");
        Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
}
