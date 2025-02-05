package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatIntelliJLaf;

import entityManagers.AlquilerDao;
import entityManagers.ClienteDao;
import entityManagers.CuotaDao;
import entityManagers.DestinoDao;
import entityManagers.GastoDao;
import entityManagers.MarcaDao;
import entityManagers.SucursalDao;
import entityManagers.TransaccionDao;
import entityManagers.VehiculoDao;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import utils.ViewUtils;

public class Dashboard extends JFrame{

	private static Dashboard currentInstance;
	private static EntityManagerFactory emf;
	
	private JPanel mainPanel;
	private CuotaDao CuotaDao;
	private static TransaccionDao TransaccionDao;
	private static AlquilerDao AlquilerDao;
	private static VehiculoDao VehiculoDao;
	private static ClienteDao ClienteDao;
	private static SucursalDao SucursalDao;
	private static MarcaDao MarcaDao;
	private static DestinoDao DestinoDao;
	private static GastoDao GastoDao;
	
	/*static {
        try {
    }*/

	public Dashboard() {
		try {
        	SwingUtilities.invokeLater(() -> {
        		//Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
        		 	emf = Persistence.createEntityManagerFactory(("persistencia"));
        			AlquilerDao = new AlquilerDao(emf);
        			VehiculoDao = new VehiculoDao(emf);
        			ClienteDao = new ClienteDao(emf);
        			SucursalDao = new SucursalDao(emf);
        			MarcaDao = new MarcaDao(emf);
        			DestinoDao = new DestinoDao(emf);
        			GastoDao = new GastoDao(emf);    
        			TransaccionDao = new TransaccionDao(emf);
        			CuotaDao = new CuotaDao(emf);
        	});                	
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("Failed to initialize EntityManagerFactory: " + e.getMessage());
        }
		setStyling();
		getRootPane().putClientProperty(FlatClientProperties.FULL_WINDOW_CONTENT, true);
		getRootPane().putClientProperty(FlatClientProperties.TITLE_BAR_BACKGROUND, new Color(84, 173, 253 ));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(719,650);
		getContentPane().setLayout(new BorderLayout(0,20));
		
		mainPanel= new JPanel();
		mainPanel.setLayout(new GridLayout(2, 4, 15, 15));
		getContentPane().add(mainPanel, BorderLayout.CENTER);			
		
		JButton btnNewButton_2_9 = new JButton("Clientes/Marcas/Sucursales/Categorias");
		btnNewButton_2_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MarcasSucursalesClientesFrame(MarcaDao, SucursalDao, ClienteDao, DestinoDao);
			}
		});
		mainPanel.add(btnNewButton_2_9);	
		JButton btnNewButton_2 = new JButton("Rent A Car");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AlquileresFrame(MarcaDao, SucursalDao, ClienteDao, AlquilerDao, VehiculoDao).setVisible(true);
			}
		});
		mainPanel.add(btnNewButton_2);	
		JButton btnNewButton_2_1 = new JButton("Vehiculos a la Venta");
		mainPanel.add(btnNewButton_2_1);
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new VentaFrame(MarcaDao, SucursalDao, VehiculoDao ,ClienteDao, TransaccionDao, CuotaDao).setVisible(true);
			}	
		});
		JButton btnNewButton = new JButton("Caja");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CajaFrame g = new CajaFrame(GastoDao, SucursalDao, AlquilerDao, VehiculoDao, DestinoDao);
				g.setVisible(true);
			}
		});
		mainPanel.add(btnNewButton);
		
		JPanel imagePanel= new JPanel(new GridLayout());
		getContentPane().add(imagePanel, BorderLayout.NORTH);
		imagePanel.setPreferredSize(new Dimension(380,210));
		
		JLabel l = new JLabel("");
		ViewUtils.setIconToLabel(l, "/resources/imgs/Alsina.png", 350,180);
		imagePanel.add(l);	
		/*for(Component b : mainPanel.getComponents()) {
			//((JButton) b).setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3, true));
		}*/	
	}
	
	public void verGastosByPlate(String plate) {
		CajaFrame g = new CajaFrame(GastoDao, SucursalDao, AlquilerDao, VehiculoDao, DestinoDao);
		g.getTabbedPane().setSelectedIndex(1);
		CajaHistorialAlquileres c = (CajaHistorialAlquileres)g.getTabbedPane().getSelectedComponent();
		c.getSearchTextField().setText(plate);
		c.getSearchButton().doClick();
		
		g.setVisible(true);
	}
	
	public void nuevoGastoByPlate(String plate) {
		CajaFrame g = new CajaFrame(GastoDao, SucursalDao, AlquilerDao, VehiculoDao, DestinoDao);
		g.getTabbedPane().setSelectedIndex(0);
		GastosForm c = (GastosForm)g.getTabbedPane().getSelectedComponent();
		c.getVehicleTextField().setText(plate);	
		g.setVisible(true);
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
		
		//contentPane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3, true)); // Soft shadow
	}

	public static Dashboard getInstance() {
		if (currentInstance == null) {
			currentInstance = new Dashboard();
		}	
		return currentInstance;
	}

	public CuotaDao getCuotaDao() {
		return CuotaDao;
	}

	public static TransaccionDao getTransaccionDao() {
		return TransaccionDao;
	}

	public static AlquilerDao getAlquilerDao() {
		return AlquilerDao;
	}

	public static VehiculoDao getVehiculoDao() {
		return VehiculoDao;
	}

	public static ClienteDao getClienteDao() {
		return ClienteDao;
	}

	public static SucursalDao getSucursalDao() {
		return SucursalDao;
	}

	public static MarcaDao getMarcaDao() {
		return MarcaDao;
	}

	public static DestinoDao getDestinoDao() {
		return DestinoDao;
	}

	public static GastoDao getGastoDao() {
		return GastoDao;
	}
	
	
}
