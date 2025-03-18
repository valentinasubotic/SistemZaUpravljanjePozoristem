package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
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

public class Controller_radnikPrikazARG implements Initializable{
	@FXML
    private TableColumn<PomocnaKlasaARG, SimpleStringProperty> imeKolona;

    @FXML
    private TableColumn<PomocnaKlasaARG, SimpleStringProperty> predstavaKolona;

    @FXML
    private TableColumn<PomocnaKlasaARG, SimpleStringProperty> prezimeKolona;

    @FXML
    private TableView<PomocnaKlasaARG> tabela;

    @FXML
    private TableColumn<PomocnaKlasaARG, SimpleStringProperty> tipKolona;

    @FXML
    private TableColumn<PomocnaKlasaARG, SimpleStringProperty> zanrKolona;

    @FXML
    private TableColumn<PomocnaKlasaARG, SimpleStringProperty> brizv;
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
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ArrayList <Osoblje> osoblje= Osoblje.getLista_osoblja();
		
		KomparatorOsoblje komparator = new KomparatorOsoblje();
		Collections.sort(osoblje,komparator);
		
		
		
		tipKolona.setCellValueFactory(new PropertyValueFactory<PomocnaKlasaARG, SimpleStringProperty>("tip"));
		imeKolona.setCellValueFactory(new PropertyValueFactory<PomocnaKlasaARG, SimpleStringProperty>("ime"));
		prezimeKolona.setCellValueFactory(new PropertyValueFactory<PomocnaKlasaARG, SimpleStringProperty>("prezime"));
		predstavaKolona.setCellValueFactory(new PropertyValueFactory<PomocnaKlasaARG, SimpleStringProperty>("predstava"));
		zanrKolona.setCellValueFactory(new PropertyValueFactory<PomocnaKlasaARG, SimpleStringProperty>("zanr"));
		brizv.setCellValueFactory(new PropertyValueFactory<PomocnaKlasaARG, SimpleStringProperty>("brizv"));
		
		ArrayList<Integer> kontrola = new ArrayList<>();
		ObservableList <PomocnaKlasaARG> dodajUTabelu = FXCollections.observableArrayList();

		

	
		for(Osoblje o : osoblje) {
			
			String tip = " ";
			String ime = " ";
			String prezime = " " ;
			String predstava= "";
			String zanr = "";
			String brizv ="";
			
			boolean osobaKontrola = false;
			
			for(Osoblje_predstave op : Osoblje_predstave.getLista_osoblja_predstave()) {
			
					if(op.getOsobolje_ID() == o.getID()) {
						osobaKontrola = true;
						if(!kontrola.contains(op.getOsobolje_ID())) {
							tip = o.getStringTip(o.getTip());
							ime = o.getIme();
							prezime = o.getPrezime();
							brizv = Integer.toString(Osoblje_predstave.brojPredstavaUKojojGlumi(o));
							kontrola.add(op.getOsobolje_ID());
							//kontrolaPr.add(op.getPredstava_ID());
							predstava = Predstava.dajNazivPredstave(op.getPredstava_ID());
							zanr=Predstava.dajNazivZanra(op.getPredstava_ID());
							
							dodajUTabelu.add(new PomocnaKlasaARG(tip,ime,prezime,predstava,zanr,brizv));
							
						}
						else {
							 tip = " ";
							 ime = " ";
							 prezime = " " ;
							 brizv ="";
							predstava = Predstava.dajNazivPredstave(op.getPredstava_ID());
							zanr=Predstava.dajNazivZanra(op.getPredstava_ID());
							
							dodajUTabelu.add(new PomocnaKlasaARG(tip,ime,prezime,predstava,zanr,brizv));
						}
					}
					
			}
			if(!osobaKontrola) {
				tip = o.getStringTip(o.getTip());
				ime = o.getIme();
				prezime = o.getPrezime();
				brizv = Integer.toString(Osoblje_predstave.brojPredstavaUKojojGlumi(o));
				predstava ="";
				zanr ="";
				dodajUTabelu.add(new PomocnaKlasaARG(tip,ime,prezime,predstava,zanr,brizv));
			}
				
				
					
			
		}
	
		tabela.setItems(dodajUTabelu);
	}
}

