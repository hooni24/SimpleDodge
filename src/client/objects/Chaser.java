package client.objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import client.character.Character;
import client.gui.GUI;

public class Chaser extends JComponent implements Runnable{
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
				for(int i = 1; i < 27; i++){

//					eclipse 버전
					chaserFile = new File("C:/java/Data/Dodge/Objects/Chaser/Chaser (" + i + ").png");
//					jar 버전
//					chaserFile = new File("./Dodge/Objects/Chaser/Chaser (" + i + ").png");
					
					chaserImg = ImageIO.read(chaserFile);
					repaint();
					chaserMovement();
					Thread.sleep(30);
					
					if((Character.char_x > chaser_x-45 && Character.char_x < chaser_x+45)
							&& (Character.char_y > chaser_y-45 && Character.char_y < chaser_y+45)){					
						gui.lifeDown();
					}
					
					if(GUI.startTime + 70000 <= System.currentTimeMillis() && GUI.startTime + 70100 >= System.currentTimeMillis()){
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
		g.drawImage(chaserImg, chaser_x, chaser_y, null);			//이따 x,y좌표는 설정. (이동 하니까 고려 )
	}
	
	public void chaserMovement(){
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
	}//chaserMovement()

}
