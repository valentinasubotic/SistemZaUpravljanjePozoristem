package application;
//enum za tipove osobe i enum za zanr predstave
public enum Tipovi {
	a,
	b,
	c,
	d,
	e,
	f,
	g;
	
	public static Tipovi izBrojaUTip(int tip) {
		switch(tip) {
		case 1 : return a;
		case 2 : return b;
		case 3 : return c;
		case 4 : return d;
		case 5 : return e;
		case 6 : return f;
		case 7 : return g;
	
		default : throw new IllegalArgumentException("Greska");
		}
	}
	public static String tipOsobe(Tipovi t) {
		switch(t) {
		case a : return "glumac";
		case b : return "reziser";
		case c : return "autor";
		default : throw new IllegalArgumentException("Greska");
		}
	}
	public static String tipZanra(Tipovi t) {
		switch(t) {
		case a : return "komedija";
		case b : return "farsa";
		case c : return "satira";
		case d : return "komedija restauracije";
		case e : return "tragedija";
		case f : return "istorija";
		case g : return "mjuzikl";
		default : throw new IllegalArgumentException("Greska");
		}
	}
	
	
	
	
}
