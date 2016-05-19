package server;

public class UserInfo {
	private String user_id, user_pw, user_char;
	private double hi_score;
	
	public UserInfo(String user_id, String user_pw){
		this.user_id = user_id;
		this.user_pw = user_pw;
	}
	
	public UserInfo(String user_id, String user_pw, double hi_score, String user_char){
		this.user_id = user_id;
		this.user_pw = user_pw;
		this.hi_score = hi_score;
		this.user_char = user_char;
	}
	

	@Override
	public String toString() {
		return "UserInfo [user_id=" + user_id + ", user_pw=" + user_pw + ", hi_score=" + hi_score + ", user_char="
				+ user_char + "]";
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_pw() {
		return user_pw;
	}

	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}

	public double getHi_score() {
		return hi_score;
	}

	public void setHi_score(double hi_score) {
		this.hi_score = hi_score;
	}

	public String getUser_char() {
		return user_char;
	}

	public void setUser_char(String user_char) {
		this.user_char = user_char;
	}
	

	
}
