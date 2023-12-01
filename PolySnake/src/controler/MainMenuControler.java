package controler;


import java.io.File;
import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MainMenuControler {

	private int skinMap;
	
	private int skinPlayer;
	
	private int skinPomme;
	
	private int MaxSkinMap = 4;
	
	private int MaxSkinPlayer = 4;
	
	private int MaxSkinPomme = 4;
	
    @FXML
    private ImageView FlecheDSkinJoueur;

    @FXML
    private ImageView FlecheDSkinMap;

    @FXML
    private ImageView FlecheDSkinPomme;

    @FXML
    private ImageView FlecheGSkinJoueur;
        
    @FXML
    private ImageView ImageSkinJoueur;

    @FXML
    private ImageView ImageSkinMap;

    @FXML
    private ImageView ImageSkinPomme;
    
    @FXML
    private ImageView FlecheGSkinMap;

    @FXML
    private ImageView FlecheGSkinPomme;
	
    @FXML
    private Button boutonQuitter;

    @FXML
    private Button boutonHeberger;
    
    @FXML
    private Button boutonRejoindre;

    @FXML
    private TextField textPseudo;
    
    @FXML
    public void initialize() {
        boutonQuitter.setOnAction(event -> clicBoutonQuitterAction());
        boutonHeberger.setOnAction(event -> clicBoutonHebergerAction());
        //boutonRejoindre.setOnAction(event -> clicBoutonRejoindreAction(ActionEvent event));
    }
    
    @FXML	
    public void clicBoutonQuitterAction() {
    	Platform.exit();
    }


    @FXML
    void clicBoutonHebergerAction() {
    	System.out.println("Bouton Heberger cliqué !");
    }
    
    @FXML
    void clicBoutonRejoindreAction(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(new File("scenes/Scene_Rejoindre.fxml").toURL());
    	Parent root = loader.load();
		
    	Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	stage.setScene(scene);

    	stage.show();
    }

    @FXML
    void clicFlecheDSkinMapAction() {
    	
    	AddSkinMap();
    	    	
    	ImageSkinMap.setImage(new Image(new File("images/map" + skinMap+".png").toURI().toString()));
    }

    @FXML
    void clicFlecheGSkinMapAction() {
    	
    	RemoveSkinMap();
    	    	
    	ImageSkinMap.setImage(new Image(new File("images/map" + skinMap+".png").toURI().toString()));
    }
    
    @FXML
    void clicFlecheDSkinJoueurAction() {
    	
    	AddSkinPlayer();
    	    	
    	ImageSkinJoueur.setImage(new Image(new File("images/perso" + skinPlayer+".png").toURI().toString()));
    }

    @FXML
    void clicFlecheGSkinJoueurAction() {
    	
    	RemoveSkinPlayer();
    	  	
    	ImageSkinJoueur.setImage(new Image(new File("images/perso" + skinPlayer+".png").toURI().toString()));
    }
    
    @FXML
    void clicFlecheDSkinPommeAction() {
    	
    	AddSkinPomme();
    	    	
    	ImageSkinPomme.setImage(new Image(new File("images/pomme" + skinPomme+".png").toURI().toString()));
    }

    @FXML
    void clicFlecheGSkinPommeAction() {
    	
    	RemoveSkinPomme();
    	    	
    	ImageSkinPomme.setImage(new Image(new File("images/pomme" + skinPomme+".png").toURI().toString()));
    }
    
    public void AddSkinMap() {
    	if (skinMap < MaxSkinMap) {
    		skinMap++;
    	}
    	else {
    		skinMap =0;
    	}
    }
    
    public void RemoveSkinMap() {
    	if (skinMap > 0) {
    		skinMap--;
    	}
    	else {
    		skinMap = MaxSkinMap;
    	}
    }
    
    
    public void AddSkinPlayer() {
    	if (skinPlayer < MaxSkinPlayer) {
    		skinPlayer++;
    	}
    	else {
    		skinPlayer =0;
    	}
    }
    
    public void RemoveSkinPlayer() {
    	if (skinPlayer > 0) {
    		skinPlayer--;
    	}
    	else {
    		skinPlayer = MaxSkinPlayer;
    	}
    }
    
    public void AddSkinPomme() {
    	if (skinPomme < MaxSkinPomme) {
    		skinPomme++;
    	}
    	else {
    		skinPomme=0;
    	}
    }
    
    public void RemoveSkinPomme() {
    	if (skinPomme > 0) {
    		skinPomme--;
    	}
    	else {
    		skinPomme = MaxSkinPomme;
    	}
    }
    
}
