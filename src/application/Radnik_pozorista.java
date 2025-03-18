package application;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Radnik_pozorista {
	private int ID;
	private String ime;
	private String prezime;
	private String korisnicko_ime;
	private String lozinka;
	private int pozoriste_ID;
	
	private static ArrayList<Radnik_pozorista> lista_radnika_pozorista = new ArrayList<>();

	public Radnik_pozorista(int ID, String ime, String prezime, String korisnicko_ime, String lozinka, int pozoriste_ID) {
		this.ID = ID;
		this.ime = ime;
		this.prezime = prezime;
		this.korisnicko_ime = korisnicko_ime;
		this.lozinka = lozinka;
		this.pozoriste_ID = pozoriste_ID;
		boolean uslov= false;
		for(Radnik_pozorista p : lista_radnika_pozorista)
			if(p.korisnicko_ime.equals(this.korisnicko_ime)) {
				//System.err.print("U bazi ste pokusali dodati radnika sa korisnickim imenom " + korisnicko_ime +".\nTo korisnicko ime je vec zauzeto");
				uslov= true;
				break;
			}
		if(!uslov) {
			lista_radnika_pozorista.add(this);
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

	public String getKorisnicko_ime() {
		return korisnicko_ime;
	}

	public String getLozinka() {
		return lozinka;
	}

	public int getPozoriste_ID() {
		return pozoriste_ID;
	}

	public static ArrayList<Radnik_pozorista> getLista_radnika_pozorista() {
		return lista_radnika_pozorista;
	}
	

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public static String getMd5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger no = new BigInteger(1, messageDigest);
			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	public static boolean daLiNekoRadiUPozirstu(int id) {
		for(Radnik_pozorista r : Radnik_pozorista.getLista_radnika_pozorista()) {
			if(r. getPozoriste_ID() == id)
				return true;
		}
		return false;
	}
	public static boolean daLiJeDobroUnesenaSifra(String korisnicko, String lozinka1) {
		for(Radnik_pozorista r : Radnik_pozorista.getLista_radnika_pozorista()) {
			if(r.getKorisnicko_ime().equals(korisnicko) && r.getLozinka().equals(lozinka1))
					return true;
		}
		return false;
		
	}
	public static int vratiIdTabele(String korisnicko) {
		int pomocniID = 0;
		for(Radnik_pozorista r :Radnik_pozorista.getLista_radnika_pozorista())
			if(r.getKorisnicko_ime().equals(korisnicko)) {
				pomocniID=r.getID();
				break;
			}
		return pomocniID;
	}
	
}
