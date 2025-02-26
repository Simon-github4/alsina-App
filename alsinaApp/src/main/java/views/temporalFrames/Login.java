package views.temporalFrames;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatIntelliJLaf;

import alsinaApp.RunApplication;
import entities.Usuario;
import entityManagers.UsuarioDao;
import views.Dashboard;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernameTextField;
	private JPasswordField passwordTextField;
	private JButton enterButton;
	private JLabel messageLabel;

	private UsuarioDao dao;

	public Login(UsuarioDao dao1) {
		FlatIntelliJLaf.setup();	

		dao = dao1;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 714, 573);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("INICIO DE SESION");
		lblNewLabel.setFont(new Font("Montserrat Black", Font.PLAIN, 25));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(138, 44, 424, 110);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("nombre de usuario");
		lblNewLabel_1.setFont(new Font("Montserrat Medium", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(56, 168, 197, 38);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("contraseña");
		lblNewLabel_1_1.setFont(new Font("Montserrat Medium", Font.PLAIN, 15));
		lblNewLabel_1_1.setBounds(56, 284, 197, 38);
		contentPane.add(lblNewLabel_1_1);
		
		usernameTextField = new JTextField();
		usernameTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					SwingUtilities.invokeLater(()-> passwordTextField.requestFocus());
			}
		});
		usernameTextField.setBounds(56, 219, 594, 38);
		contentPane.add(usernameTextField);
		usernameTextField.setColumns(10);
		
		enterButton = new JButton("INGRESAR");
		enterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ingresar();
			}
		});
		enterButton.setBounds(50, 419, 600, 56);
		contentPane.add(enterButton);
		
		passwordTextField = new JPasswordField();
		passwordTextField.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true");
		passwordTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					SwingUtilities.invokeLater(()-> enterButton.doClick());
			}
		});
		passwordTextField.setColumns(10);
		passwordTextField.setBounds(56, 332, 594, 38);
		contentPane.add(passwordTextField);
		
		messageLabel = new JLabel("");
		messageLabel.setFont(new Font("Montserrat Black", Font.PLAIN, 14));
		messageLabel.setBounds(56, 380, 594, 28);
		
		contentPane.add(messageLabel);

	}
	
	private void ingresar() {
		String username = usernameTextField.getText();
		String pass = passwordTextField.getText();
		Usuario user = dao.getUsuario(username, pass);
		if(user == null)
			setMessage("Credenciales Incorrectas");
		else {
			RunApplication.startApp();
			dispose();
		}	
	}
	
	private void setMessage(String message) {
		messageLabel.setText(message);
        messageLabel.setOpaque(true);
        messageLabel.setForeground(Color.RED);
	}
	
}
