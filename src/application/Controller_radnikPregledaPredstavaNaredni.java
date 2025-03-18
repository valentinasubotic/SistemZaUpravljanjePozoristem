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

public class Controller_radnikPregledaPredstavaNaredni implements Initializable{
	  @FXML
	    private TableColumn<PomocnaKlasaSpisakPredstava, SimpleStringProperty> brojslMKolona;

	    @FXML
	    private TableColumn<PomocnaKlasaSpisakPredstava, SimpleStringProperty> cijenaKolona;

	    @FXML
	    private TableColumn<PomocnaKlasaSpisakPredstava, SimpleStringProperty> predstavaKolona;

	    @FXML
	    private TableView<PomocnaKlasaSpisakPredstava> tabela;

	    @FXML
	    private TableColumn<PomocnaKlasaSpisakPredstava, SimpleStringProperty> datumKolona;

	    @FXML
	    private TableColumn<PomocnaKlasaSpisakPredstava, SimpleStringProperty> zanrKolona;
	
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		predstavaKolona.setCellValueFactory(new PropertyValueFactory<PomocnaKlasaSpisakPredstava, SimpleStringProperty>("predstava"));
		zanrKolona.setCellValueFactory(new PropertyValueFactory<PomocnaKlasaSpisakPredstava, SimpleStringProperty>("zanr"));
		datumKolona.setCellValueFactory(new PropertyValueFactory<PomocnaKlasaSpisakPredstava, SimpleStringProperty>("datum"));
		cijenaKolona.setCellValueFactory(new PropertyValueFactory<PomocnaKlasaSpisakPredstava, SimpleStringProperty>("cijena"));
		brojslMKolona.setCellValueFactory(new PropertyValueFactory<PomocnaKlasaSpisakPredstava, SimpleStringProperty>("brojslm"));
		
		ObservableList <PomocnaKlasaSpisakPredstava> dodajUTabelu = FXCollections.observableArrayList();
		ArrayList<Integer> kontrola = new ArrayList<>();
		
		for(Izvodjenje_predstave ip : Izvodjenje_predstave.getLista_izvodjenje_predstave()) {
			String predstava = "";
			String zanr ="";
			String datum = "";
			String cijena = "";
			String brojslm = "";
	
			if(ip.getPozoriste_ID() == Controller_nakonprijaveRadnika.vratiPozoristeID()) 
					if(Izvodjenje_predstave.daLiSeIzvodiUNarednomPeriodu(ip.getDatum_i_vrijeme())) {
						if(!kontrola.contains(ip.getPredstava_ID())) {
							Predstava p = Predstava.vratiPredstavu(ip.getPredstava_ID());
							kontrola.add(ip.getPredstava_ID());
							predstava = p.getNaziv();
							zanr = p.getStringZanr(p.getZanr());
							datum = ip.toString();
							cijena = Double.toString(ip.getCijena());
							
							int brojsjedista = Pozoriste.dajPozoristeNaOsnovuId(Controller_nakonprijaveRadnika.vratiPozoristeID()).getBroj_sjedista();
							
							for(Karta k: Karta.getKarta_lista()) {
								if(k.getIzvodjenje_predstave_ID() == ip.getID())
									brojsjedista = brojsjedista - 1;
							}
							brojslm = Integer.toString(brojsjedista);
							dodajUTabelu.add(new PomocnaKlasaSpisakPredstava(predstava,zanr,datum,cijena,brojslm));
					}
						else {
							datum = ip.toString();
							int brojsjedista = Pozoriste.dajPozoristeNaOsnovuId(Controller_nakonprijaveRadnika.vratiPozoristeID()).getBroj_sjedista();
							
							for(Karta k: Karta.getKarta_lista()) {
								if(k.getIzvodjenje_predstave_ID() == ip.getID())
									brojsjedista = brojsjedista - 1;
							}
							brojslm = Integer.toString(brojsjedista);
							dodajUTabelu.add(new PomocnaKlasaSpisakPredstava(predstava,zanr,datum,cijena,brojslm));
						}
				}
				
			
		}
		tabela.setItems(dodajUTabelu);
	}
















}











