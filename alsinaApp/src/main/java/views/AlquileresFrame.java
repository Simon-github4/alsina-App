package views;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.formdev.flatlaf.FlatClientProperties;

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
		tabbedPane.setIconAt(0, new ImageIcon(getClass().getResource("/resources/imgs/carro.png")));
		tabbedPane.addTab("Nuevo Contrato", new AlquileresForm(alquilerDao, vehiculoDao, clienteDao));
		tabbedPane.setIconAt(1, new ImageIcon(getClass().getResource("/resources/imgs/nuevo-alquiler.png")));
		tabbedPane.addTab("Historial Alquileres", new AlquileresHistorial(alquilerDao));
		tabbedPane.setIconAt(2, new ImageIcon(getClass().getResource("/resources/imgs/alquileres-historial.png")));
		
		tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int selectedIndex = tabbedPane.getSelectedIndex(); // Índice de la pestaña seleccionada

                if(selectedIndex==2) {
					AlquileresHistorial a = (AlquileresHistorial)tabbedPane.getSelectedComponent();
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

	
	

}
