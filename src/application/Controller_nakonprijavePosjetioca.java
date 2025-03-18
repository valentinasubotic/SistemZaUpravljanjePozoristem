package application;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Controller_nakonprijavePosjetioca implements Initializable{

    public void spisakPozorista(ActionEvent event)//rezervavija karte 
    {
    	//listaPozorista.setVisible(true);
    	try {
    		Parent root = FXMLLoader.load(getClass().getResource("Scena_posjetilacSpisakPozorisa.fxml"));
    		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		Scene scene = new Scene(root);
    		String imePrezime;
    		if(Controller_posjetilac.prijava) {
    			imePrezime = Controller_posjetilac.vratiImePrezimePosjetioca();
    		}
    		else {
    			imePrezime = Controlle_posjetilac_registracija.vratiImePrezimePosjetiocaRegistracija();
    		}
    		stage.setTitle("Posjetioce, " + imePrezime + " izaberite pozoriste u kom cete rezervisati kartu!");
    		stage.setScene(scene);
    		stage.show();
    	}
    	catch(IOException e) {
    		e.printStackTrace();
    	}
    }
    public void spisakOsoblja(ActionEvent event) {
    	//tabela.setVisible(true);
    	try {
    		Parent root = FXMLLoader.load(getClass().getResource("Scena_posjetilacSpisakOsoblja.fxml"));
    		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		Scene scene = new Scene(root);
    		String imePrezime;
    		if(Controller_posjetilac.prijava) {
    			imePrezime = Controller_posjetilac.vratiImePrezimePosjetioca();
    		}
    		else {
    			imePrezime = Controlle_posjetilac_registracija.vratiImePrezimePosjetiocaRegistracija();
    		}
    		stage.setTitle("Posjetioce, "+imePrezime+ " u prilogu je spisak osoblja!");
    		stage.setScene(scene);
    		stage.show();
    	}
    	catch(IOException e) {
    		e.printStackTrace();
    	}
    }

    @FXML
    void otkazivanjeRezervacije(ActionEvent event) {
    	String korisnicko;
    	if(Controller_posjetilac.prijava) {
		 	 korisnicko = Controller_posjetilac.korisnickoPosjetioicaPrijava;
		 	}
		 else {
		 		 korisnicko=Controlle_posjetilac_registracija.korisnickoPosjetioicaRegistracija;
		 	}
    	Posjetilac_pozorista pp = Posjetilac_pozorista.dajPosjetiocaNaOsnovuStringa(korisnicko);
    	
    	boolean uslov= false;
    	boolean uslov2 = false;
    	
    	for(Karta k : Karta.getKarta_lista()) {
    		if(k.getPosjetilac_ID() == pp.getID()) {
				Izvodjenje_predstave ip = Izvodjenje_predstave.dajIzvodjenjeNaOsnovuID(k.getIzvodjenje_predstave_ID());
				if(Izvodjenje_predstave.daLiSeIzvodiUNarednomPeriodu(ip.getDatum_i_vrijeme())  && k.getStatus()==2) {
					uslov=true;
					break;
					
				}
				
			}
    	}
    	for(Karta k : Karta.getKarta_lista()) {
    		if(k.getPosjetilac_ID() == pp.getID()) {
    			uslov2=true;
    			break;
    		}
    	}
    
    	
    	if(uslov && uslov2) {
    	
    	try {
    		Parent root = FXMLLoader.load(getClass().getResource("Scena_otkazivanjeRezervacije.fxml"));
    		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		Scene scene = new Scene(root);
    		stage.setTitle("Posjetioce " + pp.getIme() + ", " + pp.getPrezime() + " , otkazite rezervaciju!");
    		stage.setScene(scene);
    		stage.show();
    	}
    	catch(IOException e) {
    		e.printStackTrace();
    	}
    	}
    	else if(!uslov2 && !uslov){
    		Alert alert0 = new Alert(Alert.AlertType.WARNING);
    	 	alert0.setTitle("UPOZORENJE");
    	 	alert0.setContentText("Posjetioce " + pp.getIme() + ", " + pp.getPrezime()  + ", Vi nemate rezervisanih karata!");
    	 	alert0.show();
    	}
    	else {
    		Alert alert0 = new Alert(Alert.AlertType.WARNING);
    	 	alert0.setTitle("UPOZORENJE");
    	 	alert0.setContentText("Posjetioce " + pp.getIme() + ", " + pp.getPrezime()  + ", Vase rezervisane karte su se vec odrzale.\nUkoliko zelite rezervisite novu kartu!");
    	 	alert0.show();
    	}
    }
    

    @FXML
    void pregledKarata(ActionEvent event) {
    	String korisnicko;
    	if(Controller_posjetilac.prijava) {
		 	 korisnicko = Controller_posjetilac.korisnickoPosjetioicaPrijava;
		 	}
		 else {
		 		 korisnicko=Controlle_posjetilac_registracija.korisnickoPosjetioicaRegistracija;
		 	}
    	Posjetilac_pozorista pp = Posjetilac_pozorista.dajPosjetiocaNaOsnovuStringa(korisnicko);
    	boolean uslov= false;
    	for(Karta k : Karta.getKarta_lista()) {
    		if(k.getPosjetilac_ID() == pp.getID() && k.getStatus() != 1) {
    			uslov=true;
    			break;
    		}
    	}
    	if(uslov) {
    	
    	try {
    		Parent root = FXMLLoader.load(getClass().getResource("Scena_posjetilacPregledaKarte.fxml"));
    		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		Scene scene = new Scene(root);
    		stage.setTitle("Posjetioce " + pp.getIme() + ", " + pp.getPrezime() + " , u prilogu je pregled karata, koje ste rezervisali putem aplikacije!");
    		stage.setScene(scene);
    		stage.show();
    	}
    	catch(IOException e) {
    		e.printStackTrace();
    	}
    	}
    	else {
    		Alert alert0 = new Alert(Alert.AlertType.WARNING);
    	 	alert0.setTitle("UPOZORENJE");
    	 	alert0.setContentText("Posjetioce " + pp.getIme() + ", " + pp.getPrezime()  + ", Vi nemate rezervisanih karata u aplikaciji!");
    	 	alert0.show();
    	}
    }

    
    
    
    
    
    public void povratak(ActionEvent event){
    	Alert alert0 = new Alert(Alert.AlertType.CONFIRMATION);
	 	alert0.setTitle("ODJAVA");
	 	alert0.setContentText("Da li ste sigurni da zelite da se odjavite?");
	 	
	 	Optional <ButtonType>result = alert0.showAndWait();
	 	if(result.get()==ButtonType.OK) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Scena_posjetilac.fxml"));
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setTitle("Dobrodošli posjetioče, ukoliko imate nalog, prijavite se!");
			stage.setScene(scene);
			stage.show();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	 	}
    }
    
	
    
    
    
    
    
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		String korisnciko;
		if(Controller_posjetilac.prijava) {
			korisnciko = Controller_posjetilac.korisnickoPosjetioicaPrijava;
		}
		else
			korisnciko = Controlle_posjetilac_registracija.korisnickoPosjetioicaRegistracija;
		
		Posjetilac_pozorista pp = Posjetilac_pozorista.dajPosjetiocaNaOsnovuStringa(korisnciko);
		
		for(Karta k : Karta.getKarta_lista()) {
			if(k.getStatus() == 2 && k.getPosjetilac_ID() == pp.getID()) {
				Izvodjenje_predstave ip = Izvodjenje_predstave.dajIzvodjenjeNaOsnovuID(k.getIzvodjenje_predstave_ID()); 
				if(Izvodjenje_predstave.obavjestenjePosjetiocu(ip.getDatum_i_vrijeme())) {
					Alert alert0 = new Alert(Alert.AlertType.INFORMATION);
		    	 	alert0.setTitle("UPOZORENJE");
		    	 	alert0.setContentText("Posjetioce " + pp.getIme() + ", " + pp.getPrezime()  + ", Preuzmite Vasu rezervisanu kartu br. " + k.getBroj_karta() + ", jer je do predstave ostalo jos manje od 48h!");
		    	 	alert0.show();
					
				}
			}
		}
		
	}
    
    



}

