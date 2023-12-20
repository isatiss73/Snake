package controler;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Cell;
import model.Game;

public class GameViewControler implements Initializable {
	public static Game game;
	
	public int nbColonnes = 1;
	
	private int nbLignes = 1;
	
	private int skinMap;
	
	private int skinPlayer;
	
	private int skinPomme;
	
	public Canvas canvas;
	
	public GraphicsContext gc;
	public AnimationTimer gameLoop;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}
	
	public GameViewControler() {
	}
	
	public void setGameRules(int skinMap, int skinPlayer, int skinPomme) {
		this.skinMap = skinMap;
		this.skinPlayer = skinPlayer;
		this.skinPomme = skinPomme;
	}
	
	private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Update game state and redraw
            	if (game != null && gc != null) {
                    updateGame();
                    drawGame();
            	}
            }
        };
        gameLoop.start();
    }
	
    public void initializeCanvas(Game game, Stage stage) {
    	
		this.nbColonnes = game.getMap().length;
		this.nbLignes = game.getMap()[0].length;
    	    	
    	 // Create the Canvas
        canvas = new Canvas(nbColonnes*66, nbLignes*66);
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
        stage.setScene(scene);
        stage.setTitle("Jeu local");
        stage.show(); 
        
        shapeMapImage(gc, nbColonnes, skinPlayer);
        startGameLoop();
		loadTiles(game, gc);
        drawGame();
    }
	
	public GameViewControler(Game game, Stage stage) {
		this.game=game;
			
		nbColonnes = game.getMap().length;
		nbLignes = game.getMap()[0].length;
   
		initializeCanvas(game, stage);
	}


	public void loadTiles(Game game, GraphicsContext gc) {

		if (game != null) {
		    for (int x = 0; x < game.getMap().length; x++) {
		        for (int y = 0; y < game.getMap()[0].length; y++) {
		        	/*
		            if (game.getMap()[x][y].getEntity() == Cell.PLAYER){
                        drawPlayerImage(gc, x * 66, y * 66);
		            }*/
		        	
		        	if (game.getMap()[x][y].getDetail() == 0){
		        		drawFloorImage(gc, x * 66, y * 66);
                        drawPlayerImage(gc, x * 66, y * 66);
		            }
		            if (game.getMap()[x][y].getDetail() == 1){
		            	drawFloorImage(gc, x * 66, y * 66);
                        drawPlayer2Image(gc, x * 66, y * 66);
		            }
		            if (game.getMap()[x][y].getEntity() == Cell.WALL){
		            	drawFloorImage(gc, x * 66, y * 66);
                        drawWallImage(gc, x * 66, y * 66);
		            }
		            if (game.getMap()[x][y].getEntity() == Cell.APPLE){
		            	drawFloorImage(gc, x * 66, y * 66);
		            	drawAppleImage(gc, x * 66, y * 66);
		            }
		            if (game.getMap()[x][y].getEntity() == Cell.AIR){
		            	drawFloorImage(gc, x * 66, y * 66);
		            }
		           
		        }
		    }
		}
	}   

	
    private void drawPlayerImage(GraphicsContext gc, double x, double y) {
        Image playerImage = new Image("file:/C:/Users/mehdi/git/Snake/PolySnake/images/perso" + skinPlayer +".png"); 
        gc.drawImage(playerImage, x, y);
        
        
        //Retourne verticalement test
        
        /*
        PixelReader pixelReader = playerImage.getPixelReader();
        
        WritableImage imageInversee = new WritableImage((int) playerImage.getWidth(), (int) playerImage.getHeight());

        PixelWriter pixelWriter = imageInversee.getPixelWriter();

        for (int i = 0; i < (int) playerImage.getHeight(); i++) {
            for (int j = 0; j < (int) playerImage.getWidth(); j++) {
                pixelWriter.setArgb(j, (int) playerImage.getHeight() - i - 1, pixelReader.getArgb(j, i));
            }
        }
        
        gc.drawImage(imageInversee, x, y);
        */
    }
    
	private void drawPlayer2Image(GraphicsContext gc, double x, double y) {
        Image playerImage = new Image("file:/C:/Users/mehdi/git/Snake/PolySnake/images/perso" + 1 +".png");
        gc.drawImage(playerImage, x, y);
    }
    
    private void drawWallImage(GraphicsContext gc, double x, double y) {
        Image wallImage = new Image("file:/C:/Users/mehdi/git/Snake/PolySnake/images/map" +skinMap +".png");
        gc.drawImage(wallImage, x, y);
    }
    
    private void drawAppleImage(GraphicsContext gc, double x, double y) {
        Image wallImage = new Image("file:/C:/Users/mehdi/git/Snake/PolySnake/images/pomme" + skinPomme + ".png");
        gc.drawImage(wallImage, x, y);
    }
    
    private void drawFloorImage(GraphicsContext gc, double x, double y) {
        Image floorImage = new Image("file:/C:/Users/mehdi/git/Snake/PolySnake/images/Sol.png");
        gc.drawImage(floorImage, x, y);
    }
    
    private void shapeMapImage(GraphicsContext gc, double x, double y) {
    	for (int i = 0; i<nbColonnes; i++) {
        	for (int j=0; j<nbLignes; j++) {
        		drawFloorImage(gc, i*66, j*66);
        	}
        }
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