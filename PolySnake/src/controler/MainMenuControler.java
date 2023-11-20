package controler;

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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.Main;

public class MainMenuControler {

    @FXML
    private ImageView FlecheDSkinJoueur;

    @FXML
    private ImageView FlecheDSkinMap;

    @FXML
    private ImageView FlecheDSkinPomme;

    @FXML
    private ImageView FlecheGSkinJoueur;

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
        System.out.println("Bouton Quitter cliqué !");
    	Platform.exit();
    }


    @FXML
    void clicBoutonHebergerAction() {
    		System.out.println("Bouton Heberger cliqué !");
    }
    
    @FXML
    void clicBoutonRejoindreAction(ActionEvent event) throws IOException {
    	System.out.println("Bouton Rejoindre cliqué !");
    	FXMLLoader loader = Main.FXLoad("Scene_Rejoindre");
    	Parent root = loader.load();
		
    	Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	stage.setScene(scene);

    	stage.show();
    }

    @FXML
    void clicFlecheDSkinMapAction() {
    	System.out.println("Deplacement Droite Skin Map !");
    }

    @FXML
    void clicFlecheGSkinMapAction() {
    	System.out.println("Deplacement Gauche Skin Map !");
    }
    
    @FXML
    void clicFlecheDSkinJoueurAction() {
    	System.out.println("Deplacement Droite Skin Joueur !");
    }

    @FXML
    void clicFlecheGSkinJoueurAction() {
    	System.out.println("Deplacement Gauche Skin Joueur !");
    }
    
    @FXML
    void clicFlecheDSkinPommeAction() {
    	System.out.println("Deplacement Droite Skin Pomme !");
    }

    @FXML
    void clicFlecheGSkinPommeAction() {
    	System.out.println("Deplacement Gauche Skin Pomme !");
    }
    
}
