package application;
import java.sql.Date;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class Konekcija {

	public static Connection getConnection() {
		Connection con = null;
		try {
			// Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projekat", "root", "1234");
			// con.close();
			System.out.println("Radi");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public static void ucitajPodatke() {

		Connection myConn = null;

		try {
			myConn = getConnection();
			Statement myStmt = myConn.createStatement();

			ResultSet Rs = myStmt.executeQuery("select * from izvodjenje_predstave");
			while (Rs.next()) {
				/*
				Date d = Rs.getDate("datum_i_vrijeme");
				Time t =Rs.getTime("datum_i_vrijeme");
				String stringD = new SimpleDateFormat("yyyy-MM-dd").format(d);
				String stringT = new SimpleDateFormat("hh:mm:ss").format(t);
				LocalDate datum = LocalDate.parse(stringD);
				LocalTime vrijeme = LocalTime.parse(stringT);
				LocalDateTime ldt= LocalDateTime.of(d.toLocalDate(), t.toLocalTime());
				
				
				TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");
				TimeZone.setDefault(utcTimeZone);
				
			
				
				LocalDateTime ldt = Rs.getTimestamp("datum_i_vrijeme").toLocalDateTime();
				LocalDateTime ldt = new LocalDateTime();
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				//TimeStamp ts 
				*/
				
				/*
				Date date = Rs.getDate("datum_i_vrijeme");
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String s = df.format(date);
				try {
				java.util.Date parsedDate = df.parse(s);
				Timestamp ts = new Timestamp(parsedDate.getTime());
				new Izvodjenje_predstave(Rs.getInt("id"), Rs.getInt("predstava_id"), Rs.getInt("pozoriste_id"),
						Rs.getDouble("cijena"), ts);
				}
				catch(ParseException e) {
					e.printStackTrace();
				}
				*/
				
				/*
				Date date = Rs.getDate("datum_i_vrijeme");
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String s = df.format(date);
				DateTimeFormatter f =DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				LocalDateTime ldt =LocalDateTime.parse(s,f);
				new Izvodjenje_predstave(Rs.getInt("id"), Rs.getInt("predstava_id"), Rs.getInt("pozoriste_id"),
						Rs.getDouble("cijena"), ldt);
				
				*/
				Timestamp date = Rs.getTimestamp("datum_i_vrijeme");
				new Izvodjenje_predstave(Rs.getInt("id"), Rs.getInt("predstava_id"), Rs.getInt("pozoriste_id"),
						Rs.getDouble("cijena"), date);
				
			}
			ResultSet Rs1 = myStmt.executeQuery("select * from karta");
			while (Rs1.next()) {
				new Karta(Rs1.getInt("id"), Rs1.getInt("izvodjenje_predstave_id"), Rs1.getInt("status"),
						Rs1.getInt("posjetilac_id"), Rs1.getInt("broj_karta"));
			}

			ResultSet Rs2 = myStmt.executeQuery("select * from osoblje");
			while (Rs2.next()) {
				new Osoblje(Rs2.getInt("id"), Rs2.getString("ime"), Rs2.getString("prezime"), Rs2.getInt("tip"));
			}

			ResultSet Rs3 = myStmt.executeQuery("select * from osoblje_predstave");
			while (Rs3.next()) {
				new Osoblje_predstave(Rs3.getInt("id"), Rs3.getInt("osoblje_id"), Rs3.getInt("predstava_id"));
			}

			ResultSet Rs4 = myStmt.executeQuery("select * from posjetilac_pozorista");
			while (Rs4.next()) {
				new Posjetilac_pozorista(Rs4.getInt("id"), Rs4.getString("ime"), Rs4.getString("prezime"),
						Rs4.getString("korisnicko_ime"), Rs4.getString("lozinka"));
			}

			ResultSet Rs5 = myStmt.executeQuery("SELECT * FROM pozoriste");
			while (Rs5.next()) {
				new Pozoriste(Rs5.getInt("id"), Rs5.getString("naziv"), Rs5.getString("grad"),
						Rs5.getInt("broj_sjedista"));
			}

			ResultSet Rs6 = myStmt.executeQuery("select * from predstava");
			while (Rs6.next()) {
				new Predstava(Rs6.getInt("id"), Rs6.getString("naziv"), Rs6.getInt("zanr"));
			}

			ResultSet Rs7 = myStmt.executeQuery("select * from radnik_pozorista");
			while (Rs7.next()) {
				new Radnik_pozorista(Rs7.getInt("id"), Rs7.getString("ime"), Rs7.getString("prezime"),Rs7.getString("korisnicko_ime"), Rs7.getString("lozinka"),Rs7.getInt("pozoriste_id"));
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (myConn != null) {
			try {
				myConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void ucitajOsoblje(){
		Connection myConn = null;

		try {
			myConn = getConnection();
			Statement myStmt = myConn.createStatement();
			ResultSet Rs2 = myStmt.executeQuery("select * from osoblje");
			while (Rs2.next()) {
				new Osoblje(Rs2.getInt("id"), Rs2.getString("ime"), Rs2.getString("prezime"), Rs2.getInt("tip"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (myConn != null) {
			try {
				myConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void ucitajPredstavu() {
		Connection myConn = null;

		try {
			myConn = getConnection();
			Statement myStmt = myConn.createStatement();
			ResultSet Rs6 = myStmt.executeQuery("select * from predstava");
			while (Rs6.next()) {
				new Predstava(Rs6.getInt("id"), Rs6.getString("naziv"), Rs6.getInt("zanr"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (myConn != null) {
			try {
				myConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void ucitajKartu() {
		Connection myConn = null;

		try {
			myConn = getConnection();
			Statement myStmt = myConn.createStatement();
			ResultSet Rs1 = myStmt.executeQuery("select * from karta");
			while (Rs1.next()) {
				new Karta(Rs1.getInt("id"), Rs1.getInt("izvodjenje_predstave_id"), Rs1.getInt("status"),
						Rs1.getInt("posjetilac_id"), Rs1.getInt("broj_karta"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (myConn != null) {
			try {
				myConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void ucitajPosjetioca() {
		Connection myConn = null;

		try {
			myConn = getConnection();
			Statement myStmt = myConn.createStatement();
			ResultSet Rs4 = myStmt.executeQuery("select * from posjetilac_pozorista");
			while (Rs4.next()) {
				new Posjetilac_pozorista(Rs4.getInt("id"), Rs4.getString("ime"), Rs4.getString("prezime"),
						Rs4.getString("korisnicko_ime"), Rs4.getString("lozinka"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (myConn != null) {
			try {
				myConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void ucitajRadnika() {
		Connection myConn = null;

		try {
			myConn = getConnection();
			Statement myStmt = myConn.createStatement();
			ResultSet Rs7 = myStmt.executeQuery("select * from radnik_pozorista");
			while (Rs7.next()) {
				new Radnik_pozorista(Rs7.getInt("id"), Rs7.getString("ime"), Rs7.getString("prezime"),Rs7.getString("korisnicko_ime"), Rs7.getString("lozinka"),Rs7.getInt("pozoriste_id"));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (myConn != null) {
			try {
				myConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void ucitajOsobljePredstave() {
		Connection myConn = null;

		try {
			myConn = getConnection();
			Statement myStmt = myConn.createStatement();
			ResultSet Rs3 = myStmt.executeQuery("select * from osoblje_predstave");
			while (Rs3.next()) {
				new Osoblje_predstave(Rs3.getInt("id"), Rs3.getInt("osoblje_id"), Rs3.getInt("predstava_id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (myConn != null) {
			try {
				myConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void ucitajIzvodjenjePredstave() {
		Connection myConn = null;

		try {
			myConn = getConnection();
			Statement myStmt = myConn.createStatement();
			ResultSet Rs = myStmt.executeQuery("select * from izvodjenje_predstave");
			while (Rs.next()) {
				Timestamp date = Rs.getTimestamp("datum_i_vrijeme");
				new Izvodjenje_predstave(Rs.getInt("id"), Rs.getInt("predstava_id"), Rs.getInt("pozoriste_id"),
						Rs.getDouble("cijena"), date);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (myConn != null) {
			try {
				myConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void ucitajPozoriste() {
		Connection myConn = null;

		try {
			myConn = getConnection();
			Statement myStmt = myConn.createStatement();
			ResultSet Rs5 = myStmt.executeQuery("SELECT * FROM pozoriste");
			while (Rs5.next()) {
				new Pozoriste(Rs5.getInt("id"), Rs5.getString("naziv"), Rs5.getString("grad"),
						Rs5.getInt("broj_sjedista"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (myConn != null) {
			try {
				myConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	

	public static void main(String[] args) {
		getConnection();

	}

}
