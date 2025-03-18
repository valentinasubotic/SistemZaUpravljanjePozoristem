package application;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Izvodjenje_predstave {
	private int ID;
	private int predstava_ID;
	private int pozoriste_ID;
	private double cijena;
	private Timestamp datum_i_vrijeme;
	
	private static ArrayList<Izvodjenje_predstave> lista_izvodjenje_predstave = new ArrayList<>();

	public Izvodjenje_predstave(int ID, int predstava_ID, int pozoriste_ID, double cijena, Timestamp localDateTime) {
		this.ID = ID;
		this.predstava_ID = predstava_ID;
		this.pozoriste_ID = pozoriste_ID;
		this.cijena = cijena;
		this.datum_i_vrijeme = localDateTime;
	
		
		//lista_izvodjenje_predstave.add(this);
		
		boolean uslov = false;
		for(Izvodjenje_predstave p : lista_izvodjenje_predstave)
			if(p.getPredstava_ID() == this.predstava_ID && p.getPozoriste_ID() == this.pozoriste_ID && p.getCijena() == this.cijena && p.getDatum_i_vrijeme().equals(this.datum_i_vrijeme)) {
				//System.err.print("U bazi ste pokusali dodati radnika sa korisnickim imenom " + korisnicko_ime +".\nTo korisnicko ime je vec zauzeto");
				uslov= true;
				break;
			}
		if(!uslov) {
			lista_izvodjenje_predstave.add(this);
			
		}
		
		
		
	}

	public int getID() {
		return ID;
	}

	public int getPredstava_ID() {
		return predstava_ID;
	}

	public int getPozoriste_ID() {
		return pozoriste_ID;
	}

	public double getCijena() {
		return cijena;
	}

	public Timestamp getDatum_i_vrijeme() {
		return datum_i_vrijeme;
	}
	public String datumUString(Timestamp ts) {
		return ts.toString();
	}

	public static ArrayList<Izvodjenje_predstave> getLista_izvodjenje_predstave() {
		return lista_izvodjenje_predstave;
	}
	public static boolean daLiJeMoguceOtkazatiRez(Timestamp datumPredstave) {
		// ako se danasnji datum nalazi 2 dana prije predstave mogice je otkazati rez;
		Timestamp datumSad = new Timestamp(System.currentTimeMillis());
		Timestamp prije2dana = new Timestamp(datumPredstave.getTime() - 48*3600*1000);//2 dana prije predstave
		
		if(prije2dana.after(datumSad) || prije2dana.equals(datumSad)) 
			return true;
		
		return false;
		
		
	}
	public static boolean obavjestenjePosjetiocu(Timestamp datumPredstave) {
		Timestamp datumSad = new Timestamp(System.currentTimeMillis());
		Timestamp prije2dana = new Timestamp(datumPredstave.getTime() - 48*3600*1000);//2 dana prije predstae
		
		if(datumSad.after(prije2dana) && datumSad.before(datumPredstave)) {
			return true;
		}
		return false;
	}
	
	public static boolean daLiSeIzvodiUNarednomPeriodu(Timestamp datumPredstave) {
		Timestamp datumSad = new Timestamp(System.currentTimeMillis());
		if(datumPredstave.after(datumSad))
			return true;
		return false;
	}
	/*
	public static boolean daLiSeIzvodiUNarednomPeriodu(LocalDateTime datumPredstave) {
		LocalDateTime datumSad = LocalDateTime.now();
		if(datumPredstave.isAfter(datumSad))
			return true;
		return false;
	}
	*/
	public String toString() {
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		String string = df.format(this.datum_i_vrijeme);
		//vraca samo ispisan datum u stringu!
		return string;
	}
	/*
	public  String toString() {
		DateTimeFormatter f =DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String string = this.datum_i_vrijeme.format(f);
		return string;
	}
	*/
	public static Izvodjenje_predstave dajIzvodjenjeNaOsnovuID(int izvodjenjeID) {
		for(Izvodjenje_predstave ip : Izvodjenje_predstave.getLista_izvodjenje_predstave()) {
			if(ip.getID() == izvodjenjeID)
				return ip;
		}
		return null;
	}
	public static boolean daLiPostojiIzvodjenje(int predstavaID,int pozoristeID,double cijena1,Timestamp datumivrijeme) {
		boolean uslov = false;
		for(Izvodjenje_predstave ip : Izvodjenje_predstave.getLista_izvodjenje_predstave()) {
		 if(ip.getPredstava_ID() == predstavaID && ip.getPozoriste_ID() == pozoristeID && ip.getCijena()==cijena1 && ip.getDatum_i_vrijeme().equals(datumivrijeme)) {
			 uslov=true;
			 break;
		 }
		}
		return uslov;
	}
	
	public static boolean daLiPostojePredstaveKojeSeIzvodeUNarednomPeriodu(int pozID) {
		ArrayList<Predstava> pomocnaListaPredstava = new ArrayList<>();
		
		for(Izvodjenje_predstave ip : Izvodjenje_predstave.getLista_izvodjenje_predstave()) {
			if(ip.getPozoriste_ID() == pozID) {
				
				if(Izvodjenje_predstave.daLiSeIzvodiUNarednomPeriodu(ip.getDatum_i_vrijeme())) {
					if(!pomocnaListaPredstava.contains(Predstava.vratiPredstavu(ip.getPredstava_ID())))
					pomocnaListaPredstava.add(Predstava.vratiPredstavu(ip.getPredstava_ID()));
				}
				
			}
		}
		if(pomocnaListaPredstava.isEmpty())
			return false;
		return true;
	}
}
