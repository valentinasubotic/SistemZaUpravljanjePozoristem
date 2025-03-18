package application;

import javafx.beans.property.SimpleStringProperty;

public class PomocnaKlasaSpisakPredstava {
//sluzi za Scena_radnikPregledPredstavaNaredni.fxml
	//takodje sluzi za Scena_radnikPregledPredstavaPrethodni.fxml
	
	private SimpleStringProperty predstava,zanr,datum,cijena,brojslm;
	
	public PomocnaKlasaSpisakPredstava(String predstava, String zanr, String datum,String cijena, String brojslm) {
		this.predstava = new SimpleStringProperty(predstava);
		this.zanr = new SimpleStringProperty(zanr);
		this.datum = new SimpleStringProperty(datum);
		this.cijena = new SimpleStringProperty(cijena);
		this.brojslm = new SimpleStringProperty(brojslm);

	}
	
	public String getPredstava() {
		return predstava.get();
	}

	public String getZanr() {
		return zanr.get();
	}

	public String getDatum() {
		return datum.get();
	}

	public String getCijena() {
		return cijena.get();
	}

	public String getBrojslm() {
		return brojslm.get();
	}
	
}
