package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
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
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller_radnikPredajaKarte implements Initializable{
	 @FXML
	    private Button predajte;
 @FXML
 private TextField korisnicko_imeTf;

 @FXML
 private PasswordField passwordTf;

 @FXML
 private ListView<Karta> listaKarata;


	@FXML
 void switchToRadNaBlagajni(ActionEvent event) {
 	Alert alert0 = new Alert(Alert.AlertType.CONFIRMATION);
	 	alert0.setTitle("IZLAZAK");
	 	alert0.setContentText("Da li ste sigurni da zelite da se vratite na prethodnu stranicu?");
	 	
	 	Optional <ButtonType>result = alert0.showAndWait();
	 	if(result.get()==ButtonType.OK) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Scena_RadnikRadNaBlagajni.fxml"));
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setTitle("Radnice " + Controller_radnik.vratiImePrezime()  + ", manipulisite prodajom/rezervacijama karata!");
			stage.setScene(scene);
			stage.show();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	 	}
 }
 	 @FXML
	    void prikazKarata(ActionEvent event) {
 		 Konekcija.ucitajKartu();
 		 listaKarata.getItems().clear();
 		 if(korisnicko_imeTf.getText().equals("") || passwordTf.getText().equals("")) {
 				Alert alert0 = new Alert(Alert.AlertType.WARNING);
 	    	 	alert0.setTitle("UPOZORENJE");
 	    	 	alert0.setContentText("NEOPHPDNO JE DA SE UNESE KORISNICKO IME I LOZINKA!");
 	    	 	alert0.show();
 		 }
 		 else{
 			 ArrayList<Karta> pomocnaListaKarta = new ArrayList<>();
 			 boolean postojiLiPosjetilac = false;
 			
 			 for(Posjetilac_pozorista pp : Posjetilac_pozorista.getPosjetilac_pozorista_lista()) {
 				 if(pp.getKorisnicko_ime().equals(korisnicko_imeTf.getText())) {
 					 postojiLiPosjetilac = true;
 						if(!pp.getLozinka().equals(Posjetilac_pozorista.getMd5(passwordTf.getText()))) {
 							passwordTf.setText("");
     						Alert alert0 = new Alert(Alert.AlertType.WARNING);
         		    	 	alert0.setTitle("UPOZORENJE");
         		    	 	alert0.setContentText("Neipsravna lozinka za " + korisnicko_imeTf.getText() + " .Pokusajte ponovo");
         		    	 	alert0.show();
     					}
 						else {
 							
 							for(Karta k : Karta.getKarta_lista()) {
 								if(k.getPosjetilac_ID() == pp.getID() && k.getStatus() == 2) {
 									Izvodjenje_predstave ip = Izvodjenje_predstave.dajIzvodjenjeNaOsnovuID(k.getIzvodjenje_predstave_ID());
 									if(ip.getPozoriste_ID() == Controller_nakonprijaveRadnika.vratiPozoristeID())
 									if(Izvodjenje_predstave.daLiSeIzvodiUNarednomPeriodu(ip.getDatum_i_vrijeme())) {
 										pomocnaListaKarta.add(k);
 									}
 									
 								}
 							}
 							listaKarata.getItems().addAll(pomocnaListaKarta);
 							if(listaKarata.getItems().isEmpty()) {
 								Alert alert0 = new Alert(Alert.AlertType.WARNING);
             		    	 	alert0.setTitle("UPOZORENJE");
             		    	 	alert0.setContentText("Posjetilac " + korisnicko_imeTf.getText() + " trenutno nema rezervisanih karata u ovom pozoristu!");
             		    	 	alert0.show();
 							}
 							else {
 							listaKarata.setVisible(true);
 							predajte.setVisible(true);
 							}
 							
 						}
 				 }
 			 }
 			 if(!postojiLiPosjetilac) {
 				 Alert alert0 = new Alert(Alert.AlertType.WARNING);
		    	 	alert0.setTitle("UPOZORENJE");
		    	 	alert0.setContentText("Korisnicko ime: " + korisnicko_imeTf.getText() + " ne postoji.Pokusajte ponovo");
		    	 	alert0.show();
		    	 	korisnicko_imeTf.setText("");
		    		passwordTf.setText("");
 			 }
 		 }
	    }
 	  private Connection connect = null;
	    private PreparedStatement statement = null;
 @FXML
  void predavanje(ActionEvent event) {
	
 	if(listaKarata.getSelectionModel().getSelectedItem() == null){
 		Alert alert0 = new Alert(Alert.AlertType.WARNING);
 	 	alert0.setTitle("UPOZORENJE");
 	 	alert0.setContentText("NEOPHPDNO JE DA IZABERETE KARTU KOJU ZELITE DA PREDATE POSJETIOCU!");
 	 	alert0.show();
 	}
 	else {
 		Alert alert0 = new Alert(Alert.AlertType.CONFIRMATION);
		 	alert0.setTitle("POTVRDA PREDAVANJA KARTE");
		 	alert0.setContentText("Da li ste sigurni u to da zelite da izvrsite predaju karte?");
		 	Optional <ButtonType>result = alert0.showAndWait();
		 	boolean posmatranje = false;
		 	if(result.get()==ButtonType.OK) {
		 		ObservableList<Karta> selectedKarte = listaKarata.getSelectionModel().getSelectedItems();
		 		connect = Konekcija.getConnection();
		 		for(Karta k : selectedKarte) {
		 		try {
		 			statement = connect.prepareStatement("UPDATE karta SET status = ? WHERE id = ?");
		 			statement.setInt(1, 3);
		 			statement.setInt(2,k.getID());
		 			statement.executeUpdate();
		 			
		 			posmatranje=true;
		 			k.setStatus(3);
		 			Konekcija.ucitajKartu();
		 			
		 			
		 			
		 		}
		 		catch(Exception e) {
		    		e.printStackTrace();
		    		}
		 		}
		 		if (connect != null) {
					try {
						connect.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
		 		if(posmatranje) {
		
		    		Alert alert4 = new Alert(Alert.AlertType.INFORMATION);
	        		alert4.setTitle("OBAVJESTENJE!");
	        		alert4.setContentText("Uspjesno ste predali kartu!");
	        		Optional <ButtonType>result1 = alert4.showAndWait();
	    		 	if(result1.get()==ButtonType.OK) {
	    		 		try {
	    		 			Konekcija.ucitajKartu();
	    					Parent root = FXMLLoader.load(getClass().getResource("Scena_RadnikRadNaBlagajni.fxml"));
	    					Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    					Scene scene = new Scene(root);
	    					stage.setTitle("Radnice " + Controller_radnik.vratiImePrezime()  + ", manipulisite prodajom/rezervacijama karata!");
	    					stage.setScene(scene);
	    					stage.show();
	    				}
	    				catch(IOException e) {
	    					e.printStackTrace();
	    				}
	    		 	}
	    		 
		    	}
		 	}
 	}
 	    
 }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Konekcija.ucitajKartu();
		listaKarata.getItems().clear();
		// TODO Auto-generated method stub
		listaKarata.setVisible(false);
		listaKarata.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		predajte.setVisible(false);
	}

}
