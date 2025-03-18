package application;

import javafx.beans.property.SimpleStringProperty;

public class PomocnaKlasaARG {
private SimpleStringProperty tip,ime,prezime,predstava,zanr,brizv;
	
	public PomocnaKlasaARG(String tip,String ime, String prezime, String predstava, String zanr, String brizv) {
		this.tip = new SimpleStringProperty(tip);
		this.ime = new SimpleStringProperty(ime);
		this.prezime = new SimpleStringProperty(prezime);
		this.predstava = new SimpleStringProperty(predstava);
		this.zanr = new SimpleStringProperty(zanr);
		this.brizv = new SimpleStringProperty(brizv);
	}

	public String getTip() {
		return tip.get();
	}

	public String getIme() {
		return ime.get();
	}

	public String getPrezime() {
		return prezime.get();
	}

	public String getZanr() {
		return zanr.get();
	}

	public String getPredstava() {
		return predstava.get();
	}
	public String getBrizv() {
		return brizv.get();
	}

}
