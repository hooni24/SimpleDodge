package client.character;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JComponent;

public class Character extends JComponent{
	private static final long serialVersionUID = -1284305847707921701L;
	public static boolean isDummy, isAbilityOn, abilityAble = true;
	public static long dummyBirth = Long.MAX_VALUE - 5000;
	protected long abilityOn = Long.MAX_VALUE - 5000;
	public static int char_x = 450, char_y = 350;						//화면 중앙 좌표. (전체 1000*800 에 wolf이미지 100*100 고려)
																		//캐릭터 좌표는 여기서 스태틱으로 관리함. 
	public static int dummy_x = -200, dummy_y = -200;
	public static int ability, life;
	protected int speed = 10;

	public Character() {
		super();
	}
	
	public static void dummySwitch(){
		if(isDummy){
			char_x = dummy_x;
			char_y = dummy_y;
			dummy_x = -200;
			dummy_y = -200;
		}else {
				dummy_x = char_x;
				dummy_y = char_y;
				char_x = -200;
				char_y = -200;
		}
	}//dummySwitch()
	
	public void playSE(String fileName) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(fileName));
            Clip clip = AudioSystem.getClip();
            clip.stop();
            clip.open(ais);
            clip.start();
        }
        catch (Exception ex) {
        	ex.printStackTrace();
        }
    }
	
}//class
