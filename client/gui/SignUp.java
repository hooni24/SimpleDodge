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

public class SignUp extends JDialog implements ActionListener{
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private LoginGUI loginGUI;
	private JLabel lbl_id;
	private JTextField tf_id;
	private JLabel lbl_pw;
	private JTextField tf_pw;
	private JButton btn_signUp;
	private JButton btn_cancel;
	
	public SignUp(LoginGUI loginGUI, ObjectInputStream ois, ObjectOutputStream oos) {
		super(loginGUI, "SingUp", true);
		this.oos = oos; this.ois = ois; this.loginGUI = loginGUI;
		
		setSize(300, 100);
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(loginGUI);
		
		lbl_id = new JLabel("ID");
		tf_id = new JTextField(10);
		lbl_pw = new JLabel("PW");
		tf_pw = new JPasswordField(10);
		tf_pw.addActionListener(this);
		
		btn_signUp = new JButton("회원가입");
		btn_signUp.addActionListener(this);
		btn_cancel = new JButton("취소");
		btn_cancel.addActionListener(this);
		
		add(lbl_id);
		add(tf_id);
		add(lbl_pw);
		add(tf_pw);
		add(btn_signUp);
		add(btn_cancel);
		
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
					JOptionPane.showMessageDialog(this, "ID와 PW는 8글자 이하여야 합니다. 대신 특수문자 등등 맘대로 써두 됌\n아 그리고 ID에는 공백안됌");
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
