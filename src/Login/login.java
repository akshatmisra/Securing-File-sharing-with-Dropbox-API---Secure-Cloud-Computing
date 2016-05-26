package Login;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;

import UI.UI;
import UIController.UIController;
import UIController.UIController.listenForSubmit;
import UIFunctions.UIFunctions;

public class login extends JFrame{

	public static JFrame loginFrame;
	public JPanel loginPane;
	public static JTextField txtUsername;
	public static JTextField txtPassword;
	public JLabel lblUsername;
	public JLabel lblPassword;
	public JButton btnSubmit;
	
	public static login log;
	public static UI ui;
	public static UIController UICon;
	public static UIFunctions UIfunc;
	public JLabel header;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					
					log=new login();													//the main function
					ui= new UI();
					
					UICon=new UIController(log,ui,UIfunc);
					
														
					loginFrame.setLocationRelativeTo(null);
					loginFrame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
public login(){
	
	loginFrame=new JFrame();
	loginFrame.setFont(new Font("Bell MT", Font.BOLD, 12));
	loginFrame.setTitle("Encryption-Decryption-Dropbox");
	loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	loginFrame.setBounds(0, 0, 993, 993);
	loginFrame.setResizable(false);
	loginPane=new JPanel();
	loginPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	loginFrame.setContentPane(loginPane);
	loginPane.setLayout(null);
	
	txtUsername = new JTextField();
	txtUsername.setBounds(250, 350, 181, 60);
	txtUsername.setFont(new Font("Cambria", Font.PLAIN, 24));
	loginPane.add(txtUsername);
	txtUsername.setColumns(50);
	
	txtPassword = new JPasswordField(100);
	txtPassword.setBounds(550, 350, 181, 60);
	txtPassword.setFont(new Font("Cambria", Font.PLAIN, 24));
	loginPane.add(txtPassword);
	txtPassword.setColumns(50);
	
	lblUsername = new JLabel("Username");
	lblUsername.setFont(new Font("Cambria", Font.BOLD, 24));
	lblUsername.setBounds(250, 250, 181, 80);
	loginPane.add(lblUsername);
	
	lblPassword = new JLabel("Password");
	lblPassword.setFont(new Font("Cambria", Font.BOLD, 24));
	lblPassword.setBounds(550, 250, 181, 80);
	loginPane.add(lblPassword);
	
	btnSubmit = new JButton("Submit");
	btnSubmit.setBounds(405, 500, 181, 60);
	btnSubmit.setFont(new Font("Cambria", Font.BOLD, 24));
	loginPane.add(btnSubmit);
	
	header = new JLabel();
	header.setBounds(240,70,650,100);
	header.setFont(new Font("Bell MT", Font.BOLD, 50));
	header.setText("Dropbox File Defender");
	
	loginPane.add(header);
	
	}

public void addSubmitListener(ActionListener listenForSubmit)
{
	      
	 btnSubmit.addActionListener(listenForSubmit);
}



	
}
