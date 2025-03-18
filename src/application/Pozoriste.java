package application;

import java.util.ArrayList;

public class Pozoriste {
	private int ID;
	private String naziv;
	private String grad;
	private int broj_sjedista;
	
	private static ArrayList<Pozoriste> lista_pozorista = new ArrayList<>();

	public Pozoriste(int ID, String naziv, String grad, int broj_sjedista){
		this.ID = ID;
		this.naziv = naziv;
		this.grad = grad;
		this.broj_sjedista = broj_sjedista;
		lista_pozorista.add(this);
	}

	public int getID() {
		return ID;
	}

	public String getNaziv() {
		return naziv;
	}

	public String getGrad() {
		return grad;
	}

	public int getBroj_sjedista() {
		return broj_sjedista;
	}

	public static ArrayList<Pozoriste> getLista_pozorista() {
		return lista_pozorista;
	}
	public static Pozoriste vratiPozoriste(int j) {
		return Pozoriste.getLista_pozorista().get(j);
			
	}
	public static String pozoristeNaId(int id) {
		for(Pozoriste p : Pozoriste.getLista_pozorista()) {
			if(p.getID() == id)
				return p.toString();	
		}
		return "";
	}
	/*
	public String toString() {
		String string = "Pozoriste " + this.naziv + " u gradu: " + this.grad + ".\n Broj sjedista: " + this.broj_sjedista;
		return string;
	}
	*/
	public static boolean daLiPostojiPozoriste(String naz, String gr, int br) {
		for(Pozoriste p : Pozoriste.getLista_pozorista()) {
			if(p.getNaziv().equals(naz) && p.getGrad().equals(gr) && p.getBroj_sjedista() == br)
				return true;
		}
		return false;
	}
	public String toString() {
		String string = "Ime pozorista: " + this.naziv + "\nGrad: " + this.grad + "\nBroj sjedista: " + 
				this.broj_sjedista + ".\n";
		return string;
	}
	public static Pozoriste dajPozoristeNaOsnovuId(int pID) {
		for(Pozoriste p : Pozoriste.getLista_pozorista()) {
			if(p.getID() == pID)
				return p;
		}
		return null;
	}
	public static int dajBrojSlobodnihSjedista(Pozoriste p, Izvodjenje_predstave ip) {
		int brojSjedistaUPoz = p.getBroj_sjedista();
		
		for(Karta k : Karta.getKarta_lista()) {
			if(k.getIzvodjenje_predstave_ID() == ip.getID()) {
				brojSjedistaUPoz = brojSjedistaUPoz -1 ;
			}
		}
		return brojSjedistaUPoz;
	}
}
