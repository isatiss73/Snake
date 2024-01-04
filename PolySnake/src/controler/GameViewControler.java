package controler;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
	
	public static final String mehdiPath = "file:/C:/Users/mehdi/git/Snake/PolySnake/images/";
	public static final String quentinPath = "file:/home/quentin/git/Snake/PolySnake/images/";
	public static final String stdPath = "file:/" + System.getProperty("user.dir") + "/images/";
	public static final String imgPath = mehdiPath;
	
	public static Game game;
	
	private Stage stage;
	
	private Thread gameThread;
	
	private GameControler gameControler;
	
	private Pane root;
			
	public int nbColonnes = 1;
	
	private int nbLignes = 1;
	
	private int skinMap;
	
	private int skinPlayer0;
	
	private int skinPlayer1=1;
	
	private int skinPomme;
	
	public Canvas canvas;
	
	private boolean isCanvasInitialized = false;
	
	public GraphicsContext gc;
	public AnimationTimer gameLoop;
	
	private Image wallImage, appleImage, floorImage;
	private Image tail, body, corner, head;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// about the 'Initializable' heritage you know
	}
	
	/**
	 * seems to be necessary
	 */
	public GameViewControler() {
		// nothing
	}
	
	/**
	 * set the texture id of the differents elements
	 * @param skinMap id of the map theme
	 * @param skinPlayer id of the player skin
	 * @param skinPomme id of the apple skin
	 */
	public void setGameRules(int skinMap, int skinPlayer, int skinPomme) {
		this.skinMap = skinMap;
		this.skinPlayer0 = skinPlayer;
		this.skinPomme = skinPomme;
		
		wallImage = new Image(imgPath + "map" + skinMap + ".png");
		appleImage = new Image(imgPath + "pomme" + skinPomme + ".png");
		floorImage = new Image(imgPath + "Sol.png");
	}
	
	/**
	 * Mehdi please describe it
	 */
	private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Update game state and redraw
            	if (game != null && gc != null) {
                    drawGame();
                    checkGameOver();
            	}
            }
        };
        gameLoop.start();
    }
	
	/**
	 * initialize graphic game map canvas and that things you know
	 * @param game game model
	 * @param stage window stage
	 */
    public void initializeCanvas(Game game, Stage stage) {
    	
    	if (!isCanvasInitialized) {
    	
			nbColonnes = game.getMap().length;
			nbLignes = game.getMap()[0].length;
	    	    	
	    	 // Create the Canvas
	        canvas = new Canvas(nbColonnes*66, nbLignes*66);
	        // Get the graphics context of the canvas
	        gc = canvas.getGraphicsContext2D();
	        
	        // Create the Pane
	        root = new Pane();
	        
	        // Add the Canvas to the Pane
	        root.getChildren().add(canvas);
	        
	        isCanvasInitialized = true;
	        
	        // Create the Scene
	        Scene scene = new Scene(root);
	        gameControler = game.getControler();
	        scene.setOnKeyReleased(event -> gameControler.keyReleased(event));
	        stage.setScene(scene);
	        stage.setTitle("Jeu local");
	        gameControler.startThreads();
	        stage.show();
	        shapeMapImage(gc, nbColonnes, skinPlayer0);
	        startGameLoop();
			loadTiles(game, gc);
	        drawGame();
    	}
    }
	
    /**
     * global constructor
     * @param game game model
     * @param stage window stage
     * @param gameThread game thread
     */
	public GameViewControler(Game game, Stage stage, Thread gameThread) {
		this.game=game;
		this.stage=stage;
		this.gameThread=gameThread;
		
		nbColonnes = game.getMap().length;
		nbLignes = game.getMap()[0].length;
   
		initializeCanvas(game, stage);
	}

	/**
	 * draw images for every cell
	 * @param game model containing the map informations
	 * @param gc graphic context on wich we draw
	 */
	public void loadTiles(Game game, GraphicsContext gc) {

		// we check that the map is loaded to read it
		if (game != null && game.getMap() != null) {
			int mapWidth = game.getMap().length;
	        int mapHeight = game.getMap()[0].length;
			
	        // we cross the map to draw each cell one by one
		    for (int x = 0; x < mapWidth; x++) {
		        for (int y = 0; y < mapHeight; y++) {
		        	if (x >= 0 && x < mapWidth && y >= 0 && y < mapHeight) {
		        		if (game.getPlayer(0).isLiving() == true && game.getPlayer(0).getTail()[0] == x && game.getPlayer(0).getTail()[1] == y){
	                        drawPlayerImage(game, gc, x, y, 0);
			            }
			        	
			            if (game.getPlayer(1).isLiving() == true && game.getPlayer(1).getTail()[0] == x && game.getPlayer(1).getTail()[1] == y){
	                        drawPlayerImage(game, gc, x, y, 1);
			            }
			            if (game.getMap()[x][y].getEntity() == Cell.WALL){
			            	drawFloorImage(gc, x, y);
	                        drawWallImage(gc, x, y);
			            }
			            else if (game.getMap()[x][y].getEntity() == Cell.APPLE){
			            	drawFloorImage(gc, x, y);
			            	drawAppleImage(gc, x, y);
			            }
			            else if (game.getMap()[x][y].getEntity() == Cell.AIR){
			            	drawFloorImage(gc, x, y);
			            }
		        	}
		        }
		    }
		}
	}   

	/**
	 * draw the entire body of a snake
	 * @param game global game model
	 * @param gc graphic context where we draw
	 * @param x ???
	 * @param y ???
	 * @param who left or right snake
	 */
    private void drawPlayerImage(Game game, GraphicsContext gc, int x, int y, int who) {
 
    	int tailleSnake0 = game.getPlayer(who).getLength(); 	
    	int skinPlayer = 0;
    	
    	// we get the player's skin id to load images
    	if (who == 0) 
    		skinPlayer = skinPlayer0;
    	else if (who == 1) 
    		skinPlayer = skinPlayer1;

    	// we load every image of the snake
		tail = new Image(imgPath + "perso" + skinPlayer + "tail.png"); 
		body = new Image(imgPath + "perso" + skinPlayer +"body.png"); 
		corner = new Image(imgPath + "perso" + skinPlayer +"coin.png"); 
		head = new Image(imgPath + "perso" + skinPlayer +".png"); 
		
		
		// we draw the tail
    	int xCase = x;
    	int yCase = y;
		
		// we define the rotation of the image according to snake's direction
		int rot = 0;
		if (game.getMap()[xCase][yCase].getxdir() == -1) 
			rot = 270;
        else if (game.getMap()[xCase][yCase].getxdir() == 1) 
        	rot = 90;
        else if (game.getMap()[xCase][yCase].getydir() == -1) 
        	rot = 180;
		
		// we draw the tail image with the approriate rotation
		drawFloorImage(gc, x, y);
		drawBodyImage(gc, tail, rot, x, y);
		
		
		// we draw the head
		xCase = game.getPlayer(who).getHead()[0];
		yCase = game.getPlayer(who).getHead()[1];
		
		// we define the rotation of the image according to snake's direction
		rot = 0;
        if (game.getMap()[xCase][yCase].getxdir() == 1) 
        	rot = 270;
        else if (game.getMap()[xCase][yCase].getxdir() == -1) 
        	rot = 90;
        else if (game.getMap()[xCase][yCase].getydir() == 1) 
        	rot = 180;
        
        // we draw the head image with the appropriate direction
        drawFloorImage(gc, xCase, yCase);
        drawBodyImage(gc, head, rot, xCase, yCase);
		
    	
        // we wander the body to draw each cell one by one
    	for (int i = 1; i < tailleSnake0 - 1; i++) {
    		
			int xCaseTail=game.getPlayer(who).getTail()[0];
	    	int yCaseTail=game.getPlayer(who).getTail()[1];

			
			int xdir = game.getMap()[xCaseTail][yCaseTail].getxdir();
			int ydir = game.getMap()[xCaseTail][yCaseTail].getydir();
			
			xCase=xCaseTail+xdir;
			yCase=yCaseTail + ydir;
			
			// Mehdi please explain it
			for (int j = 0; j < tailleSnake0 - 2; j++) {
    			
    			xdir = game.getMap()[xCase][yCase].getxdir();
	    		ydir = game.getMap()[xCase][yCase].getydir();
    				    				
    			if(xCase != game.getPlayer(who).getHead()[0] || yCase != game.getPlayer(who).getHead()[1]) {
    			    			
	    			drawFloorImage(gc, xCase, yCase);
	    			
	    			// not corners
	    			if (xCase+xdir>=0 && xCase+xdir<game.getMap().length && xCase-xdir>=0 && xCase-xdir<game.getMap().length && 
	    				yCase+ydir>=0 && yCase+ydir<game.getMap().length && yCase-ydir>=0 && yCase-ydir<game.getMap().length) 
	    			{
	    				if((ydir == 0 
	    						&& game.getMap()[xCase-xdir][yCase-ydir].getDetail() == who 
	    						&& game.getMap()[xCase-xdir][yCase+ydir].getDetail() == who 
	    						&& game.getMap()[xCase-xdir][yCase-ydir].getxdir() == xdir)) 
	    					drawBodyImage(gc, body, 90, xCase, yCase);
	    				
	    				else if((xdir == 0 
	    						&& game.getMap()[xCase-xdir][yCase-ydir].getDetail() == who 
	    						&& game.getMap()[xCase-xdir][yCase+ydir].getDetail() == who 
	    						&& game.getMap()[xCase-xdir][yCase-ydir].getydir() == ydir)) 
	    					drawBodyImage(gc, body, 0, xCase, yCase);
	    			}
	    			
	    			// corners
	    			if (xCase-1>=0 || yCase-1>=0 || xCase+1<game.getMap().length || yCase+1<game.getMap().length) {

    					if (xCase-1>=0) {
    						if ((game.getMap()[xCase-1][yCase].getxdir() == 1 && ydir==-1 
    								&& game.getMap()[xCase-1][yCase].getDetail() == who 
    								&& (xCase-1 != game.getPlayer(who).getHead()[0] || yCase != game.getPlayer(who).getHead()[1]))) 
		    					drawBodyImage(gc, corner, 180, xCase, yCase);
    						
    						else if (game.getMap()[xCase-1][yCase].getxdir() == 1 && ydir==1 
    								&& game.getMap()[xCase-1][yCase].getDetail() == who 
    								&& (xCase-1 != game.getPlayer(who).getHead()[0] || yCase != game.getPlayer(who).getHead()[1]) ) 
    							drawBodyImage(gc, corner, 270, xCase, yCase);
    					}
    					
    					if (yCase-1>=0) {
    						if (game.getMap()[xCase][yCase-1].getydir() == 1 && xdir==-1 
    								&& game.getMap()[xCase][yCase-1].getDetail() == who 
    								&& (xCase != game.getPlayer(who).getHead()[0] || yCase-1 != game.getPlayer(who).getHead()[1]) ) 
    							drawBodyImage(gc, corner, 180, xCase, yCase);
    						
    						else if (game.getMap()[xCase][yCase-1].getydir() == 1 && xdir==1 
    								&& game.getMap()[xCase][yCase-1].getDetail() == who 
    								&& (xCase != game.getPlayer(who).getHead()[0] || yCase-1 != game.getPlayer(who).getHead()[1])) 
    							drawBodyImage(gc, corner, 90, xCase, yCase);
    					}
    					
    					if (xCase+1<game.getMap().length) {
    						if (game.getMap()[xCase+1][yCase].getxdir() == -1 && ydir==-1 
    								&& game.getMap()[xCase+1][yCase].getDetail() == who 
    								&& (xCase+1 != game.getPlayer(who).getHead()[0] || yCase != game.getPlayer(who).getHead()[1])) 
    							drawBodyImage(gc, corner, 90, xCase, yCase);
    						
    						else if (game.getMap()[xCase+1][yCase].getxdir() == -1 && ydir==1 
    								&& game.getMap()[xCase+1][yCase].getDetail() == who 
    								&& (xCase+1 != game.getPlayer(who).getHead()[0] || yCase != game.getPlayer(who).getHead()[1])) 
    							drawBodyImage(gc, corner, 0, xCase, yCase);
    					}
    					
    					if (yCase+1<game.getMap().length) {
    						if (game.getMap()[xCase][yCase+1].getydir() == -1 && xdir==-1 
    								&& game.getMap()[xCase][yCase+1].getDetail() == who 
    								&& (xCase != game.getPlayer(who).getHead()[0] || yCase+1 != game.getPlayer(who).getHead()[1])) 
    							drawBodyImage(gc, corner, 270, xCase, yCase);
    						
    						else if (game.getMap()[xCase][yCase+1].getydir() == -1 && xdir==1 
    								&& game.getMap()[xCase][yCase+1].getDetail() == who 
    								&& (xCase != game.getPlayer(who).getHead()[0] || yCase+1 != game.getPlayer(who).getHead()[1])) 
    							drawBodyImage(gc, corner, 0, xCase, yCase);
    					}
	    			}
    			}
				// we update xCase and yCase
    			xCase += xdir;
    			yCase += ydir;
			}
    	}
    }
    
    private void drawBodyImage(GraphicsContext gc, Image image, int angle, int x, int y) {
    	gc.drawImage(rotateImage(image, angle), x * 66, y * 66);
    }
    
    private void drawWallImage(GraphicsContext gc, int x, int y) {
        gc.drawImage(wallImage, x * 66, y * 66);
    }
    
    private void drawAppleImage(GraphicsContext gc, int x, int y) {
        gc.drawImage(appleImage, x * 66, y * 66);
    }
    
    private void drawFloorImage(GraphicsContext gc, int x, int y) {
        gc.drawImage(floorImage, x * 66, y * 66);
    }
    
    private void shapeMapImage(GraphicsContext gc, int x, int y) {
    	for (int i = 0; i<nbColonnes; i++) {
        	for (int j=0; j<nbLignes; j++) {
        		drawFloorImage(gc, i, j);
        	}
        }
    }
    
    /**
     * create a rotated version of an image
     * @param playerImage base image
     * @param angle rotation angle
     * @return rotated image
     */
    public static Image rotateImage(Image playerImage, int angle) {
    	
    	// we don't change the image if the is no rotation
    	if (angle == 0)
    		return playerImage;
    	
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

    /**
     * draw the game map
     */
    private void drawGame() {
    	gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    	
        // Dessiner les entités du jeu
        loadTiles(game, gc);
    }
	
    /**
     * return to menu if the game is over
     */
    private void checkGameOver() {
        if (game.getLivingNumber() == 0) {
        	
        	if (gameThread != null && gameThread.isAlive()) {
                gameThread.interrupt();
            }
        	
        	if (gameLoop != null) {
                gameLoop.stop();
            }
        	
        	gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            root.getChildren().remove(canvas);
        	gameControler.stopThreads();
            
            try {               
            	// Load the main menu FXML file
            	FXMLLoader loader = new FXMLLoader(new File("scenes/Scene_menu.fxml").toURL());
    	    	Parent menu = loader.load();
                
    	    	Scene menuScene = new Scene(menu);
    	    
                stage.setScene(menuScene);
                stage.setTitle("Main Menu");
                stage.show();
                
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}