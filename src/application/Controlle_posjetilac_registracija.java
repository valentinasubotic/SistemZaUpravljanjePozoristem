package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controlle_posjetilac_registracija implements Initializable {

    @FXML
    private Button button_prijavitese;

    @FXML
    private Button button_registrujtese;

    @FXML
    private TextField tf_ime;

    @FXML
    private TextField tf_koriscnicko;

    @FXML
    private TextField tf_lozinka;

    @FXML
    private TextField tf_prezime;
    
    private Connection connect = null;
    private PreparedStatement statement = null;
    private PreparedStatement postoji = null;
    private ResultSet result = null;
    
    public static String korisnickoPosjetioicaRegistracija;
    private static String ime;
    private static String prezime;
    public static String vratiImePrezimePosjetiocaRegistracija() {
    	return ime +" , " + prezime;
    }
    
    public void regirstrujSe(ActionEvent event) {
    	if(tf_koriscnicko.getText().equals("") && tf_lozinka.getText().equals("") && tf_ime.getText().equals("") && tf_prezime.getText().equals("")) {
    		Alert alert1 = new Alert(Alert.AlertType.ERROR);
			alert1.setContentText("Neophodno je da uneste informacije o sebi!");
			alert1.show();
    	}
    	else if(tf_koriscnicko.getText().equals("") || tf_lozinka.getText().equals("") || tf_ime.getText().equals("") || tf_prezime.getText().equals("")) {
    		Alert alert4 = new Alert(Alert.AlertType.ERROR);
			alert4.setContentText("Neophodno je da popunite sva polja!");
			alert4.show();
    	}
    	else {	 
    		boolean postojiKorisnicko = false;
    		for(Posjetilac_pozorista pp : Posjetilac_pozorista.getPosjetilac_pozorista_lista()) {
    			if(pp.getKorisnicko_ime().equals(tf_koriscnicko.getText())) {
    				postojiKorisnicko = true;
    				break;
    			}
    		}
    		if(postojiKorisnicko) {
    			Alert alert0 = new Alert(Alert.AlertType.WARNING);
	    	 	alert0.setTitle("UPOZORENJE");
	    	 	alert0.setContentText("Korisnciko ime " + tf_koriscnicko.getText() + " vec postoji. Molimo Vas da unesete drugo!");
	    	 	alert0.show();
	    	 	tf_koriscnicko.setText("");
	    	 	tf_lozinka.setText("");
	    	 	tf_ime.setText("");
	    	 	tf_prezime.setText("");
    		}
    		else {
    			connect = Konekcija.getConnection();
				boolean dodavanjeposj = false;
				try {
					statement = connect.prepareStatement("INSERT INTO posjetilac_pozorista (ime, prezime, korisnicko_ime, lozinka) VALUE (?,?,?,?)");
					statement.setString(1, tf_ime.getText());
					statement.setString(2, tf_prezime.getText());
					statement.setString(3, tf_koriscnicko.getText());
					statement.setString(4, Posjetilac_pozorista.getMd5(tf_lozinka.getText()));
					statement.executeUpdate();
					Konekcija.ucitajPosjetioca();
					dodavanjeposj = true;
					
					korisnickoPosjetioicaRegistracija=tf_koriscnicko.getText();
    				
    				button_registrujtese.getScene().getWindow().hide();
    				Parent root = FXMLLoader.load(getClass().getResource("Scena_nakonprijavePosjetioca.fxml"));
    				Scene scene = new Scene(root);
    				Stage stage = new Stage();
    				ime = tf_ime.getText();
    				prezime=  tf_prezime.getText();
    				stage.setTitle("Dobrodošli posjetioče, " + Controlle_posjetilac_registracija.vratiImePrezimePosjetiocaRegistracija());
    				stage.setScene(scene);
    				stage.show();
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
    		
    		
    		
    		
    		
    		
    		
    		
    		
    		
    		/*
    		
    	connect = Konekcija.getConnection();
    	String sql = "SELECT * FROM posjetilac_pozorista WHERE korisnicko_ime = ?";
    	try {
    			postoji = connect.prepareStatement(sql);
    			postoji.setString(1, tf_koriscnicko.getText());
    			
    			result = postoji.executeQuery();
    			if(result.isBeforeFirst()) {
    				Alert alert = new Alert(Alert.AlertType.ERROR);
    				alert.setContentText("Korisnicko ime " + tf_koriscnicko.getText() + " vec postoji ");
    				alert.show();
    			}
    			else {
    				statement = connect.prepareStatement("INSERT INTO posjetilac_pozorista (ime, prezime, korisnicko_ime, lozinka) VALUE (?,?,?,?)");
    				statement.setString(1, tf_ime.getText());
    				statement.setString(2, tf_prezime.getText());
    				statement.setString(3, tf_koriscnicko.getText());
    				statement.setString(4, Posjetilac_pozorista.getMd5(tf_lozinka.getText()));
    				statement.executeUpdate();
    				
    				korisnickoPosjetioicaRegistracija=tf_koriscnicko.getText();
    				
    				button_registrujtese.getScene().getWindow().hide();
    				Parent root = FXMLLoader.load(getClass().getResource("Scena_nakonprijavePosjetioca.fxml"));
    				Scene scene = new Scene(root);
    				Stage stage = new Stage();
    				stage.setTitle("Dobrodošli posjetioče");
    				stage.setScene(scene);
    				stage.show();
    				
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
    public void switchtoPosjetilacPrijava(ActionEvent event){
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Scena_posjetilac.fxml"));
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setTitle("Dobrodošli posjetioce, ukoliko imate nalog, registujte se!");
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