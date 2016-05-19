package client.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import client.gui.SelectWindow;

public class PatchDialog extends JDialog implements ActionListener{
	private static final long serialVersionUID = -8753985202791149691L;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JTextArea ta_patch;
	private JButton btn_ok;
	private JPanel panel_3;
	private JPanel panel_4;
	
	public PatchDialog(SelectWindow selectWindow) {
		super(selectWindow, "패치노트", true);		//true이면 다이얼로그 처리 꼭 해야함. false이면 안해도 다른 창 사용 가능 ( modal )
		setSize(500,655);
		getContentPane().setBackground(Color.DARK_GRAY);
		BorderLayout borderLayout = (BorderLayout) getContentPane().getLayout();
		borderLayout.setVgap(30);
		borderLayout.setHgap(30);
		
		
		panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		getContentPane().add(panel, BorderLayout.CENTER);
		
		ta_patch = new JTextArea();
		ta_patch.setFont(new Font("D2Coding", Font.PLAIN, 15));
		ta_patch.setForeground(Color.GREEN);
		ta_patch.setBackground(Color.DARK_GRAY);
		ta_patch.setEditable(false);
		ta_patch.setColumns(20);
		panel.add(ta_patch);
		
		ta_patch.setText("             현재 버전 : ===Ver.04===\n\n"
				+ "===Ver.01=== (2016.05.12)\n- 불덩이만 등장.\n- 유령맨,참새맨 선택가능.\n\n"
				+ "===Ver.02=== (2016.05.17)\n- 바람 장애물 등장.\n- 시간에 따라 난이도 조정.\n- 캐릭터정보, 온라인랭킹(정렬안돼서 무의미) 추가.\n\n"
				+ "===Ver.03=== (2016.05.19)\n- 체이서 장애물 등장.\n- 특수능력(Space Bar) 구현.\n- 피격시 일정시간무적.\n- 난이도 재조정.\n- 사운드 추가.\n- 현재 참새맨과 유령맨의 차이는 모양뿐.\n- UI변경\n\n"
				+ "===Ver.04=== (2016.05.XX)\n- NOTHING NOW");
		
		panel_1 = new JPanel();
		panel_1.setBackground(Color.DARK_GRAY);
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panel_1, BorderLayout.WEST);
		
		panel_2 = new JPanel();
		panel_2.setBackground(Color.DARK_GRAY);
		getContentPane().add(panel_2, BorderLayout.EAST);
		
		panel_3 = new JPanel();
		panel_3.setBackground(Color.DARK_GRAY);
		getContentPane().add(panel_3, BorderLayout.SOUTH);
		
		btn_ok = new JButton("\uD655\uC778");
		btn_ok.setFont(new Font("D2Coding", Font.PLAIN, 15));
		btn_ok.setForeground(Color.GREEN);
		btn_ok.setBackground(Color.DARK_GRAY);
		btn_ok.addActionListener(this);
		panel_3.add(btn_ok);
		
		panel_4 = new JPanel();
		panel_4.setBackground(Color.DARK_GRAY);
		getContentPane().add(panel_4, BorderLayout.NORTH);
		
		setLocationRelativeTo(selectWindow);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btn_ok){
			dispose();
		}
	}

}
