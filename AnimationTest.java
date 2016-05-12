import javax.swing.JFrame;

public class AnimationTest extends JFrame{
	
	public static void main(String[] args) {
		new AnimationTest();
	}
	
	public AnimationTest() {
		setTitle("Test");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,800);
		Bomb b = new Bomb();
		add(b);
		new Thread(b).start();
		setVisible(true);
	}
}
