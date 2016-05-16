package client.objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

public class Wind extends JComponent implements Runnable{
	BufferedImage windImg;
	File windFile;
	int wind_x, wind_y;

	@Override
	public void run() {
		try {
			while(true){
				for(int i = 1; i < 31; i++){

//					eclipse 버전
					windFile = new File("C:/java/Data/Dodge/Wind/Wind (" + i + ").png");
					
//					jar 버전
//					windFile = new File("./Dodge/Wind/Wind (" + i + ").png");
					
						windImg = ImageIO.read(windFile);
						repaint();
						Thread.sleep(30);
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
		g.drawImage(windImg, 300, 300, null);			//이따 x,y좌표는 설정. (이동 하니까 고려 )
	}

}
