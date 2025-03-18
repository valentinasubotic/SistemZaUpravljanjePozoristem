package application;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class Controller_posjetilacPregledaKarte implements Initializable{
    @FXML
    private ListView<Karta> listaAktivne;

    @FXML
    private ListView<Karta> listaIstekle;

    @FXML
    private Label labelAktivne;

    @FXML
    private Label labelIstekle;

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
		listaAktivne.setVisible(false);
		listaIstekle.setVisible(false);
		labelAktivne.setVisible(false);
		labelIstekle.setVisible(false);
		
		ArrayList <Karta> aktivne = new ArrayList<>();
		ArrayList <Karta> istekle = new ArrayList<>();
		
		String korisnicko;
		if(Controller_posjetilac.prijava) {
		 	 korisnicko = Controller_posjetilac.korisnickoPosjetioicaPrijava;
		 	}
		 	else {
		 		 korisnicko=Controlle_posjetilac_registracija.korisnickoPosjetioicaRegistracija;
		 	}
		
		Posjetilac_pozorista pp = Posjetilac_pozorista.dajPosjetiocaNaOsnovuStringa(korisnicko);
		for(Karta k : Karta.getKarta_lista()) {
			if(k.getPosjetilac_ID() == pp.getID() && k.getStatus()!=1) {
				Izvodjenje_predstave ip = Izvodjenje_predstave.dajIzvodjenjeNaOsnovuID(k.getIzvodjenje_predstave_ID());
				if(Izvodjenje_predstave.daLiSeIzvodiUNarednomPeriodu(ip.getDatum_i_vrijeme())) {
					aktivne.add(k);
				}
				else {
					istekle.add(k);
				}
				
			}
		}
		listaAktivne.getItems().addAll(aktivne);
		listaIstekle.getItems().addAll(istekle);
		
		if(!listaAktivne.getItems().isEmpty())
			listaAktivne.setVisible(true);
		else {
			labelAktivne.setText("U ovom trenutku\n nema aktivnih\nrezervacija!");
			labelAktivne.setVisible(true);
		}
		if(!listaIstekle.getItems().isEmpty())
			listaIstekle.setVisible(true);
		else {
			labelIstekle.setText("U ovom trenutku\n nema rezervacija\n koje su istekle!");
			labelIstekle.setVisible(true);
		}
			
	}
}
