package server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class ServerGUI extends JFrame implements ActionListener{
	private static final long serialVersionUID = 4176997584750967754L;
	private JPanel contentPane;
	private JPanel p_left;
	private JPanel p_center;
	private JPanel p_right;
	private JTextArea ta_left;
	private JScrollPane sp_left;
	private JList<String> list_user;
	private JScrollPane sp_center;
	private JScrollPane sp_right;
	private JLabel lbl_user;
	private JLabel lbl_console;
	private JLabel lbl_rank;
	private JPopupMenu popupMenu;
	private JMenuItem mi_kick;
	private JMenuItem mi_record;
	private JSeparator separator;
	private DefaultListModel<Object> model_user;
	private ServerSocket server;
	private Socket client;
	public static ServerDB db = new ServerDB();
	private JTable table_rank;
	private Object[][] table_model = new Object[10][4];
	private DefaultTableModel modelT;
	private JPanel panel;
	private JLabel label;
	private JLabel label_1;
	private String[] columnNames;
	


	public ServerGUI() {
		setTitle("\uC6B0\uC8FC\uC804\uC7C1 \uC11C\uBC84");
		setResizable(false);
		getContentPane().setBackground(SystemColor.desktop);
		drawGUI();
		serverOpen();
	}
	
	public void drawGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 490);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(51, 51, 51));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(3, 3));
		setContentPane(contentPane);
		
		lbl_console = new JLabel("                  ▼ ▼ ▼ CONSOLE ▼ ▼ ▼                                                                                                                                                          "
								+ "        Version.4");
		lbl_console.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_console.setPreferredSize(new Dimension(200, 25));
		lbl_console.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_console.setForeground(new Color(51, 255, 51));
		lbl_console.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 12));
		contentPane.add(lbl_console, BorderLayout.NORTH);

		for(int i = 0; i < 10; i++){
				table_model[i][0] = i+1;
		}
		
		columnNames = new String[] {"순위", "ID", "기록", "캐릭터"};
		
		modelT = new DefaultTableModel(table_model, columnNames);
		
		lbl_rank = new JLabel("\u25B2 \u25B2 \u25B2 RANKING \u25B2 \u25B2 \u25B2            ");
		lbl_rank.setVerticalAlignment(SwingConstants.TOP);
		lbl_rank.setForeground(new Color(51, 255, 255));
		lbl_rank.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 12));
		lbl_rank.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_rank.setPreferredSize(new Dimension(200, 20));
		contentPane.add(lbl_rank, BorderLayout.SOUTH);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		p_left = new JPanel();
		panel.add(p_left, BorderLayout.WEST);
		FlowLayout flowLayout = (FlowLayout) p_left.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		p_left.setBackground(new Color(51, 51, 51));
		
		sp_left = new JScrollPane();
		sp_left.setBorder(new LineBorder(UIManager.getColor("SplitPaneDivider.draggingColor")));
		sp_left.setBackground(new Color(51, 51, 51));
		sp_left.setPreferredSize(new Dimension(238, 388));
		p_left.add(sp_left);
		
		ta_left = new JTextArea();
		ta_left.setForeground(UIManager.getColor("TabbedPane.highlight"));
		ta_left.setBackground(new Color(51, 51, 51));
		ta_left.setLineWrap(true);
		sp_left.setViewportView(ta_left);
		
		label = new JLabel("");
		panel.add(label);
		
		label_1 = new JLabel("");
		panel.add(label_1);
		
		p_center = new JPanel();
		panel.add(p_center, BorderLayout.CENTER);
		FlowLayout flowLayout_1 = (FlowLayout) p_center.getLayout();
		flowLayout_1.setVgap(0);
		flowLayout_1.setHgap(0);
		p_center.setForeground(new Color(255, 255, 255));
		p_center.setBackground(new Color(51, 51, 51));
		
		lbl_user = new JLabel("*  *  *  *  *  LOGIN USER  *  *  *  *  *");
		lbl_user.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_user.setPreferredSize(new Dimension(200, 25));
		lbl_user.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 12));
		lbl_user.setForeground(new Color(51, 255, 153));
		p_center.add(lbl_user);
		
		sp_center = new JScrollPane();
		sp_center.setBorder(new LineBorder(new Color(102, 102, 102)));
		sp_center.setBackground(Color.BLACK);
		sp_center.setPreferredSize(new Dimension(238, 364));
		p_center.add(sp_center);
		
		list_user = new JList<>();
		list_user.setForeground(SystemColor.control);
		list_user.setBackground(new Color(51, 51, 51));
		list_user.setBorder(null);
		sp_center.setViewportView(list_user);
		
		popupMenu = new JPopupMenu();
		addPopup(list_user, popupMenu);
		
		mi_kick = new JMenuItem("\uD68C\uC6D0 \uAC15\uD1F4");
		mi_kick.addActionListener(this);
		popupMenu.add(mi_kick);
		
		separator = new JSeparator();
		popupMenu.add(separator);
		
		mi_record = new JMenuItem("\uC804\uCCB4 \uBAA9\uB85D \uBCF4\uAE30");
		mi_record.addActionListener(this);
		popupMenu.add(mi_record);
		
		p_right = new JPanel();
		panel.add(p_right, BorderLayout.EAST);
		FlowLayout flowLayout_2 = (FlowLayout) p_right.getLayout();
		flowLayout_2.setVgap(0);
		flowLayout_2.setHgap(0);
		p_right.setForeground(new Color(255, 255, 255));
		p_right.setBackground(new Color(51, 51, 51));
		
		sp_right = new JScrollPane();
		sp_right.setBorder(new LineBorder(UIManager.getColor("SplitPaneDivider.draggingColor")));
		sp_right.setBackground(new Color(51, 51, 51));
		sp_right.setPreferredSize(new Dimension(238, 388));
		p_right.add(sp_right);
		
		table_rank = new JTable();
		table_rank.setEnabled(false);
		table_rank.setFillsViewportHeight(true);
		table_rank.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
		table_rank.setForeground(Color.WHITE);
		table_rank.setBackground(Color.DARK_GRAY);
		table_rank.setRequestFocusEnabled(false);
		table_rank.setRowHeight(36);
		table_rank.setModel(modelT);
		table_rank.getColumnModel().getColumn(0).setResizable(false);
		table_rank.getColumnModel().getColumn(0).setPreferredWidth(35);
		table_rank.getColumnModel().getColumn(1).setResizable(false);
		table_rank.getColumnModel().getColumn(1).setPreferredWidth(107);
		table_rank.getColumnModel().getColumn(2).setResizable(false);
		table_rank.getColumnModel().getColumn(2).setPreferredWidth(50);
		table_rank.getColumnModel().getColumn(3).setResizable(false);
		table_rank.getColumnModel().getColumn(3).setPreferredWidth(50);
		sp_right.setViewportView(table_rank);
		
		model_user = new DefaultListModel<>();
		
		setVisible(true);
	}
	
	public void serverOpen(){
		try {
			server = new ServerSocket(23231);
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void userSetModel(ListModel model){
		list_user.setModel(model);
	}
	
	public void rankSetModel(HashMap<String, Double> ranking, HashMap<String, String> characterType){
		Object[] idSet = ranking.keySet().toArray();				//id랑 기록 묶인 맵
		ArrayList<Double> scoreList = new ArrayList<>();
		for(int i = 0; i < idSet.length; i++){						//id에 묶인 밸류인 기록을 어레이리스트화 시킴
			scoreList.add(ranking.get(idSet[i]));
			if(i == 9) break;										//10개만..
		}
		for(int i = 0; i < idSet.length; i++){						//10개 테이블모델에 세팅 함.
			table_model[i][1] = idSet[i];
			table_model[i][2] = scoreList.get(i);
			if(i == 9) break;
		}
		
		Object[] charSet = characterType.keySet().toArray();			//id랑 플레이한 캐릭터 묶인 맵 (이하 로직은 id랑 기록 저장하는거랑 같음)
		ArrayList<String> charList = new ArrayList<>();
		for(int i = 0; i < charSet.length; i++){
			charList.add(characterType.get(charSet[i]));
			if(i == 9) break;
		}
		for(int i = 0; i < charList.size(); i++){
			table_model[i][3] = charList.get(i);
			if(i == 9) break;
		}
		modelT.setDataVector(table_model, columnNames);
		table_rank.setModel(modelT);								//세팅끝난 모델을 테이블에 적용시킴
	}
	
	public String getTime(){
		Calendar oCalendar = Calendar.getInstance( );
		String currentDate = (oCalendar.get(Calendar.MONTH) + 1)>9 ? ""+Integer.toString(oCalendar.get(Calendar.MONTH) + 1) : '0'+Integer.toString(oCalendar.get(Calendar.MONTH) + 1);
		currentDate += "/" + Integer.toString(oCalendar.get(Calendar.DAY_OF_MONTH));
		TimeZone jst = TimeZone.getTimeZone ("JST");
		Calendar cal = Calendar.getInstance ( jst ); // 주어진 시간대에 맞게 현재 시각으로 초기화된 GregorianCalender 객체를 반환.// 또는 Calendar now = Calendar.getInstance(Locale.KOREA);
		String time = cal.get ( Calendar.HOUR_OF_DAY ) + ":" +cal.get ( Calendar.MINUTE );
		
		
		
		return currentDate + "  " +time;
	}
	
	public void appendMsg(String message){
		ta_left.append("[" + getTime() + "] " + message + "\n");
		sp_left.getVerticalScrollBar().setValue(sp_left.getVerticalScrollBar().getMaximum());
	}
	

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
				if(source == mi_record){
					model_user.clear();
					if(mi_record.getText().equals("전체 목록 보기")){
						mi_record.setText("접속자 목록 보기");
						lbl_user.setText("*  *  *  *  *  EVERY USER  *  *  *  *  *");
						ArrayList<String> allList = new ArrayList<>();
						for (UserInfo userInfo : db.getAllInfo()) {
							allList.add(userInfo.getUser_id());
						}
						for (String id : allList) {
							model_user.addElement(id);
						}
						userSetModel(model_user);
					}else {
						mi_record.setText("전체 목록 보기");
						lbl_user.setText("*  *  *  *  *  LOGIN USER  *  *  *  *  *");
						for (String id : ServerThread.currentConnectedUser) {
							model_user.addElement(id);
						}
						userSetModel(model_user);
					}
				}//mi_record의 액션
				
				if(source == mi_kick){
					if(list_user.getSelectedValue() != null){
						int select = JOptionPane.showConfirmDialog(this, list_user.getSelectedValue() + "님을 탈퇴시키겠습니까?", "Kick", JOptionPane.OK_CANCEL_OPTION);
						if(select == JOptionPane.OK_OPTION){
							String targetId = db.searchInfo(list_user.getSelectedValue()).getUser_id();
							db.deleteInfo(targetId);
							ArrayList<String> allList = new ArrayList<>();
							for (UserInfo userInfo : db.getAllInfo()) {
								allList.add(userInfo.getUser_id());
							}
							model_user.clear();
							for (String id : allList) {
								model_user.addElement(id);
							}
							userSetModel(model_user);
						}
					}
				}
	}//actionPerformed()
}//class
