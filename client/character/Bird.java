package client.character;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Bird extends Character implements KeyListener, Runnable{
	private static final long serialVersionUID = 4457498354298467761L;
	private BufferedImage birdImg;
	private boolean up, down, left, right;							//keylistener에서 사용하는 불리언 변수들
	
	public Bird(JFrame gui) {										//이 constructor에서는 이미지를 그리지 않음. run메소드로 그린다.
		super();
		setFocusable(true);
		requestFocus();
		try {
//			birdImg = ImageIO.read(new File("C:/java/Data/Dodge/Character/Bird/Bird_Right.png"));	//eclipse버전
			birdImg = ImageIO.read(new File("./Dodge/Character/Bird/Bird_Right.png"));	//jar버전
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "이미지 파일 손상 또는 없음");
			System.exit(0);
		}
		addKeyListener(this);
		setVisible(true);
	}
	
	public int getGhost_x() {
		return Character.char_x;
	}
	public int getGhost_y() {
		return Character.char_y;
	}
	
	public void paintComponent(Graphics g){							//wolf의 x,y좌표에 그림. repaint시 갱신된 x,y 참조 
		super.paintComponent(g);
		g.drawImage(birdImg, Character.char_x, Character.char_y, null);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {							//방향키 누르는 순간 불리언들이 true됨 (run에서 사용된다)
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP:
			up = true;
			break;
		case KeyEvent.VK_DOWN:
			down = true;
			break;
		case KeyEvent.VK_LEFT:
			left = true;
			break;
		case KeyEvent.VK_RIGHT:
			right = true;
			break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {							//방향키 떼는 순간 불리언들이 false됨 (run에서 사용된다)
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP:
			up = false;
			break;
		case KeyEvent.VK_DOWN:
			down = false;
			break;
		case KeyEvent.VK_LEFT:
			left = false;
			break;
		case KeyEvent.VK_RIGHT:
			right = false;
			break;
		}
		
	}

	@Override
	public void run() {												
		while(true){
				try {		//방향 이동에 따라 캐릭터 이미지 변화
					
//					jar 버전
					if(right) birdImg = ImageIO.read(new File("./Dodge/Character/Bird/Bird_Right.png"));
					if(left) birdImg = ImageIO.read(new File("./Dodge/Character/Bird/Bird_Left.png"));

//					eclipse 버전
//					if(right) birdImg = ImageIO.read(new File("C:/java/Data/Dodge/Character/Bird/Bird_Right.png"));
//					if(left) birdImg = ImageIO.read(new File("C:/java/Data/Dodge/Character/Bird/Bird_Left.png"));
					
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(this, "이미지 파일 손상 또는 없음");
					System.exit(0);
				}
			
			if(up && Character.char_y > 0) Character.char_y -= 10;						//17밀리세컨드(약 60fps)로 돌면서 불리언을 확인하고, true일시 그 방향으로 이동  
			if(down && Character.char_y < 670) Character.char_y += 10;
			if(left && Character.char_x > 0) Character.char_x -= 10;
			if(right && Character.char_x < 900) Character.char_x += 10;
			repaint();
			
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}//while
	}//run()
}
