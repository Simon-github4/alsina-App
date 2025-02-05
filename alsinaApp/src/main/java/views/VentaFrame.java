package views;

import java.awt.Dialog.ModalityType;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.formdev.flatlaf.FlatClientProperties;

import entities.Transaccion;
import entities.Vehiculo;
import entityManagers.ClienteDao;
import entityManagers.CuotaDao;
import entityManagers.MarcaDao;
import entityManagers.SucursalDao;
import entityManagers.TransaccionDao;
import entityManagers.VehiculoDao;
import views.temporalFrames.TransaccionNuevaFrame;
import views.temporalFrames.VentaNuevaPagosFrame;

public class VentaFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;
	private VehiculoDao vehiculoDao;
	private ClienteDao clienteDao;
	private TransaccionDao transaccionDao;
	private CuotaDao cuotaDao;

	public VentaFrame(MarcaDao marcaDao, SucursalDao sucursalDao, VehiculoDao vehiculoDao, ClienteDao clienteDao, TransaccionDao transaccionDao, CuotaDao cuotaDao) {
		setSize(new Dimension(1400, 800));
		setVisible(true);
		setLocationRelativeTo(null);
		this.clienteDao = clienteDao;
		this.vehiculoDao= vehiculoDao;
		this.transaccionDao = transaccionDao;
		this.cuotaDao = cuotaDao;
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane);

		tabbedPane.addTab("Vehiculos de Venta", new VehiculoVentaForm(vehiculoDao, sucursalDao, marcaDao, transaccionDao));
		tabbedPane.setIconAt(0, new ImageIcon(getClass().getResource("/resources/imgs/carro.png")));

		tabbedPane.addTab("Historial de Boletos de C - V", new VentasHistorial(transaccionDao, clienteDao));
		tabbedPane.setIconAt(1, new ImageIcon(getClass().getResource("/resources/imgs/historial-de-transacciones.png")));

		tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int selectedIndex = tabbedPane.getSelectedIndex(); // Índice de la pestaña seleccionada

                if(selectedIndex == 1) {
                	VentasHistorial a = (VentasHistorial)tabbedPane.getSelectedComponent();
			          SwingUtilities.invokeLater(() -> {
			                a.getSearchButton().doClick();
			            });                	
                }else if(selectedIndex == 0) {
                	VehiculoVentaForm a = (VehiculoVentaForm)tabbedPane.getSelectedComponent();
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

	public void openNuevaVenta(Vehiculo v, Transaccion venta, String tipoTransaction) {
		TransaccionNuevaFrame frame = new TransaccionNuevaFrame(vehiculoDao, clienteDao, transaccionDao, v, tipoTransaction);
		frame.setVisible(true);
		if(venta != null)
			frame.setVentaToUpdate(venta);
			
	}
	public void openListadoCuotas(Long transaccion) {
		new VentaNuevaPagosFrame(transaccionDao, cuotaDao, transaccion).setVisible(true);
	}
}
