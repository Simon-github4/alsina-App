package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatIntelliJLaf;

import entityManagers.AlquilerDao;
import entityManagers.ClienteDao;
import entityManagers.DestinoDao;
import entityManagers.GastoDao;
import entityManagers.MarcaDao;
import entityManagers.SucursalDao;
import entityManagers.VehiculoDao;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Dashboard extends JFrame{

	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");

	private JPanel mainPanel;
	private static AlquilerDao AlquilerDao = new AlquilerDao(emf);
	private static VehiculoDao VehiculoDao = new VehiculoDao(emf);
	private static ClienteDao ClienteDao = new ClienteDao(emf);
	private static SucursalDao SucursalDao = new SucursalDao(emf);
	private static MarcaDao MarcaDao = new MarcaDao(emf);
	private static DestinoDao DestinoDao = new DestinoDao(emf);
	private static GastoDao GastoDao = new GastoDao(emf);
	
	public Dashboard() {
		
		setStyling();
		getRootPane().putClientProperty(FlatClientProperties.FULL_WINDOW_CONTENT, true);
		getRootPane().putClientProperty(FlatClientProperties.TITLE_BAR_BACKGROUND, new Color(84, 173, 253 ));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(719,448);
		getContentPane().setLayout(new BorderLayout(40, 40));
	
		mainPanel= new JPanel();
		mainPanel.setLayout(new GridLayout(3, 4, 15, 15));
		//mainPanel.setBackground(new Color(0, 102, 204));
		getContentPane().add(mainPanel, BorderLayout.CENTER);
				
		
		JButton btnNewButton_2_9 = new JButton("Clientes/Marcas/Sucursales");
		btnNewButton_2_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MarcasSucursalesClientesFrame(MarcaDao, SucursalDao, ClienteDao, DestinoDao);
			}
		});
		btnNewButton_2_9.setBounds(208, 194, 128, 100);
		mainPanel.add(btnNewButton_2_9);
		
		JButton btnNewButton_2 = new JButton("Vehiculos Alquiler");
		btnNewButton_2.setBounds(45, 35, 128, 100);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AlquileresFrame(MarcaDao, SucursalDao, ClienteDao, AlquilerDao, VehiculoDao).setVisible(true);
			}
		});
		mainPanel.add(btnNewButton_2);
		
		JButton btnNewButton_2_1 = new JButton("Vehiculos a la Venta");
		btnNewButton_2_1.setBounds(208, 35, 128, 100);
		mainPanel.add(btnNewButton_2_1);
		
		JButton btnNewButton_2_5 = new JButton("Ventas/Compras");
		btnNewButton_2_5.setBounds(374, 194, 128, 100);
		mainPanel.add(btnNewButton_2_5);
		
		JButton btnNewButton = new JButton("Gastos");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CajaFrame g = new CajaFrame(GastoDao, SucursalDao, AlquilerDao, VehiculoDao, DestinoDao);
				g.setVisible(true);
			}
		});
		btnNewButton.setBounds(45, 320, 628, 62);
		mainPanel.add(btnNewButton);
		
		/*for(Component b : mainPanel.getComponents()) {
			//((JButton) b).setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3, true));
		}*/
		
	}
	
	private void setStyling() {
		FlatIntelliJLaf.setup();	

		UIManager.put("Button.arc", 20);		
		UIManager.put("Button.foreground", Color.WHITE);		
		UIManager.put("Button.background", new Color(84, 173, 253 ));	//new Color(157, 201, 255)	
		//UIManager.put("Button.border", BorderFactory.createLineBorder(Color.WHITE, 1));
		UIManager.put("Button.borderColor", Color.WHITE);				

		UIManager.put("TextComponent.arc", 10);		
		UIManager.put("Component.arc", 10);		
		UIManager.put("Component.innerFocusWidth", 1);		
		UIManager.put("Table.alternateRowColor", new Color(225,225,225));
	    
		UIManager.put("defaultFont", new Font("Montserrat", Font.TYPE1_FONT, 15));
		
		//putClientProperty(FlatClientProperties.FULL_WINDOW_CONTENT, "true");
		//contentPane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3, true)); // Soft shadow
	}

}
