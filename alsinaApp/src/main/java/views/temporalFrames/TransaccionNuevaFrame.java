package views.temporalFrames;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import entities.Cliente;
import entities.Compra;
import entities.Marca;
import entities.Transaccion;
import entities.Vehiculo;
import entities.VehiculoVenta;
import entities.Venta;
import entityManagers.ClienteDao;
import entityManagers.TransaccionDao;
import entityManagers.VehiculoDao;
import jakarta.persistence.PersistenceException;
import raven.datetime.DatePicker;
import raven.datetime.DatePicker.DateSelectionMode;
import utils.ViewUtils;

public class TransaccionNuevaFrame extends JFrame {

private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JPanel inputPanel;
	private JPanel west;
	private JPanel tablePanel;
	private JTable table;
	private DefaultTableModel tableModel;
	private JLabel messageLabel;
	private JComboBox<Cliente> clientComboBox;
	private JComboBox<Vehiculo> vehicleComboBox;
	private JTextField searchTextField;
	private JComboBox<Marca> filterBrandComboBox;
	private VehiculoDao VehiculoDao;
	private ClienteDao clienteDao;
	private TransaccionDao transaccionDao;
	
	private JButton searchButton;
	private JTextField sellingPriceTextField;
	private DatePicker dp;
	private JTextArea observationsTextArea;
	private JLabel titulo;
	
	private Transaccion venta;	
	private String tipoTransaction;
	
	public TransaccionNuevaFrame(VehiculoDao vehiculoDao, ClienteDao clienteDao, TransaccionDao transaccionDao, Vehiculo v, String tipoTrans) {
			this.VehiculoDao = vehiculoDao;
			this.clienteDao = clienteDao;
			this.transaccionDao = transaccionDao;
			this.tipoTransaction = tipoTrans;
			
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.setLayout(new BorderLayout(0, 5));
			contentPane.setPreferredSize(new Dimension(1050, 670));
			this.setSize(1100, 725);
			setLocationRelativeTo(null);
			this.setLayout(new BorderLayout());
			this.add(contentPane, BorderLayout.CENTER);

			inputPanel = new JPanel();
			inputPanel.setBorder(new LineBorder(new Color(84, 173, 253 ), 2, true));
			inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
			//inputPanel.setPreferredSize(new Dimension(150, HEIGHT));
			contentPane.add(inputPanel, BorderLayout.CENTER);
			

			JPanel horizontalPanel = new JPanel(new GridLayout());
			
			titulo = new JLabel("NUEVA "+tipoTransaction.toUpperCase(), JLabel.CENTER);
			titulo.setFont(new Font("Montserrat Black", Font.BOLD, 46));
			horizontalPanel.add(titulo);		
			inputPanel.add(horizontalPanel);
			
			
			horizontalPanel = new JPanel(new GridLayout(0,3));
			horizontalPanel.add(new JLabel("Vehiculo", JLabel.RIGHT));
			vehicleComboBox = new JComboBox<Vehiculo>();
			horizontalPanel.add(vehicleComboBox);
			horizontalPanel.add(new JLabel(""));
			horizontalPanel.add(new JLabel("Cliente", JLabel.RIGHT));
			clientComboBox = new JComboBox<Cliente>();
			horizontalPanel.add(clientComboBox);
			horizontalPanel.add(new JLabel(""));
			horizontalPanel.add(new JLabel("Fecha", JLabel.RIGHT));
			dp = new DatePicker();
			dp.setDateSelectionMode(DateSelectionMode.SINGLE_DATE_SELECTED);
			dp.setDateFormat("dd/MM/yyyy");
			dp.setBackground(Color.GRAY);
			JFormattedTextField tfd = new JFormattedTextField(); 
			dp.setEditor(tfd);
			horizontalPanel.add(tfd);
			horizontalPanel.add(new JLabel(""));
			inputPanel.add(horizontalPanel);		
			
			horizontalPanel = new JPanel(new GridLayout(0,3));
			horizontalPanel.add(new JLabel("Precio de venta", JLabel.RIGHT));
			sellingPriceTextField = new JTextField();
			sellingPriceTextField.setBackground(Color.yellow);
			horizontalPanel.add(sellingPriceTextField);
			horizontalPanel.add(new JLabel(""));
			inputPanel.add(horizontalPanel);

			horizontalPanel = new JPanel(new GridLayout(0,3));
			horizontalPanel.add(new JLabel("Observaciones", JLabel.RIGHT));
			observationsTextArea = new JTextArea();
			observationsTextArea.setRows(8);
	        observationsTextArea.setLineWrap(true);
	        observationsTextArea.setWrapStyleWord(true);
			horizontalPanel.add(observationsTextArea);
			horizontalPanel.add(new JLabel(""));
			inputPanel.add(horizontalPanel);
			
			horizontalPanel = new JPanel(new GridLayout());
			JButton confirm = new JButton("Confirmar");
			confirm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
						if(validateFields())
							if(insert())
								dispose();
					//JTabbedPane t = getTabbedPane();
					//t.setSelectedIndex(1);
					//VentaNuevaPagosForm a = (VentaNuevaPagosForm)t.getSelectedComponent();
					

			}});
	        confirm.setPreferredSize(new Dimension(250,40));
			JButton cancel = new JButton("Cancelar");
			cancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					clearFields();
				}
			});
			cancel.setPreferredSize(new Dimension(250,40));

			horizontalPanel.add(new JLabel(""));
			horizontalPanel.add(confirm);
			horizontalPanel.add(cancel);		
			horizontalPanel.add(new JLabel(""));
			inputPanel.add(horizontalPanel);		

			JPanel south = new JPanel();
	        south.setLayout(new BoxLayout(south, BoxLayout.Y_AXIS));
			
			horizontalPanel = new JPanel(new GridLayout());
			messageLabel = new JLabel("", SwingConstants.CENTER);
	        messageLabel.setForeground(Color.WHITE);
	        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
	        messageLabel.setBackground(Color.RED);
	        messageLabel.setOpaque(false);		
	        messageLabel.setPreferredSize(new Dimension(500,70));
			horizontalPanel.add(messageLabel);
			
			south.add(horizontalPanel);
			contentPane.add(south, BorderLayout.SOUTH);

			ViewUtils.setIconToButton(confirm, "/resources/imgs/confirmar.png", 32, 32);
			ViewUtils.setIconToButton(cancel, "/resources/imgs/escoba.png", 32, 32);

			fillClients();
			fillVehicles();
			vehicleComboBox.setSelectedItem(v);
			this.venta= null;
		}
		
		private void fillVehicles() {
			vehicleComboBox.addItem(new VehiculoVenta((long) 0, 0, 0, "Seleccione un Vehiculo", null, null, null));

			List<VehiculoVenta> sucursales = VehiculoDao.getVehiculosVenta("", new Marca(""));
			for(VehiculoVenta s : sucursales) {
				vehicleComboBox.addItem(s);
			}
		}

		private void fillClients() {
			clientComboBox.addItem(new Cliente("Seleccione un Cliente", null, null, null, null, null, null, null, null, null, null));

			List<Cliente> marcas = clienteDao.getClientes();
			for(Cliente m : marcas) 
				clientComboBox.addItem(m);
			
		}

		private boolean insert() {
			try {
				Vehiculo v = (Vehiculo)vehicleComboBox.getSelectedItem();
				Cliente c = (Cliente)clientComboBox.getSelectedItem();
				int amount = Integer.parseInt(sellingPriceTextField.getText());
				LocalDate date = dp.getSelectedDate();
				String obs = observationsTextArea.getText();
				
				Transaccion t = null;
				if(tipoTransaction.equalsIgnoreCase("VENTA"))
					t = new Venta(v, c, amount, date, obs);
				else
					if(tipoTransaction.equalsIgnoreCase("COMPRA"))
						t = new Compra(v, c, amount, date, obs);
				
				if(this.venta != null) 
					t.setId(this.venta.getId());
					
				transaccionDao.save(t);
				JOptionPane.showMessageDialog(null, "Operacion Exitosa.");
				
				if(tipoTransaction.equalsIgnoreCase("VENTA"))
					VehiculoDao.delete(t.getVehicle().getId());
				
			}catch (NumberFormatException e2) {
				JOptionPane.showMessageDialog(null,"Asegurese de que todos los campos tengan formato valido. (Campos NUMERICOS no pueden estar vacios)");
				return false;
			}catch(PersistenceException e5) {
				JOptionPane.showMessageDialog(null,"ya existe un Vehiculo con esa Patente");	
				return false;
			} catch(Exception e3) {
				JOptionPane.showMessageDialog(null,"Ha ocurrido un Error:"+e3.getLocalizedMessage());	
				return false;
			}
			return true;
		}		

		public void setVentaToUpdate(Transaccion v) {
			venta = v;
			titulo.setText("MODIFICANDO "+tipoTransaction.toUpperCase());
			clientComboBox.setSelectedItem(v.getClient());
			sellingPriceTextField.setText(String.valueOf(v.getAmount()));
			dp.setSelectedDate(v.getDate());
			observationsTextArea.setText(v.getDescription());
		}
		
		private boolean validateFields() {						
					if(clientComboBox.getSelectedIndex() == 0) {
						setMessage("Seleccione una Marca valida", false);
						return false;
					}				
					if(vehicleComboBox.getSelectedIndex() == 0) {
						setMessage("Seleccione una Sucursal valida", false);
						return false;
					}
					if(dp.getSelectedDate() == null) {
						setMessage("Seleccione una Fecha valida", false);
						return false;
					}
			return true;	
		}

		private void setMessage(String message, boolean succes) {
			messageLabel.setText(message);
	        messageLabel.setOpaque(true);
	        if(succes == true)
	        	messageLabel.setBackground(Color.GREEN);
	        else 
	        	messageLabel.setBackground(Color.RED);
		}

		private void clearFields() {
			clientComboBox.setSelectedIndex(0);
			vehicleComboBox.setSelectedIndex(0);
			sellingPriceTextField.setText("");
						
			messageLabel.setText("");
	        messageLabel.setOpaque(false);
		}

		public JTabbedPane getTabbedPane() {
	        Container parent = this.getParent();
	        while (parent != null) {
	            if (parent instanceof JTabbedPane) {
	                return (JTabbedPane) parent;
	            }
	            parent = parent.getParent();
	        }
	        return null; // Return null if not found
	    }
	
}
