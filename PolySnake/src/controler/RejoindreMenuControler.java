package controler;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RejoindreMenuControler {
	
    @FXML
    private TextField IPaddress;

    @FXML
    private TextField PortAddress;

    @FXML
    private Button boutonMenu;

    @FXML
    private Button boutonOK;

    @FXML
    public void initialize() {
        //boutonMenu.setOnAction(event -> clicboutonMenuAction());
        boutonOK.setOnAction(event -> clicboutonOKAction());
    }
    
    @FXML	
    public void clicboutonMenuAction(ActionEvent event) throws IOException {
        System.out.println("Bouton Menu cliqué !");
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scene_menu.fxml"));
    	Parent root = loader.load();
		
    	Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	stage.setScene(scene);

    	stage.show();
    }
    
    @FXML	
    public void clicboutonOKAction() {
        System.out.println("Bouton OK cliqué !");
    }

}
