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

		if (game != null && game.getMap() != null) {
			int mapWidth = game.getMap().length;
	        int mapHeight = game.getMap()[0].length;
			
		    for (int x = 0; x < mapWidth; x++) {
		        for (int y = 0; y < mapHeight; y++) {
		        	if (x >= 0 && x < mapWidth && y >= 0 && y < mapHeight) {
		        			
		        		/*        	
			        	if (game.getPlayer(0).isLiving() == true && game.getMap()[x][y].getDetail() == 0 && game.getPlayer(0).getTail()[0] == x && game.getPlayer(0).getTail()[1] == y){
			        		//drawFloorImage(gc, x * 66, y * 66);
	                        drawPlayerImage(game, gc, x, y);
			            }*/
		        		
		        		if (game.getPlayer(0).getTail()[0] == x && game.getPlayer(0).getTail()[1] == y){
			        		//drawFloorImage(gc, x * 66, y * 66);
	                        drawPlayerImage(game, gc, x, y);
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
	}   

	
    private void drawPlayerImage(Game game, GraphicsContext gc, double x, double y) {
    	
    	int tailleSnake0 = game.getPlayer(0).getLength();
    	int xdir = game.getMap()[(int)x][(int)y].getxdir();
    	int ydir = game.getMap()[(int)x][(int)y].getydir();
    	
    	int xCase=(int)x;
    	int yCase=(int)y;
    	

    	
    	for (int i=0; i<tailleSnake0; i++) {
        	
    		if (i==0) {
    			
    			int rot = 0;
    			
    			drawFloorImage(gc, x*66, y*66);
    			Image playerTailImage = new Image("file:/C:/Users/mehdi/git/Snake/PolySnake/images/tail" + skinPlayer +".png"); 
    			
    			if (game.getMap()[xCase][yCase].getxdir() == -1) {
    	        	rot = 270;
    	        }
    	        else if (game.getMap()[xCase][yCase].getxdir() == 1) {
    	        	rot = 90;
    	        }
    	        else if (game.getMap()[xCase][yCase].getydir() == -1) {
    	        	rot = 180;
    	        }
    	        else if (game.getMap()[xCase][yCase].getydir() == 1) {
    	        	rot = 0;
    	        }
    			
    	        gc.drawImage(rotateImage(playerTailImage, rot), x*66, y*66);
    		}
    		else if (i>0 && i<tailleSnake0-1) {
    			xdir = game.getMap()[xCase][yCase].getxdir();
    			ydir = game.getMap()[xCase][yCase].getydir();
    			    		
    			if (xCase>=0 && xdir>=0) xCase=xCase+xdir;
    			if (yCase >=0 && ydir >=0) yCase=yCase + ydir;
    
    			drawFloorImage(gc, xCase*66, yCase*66);
    			 
    			//Pour corps droit horizontal
    			
    			if (xCase+xdir>=0 && xCase+xdir<=game.getMap().length && xCase-xdir>=0 && xCase-xdir<=game.getMap().length ) {
	    			if((ydir == 0 && game.getMap()[xCase+xdir][yCase].getEntity() == Cell.PLAYER && game.getMap()[xCase-xdir][yCase].getEntity() == Cell.PLAYER)) {
	    				Image playerBodyImage = new Image("file:/C:/Users/mehdi/git/Snake/PolySnake/images/perso" + skinPlayer +"body.png"); 
	        	        gc.drawImage(rotateImage(playerBodyImage, 90) , xCase*66, yCase*66);
	    			}
	    			//Pour corps droit vertical
	    			
	    			else if((xdir == 0 && game.getMap()[xCase][yCase+ydir].getEntity() == Cell.PLAYER && game.getMap()[xCase][yCase-ydir].getEntity() == Cell.PLAYER)) {
	    				Image playerBodyImage = new Image("file:/C:/Users/mehdi/git/Snake/PolySnake/images/perso" + skinPlayer +"body.png"); 
	        	        gc.drawImage(playerBodyImage, xCase*66, yCase*66);
	    			}
	    			//Pour corps coin
	    			else {
	    				Image playerBodyImage = new Image("file:/C:/Users/mehdi/git/Snake/PolySnake/images/perso" + skinPlayer +"coin.png"); 
	        	        
	    				//On teste chaque coin possible
	    				
	    				gc.drawImage(playerBodyImage, xCase*66, yCase*66);  
	    			}
    			}
			}
    		else {
    			
    			xdir = game.getMap()[xCase][yCase].getxdir();
    			ydir = game.getMap()[xCase][yCase].getydir();
    			
    			int rot = 0;
    			    			
    			xCase=xCase+xdir;
    			yCase=yCase + ydir;
    			
    			drawFloorImage(gc, xCase*66, yCase*66);
    	        Image playerBodyImage = new Image("file:/C:/Users/mehdi/git/Snake/PolySnake/images/perso" + skinPlayer +".png"); 
    	        
    	        if (game.getMap()[xCase][yCase].getxdir() == 1) {
    	        	rot = 270;
    	        }
    	        else if (game.getMap()[xCase][yCase].getxdir() == -1) {
    	        	rot = 90;
    	        }
    	        else if (game.getMap()[xCase][yCase].getydir() == 1) {
    	        	rot = 180;
    	        }
    	        else if (game.getMap()[xCase][yCase].getydir() == -1) {
    	        	rot = 0;
    	        }
    	        
    	        gc.drawImage(rotateImage(playerBodyImage, rot), xCase*66, yCase*66);
    		}    		
    	}
    }
    
	private void drawPlayer2Image(GraphicsContext gc, double x, double y) {
        Image playerImage = new Image("file:/C:/Users/mehdi/git/Snake/PolySnake/images/persoj2.png");
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
    
    public static WritableImage rotateImage(Image playerImage, int angle) {
        int width = (int) playerImage.getWidth();
        int height = (int) playerImage.getHeight();

        PixelReader pixelReader = playerImage.getPixelReader();
        WritableImage rotatedImage = new WritableImage(width, height);
        PixelWriter pixelWriter = rotatedImage.getPixelWriter();

        switch (angle) {
            case 90:
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        pixelWriter.setArgb(i, width - j - 1, pixelReader.getArgb(j, i));
                    }
                }
                break;

            case 180:
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        pixelWriter.setArgb(width - j - 1, height - i - 1, pixelReader.getArgb(j, i));
                    }
                }
                break;

            case 270:
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        pixelWriter.setArgb(height - i - 1, j, pixelReader.getArgb(j, i));
                    }
                }
                break;
            case 0 :
            	break;
        }

        return rotatedImage;
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