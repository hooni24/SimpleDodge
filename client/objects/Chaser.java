package client.objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import client.character.Character;
import client.gui.GUI;

public class Chaser extends Object implements Runnable{
	private static final long serialVersionUID = -5972591091569069996L;
	private BufferedImage chaserImg;
	private File chaserFile;
	private int chaser_x = Character.char_x + 100, chaser_y = Character.char_y + 100;
	private int speed = 5;
	private GUI gui;
	
	public Chaser(GUI gui) {
		this.gui = gui;
	}

	@Override
	public void run() {
		try {
			while(true){
				for(int i = 1; i < 19; i++){

//					eclipse 버전
//					chaserFile = new File("C:/java/Data/Dodge/Objects/Chaser/Chaser (" + i + ").ksh");
//					jar 버전
					chaserFile = new File("./Dodge/Objects/Chaser/Chaser (" + i + ").ksh");
					
					chaserImg = ImageIO.read(chaserFile);
					repaint();
					chaserMovement();
					Thread.sleep(30);
					
					if((Character.char_x > chaser_x-45 && Character.char_x < chaser_x+45)			//부딪히면?
							&& (Character.char_y > chaser_y-45 && Character.char_y < chaser_y+45)
							&& !Character.isDummy
							&& !Character.isAbilityOn){					
						chaser_x = 450;	chaser_y = 350; 									//체이서 멀리 던짐. (겹치는경우 화면밖 나가는거 방지)
//						eclipse 버전
//						playSE("C:/java/Data/Dodge/Audio/Chaser.kshA");
//						jar 버전
						playSE("./Dodge/Audio/Chaser.kshA");
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
					
					if(GUI.startTime + 50000 <= System.currentTimeMillis() && GUI.startTime + 50100 >= System.currentTimeMillis()){		//50초에 속도증가
						speed = 9;
					}
				
				}//for
			}//while(true)
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "이미지 파일 손상 또는 없음");
			System.exit(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}//run()
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(chaserImg, chaser_x, chaser_y, null);			
	}
	
	public void chaserMovement(){
		if(!Character.isDummy){
			if(chaser_x > Character.char_x){
				chaser_x -= speed;
			}else {
				chaser_x += speed;
			}
			if(chaser_y > Character.char_y){
				chaser_y -= speed;
			}else {
				chaser_y += speed;
			}
		}//if(!isDummy)
	}//chaserMovement()

}
