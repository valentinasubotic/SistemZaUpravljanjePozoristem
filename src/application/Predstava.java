package application;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;

public class Predstava {
	private int ID;
	private String naziv;
	private int zanr;
	
	private static ArrayList<Predstava> lista_predstava = new ArrayList<>();

	public Predstava(int ID, String naziv, int zanr) {
		this.ID = ID;
		this.naziv = naziv;
		this.zanr = zanr;
		boolean uslov= false;
		for(Predstava p : lista_predstava)
			if(p.getNaziv().equals(this.naziv) && p.getZanr() == this.zanr) {
				//System.err.print("U bazi ste pokusali dodati predstavu " + this.naziv + " ,zanra " + this.zanr + ".\nTa predstava  vec postoji.\n");
				uslov= true;
				break;
			}
		if(!uslov) {
			lista_predstava.add(this);
		}
	}

	public int getID() {
		return ID;
	}

	public String getNaziv() {
		return naziv;
	}

	public int getZanr() {
		return zanr;
	}

	public static ArrayList<Predstava> getLista_predstava() {
		return lista_predstava;
	}
	public String getStringZanr(int zanr) {
		return Tipovi.tipZanra(Tipovi.izBrojaUTip(zanr));
	}
	
	public static int izStringaUInt(String zanr1) {
		switch(zanr1) {
		case "komedija" : return 1;
		case "farsa" : return 2;
		case "satira" : return 3;
		case "komedija restauracije" : return 4;
		case "tragedija" : return 5;
		case "istorija" : return 6;
		case "mjuzikl" : return 7;
		default : throw new IllegalArgumentException("Greska");
		}
	}
	
	public static boolean daLiPostojiPredstava(String naz,int zanr1) {
		for(Predstava p : Predstava.getLista_predstava()) 
			if(p.getNaziv().equals(naz) && p.getZanr() == zanr1) 
				return true;
			
		return false;
	}
	public static int vratiPredstavinID(String nazivPredstave, int zanrPredstave) {
		for(Predstava p : Predstava.getLista_predstava()) 
			if(p.getNaziv().equals(nazivPredstave) && p.getZanr() == zanrPredstave)
				return p.getID();
		return 0;
		
	}
	public static String dajNazivPredstave(int predstava_id) {
		for(Predstava p : Predstava.getLista_predstava()) {
			if(p.getID() == predstava_id) {
				return p.getNaziv();
			}
		}
		return null;
	}
	public static String dajNazivZanra(int predstava_id) {
		for(Predstava p : Predstava.getLista_predstava()) {
			if(p.getID() == predstava_id) {
				return Tipovi.tipZanra(Tipovi.izBrojaUTip(p.getZanr()));
			}
		}
		return null;
	}
	
	public static Predstava vratiPredstavu(int predstavaID) {
		for(Predstava p : Predstava.getLista_predstava()) {
			if(p.getID() == predstavaID) 
				return p;
		}
		return null;
	}
	public String toString() {
		return "Naziv predstave: " + this.naziv + " \nZanr predstave: " + Tipovi.tipZanra(Tipovi.izBrojaUTip(this.zanr))+"\n";
	}


}
