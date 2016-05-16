package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import common.TransData;

public class ServerThread implements Runnable{
	private Socket client;
	private static ArrayList<ServerThread> thList = new ArrayList<>();
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private boolean runLoop;
	private static ArrayList<Double> scoreList = new ArrayList<>();
	private static HashMap<String, String> accountMap = new HashMap<>();
	private static HashMap<String, Double> ranking = new HashMap<>();

	public ServerThread(Socket client) {
		this.client = client;
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
					scoreList.add(data.getHiScore());
					ranking.put(data.getId(), data.getHiScore());
					System.out.println("Ranked in..!! ID:" + data.getId() + " / SCORE:" + data.getHiScore());
					break;
				case TransData.ACCOUNT_CHECK:
					String value = accountMap.get(data.getId());
					if(value != null){
						if(value.equals(data.getPw())){
							oos.writeObject(1);					//로그인성공 = 1, pw불일치 = 2, id미존재 = 3...
						}else {
							oos.writeObject(2);
						}
					}else {
						oos.writeObject(3);
					}
					break;
				case TransData.SIGN_UP:
					value = accountMap.get(data.getId());		//가입성공 true, 가입실패 false
					if(value != null){
						oos.writeObject(false);
						System.out.println("false날림");
					}else {
						accountMap.put(data.getId(), data.getPw());
						oos.writeObject(true);			
						System.out.println("true날림");
					}
					break;
				}
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				runLoop = !runLoop;
				System.out.println("disconnected!");
			}
		}
	}
}
