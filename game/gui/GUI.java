package game.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import game.character.Bird;
import game.character.Ghost;
import game.objects.Flame;
import game.objects.Wind;

public class GUI extends JFrame implements Runnable{
	private Flame[] fireBallArray = new Flame[30];
	private int life = 20, timer_sec, timer_dot;
	private JLabel lbl_life, lbl_timer;
	private ArrayList<Thread> thList = new ArrayList<>();
	private Thread guiThread;
	private String result;
	private JLayeredPane lp;
	
	public void setLife(int life) {
		this.life = life;
	}
	public int getLife() {
		return life;
	}
	public String getResult() {
		return result;
	}

	public GUI(String type) {
		
		getContentPane().setBackground(Color.BLACK);
		lp = new JLayeredPane();			//레이어드페인 생성
		getContentPane().add(lp, BorderLayout.CENTER);
		
		switch(type){
			case "Ghost":
				ghostGame();	break;
			case "Bird":
				birdGame();		break;
		}

		lbl_life = new JLabel("남은 라이프 : " + life);				//라이프 설치
		lbl_life.setFont(new Font("gulim", Font.BOLD, 17));
		lbl_life.setForeground(Color.WHITE);
		lbl_life.setBounds(830, 5, 200, 40);
		lp.add(lbl_life, new Integer(3));
		
		lbl_timer = new JLabel("버틴 시간  : " + timer_sec + "." + timer_dot + "초");				//시간 설치
		lbl_timer.setFont(new Font("gulim", Font.BOLD, 17));
		lbl_timer.setForeground(Color.WHITE);
		lbl_timer.setBounds(830, 25, 200, 40);
		lp.add(lbl_timer, new Integer(4));
		
		setTitle("Simple Dodge");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(1000, 800);
		
		setBackground(Color.BLACK);
		setVisible(true);
		
		guiThread = new Thread(this);
		guiThread.start();										//플레이시간 체크를 위한 스레드 시작
	}
	
	public void lifeDown(){
		lbl_life.setText("남은 라이프 : " + --life);
	}
	
	public void resetGame() throws InterruptedException{
		for (Thread thread : thList) {
	//		System.out.println("충돌 했고 맵 리셋 해야 함");
		}
	}
	
	@Override
	public void run() {
		while(true){
			lbl_timer.setText("버틴 시간  : " + timer_sec + "." + timer_dot + "초");
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
		}//while
	}//run()
	
	
	public void ghostGame(){
		Ghost ghost = new Ghost(this);					//Ghost 객체 생성하고 lp위에 설치
		ghost.setBounds(0, 0, 1000, 800);
		lp.add(ghost, new Integer(1));
		Thread ghostThread = new Thread(ghost);
		ghostThread.start();
		thList.add(ghostThread);
		
		for (Flame fireBall : fireBallArray) {			//fireBall 객체 배열 갯수만큼 생성하고 lp위에 설치 (전역변수에서 갯수설정가능)
			fireBall = new Flame(this);
			fireBall.setBounds(0,0,1000,800);
			lp.add(fireBall, 2);
			Thread fireBallThread = new Thread(fireBall);
			fireBallThread.start();
			thList.add(fireBallThread);
		}
		
		Wind wind = new Wind();							//Wind객체 생성하고 lp위에 설치
		wind.setBounds(0, 0, 1000, 800);
		lp.add(wind, new Integer(3));
		Thread windThread = new Thread(wind);
		windThread.start();
		thList.add(windThread);
	}//ghostGame()
	
	private void birdGame() {
		Bird bird = new Bird(this);						//Bird 객체 생성하고 lp 위에 설치
		bird.setBounds(0,0,1000,800);
		lp.add(bird, new Integer(1));
		Thread birdThread = new Thread(bird);
		birdThread.start();
		thList.add(birdThread);
		
		for (Flame fireBall : fireBallArray) {			//fireBall 객체 배열 갯수만큼 생성하고 lp위에 설치 (전역변수에서 갯수설정가능)
			fireBall = new Flame(this);
			fireBall.setBounds(0,0,1000,800);
			lp.add(fireBall, 2);
			Thread fireBallThread = new Thread(fireBall);
			fireBallThread.start();
			thList.add(fireBallThread);
		}
		
		Wind wind = new Wind();							//Wind객체 생성하고 lp위에 설치
		wind.setBounds(0, 0, 1000, 800);
		lp.add(wind, new Integer(3));
		Thread windThread = new Thread(wind);
		windThread.start();
		thList.add(windThread);
		
	}//birdGame()
}//class
