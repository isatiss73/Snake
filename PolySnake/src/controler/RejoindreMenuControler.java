
package controler;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;

public class RejoindreMenuControler {
	
	private int skinMap;
    private int skinPlayer;
    private int skinPomme;
    private String Pseudo;
	
    @FXML
    private TextField IPaddress;

    @FXML
    private TextField PortAddress;

    @FXML
    private Text TextIP;

    @FXML
    private Text TextPort;
    
    @FXML
    private Button boutonMenu;

    @FXML
    private Button boutonOK;

    public void setSkins(int skinMap, int skinPlayer, int skinPomme, String Pseudo) {
    	this.skinMap = skinMap;
    	this.skinPlayer = skinPlayer;
    	this.skinPomme = skinPomme;
    	this.Pseudo = Pseudo;
    }
    
    @FXML
    public void initialize() {
    	try {
			TextIP.setText("IP : " +InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	    	
    	try (ServerSocket serverSocket = new ServerSocket(0)) { // 0 permet au système de choisir un port disponible
            TextPort.setText("Port : " + serverSocket.getLocalPort());
        } catch (Exception e) {
            e.printStackTrace();
        }
        boutonOK.setOnAction(event -> clicboutonOKAction());        
    }
    
    @FXML	
    public void clicboutonMenuAction(ActionEvent event) throws IOException {
        FXMLLoader loader = Main.FXLoad("Scene_menu");
    	Parent root = loader.load();
    	
        MainMenuControler menu = loader.getController();
        
    	Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	stage.setScene(scene);

        menu.setSkins(skinMap, skinPlayer, skinPomme, Pseudo);
    	
    	stage.show();
    }
    
    @FXML	
    public void clicboutonOKAction() {
        System.out.println("Bouton OK cliqué !");
    }

}
