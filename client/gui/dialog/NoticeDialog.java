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

public class NoticeDialog extends JDialog implements ActionListener{
	private static final long serialVersionUID = -4805013257326984759L;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JTextArea ta_patch;
	private JButton btn_ok;
	private JPanel panel_3;
	private JPanel panel_4;
	
	public NoticeDialog(SelectWindow selectWindow) {
		super(selectWindow, "공지사항", true);		//true이면 다이얼로그 처리 꼭 해야함. false이면 안해도 다른 창 사용 가능 ( modal )
		setSize(486,347);
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
		
		ta_patch.setText("             ===공지사항===\n\n\n"
						+ "현재 수정중인 버그 : \n"
						+ "- 게임도중 캐릭터가 화면 밖으로 나가는 현상.\n"
						+ "- 랭킹 시스템 정렬 문제.\n"
						+ "- 버그 악용시 계정삭제.\n"
						+ "- 이게 뭐라고 ㅋ\n\n"
						+ "기타 버그 제보 : D반 김성훈");
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
