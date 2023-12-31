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
	
	private Pane root;
			
	public int nbColonnes = 1;
	
	private int nbLignes = 1;
	
	private int skinMap;
	
	private int skinPlayer0;
	
	private int skinPlayer1=3;
	
	private int skinPomme;
	
	public Canvas canvas;
	
	private boolean isCanvasInitialized = false;
	
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
		this.skinPlayer0 = skinPlayer;
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
                    checkGameOver();
            	}
            }
        };
        gameLoop.start();
    }
	
    public void initializeCanvas(Game game, Stage stage) {
    	
    	if (!isCanvasInitialized) {
    	
			this.nbColonnes = game.getMap().length;
			this.nbLignes = game.getMap()[0].length;
	    	    	
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
	        GameControler gameControler = new GameControler(0, 1);
	        scene.setOnKeyReleased(event -> gameControler.keyReleased(event));
	        stage.setScene(scene);
	        stage.setTitle("Jeu local");
	        stage.show(); 	        
	        shapeMapImage(gc, nbColonnes, skinPlayer0);
	        startGameLoop();
			loadTiles(game, gc);
	        drawGame();
    	}
    }
	
	public GameViewControler(Game game, Stage stage, Thread gameThread) {
		this.game=game;
		this.stage=stage;
		this.gameThread=gameThread;
		
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
			        if (x>=0 && x<mapWidth && y>=0 && y < mapHeight) {
			        	
		        		if (game.getPlayer(0).isLiving() == true && game.getPlayer(0).getTail()[0] == x && game.getPlayer(0).getTail()[1] == y){
	                        drawPlayerImage(game, gc, x, y, 0);
			            }
			        	
			            if (game.getPlayer(1).isLiving() == true && game.getPlayer(1).getTail()[0] == x && game.getPlayer(1).getTail()[1] == y){
	                        drawPlayerImage(game, gc, x, y, 1);
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

	
    private void drawPlayerImage(Game game, GraphicsContext gc, int x, int y, int who) {
 
    	int tailleSnake0 = game.getPlayer(who).getLength(); 	
    	int skinPlayer = 0;
    	
    	if (who == 0) {skinPlayer = skinPlayer0;}
    	
    	else if (who == 1) {skinPlayer = skinPlayer1;}
    	
    	for (int i=0; i<tailleSnake0; i++) {
    		
    		//La queue
    		if (i==0) {
    			int rot = 0;
    	    	int xCase = x;
    	    	int yCase = y;
    			
    			drawFloorImage(gc, x*66, y*66);
    			Image playerTailImage = new Image(imgPath + "perso" + skinPlayer +"tail.png"); 
    			
    			
    			if (game.getMap()[xCase][yCase].getxdir() == -1) {
    	        	rot = 270;
    	        	gc.drawImage(rotateImage(playerTailImage, rot), x*66, y*66);
    	        }
    	        else if (game.getMap()[xCase][yCase].getxdir() == 1) {
    	        	rot = 90;
    	        	gc.drawImage(rotateImage(playerTailImage, rot), x*66, y*66);
    	        }
    	        else if (game.getMap()[xCase][yCase].getydir() == -1) {
    	        	rot = 180;
    	        	gc.drawImage(rotateImage(playerTailImage, rot), x*66, y*66);
    	        }
    	        else if (game.getMap()[xCase][yCase].getydir() == 1) {
    	        	gc.drawImage(playerTailImage, x*66, y*66);
    	        }
    		}
    		
    		else if (i>0 && i<tailleSnake0-1) {
    			
    			int xCaseTail=game.getPlayer(who).getTail()[0];
    	    	int yCaseTail=game.getPlayer(who).getTail()[1];

    			
    			int xdir = game.getMap()[xCaseTail][yCaseTail].getxdir();
    			int ydir = game.getMap()[xCaseTail][yCaseTail].getydir();
    			
    			int xCase=xCaseTail+xdir;
    			int yCase=yCaseTail + ydir;
    			
    			for (int j=0; j<tailleSnake0-2; j++) {
	    			
	    			xdir = game.getMap()[xCase][yCase].getxdir();
		    		ydir = game.getMap()[xCase][yCase].getydir();
	    				    				
	    			if(xCase != game.getPlayer(who).getHead()[0] || yCase != game.getPlayer(who).getHead()[1]) {
	    			    			
		    			drawFloorImage(gc, xCase*66, yCase*66);
		    						    			
		    			if (xCase+xdir>=0 && xCase+xdir<game.getMap().length && xCase-xdir>=0 && xCase-xdir<game.getMap().length && 
		    				yCase+ydir>=0 && yCase+ydir<game.getMap().length && yCase-ydir>=0 && yCase-ydir<game.getMap().length) 
		    			{
		    				if((ydir == 0 && game.getMap()[xCase-xdir][yCase-ydir].getDetail() == who && game.getMap()[xCase-xdir][yCase+ydir].getDetail() == who && game.getMap()[xCase-xdir][yCase-ydir].getxdir() == xdir)) {
			    				Image playerBodyImage = new Image(imgPath + "perso" + skinPlayer +"body.png"); 
			    				gc.drawImage(rotateImage(playerBodyImage, 90) , xCase*66, yCase*66);
			    			}
		    				
		    				else if((xdir == 0 && game.getMap()[xCase-xdir][yCase-ydir].getDetail() == who && game.getMap()[xCase-xdir][yCase+ydir].getDetail() == who && game.getMap()[xCase-xdir][yCase-ydir].getydir() == ydir)) {
			    				Image playerBodyImage = new Image(imgPath + "perso" + skinPlayer +"body.png"); 
			    				gc.drawImage(playerBodyImage, xCase*66, yCase*66);
			    			}
		    			}
		    				
			    		if (xCase-1>=0 || yCase-1>=0 || xCase+1<game.getMap().length || yCase+1<game.getMap().length) {
			    				Image playerBodyImage = new Image(imgPath + "perso" + skinPlayer +"coin.png"); 

			    				//On teste chaque coin possible
		    					if (xCase-1>=0) {
		    						if ((game.getMap()[xCase-1][yCase].getxdir() == 1 && ydir==-1 && game.getMap()[xCase-1][yCase].getDetail() == who && (xCase-1 != game.getPlayer(who).getHead()[0] || yCase != game.getPlayer(who).getHead()[1]))) {
				    					gc.drawImage(rotateImage(playerBodyImage, 180), xCase*66, yCase*66); 
		    						}
		    						
		    						else if (game.getMap()[xCase-1][yCase].getxdir() == 1 && ydir==1 && game.getMap()[xCase-1][yCase].getDetail() == who && (xCase-1 != game.getPlayer(who).getHead()[0] || yCase != game.getPlayer(who).getHead()[1]) ) {
		    							gc.drawImage(rotateImage(playerBodyImage, 270), xCase*66, yCase*66);
		    						}
		    					}
		    					
		    					if (yCase-1>=0) {
		    						if (game.getMap()[xCase][yCase-1].getydir() == 1 && xdir==-1 && game.getMap()[xCase][yCase-1].getDetail() == who && (xCase != game.getPlayer(who).getHead()[0] || yCase-1 != game.getPlayer(who).getHead()[1]) ) {
		    							gc.drawImage(rotateImage(playerBodyImage, 180), xCase*66, yCase*66);
		    						}
		    						
		    						else if (game.getMap()[xCase][yCase-1].getydir() == 1 && xdir==1 && game.getMap()[xCase][yCase-1].getDetail() == who && (xCase != game.getPlayer(who).getHead()[0] || yCase-1 != game.getPlayer(who).getHead()[1])) {
		    							gc.drawImage(rotateImage(playerBodyImage, 90), xCase*66, yCase*66); 
		    						}
		    					}
		    					
		    					if (xCase+1<game.getMap().length) {
		    						if (game.getMap()[xCase+1][yCase].getxdir() == -1 && ydir==-1 && game.getMap()[xCase+1][yCase].getDetail() == who && (xCase+1 != game.getPlayer(who).getHead()[0] || yCase != game.getPlayer(who).getHead()[1]) ) {
		    							gc.drawImage(rotateImage(playerBodyImage, 90), xCase*66, yCase*66);
		    						}
		    						
		    						else if (game.getMap()[xCase+1][yCase].getxdir() == -1 && ydir==1 && game.getMap()[xCase+1][yCase].getDetail() == who && (xCase+1 != game.getPlayer(who).getHead()[0] || yCase != game.getPlayer(who).getHead()[1])) {
		    							gc.drawImage(playerBodyImage, xCase*66, yCase*66);
		    						}
		    					}
		    					
		    					if (yCase+1<game.getMap().length) {
		    						if (game.getMap()[xCase][yCase+1].getydir() == -1 && xdir==-1 && game.getMap()[xCase][yCase+1].getDetail() == who && (xCase != game.getPlayer(who).getHead()[0] || yCase+1 != game.getPlayer(who).getHead()[1])) {
		    							gc.drawImage(rotateImage(playerBodyImage, 270), xCase*66, yCase*66); 
		    						}
		    						
		    						else if (game.getMap()[xCase][yCase+1].getydir() == -1 && xdir==1 && game.getMap()[xCase][yCase+1].getDetail() == who && (xCase != game.getPlayer(who).getHead()[0] || yCase+1 != game.getPlayer(who).getHead()[1])) {
		    							gc.drawImage(playerBodyImage, xCase*66, yCase*66);
		    						}
		    					}
		    			}	
	    			}
    				//on met à jour xCase et yCase
	    			xCase=xCase+xdir;
	    			yCase=yCase + ydir;
    			}			
			}
    		
    		//La tête
    		else {
    			
    	    	int xdir = game.getMap()[(int)x][(int)y].getxdir();
    	    	int ydir = game.getMap()[(int)x][(int)y].getydir();
    	    	
    	    	int xCase=(int)x;
    	    	int yCase=(int)y;
    			
    			xdir = game.getMap()[game.getPlayer(who).getHead()[0]][game.getPlayer(who).getHead()[1]].getxdir();
    			ydir = game.getMap()[game.getPlayer(who).getHead()[0]][game.getPlayer(who).getHead()[1]].getydir();
    			
    			int rot = 0;
    				    			
    			xCase=game.getPlayer(who).getHead()[0];
    			yCase=game.getPlayer(who).getHead()[1];
    			
    			drawFloorImage(gc, xCase*66, yCase*66);
    	        Image playerBodyImage = new Image(imgPath + "perso" + skinPlayer +".png"); 
    	        	    	        
    	        if (game.getMap()[xCase][yCase].getxdir() == 1) {
    	        	rot = 270;
    	        	gc.drawImage(rotateImage(playerBodyImage, rot), xCase*66, yCase*66);
    	        }
    	        else if (game.getMap()[xCase][yCase].getxdir() == -1) {
    	        	rot = 90;
    	        	gc.drawImage(rotateImage(playerBodyImage, rot), xCase*66, yCase*66);
    	        }
    	        else if (game.getMap()[xCase][yCase].getydir() == 1) {
    	        	rot = 180;
    	        	gc.drawImage(rotateImage(playerBodyImage, rot), xCase*66, yCase*66);
    	        }
    	        else if (game.getMap()[xCase][yCase].getydir() == -1) {
    	        	gc.drawImage(playerBodyImage, xCase*66, yCase*66);
    	        }
    		}    			
	}
    }
    
    private void drawWallImage(GraphicsContext gc, double x, double y) {
        Image wallImage = new Image(imgPath + "map" + skinMap + ".png");
        gc.drawImage(wallImage, x, y);
    }
    
    private void drawAppleImage(GraphicsContext gc, double x, double y) {
        Image wallImage = new Image(imgPath + "pomme" + skinPomme + ".png");
        gc.drawImage(wallImage, x, y);
    }
    
    private void drawFloorImage(GraphicsContext gc, double x, double y) {
        Image floorImage = new Image(imgPath + "Sol.png");
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
    	
    }

    private void drawGame() {
    	gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    	
        // Dessiner les entités du jeu
        loadTiles(game, gc);
    }
	
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