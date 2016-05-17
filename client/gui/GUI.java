package client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import client.character.Bird;
import client.character.Ghost;
import client.objects.FireBall;
import common.TransData;

public class GUI extends JFrame implements Runnable{
	private static final long serialVersionUID = 4655929275227340979L;
	private FireBall[] fireBallArray = new FireBall[30];
	private int life, timer_sec, timer_dot;
	private JLabel lbl_life, lbl_timer;
	private ArrayList<Thread> thList = new ArrayList<>();
	private Thread guiThread;
	private String result;
	private JLayeredPane lp;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private String id;
	public static long startTime;
	
	public void setLife(int life) {
		this.life = life;
	}
	public int getLife() {
		return life;
	}
	public String getResult() {
		return result;
	}

	public GUI(String type, String id, ObjectInputStream ois, ObjectOutputStream oos) {
		startTime = System.currentTimeMillis();
		this.ois = ois;
		this.oos = oos;
		this.id = id;
		
		getContentPane().setBackground(Color.BLACK);
		lp = new JLayeredPane();			//레이어드페인 생성
		getContentPane().add(lp, BorderLayout.CENTER);
		
		switch(type){
			case "Ghost":
				ghostGame();	break;
			case "Bird":
				birdGame();		break;
		}

		lbl_life = new JLabel("남은체력 : " + life);				//라이프 설치
		lbl_life.setFont(new Font("gulim", Font.BOLD, 17));
		lbl_life.setForeground(Color.WHITE);
		lbl_life.setBounds(830, 5, 200, 40);
		lp.add(lbl_life, new Integer(3));
		
		lbl_timer = new JLabel("버틴시간: " + timer_sec + "." + timer_dot + " 초");				//시간 설치
		lbl_timer.setFont(new Font("gulim", Font.BOLD, 17));
		lbl_timer.setForeground(Color.WHITE);
		lbl_timer.setBounds(830, 25, 200, 40);
		lp.add(lbl_timer, new Integer(4));
		

		setTitle("Simple Dodge");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(1000, 800);
		setBackground(Color.BLACK);
		setLocationRelativeTo(null);
		
		new RankTable(this, ois, oos);
		setVisible(true);							//setVisible 전에 RankTable을 만들어야 활성화가 이쪽으로 옴
		new Thread(this).start();					//플레이시간 체크를 위한 스레드 시작
	}
	
	public void lifeDown(){
		lbl_life.setText("남은체력 : " + --life);
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		while(true){
			lbl_timer.setText("버틴시간: " + timer_sec + "." + timer_dot + " 초");
			try {
				guiThread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(timer_dot++ == 9) {						//timer_dot 0~9반복, 9되면 timer_sec 하나 증가. 0.1초 단위로 체크
				timer_dot = 0;
				timer_sec++;
			}
			result = lbl_timer.getText();
			
			if(getLife() < 1){							//라이프 끝나면 프로그램 종료, 버틴 시간 표시 (data에 포장해서 서버로 점수 전송)
				String[] a = getResult().split(" ");
				TransData data = new TransData();
				data.setCommand(TransData.HI_SCORE);
				data.setHiScore(Double.parseDouble(a[1]));
				data.setId(id);
				try {
					oos.writeObject(data);
				} catch (IOException e) {
					e.printStackTrace();
				}
				dispose();
				setLife(20);
				JOptionPane.showMessageDialog(this, getResult(), "결과", JOptionPane.PLAIN_MESSAGE);
				System.exit(0);
			}
		}//while
	}//run()
	
	
	public void ghostGame(){
		life = 50;
		Ghost ghost = new Ghost(this);					//Ghost 객체 생성하고 lp위에 설치
		ghost.setBounds(0, 0, 1000, 800);
		lp.add(ghost, new Integer(1));
		Thread ghostThread = new Thread(ghost);
		ghostThread.start();
		thList.add(ghostThread);
		
		for (FireBall fireBall : fireBallArray) {			//fireBall 객체 배열 갯수만큼 생성하고 lp위에 설치 (전역변수에서 갯수설정가능)
			fireBall = new FireBall(this);
			fireBall.setBounds(0,0,1000,800);
			lp.add(fireBall, 2);
			Thread fireBallThread = new Thread(fireBall);
			fireBallThread.start();
			thList.add(fireBallThread);
		}
		
//		Wind wind = new Wind();							//Wind객체 생성하고 lp위에 설치
//		wind.setBounds(0, 0, 1000, 800);
//		lp.add(wind, new Integer(3));
//		Thread windThread = new Thread(wind);
//		windThread.start();
//		thList.add(windThread);
	}//ghostGame()
	
	private void birdGame() {
		life = 20;
		Bird bird = new Bird(this);						//Bird 객체 생성하고 lp 위에 설치
		bird.setBounds(0,0,1000,800);
		lp.add(bird, new Integer(1));
		Thread birdThread = new Thread(bird);
		birdThread.start();
		thList.add(birdThread);
		
		for (FireBall fireBall : fireBallArray) {			//fireBall 객체 배열 갯수만큼 생성하고 lp위에 설치 (전역변수에서 갯수설정가능)
			fireBall = new FireBall(this);
			fireBall.setBounds(0,0,1000,800);
			lp.add(fireBall, 2);
			Thread fireBallThread = new Thread(fireBall);
			fireBallThread.start();
			thList.add(fireBallThread);
		}
		
//		Wind wind = new Wind();							//Wind객체 생성하고 lp위에 설치
//		wind.setBounds(0, 0, 1000, 800);
//		lp.add(wind, new Integer(3));
//		Thread windThread = new Thread(wind);
//		windThread.start();
//		thList.add(windThread);
		
	}//birdGame()
}//class
