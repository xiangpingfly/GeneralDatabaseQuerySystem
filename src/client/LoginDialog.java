package client;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
/**
 * ��½����ľ���ʵ��
 */
class LoginDialog extends JFrame implements ActionListener {
	JTextField userName;
	JTextField password;
	JLabel jl1;
	JLabel jl2;
	JButton button_ok;
	JButton button_cancel;

	LoginDialog(String title){
		super(title);
		this.setLayout(new FlowLayout());
		
		userName = new JTextField(15);
		password = new JPasswordField(15);
		jl1 = new JLabel("�û���:",4);
		jl2 = new JLabel("��    ��:",4);
		button_ok = new JButton("��¼");
		button_cancel = new JButton("ȡ��");
		
		this.add(jl1);
		this.add(userName);
		this.add(jl2);
		this.add(password);
		this.add(button_ok);
		this.add(button_cancel);
		
		button_ok.addActionListener(this);
		button_cancel.addActionListener(this);
		this.setResizable(false);
		this.setBounds(300, 300, 250,130);
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == button_ok){
//			�����������
			if(userName.getText().equals("")){
				JOptionPane.showMessageDialog(null, "�������û���!");
			}else if(password.getText().equals("")){
				JOptionPane.showMessageDialog(null, "����������!");
			}
			boolean flag = false;
			String sign = "login";//���õ�½��־
			Socket s = null;
			DataOutputStream dos = null;
			DataInputStream dis = null;
			try {
				s = new Socket("localhost",6666);
				dos = new DataOutputStream(s.getOutputStream());
				dis = new DataInputStream(s.getInputStream());
				dos.writeUTF(sign);
				dos.writeUTF(userName.getText().trim());//write user name
				dos.writeUTF(password.getText().trim());//write password
				flag = dis.readBoolean();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				try {
					if(dos!=null) {
						dos.close();
						dos = null;
					}
					if(dis!=null) {
						dis.close();
						dis = null;
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if(flag){
				this.setVisible(false);
				new Administrator();
			} else {
				JOptionPane.showMessageDialog(null,"��¼ʧ��!\n��������û����������Ƿ���ȷ !");
			}
		} else if(e.getSource() == button_cancel){
			this.setVisible(false);
		}
	}
}
