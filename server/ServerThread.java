package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.DefaultListModel;

import common.TransData;

public class ServerThread implements Runnable{
	private Socket client;
	private static ArrayList<ServerThread> thList = new ArrayList<>();
	private static ArrayList<String> currentConnectedUser = new ArrayList<>();
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private ServerGUI gui;
	private String id;
	private boolean runLoop;
	private DefaultListModel model = new DefaultListModel<>();

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
				case TransData.HI_SCORE:
					gui.appendMsg(id + "님의 방금 기록 : " + data.getHiScore());
					if(ServerGUI.ranking.containsKey(data.getId())){						// 랭킹에 등록된 유저인가?
						if(ServerGUI.ranking.get(data.getId()) < data.getHiScore()){		// 등록된 유저 이면서 방금 들어온 버틴시간이 더 큰가 ? 
							ServerGUI.ranking.put(data.getId(), data.getHiScore());		// 그렇다면 점수 갱신.
							gui.appendMsg(id + "님의 개인기록 경신! :" + data.getHiScore());
							model.clear();
							
							//////////////////////////////////////////////	여기에 랭킹 순서 정렬해서 붙이기
							
						}
					}else {
						ServerGUI.ranking.put(data.getId(), data.getHiScore());			// 등록되지 않은 유저라면 바로 갱신
					}
					gui.saveRankData();
					break;
				case TransData.ACCOUNT_CHECK:
					String value = ServerGUI.accountMap.get(data.getId());
					if(value != null){
						if(value.equals(data.getPw())){
							id = data.getId();
							gui.appendMsg(client.getInetAddress() + "님. ID: " + id + "로 로그인!");
							model.clear();
							currentConnectedUser.add(data.getId());
							for (String id : currentConnectedUser) {
								model.addElement(id);
							}
							gui.userSetModel(model);
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
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				runLoop = !runLoop;
				model.clear();
				currentConnectedUser.remove(id);
				for (String id : currentConnectedUser) {
					model.addElement(id);
				}
				gui.userSetModel(model);
				gui.appendMsg(id + "님 접속종료");
			}
		}
	}//run()
	
	
//	DefaultListModel model = new DefaultListModel<>();
//	Set<String> idSet = ServerGUI.accountMap.keySet();
//	for (String id : idSet) {
//		model.addElement(id);
//	}
//	gui.userSetModel(model);
//	이것은 전체 유저를 리스트에 박는 것 .	
	
}//class
