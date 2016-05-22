package client.character;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import client.gui.GUI;

public class Bird extends Character implements KeyListener, Runnable{
	private static final long serialVersionUID = 4457498354298467761L;
	private BufferedImage birdImg, dummyImg;
	private boolean up, down, left, right;							//keylistener에서 사용하는 불리언 변수들
	private GUI gui;
	
	public Bird(GUI gui) {										//이 constructor에서는 이미지를 그리지 않음. run메소드로 그린다.
		super();
		this.gui =gui;
		playSE("./Dodge/Audio/Bird_Start.kshA");
		setFocusable(true);
		requestFocus();
		try {
			birdImg = ImageIO.read(new File("./Dodge/Character/Bird/Bird_Right.ksh"));	
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
		g.drawImage(dummyImg, Character.dummy_x, Character.dummy_y, null);
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
		case KeyEvent.VK_SPACE:
			if(!isAbilityOn && Character.abilityAble && !Character.isDummy){
				gui.abilityDown();
				if(Character.ability == 0) Character.abilityAble = false;			//특수능력 체크해서 0되면 더이상 사용 못하게
				abilityOn = System.currentTimeMillis();
				isAbilityOn = true;
				playSE("./Dodge/Audio/Bird_Ability.kshA");
			}
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
			if(isDummy){
				dummyBirdMovement();
			}else if(!isDummy && !isAbilityOn){
				realBirdMovement();
			}else if(!isDummy && isAbilityOn){
				birdAbility();
			}
		}//while
	}//run()
	
	public void realBirdMovement(){
		try {		//방향 이동에 따라 캐릭터 이미지 변화
			
			if(right) birdImg = ImageIO.read(new File("./Dodge/Character/Bird/Bird_Right.ksh"));
			if(left) birdImg = ImageIO.read(new File("./Dodge/Character/Bird/Bird_Left.ksh"));
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(this, "이미지 파일 손상 또는 없음");
			System.exit(0);
		}
		if(up && Character.char_y > 0) Character.char_y -= speed;						//17밀리세컨드(약 60fps)로 돌면서 불리언을 확인하고, true일시 그 방향으로 이동  
		if(down && Character.char_y < 720) Character.char_y += speed;
		if(left && Character.char_x > 0) Character.char_x -= speed;
		if(right && Character.char_x < 950) Character.char_x += speed;
		repaint();
		
		try {
			Thread.sleep(17);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}//realBirdMovement()
	
	public void dummyBirdMovement() {
		try {		//방향 이동에 따라 캐릭터 이미지 변화
			dummyImg = ImageIO.read(new File("./Dodge/Character/Bird/Dummy/Bird_Dummy.ksh"));
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(this, "이미지 파일 손상 또는 없음");
			System.exit(0);
		}
		if(up && Character.dummy_y > 0) Character.dummy_y -= speed;						//17밀리세컨드(약 60fps)로 돌면서 불리언을 확인하고, true일시 그 방향으로 이동  
		if(down && Character.dummy_y < 720) Character.dummy_y += speed;
		if(left && Character.dummy_x > 0) Character.dummy_x -= speed;
		if(right && Character.dummy_x < 950) Character.dummy_x += speed;
		repaint();
		
		try {
			Thread.sleep(17);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void birdAbility() {
		try {		
			birdImg = ImageIO.read(new File("./Dodge/Character/Bird/Ability/Bird_Ability.ksh"));
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(this, "이미지 파일 손상 또는 없음");
			System.exit(0);
		}
		if(up && Character.char_y > 0) Character.char_y -= speed;						//17밀리세컨드(약 60fps)로 돌면서 불리언을 확인하고, true일시 그 방향으로 이동  
		if(down && Character.char_y < 720) Character.char_y += speed;
		if(left && Character.char_x > 0) Character.char_x -= speed;
		if(right && Character.char_x < 950) Character.char_x += speed;
		repaint();
		
		try {
			Thread.sleep(17);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(abilityOn + 3000 < System.currentTimeMillis()){
			isAbilityOn = false;
		}
		
	}//birdAbility()
}
