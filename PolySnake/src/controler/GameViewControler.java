package controler;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Cell;
import model.Game;

public class GameViewControler implements Initializable {
	public static Game game;
	
	public int nbColonnes = 1;
	
	private int nbLignes = 1;
	
	public Canvas canvas;
	
	public GraphicsContext gc;
	public AnimationTimer gameLoop;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}
	
	public GameViewControler() {
	}
	
	private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Update game state and redraw

            	if (game != null && gc != null) {
                    updateGame();
                    drawGame();
            		//loadTiles(game, gc);
            	}
            }
        };
        gameLoop.start();
    }
	
    public void initializeCanvas(Game game, Stage stage) {
    	
		nbColonnes = game.getMap().length;
		nbLignes = game.getMap()[0].length;
    	
		System.out.println(nbColonnes + " " + nbLignes);
    	
    	 // Create the Canvas
        canvas = new Canvas(nbColonnes*67, nbLignes*67);
        // Get the graphics context of the canvas
        gc = canvas.getGraphicsContext2D();
        
        // Create the Pane
        Pane root = new Pane();
        
        // Add the Canvas to the Pane
        root.getChildren().add(canvas);
        
        // Create the Scene
        Scene scene = new Scene(root);
        
        GameControler gameControler = new GameControler(0, 1);
        
        scene.setOnKeyReleased(event -> gameControler.keyReleased(event));
        // Add the Scene to the Stage
        stage.setScene(scene);
        // Set the Title of the Stage
        stage.setTitle("Snake local");
        // Display the Stage
        stage.show(); 
        startGameLoop();

		loadTiles(game, gc);
        drawGame();
    }
	
	public GameViewControler(Game game, Stage stage) {
		this.game=game;
		
		nbColonnes = game.getMap().length;
		nbLignes = game.getMap()[0].length;
        
		System.out.println(nbColonnes + " " + nbLignes);
   
		initializeCanvas(game, stage);
		

		
		if (game!= null) {
			//shapeMap(game);
		}

	}


	public void loadTiles(Game game, GraphicsContext gc) {

		if (game != null) {
		    for (int x = 0; x < game.getMap().length; x++) {
		        for (int y = 0; y < game.getMap()[0].length; y++) {
		        	
		            if (game.getMap()[x][y].getEntity() == Cell.PLAYER){
                        drawPlayerImage(gc, x * 67, y * 67);
		            }
		            if (game.getMap()[x][y].getEntity() == Cell.WALL){
                        drawWallImage(gc, x * 67, y * 67);
		            }
		            if (game.getMap()[x][y].getEntity() == Cell.APPLE){
		            	drawAppleImage(gc, x * 67, y * 67);
		            }
		            if (game.getMap()[x][y].getEntity() == Cell.AIR){
		            	drawFloorImage(gc, x * 67, y * 67);
		            }
		           
		        }
		    }
		}
	}   
	
    private void drawPlayerImage(GraphicsContext gc, double x, double y) {
        Image playerImage = new Image("file:/C:/Users/mehdi/git/Snake/PolySnake/images/perso0.png");
        gc.drawImage(playerImage, x, y);
    }
    
    private void drawWallImage(GraphicsContext gc, double x, double y) {
        Image wallImage = new Image("file:/C:/Users/mehdi/git/Snake/PolySnake/images/map0.png");
        gc.drawImage(wallImage, x, y);
    }
    
    private void drawAppleImage(GraphicsContext gc, double x, double y) {
        Image wallImage = new Image("file:/C:/Users/mehdi/git/Snake/PolySnake/images/pomme0.png");
        gc.drawImage(wallImage, x, y);
    }
    
    private void drawFloorImage(GraphicsContext gc, double x, double y) {
        Image floorImage = new Image("file:/C:/Users/mehdi/git/Snake/PolySnake/images/Sol.png");
        gc.drawImage(floorImage, x, y);
    }
    
    private void updateGame() {
        // Mettez à jour l'état du jeu ici (par exemple, déplacez le joueur, vérifiez les collisions, etc.)
        // game.update(); // Vous devez implémenter la méthode update dans votre classe Game
    }

    private void drawGame() {
        // Effacez le canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Dessinez les entités du jeu
        loadTiles(game, gc);
    }
	
}