package client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import client.gui.dialog.BirdInfoDialog;
import client.gui.dialog.GhostInfoDialog;
import client.gui.dialog.NoticeDialog;
import client.gui.dialog.PatchDialog;
import common.TransData;

public class SelectWindow extends JFrame implements ActionListener, KeyListener, Runnable{
	private static final long serialVersionUID = -3459467354956808841L;
	private JButton btn_ghost, btn_bird;
	public BufferedImage bi_ghost, bi_bird;
	private File file_ghost, file_bird;
	private JPanel p_characters, p_ghost, p_bird;
	private Socket client;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private String id;
	private JPanel p_south;
	private JButton btn_rank;
	private JButton btn_ghost_info;
	private JButton btn_bird_info;
	private JButton btn_patch;
	private AudioInputStream ais;
	private Clip clip;
	
	private final int VERSION_NUMBER = 4;
	private JPanel panel;
	private JLabel lbl_version;
	private JButton btn_notice;
	
	public SelectWindow() {
		randomBGM();

		try {
//			jar버전 데스크탑 아이피
//			client = new Socket("203.233.196.232", 23231);
//			eclipse 버전 로컬호스트
			client = new Socket("localhost", 23231);
			oos = new ObjectOutputStream(client.getOutputStream());
			ois = new ObjectInputStream(client.getInputStream());
			
			TransData data = new TransData();
			data.setCommand(TransData.VERSION_CHEK);
			data.setClientVersion(VERSION_NUMBER);
			oos.writeObject(data);
			
			data = (TransData)ois.readObject();
			if(!data.getIsVersionRecent()){
				JOptionPane.showMessageDialog(this, "최신 버전으로 업데이트 해 주세요!\n최신버전 : Ver." + data.getRecentVersion() + "\n현재버전 : Ver." + VERSION_NUMBER + "\n\n문의 : D반 김성훈");
				System.exit(0);
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		drawUI();
	}
	
	public void drawUI(){
		setTitle("\uC6B0\uC8FC\uC804\uC7C1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
		setResizable(false);
		p_characters = new JPanel();
		p_characters.setBackground(Color.DARK_GRAY);
		p_characters.setLayout(new FlowLayout());
		
		ghostMenu();
		birdMenu();
		
		setLocationRelativeTo(null);
		
		getContentPane().add(p_characters);
		
		p_south = new JPanel();
		p_south.setBackground(Color.DARK_GRAY);
		getContentPane().add(p_south, BorderLayout.SOUTH);
		
		btn_rank = new JButton("\uC21C\uC704\uD45C");
		btn_rank.setFont(new Font("D2Coding", Font.PLAIN, 15));
		btn_rank.setForeground(new Color(0, 255, 0));
		btn_rank.setBackground(Color.DARK_GRAY);
		btn_rank.addActionListener(this);
		p_south.add(btn_rank);
		
		btn_patch = new JButton("\uD328\uCE58\uB178\uD2B8");
		btn_patch.setFont(new Font("D2Coding", Font.PLAIN, 15));
		btn_patch.setForeground(new Color(0, 255, 0));
		btn_patch.setBackground(Color.DARK_GRAY);
		btn_patch.addActionListener(this);
		p_south.add(btn_patch);
		
		btn_notice = new JButton("\uACF5\uC9C0\uC0AC\uD56D");
		btn_notice.addActionListener(this);
		btn_notice.setForeground(Color.GREEN);
		btn_notice.setFont(new Font("D2Coding", Font.PLAIN, 15));
		btn_notice.setBackground(Color.DARK_GRAY);
		p_south.add(btn_notice);
		
		panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		getContentPane().add(panel, BorderLayout.NORTH);
		
		lbl_version = new JLabel("\uC6B0\uC8FC\uC804\uC7C1 Ver.4");
		lbl_version.setFont(new Font("D2Coding", Font.PLAIN, 26));
		lbl_version.setForeground(new Color(173, 255, 47));
		panel.add(lbl_version);
		setVisible(true);
		new LoginGUI(this, ois, oos);
	}
	
	
	public void setId(String id) {
		this.id = id;
	}

	public void ghostMenu(){
		p_ghost = new JPanel();
		p_ghost.setBorder(new EtchedBorder(EtchedBorder.RAISED, Color.LIGHT_GRAY, Color.BLACK));
		p_ghost.setLayout(new BorderLayout());
		file_ghost = new File("./Dodge/Character/Ghost/Ghost_Menu.ksh");
		
		try {
			bi_ghost = ImageIO.read(file_ghost);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "이미지 파일 손상 또는 없음");
			System.exit(0);
		}
		
		class p_ghostImg extends JComponent{
			private static final long serialVersionUID = 5268273211264080430L;
			public void paint(Graphics g){
				g.drawImage(bi_ghost, 0, 0, null);
			}
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(bi_ghost.getWidth(), bi_ghost.getHeight());
			}
		}//inner class - ghostImg Panel
		p_ghostImg gi = new p_ghostImg();
		JPanel p_ghostCenter = new JPanel();
		p_ghostCenter.setBackground(Color.DARK_GRAY);
		p_ghostCenter.add(gi);
		p_ghost.add(p_ghostCenter, BorderLayout.CENTER);
		p_ghostCenter.setAlignmentX(CENTER_ALIGNMENT);
		btn_ghost = new JButton("\uC720\uB839\uB9E8 \uC120\uD0DD");
		btn_ghost.setFont(new Font("D2Coding", Font.PLAIN, 15));
		btn_ghost.setForeground(new Color(0, 255, 0));
		btn_ghost.setBackground(Color.DARK_GRAY);
		btn_ghost.addActionListener(this);
		btn_ghost.addKeyListener(this);
		p_ghost.add(btn_ghost, BorderLayout.SOUTH);
		p_characters.add(p_ghost);
		
		btn_ghost_info = new JButton("\uCE90\uB9AD\uD130 \uC815\uBCF4");
		btn_ghost_info.setFont(new Font("D2Coding", Font.PLAIN, 15));
		btn_ghost_info.setForeground(new Color(0, 255, 0));
		btn_ghost_info.setBackground(Color.DARK_GRAY);
		btn_ghost_info.addKeyListener(this);
		btn_ghost_info.addActionListener(this);
		p_ghost.add(btn_ghost_info, BorderLayout.NORTH);
	}
	
	private void birdMenu() {
		p_bird = new JPanel();
		p_bird.setBorder(new EtchedBorder(EtchedBorder.RAISED, Color.LIGHT_GRAY, Color.BLACK));
		p_bird.setLayout(new BorderLayout());
		file_bird = new File("./Dodge/Character/Bird/Bird_Menu.ksh");
		
		try {
			bi_bird = ImageIO.read(file_bird);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "이미지 파일 손상 또는 없음");
			System.exit(0);
		}
		
		class p_birdImg extends JComponent{
			private static final long serialVersionUID = -1631606650281790076L;
			public void paint(Graphics g){
				g.drawImage(bi_bird, 0, 0, null);
			}
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(bi_bird.getWidth(), bi_bird.getHeight());
			}
		}//inner class - ghostImg Panel
		p_birdImg bi = new p_birdImg();
		JPanel p_birdCenter = new JPanel();
		p_birdCenter.setBackground(Color.DARK_GRAY);
		p_birdCenter.add(bi);
		p_bird.add(p_birdCenter, BorderLayout.CENTER);
		p_birdCenter.setAlignmentX(CENTER_ALIGNMENT);
		btn_bird = new JButton("\uCC38\uC0C8\uB9E8 \uC120\uD0DD");
		btn_bird.setFont(new Font("D2Coding", Font.PLAIN, 15));
		btn_bird.setForeground(new Color(0, 255, 0));
		btn_bird.setBackground(Color.DARK_GRAY);
		btn_bird.addActionListener(this);
		btn_bird.addKeyListener(this);
		p_bird.add(btn_bird, BorderLayout.SOUTH);
		p_characters.add(p_bird);
		
		btn_bird_info = new JButton("\uCE90\uB9AD\uD130 \uC815\uBCF4");
		btn_bird_info.setFont(new Font("D2Coding", Font.PLAIN, 15));
		btn_bird_info.setForeground(new Color(0, 255, 0));
		btn_bird_info.setBackground(Color.DARK_GRAY);
		btn_bird_info.addKeyListener(this);
		btn_bird_info.addActionListener(this);
		p_bird.add(btn_bird_info, BorderLayout.NORTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source == btn_ghost){
			dispose();
			new GUI("Ghost", id, ois, oos);
		}else if(source == btn_bird){
			dispose();
			new GUI("Bird", id, ois, oos);
		}
		if(source == btn_rank){
			new RankTable(this, ois, oos);								////////////////////////////////////////랭킹 테이블 클라이언트쪽 
		}
		if(source == btn_patch){
			new PatchDialog(this);
		}
		if(source == btn_ghost_info){
			new GhostInfoDialog(this);
		}
		if(source == btn_bird_info){
			new BirdInfoDialog(this);
		}
		if(source == btn_notice){
			new NoticeDialog(this);
		}
		
	}//actionPerformed()

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(btn_ghost.hasFocus()){
				btn_ghost.doClick();
			}//ghost선택
			if(btn_bird.hasFocus()){
				btn_bird.doClick();
			}//bird선택
		}//enter입력
	}//keyPressed()
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	
	
	public void playBGM(String fileName) {
        try {
            ais = AudioSystem.getAudioInputStream(new File(fileName));
            clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        }
        catch (Exception ex) {
        	ex.printStackTrace();
        }
    }
	
	public void randomBGM(){
		int rand = (int) (Math.random() * 8);
		switch (rand) {
		case 0:
			playBGM("./Dodge/Audio/Intro00_IronMan3_Theme.kshA");
			break;
		case 1:
			playBGM("./Dodge/Audio/Intro01_Cap's Promise.kshA");
			break;
		case 2:
			playBGM("./Dodge/Audio/Intro02_IronMan_FinalBattle.kshA");
			break;
		case 3:
			playBGM("./Dodge/Audio/Intro03_Avengers2_Church_Battle.kshA");
			break;
		case 4:
			playBGM("./Dodge/Audio/Intro04_DJ_Sona_Kinetic_LoginScreenIntro.kshA");
			break;
		case 5:
			playBGM("./Dodge/Audio/Intro05_Fortress_bgm01.kshA");
			break;
		case 6:
			playBGM("./Dodge/Audio/Intro06_Loginloop_AprilFools2015.kshA");
			break;
		case 7:
			playBGM("./Dodge/Audio/Intro07_LOL_2016_Theme.kshA");
			break;
		}

	}

	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(!clip.isRunning()){
				randomBGM();
			}
		}//while(true)
	}//run()
	
	
}//class

