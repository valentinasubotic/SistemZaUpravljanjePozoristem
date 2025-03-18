package application;

import java.util.ArrayList;

public class Osoblje {
	private int ID;
	private String ime;
	private String prezime;
	private int tip;
	
	private static ArrayList<Osoblje> lista_osoblja = new ArrayList<>();

	public Osoblje(int ID, String ime, String prezime, int tip) {
		this.ID = ID;
		this.ime = ime;
		this.prezime = prezime;
		this.tip = tip;
		boolean uslov= false;
		for(Osoblje o : lista_osoblja)
			if(o.getIme().equals(this.ime) && o.getPrezime().equals(this.prezime)) {
				//System.err.print("U bazi ste pokusali dodati osobu " + this.ime+ " " + this.prezime + ".\nTo ime vec postoji.");
				uslov= true;
				break;
			}
		if(!uslov) {
			lista_osoblja.add(this);
		}
		
	}
	

	public int getID() {
		return ID;
	}

	public String getIme() {
		return ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public int getTip() {
		return tip;
	}
	public String getStringTip(int tip) {
		return Tipovi.tipOsobe(Tipovi.izBrojaUTip(tip));
	}

	public static ArrayList<Osoblje> getLista_osoblja() {
		return lista_osoblja;
	}
	
	


	public String toString() {
		return this.prezime + ", " + this.ime;
		
	}
	public static boolean daLiPostojiOsoblje(int tip1, String ime1, String prezime1) {
		for(Osoblje o : Osoblje.getLista_osoblja()) 
			if(o.getTip() == tip1 && ime1.equals(o.getIme()) && prezime1.equals(o.getPrezime()))
				return true;
			
		return false;
	}
	public static Osoblje vratiNaOsnovuImenaPrezimena(int tip1, String ime1, String prezime1) {
		for(Osoblje o : Osoblje.getLista_osoblja()) {
			if(o.getTip()==tip1 && o.getIme().equals(ime1) && o.getPrezime().equals(prezime1)) {
				return o;
			}
		}
		return null;
		
	}
	
	public static int vratiDuzinuListe(int tip1) {
		int n = 0;
		for(Osoblje o : Osoblje.getLista_osoblja())
			if(o.getTip() == tip1)
				n++;
		return n;
	}
	
	public static Osoblje vratiOsobuNaOsnovuID(int id) {
		for(Osoblje o : Osoblje.getLista_osoblja()) {
			if(o.getID() == id)
				return o;
		}
		return null;
	}
	
}
