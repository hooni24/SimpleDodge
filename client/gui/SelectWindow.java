package client.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class SelectWindow extends JFrame implements ActionListener, KeyListener{
	private static final long serialVersionUID = -3459467354956808841L;
	private JButton btn_ghost, btn_bird;
	public BufferedImage bi_ghost, bi_bird;
	private File file_ghost, file_bird;
	private JPanel p_characters, p_ghost, p_bird;
	private Socket client;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private String id;
	
	public SelectWindow() {
		try {
			client = new Socket("localhost", 7979);
			oos = new ObjectOutputStream(client.getOutputStream());
			ois = new ObjectInputStream(client.getInputStream());
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		drawUI();
	}
	
	public void drawUI(){
		setTitle("Select Character");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 230);
		p_characters = new JPanel();
		p_characters.setLayout(new FlowLayout());
		
		ghostMenu();
		birdMenu();
		
		setLocationRelativeTo(null);
		
		getContentPane().add(p_characters);
		setVisible(true);
		new LoginGUI(this, ois, oos);
	}
	
	
	public void setId(String id) {
		this.id = id;
	}

	public void ghostMenu(){
		p_ghost = new JPanel();
		p_ghost.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		p_ghost.setLayout(new BorderLayout());
//		eclipse 버전
		file_ghost = new File("C:/java/Data/Dodge/Character/Ghost/Ghost_Menu.png");
		
//		jar 버전
//		file_ghost = new File("./Dodge/Character/Ghost/Ghost_Menu.png");
		
		try {
			bi_ghost = ImageIO.read(file_ghost);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "이미지 파일 손상 또는 없음");
			System.exit(0);
		}
		
		class p_ghostImg extends JComponent{
			private static final long serialVersionUID = 5268273211264080430L;
			public void paint(Graphics g){
				g.drawImage(bi_ghost, 0, 0, null);
			}
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(bi_ghost.getWidth(), bi_ghost.getHeight());
			}
		}//inner class - ghostImg Panel
		p_ghostImg gi = new p_ghostImg();
		JPanel p_ghostCenter = new JPanel();
		p_ghostCenter.add(gi);
		p_ghost.add(p_ghostCenter, BorderLayout.CENTER);
		p_ghostCenter.setAlignmentX(CENTER_ALIGNMENT);
		btn_ghost = new JButton("유령맨");
		btn_ghost.addActionListener(this);
		btn_ghost.addKeyListener(this);
		p_ghost.add(btn_ghost, BorderLayout.SOUTH);
		p_characters.add(p_ghost);
	}
	
	private void birdMenu() {
		p_bird = new JPanel();
		p_bird.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		p_bird.setLayout(new BorderLayout());
//		eclipse 버전
		file_bird = new File("C:/java/Data/Dodge/Character/Bird/Bird_Menu.png");
		
//		jar 버전
//		file_bird = new File("./Dodge/Character/Bird/Bird_Menu.png");
		
		try {
			bi_bird = ImageIO.read(file_bird);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "이미지 파일 손상 또는 없음");
			System.exit(0);
		}
		
		class p_birdImg extends JComponent{
			private static final long serialVersionUID = -1631606650281790076L;
			public void paint(Graphics g){
				g.drawImage(bi_bird, 0, 0, null);
			}
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(bi_bird.getWidth(), bi_bird.getHeight());
			}
		}//inner class - ghostImg Panel
		p_birdImg bi = new p_birdImg();
		JPanel p_birdCenter = new JPanel();
		p_birdCenter.add(bi);
		p_bird.add(p_birdCenter, BorderLayout.CENTER);
		p_birdCenter.setAlignmentX(CENTER_ALIGNMENT);
		btn_bird = new JButton("참새맨");
		btn_bird.addActionListener(this);
		btn_bird.addKeyListener(this);
		p_bird.add(btn_bird, BorderLayout.SOUTH);
		p_characters.add(p_bird);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source == btn_ghost){
			dispose();
			new GUI("Ghost", id, ois, oos);
		}else if(source == btn_bird){
			dispose();
			new GUI("Bird", id, ois, oos);
		}
	}//actionPerformed()



	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(btn_ghost.hasFocus()){
				btn_ghost.doClick();
			}//ghost선택
			if(btn_bird.hasFocus()){
				btn_bird.doClick();
			}//bird선택
		}//enter입력
	}//keyPressed()
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
}//class
