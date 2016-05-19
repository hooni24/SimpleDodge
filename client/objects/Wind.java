package client.objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import client.character.Character;
import client.gui.GUI;

public class Wind extends Object implements Runnable{
	private static final long serialVersionUID = -7049669062398453892L;
	private BufferedImage windImg;
	private File windFile;
	private int wind_x = (int) (Math.random() * 900), wind_y = (int) (Math.random() * 700);
	private boolean isXOver, isYOver;
	private int speedX = (int) (Math.random() * 3) + 12;
	private int speedY = (int) (Math.random() * 3) + 12;
	private GUI gui;
	
	public Wind(GUI gui) {
		this.gui = gui;
	}

	@Override
	public void run() {
		try {
			while(true){
				for(int i = 1; i < 31; i++){
//					eclipse 버전
					windFile = new File("C:/java/Data/Dodge/Objects/Wind/Wind (" + i + ").ksh");
					
//					jar 버전
//					windFile = new File("./Dodge/Objects/Wind/Wind (" + i + ").ksh");
					
						windImg = ImageIO.read(windFile);
						repaint();
						windMovement();
						Thread.sleep(30);
						
						if((Character.char_x > wind_x-45 && Character.char_x < wind_x+45)
								&& (Character.char_y > wind_y-45 && Character.char_y < wind_y+45)
								&& !Character.isDummy
								&& !Character.isAbilityOn){					
							gui.lifeDown();
//							eclipse 버전
							playSE("C:/java/Data/Dodge/Audio/Wind.kshA");
//							jar 버전
//							playSE("./Dodge/Audio/Wind.kshA");
							Character.dummyBirth = System.currentTimeMillis();					//더미생성시간 초기화
							Character.dummySwitch();
							Character.isDummy = true;
						}
						
						if(Character.dummyBirth + 1500 < System.currentTimeMillis() && Character.isDummy){
							Character.dummySwitch();
							Character.isDummy = false;
							Character.dummyBirth = Long.MAX_VALUE - 5000;						//원래대로 돌아가면서 최대값근사치로 초기화
						}
						
						if(GUI.startTime + 35000 <= System.currentTimeMillis() && GUI.startTime + 35030 >= System.currentTimeMillis()){		//35초에 속도증가
							 speedX = (int) (Math.random() * 5) + 20;
							 speedY = (int) (Math.random() * 5) + 20;
						}
						
				}//for
			}//while
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "이미지 파일 손상 또는 없음");
			System.exit(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}//run()
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(windImg, wind_x, wind_y, null);			//이따 x,y좌표는 설정. (이동 하니까 고려 )
	}
	
	public void windMovement(){
		if(!isXOver){
				wind_x += speedX;
				if(wind_x > 950){
					isXOver = !isXOver;
					speedX = (int) (Math.random() * 3) + 12;			//벽에 튕길때마다 속도 조절 ( 반사각 변경을 위함 )
				}
		}else {
			wind_x -= speedX;
			if(wind_x < 0){
				isXOver = !isXOver;
				speedX = (int) (Math.random() * 3) + 12;
			}
		}
		
		if(!isYOver){
			wind_y += speedY;
			if(wind_y > 750){
				isYOver = !isYOver;
				speedY = (int) (Math.random() * 3) + 12;
			}
	}else {
		wind_y -= speedY;
		if(wind_y < 0){
			isYOver = !isYOver;
			speedY = (int) (Math.random() * 3) + 12;
		}
	}
	}//windMovement()
}
