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
import client.character.Character;
import client.character.Ghost;
import client.objects.Chaser;
import client.objects.Flame;
import client.objects.Wind;
import common.TransData;

public class GUI extends JFrame implements Runnable{
	private static final long serialVersionUID = 4655929275227340979L;
	private Flame[] flameArray = new Flame[30];
	private int timer_sec, timer_dot;
	private JLabel lbl_life, lbl_timer, lbl_ability;
	private ArrayList<Thread> thList = new ArrayList<>();
	private Thread guiThread;
	private String result;
	private JLayeredPane lp;
	@SuppressWarnings("unused")
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private String id;
	private String characterType;
	public static long startTime;
	private Bird bird;
	private Ghost ghost;
	
	public String getResult() {
		return result;
	}
	public void abilityDown(){
		lbl_ability.setText("남은 특수능력 : " + --Character.ability);
	}
	public void lifeDown(){
		lbl_life.setText("남은라이프 : " + --Character.life);
	}

	public GUI(String type, String id, ObjectInputStream ois, ObjectOutputStream oos) {
		startTime = System.currentTimeMillis();
		this.ois = ois;
		this.oos = oos;
		this.id = id;
		
		getContentPane().setBackground(Color.BLACK);
		lp = new JLayeredPane();			//레이어드페인 생성
		getContentPane().add(lp, BorderLayout.CENTER);
		
		switch(type){															//어빌리티 세팅 되고 나서 xxGame()이 실행되어야 해당 캐릭터 어빌리티 세팅.
		case "Ghost":															//xxGame()이 실행된 다음이어야 라이프를 가져올 수 있으니 
			characterType = "Ghost";											//라이프세팅은 xxGame() 다음에 와야 함. 그래서 이 switch문이 사이에 들어온 것임.
			ghostGame();	break;
		case "Bird":
			characterType = "Bird";
			birdGame();		break;
		}
		
		lbl_ability = new JLabel("남은 특수능력 : " + Character.ability);
		lbl_ability.setFont(new Font("gulim", Font.BOLD, 17));
		lbl_ability.setForeground(Color.WHITE);
		lbl_ability.setBounds(830, 25, 200, 40);
		lp.add(lbl_ability, new Integer(2));
		
		lbl_life = new JLabel("남은라이프 : " + Character.life);				//라이프 설치
		lbl_life.setFont(new Font("gulim", Font.BOLD, 17));
		lbl_life.setForeground(Color.WHITE);
		lbl_life.setBounds(830, 5, 200, 40);
		lp.add(lbl_life, new Integer(3));
		
		lbl_timer = new JLabel("버틴시간 : " + timer_sec + "." + timer_dot + " 초");				//시간 설치
		lbl_timer.setFont(new Font("gulim", Font.BOLD, 17));
		lbl_timer.setForeground(Color.WHITE);
		lbl_timer.setBounds(830, 45, 200, 40);
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
		
		Background bg = new Background();
		bg.setBounds(0,0,1000,800);
		lp.add(bg, new Integer(1));
		
		for (Flame flame : flameArray) {			//Flame 객체 배열 갯수만큼 생성하고 lp위에 설치 (전역변수에서 갯수설정가능)
			flame = new Flame(this);
			flame.setBounds(0,0,1000,800);
			lp.add(flame, new Integer(5));
			Thread fireBallThread = new Thread(flame);
			fireBallThread.start();
			thList.add(fireBallThread);
		}
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		while(true){
			lbl_timer.setText("버틴시간 : " + timer_sec + "." + timer_dot + " 초");
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
			
			if(Character.life < 1){							//라이프 끝나면 프로그램 종료, 버틴 시간 표시 (data에 포장해서 서버로 점수 전송)
				String[] a = getResult().split(" ");
				TransData data = new TransData();
				data.setCommand(TransData.GAME_OVER);
				data.setHiScore(Double.parseDouble(a[2]));
				data.setId(id);
				data.setCharacterType(characterType);
				try {
					oos.writeObject(data);
				} catch (IOException e) {
					e.printStackTrace();
				}
				dispose();
				Character.life = 20;
				JOptionPane.showMessageDialog(this, getResult(), "결과", JOptionPane.PLAIN_MESSAGE);
				System.exit(0);
			}
			
			if(startTime + 40000 <= System.currentTimeMillis() && startTime + 40100 >= System.currentTimeMillis()){
				Chaser chaser = new Chaser(this);							//Chaser객체 생성하고 lp위에 설치
				chaser.setBounds(0, 0, 1000, 800);
				lp.add(chaser, new Integer(7));
				Thread chaserThread = new Thread(chaser);
				chaserThread.start();
				thList.add(chaserThread);
			}
			
			if(startTime + 20000 <= System.currentTimeMillis() && startTime + 20100 >= System.currentTimeMillis()
					|| startTime + 25000 <= System.currentTimeMillis() && startTime + 25100 >= System.currentTimeMillis()
					|| startTime + 30000 <= System.currentTimeMillis() && startTime + 30100 >= System.currentTimeMillis()){
				Wind wind = new Wind(this);							//Wind객체 생성하고 lp위에 설치
				wind.setBounds(0, 0, 1000, 800);
				lp.add(wind, new Integer(6));
				Thread windThread = new Thread(wind);
				windThread.start();
				thList.add(windThread);
			}
		}//while
	}//run()
	
	public void ghostGame(){
		Character.ability = 2;
		Character.life = 5;
		ghost = new Ghost(this);
		ghost.setBounds(0, 0, 1000, 800);
		lp.add(ghost, new Integer(100));
		Thread ghostThread = new Thread(ghost);
		ghostThread.start();
		thList.add(ghostThread);
	}//ghostGame()
	
	private void birdGame() {
		Character.ability = 2;
		Character.life = 5;
		bird = new Bird(this);
		bird.setBounds(0,0,1000,800);
		lp.add(bird, new Integer(100));
		Thread birdThread = new Thread(bird);
		birdThread.start();
		thList.add(birdThread);
	}//birdGame()
}//class
