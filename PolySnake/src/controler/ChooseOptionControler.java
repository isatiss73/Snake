package controler;


import java.io.File;
import java.io.IOException;

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

public class ChooseOptionControler {
	
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
	    private Button BoutonLancerPartie;
	    
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
	    	BoiteNbBots.valueProperty().addListener((observable, oldValue, newValue) -> {
	    		NbBots=newValue.intValue();
	    		ChiffreNbBots.setText(Integer.toString(NbBots));
	    	});
	    	
	    	BoiteTailleSerpent.valueProperty().addListener((observable, oldValue, newValue) -> {
	    		TailleSerpent=newValue.intValue(); 	    
	    		ChiffreTailleSerpent.setText(Integer.toString(TailleSerpent));
	    	});
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
	    void clicBoutonLancerPartieAction(ActionEvent event) throws IOException {
	    	System.out.println("Bouton Lancer partie !");
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
    
}
