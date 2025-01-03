package views;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import entityManagers.AlquilerDao;
import entityManagers.ClienteDao;
import entityManagers.MarcaDao;
import entityManagers.SucursalDao;
import entityManagers.VehiculoDao;

public class AlquileresFrame extends JFrame{
	
	private JPanel contentPane;
	private JTabbedPane tabbedPane;

	public AlquileresFrame(MarcaDao marcaDao, SucursalDao sucursalDao, ClienteDao clienteDao, AlquilerDao alquilerDao,VehiculoDao vehiculoDao) {
		setSize(new Dimension(1400, 800));
		//setSize(1100, 850);
		setVisible(true);
		setLocationRelativeTo(null);
		
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane);
		
		tabbedPane.addTab("Vehiculos de Alquiler", new AutosAlquilerForm(vehiculoDao, sucursalDao, marcaDao));
		tabbedPane.setIconAt(0, new FlatSVGIcon("resources/imgs/car.svg"));;
		tabbedPane.addTab("Alquileres", new AlquileresForm(alquilerDao, vehiculoDao, clienteDao));
		
		tabbedPane.putClientProperty(FlatClientProperties.TABBED_PANE_HAS_FULL_BORDER, new Boolean(true));
		tabbedPane.putClientProperty(FlatClientProperties.TABBED_PANE_SHOW_TAB_SEPARATORS, true);
		tabbedPane.putClientProperty(FlatClientProperties.TABBED_PANE_TAB_WIDTH_MODE_EQUAL, true);
		tabbedPane.putClientProperty(FlatClientProperties.TABBED_PANE_TAB_ROTATION_AUTO, true);
	}

	
	

}
