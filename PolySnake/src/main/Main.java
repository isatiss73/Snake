package main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * main application class
 */
public class Main extends Application
{
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
		stage.setTitle("PolySnake");
		Button button = new Button();
		button.setText("Play in terminal");
		button.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				System.out.println("Play here bro");
			}
		});
		StackPane root = new StackPane();
		root.getChildren().add(button);
		stage.setScene(new Scene(root, 300, 250));
		stage.show();
	}
}
