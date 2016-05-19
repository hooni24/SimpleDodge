package client.objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import client.character.Character;
import client.gui.GUI;

public class Flame extends Object implements Runnable{
	private static final long serialVersionUID = -1936987593223746976L;
	private BufferedImage flameImg;
	private File flameFile;
	private int randomX, flame_y = 800, randomSpeed;			//생성되는 y좌표는 고정. x좌표는 랜덤으로 그때그때 생성, 스피드도 랜덤
	private boolean isOn;								//Flame객체가 발사되었는가 여부
	private GUI gui;
	private boolean thSw = true;
	
	public void changeThSw(){
		thSw = !thSw;
	}
	
	public Flame(GUI gui) {
		this.gui = gui;
	}
	
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep((int)(Math.random()*5000));			//발사하기 전에 대기한다.(약0초~5초) (초탄 발사 시간에 차이를 두기 위함)
				shoot();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}//run()
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(flameImg, randomX, flame_y, null);
	}
	
	public void shoot(){
		
		isOn = true;												//발사되면 isOn을 true로 함. (날아가는 도중에는 계속 true이므로 무한루프 돌게 됨)
		randomX = (int)(Math.random() * 950);						//x좌표 랜덤 부여
		randomSpeed = (int)(Math.random() * 5)+7;					//속도(슬립당 이동 픽셀) 랜덤 부여 =  약 7~12픽셀
		if(System.currentTimeMillis() > GUI.startTime + 10000) 			// 시작후 10초 지남. 속도증가
			randomSpeed = (int)(Math.random() * 5)+15;
		if(System.currentTimeMillis() > GUI.startTime + 15000) 			// 시작후 15초 지남. 속도증가
			randomSpeed = (int)(Math.random() * 5)+22; 
		try {
			while(isOn){
				for(int i = 1; i < 8; i ++){						//isOn이 true인 동안. (즉 날아가는 동안) 지속적으로 repaint 함으로 애니메이션 효과

//					eclipse 버전
					flameFile = new File("C:/java/Data/Dodge/Objects/Flame/Flame (" + i + ").ksh");
					
//					jar 버전
//					flameFile = new File("./Dodge/Objects/Flame/Flame (" + i + ").ksh");
					
					flameImg = ImageIO.read(flameFile);
					repaint();
					Thread.sleep(45);								//애니메이션 재생 속도 조절.
					flame_y -= randomSpeed;							//매 슬립마다 위에서 지정한 randomSpeed만큼 객체이동
					
					
					//캐릭터 객체와 부딪히면, 라이프 감소 시킴(여기 있는 이유는 여러 객체 생성되니까), 그리고 더미 생성
					if((Character.char_x > randomX-45 && Character.char_x < randomX+45)
							&& (Character.char_y > flame_y-45 && Character.char_y < flame_y+45)
							&& !Character.isDummy
							&& !Character.isAbilityOn){
//						eclipse 버전
						playSE("C:/java/Data/Dodge/Audio/Flame.kshA");
//						jar 버전
//						playSE("./Dodge/Audio/Flame.kshA");
						
						gui.lifeDown();
						Character.dummyBirth = System.currentTimeMillis();					//더미생성시간 초기화
						Character.dummySwitch();
						Character.isDummy = true;
					}
					
					if(Character.dummyBirth + 1500 < System.currentTimeMillis() && Character.isDummy){
						Character.dummySwitch();
						Character.isDummy = false;
						Character.dummyBirth = Long.MAX_VALUE - 5000;						//원래대로 돌아가면서 최대값근사치로 초기화
					}
					
					
					if(flame_y < -110){									//y좌표가 -110보다 작아지면.(즉 화면 밖으로 벗어나면) 스위치끄고 y좌표 바닥으로 초기화
						isOn = false;									//														-> 다음 발사 대기
						flame_y = 800;
					}
				}//for
			}//while
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "이미지 파일 손상 또는 없음");
			System.exit(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}//t-c
	}//shoot()
	
}//class
