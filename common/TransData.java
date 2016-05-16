package common;

import java.io.Serializable;

public class TransData implements Serializable {
	private static final long serialVersionUID = 5905850979961626244L;

	public static final int HI_SCORE = 1;
	public static final int ACCOUNT_CHECK = 2;
	public static final int SIGN_UP = 3;
	
	private int command;
	private double hiScore;
	private int rank;
	private String id;
	private String pw;
	
	
	
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCommand() {
		return command;
	}
	public void setCommand(int command) {
		this.command = command;
	}
	public double getHiScore() {
		return hiScore;
	}
	public void setHiScore(double hiScore) {
		this.hiScore = hiScore;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	
}
