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

public class GhostInfoDialog extends JDialog implements ActionListener{
	private static final long serialVersionUID = -4805013257326984759L;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JTextArea ta_patch;
	private JButton btn_ok;
	private JPanel panel_3;
	private JPanel panel_4;
	
	public GhostInfoDialog(SelectWindow selectWindow) {
		super(selectWindow, "유령맨 정보", true);		//true이면 다이얼로그 처리 꼭 해야함. false이면 안해도 다른 창 사용 가능 ( modal )
		setSize(486,312);
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
		
		ta_patch.setText( "이름 : 유령맨\n\n"
						+ "라이프 : 5\n"
						+ "특징 : 8방향모션있음(무쓸모)\n"
						+ "특수능력(Space Bar)\n"
						+ ": 약 3초간 피격되지 않음.(총 2회)\n\n"
						+ "유령맨> 나는 유령이 아니다. 날아다니는 보자기다!!");
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
