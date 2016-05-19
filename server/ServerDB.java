package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class ServerDB {
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private Properties info;
	
	public void makeConnection(){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 로딩 성공");
			info = new Properties();
			info.put("user", "ksh");
			info.put("password", "0000");
			con = DriverManager.getConnection(url, info);
			System.out.println("오라클 접속 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}//makeConnection()
	
	public void closeAll(){
			try {
				if(con != null) con.close();
				if(pstmt != null) pstmt.close();
				if(rs != null) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}//closeAll()
	
	public ArrayList<UserInfo> getAllInfo(){
		ArrayList<UserInfo> result = new ArrayList<>();
		try {
			makeConnection();
			String sql = "SELECT user_id, user_pw, hi_score, user_char FROM spacewar";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				result.add(new UserInfo(rs.getString(1), rs.getString(2), Double.parseDouble(rs.getString(3)), rs.getString(4)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return result;
	}//getAllInfo()
	
	public synchronized void insertInfo(String user_id, String user_pw, String hi_score, String user_char){
		try {
			makeConnection();
			String sql = "INSERT INTO spacewar "
						+ "VALUES (?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_id);
			pstmt.setString(2, user_pw);
			pstmt.setString(3, hi_score);
			pstmt.setString(4, user_char);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeAll();
		}
	}//insertInfo()
	
	public synchronized void insertInfo(String user_id, String user_pw){		//회원가입 할때만 insert 하니까 다른 건 필요 없다.
		try {
			makeConnection();
			String sql = "INSERT INTO spacewar (user_id, user_pw) "
						+ "VALUES (?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_id);
			pstmt.setString(2, user_pw);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeAll();
		}
	}//insertInfo()
	
	public synchronized void deleteInfo(String user_id){
		try {
			makeConnection();
			String sql = "DELETE spacewar WHERE user_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
	}//deleteInfo()
	
	public synchronized void updateScore(String user_id, String hi_score, String user_char){
		try {
			makeConnection();
			String sql = "UPDATE spacewar SET hi_score = ?, user_char = ? WHERE user_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, hi_score);
			pstmt.setString(2, user_char);
			pstmt.setString(3, user_id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
	}//updateScore()
	
	public UserInfo searchInfo(String user_id){
		UserInfo result = null;
		try {
			makeConnection();
			String sql = "SELECT * FROM spacewar WHERE user_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				result = new UserInfo(rs.getString(1), rs.getString(2), Double.parseDouble(rs.getString(3)), rs.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}//searchInfo()

	public static void main(String[] args) {
		ServerDB d = new ServerDB();
		System.out.println(d.searchInfo("fewf"));
		System.out.println(d.getAllInfo());
	}//main
}//class
