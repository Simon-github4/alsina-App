package views;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
		tabbedPane.setIconAt(0, new ImageIcon(getClass().getResource("/resources/imgs/nuevo-gasto.png")));
		
		tabbedPane.addTab("Resumen CAJA ALQUILERES", new CajaHistorialAlquileres(alquilerDao, vehiculoDao, sucursalDao, gastoDao, destinoDao));
		tabbedPane.setIconAt(1, new ImageIcon(getClass().getResource("/resources/imgs/gastos-historial.png")));

		//tabbedPane.addTab("Resumen CAJA VENTAS", new CajaHistorialVentas(alquilerDao, vehiculoDao, sucursalDao, gastoDao, destinoDao));
		
		tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int selectedIndex = tabbedPane.getSelectedIndex(); // Índice de la pestaña seleccionada

                if(selectedIndex == 1) {
					CajaHistorialAlquileres a = (CajaHistorialAlquileres)tabbedPane.getSelectedComponent();
			          SwingUtilities.invokeLater(() -> {
			                a.getSearchButton().doClick();
			            });                	
                }
            }
        });
		
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
