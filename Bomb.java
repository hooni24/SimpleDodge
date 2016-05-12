import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class Bomb extends JComponent implements Runnable {
	BufferedImage bi;
	File file;
	int x = 0, y = 0;
	
	@Override
	public void run() {
		while(true){
			for(int i = 0; i < 117; i++){
				if(i < 10)	file = new File("C:/java/Data/Animation/bomb/bomb_f0" + i + ".png");
				else file = new File("C:/java/Data/Animation/bomb/bomb_f" + i + ".png");
				try {
					bi = ImageIO.read(file);
					repaint();
					Thread.sleep(17);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}//t-c
			}//for
		}//while
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(bi, x, y, null);
	}
	
	
}
