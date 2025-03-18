package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
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
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

public class Controller_nakonprijaveRadnika implements Initializable{
    @FXML
    private TreeView<String> treeview;
    
    private String stringImeRadnika,stringNazivPredstave,stringZanr;
    private String stringPrezimeRadnika;
    private String pozoristeUKomRadi;
    private String korisnicko = Controller_radnik.vratiKorisnicko();
    private static int pozoristeID;
    
    
    public static int vratiPozoristeID() {
    	return pozoristeID;
    }
    
    public void povratak(ActionEvent event){
    	Alert alert0 = new Alert(Alert.AlertType.CONFIRMATION);
	 	alert0.setTitle("ODJAVA");
	 	alert0.setContentText("Da li ste sigurni da zelite da se odjavite?");
	 	
	 	Optional <ButtonType>result = alert0.showAndWait();
	 	if(result.get()==ButtonType.OK) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Scena_radnik.fxml"));
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setTitle("Dobrodošli radniče, prijavite se");
			stage.setScene(scene);
			stage.show();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	 	}
	}
    
    public void promjenaLozinke(ActionEvent event){
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Scena_RadnikMijenjaLozinku.fxml"));
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setTitle("Radniče " + Controller_radnik.vratiImePrezime() + ", promijenite lozinku!");
			stage.setScene(scene);
			stage.show();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
    public void dodajPredstavu(ActionEvent event){
  		try {
  			Parent root = FXMLLoader.load(getClass().getResource("Scena_RadnikDodajePredstavu.fxml"));
  			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
  			Scene scene = new Scene(root);
  			stage.setTitle("Radnice, "+ Controller_radnik.vratiImePrezime() + " dodajte novu predstavu.");
  			stage.setScene(scene);
  			stage.show();
  		}
  		catch(IOException e) {
  			e.printStackTrace();
  		}
  	}
    @FXML
    public void switchToRadnikKreiraRadnika(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("Scena_RadnikKreiraRadnika.fxml"));
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setTitle("Radnice, " + Controller_radnik.vratiImePrezime()  + " dodajte novog radnika! ");
			stage.setScene(scene);
			stage.show();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
    }
    @FXML
    public void switchToRadnikKreiraPozoriste(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("Scena_RadnikKreiraPozoriste.fxml"));
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setTitle("Radnice, " + Controller_radnik.vratiImePrezime()  + " krerajte novo pozoriste! ");
			stage.setScene(scene);
			stage.show();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
    }
    @FXML
    void dodajIzvodjenjePredstave(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("Scena_radnikDodajeIzvodjenje.fxml"));
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setTitle("Radnice, " + Controller_radnik.vratiImePrezime()  + " dodajte novo izvodjenje predstave!");
			stage.setScene(scene);
			stage.show();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void prikazPredstavaUNarednom(ActionEvent event) {
     	try {
			Parent root = FXMLLoader.load(getClass().getResource("Scena_radnikPregledPredstavaNaredni.fxml"));
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setTitle("Spisak predstava koje se izvode u narednom periodu u " + Pozoriste.dajPozoristeNaOsnovuId(Controller_nakonprijaveRadnika.vratiPozoristeID()).getNaziv());
			stage.setScene(scene);
			stage.show();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void prikazPredstavaUPrethodnom(ActionEvent event) {
     	try {
			Parent root = FXMLLoader.load(getClass().getResource("Scena_radnikPregledPredstavaPrethodni.fxml"));
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setTitle("Spisak predstava koje su se izvele u prethodnom periodu u " + Pozoriste.dajPozoristeNaOsnovuId(Controller_nakonprijaveRadnika.vratiPozoristeID()).getNaziv());
			stage.setScene(scene);
			stage.show();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
    }
    @FXML
    void radNaBlagajni(ActionEvent event) {
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
    @FXML
    void prikazARG(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("Scena_radnikPrikazARG.fxml"));
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setTitle("Radnice " + Controller_radnik.vratiImePrezime()  + ", u prilogu je spisak osoblja");
			stage.setScene(scene);
			stage.show();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void prikazSvihPredstava(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("Scena_radnikSpisakPredstava.fxml"));
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setTitle("Radnice " + Controller_radnik.vratiImePrezime()  + ", u prilogu je spisak predstava!");
			stage.setScene(scene);
			stage.show();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		 
		
		for(Radnik_pozorista r : Radnik_pozorista.getLista_radnika_pozorista()) {
			if(r.getKorisnicko_ime().equals(korisnicko)) {
				stringImeRadnika = r.getIme();
				stringPrezimeRadnika = r.getPrezime();
				pozoristeUKomRadi = Pozoriste.pozoristeNaId(r.getPozoriste_ID());
				pozoristeID = r.getPozoriste_ID();
			}
		}
		
		
		TreeItem<String> rootItem = new TreeItem<>("Vasi licni podaci: ");
	//	rootItem.setGraphic(treeview);
		
		TreeItem<String> ime = new TreeItem<>("Vase ime:");
		TreeItem<String> prezime = new TreeItem<>("Vase prezime:");
		TreeItem<String> pozoriste = new TreeItem<>("Pozoriste u kom radite:");
		
	
		TreeItem<String> imeRadnika = new TreeItem<>(stringImeRadnika);
		ime.getChildren().add(imeRadnika);
		
		TreeItem<String> prezimeRadnika = new TreeItem<>(stringPrezimeRadnika);
		prezime.getChildren().add(prezimeRadnika);
		
		TreeItem<String> imePozorista = new TreeItem<>(pozoristeUKomRadi);
		pozoriste.getChildren().add(imePozorista);
		
		rootItem.getChildren().addAll(ime,prezime,pozoriste);
		
		TreeItem<String> rootItem1 = new TreeItem<>("Spisak predstava: ");
		
		
		for(Predstava p : Predstava.getLista_predstava()) {
			stringNazivPredstave =p.getNaziv();
			stringZanr = p.getStringZanr(p.getZanr());
		
		TreeItem<String> naziv = new TreeItem<>("Naziv predstave:");
		TreeItem<String> zanr = new TreeItem<>("Zanr predstave:");
		TreeItem<String> glumci = new TreeItem<>("Glumci predstave:");
		TreeItem<String> reziser = new TreeItem<>("Reziser predstave:");
		TreeItem<String> autor = new TreeItem<>("Autor predstve:");
		
		TreeItem<String> nazivPredstave = new TreeItem<>(stringNazivPredstave);
		naziv.getChildren().add(nazivPredstave);
		naziv.getChildren().add(zanr);
		naziv.getChildren().add(glumci);
		naziv.getChildren().add(reziser);
		naziv.getChildren().add(autor);
		
		TreeItem<String> zanrPredstave = new TreeItem<>(stringZanr);
		
		zanr.getChildren().add(zanrPredstave);
		
		for(Osoblje_predstave o :Osoblje_predstave.getLista_osoblja_predstave()) {
			if(o.getPredstava_ID() == p.getID()) {
				for(Osoblje os : Osoblje.getLista_osoblja()) {
					if(os.getID() == o.getOsobolje_ID()) {
						if(os.getStringTip(os.getTip()).equals("glumac")) {
							String string = "Ime: " + os.getIme() + "\nPrezime: " + os.getPrezime();
							TreeItem<String> glumacPredstave = new TreeItem<>(string);
							glumci.getChildren().add(glumacPredstave);
						}
						else if(os.getStringTip(os.getTip()).equals("reziser")) {
							String string = "Ime: " + os.getIme() + "\nPrezime: " + os.getPrezime();
							TreeItem<String> reziserPredstave = new TreeItem<>(string);
							reziser.getChildren().add(reziserPredstave);
						}
						else if(os.getStringTip(os.getTip()).equals("autor")){
							String string = "Ime: " + os.getIme() + "\nPrezime: " + os.getPrezime();
							TreeItem<String> autorPredstave = new TreeItem<>(string);
							autor.getChildren().add(autorPredstave);
						}
						else {
						}
					}
				}
			}
		}
		
	rootItem1.getChildren().add(naziv);
		}
		
		TreeItem<String> rootItem2 = new TreeItem<>("Spisak autora, rezisera i glumaca: ");
		
		ArrayList<Osoblje> autoriLista = new ArrayList<>();
		ArrayList<Osoblje> glumciLista = new ArrayList<>();
		ArrayList<Osoblje> reziseriLista = new ArrayList<>();
		for(Osoblje o : Osoblje.getLista_osoblja()) {
			if(o.getStringTip(o.getTip()).equals("autor"))
				autoriLista.add(o);
			else if(o.getStringTip(o.getTip()).equals("glumac"))
				glumciLista.add(o);
			else
				reziseriLista.add(o);
		}
		TreeItem<String> AUTORI = new TreeItem<>("Autori:");
		TreeItem<String> REZISERI = new TreeItem<>("Reziseri:");
		TreeItem<String> GLUMCI = new TreeItem<>("Glumci:");
		
		KomparatorOsoblje komparator = new KomparatorOsoblje();
		Collections.sort(autoriLista,komparator);
		
		for(Osoblje a : autoriLista) {
			String string = "Ime: " + a.getIme() + "\nPrezime: " + a.getPrezime() + "\nBroj izvodjenja predstava: " + Osoblje_predstave.brojPredstavaUKojojGlumi(a);
			TreeItem<String> autor1 = new TreeItem<>(string);
			AUTORI.getChildren().add(autor1);
		}
		
	   Collections.sort(reziseriLista,komparator);
		
		for(Osoblje r : reziseriLista) {
			String string = "Ime: " + r.getIme() + "\nPrezime: " + r.getPrezime() + "\nBroj izvodjenja predstava: " + Osoblje_predstave.brojPredstavaUKojojGlumi(r);
			TreeItem<String> reziser1 = new TreeItem<>(string);
			REZISERI.getChildren().add(reziser1);
		}
		
		  Collections.sort(glumciLista,komparator);
			
			for(Osoblje g : glumciLista) {
				String string = "Ime: " + g.getIme() + "\nPrezime: " + g.getPrezime() + "\nBroj izvodjenja predstava: " + Osoblje_predstave.brojPredstavaUKojojGlumi(g);
				TreeItem<String> glumac1 = new TreeItem<>(string);
				GLUMCI.getChildren().add(glumac1);
			}
		
		
		
		
		
		
		
		rootItem2.getChildren().addAll(AUTORI,REZISERI,GLUMCI);
		
		
		
		
		
		
		
		
		TreeItem<String> dummyRoot = new TreeItem<>();
		dummyRoot.getChildren().addAll(rootItem);
		
		
		treeview.setRoot(dummyRoot);
		treeview.setShowRoot(false);
		
		
	}
    
    
}
