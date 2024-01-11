package controler;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;


public class EndGameControler {
	
	private ArrayList<Integer> Podium = new ArrayList<>();
	
	private int skinMap;
	
	private int skinPomme;
	
	private int skinPlayer;
	
	private String Pseudo;
	
    @FXML
    private Button Bouton_relancer;

	@FXML
	private Text ID_Gagnant;
	
    @FXML
    private Text ID_2eme;
    
    @FXML
    private Text ID_3eme;

    @FXML
    private Text ID_4eme;
    
    @FXML
    private ImageView Image_Gagnant;

    @FXML
    private ImageView Image_2eme;
    
    @FXML
    private ImageView Image_3eme;

    @FXML
    private ImageView Image_4eme;
    
    @FXML
    private Text Text_Taille1er;

    @FXML
    private Text Text_Taille2eme;

    @FXML
    private Text Text_Taille3eme;

    @FXML
    private Text Text_Taille4eme;
    

    @FXML
    private ImageView Wall0;

    @FXML
    private ImageView Wall1;

    @FXML
    private ImageView Wall10;

    @FXML
    private ImageView Wall11;

    @FXML
    private ImageView Wall2;

    @FXML
    private ImageView Wall3;

    @FXML
    private ImageView Wall4;

    @FXML
    private ImageView Wall5;

    @FXML
    private ImageView Wall6;

    @FXML
    private ImageView Wall7;

    @FXML
    private ImageView Wall8;

    @FXML
    private ImageView Wall9;

    @FXML
    private ImageView Coin0;

    @FXML
    private ImageView Coin1;

    @FXML
    private ImageView Coin2;

    @FXML
    private ImageView Coin3;

    @FXML
    private ImageView Coin4;

    @FXML
    private ImageView Coin5;
    
    
    public EndGameControler() {
    	
    }

    @FXML
    void Bouton_relancerAction(ActionEvent event) throws IOException {
    	FXMLLoader loader = Main.FXLoad("Scene_menu");
    	Parent root = loader.load();
		
    	Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	stage.setScene(scene);

        MainMenuControler menu = loader.getController();
        
    	stage.setScene(scene);
    	
        menu.setSkins(skinMap, skinPlayer, skinPomme, Pseudo);
    	
    	stage.show();
    }

    public void setPlayers(ArrayList<Integer> ListeSkins, ArrayList<String> ListePseudo, int skinMap, int skinPomme, int skinPlayer, String Pseudo, ArrayList<Integer> Podium) {
    	    	
    	if (ListeSkins.size() < 3) {
    		ID_3eme.setVisible(false);
    		Image_3eme.setVisible(false);
    		Text_Taille3eme.setVisible(false);
    	}
    	
    	if (ListeSkins.size() < 4) {
    		ID_4eme.setVisible(false);
    		Image_4eme.setVisible(false);
    		Text_Taille4eme.setVisible(false);
    	}
    	
        	ChangeImage(Wall0, "map", skinMap);
        	ChangeImage(Wall1, "map", skinMap);
        	ChangeImage(Wall2, "map", skinMap);
        	ChangeImage(Wall3, "map", skinMap);
        	ChangeImage(Wall4, "map", skinMap);
        	ChangeImage(Wall5, "map", skinMap);
        	ChangeImage(Wall6, "map", skinMap);
        	ChangeImage(Wall7, "map", skinMap);
        	ChangeImage(Wall8, "map", skinMap);
        	ChangeImage(Wall9, "map", skinMap);
        	ChangeImage(Wall10, "map", skinMap);
        	ChangeImage(Wall11, "map", skinMap);
        	ChangeImage(Coin0, "pomme", skinPomme);
        	ChangeImage(Coin1, "pomme", skinPomme);
        	ChangeImage(Coin2, "pomme", skinPomme);
        	ChangeImage(Coin3, "pomme", skinPomme);
        	ChangeImage(Coin4, "pomme", skinPomme);
        	ChangeImage(Coin5, "pomme", skinPomme);
    	
    	for (int i=0; i<Podium.size(); i++) {
    		if (Podium.get(i)==0) {
    	    	ChangeImage(Image_Gagnant, "perso", ListeSkins.get(i));
    	    	ID_Gagnant.setText(ListePseudo.get(i));
    	    }
    		if (Podium.get(i)==1) {
    	    	ChangeImage(Image_2eme, "perso", ListeSkins.get(i));
    	    	ID_2eme.setText(ListePseudo.get(i));
    		}
    		if (Podium.get(i)==2) {
    	    	ChangeImage(Image_3eme, "perso", ListeSkins.get(i));
    	    	ID_3eme.setText(ListePseudo.get(i));
    		}
    		if (Podium.get(i)==3) {
    	    	ChangeImage(Image_4eme, "perso", ListeSkins.get(i));
    	    	ID_4eme.setText(ListePseudo.get(i));
    		}
    	}
    	
    	//On stocke les valeurs des skins du joueur
    	this.skinMap=skinMap;
    	this.skinPomme=skinPomme;
    	this.skinPlayer=skinPlayer;
    	this.Pseudo = Pseudo;
    }
    
    public void ChangeImage (ImageView Image, String FileName, int VarName) {
    	Image.setImage(new Image(new File("images/" +FileName + VarName+".png").toURI().toString()));    
    }

    public void setLengths(ArrayList<Integer> Tailles, ArrayList<Integer> Podium) {
    	this.Podium = Podium;
    	
    	for (int i=0; i<Podium.size(); i++) {
    		if (Podium.get(i)==0) {
    	    	Text_Taille1er.setText(Tailles.get(i).toString());
    		}
    		if (Podium.get(i)==1) {
    			Text_Taille2eme.setText(Tailles.get(i).toString());
    		}
    		if (Podium.get(i)==2) {
    			Text_Taille3eme.setText(Tailles.get(i).toString());
    		}
    		if (Podium.get(i)==3) {
    			Text_Taille4eme.setText(Tailles.get(i).toString());
    		}
    	}
    }
    
}