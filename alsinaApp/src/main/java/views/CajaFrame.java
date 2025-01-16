package views;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import entityManagers.AlquilerDao;
import entityManagers.ClienteDao;
import entityManagers.DestinoDao;
import entityManagers.GastoDao;
import entityManagers.MarcaDao;
import entityManagers.SucursalDao;
import entityManagers.VehiculoDao;

public class CajaFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTabbedPane tabbedPane;

	public CajaFrame(GastoDao gastoDao, SucursalDao sucursalDao, AlquilerDao alquilerDao,VehiculoDao vehiculoDao, DestinoDao destinoDao) {
		setSize(new Dimension(1400, 800));
		setVisible(true);
		setLocationRelativeTo(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane);
		
		tabbedPane.addTab("Nuevo Gasto", new GastosForm(vehiculoDao, sucursalDao, gastoDao, destinoDao));
		//tabbedPane.setIconAt(0, new FlatSVGIcon("resources/imgs/car.svg"));;
		tabbedPane.addTab("Resumen CAJA", new CajaHistorial(alquilerDao, vehiculoDao, sucursalDao, gastoDao, destinoDao));
		
		tabbedPane.putClientProperty(FlatClientProperties.TABBED_PANE_HAS_FULL_BORDER, new Boolean(true));
		tabbedPane.putClientProperty(FlatClientProperties.TABBED_PANE_SHOW_TAB_SEPARATORS, true);
		tabbedPane.putClientProperty(FlatClientProperties.TABBED_PANE_TAB_WIDTH_MODE_EQUAL, true);
		tabbedPane.putClientProperty(FlatClientProperties.TABBED_PANE_TAB_ROTATION_AUTO, true);
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

}
