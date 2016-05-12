package game.character;

import javax.swing.JComponent;

public class Character extends JComponent{
	public static int char_x = 450, char_y = 350;						//화면 중앙 좌표. (전체 1000*800 에 wolf이미지 100*100 고려)
																		//캐릭터 좌표는 여기서 스태틱으로 관리함. 

	public Character() {
		super();
	}
}
