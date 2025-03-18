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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class Controller_radnikProdajeKarte implements Initializable{
    @FXML
    private Button buttonProdajaKarte;
    @FXML
    private TextField imeNematf;
    @FXML
    private Label imeLabela;
    @FXML
    private TextField korisnickoImeNematf;
    @FXML
    private Label korisnickoNema;
    @FXML
    private Label lozinkaNema;

    @FXML
    private PasswordField lozinkaNematf;
    @FXML
    private Label prezimeLabela;

    @FXML
    private TextField prezimeNematf;
    
    ///////
    @FXML
    private Label korisnickoIma;

    @FXML
    private TextField korisnickoImeImatf;
    @FXML
    private Label lozinkaIma;
    @FXML
    private PasswordField lozinkaImatf;
	 @FXML
	    private RadioButton imaNalogID;
	 @FXML
	    private RadioButton nemaNalogID;
	    @FXML
	    private ToggleGroup nalog;
	    @FXML
	    private Label nestaniLabela;
	    @FXML
	    private TextField textFieldBrojKarata;
	    @FXML
	    private Label nestani12;
    @FXML
    private ListView<Karta> listaKarata;
    @FXML
    private Button buttonPrikazRezervacija;
	@FXML
    private CheckBox checkBoxBrojKarata;

    @FXML
    private ChoiceBox<Izvodjenje_predstave> choiceBoxTermini;
    @FXML
    private Label cijenaLabela;
    @FXML
    private Label labelaBrojKarata;

    @FXML
    private Label nemaslkarata;
    @FXML
    private ChoiceBox<Predstava> cbListaPredstava;
    @FXML
    private ChoiceBox<Osoblje> choiceBoxGlumci;

    @FXML
    private Label imeAutora;

    @FXML
    private Label imeRezisera;

    @FXML
    private Label nestani;

    @FXML
    private Label nestani2;
    @FXML
    private Label labelaNemaRez;

    @FXML
    private Label nestani4;
    @FXML
    private Label nestani9;

    @FXML
    private Label nestani10;

    @FXML
    private Label predstavinoIme;

    @FXML
    private Label predstavinoIme1;

    @FXML
    private Label predstavinoIme11;
    
    Osoblje reziserPredstave = null;
    Osoblje autorPredstave = null;
    double cijenaIzvodjenja;


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
    void potvrdaPredstava(ActionEvent event) {
    	if(cbListaPredstava.getValue()==null) {
    		Alert alert0 = new Alert(Alert.AlertType.WARNING);
    	 	alert0.setTitle("UPOZORENJE");
    	 	alert0.setContentText("NEOPHPDNO JE DA IZABERETE PREDSTAVU!");
    	 	alert0.show();
    	}
    	else {
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
    		
    		checkBoxBrojKarata.setVisible(true);
    		checkBoxBrojKarata.setSelected(false);
    		choiceBoxTermini.setVisible(true);
    		choiceBoxTermini.getItems().clear();
    		cijenaLabela.setVisible(true);
    		nestani10.setVisible(true);
    		nestani9.setVisible(true);
    		
    		buttonPrikazRezervacija.setVisible(true);
    		
    		labelaBrojKarata.setText(" ");
    		
    		imaNalogID.setVisible(true);
    		nemaNalogID.setVisible(true);
    		nestaniLabela.setVisible(true);
    		textFieldBrojKarata.setVisible(true);
    		nestani12.setVisible(true);
    		
    		buttonProdajaKarte.setVisible(true);
    		
    		
    		
    		
    		Predstava predstava = cbListaPredstava.getValue();
    		predstavinoIme.setText(predstava.getNaziv());
    		predstavinoIme1.setText(predstava.getNaziv());
    		predstavinoIme11.setText(predstava.getNaziv());
    		
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
    		
    		ArrayList<Izvodjenje_predstave> pomocnaLista = new ArrayList<>();
    		for(Izvodjenje_predstave ip : Izvodjenje_predstave.getLista_izvodjenje_predstave()) {
    			if(ip.getPozoriste_ID() == Controller_nakonprijaveRadnika.vratiPozoristeID() && ip.getPredstava_ID() == predstava.getID()) {
    				cijenaIzvodjenja = ip.getCijena();
    				if(Izvodjenje_predstave.daLiSeIzvodiUNarednomPeriodu(ip.getDatum_i_vrijeme())) {
    					pomocnaLista.add(ip);
    				}
    				
    			}
    		}
    		
    		cijenaLabela.setText(Double.toString(cijenaIzvodjenja));
    		choiceBoxTermini.getItems().addAll(pomocnaLista);	
    		
    		
    	}
    }
    @FXML
    void prikaziBrojSlobodnihKarata(ActionEvent event) {
    	if(choiceBoxTermini.getValue() == null) {
    		checkBoxBrojKarata.setSelected(false);
    		Alert alert0 = new Alert(Alert.AlertType.WARNING);
    	 	alert0.setTitle("UPOZORENJE");
    	 	alert0.setContentText("NEOPHPDNO JE DA IZABERETE TERMIN IZVODJENJA, KOJI VAM NAJVISE ODGOVARA");
    	 	alert0.show();
    	}
    	else {
    		if(checkBoxBrojKarata.isSelected()) {
    			labelaBrojKarata.setVisible(true);
    			
    			int brojSjedistaUPoz = Pozoriste.dajPozoristeNaOsnovuId(Controller_nakonprijaveRadnika.vratiPozoristeID()).getBroj_sjedista();
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
    			//checkBoxBrojKarata.setSelected(false);
    			
    		}
    		else {
    			labelaBrojKarata.setText(" ");
    			
    		}
    		
    	}
    }  
    @FXML
    void prikazRezervacija(ActionEvent event) {
    	if(choiceBoxTermini.getValue() == null) {
    		Alert alert0 = new Alert(Alert.AlertType.WARNING);
    	 	alert0.setTitle("UPOZORENJE");
    	 	alert0.setContentText("NEOPHPDNO JE DA IZABERETE TERMIN IZVODJENJA, KOJI VAM NAJVISE ODGOVARA");
    	 	alert0.show();
    	}
    	else {
    		listaKarata.getItems().clear();
    		listaKarata.setVisible(true);
    		ArrayList<Karta> pomocnaListaKarta = new ArrayList<>();
    		for(Karta k : Karta.getKarta_lista()) {
    			if(k.getIzvodjenje_predstave_ID() == choiceBoxTermini.getValue().getID()) {
    				pomocnaListaKarta.add(k);
    			}
    		}
    		listaKarata.getItems().addAll(pomocnaListaKarta);
    		if(listaKarata.getItems().isEmpty()) {
    			labelaNemaRez.setVisible(true);
    			listaKarata.setVisible(false);
    		}
    		else {
    			listaKarata.setVisible(true);
    			labelaNemaRez.setVisible(false);
    		}
    	}
    }
    @FXML
    void imaNalog(ActionEvent event) {
    	if(imaNalogID.isSelected()) {
    	korisnickoIma.setVisible(true);
		korisnickoImeImatf.setVisible(true);
		lozinkaIma.setVisible(true);
		lozinkaImatf.setVisible(true);
		
	
		korisnickoImeImatf.setText("");
		lozinkaImatf.setText("");
		
		imeNematf.setVisible(false);
		imeLabela.setVisible(false);
		korisnickoImeNematf.setVisible(false);
		korisnickoNema.setVisible(false);
		lozinkaNema.setVisible(false);
		lozinkaNematf.setVisible(false);
		prezimeLabela.setVisible(false);
		prezimeNematf.setVisible(false);
		
    	}
    	else {
    		korisnickoIma.setVisible(false);
    		korisnickoImeImatf.setVisible(false);
    		lozinkaIma.setVisible(false);
    		lozinkaImatf.setVisible(false);
    	}
    }

    @FXML
    void nemaNalog(ActionEvent event) {
    	if(nemaNalogID.isSelected()) {
    		imeNematf.setVisible(true);
    		imeLabela.setVisible(true);
    		korisnickoImeNematf.setVisible(true);
    		korisnickoNema.setVisible(true);
    		lozinkaNema.setVisible(true);
    		lozinkaNematf.setVisible(true);
    		prezimeLabela.setVisible(true);
    		prezimeNematf.setVisible(true);
    		
    		imeNematf.setText("");
    		korisnickoImeNematf.setText("");
    		lozinkaNematf.setText("");
    		prezimeNematf.setText("");
    		
    		korisnickoIma.setVisible(false);
    		korisnickoImeImatf.setVisible(false);
    		lozinkaIma.setVisible(false);
    		lozinkaImatf.setVisible(false);
    	}
    	else {
    		imeNematf.setVisible(false);
    		imeLabela.setVisible(false);
    		korisnickoImeNematf.setVisible(false);
    		korisnickoNema.setVisible(false);
    		lozinkaNema.setVisible(false);
    		lozinkaNematf.setVisible(false);
    		prezimeLabela.setVisible(false);
    		prezimeNematf.setVisible(false);
    	}
    	
    }
    
    private Connection connect = null;
    private PreparedStatement statement = null;
    
    private PreparedStatement statementDodajPosj = null;
    private ResultSet resultStari = null;
    
    private PreparedStatement statementNovi = null;
    private PreparedStatement postojiNovi = null;
    private ResultSet resultNovi = null;
    
    
    @FXML
    void prodajaKarte(ActionEvent event) {
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
    	else if(!imaNalogID.isSelected() && !nemaNalogID.isSelected()) {
    		Alert alert0 = new Alert(Alert.AlertType.WARNING);
    	 	alert0.setTitle("UPOZORENJE");
    	 	alert0.setContentText("ODABERITE JEDNU OD OPCIJA, DA LI POSJETILAC VEC IME NALOG ILI NEMA!");
    	 	alert0.show();
    	}
    	else if(imaNalogID.isSelected() && (korisnickoImeImatf.getText().equals("") || lozinkaImatf.getText().equals(""))) {
    		Alert alert0 = new Alert(Alert.AlertType.WARNING);
    	 	alert0.setTitle("UPOZORENJE");
    	 	alert0.setContentText("NEKA POSJETILAC UNESE SVOJE KORISCNICKO IME I LOZINKU");
    	 	alert0.show();
    	}
    	else if(nemaNalogID.isSelected() && (imeNematf.getText().equals("") || korisnickoImeNematf.getText().equals("") || lozinkaNematf.getText().equals("") || prezimeNematf.getText().equals(""))) {
    		Alert alert0 = new Alert(Alert.AlertType.WARNING);
    	 	alert0.setTitle("UPOZORENJE");
    	 	alert0.setContentText("NEKA NOVI POSJETILAC UNESE INFORMACIJE O SEBI!");
    	 	alert0.show();
    	}
    	else if(Pozoriste.dajBrojSlobodnihSjedista(Pozoriste.dajPozoristeNaOsnovuId(Controller_nakonprijaveRadnika.vratiPozoristeID()), choiceBoxTermini.getValue()) < Integer.parseInt(textFieldBrojKarata.getText())) {
    		Alert alert0 = new Alert(Alert.AlertType.WARNING);
    	 	alert0.setTitle("UPOZORENJE");
    	 	String s = "Slobodan broj sjedista je " +Pozoriste.dajBrojSlobodnihSjedista(Pozoriste.dajPozoristeNaOsnovuId(Controller_nakonprijaveRadnika.vratiPozoristeID()), choiceBoxTermini.getValue()) +", a vama je potrebno " + textFieldBrojKarata.getText() + 
    	 			"\nDakle, za odabrani termin nije moguce izvrsiti rezervaciju. Molimo Vas da izaberete drugi termin!";
    		alert0.setContentText(s);
    	 	alert0.show();
    	}
    	else {
    	
    		
    		if(imaNalogID.isSelected()) {
    			boolean postojiLiPosjetilac = false;
    			boolean uslov = true;
    			boolean uslov1 = false;
    			Posjetilac_pozorista posj=null;
    			String posjIme = "";
    			for(Posjetilac_pozorista pp : Posjetilac_pozorista.getPosjetilac_pozorista_lista()) {
    				if(pp.getKorisnicko_ime().equals(korisnickoImeImatf.getText())) {
    					postojiLiPosjetilac = true;
    					if(!pp.getLozinka().equals(Posjetilac_pozorista.getMd5(lozinkaImatf.getText()))) {
    						lozinkaImatf.setText("");
    						Alert alert0 = new Alert(Alert.AlertType.WARNING);
        		    	 	alert0.setTitle("UPOZORENJE");
        		    	 	alert0.setContentText("Neipsravna lozinka za " + korisnickoImeImatf.getText() + " .Pokusajte ponovo");
        		    	 	alert0.show();
    					}
    					else {
    						uslov1=true;
    						
    						posj = pp;
    						posjIme =pp.getIme();
    						for(Karta k : Karta.getKarta_lista()) {
    							if(k.getIzvodjenje_predstave_ID() == choiceBoxTermini.getValue().getID())
    								if(k.getPosjetilac_ID() == pp.getID() && k.getStatus() == 2) {
	    								uslov=false;
	    								Alert alert0 = new Alert(Alert.AlertType.WARNING);
	    	        		    	 	alert0.setTitle("UPOZORENJE");
	    	        		    	 	alert0.setContentText("Posjetioce, " + pp.getIme() + ", Vi ste vec rezervisali kartu za ovo izvodjenje i niste je preuzeli, te nije moguce da vam je prodam!");
	    	        		    	 	alert0.show();
    							}
    							
    						}
    				
    					}
    				}
    			
    			}
    			if(postojiLiPosjetilac && uslov && uslov1) {
    				
    				
						Alert alert0 = new Alert(Alert.AlertType.CONFIRMATION);
					 	alert0.setTitle("POTVRDA PRODAJE");
					 	
						//REZIME INFORMACIJA
					 	String s = "Rezime informacija: Posjetioc: " + posjIme + " je izabrao: \n";
					 	s+= "Pozoriste: " + Pozoriste.dajPozoristeNaOsnovuId(Controller_nakonprijaveRadnika.vratiPozoristeID());
					 	s+= "Predstava" + cbListaPredstava.getValue();
					 	s += "Termin izvodjenja predstave: " + choiceBoxTermini.getValue()+"\n";
					 	s += "Broj karata za rezervaiju: " + textFieldBrojKarata.getText();
					 	alert0.setContentText(s);
					 	Optional <ButtonType>result = alert0.showAndWait();
					 	boolean posmatranje = false;
					 	if(result.get()==ButtonType.OK) {
					 		connect = Konekcija.getConnection();
					 		for(int i =0; i<Integer.parseInt(textFieldBrojKarata.getText());i++) {
					 			try {
						 			statement = connect.prepareStatement("INSERT INTO karta (izvodjenje_predstave_id, status, posjetilac_id, broj_karta) VALUE (?,?,?,?)");
				    				statement.setInt(1, choiceBoxTermini.getValue().getID());
				    				statement.setInt(2, 1);
				    				statement.setInt(3, posj.getID());
				    				statement.setInt(4, Karta.sljedeciSlobodanBroj( choiceBoxTermini.getValue().getID(), Pozoriste.dajPozoristeNaOsnovuId(Controller_nakonprijaveRadnika.vratiPozoristeID()).getBroj_sjedista()));
				    				statement.executeUpdate();
						 			
				    				Konekcija.ucitajKartu();
				    				posmatranje=true;
						 				
						 			
						 			
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
				        		alert4.setContentText("Uspjesno ste prodali kartu!");
				        		Optional <ButtonType>result1 = alert4.showAndWait();
				    		 	if(result1.get()==ButtonType.OK) {
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
					 
    			}
    			
        			
        			
    		}
    			 if(!postojiLiPosjetilac) {
    				 
    				Alert alert0 = new Alert(Alert.AlertType.WARNING);
		    	 	alert0.setTitle("UPOZORENJE");
		    	 	alert0.setContentText("Koriscnicko ime " + korisnickoImeImatf.getText() + " ne postoji!");
		    	 	alert0.show();
		    	 	korisnickoImeImatf.setText("");
		    	 	lozinkaImatf.setText("");
    			}
    			
    	}
    		if(nemaNalogID.isSelected()) {
    			boolean postojiKorisnicko = false;
    			for(Posjetilac_pozorista pp : Posjetilac_pozorista.getPosjetilac_pozorista_lista()) 
    				if(pp.getKorisnicko_ime().equals(korisnickoImeNematf.getText())) {
    					postojiKorisnicko = true;
    					break;
    				}
    			if(postojiKorisnicko) {
    				Alert alert0 = new Alert(Alert.AlertType.WARNING);
		    	 	alert0.setTitle("UPOZORENJE");
		    	 	alert0.setContentText("Korisnciko ime " + korisnickoImeNematf.getText() + " vec postoji. Molimo Vas da unesete drugo!");
		    	 	alert0.show();
		    	 	korisnickoImeNematf.setText("");
		    	 	imeNematf.setText("");
		    	 	prezimeNematf.setText("");
		    	 	lozinkaNematf.setText("");
    			}
    			else {
    				connect = Konekcija.getConnection();
    				boolean dodavanjeposj = false;
    				try {
    					statementDodajPosj = connect.prepareStatement("INSERT INTO posjetilac_pozorista (ime, prezime, korisnicko_ime, lozinka) VALUE (?,?,?,?)");
    					statementDodajPosj.setString(1, imeNematf.getText());
    					statementDodajPosj.setString(2, prezimeNematf.getText());
    					statementDodajPosj.setString(3, korisnickoImeNematf.getText());
    					statementDodajPosj.setString(4, Posjetilac_pozorista.getMd5(lozinkaNematf.getText()));
    					statementDodajPosj.executeUpdate();
    					Konekcija.ucitajPosjetioca();
    					dodavanjeposj = true;
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
		 		if(dodavanjeposj) {
		 			
		 			Alert alert0 = new Alert(Alert.AlertType.CONFIRMATION);
				 	alert0.setTitle("POTVRDA PRODAJE");
				 	
					//REZIME INFORMACIJA
				 	String s = "Rezime informacija: Posjetioc: " + imeNematf.getText() + " je izabrao: \n";
				 	s+= "Pozoriste: " + Pozoriste.dajPozoristeNaOsnovuId(Controller_nakonprijaveRadnika.vratiPozoristeID());
				 	s+= "Predstava" + cbListaPredstava.getValue();
				 	s += "Termin izvodjenja predstave: " + choiceBoxTermini.getValue()+"\n";
				 	s += "Broj karata za rezervaiju: " + textFieldBrojKarata.getText();
				 	alert0.setContentText(s);
				 	Optional <ButtonType>result = alert0.showAndWait();
				 	boolean posmatranje = false;
				 	if(result.get()==ButtonType.OK) {
				 		connect = Konekcija.getConnection();
				 		for(int i =0; i<Integer.parseInt(textFieldBrojKarata.getText());i++) {
				 			try {
					 			statement = connect.prepareStatement("INSERT INTO karta (izvodjenje_predstave_id, status, posjetilac_id, broj_karta) VALUE (?,?,?,?)");
			    				statement.setInt(1, choiceBoxTermini.getValue().getID());
			    				statement.setInt(2, 1);
			    				statement.setInt(3, Posjetilac_pozorista.dajPosjetiocaNaOsnovuStringa(korisnickoImeNematf.getText()).getID());
			    				statement.setInt(4, Karta.sljedeciSlobodanBroj( choiceBoxTermini.getValue().getID(), Pozoriste.dajPozoristeNaOsnovuId(Controller_nakonprijaveRadnika.vratiPozoristeID()).getBroj_sjedista()));
			    				statement.executeUpdate();
					 			
			    				Konekcija.ucitajKartu();
			    				posmatranje=true;
					 				
					 			
					 			
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
			        		alert4.setContentText("Uspjesno ste prodali kartu!");
			        		Optional <ButtonType>result1 = alert4.showAndWait();
			    		 	if(result1.get()==ButtonType.OK) {
			    		 		try {
			    					Parent root = FXMLLoader.load(getClass().getResource("Scena_RadnikRadNaBlagajni.fxml"));
			    					Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			    					Scene scene = new Scene(root);
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
    	}
    }
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Konekcija.ucitajKartu();
		// TODO Auto-generated method stub
		ArrayList<Predstava> pomocnaListaPredstava = new ArrayList<>();
		
		for(Izvodjenje_predstave ip : Izvodjenje_predstave.getLista_izvodjenje_predstave()) {
			if(ip.getPozoriste_ID() == Controller_nakonprijaveRadnika.vratiPozoristeID()) {
				
				if(Izvodjenje_predstave.daLiSeIzvodiUNarednomPeriodu(ip.getDatum_i_vrijeme())) {
					if(!pomocnaListaPredstava.contains(Predstava.vratiPredstavu(ip.getPredstava_ID())))
					pomocnaListaPredstava.add(Predstava.vratiPredstavu(ip.getPredstava_ID()));
				}
				
			}
		}
		
		cbListaPredstava.getItems().addAll(pomocnaListaPredstava);
		
		//////////////////////
		choiceBoxGlumci.setVisible(false);
		imeAutora.setVisible(false);
		imeRezisera.setVisible(false);
		nestani.setVisible(false);
		nestani2.setVisible(false);
		nestani4.setVisible(false);
		predstavinoIme.setVisible(false);
		predstavinoIme1.setVisible(false);
		predstavinoIme11.setVisible(false);
		///////////////////////////
		checkBoxBrojKarata.setVisible(false);
		choiceBoxTermini.setVisible(false);
		cijenaLabela.setVisible(false);
		nestani10.setVisible(false);
		nestani9.setVisible(false);
		/////////////////////////////////////
		labelaBrojKarata.setVisible(false);//prikazi je nakon sto se klikne dugme checkBoxBrojKarata
		///////////////////////////
		nemaslkarata.setVisible(false);// ukolik
		///////////////////////
		buttonPrikazRezervacija.setVisible(false);
		///////////////////////////
		listaKarata.setVisible(false);
		labelaNemaRez.setVisible(false);
		//////////////////////////////
		imaNalogID.setVisible(false);
		nemaNalogID.setVisible(false);
		nestaniLabela.setVisible(false);
		textFieldBrojKarata.setVisible(false);
		nestani12.setVisible(false);
		///////////////////////////
		korisnickoIma.setVisible(false);
		korisnickoImeImatf.setVisible(false);
		lozinkaIma.setVisible(false);
		lozinkaImatf.setVisible(false);
		////////////////////////
		imeNematf.setVisible(false);
		imeLabela.setVisible(false);
		korisnickoImeNematf.setVisible(false);
		korisnickoNema.setVisible(false);
		lozinkaNema.setVisible(false);
		lozinkaNematf.setVisible(false);
		prezimeLabela.setVisible(false);
		prezimeNematf.setVisible(false);
		////////////////////////////
		buttonProdajaKarte.setVisible(false);
	}
}
