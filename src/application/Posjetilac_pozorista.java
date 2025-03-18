package application;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Posjetilac_pozorista {
	private int ID;
	private String ime;
	private String prezime;
	private String korisnicko_ime;
	private String lozinka;
	
	private static ArrayList<Posjetilac_pozorista> posjetilac_pozorista_lista = new ArrayList<>();

	public Posjetilac_pozorista(int ID, String ime, String prezime, String korisnicko_ime, String lozinka) {
		this.ID = ID;
		this.ime = ime;
		this.prezime = prezime;
		this.korisnicko_ime = korisnicko_ime;
		this.lozinka = lozinka;
		
		boolean uslov= false;
		for(Posjetilac_pozorista p : posjetilac_pozorista_lista)
			if(p.korisnicko_ime.equals(this.korisnicko_ime)) {
				//System.err.print("U bazi ste pokusali dodati posjetioca sa korisnickim imenom " + korisnicko_ime +".\nTo korisnicko ime je vec zauzeto");
				uslov= true;
				break;
			}
		if(!uslov) {
			posjetilac_pozorista_lista.add(this);
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

	public static ArrayList<Posjetilac_pozorista> getPosjetilac_pozorista_lista() {
		return posjetilac_pozorista_lista;
	}
	public  String toString() {
		return "Posjetilac: " + this.ime + " "  + this.prezime +".\n";
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
	public static Posjetilac_pozorista dajPosjetiocaNaOsnovuStringa(String korisnicko) {
		for(Posjetilac_pozorista pp : Posjetilac_pozorista.posjetilac_pozorista_lista) {
			if(pp.getKorisnicko_ime().equals(korisnicko))
				return pp;
		}
		return null;
	}
	public static Posjetilac_pozorista dajPosjetiocaNaOsnovuIDa(int posjID) {
		for(Posjetilac_pozorista pp : Posjetilac_pozorista.getPosjetilac_pozorista_lista()) {
			if(pp.getID() == posjID) {
				return pp;
			}
		}
		return null;
	}
	
	
	
}
