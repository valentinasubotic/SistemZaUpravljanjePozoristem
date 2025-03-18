package application;

import java.util.ArrayList;

public class Karta {
	private int ID;
	private int izvodjenje_predstave_ID;
	private int status;
	private int posjetilac_ID;
	private int broj_karta;
	
	private static ArrayList<Karta> karta_lista = new ArrayList<>();
	
	

	public Karta(int ID, int izvodjenje_predstave_ID, int status, int posjetilac_ID, int broj_karta) {
		this.ID = ID;
		this.izvodjenje_predstave_ID = izvodjenje_predstave_ID;
		this.status = status;
		this.posjetilac_ID = posjetilac_ID;
		this.broj_karta = broj_karta;
		
		boolean uslov = false;
		for(Karta p : karta_lista)
			if(p.getIzvodjenje_predstave_ID() == this.izvodjenje_predstave_ID && p.getStatus() == this.status && p.getPosjetilac_ID() == this.posjetilac_ID && p.getBroj_karta() == this.broj_karta) {
				//System.err.print("U bazi ste pokusali dodati radnika sa korisnickim imenom " + korisnicko_ime +".\nTo korisnicko ime je vec zauzeto");
				uslov= true;
				break;
			}
		if(!uslov) {
			karta_lista.add(this);
		
		}
		
	}

	public int getID() {
		return ID;
	}

	public int getIzvodjenje_predstave_ID() {
		return izvodjenje_predstave_ID;
	}

	public int getStatus() {
		return status;
	}

	public int getPosjetilac_ID() {
		return posjetilac_ID;
	}

	public int getBroj_karta() {
		return broj_karta;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public static ArrayList<Karta> getKarta_lista() {
		return karta_lista;
	}
	public static int sljedeciSlobodanBroj(int izvodjenjepredstaveID, int brojSjedista) {
		ArrayList<Integer> postojeciBrojevi = new ArrayList<>();
		boolean provjera=false;
		for(Karta k: Karta.getKarta_lista()) {
			if(k.getIzvodjenje_predstave_ID() == izvodjenjepredstaveID) {
				postojeciBrojevi.add(k.getBroj_karta());
				provjera = true;
			}
		}
		if(!provjera) {
			return 1;
		}
		for(int i=1;i<=brojSjedista;i++) {
			if(!postojeciBrojevi.contains(i))
				return i;
		}
		return 1;
	}
	
	public String toString() {
		String string="Broj karte: " + this.getBroj_karta()+"\n";
		if(this.status == 1) 
			string += "Satus: kupljena, ne koristeci ovu aplikaciju\n";
		else if(this.status == 2)
			string += "Status: rezervisana koristeci aplikaciju, ali nije preuzeta\n";
		else
			string += "Status: rezervisana koristeci aplikaciju, preuzeta\n";
		string += Posjetilac_pozorista.dajPosjetiocaNaOsnovuIDa(this.getPosjetilac_ID());
		
		for(Izvodjenje_predstave ip : Izvodjenje_predstave.getLista_izvodjenje_predstave()) {
			if(this.getIzvodjenje_predstave_ID() == ip.getID()) {
				string += Predstava.vratiPredstavu(ip.getPredstava_ID());
				string+= "Ime pozorista: " + Pozoriste.dajPozoristeNaOsnovuId(ip.getPozoriste_ID()).getNaziv()+"\nGrad: " + Pozoriste.dajPozoristeNaOsnovuId(ip.getPozoriste_ID()).getGrad()+"\n";
				string+="Cijena: " + ip.getCijena() + "\n";
				string+="Datum izvodjenja: " + ip;	
			}
		
	}
		return string;
		
	}

	
	
	
}
