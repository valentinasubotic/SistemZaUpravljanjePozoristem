package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.Optional;

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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller_radnikDodajePredstavu implements Initializable{
    		@FXML
    		private ChoiceBox<String> choiceBox;
			@FXML
    	    private Button buttonDodajAutora;

    	    @FXML
    	    private Button buttonDodajGlumca;

    	    @FXML
    	    private Button buttonDodajRezisera;
    	    
    	    @FXML
    	    private Button button_dodajPredstavu;
    	    
    	    @FXML
    	    private Label labelime;

    	    @FXML
    	    private Label labelimeA;

    	    @FXML
    	    private Label labelimeR;

    	    @FXML
    	    private Label labelprezime;

    	    @FXML
    	    private Label labelprezimeA;

    	    @FXML
    	    private Label labelprezimeR;

	    @FXML
	    private ListView<Osoblje> listaAutori;

	    @FXML
	    private ListView<Osoblje> listaGlumci;

	    @FXML
	    private ListView<Osoblje> listaReziseri;

	    @FXML
	    private TextField tf_naziv;//naziv predstave


	    @FXML
	    private TextField tf_imeAutora;

	    @FXML
	    private TextField tf_imeGlumca;

	    @FXML
	    private TextField tf_imeRezisera;
	    
	    @FXML
	    private TextField tf_prezimeAutora;

	    @FXML
	    private TextField tf_prezimeGlumca;

	    @FXML
	    private TextField tf_prezimeRezisera;
	

	   
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
	 public void dodajNovogGlumca(ActionEvent event) {
		 	labelime.setVisible(true);
			tf_imeGlumca.setVisible(true);
			labelprezime.setVisible(true);
			tf_prezimeGlumca.setVisible(true);
			buttonDodajGlumca.setVisible(true);
	    	
	    }
	 
	 public void dodajNovogAutora(ActionEvent event) {
			labelimeA.setVisible(true);
			tf_imeAutora.setVisible(true);
			labelprezimeA.setVisible(true);
			tf_prezimeAutora.setVisible(true);
			buttonDodajAutora.setVisible(true);
	    }
	 public void dodajNovogRezisera(ActionEvent event) {
			labelimeR.setVisible(true);
			tf_imeRezisera.setVisible(true);
			labelprezimeR.setVisible(true);
			tf_prezimeRezisera.setVisible(true);
			buttonDodajRezisera.setVisible(true);
	    }
	 private Connection connect = null;
	 private PreparedStatement statement = null;
	 private PreparedStatement statement1 = null;
	 private PreparedStatement statement2 = null;
	 private PreparedStatement statement3 = null;
	 
	@FXML
	public void dodajGlumca(ActionEvent event) {
		if(tf_imeGlumca.getText().equals("") && tf_prezimeGlumca.getText().equals("")) {
    		Alert alert1 = new Alert(Alert.AlertType.ERROR);
			alert1.setContentText("Popunite polja");
			alert1.show();
    	}
    	else if(tf_imeGlumca.getText().equals("") || tf_prezimeGlumca.getText().equals("")) {
    		Alert alert4 = new Alert(Alert.AlertType.ERROR);
			alert4.setContentText("Sva polja moraju biti popunjena");
			alert4.show();
    	}
    	else {
	    	connect = Konekcija.getConnection();
	    	try {
	    			if(Osoblje.daLiPostojiOsoblje(1,tf_imeGlumca.getText(),tf_prezimeGlumca.getText())) {
	    				Alert alert = new Alert(Alert.AlertType.ERROR);
	    				alert.setContentText("Glumac " + tf_prezimeGlumca.getText() + ", " + tf_imeGlumca.getText() + " se vec nalazi u listi!\nUnesite novog glumca!");
	    				alert.show();
	    			}
	    			else {
	    				statement = connect.prepareStatement("INSERT INTO osoblje (ime, prezime, tip) VALUE (?,?,?)");
	    				statement.setString(1, tf_imeGlumca.getText());
	    				statement.setString(2, tf_prezimeGlumca.getText());
	    				statement.setInt(3, 1);
	    				statement.executeUpdate();
	    				
	    				Alert alert4 = new Alert(Alert.AlertType.INFORMATION);
		        		alert4.setTitle("OBAVJESTENJE!");
		        		alert4.setContentText("Uspjesno ste dodali glumca!");
		        		alert4.show();
		        		
		        		int i = Osoblje.vratiDuzinuListe(1);
		        		Konekcija.ucitajOsoblje();
	    				listaGlumci.getItems().add(i,Osoblje.vratiNaOsnovuImenaPrezimena(1, tf_imeGlumca.getText(), tf_prezimeGlumca.getText()));
	    				listaGlumci.getSelectionModel().select(i);
	    	
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
	@FXML
	public void dodajAutora(ActionEvent event) {
		if(tf_imeAutora.getText().equals("") && tf_prezimeAutora.getText().equals("")) {
    		Alert alert1 = new Alert(Alert.AlertType.ERROR);
			alert1.setContentText("Popunite polja");
			alert1.show();
    	}
    	else if(tf_imeAutora.getText().equals("") || tf_prezimeAutora.getText().equals("")) {
    		Alert alert4 = new Alert(Alert.AlertType.ERROR);
			alert4.setContentText("Sva polja moraju biti popunjena");
			alert4.show();
    	}
    	else {
	    	connect = Konekcija.getConnection();
	    	try {
	    			if(Osoblje.daLiPostojiOsoblje(3,tf_imeAutora.getText(),tf_prezimeAutora.getText())) {
	    				Alert alert = new Alert(Alert.AlertType.ERROR);
	    				alert.setContentText("Autor " + tf_prezimeAutora.getText() + ", " + tf_imeAutora.getText() + " se vec nalazi u listi!\nUnesite novog autora!");
	    				alert.show();
	    			}
	    			else {
	    				statement = connect.prepareStatement("INSERT INTO osoblje (ime, prezime, tip) VALUE (?,?,?)");
	    				statement.setString(1, tf_imeAutora.getText());
	    				statement.setString(2, tf_prezimeAutora.getText());
	    				statement.setInt(3, 3);
	    				statement.executeUpdate();
	    				
	    				Alert alert4 = new Alert(Alert.AlertType.INFORMATION);
		        		alert4.setTitle("OBAVJESTENJE!");
		        		alert4.setContentText("Uspjesno ste dodali autora!");
		        		alert4.show();
		        		
		        		int i = Osoblje.vratiDuzinuListe(3);
		        		Konekcija.ucitajOsoblje();
	    				listaAutori.getItems().add(i,Osoblje.vratiNaOsnovuImenaPrezimena(3, tf_imeAutora.getText(), tf_prezimeAutora.getText()));
	    				listaAutori.getSelectionModel().select(i);
	    	
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
	@FXML
	public void dodajRezisera(ActionEvent event) {
		if(tf_imeRezisera.getText().equals("") && tf_prezimeRezisera.getText().equals("")) {
    		Alert alert1 = new Alert(Alert.AlertType.ERROR);
			alert1.setContentText("Popunite polja");
			alert1.show();
    	}
    	else if(tf_imeRezisera.getText().equals("") || tf_prezimeRezisera.getText().equals("")) {
    		Alert alert4 = new Alert(Alert.AlertType.ERROR);
			alert4.setContentText("Sva polja moraju biti popunjena");
			alert4.show();
    	}
    	else {
	    	connect = Konekcija.getConnection();
	    	try {
	    			if(Osoblje.daLiPostojiOsoblje(2,tf_imeRezisera.getText(),tf_prezimeRezisera.getText())) {
	    				Alert alert = new Alert(Alert.AlertType.ERROR);
	    				alert.setContentText("Reziser " + tf_prezimeRezisera.getText() + ", " + tf_imeRezisera.getText() + " se vec nalazi u listi!\nUnesite novog rezisera!");
	    				alert.show();
	    			}
	    			else {
	    				statement = connect.prepareStatement("INSERT INTO osoblje (ime, prezime, tip) VALUE (?,?,?)");
	    				statement.setString(1, tf_imeRezisera.getText());
	    				statement.setString(2, tf_prezimeRezisera.getText());
	    				statement.setInt(3, 2);
	    				statement.executeUpdate();
	    				
	    				Alert alert4 = new Alert(Alert.AlertType.INFORMATION);
		        		alert4.setTitle("OBAVJESTENJE!");
		        		alert4.setContentText("Uspjesno ste dodali rezisera!");
		        		alert4.show();
		        		
		        		int i = Osoblje.vratiDuzinuListe(2);
		        		Konekcija.ucitajOsoblje();
	    				listaReziseri.getItems().add(i,Osoblje.vratiNaOsnovuImenaPrezimena(2, tf_imeRezisera.getText(), tf_prezimeRezisera.getText()));
	    				listaReziseri.getSelectionModel().select(i);
	    	
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

	@FXML
	public void dodajPredstavu(ActionEvent event) {
		if(tf_naziv.getText().equals("") && choiceBox.getValue() == null && listaGlumci.getSelectionModel().getSelectedItem() == null && listaAutori.getSelectionModel().getSelectedItem() == null && listaReziseri.getSelectionModel().getSelectedItem() == null) {
    		Alert alert1 = new Alert(Alert.AlertType.ERROR);
			alert1.setContentText("Neophodno je da unesete informacije o predstavi!");
			alert1.show();
    	}
		
    	else if(tf_naziv.getText().equals("") ||choiceBox.getValue() == null) {
    		Alert alert4 = new Alert(Alert.AlertType.ERROR);
			alert4.setContentText("Unesite naziv i zanr predstave");
			alert4.show();
    	}
    	else if(listaGlumci.getSelectionModel().getSelectedItem() == null && listaAutori.getSelectionModel().getSelectedItem() == null && listaReziseri.getSelectionModel().getSelectedItem() == null) {
    		Alert alert2 = new Alert(Alert.AlertType.ERROR);
			alert2.setContentText("Neophpdno je da izaberete glumca, autora i rezisera!");
			alert2.show();
    	}
    	else if(listaGlumci.getSelectionModel().getSelectedItem() == null ) {
    		Alert alert2 = new Alert(Alert.AlertType.ERROR);
			alert2.setContentText("Izaberite bar jednog glumca, a mozete i vise ili dodajte novog glumca!");
			alert2.show();
    	}
    	else if(listaAutori.getSelectionModel().getSelectedItem() == null ) {
    		Alert alert2 = new Alert(Alert.AlertType.ERROR);
			alert2.setContentText("Izaberite tacno jednog autora ili dodajte novog autora!");
			alert2.show();
    	}
    	else if(listaReziseri.getSelectionModel().getSelectedItem() == null ) {
    		Alert alert2 = new Alert(Alert.AlertType.ERROR);
			alert2.setContentText("Izaberite tacno jednog rezisera ili dodajte novog rezisera!");
			alert2.show();
    	}
    	else {
	    	connect = Konekcija.getConnection();
	    	try {
	    			if(Predstava.daLiPostojiPredstava(tf_naziv.getText(), Predstava.izStringaUInt(choiceBox.getValue()))) {
	    				Alert alert = new Alert(Alert.AlertType.ERROR);
	    				alert.setContentText("Predstava " + tf_naziv.getText() + ",zanra " + choiceBox.getValue() + " vec postoji.\nUnesite novi naziv predstave!\n");
	    				alert.show();
	    			}
	    			else {
	    				statement = connect.prepareStatement("INSERT INTO predstava (naziv, zanr) VALUE (?,?)");
	    				statement.setString(1, tf_naziv.getText());
	    				statement.setInt(2, Predstava.izStringaUInt(choiceBox.getValue()));
	    				statement.executeUpdate();
	    				
	    				Konekcija.ucitajPredstavu();
	    				int PredstavaID = Predstava.vratiPredstavinID(tf_naziv.getText(), Predstava.izStringaUInt(choiceBox.getValue()));
	    				
	    				
	    			
		        		ObservableList<Osoblje> selectedGlumci = listaGlumci.getSelectionModel().getSelectedItems();
		        		Osoblje selectedAutor = listaAutori.getSelectionModel().getSelectedItem();
		        		Osoblje selectedReziser = listaReziseri.getSelectionModel().getSelectedItem();
		        		
		        		statement1 = connect.prepareStatement("INSERT INTO osoblje_predstave (osoblje_id, predstava_id) VALUE (?,?)");
		        		for(Osoblje selectedGlumac : selectedGlumci) {
		        			statement1.setInt(1,selectedGlumac.getID());
		        			statement1.setInt(2, PredstavaID);
		        			statement1.executeUpdate();
		        		}
		        		
		        		statement2 = connect.prepareStatement("INSERT INTO osoblje_predstave (osoblje_id, predstava_id) VALUE (?,?)");
		        		statement2.setInt(1,selectedAutor.getID());
		        		statement2.setInt(2, PredstavaID);
		        		statement2.executeUpdate();
		        		
		        		statement3 = connect.prepareStatement("INSERT INTO osoblje_predstave (osoblje_id, predstava_id) VALUE (?,?)");
		        		statement3.setInt(1,selectedReziser.getID());
		        		statement3.setInt(2, PredstavaID);
		        		statement3.executeUpdate();
		        		 
		        		Konekcija.ucitajOsobljePredstave();
		        		
		        		Alert alert4 = new Alert(Alert.AlertType.INFORMATION);
		        		alert4.setTitle("OBAVJESTENJE!");
		        		alert4.setContentText("Uspjesno ste dodali predstavu!");
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
		ArrayList<String> zanrovi = new ArrayList<>();
		
		zanrovi.add("komedija");
		zanrovi.add("farsa");
		zanrovi.add("satira");
		zanrovi.add("komedija restauracije");
		zanrovi.add("tragedija");
		zanrovi.add("istorija");
		zanrovi.add("mjuzikl");
		
		choiceBox.getItems().addAll(zanrovi);

		
		//glumac
		labelime.setVisible(false);
		tf_imeGlumca.setVisible(false);
		labelprezime.setVisible(false);
		tf_prezimeGlumca.setVisible(false);
		buttonDodajGlumca.setVisible(false);
		//autor
		labelimeA.setVisible(false);
		tf_imeAutora.setVisible(false);
		labelprezimeA.setVisible(false);
		tf_prezimeAutora.setVisible(false);
		buttonDodajAutora.setVisible(false);
		//reziser
		labelimeR.setVisible(false);
		tf_imeRezisera.setVisible(false);
		labelprezimeR.setVisible(false);
		tf_prezimeRezisera.setVisible(false);
		buttonDodajRezisera.setVisible(false);
		
		ArrayList <Osoblje> pomocnaListaGlumci = new ArrayList<>();
		for(Osoblje g : Osoblje.getLista_osoblja()) {
			if(g.getStringTip(g.getTip()).equals("glumac")) {
				pomocnaListaGlumci.add(g);
			}
		}
		
		listaGlumci.getItems().addAll(pomocnaListaGlumci);
		listaGlumci.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		ArrayList <Osoblje> pomocnaListaAutori = new ArrayList<>();
		for(Osoblje g : Osoblje.getLista_osoblja()) {
			if(g.getStringTip(g.getTip()).equals("autor")) {
				pomocnaListaAutori.add(g);
			}
		}
		listaAutori.getItems().addAll(pomocnaListaAutori);
		
		ArrayList <Osoblje> pomocnaListaReziseri = new ArrayList<>();
		for(Osoblje g : Osoblje.getLista_osoblja()) {
			if(g.getStringTip(g.getTip()).equals("reziser")) {
				pomocnaListaReziseri.add(g);
			}
		}
		listaReziseri.getItems().addAll(pomocnaListaReziseri);
		
	}

	
}

