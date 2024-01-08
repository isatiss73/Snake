package controler;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Cell;
import model.Game;

public class ChooseOptionControler extends Application {
	public static Game game;
		
		private boolean HostORGuest;
	
	
		private int skinMap;
	    private int skinPlayer;
	    private int skinPomme;
	
	
		private int ruleAppPomme=0;
		private int ruleAssiste=0;
		private int ruleChacunPomme=0;
		private int ruleSansCroissance=0;
		private int ruleEchec=0;
		private int ruleItems=0;
		private int ruleMapRand=0;
		private int ruleMurSpec=0;
		private int ruleRevanche=0;
		private int ruleRueeOr=0;

		private int NbBots=0;
		private int TailleSerpent=2;
		
	 	@FXML
	    private CheckBox BoiteApparitionPomme;

	    @FXML
	    private CheckBox BoiteAssiste;

	    @FXML
	    private CheckBox BoiteChacunPomme;

	    @FXML
	    private CheckBox BoiteCroissance;

	    @FXML
	    private CheckBox BoiteEchec;

	    @FXML
	    private CheckBox BoiteItem;

	    @FXML
	    private CheckBox BoiteMap;

	    @FXML
	    private CheckBox BoiteMurSpecial;

	    @FXML
	    private Slider BoiteNbBots;

	    @FXML
	    private CheckBox BoiteRevanche;

	    @FXML
	    private CheckBox BoiteRuéeOr;

	    @FXML
	    private Slider BoiteTailleSerpent;

	    @FXML
	    private Button BoutonLancerPartieLAN;

	    @FXML
	    private Button BoutonLancerPartieLocal;
	    
	    @FXML
	    private Button BoutonRetour;

	    @FXML
	    private Text ChiffreNbBots;

	    @FXML
	    private Text ChiffreTailleSerpent;
	    
	    @FXML
	    private Text TextIP;

	    @FXML
	    private Text TextPort;
	    
	    @FXML
	    private Text TextPseudoJ2;

	    @FXML
	    private Text TextPseudoJ3;

	    @FXML
	    private Text TextPseudoJ4;
	    
	    @FXML
	    private ImageView J2LogoValidation;

	    @FXML
	    private ImageView J3LogoValidation;

	    @FXML
	    private ImageView J4LogoValidation;
	    
	    @FXML
	    public void initialize() {
	    	//tell if the player is the host or a guest :
	    	game = Game.getInstance();	   	
	    	
	    	if (game != null) {
		    	if (game.getControler().leftID==0) HostORGuest = true;
		    	else HostORGuest = false;		    	
	    	}
	    	
	    	//Write the IP Adress and Port of the player :
	    	try {
                TextIP.setText("IP : " +InetAddress.getLocalHost().getHostAddress());
                //TextPort.setText(InetAddress.getLocalHost().getHostName());
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
	    	
	    	// Si le client se connecte, on réécris son port plutôt qu'en prendre un nouveau automatiquement
	    	if (HostORGuest) {
	            try (ServerSocket serverSocket = new ServerSocket(1002)) { // 0 pour choisir automatiquement un port disponible
	                TextPort.setText("Port : " + serverSocket.getLocalPort());
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	    	}
            
            BoiteNbBots.valueProperty().addListener((observable, oldValue, newValue) -> {
                NbBots=newValue.intValue();
                ChiffreNbBots.setText(Integer.toString(NbBots));
            });

            BoiteTailleSerpent.valueProperty().addListener((observable, oldValue, newValue) -> {
                TailleSerpent=newValue.intValue();
                ChiffreTailleSerpent.setText(Integer.toString(TailleSerpent));
            });

        	
            if (HostORGuest == false) {
            	BoiteApparitionPomme.setDisable(true);
            	BoiteAssiste.setDisable(true);
            	BoiteChacunPomme.setDisable(true);
            	BoiteCroissance.setDisable(true);
            	BoiteEchec.setDisable(true);
            	BoiteItem.setDisable(true);
                BoiteMap.setDisable(true);
            	BoiteMurSpecial.setDisable(true);
            	BoiteNbBots.setDisable(true);
            	BoiteRevanche.setDisable(true);
            	BoiteRuéeOr.setDisable(true);
            	BoiteTailleSerpent.setDisable(true);
            	BoutonLancerPartieLAN.setDisable(true);
            	BoutonLancerPartieLocal.setDisable(true);
            }
        }
	    
	    public void setSkinOptions(int skinMap, int skinPlayer, int skinPomme) {
	        this.skinMap = skinMap;
	        this.skinPlayer = skinPlayer;
	        this.skinPomme = skinPomme;
	    }
	    
	    
	    @FXML
	    void BoiteApparitionPommeAction() {
	    	ruleAppPomme=1-ruleAppPomme;
	    }

	    @FXML
	    void BoiteAssisteAction() {
	    	ruleAssiste=1-ruleAssiste;
	    }

	    @FXML
	    void BoiteChacunPommeAction() {
	    	ruleChacunPomme=1-ruleChacunPomme;
	    }

	    @FXML
	    void BoiteCroissanceAction() {
	    	ruleSansCroissance=1-ruleSansCroissance;
	    }

	    @FXML
	    void BoiteEchecAction() {
	    	ruleEchec=1-ruleEchec;
	    }

	    @FXML
	    void BoiteItemAction() {
	    	ruleItems=1-ruleItems;
	    }

	    @FXML
	    void BoiteMapAction() {
	    	ruleMapRand=1-ruleMapRand;
	    }

	    @FXML
	    void BoiteMurSpecialAction() {
	    	ruleMurSpec=1-ruleMurSpec;
	    }

	    @FXML
	    void BoiteRevancheAction() {
	    	ruleRevanche=1-ruleRevanche;
	    }

	    @FXML
	    void BoiteRuéeOrAction() {
	    	ruleRueeOr=1-ruleRueeOr;
	    }

	    @FXML
	    void clicBoutonLancerPartieLANAction(ActionEvent event) throws IOException {

	    	
	    }
	    
	    
	    @FXML
	    void clicBoutonLancerPartieLocalAction(ActionEvent event) throws IOException {
	    	
	    	// Initialiser le jeu en premier
		    game = Game.getInstance();
		    game.reset(10,10, 2);
		    
		    //System.out.println(game.smoothString());
		    
		    game.createSnake(0, 2, 2, 2, 1, 0);
		    game.createSnake(1, 2, 4, 2, 1, 0);
		    game.getPlayer(0).setSpeed(3);
		    game.getPlayer(1).setSpeed(3);
		    
		    //System.out.println(game);
		    
		    //game.createApple(Cell.A_LENGTH_ONLY);
		    //game.createWall(Cell.A_LENGTH_ONLY);
		    
	    	Thread gameThread = new Thread(new GameRunnable());
			gameThread.start();
			
			FXMLLoader loader = new FXMLLoader(new File("scenes/Scene_partie.fxml").toURL());

	    	Parent root = loader.load();
		    
	    	Scene scene = new Scene(root);
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    	
	    	stage.setScene(scene);
		    GameViewControler jeu = new GameViewControler(game, stage, gameThread);
			
			jeu.setGameRules(skinMap, skinPlayer, skinPomme);
			
		    jeu.initializeCanvas(game,stage);
	    	
	    	stage.show();
	    }
	    

	    @FXML
	    void clicBoutonRetourAction(ActionEvent event) throws IOException {
	    	FXMLLoader loader = new FXMLLoader(new File("scenes/Scene_menu.fxml").toURL());
	    	Parent root = loader.load();
			
	    	Scene scene = new Scene(root);
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    	
	    	stage.setScene(scene);

	    	stage.show();
	    }


		@Override
		public void start(Stage arg0) throws Exception {
			// TODO Auto-generated method stub
			
		}
    
}
