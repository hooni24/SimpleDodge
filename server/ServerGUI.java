package server;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.SwingConstants;

public class ServerGUI extends JFrame{
	private JPanel p_left;
	private JPanel p_center;
	private JPanel p_right;
	private JTextArea ta_log;
	private JLabel lbl_record;
	private JList list_user;
	private JList list_rank;
	private DefaultListModel listmodel_user, listmodel_rank;
	
	private ServerSocket server;
	private Socket client;
	private ObjectInputStream ois_user;
	private ObjectOutputStream oos_user;
	private ObjectInputStream ois_rank;
	private ObjectOutputStream oos_rank;
	private File file_user = new File("C:/java/Data/Dodge/SaveFiles/UserData.ser");
	private File file_rank = new File("C:/java/Data/Dodge/SaveFiles/RankData.ser");
	public static HashMap<String, String> accountMap = new HashMap<>();
	public static HashMap<String, Double> ranking = new HashMap<>();


	public ServerGUI() {
		if(file_user.exists()){
			accountMap = loadUserData();
		}else {
			saveUserData();
		}
		if(file_rank.exists()){
			ranking = loadRankData();
		}else {
			saveRankData();
		}
		drawGUI();
		serverOpen();
	}
	
	public void drawGUI(){
		setTitle("Game Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 600);
		setLocationRelativeTo(null);
		GridLayout gridLayout = new GridLayout(1, 3);
		gridLayout.setHgap(10);
		getContentPane().setLayout(gridLayout);
		p_left = new JPanel();		p_left.setLayout(new BorderLayout());
		p_center = new JPanel();	p_center.setLayout(new BorderLayout());
		p_right = new JPanel();		p_right.setLayout(new BorderLayout());
	
		ta_log = new JTextArea();
		ta_log.setEditable(false);
		p_left.add(ta_log);

		list_rank = new JList();
		p_right.add(list_rank);
		
		getContentPane().add(p_left);
		getContentPane().add(p_center);
		
		list_user = new JList();
		p_center.add(list_user, BorderLayout.CENTER);
		getContentPane().add(p_right);
		lbl_record = new JLabel("기록");
		lbl_record.setFont(new Font("gulim", Font.BOLD, 60));
		lbl_record.setHorizontalAlignment(SwingConstants.CENTER);
		p_center.add(lbl_record, BorderLayout.SOUTH);
		
		setVisible(true);
	}
	
	public void serverOpen(){
		try {
			server = new ServerSocket(7979);
			appendMsg("서버 오픈!");
			while(true){
				appendMsg("대기중......");
				client = server.accept();
				appendMsg("사용자 접속!! IP : " + client.getInetAddress());

				new Thread(new ServerThread(client, this)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void userSetModel(ListModel model){
		list_user.setModel(model);
	}
	
	public String getTime(){
		TimeZone jst = TimeZone.getTimeZone ("JST");
		Calendar cal = Calendar.getInstance ( jst ); // 주어진 시간대에 맞게 현재 시각으로 초기화된 GregorianCalender 객체를 반환.// 또는 Calendar now = Calendar.getInstance(Locale.KOREA);
		String time = cal.get ( Calendar.HOUR_OF_DAY ) + ":" +cal.get ( Calendar.MINUTE ) + ":" + cal.get ( Calendar.SECOND );
		return time;
	}
	
	public void appendMsg(String message){
		ta_log.append("[" + getTime() + "] " + message + "\n");
	}
	
	public HashMap<String, Double> loadRankData(){
		HashMap<String, Double> result = null;
		try {
				ois_rank = new ObjectInputStream(new FileInputStream(file_rank));
				result =  (HashMap<String, Double>) ois_rank.readObject();
				ois_rank.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public HashMap<String, String> loadUserData(){
		HashMap<String, String> result = null;
		try {
			ois_user = new ObjectInputStream(new FileInputStream(file_user));
			result = (HashMap<String, String>) ois_user.readObject();
			ois_user.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void saveRankData(){
		try {
			oos_rank = new ObjectOutputStream(new FileOutputStream(file_rank));
			oos_rank.writeObject(ranking);
			oos_rank.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveUserData(){
		try {
			oos_user = new ObjectOutputStream(new FileOutputStream(file_user));
			oos_user.writeObject(accountMap);
			oos_user.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
