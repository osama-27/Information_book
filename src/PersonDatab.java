import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class PersonDatab {
	private Connection con;
	private String url;
	private ArrayList<PersonInfo> plist;

	public PersonDatab(String file) {
		plist = new ArrayList<PersonInfo>();
		url = "jdbc:ucanaccess://";
		url += System.getProperty("user.home");
		url += System.getProperty("file.separator") + "Desktop"
				+ System.getProperty("file.separator") + file;
		System.out.println(url);
		getConnection(url);
	}

	private Connection getConnection(String url) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		} catch (java.lang.ClassNotFoundException e) {
			System.err.println("ClassNotFoundException: " + e.getMessage());
		}
		try {
			con = DriverManager.getConnection(url);
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}
		return con;
	}

	protected void saveP(PersonInfo p) {
		try {
			String sql = "INSERT INTO Person(name2,address2, phone2, email2) VALUES (?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, p.getName());
			ps.setString(2, p.getAddress());
			ps.setString(3, p.getPhone());
			ps.setString(4, p.getEmail());
			ps.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	protected ArrayList<PersonInfo> searchP(String n) {
		try {
			String sql = "Select * FROM Person WHERE name2 LIKE '%" + n + "%'";
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			String name = "", address = "", email = "", phone = "";
			while (rs.next()) {
				int id = rs.getInt("id");
				name = rs.getString("name2");
				address = rs.getString("address2");
				phone = rs.getString("phone2");
				email = rs.getString("email2");
				PersonInfo person = new PersonInfo(name, address, phone, email);
				person.setId(id);
				plist.add(person);

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return plist;

	}

	protected void updateP(PersonInfo p, int id) {
		try {
			String sql = "UPDATE Person SET name2 = ?, address2=? , "
					+ "phone2=? , email2=? where id=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, p.getName());
			ps.setString(2, p.getAddress());
			ps.setString(3, p.getPhone());
			ps.setString(4, p.getEmail());
			ps.setInt(5, id);
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	protected void deleteP(int id) {
		try {
			String sql = "DELETE * FROM Person where id=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}