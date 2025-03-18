package application;

import java.io.IOException;
import java.net.URL;
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

public class Controller_klik implements Initializable{
	    @FXML
	    private TableColumn<PomocnaKlasaKlik, SimpleStringProperty> brojslMKolona;

	    @FXML
	    private TableColumn<PomocnaKlasaKlik, SimpleStringProperty> cijenaKolona;

	    @FXML
	    private TableColumn<PomocnaKlasaKlik, SimpleStringProperty> datumKolona;

	    @FXML
	    private TableColumn<PomocnaKlasaKlik, SimpleStringProperty> gradKolona;

	    @FXML
	    private TableColumn<PomocnaKlasaKlik, SimpleStringProperty> pozoristeKolona;

	    @FXML
	    private TableColumn<PomocnaKlasaKlik, SimpleStringProperty> predstavaKolona;

	    @FXML
	    private TableColumn<PomocnaKlasaKlik, SimpleStringProperty> zanrKolona;
	    @FXML
	    private TableView<PomocnaKlasaKlik> tabela;
	    
	    
	   
	    
	    
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			// TODO Auto-generated method stub
			pozoristeKolona.setCellValueFactory(new PropertyValueFactory<PomocnaKlasaKlik, SimpleStringProperty>("pozoriste"));
			gradKolona.setCellValueFactory(new PropertyValueFactory<PomocnaKlasaKlik, SimpleStringProperty>("grad"));
			predstavaKolona.setCellValueFactory(new PropertyValueFactory<PomocnaKlasaKlik, SimpleStringProperty>("predstava"));
			zanrKolona.setCellValueFactory(new PropertyValueFactory<PomocnaKlasaKlik, SimpleStringProperty>("zanr"));
			datumKolona.setCellValueFactory(new PropertyValueFactory<PomocnaKlasaKlik, SimpleStringProperty>("datum"));
			cijenaKolona.setCellValueFactory(new PropertyValueFactory<PomocnaKlasaKlik, SimpleStringProperty>("cijena"));
			brojslMKolona.setCellValueFactory(new PropertyValueFactory<PomocnaKlasaKlik, SimpleStringProperty>("brojslm"));
			
			ObservableList <PomocnaKlasaKlik> dodajUTabelu = FXCollections.observableArrayList();
			for(Izvodjenje_predstave ip : Izvodjenje_predstave.getLista_izvodjenje_predstave()) {
				String pozoriste = " ";
				String grad = " ";
				String predstava= "";
				String zanr = "";
				String datum = " " ;
				String cijena = " " ;
				String brojslm = " " ;
				
				Pozoriste poz = Pozoriste.dajPozoristeNaOsnovuId(ip.getPozoriste_ID());
				Predstava pred = Predstava.vratiPredstavu(ip.getPredstava_ID());
				if(poz!= null && pred!=null) {
					pozoriste = poz.getNaziv();
					grad = poz.getGrad();
					predstava = pred.getNaziv();
					zanr = pred.getStringZanr(pred.getZanr());
					datum = ip.toString();
					cijena = Double.toString(ip.getCijena());
					brojslm = String.valueOf(Pozoriste.dajBrojSlobodnihSjedista(poz, ip));
					
					dodajUTabelu.add(new PomocnaKlasaKlik(pozoriste,grad,predstava,zanr,datum,cijena,brojslm));
					
				}
				
			}
			tabela.setItems(dodajUTabelu);
			
			
		}
}
