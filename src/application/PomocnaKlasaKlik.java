package application;

import javafx.beans.property.SimpleStringProperty;

public class PomocnaKlasaKlik {
	private SimpleStringProperty pozoriste,grad,predstava,zanr,datum,cijena,brojslm;
	
	public PomocnaKlasaKlik(String pozoriste,String grad, String predstava, String zanr, String datum,String cijena, String brojslm) {
		this.pozoriste = new SimpleStringProperty(pozoriste);
		this.grad = new SimpleStringProperty(grad);
		this.predstava = new SimpleStringProperty(predstava);
		this.zanr = new SimpleStringProperty(zanr);
		this.datum = new SimpleStringProperty(datum);
		this.cijena = new SimpleStringProperty(cijena);
		this.brojslm = new SimpleStringProperty(brojslm);

	}

	public String getPozoriste() {
		return pozoriste.get();
	}

	public String getGrad() {
		return grad.get();
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
