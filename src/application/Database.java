package application;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class Database {
	private Statement lk;
	private Connection la;
	Database(){
		try {
			la = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "Ar14E-65BIdJt#");
			lk = la.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected boolean getAuthenticationServer(String Admin, String Password) {
		ResultSet ad=null;
		boolean ku = false;
		try {
			ad = lk.executeQuery("select username,password from server_admin where username='"+ Admin +"'"+"and password='"+Password+"'");
			if(ad.next()) {
				ku = true;
			}
			else {
				ku = false;
			}
			ad.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			ku = false;
		}
		return ku;
	}

	protected HashMap<String,Object> LoadData () throws SQLException {
		ResultSet pq = lk.executeQuery("select * from users");
		HashMap<String,Object> kj = new HashMap<>();
		LinkedList<Object> user = new LinkedList<>();
		LinkedList<Object> book = new LinkedList<>();
		while (pq.next()) {
			Blob h = pq.getBlob(5);
			Object[] l = {pq.getInt(1),pq.getString(2),pq.getString(3),pq.getString(4),pq.getString(6),h.getBinaryStream()};
			user.add(l);
		}
		pq.close();
		ResultSet lq = lk.executeQuery("select * from books order by book_title");
		while(lq.next()) {
			Blob h = lq.getBlob(4);
			Object[] k = {lq.getString(1),lq.getString(2),lq.getString(3),h.getBinaryStream()};
			book.add(k);
		}
		kj.put("user", user);
		kj.put("book", book);
		lq.close();
		return kj;
	}
	protected LinkedList<Object> refreshUser() throws SQLException {
		ResultSet pq = lk.executeQuery("select * from users");
		LinkedList<Object> user = new LinkedList<>();
		while (pq.next()) {
			Blob h = pq.getBlob(5);
			Object[] l = {pq.getInt(1),pq.getString(2),pq.getString(3),pq.getString(4),pq.getString(6),h.getBinaryStream()};
			user.add(l);
		}
		pq.close();
		return user;
	}

	protected LinkedList<Object> searchUser(String g) throws SQLException {
		LinkedList<Object> user = null;
		if(g.equals("")) {
			user = refreshUser();
		}
		else {
		user = new LinkedList<>();
		ResultSet pq = lk.executeQuery("select * from users where name = '"+g+"' or password = '"+g+"'");
		while(pq.next()) {
			Blob h = pq.getBlob(5);
			Object[] l = {pq.getInt(1),pq.getString(2),pq.getString(3),pq.getString(4),pq.getString(6),h.getBinaryStream()};
			user.add(l);
		}
		pq.close();}
		return user;
	}

	protected LinkedList<Object> searchUser(int g) throws SQLException {
		LinkedList<Object> user = new LinkedList<>();
		ResultSet pq = lk.executeQuery("select * from users where id = "+g);
		while(pq.next()) {
			Blob h = pq.getBlob(5);
			Object[] l = {pq.getInt(1),pq.getString(2),pq.getString(3),pq.getString(4),pq.getString(6),h.getBinaryStream()};
			user.add(l);
		}
		pq.close();
		return user;
	}

	protected void deleteUser(int[] k) throws SQLException {
		for(int j : k) {
			lk.executeUpdate("delete from users where id="+j);
		}
	}

	protected LinkedList<Object> searchBook(String g) throws SQLException{
		LinkedList<Object> book = null;
		if(g.equals("")) {
			book = refreshBook();
		}
		else {
			book = new LinkedList<>();
			ResultSet lq = lk.executeQuery("select * from books where book_title = '"+g+"' or book_author = '"+g+"'");
			while(lq.next()) {
				Blob h = lq.getBlob(4);
				Object[] k = {lq.getString(1),lq.getString(2),lq.getString(3),h.getBinaryStream()};
				book.add(k);
			}
		lq.close();}
		return book;
	}

	protected void deleteBook(String[] k, String[] u) throws SQLException {
		PreparedStatement lk = la.prepareStatement("delete from books where book_title = ? and book_author = ?");
		for(int j = 0; j < k.length;j++) {
			String po = k[j];
			String jc = u[j];
			for(int a=0;a<5;a++) {
				if(Pattern.matches("[0-9]", String.valueOf(po.charAt(a)))) {
					continue;
				}
				else {po = po.substring(a, po.length());jc = jc.substring(a, jc.length());break;}
			}
			lk.setString(1, po);
			lk.setString(2, jc);
			lk.execute();
		}
		lk.close();
	}

	protected void updateBook(String[] titlebookawal, String[] authorbookawal, String[] titlebook, String[] authorbook, String[] descbook) throws SQLException {
		PreparedStatement hw = la.prepareStatement("update books set book_title = ? , book_author = ? , book_desc = ? where book_title = ? and book_author = ?");
		for(int j = 0; j < titlebook.length;j++) {
			String po = titlebookawal[j];
			String jc = authorbookawal[j];
			String mn = titlebook[j],mn2 = authorbook[j],mn3 = descbook[j];
			System.out.println(titlebook.length);
			for(int a=0;a<5;a++) {
				if(Pattern.matches("[0-9]", String.valueOf(po.charAt(a)))) {
					continue;
				}
				else {po = po.substring(a, po.length());jc = jc.substring(a, jc.length());mn = mn.substring(a, mn.length());mn2 = mn2.substring(a, mn2.length());break;}
			}		
			System.out.println(po+"\n"+jc+"\n"+mn+"\n"+mn2+"\n"+mn3);
			hw.setString(1, mn);
			hw.setString(2, mn2);
			hw.setString(3, mn3);
			hw.setString(4, po);
			hw.setString(5, jc);
			hw.execute();
		}
		hw.close();
	}

	protected void addBook(String title, String author, String desc, ByteArrayInputStream k) throws SQLException {
		PreparedStatement kja = la.prepareStatement("insert into books values(?,?,?,?)");
		kja.setString(1, title);
		kja.setString(2, author);
		kja.setString(3, desc);
		kja.setBlob(4, k);
		kja.closeOnCompletion();
		kja.execute();
	}

	protected LinkedList<Object> refreshBook() throws SQLException {
		ResultSet lq = lk.executeQuery("select * from books order by book_title");
		LinkedList<Object> book = new LinkedList<>();
		while (lq.next()) {
			Blob h = lq.getBlob(4);
			Object[] k = {lq.getString(1),lq.getString(2),lq.getString(3),h.getBinaryStream()};
			book.add(k);
		}
		lq.close();
		return book;
	}
	protected void closed() throws SQLException {
		lk.close();
		la.close();
	}
}