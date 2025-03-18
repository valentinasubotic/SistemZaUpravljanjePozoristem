package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller_posjetilac implements Initializable {

    @FXML
    private Button button_prijava;

    @FXML
    private Button button_registracija;

    @FXML
    private TextField tf_koriscnicko;

    @FXML
    private PasswordField tf_lozinka;
    
    private Connection connect = null;
    private PreparedStatement statement = null;
    private ResultSet result = null;
    public static boolean prijava;
    public static String korisnickoPosjetioicaPrijava;
    
    private static String ime;
    private static String prezime;
    public static String vratiImePrezimePosjetioca() {
    	return ime +" , " + prezime;
    }
    
    @FXML
    void switchToNakonPrijavePosjetioca(ActionEvent event) {
    	Alert alert0 = new Alert(Alert.AlertType.CONFIRMATION);
	 	alert0.setTitle("IZLAZAK");
	 	alert0.setContentText("Da li ste sigurni da zelite da se vratite na pocetnu stranicu?");
	 	
	 	Optional <ButtonType>result = alert0.showAndWait();
	 	if(result.get()==ButtonType.OK) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Scena_proba.fxml"));
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setTitle("DOBRODOSLI!");
			stage.setScene(scene);
			stage.show();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	 	}
    }
    
 

    
    public void prijaviSe(ActionEvent event) {
    	if(tf_koriscnicko.getText().equals("") && tf_lozinka.getText().equals("")) {
    		Alert alert1 = new Alert(Alert.AlertType.ERROR);
			alert1.setContentText("Popunite polja");
			alert1.show();
    	}
    	else if(tf_koriscnicko.getText().equals("")) {
    		Alert alert3 = new Alert(Alert.AlertType.ERROR);
			alert3.setContentText("Prvo unesite korisnciko ime!");
			alert3.show();
    	}
    	else if(tf_lozinka.getText().equals("")) {
    		Alert alert2 = new Alert(Alert.AlertType.ERROR);
			alert2.setContentText("Unesite lozinku za: " +  " korisnicko ime " + tf_koriscnicko.getText() );
			alert2.show();
    	}
    	
    	else {	 	
    		
    	boolean postojiPosjetilac = false;
    	for(Posjetilac_pozorista pp : Posjetilac_pozorista.getPosjetilac_pozorista_lista()) {
    		if(pp.getKorisnicko_ime().equals(tf_koriscnicko.getText())) {
    			postojiPosjetilac = true;
    			if(!pp.getLozinka().equals(Posjetilac_pozorista.getMd5(tf_lozinka.getText()))) {
    				tf_lozinka.setText("");
						Alert alert0 = new Alert(Alert.AlertType.WARNING);
 		    	 	alert0.setTitle("UPOZORENJE");
 		    	 	alert0.setContentText("Neipsravna lozinka za " + tf_koriscnicko.getText() + " .Pokusajte ponovo");
 		    	 	alert0.show();
					}
    			else {
    				connect = Konekcija.getConnection();
    		    	String sql = "SELECT * FROM posjetilac_pozorista WHERE korisnicko_ime = ? AND lozinka = ?";
    		    	try {
    		    			statement = connect.prepareStatement(sql);
    		    			statement.setString(1, tf_koriscnicko.getText());
    		    			statement.setString(2, Posjetilac_pozorista.getMd5(tf_lozinka.getText()));
    		    			result = statement.executeQuery();
    		    			if(result.next()) {
    		    				prijava = true;
    		    				korisnickoPosjetioicaPrijava=tf_koriscnicko.getText();
    		    				ime=pp.getIme();
    		    				prezime=pp.getPrezime();
    		    				button_prijava.getScene().getWindow().hide();
    		    				Parent root = FXMLLoader.load(getClass().getResource("Scena_nakonprijavePosjetioca.fxml"));
    		    				Scene scene = new Scene(root);
    		    				Stage stage = new Stage();
    		    				stage.setTitle("Dobrodošli posjetioče " + Controller_posjetilac.vratiImePrezimePosjetioca());
    		    				stage.setScene(scene);
    		    				stage.show();
    		    				
    		    			}
    		    			else {	
    		    				
    		    				Alert alert = new Alert(Alert.AlertType.ERROR);
    		    				alert.setContentText("Neispravan unos!");
    		    				alert.show();
    		    		
    		    		
    		    			}
    		    			
    		    		}
    		    	catch(Exception e) {
    		    		e.printStackTrace();
    		    	}
    		    	if (connect != null) {
    					try {
    						connect.close();
    					} catch (SQLException e) {
    						e.printStackTrace();
    					}
    				}
    			}
    			
    		}
    	}
    		if(!postojiPosjetilac) {
				 Alert alert0 = new Alert(Alert.AlertType.WARNING);
		    	 	alert0.setTitle("UPOZORENJE");
		    	 	alert0.setContentText("Korisnicko ime: " + tf_koriscnicko.getText() + " ne postoji.Pokusajte ponovo");
		    	 	alert0.show();
		    	 	tf_koriscnicko.setText("");
		    	 	tf_lozinka.setText("");
			 }
    	
    	
    	
    	/*
    	connect = Konekcija.getConnection();
    	String sql = "SELECT * FROM posjetilac_pozorista WHERE korisnicko_ime = ? AND lozinka = ?";
    	try {
    			statement = connect.prepareStatement(sql);
    			statement.setString(1, tf_koriscnicko.getText());
    			statement.setString(2, Posjetilac_pozorista.getMd5(tf_lozinka.getText()));
    			result = statement.executeQuery();
    			if(result.next()) {
    				prijava = true;
    				korisnickoPosjetioicaPrijava=tf_koriscnicko.getText();
    				button_prijava.getScene().getWindow().hide();
    				Parent root = FXMLLoader.load(getClass().getResource("Scena_nakonprijavePosjetioca.fxml"));
    				Scene scene = new Scene(root);
    				Stage stage = new Stage();
    				stage.setTitle("Dobrodošli posjetioče");
    				stage.setScene(scene);
    				stage.show();
    				
    			}else {	
    				
    				Alert alert = new Alert(Alert.AlertType.ERROR);
    				alert.setContentText("Neispravan unos!");
    				alert.show();
    		
    		
    			}
    			
    		}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	if (connect != null) {
			try {
				connect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    	*/
    	}
}
    public void switchtoPosjetilacRegistracija(ActionEvent event){
		try {
			prijava = false;
			Parent root = FXMLLoader.load(getClass().getResource("Scena_posjetilac_registracija.fxml"));
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setTitle("Dobrodošli posjetioce, registrujte se");
			stage.setScene(scene);
			stage.show();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}	
}