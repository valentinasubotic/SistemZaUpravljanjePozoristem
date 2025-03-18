package application;



import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//povezan sa Scena_proba.fxml to je pocetna scena 

public class Controller {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void switchtoPosjetilac(ActionEvent event){
		try {
			root = FXMLLoader.load(getClass().getResource("Scena_posjetilac.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setTitle("Dobrodošli posjetioče, ukoliko imate nalog, prijavite se!");
			stage.setScene(scene);
			stage.show();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void switchtoRadnik(ActionEvent event){
		try {
			root = FXMLLoader.load(getClass().getResource("Scena_radnik.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setTitle("Dobrodošli radniče, prijavite se");
			stage.setScene(scene);
			stage.show();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}
