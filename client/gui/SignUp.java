package client.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import common.TransData;
import java.awt.Color;
import java.awt.Font;

public class SignUp extends JDialog implements ActionListener{
	private static final long serialVersionUID = -3291434890544364721L;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private JLabel lbl_id;
	private JTextField tf_id;
	private JLabel lbl_pw;
	private JTextField tf_pw;
	private JButton btn_signUp;
	private JButton btn_cancel;
	
	public SignUp(LoginGUI loginGUI, ObjectInputStream ois, ObjectOutputStream oos) {
		super(loginGUI, "\uD68C\uC6D0\uAC00\uC785", true);
		getContentPane().setBackground(Color.DARK_GRAY);
		this.oos = oos; this.ois = ois;
		
		setSize(359, 208);
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setVgap(40);
		flowLayout.setHgap(10);
		getContentPane().setLayout(flowLayout);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(loginGUI);
		
		lbl_id = new JLabel("ID");
		lbl_id.setFont(new Font("D2Coding", Font.PLAIN, 18));
		lbl_id.setForeground(new Color(0, 255, 127));
		lbl_id.setBackground(Color.DARK_GRAY);
		tf_id = new JTextField(12);
		tf_id.setFont(new Font("D2Coding", Font.PLAIN, 16));
		tf_id.setBackground(Color.GRAY);
		tf_id.setForeground(Color.GREEN);
		tf_id.setDisabledTextColor(Color.GRAY);
		lbl_pw = new JLabel("PW");
		lbl_pw.setFont(new Font("D2Coding", Font.PLAIN, 18));
		lbl_pw.setForeground(new Color(0, 255, 127));
		lbl_pw.setBackground(Color.DARK_GRAY);
		tf_pw = new JPasswordField(12);
		tf_pw.setFont(new Font("D2Coding", Font.PLAIN, 16));
		tf_pw.setBackground(Color.GRAY);
		tf_pw.setForeground(Color.GREEN);
		tf_pw.setDisabledTextColor(Color.GRAY);
		tf_pw.addActionListener(this);
		
		btn_signUp = new JButton("회원가입");
		btn_signUp.setFont(new Font("D2Coding", Font.PLAIN, 15));
		btn_signUp.setForeground(new Color(0, 255, 127));
		btn_signUp.setBackground(Color.DARK_GRAY);
		btn_signUp.addActionListener(this);
		btn_cancel = new JButton("취소");
		btn_cancel.setFont(new Font("D2Coding", Font.PLAIN, 15));
		btn_cancel.setForeground(new Color(0, 255, 127));
		btn_cancel.setBackground(Color.DARK_GRAY);
		btn_cancel.addActionListener(this);
		
		getContentPane().add(lbl_id);
		getContentPane().add(tf_id);
		getContentPane().add(lbl_pw);
		getContentPane().add(tf_pw);
		getContentPane().add(btn_signUp);
		getContentPane().add(btn_cancel);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == btn_signUp || source == tf_pw){
			if(!tf_id.getText().equals("") && !tf_pw.getText().equals("")){
				if(tf_id.getText().length() <= 8 && tf_pw.getText().length() <= 8 && !tf_id.getText().contains(" ")){
					char[] idChar = tf_id.getText().toCharArray();
					boolean isEnglish = true;
					int a;
					for(int i = 0; i < idChar.length; i++){
						a = (int) idChar[i];
						if(!(a>=48 && a<=59 || a>=65 && a<=90 || a>=97 && a<=122)){
							isEnglish = false;
						}
					}
					if(isEnglish){
						TransData data = new TransData();
						data.setCommand(TransData.SIGN_UP);
						data.setId(tf_id.getText());
						data.setPw(tf_pw.getText());
						try {
							oos.writeObject(data);
							boolean result = (boolean) ois.readObject();
							if(result){
								JOptionPane.showMessageDialog(this, "가입 성공!");
								dispose();
							}else {
								JOptionPane.showMessageDialog(this, "이미 존재하는 ID입니다...");
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						}
					}else {
						JOptionPane.showMessageDialog(this, "ID는 숫자나 영문자만 가능합니다.");
					}
				}else {
					JOptionPane.showMessageDialog(this, "ID와 PW는 8글자 이하여야 합니다.\n아 그리고 ID에는 공백안됌");
				}
			}else {
				JOptionPane.showMessageDialog(this, "ID,PW를 정확히 입력하세요");
			}
		}
		
		if(source == btn_cancel){
			dispose();
		}
	}
}
