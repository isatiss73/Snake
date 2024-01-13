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

/**
 * main application class
 */
public class Main extends Application {    
	//public static Game game;
	public static final String scenePath = "scenes/";
	
	/**
	 * create a FXMLLoader without headache
	 * @param fileName just the name of the file without extension
	 * @return the beautiful FXMLLoader if you are lucky
	 * @throws MalformedURLException a huge error if you are unlucky
	 */
	public static FXMLLoader FXLoad(String fileName) throws MalformedURLException {
		String path = scenePath + fileName + ".fxml";
		return new FXMLLoader(new File(path).toURL());
	}
	
	/**
	 * main application entry point
	 * @param args program's arguments (nothing special here)
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * standard application start method
	 * @param stage application stage
	 * @throws Exception we don't really know
	 */
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("PolySnake");
		FXMLLoader loader = FXLoad("Scene_menu");
        Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		
		GameControler controler = Game.getInstance().getControler();
		// controler.addGuest("192.168.13.228", 9001, 9002);
		// controler.addGuest("localhost", 8080, 8080);
		// controler.startThreads();
		scene.setOnKeyReleased(event -> controler.keyReleased(event));
		
		stage.setOnCloseRequest(event -> {
			controler.stopThreads();
			stage.close();
		});
		
		stage.show();
		
	}
}
