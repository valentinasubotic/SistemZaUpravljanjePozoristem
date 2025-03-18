package application;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
/* 
public class PomocnaTestirana {
	
	public static void main(String[] args) {
		/*
		try {
			//java.sql.Date dat1 ="2023/10/10 12:00:00";
			String string ="2023/10/10 12:00:00";
			SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date parsed;
			parsed = df.parse(string);
			Timestamp ts= new Timestamp(parsed.getTime());
			Timestamp datumSad = new Timestamp(System.currentTimeMillis());
			System.out.println(ts.toString());
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		ArrayList <Osoblje> osoblje= Osoblje.getLista_osoblja();
		for(Osoblje o : osoblje) {
			System.out.println(o.getIme() + " " + o.getIme() + " " + o.getStringTip(o.getTip()) + " " + Osoblje_predstave.brojPredstavaUKojojGlumi(o));
		}
		KomparatorOsoblje komparator = new KomparatorOsoblje();
		Collections.sort(osoblje,komparator);
		System.out.println("Nakon sortiranja:");
		for(Osoblje o : osoblje) {
			System.out.println(o.getIme() + " " + o.getIme() + " " + o.getStringTip(o.getTip()) + " " + Osoblje_predstave.brojPredstavaUKojojGlumi(o));
		}
		
			
	}
	 

}
*/

	 /*
	 izvodjenje predstave radi na ovaj nacin:::::
	 
Date date = Rs.getDate("datum_i_vrijeme");
Timestamp ts = new Timestamp(date.getTime());
new Izvodjenje_predstave(Rs.getInt("id"), Rs.getInt("predstava_id"), Rs.getInt("pozoriste_id"),
		Rs.getDouble("cijena"), ts);
*/