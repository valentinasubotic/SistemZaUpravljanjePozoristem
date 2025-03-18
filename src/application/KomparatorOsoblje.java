package application;

import java.util.Comparator;

public class KomparatorOsoblje implements Comparator <Osoblje>{

	@Override
	public int compare(Osoblje o1, Osoblje o2) {
		if(o1.getStringTip(o1.getTip()).equals(o2.getStringTip(o2.getTip()))) {
			if(Osoblje_predstave.brojPredstavaUKojojGlumi(o1)>=Osoblje_predstave.brojPredstavaUKojojGlumi(o2))
				return -1;
			else
				return 1;
		}
		else if(o1.getStringTip(o1.getTip()).equals("autor") && !o2.getStringTip(o2.getTip()).equals("autor")) {
			return -1;
		}
		else if(o1.getStringTip(o1.getTip()).equals("reziser") && o2.getStringTip(o2.getTip()).equals("glumac")) {
			return -1;
		}
		else if(o1.getStringTip(o1.getTip()).equals("glumac") && !o2.getStringTip(o2.getTip()).equals("glumac")) {
			return 1;
		}
		else return 0;
		
		
	}
/*
	@Override
	public int compare(Osoblje o1, Osoblje o2) {
		if(Osoblje_predstave.brojPredstavaUKojojGlumi(o1)>=Osoblje_predstave.brojPredstavaUKojojGlumi(o2))
			return -1;
		else
			return 1;
	}
	*/
	

}
