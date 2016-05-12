package game.objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import game.character.Character;
import game.character.Ghost;
import game.gui.GUI;

public class Flame extends JComponent implements Runnable{
	private BufferedImage flameImg;
	private File flameFile;
	private int randomX, flame_y = 800, randomSpeed;			//생성되는 y좌표는 고정. x좌표는 랜덤으로 그때그때 생성, 스피드도 랜덤
	private boolean isOn;								//Flame객체가 발사되었는가 여부
	private GUI gui;
	
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
		try {
			while(isOn){
				for(int i = 1; i < 8; i ++){						//isOn이 true인 동안. (즉 날아가는 동안) 지속적으로 repaint 함으로 애니메이션 효과
					flameFile = new File("C:/java/Data/Dodge/Flame/Flame (" + i + ").png");
					flameImg = ImageIO.read(flameFile);
					repaint();
					Thread.sleep(45);								//애니메이션 재생 속도 조절.
					flame_y -= randomSpeed;							//매 슬립마다 위에서 지정한 randomSpeed만큼 객체이동
					
					
					//캐릭터 객체와 부딪히면, 라이프 감소 시킴(여기 있는 이유는 여러 객체 생성되니까), 그리고 게임 리셋 (현재는 프로그램 종료됨)
					if((Character.char_x > randomX-45 && Character.char_x < randomX+45)
							&& (Character.char_y > flame_y-45 && Character.char_y < flame_y+45)){					
						gui.lifeDown();
						gui.resetGame();
					}
					
					if(gui.getLife() < 1){							//라이프 끝나면 프로그램 종료, 버틴 시간 표시
						gui.dispose();
						gui.setLife(20);
						JOptionPane.showMessageDialog(gui, gui.getResult(), "결과", JOptionPane.PLAIN_MESSAGE);
						System.exit(0);
					}
					
					if(flame_y < -110){									//y좌표가 -110보다 작아지면.(즉 화면 밖으로 벗어나면) 스위치끄고 y좌표 바닥으로 초기화
						isOn = false;									//														-> 다음 발사 대기
						flame_y = 800;
						return;
					}
				}//for
			}//while
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}//t-c
	}//shoot()
	
}//class
