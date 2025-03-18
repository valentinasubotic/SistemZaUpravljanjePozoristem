package application;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller_radnikKreiraRadnika implements Initializable{

		@FXML
		private Button button_registrujtese;
		
	    @FXML
	    private ChoiceBox<Pozoriste> choiceBox;

	    @FXML
	    private TextField tf_ime;

	    @FXML
	    private TextField tf_korisnicko;

	    @FXML
	    private PasswordField tf_lozinka;

	    @FXML
	    private TextField tf_prezime;
	    
	    @FXML
	    public void switchToNakonPrijaveRadnika(ActionEvent event) {
	    	Alert alert0 = new Alert(Alert.AlertType.CONFIRMATION);
		 	alert0.setTitle("IZLAZAK");
		 	alert0.setContentText("Da li ste sigurni da zelite napustite stranicu?");
		 	
		 	Optional <ButtonType>result = alert0.showAndWait();
		 	if(result.get()==ButtonType.OK) {
			try {
				Parent root = FXMLLoader.load(getClass().getResource("Scena_nakonprijaveRadnika.fxml"));
				Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				Scene scene = new Scene(root);
				stage.setTitle("Dobrodošli radniče " + Controller_radnik.vratiImePrezime());
				stage.setScene(scene);
				stage.show();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		 	}
	    }
	    
	    private Connection connect = null;
	    private PreparedStatement statement = null;
	    private PreparedStatement postoji = null;
	    private ResultSet result = null;
	    
	    public void regirstrujSe(ActionEvent event) {
	    	if(tf_korisnicko.getText().equals("") && tf_lozinka.getText().equals("") && tf_ime.getText().equals("") && tf_prezime.getText().equals("")) {
	    		Alert alert1 = new Alert(Alert.AlertType.ERROR);
				alert1.setContentText("Popunite polja");
				alert1.show();
	    	}
	    	else if(tf_korisnicko.getText().equals("") || tf_lozinka.getText().equals("") || tf_ime.getText().equals("") || tf_prezime.getText().equals("")) {
	    		Alert alert4 = new Alert(Alert.AlertType.ERROR);
				alert4.setContentText("Sva polja moraju biti popunjena");
				alert4.show();
	    	}
	    	else if(choiceBox.getValue() == null) {
	    		Alert alert5 = new Alert(Alert.AlertType.ERROR);
	    		alert5.setContentText("Neophodno je da izaberete pozoriste");
	    		alert5.show();
	    	}
	    	else if(Controller_nakonprijaveRadnika.vratiPozoristeID() != choiceBox.getValue().getID() && Radnik_pozorista.daLiNekoRadiUPozirstu(choiceBox.getValue().getID())) {
	    			Alert alert6 = new Alert(Alert.AlertType.ERROR);
		    		alert6.setContentText("Ukoliko zelite da dodate novog radnika u pozoristu u kojem NE radite neophodno je da izaberete pozoriste u kome nema radnika\n" + choiceBox.getValue().getNaziv() + " pozoriste vec posjeduje radnika.");
		    		alert6.show();			
	    	}
	    	
	    	else {
	    	connect = Konekcija.getConnection();
	    	String sql = "SELECT * FROM radnik_pozorista WHERE korisnicko_ime = ?";
	    	try {
	    			postoji = connect.prepareStatement(sql);
	    			postoji.setString(1, tf_korisnicko.getText());
	    			
	    			result = postoji.executeQuery();
	    			if(result.isBeforeFirst()) {
	    				Alert alert = new Alert(Alert.AlertType.ERROR);
	    				alert.setContentText("Korisnicko ime " + tf_korisnicko.getText() + " vec postoji. Molimo Vas unesite novo korisnicko! ");
	    				alert.show();
	    				tf_korisnicko.setText("");
	    				tf_lozinka.setText("");
	    			}
	    			else {
	    				statement = connect.prepareStatement("INSERT INTO radnik_pozorista (ime, prezime, korisnicko_ime, lozinka, pozoriste_id) VALUE (?,?,?,?,?)");
	    				statement.setString(1, tf_ime.getText());
	    				statement.setString(2, tf_prezime.getText());
	    				statement.setString(3, tf_korisnicko.getText());
	    				statement.setString(4, Radnik_pozorista.getMd5(tf_lozinka.getText()));
	    				statement.setInt(5, choiceBox.getValue().getID());
	    				statement.executeUpdate();
	    				
	    				Konekcija.ucitajRadnika();
	    				
	    				Alert alert4 = new Alert(Alert.AlertType.INFORMATION);
		        		alert4.setTitle("OBAVJESTENJE!");
		        		alert4.setContentText("Uspjesno ste dodali radnika!");
		        		Optional <ButtonType>result1 = alert4.showAndWait();
		    		 	if(result1.get()==ButtonType.OK) {
		    		 		try {
		    					Parent root = FXMLLoader.load(getClass().getResource("Scena_nakonprijaveRadnika.fxml"));
		    					Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		    					Scene scene = new Scene(root);
		    					stage.setTitle("Dobrodošli radniče " + Controller_radnik.vratiImePrezime());
		    					stage.setScene(scene);
		    					stage.show();
		    				}
		    				catch(IOException e) {
		    					e.printStackTrace();
		    				}
		    		 	}
	    				
	    				
	    				/*
	    				button_registrujtese.getScene().getWindow().hide();
	    				Parent root = FXMLLoader.load(getClass().getResource("Scena_nakonprijaveRadnika.fxml"));
	    				Scene scene = new Scene(root);
	    				Stage stage = new Stage();
	    				//stage.setTitle("Dobrodošli radniče " + Controller_radnik.vratiImePrezime());
	    				stage.setScene(scene);
	    				stage.show();
	    				*/
	    				
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
	

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			// TODO Auto-generated method stub
			

			choiceBox.getItems().addAll(Pozoriste.getLista_pozorista());
			
		}
		

	}

