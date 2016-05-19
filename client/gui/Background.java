package client.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

public class Background extends JComponent{
	private static final long serialVersionUID = 2527676354132666402L;
	BufferedImage bgImg;
	File bgFile;

	public Background() {
		try {
//			eclipse 버전
//			bgFile = new File("C:/java/Data/Dodge/Objects/Background.ksh");
//			jar 버전
			bgFile = new File("./Dodge/Objects/Background.ksh");
			bgImg = ImageIO.read(bgFile);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "이미지 파일 손상 또는 없음");
			System.exit(0);
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(bgImg, 0, 0, null);
	}//paintComponent()

}//class
