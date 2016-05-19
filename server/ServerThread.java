package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

import common.TransData;

public class ServerThread implements Runnable{
	private Socket client;
	private static ArrayList<ServerThread> thList = new ArrayList<>();
	public static ArrayList<String> currentConnectedUser = new ArrayList<>();
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private ServerGUI gui;
	private String id;
	private boolean runLoop;
	private DefaultListModel<Object> model_user = new DefaultListModel<>();
	private static final int RECENT_VERSION = 3;

	public ServerThread(Socket client, ServerGUI gui) {
		this.client = client;	this.gui = gui;
		try {
			ois = new ObjectInputStream(client.getInputStream());
			oos = new ObjectOutputStream(client.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		thList.add(this);
	}

	@Override
	public void run() {
		while(!runLoop){
			try {
				TransData data = (TransData) ois.readObject();
				switch(data.getCommand()){
				case TransData.GAME_OVER:
					gui.appendMsg(id + "님의 방금 기록 : " + data.getHiScore());
					if(ServerGUI.ranking.containsKey(data.getId())){						// 랭킹에 등록된 유저인가?
						if(ServerGUI.ranking.get(data.getId()) < data.getHiScore()){		// 등록된 유저 이면서 방금 들어온 버틴시간이 더 큰가 ? 
							ServerGUI.ranking.put(data.getId(), data.getHiScore());		// 그렇다면 점수 갱신.
							ServerGUI.characterMap.put(data.getId(), data.getCharacterType());
							gui.appendMsg(id + "님의 개인기록 경신! :" + data.getHiScore() + " with " + data.getCharacterType());
							gui.rankSetModel(ServerGUI.ranking, ServerGUI.characterMap);
						}
					}else {
						ServerGUI.ranking.put(data.getId(), data.getHiScore());
						ServerGUI.characterMap.put(data.getId(), data.getCharacterType());
						gui.appendMsg(id + "님의 최초 기록 등록! :" + data.getHiScore() + " with " + data.getCharacterType());
						gui.rankSetModel(ServerGUI.ranking, ServerGUI.characterMap);
					}
					gui.saveRankData();
					gui.saveCharData();
					break;
				case TransData.TRY_LOG_IN:
					String value = ServerGUI.accountMap.get(data.getId());
					if(value != null){
						if(value.equals(data.getPw())){
							id = data.getId();
							gui.appendMsg(client.getInetAddress() + "님. ID: " + id + "로 로그인!");
							model_user.clear();
							currentConnectedUser.add(data.getId());
							for (String id : currentConnectedUser) {
								model_user.addElement(id);
							}
							gui.userSetModel(model_user);
							oos.writeObject(1);					//로그인성공 = 1, pw불일치 = 2, id미존재 = 3...
						}else {
							oos.writeObject(2);
						}
					}else {
						oos.writeObject(3);
					}
					break;
				case TransData.SIGN_UP:
					value = ServerGUI.accountMap.get(data.getId());		//가입성공 true, 가입실패 false
					if(value != null){
						oos.writeObject(false);
					}else {
						ServerGUI.accountMap.put(data.getId(), data.getPw());
						gui.appendMsg(client.getInetAddress() + "님. ID: " + data.getId() + "로 회원가입!");
						gui.saveUserData();
						oos.writeObject(true);
					}
					break;
				case TransData.TABLE_REFRESH:
					data.setCharData(ServerGUI.characterMap);
					data.setRankingData(ServerGUI.ranking);
					oos.writeObject(data);
					break;
				case TransData.VERSION_CHEK:
					if(data.getClientVersion() == RECENT_VERSION){
						data.setIsVersionRecent(true);
						oos.writeObject(data);
					}else {
						data.setRecentVersion(RECENT_VERSION);
						data.setIsVersionRecent(false);
						oos.writeObject(data);
					}
					break;
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				runLoop = !runLoop;
				model_user.clear();
				currentConnectedUser.remove(id);
				for (String id : currentConnectedUser) {
					model_user.addElement(id);
				}
				gui.userSetModel(model_user);
				gui.appendMsg(id + "님 접속종료");
			}
		}
	}//run()
	
}//class
