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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Controller_posjetilacSpisakOsoblja implements Initializable{
	 @FXML
	    private TableColumn<PomocnaKlasa, SimpleStringProperty> imeKolona;

	    @FXML
	    private TableColumn<PomocnaKlasa, SimpleStringProperty> predstavaKolona;

	    @FXML
	    private TableColumn<PomocnaKlasa, SimpleStringProperty> prezimeKolona;

	    @FXML
	    private TableView<PomocnaKlasa> tabela;

	    @FXML
	    private TableColumn<PomocnaKlasa, SimpleStringProperty> tipKolona;

	    @FXML
	    private TableColumn<PomocnaKlasa, SimpleStringProperty> zanrKolona;
	    
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
	    
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		tipKolona.setCellValueFactory(new PropertyValueFactory<PomocnaKlasa, SimpleStringProperty>("tip"));
		imeKolona.setCellValueFactory(new PropertyValueFactory<PomocnaKlasa, SimpleStringProperty>("ime"));
		prezimeKolona.setCellValueFactory(new PropertyValueFactory<PomocnaKlasa, SimpleStringProperty>("prezime"));
		predstavaKolona.setCellValueFactory(new PropertyValueFactory<PomocnaKlasa, SimpleStringProperty>("predstava"));
		zanrKolona.setCellValueFactory(new PropertyValueFactory<PomocnaKlasa, SimpleStringProperty>("zanr"));
		
		ArrayList<Integer> kontrola = new ArrayList<>();
		ObservableList <PomocnaKlasa> dodajUTabelu = FXCollections.observableArrayList();
		
		/*for(Osoblje_predstave op :Osoblje_predstave.getLista_osoblja_predstave()) {
			String tip = " ";
			String ime = " ";
			String prezime = " " ;
			String predstava= "";
			String zanr = "";
			
			if(!kontrola.contains(op.getOsobolje_ID())){
			for(Osoblje o : Osoblje.getLista_osoblja()) {
				boolean kontrolaOsobe = false;
				if(op.getOsobolje_ID() == o.getID()) {
					kontrolaOsobe = true;
					tip = o.getStringTip(o.getTip());
					ime = o.getIme();
					prezime = o.getPrezime();
					kontrola.add(op.getOsobolje_ID());
					predstava = Predstava.dajNazivPredstave(op.getPredstava_ID());
					zanr=Predstava.dajNazivZanra(op.getPredstava_ID());
					dodajUTabelu.add(new PomocnaKlasa(tip,ime,prezime,predstava,zanr));
					
					
					for(Osoblje_predstave op1 : Osoblje_predstave.getLista_osoblja_predstave()) {
						if(op1.getOsobolje_ID() == o.getID() && op1.getPredstava_ID() != op.getPredstava_ID()) {
							tip = ""; 
							ime = "";
							prezime =""; 
							predstava = Predstava.dajNazivPredstave(op1.getPredstava_ID());
							zanr=Predstava.dajNazivZanra(op1.getPredstava_ID());
							dodajUTabelu.add(new PomocnaKlasa(tip,ime,prezime,predstava,zanr));
						}
					}
					break;
				}
				if(!kontrolaOsobe && !kontrola.contains(o.getID())) {
					tip = o.getStringTip(o.getTip());
					ime = o.getIme();
					prezime = o.getPrezime();
					predstava="";
					zanr="";
					dodajUTabelu.add(new PomocnaKlasa(tip,ime,prezime,predstava,zanr));
				}
			}
			}
		
			
		}
		*/
for(Osoblje o : Osoblje.getLista_osoblja()) {
			
			String tip = " ";
			String ime = " ";
			String prezime = " " ;
			String predstava= "";
			String zanr = "";
		
			
			boolean osobaKontrola = false;
			
			for(Osoblje_predstave op : Osoblje_predstave.getLista_osoblja_predstave()) {
			
					if(op.getOsobolje_ID() == o.getID()) {
						osobaKontrola = true;
						if(!kontrola.contains(op.getOsobolje_ID())) {
							tip = o.getStringTip(o.getTip());
							ime = o.getIme();
							prezime = o.getPrezime();
				
							kontrola.add(op.getOsobolje_ID());
						
							predstava = Predstava.dajNazivPredstave(op.getPredstava_ID());
							zanr=Predstava.dajNazivZanra(op.getPredstava_ID());
							
							dodajUTabelu.add(new PomocnaKlasa(tip,ime,prezime,predstava,zanr));
							
						}
						else {
							 tip = " ";
							 ime = " ";
							 prezime = " " ;
		
							predstava = Predstava.dajNazivPredstave(op.getPredstava_ID());
							zanr=Predstava.dajNazivZanra(op.getPredstava_ID());
							
							dodajUTabelu.add(new PomocnaKlasa(tip,ime,prezime,predstava,zanr));
						}
					}
					
			}
			if(!osobaKontrola) {
				tip = o.getStringTip(o.getTip());
				ime = o.getIme();
				prezime = o.getPrezime();
	
				predstava ="";
				zanr ="";
				dodajUTabelu.add(new PomocnaKlasa(tip,ime,prezime,predstava,zanr));
			}
				
				
					
			
		}
		tabela.setItems(dodajUTabelu);
	}

}
