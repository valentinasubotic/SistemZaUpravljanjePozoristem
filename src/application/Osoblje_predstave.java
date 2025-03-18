package application;

import java.util.ArrayList;
//UMJESTO OSOBLJE PISE OSOBELJE!!!
//UMJESGTO GETOSOBLJE PISE OSOBELJE
//KASNO SAM VIDJELA DA SAM POGRESNO UNIJELA
//I UPOTREBLJAVALA SAM TE NAZIVE :)
public class Osoblje_predstave {
	private int ID;
	private int osobolje_ID;
	private int predstava_ID;
	
	private static ArrayList<Osoblje_predstave> lista_osoblja_predstave = new ArrayList<>();

	public Osoblje_predstave(int ID, int osobolje_ID, int predstava_ID) {
		this.ID = ID;
		this.osobolje_ID = osobolje_ID;
		this.predstava_ID = predstava_ID;
		boolean uslov= false;
		for(Osoblje_predstave op : lista_osoblja_predstave)
			if(op.getOsobolje_ID() == this.osobolje_ID && op.getPredstava_ID() == this.predstava_ID) {
				//System.err.print("U bazi ste pokusali dodati osobu " + this.ime+ " " + this.prezime + ".\nTo ime vec postoji.");
				uslov= true;
				break;
			}
		if(!uslov) {
			lista_osoblja_predstave.add(this);
		}
	
	}

	public int getID() {
		return ID;
	}

	public int getOsobolje_ID() {
		return osobolje_ID;
	}

	public int getPredstava_ID() {
		return predstava_ID;
	}

	public static ArrayList<Osoblje_predstave> getLista_osoblja_predstave() {
		return lista_osoblja_predstave;
	}
	
	public static int brojPredstavaUKojojGlumi(Osoblje o) {
		int broj = 0;
		for(Osoblje_predstave os : Osoblje_predstave.getLista_osoblja_predstave()) {
			if(os.getOsobolje_ID() == o.getID())
				broj++;
		}
		return broj;
	}
	


	
}
