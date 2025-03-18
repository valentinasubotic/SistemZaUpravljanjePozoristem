package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller_posjetilacSpisakPozorista implements Initializable {
	@FXML
    private Label nemaslkarata;
	    @FXML
	    private CheckBox checkBoxBrojKarata;
	    @FXML
	    private ChoiceBox<Izvodjenje_predstave> choiceBoxTermini;

	    @FXML
	    private Label cijenaLabela;
	    @FXML
	    private Button klikButton;
	    @FXML
	    private Label labelaBrojKarata;
	    @FXML
	    private Label nestani10;

	    @FXML
	    private Label nestani11;

	    @FXML
	    private Label nestani12;
	    @FXML
	    private Label nestani8;

	    @FXML
	    private Label nestani9;
	  
	    @FXML
	    private Button potvrdaRezervacije;
	    @FXML
	    private TextField textFieldBrojKarata;

	    @FXML
	    private Label zanrLabela;
	    /////
	    @FXML
	    private Label imePozorista;
	
	    @FXML
	    private Label imePredstave1;
	    @FXML
	    private Label nestani6;

	    @FXML
	    private Label nestani7;
	    @FXML
	    private CheckBox checkBoxAutor;

	    @FXML
	    private CheckBox checkBoxGlumac;

	    @FXML
	    private CheckBox checkBoxReziser;

	    @FXML
	    private ChoiceBox<Osoblje> choiceBoxGlumci;

	    @FXML
	    private Label imeAutora;

	    @FXML
	    private Label imeRezisera;

	    @FXML
	    private ListView<Predstava> listaAutorPredstava;

	    @FXML
	    private ListView<Predstava> listaGlumciPredstave;

	    @FXML
	    private ListView<Predstava> listaReziserPredstava;

	    @FXML
	    private Label nestani;

	    @FXML
	    private Label nestani1;

	    @FXML
	    private Label nestani2;

	    @FXML
	    private Label nestani3;

	    @FXML
	    private Label nestani4;

	    @FXML
	    private Label nestani5;

	    @FXML
	    private Label predstavinoIme;

	    @FXML
	    private Label predstavinoIme1;

	    @FXML
	    private Label predstavinoIme11;

	 

	@FXML
	private Button buttonPotvrdaPredstava;
    @FXML
    private Label labela1;
    @FXML
    private Label labela;
    @FXML
    private ListView<Pozoriste> listaPozoriste;

    @FXML
    private ChoiceBox<Predstava> cbListaPredstava;
    
    int pomocniPozoristeID;
    double cijenaIzvodjenja;
    Osoblje reziserPredstave = null;
    Osoblje autorPredstave = null;
    
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
    @FXML
    void KlikScena(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("Scena_klik.fxml"));
			//Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Stage stage = new Stage();
			Scene scene = new Scene(root);
			stage.setTitle("Posjetioce, pogledajte sva ostala izvodjenja predstava u svim pozoristima! ");
			stage.setScene(scene);
			stage.show();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
    }
    @FXML
    void potvrdaPozorista(ActionEvent event) {
    	cbListaPredstava.getItems().clear();
    	ArrayList<Predstava> pomocnaListaPredstava = new ArrayList<>();
    	if(listaPozoriste.getSelectionModel().getSelectedItem() == null) {
    		Alert alert0 = new Alert(Alert.AlertType.WARNING);
    	 	alert0.setTitle("UPOZORENJE");
    	 	alert0.setContentText("NEOPHPDNO JE DA IZABERETE POZORISTE!");
    	 	alert0.show();
    	}
    	else {
    	Pozoriste selectedPozoriste = listaPozoriste.getSelectionModel().getSelectedItem();
    	pomocniPozoristeID=selectedPozoriste.getID();
    	
    	labela.setText(selectedPozoriste.getNaziv() + " pozoristu.");
    	labela1.setVisible(true);
    	labela.setVisible(true);
    	buttonPotvrdaPredstava.setVisible(true);
		for(Izvodjenje_predstave ip : Izvodjenje_predstave.getLista_izvodjenje_predstave()) {
			if(ip.getPozoriste_ID() == pomocniPozoristeID) {
				
				if(Izvodjenje_predstave.daLiSeIzvodiUNarednomPeriodu(ip.getDatum_i_vrijeme())) {
					if(!pomocnaListaPredstava.contains(Predstava.vratiPredstavu(ip.getPredstava_ID())))
					pomocnaListaPredstava.add(Predstava.vratiPredstavu(ip.getPredstava_ID()));
				}
				
			}
		}
		
		cbListaPredstava.getItems().addAll(pomocnaListaPredstava);
		cbListaPredstava.setVisible(true);
		if(cbListaPredstava.getItems().isEmpty()) {
			Alert alert4 = new Alert(Alert.AlertType.INFORMATION);
			alert4.setTitle("OBAVJESTENJE!");
			alert4.setContentText("U pozoristu koje ste odabrali nema izvodjenja predstava u narednom periodu.\nMolimo Vas da izaberete drugo pozoriste!");
			alert4.show();
		}
    	}
    }
    

    @FXML
    void potvrdaPredstava(ActionEvent event) {
    	if(cbListaPredstava.getItems().isEmpty()) {
    		Alert alert4 = new Alert(Alert.AlertType.INFORMATION);
    		alert4.setTitle("OBAVJESTENJE!");
    		alert4.setContentText("U pozoristu koje ste odabrali nema izvodjenja predstava u narednom periodu.\nMolimo Vas da izaberete drugo pozoriste!");
    		alert4.show();
    	}
    	else if(cbListaPredstava.getValue()==null) {
    		Alert alert0 = new Alert(Alert.AlertType.WARNING);
    	 	alert0.setTitle("UPOZORENJE");
    	 	alert0.setContentText("NEOPHPDNO JE DA IZABERETE PREDSTAVU!");
    	 	alert0.show();
    	}
    	else {
		checkBoxAutor.setVisible(true);
		checkBoxGlumac.setVisible(true);
		checkBoxReziser.setVisible(true);
		choiceBoxGlumci.setVisible(true);
		choiceBoxGlumci.getItems().clear();
		imeAutora.setVisible(true);
		imeRezisera.setVisible(true);
		nestani.setVisible(true);
		nestani2.setVisible(true);
		nestani4.setVisible(true);
		predstavinoIme.setVisible(true);
		predstavinoIme1.setVisible(true);
		predstavinoIme11.setVisible(true);

		
		imePozorista.setVisible(true);
		imePredstave1.setVisible(true);
		nestani6.setVisible(true);
		nestani7.setVisible(true);
		
		nestani8.setVisible(true);
		nestani9.setVisible(true);
		nestani10.setVisible(true);
		zanrLabela.setVisible(true);
		cijenaLabela.setVisible(true);
		choiceBoxTermini.setVisible(true);
		checkBoxBrojKarata.setVisible(true);
		nestani11.setVisible(true);
		nestani12.setVisible(true);
		textFieldBrojKarata.setVisible(true);
		klikButton.setVisible(true);
		potvrdaRezervacije.setVisible(true);
		
		choiceBoxTermini.getItems().clear();
		
		ArrayList<Izvodjenje_predstave> pomocnaLista = new ArrayList<>();
		
		Predstava predstava = cbListaPredstava.getValue();
		predstavinoIme.setText(predstava.getNaziv());
		predstavinoIme1.setText(predstava.getNaziv());
		predstavinoIme11.setText(predstava.getNaziv());
		imePozorista.setText(listaPozoriste.getSelectionModel().getSelectedItem().getNaziv());
		imePredstave1.setText(predstava.getNaziv());
		zanrLabela.setText(predstava.getStringZanr(predstava.getZanr()));
		
		for(Izvodjenje_predstave ip : Izvodjenje_predstave.getLista_izvodjenje_predstave()) {
			if(ip.getPozoriste_ID() == pomocniPozoristeID && ip.getPredstava_ID() == predstava.getID()) {
				cijenaIzvodjenja = ip.getCijena();
				if(Izvodjenje_predstave.daLiSeIzvodiUNarednomPeriodu(ip.getDatum_i_vrijeme())) {
					pomocnaLista.add(ip);
				}
				
			}
		}
		
		cijenaLabela.setText(Double.toString(cijenaIzvodjenja));
		choiceBoxTermini.getItems().addAll(pomocnaLista);	
		
		
		ArrayList<Osoblje> glumciPredstave = new ArrayList<>();
		for(Osoblje_predstave op : Osoblje_predstave.getLista_osoblja_predstave()) {
			if(op.getPredstava_ID() == predstava.getID()) {
				Osoblje o = Osoblje.vratiOsobuNaOsnovuID(op.getOsobolje_ID());
				if(o.getStringTip(o.getTip()).equals("glumac"))
					glumciPredstave.add(o);
				else if(o.getStringTip(o.getTip()).equals("reziser"))
					reziserPredstave = o;
				else
					autorPredstave = o;
			}
		}
		choiceBoxGlumci.getItems().addAll(glumciPredstave);
		imeRezisera.setText(reziserPredstave.toString());
		imeAutora.setText(autorPredstave.toString());
		
		
	
    	}
    }
    @FXML
    void prikaziPredstaveGlumca(ActionEvent event) {
    	if(choiceBoxGlumci.getValue() == null) {
    		Alert alert0 = new Alert(Alert.AlertType.WARNING);
    	 	alert0.setTitle("UPOZORENJE");
    	 	alert0.setContentText("NEOPHPDNO JE DA IZABERETE GLUMCA, KOJI VAS DODATNO INTERESUJE!");
    	 	alert0.show();
    	}
    	else{
    		if(checkBoxGlumac.isSelected()) {
    	nestani1.setVisible(true);
    	listaGlumciPredstave.setVisible(true);
    	listaGlumciPredstave.getItems().clear();
    	
    	ArrayList<Predstava> pomocnaListaGlumciPredstave = new ArrayList<>();
    	for(Osoblje_predstave op : Osoblje_predstave.getLista_osoblja_predstave()) {
    		if(op.getOsobolje_ID() == choiceBoxGlumci.getValue().getID()) {
    			pomocnaListaGlumciPredstave.add(Predstava.vratiPredstavu(op.getPredstava_ID()));
    		}
    	}
    	
    	listaGlumciPredstave.getItems().addAll(pomocnaListaGlumciPredstave);
  
    	}
    		else {
    			listaGlumciPredstave.getItems().clear();
    		}
    	}
    }
    @FXML
    void prikaziPredstaveRezisera(ActionEvent event) {
    	if(checkBoxReziser.isSelected()) {
    	nestani3.setVisible(true);
    	listaReziserPredstava.setVisible(true);
    	listaReziserPredstava.getItems().clear();
    	
    	
    	ArrayList<Predstava> pomocnaListaReziserPredstave = new ArrayList<>();
    	for(Osoblje_predstave op : Osoblje_predstave.getLista_osoblja_predstave()) {
    		if(op.getOsobolje_ID() == reziserPredstave.getID()) {
    			pomocnaListaReziserPredstave.add(Predstava.vratiPredstavu(op.getPredstava_ID()));
    		}
    	}
    	
    	listaReziserPredstava.getItems().addAll(pomocnaListaReziserPredstave);
    	}
    	else {
    		listaReziserPredstava.getItems().clear();
    	}
    }
    @FXML
    void prikaziPredstaveAutora(ActionEvent event) {
    	if(checkBoxAutor.isSelected()) {
    	nestani5.setVisible(true);
    	listaAutorPredstava.setVisible(true);
    	listaAutorPredstava.getItems().clear();    	
    	
      	ArrayList<Predstava> pomocnaListaAutorPredstave = new ArrayList<>();
    	for(Osoblje_predstave op : Osoblje_predstave.getLista_osoblja_predstave()) {
    		if(op.getOsobolje_ID() == autorPredstave.getID()) {
    			pomocnaListaAutorPredstave.add(Predstava.vratiPredstavu(op.getPredstava_ID()));
    		}
    	}
    	
    	listaAutorPredstava.getItems().addAll(pomocnaListaAutorPredstave);

    }
    	else {
    		listaAutorPredstava.getItems().clear();    	
    	}
    }


    @FXML
    void prikaziBrojSlobodnihKarata(ActionEvent event) {
    	if(choiceBoxTermini.getValue() == null) {
    		Alert alert0 = new Alert(Alert.AlertType.WARNING);
    	 	alert0.setTitle("UPOZORENJE");
    	 	alert0.setContentText("NEOPHPDNO JE DA IZABERETE TERMIN IZVODJENJA, KOJI VAM NAJVISE ODGOVARA");
    	 	alert0.show();
    	}
    	else {
    		if(checkBoxBrojKarata.isSelected()) {
    			labelaBrojKarata.setVisible(true);
    			
    			int brojSjedistaUPoz = listaPozoriste.getSelectionModel().getSelectedItem().getBroj_sjedista();
    			Izvodjenje_predstave ipSelected = choiceBoxTermini.getValue();
    		
    			for(Karta k : Karta.getKarta_lista()) {
    				if(k.getIzvodjenje_predstave_ID() == ipSelected.getID()) {
    					brojSjedistaUPoz = brojSjedistaUPoz -1 ;
    				}
    			}
    			labelaBrojKarata.setText(Integer.toString(brojSjedistaUPoz));
    			if(brojSjedistaUPoz == 0) {
    				nemaslkarata.setVisible(true);
    				nemaslkarata.setText("Nazalost, nema slobodnih mjeste u terminu: " + ipSelected.toString());
    			}
    
    			
    		}
    		else {
    			labelaBrojKarata.setText(" ");
    			
    		}
    		
    	}
    }  
    private Connection connect = null;
    private PreparedStatement statement = null;

    
    @FXML
    void potvrdaRezervacije(ActionEvent event) {
    	if(choiceBoxTermini.getValue() == null && textFieldBrojKarata.getText().equals("")) {
    		Alert alert0 = new Alert(Alert.AlertType.WARNING);
    	 	alert0.setTitle("UPOZORENJE");
    	 	alert0.setContentText("NEOPHPDNO JE DA IZABERETE TERMIN IZVODJENJA, KOJI VAM NAJVISE ODGOVARA!\nTAKO]E, NEOPHODNO JE DA UNESETE KOLIKO KARATA ZELITE DA REZERVISETE!");
    	 	alert0.show();
    	}
    	else if(choiceBoxTermini.getValue() == null){
    		Alert alert0 = new Alert(Alert.AlertType.WARNING);
    	 	alert0.setTitle("UPOZORENJE");
    	 	alert0.setContentText("NEOPHPDNO JE DA IZABERETE TERMIN IZVODJENJA, KOJI VAM NAJVISE ODGOVARA");
    	 	alert0.show();
    	}
    	else if(textFieldBrojKarata.getText().equals("") || Integer.parseInt(textFieldBrojKarata.getText()) <= 0) {
    		Alert alert0 = new Alert(Alert.AlertType.WARNING);
    	 	alert0.setTitle("UPOZORENJE");
    	 	alert0.setContentText("UNESITE BROJ, KOJI PREDSTAVLJA BROJ KARATA KOJI CETE REZERVISATI!");
    	 	alert0.show();
    	}
    	else if(Pozoriste.dajBrojSlobodnihSjedista(listaPozoriste.getSelectionModel().getSelectedItem(), choiceBoxTermini.getValue())<Integer.parseInt(textFieldBrojKarata.getText())) {
    		Alert alert0 = new Alert(Alert.AlertType.WARNING);
    	
    	 	alert0.setTitle("UPOZORENJE");
    	 	String s = "Slobodan broj sjedista je " + Pozoriste.dajBrojSlobodnihSjedista(listaPozoriste.getSelectionModel().getSelectedItem(), choiceBoxTermini.getValue())+", a vama je potrebno " + textFieldBrojKarata.getText() +
    	 			"\nDakle, za odabrani termin nije moguce izvrsiti rezervaciju. Molimo Vas da izaberete drugi termin!";
   
    	 	alert0.setContentText(s);
    	 	alert0.show();
    	}
    	else {
    		Alert alert0 = new Alert(Alert.AlertType.CONFIRMATION);
		 	alert0.setTitle("POTVRDA REZERVACIJE");
		 	
		 	int posjetilacID=1;
		 	String korisnicko;
		 	String imePosj ="";
		 	if(Controller_posjetilac.prijava) {
		 	 korisnicko = Controller_posjetilac.korisnickoPosjetioicaPrijava;
		 	}
		 	else {
		 		 korisnicko=Controlle_posjetilac_registracija.korisnickoPosjetioicaRegistracija;
		 	}
		 	Konekcija.ucitajPosjetioca();
			for(Posjetilac_pozorista p : Posjetilac_pozorista.getPosjetilac_pozorista_lista()) {
	 			if(p.getKorisnicko_ime().equals(korisnicko)) {
	 				posjetilacID=p.getID();
	 				imePosj = p.getIme() + ", " + p.getPrezime();
	 				break;
	 			}
	 			
	 		}
		
		 	
			String s = "Rezime informacija." + imePosj + ", izabrali ste:\n";
		 	s += "Pozoriste: " + listaPozoriste.getSelectionModel().getSelectedItem().getNaziv() + " u " + listaPozoriste.getSelectionModel().getSelectedItem().getGrad() + ".\n";
		 	s += "Predstava: " + cbListaPredstava.getValue().getNaziv() + ", zanr: " + cbListaPredstava.getValue().getStringZanr(cbListaPredstava.getValue().getZanr()) + ".\n";
		 	s += "Termin izvodjenja predstave: " + choiceBoxTermini.getValue()+"\n";
		 	s += "Broj karata za rezervaiju: " + textFieldBrojKarata.getText();
		 	alert0.setContentText(s);
		 	
		 	Optional <ButtonType>result = alert0.showAndWait();
		 	boolean posmatranje = false;
		 	if(result.get()==ButtonType.OK) {
		 		connect = Konekcija.getConnection();
		 		for(int i =0; i<Integer.parseInt(textFieldBrojKarata.getText());i++) {
		 		/////////////////////////////////////
		 		try {
		 			statement = connect.prepareStatement("INSERT INTO karta (izvodjenje_predstave_id, status, posjetilac_id, broj_karta) VALUE (?,?,?,?)");
    				statement.setInt(1, choiceBoxTermini.getValue().getID());
    				statement.setInt(2, 2);
    				statement.setInt(3, posjetilacID);
    				statement.setInt(4, Karta.sljedeciSlobodanBroj( choiceBoxTermini.getValue().getID(), listaPozoriste.getSelectionModel().getSelectedItem().getBroj_sjedista()));
    				statement.executeUpdate();
		 			
    				Konekcija.ucitajKartu();
    				
		 			posmatranje=true;
		 			
		 			
		 			
		 		}catch(Exception e) {
		    		e.printStackTrace();
		    		}
		 		}
		 		//////////////////////////////////////
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
	        		alert4.setContentText("Uspjesno ste rezervisali kartu!");
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
		listaPozoriste.getItems().addAll(Pozoriste.getLista_pozorista());
		labela1.setVisible(false);
		labela.setVisible(false);
		cbListaPredstava.setVisible(false);
		////////////////////////////////////
		buttonPotvrdaPredstava.setVisible(false);
		/////////////////////////////////
		checkBoxAutor.setVisible(false);
		checkBoxGlumac.setVisible(false);
		checkBoxReziser.setVisible(false);
		choiceBoxGlumci.setVisible(false);
		imeAutora.setVisible(false);
		imeRezisera.setVisible(false);
		nestani.setVisible(false);
		nestani2.setVisible(false);
		nestani4.setVisible(false);
		predstavinoIme.setVisible(false);
		predstavinoIme1.setVisible(false);
		predstavinoIme11.setVisible(false);
	
		/////////////////////////////////////////////////
		nestani1.setVisible(false);
		nestani3.setVisible(false);
		nestani5.setVisible(false);
		listaAutorPredstava.setVisible(false);
		listaGlumciPredstave.setVisible(false);
		listaReziserPredstava.setVisible(false);
		//////////////////////////////
		imePozorista.setVisible(false);
		imePredstave1.setVisible(false);
		nestani6.setVisible(false);
		nestani7.setVisible(false);
		///////////////////////
		checkBoxBrojKarata.setVisible(false);
		choiceBoxTermini.setVisible(false);
		cijenaLabela.setVisible(false);
		klikButton.setVisible(false);
		nestani10.setVisible(false);
		nestani11.setVisible(false);
		nestani12.setVisible(false);
		nestani8.setVisible(false);
		nestani9.setVisible(false);
		potvrdaRezervacije.setVisible(false);
		textFieldBrojKarata.setVisible(false);
		zanrLabela.setVisible(false);
		/////////////////////////////////////
		labelaBrojKarata.setVisible(false);//prikazi je nakon sto se klikne dugme checkBoxBrojKarata
		///////////////////////////
		nemaslkarata.setVisible(false);// ukoliko nema sl karata, tj. ako je broj sjedista==0 ispisace se ova labela
	}
	  

}
