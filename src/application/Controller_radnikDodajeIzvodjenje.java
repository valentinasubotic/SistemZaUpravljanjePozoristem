package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller_radnikDodajeIzvodjenje implements Initializable{

    @FXML
    private ChoiceBox<Predstava> choiceBoxPredstave;
    @FXML
    private Label ispisPorukuOCijeni;

    @FXML
    private Label unesiCijenu;

    @FXML
    private TextField unesiCijenuTf;
    
    @FXML
    private DatePicker datum;

    @FXML
    private Label datumLabel;
    @FXML
    private Button buttonDodaj;

    @FXML
    private Label dvotacka;

    @FXML
    private Label dvotacka1;

    @FXML
    private TextField hh;
    @FXML
    private TextField mm;

    @FXML
    private TextField ss;
    @FXML
    private Label unesiteSatLabela;
    
    Double cijena1;
    boolean uslov;


	  @FXML
	    void switchToNakonPrijaveRadnika(ActionEvent event) {
		  Alert alert0 = new Alert(Alert.AlertType.CONFIRMATION);
		 	alert0.setTitle("IZLAZAK");
		 	alert0.setContentText("Da li ste sigurni da zelite da se vratite na pocetnu stranicu?");
		 	
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
	
		
	  @FXML
	  void potvrdaChoiceBoxPredstave(ActionEvent event) {
		  if(choiceBoxPredstave.getValue() == null) {
	    		Alert alert0 = new Alert(Alert.AlertType.WARNING);
	    	 	alert0.setTitle("UPOZORENJE");
	    	 	alert0.setContentText("NEOPHPDNO JE DA IZABERETE PREDSTAVU");
	    	 	alert0.show();
	    	}
		  else {
				dvotacka.setVisible(true);
				dvotacka1.setVisible(true);
				unesiteSatLabela.setVisible(true);
				hh.setVisible(true);
				mm.setVisible(true);
				ss.setVisible(true);
			  buttonDodaj.setVisible(true);
			  datum.setVisible(true);
				datumLabel.setVisible(true);
			  ispisPorukuOCijeni.setVisible(false);
			  unesiCijenu.setVisible(false);
				unesiCijenuTf.setVisible(false);
			   uslov = false;
			  for(Izvodjenje_predstave ip : Izvodjenje_predstave.getLista_izvodjenje_predstave()) {
				  if(ip.getPredstava_ID() == choiceBoxPredstave.getValue().getID() && ip.getPozoriste_ID() == Controller_nakonprijaveRadnika.vratiPozoristeID()) {
					  ispisPorukuOCijeni.setVisible(true);
					  ispisPorukuOCijeni.setText(choiceBoxPredstave.getValue().getNaziv() + " se izvodi vec \nu pozoristu " + 
					  Pozoriste.dajPozoristeNaOsnovuId(Controller_nakonprijaveRadnika.vratiPozoristeID()).getNaziv() + "\nTe cijena izvodjenja predstave je " + 
							  Double.toString(ip.getCijena()));
					  cijena1 = ip.getCijena();
					  uslov=true;
					  break;
				  }
			  }
			  if(!uslov) {
				    unesiCijenu.setVisible(true);
					unesiCijenuTf.setVisible(true);
			  }
		  }
		  
	  }
	    private Connection connect = null;
	    private PreparedStatement statement = null;

	   @FXML
	   void dodajIzvodjenje(ActionEvent event) {
		  
		   if(datum.getValue() == null && hh.getText().equals("") && ss.getText().equals("") && mm.getText().equals("")) {
			   Alert alert0 = new Alert(Alert.AlertType.WARNING);
	    	 	alert0.setTitle("UPOZORENJE");
	    	 	alert0.setContentText("NEOPHPDNO JE DA UNESETE DATUM I VRIJEME!");
	    	 	alert0.show();
		   }
		   else if(datum.getValue() == null) {
			   Alert alert0 = new Alert(Alert.AlertType.WARNING);
	    	 	alert0.setTitle("UPOZORENJE");
	    	 	alert0.setContentText("NEOPHPDNO JE DA UNESETE DATUM!");
	    	 	alert0.show();
		   }
		   else if(hh.getText().equals("") && ss.getText().equals("") && mm.getText().equals("")) {
			   Alert alert0 = new Alert(Alert.AlertType.WARNING);
	    	 	alert0.setTitle("UPOZORENJE");
	    	 	alert0.setContentText("NEOPHPDNO JE DA UNESETE VRIJEME!");
	    	 	alert0.show();
		   }
		   else if(hh.getText().equals("") || ss.getText().equals("") || mm.getText().equals("")) {
			   Alert alert0 = new Alert(Alert.AlertType.WARNING);
	    	 	alert0.setTitle("UPOZORENJE");
	    	 	alert0.setContentText("NEOPHPDNO JE DA POPUNITE SVA POLJA ZA SATE, MINUTE I SEKUNDE!");
	    	 	alert0.show();
		   }
		   else if((Integer.valueOf(hh.getText())<0 || Integer.valueOf(hh.getText())>60) || (Integer.valueOf(mm.getText())<0 || Integer.valueOf(mm.getText())>60) || (Integer.valueOf(ss.getText())<0 || Integer.valueOf(ss.getText())>60) ) {
			   Alert alert0 = new Alert(Alert.AlertType.WARNING);
	    	 	alert0.setTitle("UPOZORENJE");
	    	 	alert0.setContentText("UNESITE VRIJEDNOSTI ISPRAVNE VRIJEDNOSTI SATA!");
	    	 	alert0.show();
		   }
		   else if(!uslov && unesiCijenuTf.getText().equals("")) {
			   Alert alert0 = new Alert(Alert.AlertType.WARNING);
	    	 	alert0.setTitle("UPOZORENJE");
	    	 	alert0.setContentText("NEOPHPDNO JE DA UNESETE CIJENU!");
	    	 	alert0.show();
		   }
		   else if(datum.getValue().isBefore(LocalDate.now())) {
			   Alert alert0 = new Alert(Alert.AlertType.WARNING);
	    	 	alert0.setTitle("UPOZORENJE");
	    	 	alert0.setContentText("IZABRALI STE DATUM KOJI JE PROSAO, NEOPHODNO JE DA DODATE DATUM KOJI SLJIEDI!");
	    	 	alert0.show();
		   }

		   else if(hh.getText().length() !=2 || mm.getText().length() != 2 || ss.getText().length() != 2) {
			   Alert alert0 = new Alert(Alert.AlertType.WARNING);
	    	 	alert0.setTitle("UPOZORENJE");
	    	 	alert0.setContentText("NEISPRAVAN UNOS SATA, SVAKO POLJE MORA DA SADRTI DVIJE CIFRE");
	    	 	alert0.show();
		   }
		   else if(datum.getValue().isEqual(LocalDate.now()) && LocalTime.parse(hh.getText() + ":" + mm.getText() + ":" + ss.getText()).isBefore(LocalTime.now()) ) {
				   Alert alert0 = new Alert(Alert.AlertType.WARNING);
		    	 	alert0.setTitle("UPOZORENJE");
		    	 	alert0.setContentText("NEISPRAVAN IZBOR SATA, IZABRALI STE VRIJEME KOJE JE PROSLO!");
		    	 	hh.setText("");
		    	 	mm.setText("");
		    	 	ss.setText("");
		    	 	alert0.show();
		   }
		  
		   else {
			   
			   
		   	if(!uslov) {
		   		cijena1 = Double.parseDouble(unesiCijenuTf.getText());
		   	}
		   	DateTimeFormatter formatter =DateTimeFormatter.ofPattern("yyyy-MM-dd");
		   	LocalDate lokalDate = datum.getValue();
		   	String datumivrijeme="";
		   	if(lokalDate != null) {
		   		datumivrijeme = formatter.format(lokalDate);
		   	}
		   	String vrijeme = hh.getText() + ":" + mm.getText() + ":" + ss.getText();
		   	datumivrijeme += " " + vrijeme;
		   	
		   	Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
		   	alert1.setTitle("POTVRDA DODAVANJA TERMINA IZVODJENJA");
		   	String s = "Rezime informacija.Radnice, " + Controller_radnik.vratiImePrezime() + " u pozoristu " + Pozoriste.dajPozoristeNaOsnovuId(Controller_nakonprijaveRadnika.vratiPozoristeID()).getNaziv() + " odabrali ste sljedece:\n";
		   	s += "Predstava " + Predstava.dajNazivPredstave(choiceBoxPredstave.getValue().getID()) + ".\n";
		   	s += "Cijena: " + cijena1;
		   	s += "\nDatum izvodjenja: " + datumivrijeme;
		   	alert1.setContentText(s);
		   	Optional <ButtonType>result = alert1.showAndWait();
		 	boolean posmatranje = false;
		 	if(result.get()==ButtonType.OK) {
		   
		   	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   	Timestamp ts;
		   	
		   	try {
		   	Date parsed;
			parsed = df.parse(datumivrijeme);
			 ts= new Timestamp(parsed.getTime());
			 
				connect = Konekcija.getConnection();
			   	try {
			   		if(Izvodjenje_predstave.daLiPostojiIzvodjenje( choiceBoxPredstave.getValue().getID(), Controller_nakonprijaveRadnika.vratiPozoristeID(), cijena1, ts)){
			   		   Alert alert0 = new Alert(Alert.AlertType.WARNING);
			    	 	alert0.setTitle("UPOZORENJE");
			    	 	alert0.setContentText("Unijeto izvodjenje predstave vec postoji!");
			    	 	alert0.show();
			   		}
			   		else {
			   			statement = connect.prepareStatement("INSERT INTO izvodjenje_predstave (predstava_id,pozoriste_id,cijena,datum_i_vrijeme) VALUE (?,?,?,?)");
			   			
	    				statement.setInt(1, choiceBoxPredstave.getValue().getID());
	    				statement.setInt(2, Controller_nakonprijaveRadnika.vratiPozoristeID());
	    				statement.setDouble(3, cijena1);
	    				statement.setTimestamp(4, ts);
	    				statement.executeUpdate();
	    				
	    				 Konekcija.ucitajIzvodjenjePredstave();
	    				posmatranje =true;
	    				
	    				
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
		    catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   	if(posmatranje) {
	    		Alert alert4 = new Alert(Alert.AlertType.INFORMATION);
        		alert4.setTitle("OBAVJESTENJE!");
        		alert4.setContentText("Uspjesno ste dodali izvodjenje predstave!");
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
		 	}
		   	
		   	
		   	
		   	
		   	
		   	
		   	
		   }
		   
	    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		choiceBoxPredstave.getItems().addAll(Predstava.getLista_predstava());
		
		ispisPorukuOCijeni.setVisible(false);
		unesiCijenu.setVisible(false);
		unesiCijenuTf.setVisible(false);
		
		datum.setVisible(false);
		datumLabel.setVisible(false);
		
		buttonDodaj.setVisible(false);
		
		dvotacka.setVisible(false);
		dvotacka1.setVisible(false);
		unesiteSatLabela.setVisible(false);
		hh.setVisible(false);
		mm.setVisible(false);
		ss.setVisible(false);
	}

}
