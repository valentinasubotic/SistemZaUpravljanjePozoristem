package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class Controller_radnikMijenjaLozinku implements Initializable {
	@FXML
    private PasswordField tf_nova;

    @FXML
    private PasswordField tf_trenutna;

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
    
	 public void potvrdaPromjeneLozinke(ActionEvent event) {
		 	Alert alert0 = new Alert(Alert.AlertType.CONFIRMATION);
		 	alert0.setTitle("POTVRDA");
		 	alert0.setContentText("Da li ste sigurni da zelite da promijenite lozinku");
		 	
		 	Optional <ButtonType>result = alert0.showAndWait();
		 	if(result.get()==ButtonType.OK) {
		 	
	    	if(tf_trenutna.getText().equals("") && tf_nova.getText().equals("")) {
	    		Alert alert1 = new Alert(Alert.AlertType.ERROR);
	    		alert1.setTitle("UPOZORENJE");
				alert1.setContentText("Popunite polja");
				alert1.show();
	    	}
	    	else if(tf_nova.getText().equals("")) {
	    		Alert alert3 = new Alert(Alert.AlertType.ERROR);
				alert3.setContentText("Unesite novu lozinku!");
				alert3.show();
	    	}
	    	else if(tf_trenutna.getText().equals("")) {
	    		Alert alert2 = new Alert(Alert.AlertType.ERROR);
				alert2.setContentText("Unesite trenutnu lozinku!");
				alert2.show();
	    	}
	    	
	    	else if(!Radnik_pozorista.daLiJeDobroUnesenaSifra(Controller_radnik.vratiKorisnicko(), Radnik_pozorista.getMd5(tf_trenutna.getText()))) {
	    		Alert alert3 = new Alert(Alert.AlertType.ERROR);
				alert3.setContentText("Netacno unesena lozinka za koriscniko ime:\n" + Controller_radnik.vratiKorisnicko());
				tf_trenutna.setText("");
				tf_nova.setText("");
				alert3.show();
				
	    	}
	    	else if(Radnik_pozorista.daLiJeDobroUnesenaSifra(Controller_radnik.vratiKorisnicko(), Radnik_pozorista.getMd5(tf_trenutna.getText())) && tf_trenutna.getText().equals(tf_nova.getText())) {
	    		Alert alert3 = new Alert(Alert.AlertType.ERROR);
				alert3.setContentText("Koriscnice " + Controller_radnik.vratiKorisnicko() + " unijeli ste opet staru lozinku za novu. Ako zelite da promijenite sifru unesite potpuno novu sifru!");
				alert3.show();
				tf_nova.setText("");
	    	}
	 
	    	else {
	    		connect = Konekcija.getConnection();
	        	String sql = "UPDATE radnik_pozorista SET lozinka = ? WHERE id = ?";
	        	try {
	        		statement = connect.prepareStatement(sql);
	        		statement.setString(1,Radnik_pozorista.getMd5(tf_nova.getText()));
	        		statement.setInt(2, Radnik_pozorista.vratiIdTabele(Controller_radnik.vratiKorisnicko()));
	        		statement.execute();
	        		
	        		for(Radnik_pozorista rp : Radnik_pozorista.getLista_radnika_pozorista()) {
	        			if(rp.getKorisnicko_ime().equals(Controller_radnik.vratiKorisnicko()))
	        				rp.setLozinka(Radnik_pozorista.getMd5(tf_nova.getText()));
	        		}
	        		
	        		Konekcija.ucitajRadnika();
	        		Alert alert4 = new Alert(Alert.AlertType.INFORMATION);
	        		alert4.setTitle("OBAVJESTENJE!");
	        		alert4.setContentText("Uspjesno ste promijenili lozinku!");
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
		 	else {
		 		/*
		 
				*/
		 		
		 	}
	 }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
