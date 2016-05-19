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
	private boolean runLoop;
	private DefaultListModel<Object> model_user = new DefaultListModel<>();
	private static final int RECENT_VERSION = 4;
	private String id;

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
						if(ServerGUI.db.searchInfo(data.getId()).getHi_score() < data.getHiScore()){		// 방금 들어온 버틴시간이 더 큰가 ?
							ServerGUI.db.updateScore(data.getId(), String.valueOf(data.getHiScore()), data.getCharacterType());
							gui.appendMsg(id + "님의 개인기록 경신! :" + data.getHiScore() + " with " + data.getCharacterType());
						}
					break;
				case TransData.TRY_LOG_IN:
					UserInfo info = ServerGUI.db.searchInfo(data.getId());
					if(info != null) id = info.getUser_id();						//getUser_id() 를 통해 밸류값으로 존재하는 아이디인지 확인함.
					if(id != null){
						if(data.getPw().equals(info.getUser_pw())){
							gui.appendMsg(client.getInetAddress() + "님. ID: " + id + "로 로그인!");
							currentConnectedUser.add(id);
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
					UserInfo info_sign = ServerGUI.db.searchInfo(data.getId());		//log in이랑 같은 방식
					String id_sign = null;
					if(info_sign != null) id_sign = info_sign.getUser_id();
					if(id_sign != null){
						oos.writeObject(false);
					}else {
						gui.appendMsg(client.getInetAddress() + "님. ID: " + id_sign + "로 회원가입!");
						ServerGUI.db.insertInfo(data.getId(), data.getPw());		//db에 저장
						oos.writeObject(true);
					}
					break;
				case TransData.TABLE_REFRESH:
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
