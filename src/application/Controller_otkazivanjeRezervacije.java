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
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;

public class Controller_otkazivanjeRezervacije  implements Initializable{
	//posjetilac
    @FXML
    private ListView<Karta> listaKarata;
    @FXML
    private Button dugme;

	
	   @FXML
	    void switchToNakonPrijavePosjetioca(ActionEvent event) {
	    	Alert alert0 = new Alert(Alert.AlertType.CONFIRMATION);
		 	alert0.setTitle("IZLAZAK");
		 	alert0.setContentText("Da li ste sigurni da zelite da se vratite na pocetnu stranicu?");
		 	
		 	Optional <ButtonType>result = alert0.showAndWait();
		 	if(result.get()==ButtonType.OK) {
			try {
				Parent root = FXMLLoader.load(getClass().getResource("Scena_nakonprijavePosjetioca.fxml"));
				Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				Scene scene = new Scene(root);
				String imePrezime;
	    		if(Controller_posjetilac.prijava) {
	    			imePrezime = Controller_posjetilac.vratiImePrezimePosjetioca();
	    		}
	    		else {
	    			imePrezime = Controlle_posjetilac_registracija.vratiImePrezimePosjetiocaRegistracija();
	    		}
	    		stage.setTitle("Dobrodošli posjetioče " + imePrezime);
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

	    @FXML
	    void otkazivanje(ActionEvent event) {
	    	
	    	 if(listaKarata.getSelectionModel().getSelectedItem() == null){
	    		Alert alert0 = new Alert(Alert.AlertType.WARNING);
	    	 	alert0.setTitle("UPOZORENJE");
	    	 	alert0.setContentText("NEOPHPDNO JE DA IZABERETE KARTU KOJU ZELITE DA OTKAZETE!");
	    	 	alert0.show();
	    	}
	    	else {
	    		Alert alert0 = new Alert(Alert.AlertType.CONFIRMATION);
			 	alert0.setTitle("POTVRDA OTKAZIVANJA KARTE");
			 	alert0.setContentText("Da li ste sigurni u to da zelite da izvrsite otkazivanje rezervacije?");
			 	Optional <ButtonType>result = alert0.showAndWait();
			 	boolean posmatranje = false;
			 	if(result.get()==ButtonType.OK) {
			 		ObservableList<Karta> selectedKarte = listaKarata.getSelectionModel().getSelectedItems();
			 		connect = Konekcija.getConnection();
			 		for(Karta k : selectedKarte) {
			 		try {
			 			statement = connect.prepareStatement("DELETE FROM karta WHERE id = ?");
	    				statement.setInt(1, k.getID());
	    				statement.executeUpdate();
			 			
			 			posmatranje=true;
			 			
			 			Karta.getKarta_lista().remove(k);
			 			
			 			
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
		        		alert4.setContentText("Uspjesno ste otkazali rezervaciju!");
		        		Optional <ButtonType>result1 = alert4.showAndWait();
		    		 	if(result1.get()==ButtonType.OK) {
		    		 		try {
		    					Parent root = FXMLLoader.load(getClass().getResource("Scena_nakonprijavePosjetioca.fxml"));
		    					Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		    					Scene scene = new Scene(root);
		    					String imePrezime;
		    		    		if(Controller_posjetilac.prijava) {
		    		    			imePrezime = Controller_posjetilac.vratiImePrezimePosjetioca();
		    		    		}
		    		    		else {
		    		    			imePrezime = Controlle_posjetilac_registracija.vratiImePrezimePosjetiocaRegistracija();
		    		    		}
		    		    		stage.setTitle("Dobrodošli posjetioče " + imePrezime);
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
		// TODO Auto-generated method stub
	
		ArrayList<Karta> pomocnaLista = new ArrayList<>();
		String korisnicko;
		if(Controller_posjetilac.prijava) {
		 	 korisnicko = Controller_posjetilac.korisnickoPosjetioicaPrijava;
		 	}
		 	else {
		 		 korisnicko=Controlle_posjetilac_registracija.korisnickoPosjetioicaRegistracija;
		 	}
		listaKarata.getItems().clear();
		Posjetilac_pozorista pp = Posjetilac_pozorista.dajPosjetiocaNaOsnovuStringa(korisnicko);
		for(Karta k : Karta.getKarta_lista()) {
			if(k.getPosjetilac_ID() == pp.getID()) {
				Izvodjenje_predstave ip = Izvodjenje_predstave.dajIzvodjenjeNaOsnovuID(k.getIzvodjenje_predstave_ID());
				
				if(Izvodjenje_predstave.daLiJeMoguceOtkazatiRez(ip.getDatum_i_vrijeme()) && k.getStatus()==2) {
					pomocnaLista.add(k);
				}
				
			}
		}
		listaKarata.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		listaKarata.getItems().addAll(pomocnaLista);
		
		if(listaKarata.getItems().isEmpty()) {
			listaKarata.setVisible(false);
			dugme.setVisible(false);
			Alert alert4 = new Alert(Alert.AlertType.INFORMATION);
    		alert4.setTitle("OBAVJESTENJE!");
    		alert4.setContentText("Niste u mogucnosti da otkazete rezervaciju karte, predstava se odrzava za manje od 48h!");
    		alert4.show();
		 
		}
		
		
	}
	   
}
