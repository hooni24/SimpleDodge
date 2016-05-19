package common;

import java.io.Serializable;
import java.util.HashMap;

public class TransData implements Serializable {
	private static final long serialVersionUID = 5905850979961626244L;

	public static final int GAME_OVER = 1;
	public static final int TRY_LOG_IN = 2;
	public static final int SIGN_UP = 3;
	public static final int TABLE_REFRESH = 4;
	public static final int VERSION_CHEK = 5;
	
	private int command;
	private String characterType;
	private double hiScore;
	private int rank;
	private String id;
	private String pw;
	private HashMap<String, Double> rankingData;
	private HashMap<String, String> charData;
	private int clientVersion, recentVersion;
	private boolean isVersionRecent;
	
	
	
	
	public boolean getIsVersionRecent() {
		return isVersionRecent;
	}
	public void setIsVersionRecent(boolean isVersionRecent) {
		this.isVersionRecent = isVersionRecent;
	}
	public int getClientVersion() {
		return clientVersion;
	}
	public void setClientVersion(int clientVersion) {
		this.clientVersion = clientVersion;
	}
	public int getRecentVersion() {
		return recentVersion;
	}
	public void setRecentVersion(int recentVersion) {
		this.recentVersion = recentVersion;
	}
	public HashMap<String, Double> getRankingData() {
		return rankingData;
	}
	public void setRankingData(HashMap<String, Double> rankingData) {
		this.rankingData = rankingData;
	}
	public HashMap<String, String> getCharData() {
		return charData;
	}
	public void setCharData(HashMap<String, String> charData) {
		this.charData = charData;
	}
	public String getCharacterType() {
		return characterType;
	}
	public void setCharacterType(String characterType) {
		this.characterType = characterType;
	}
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
