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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;


public class EndGameControler {
	
	private int skin1er;
	
	private int skin2eme;
	
	private int skinMap;
	
	private int skinPomme;
	
	private int skinPlayer;
	
	private String Pseudo;
	
    @FXML
    private Button Bouton_relancer;

    @FXML
    private Text ID_2eme;

    @FXML
    private Text ID_Gagnant;

    @FXML
    private ImageView Image_2eme;

    @FXML
    private ImageView Image_Gagnant;
    
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

    public void setSkins(int SkinPlayer0, int SkinPlayer1, int skinMap, int skinPomme, int skinPlayer, String Pseudo) {
    	//test sans classement
    	this.skin1er=SkinPlayer0;
    	this.skin2eme=SkinPlayer1;
    	
    	//On stocke les valeurs des skins du joueur
    	this.skinMap=skinMap;
    	this.skinPomme=skinPomme;
    	this.skinPlayer=skinPlayer;
    	this.Pseudo = Pseudo;
    	System.out.println(skin1er + " J1 " + skin2eme + " J2");
    	if (Image_Gagnant!=null) ChangeImage(Image_Gagnant, "perso", skin1er);
    	if (Image_2eme!=null) ChangeImage(Image_2eme, "perso", skin2eme);
    	ID_Gagnant.setText("Gagnant : " + Pseudo);
    }
    
    public void ChangeImage (ImageView Image, String FileName, int VarName) {
    	Image.setImage(new Image(new File("images/" +FileName + VarName+".png").toURI().toString()));    
    }

}
