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

public class LoginGUI extends JDialog implements ActionListener{
	private static final long serialVersionUID = 7417431979684212919L;
	private JButton btn_ok;
	private JButton btn_signUp;
	private JTextField tf_pw;
	private JLabel lbl_pw;
	private JTextField tf_id;
	private JLabel lbl_id;
	private SelectWindow selectWindow;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	public LoginGUI(SelectWindow selectWindow, ObjectInputStream ois, ObjectOutputStream oos) {
		super(selectWindow, "LogIn", true);		//true이면 다이얼로그 처리 꼭 해야함. false이면 안해도 다른 창 사용 가능 ( modal )
		this.oos = oos; this.ois = ois;
		this.selectWindow = selectWindow;
		setSize(300, 100);
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(selectWindow);
		
		lbl_id = new JLabel("ID");
		tf_id = new JTextField(10);
		lbl_pw = new JLabel("PW");
		tf_pw = new JPasswordField(10);
		tf_pw.addActionListener(this);
		
		btn_ok = new JButton("로그인");
		btn_ok.addActionListener(this);
		btn_signUp = new JButton("회원가입");
		btn_signUp.addActionListener(this);
		
		add(lbl_id);
		add(tf_id);
		add(lbl_pw);
		add(tf_pw);
		add(btn_ok);
		add(btn_signUp);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == btn_ok || source == tf_pw){
			//id/pw체크 한 다음에 로그인 시도.
			TransData data = new TransData();
			data.setCommand(TransData.ACCOUNT_CHECK);
			data.setId(tf_id.getText());
			data.setPw(tf_pw.getText());
			
			try {
				oos.writeObject(data);
				int result = (int) ois.readObject();
				switch(result){
				case 1:
					selectWindow.setId(tf_id.getText());
					dispose();
					break;
				case 2:
					JOptionPane.showMessageDialog(this, "비밀번호가 틀렸습니다");
					break;
				case 3:
					JOptionPane.showMessageDialog(this, "아이디가 없습니다.");
					break;
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}//로그인 시도. (ok버튼)
		
		if(source == btn_signUp){
			new SignUp(this, ois, oos);
			
		}
		
	}//actionPerformed()
	
}//class
