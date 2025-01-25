package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatClientProperties;

import entities.Alquiler;
import entities.Cliente;
import entities.Destino;
import entities.Gasto;
import entities.Sucursal;
import entities.VehiculoAlquilable;
import entityManagers.AlquilerDao;
import entityManagers.ClienteDao;
import entityManagers.DestinoDao;
import entityManagers.GastoDao;
import entityManagers.SucursalDao;
import entityManagers.VehiculoDao;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import raven.datetime.DatePicker;
import utils.ViewUtils;

public class GastosForm extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JPanel inputPanel;
	private JLabel messageLabel;
	
	private JTextField vehicleTextField;
	private JTextField descriptionTextField;
	private JTextField paymentTextField;
	private JTextField amountTextField;
	private JFormattedTextField date;
	private JComboBox<Sucursal> branchComboBox;
	private JComboBox<Destino> destinationComboBox;
	private DatePicker dp;
	
	private VehiculoDao VehiculoDao;
	private SucursalDao SucursalDao;
	private GastoDao GastoDao;
	private DestinoDao DestinoDao;
	private JLabel title;
	private Long id;
	
	public GastosForm(VehiculoDao vehiculoDao, SucursalDao SucursalDao, GastoDao GastoDao, DestinoDao DestinoDao) {
			this.VehiculoDao = vehiculoDao;
			this.SucursalDao = SucursalDao;
			this.GastoDao = GastoDao;
			this.DestinoDao = DestinoDao;
			this.id=null;
			
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.setLayout(new BorderLayout(0, 5));
			contentPane.setPreferredSize(new Dimension(1050, 670));
			this.setSize(1100, 850);
			this.setLayout(new BorderLayout());
			this.add(contentPane, BorderLayout.CENTER);
			
			inputPanel = new JPanel();
			//inputPanel.setBorder(new LineBorder(new Color(84, 173, 253 ), 2, true));
			inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
			contentPane.add(inputPanel, BorderLayout.CENTER);		

			JPanel horizontalPanel = new JPanel(new GridLayout());
			
			title = new JLabel("NUEVO GASTO", JLabel.CENTER);
			title.setFont(new Font("Montserrat Black", Font.BOLD, 46));
			horizontalPanel.add(title);		
			inputPanel.add(horizontalPanel);	
			
			horizontalPanel = new JPanel(new GridLayout(0,3));
			horizontalPanel.add(new JLabel("Vehiculo", JLabel.RIGHT));
			vehicleTextField = new JTextField();
			vehicleTextField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Opcional");
			horizontalPanel.add(vehicleTextField);
			horizontalPanel.add(new JLabel(""));
			
			horizontalPanel.add(new JLabel("Fecha", JLabel.RIGHT));
			dp = new DatePicker();
			dp.setDateSelectionMode(DatePicker.DateSelectionMode.SINGLE_DATE_SELECTED);
			dp.setUsePanelOption(true);  
			date = new JFormattedTextField();
			dp.setBackground(Color.GRAY); // Color de fondo oscuro
			dp.setDateFormat("yyyy/MM/dd");
			dp.setEditor(date);
			horizontalPanel.add(date);
			horizontalPanel.add(new JLabel("", JLabel.RIGHT));
			
			horizontalPanel.add(new JLabel("Descripcion", JLabel.RIGHT));
			descriptionTextField = new JTextField();
			horizontalPanel.add(descriptionTextField);
			horizontalPanel.add(new JLabel("", JLabel.RIGHT));

			inputPanel.add(horizontalPanel);		
			
			horizontalPanel.add(new JLabel("Destino", JLabel.RIGHT));
			destinationComboBox = new JComboBox<Destino>();
			horizontalPanel.add(destinationComboBox);
			horizontalPanel.add(new JLabel(""));

			
			horizontalPanel = new JPanel(new GridLayout(0,3));
			horizontalPanel.add(new JLabel("Consecionaria", JLabel.RIGHT));
			branchComboBox = new JComboBox<Sucursal>();
			horizontalPanel.add(branchComboBox);
			horizontalPanel.add(new JLabel(""));

			horizontalPanel.add(new JLabel("Forma de Pago", JLabel.RIGHT));
			paymentTextField = new JTextField();
			horizontalPanel.add(paymentTextField);
			horizontalPanel.add(new JLabel("", JLabel.RIGHT));
			
			horizontalPanel.add(new JLabel("Importe", JLabel.RIGHT));
			amountTextField = new JTextField(10);
			amountTextField.setBackground(Color.YELLOW);
			horizontalPanel.add(amountTextField);
			horizontalPanel.add(new JLabel(""));
			
			inputPanel.add(horizontalPanel);		
			
			horizontalPanel = new JPanel(new GridLayout(0,4));
			horizontalPanel.add(new JLabel(""));

			JButton confirm = new JButton("Confirmar");
			confirm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

						if(validateFields())
								insert();				

				}});
	        confirm.setPreferredSize(new Dimension(WIDTH, 80));
			JButton cancel = new JButton("Cancelar");
			cancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					clearFields();
				}
			});
			cancel.setPreferredSize(new Dimension(WIDTH, 80));
			horizontalPanel.add(confirm);
			horizontalPanel.add(cancel);	
			horizontalPanel.add(new JLabel(""));
			horizontalPanel.add(new JLabel(""));
			horizontalPanel.add(new JLabel(""));
			horizontalPanel.add(new JLabel(""));
			horizontalPanel.add(new JLabel(""));

			ViewUtils.setIconToButton(confirm, "/resources/imgs/confirmar.png", 32, 32);
			ViewUtils.setIconToButton(cancel, "/resources/imgs/escoba.png", 32, 32);
			horizontalPanel.setPreferredSize(new Dimension(WIDTH, 80));
			inputPanel.add(horizontalPanel);		
			
	        JPanel south = new JPanel();
	        south.setLayout(new BoxLayout(south, BoxLayout.Y_AXIS));

			horizontalPanel = new JPanel(new GridLayout());
			south.add(horizontalPanel);
			
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

			fillBranchs();
			fillDestinations();
			clearFields();
		}	
	
		private void fillBranchs() {
			branchComboBox.addItem(new Sucursal("Seleccione una Consecionaria"));

			List<Sucursal> sucursales = SucursalDao.getSucursales();
			for(Sucursal s : sucursales) 
				branchComboBox.addItem(s);
				
		}

		private void fillDestinations() {
			destinationComboBox.addItem(new Destino("Seleccione un Destino"));
			
			List<Destino> destinos = DestinoDao.getDestinos();
			for(Destino s : destinos) 
				destinationComboBox.addItem(s);
		}
		
		private void insert() {
			VehiculoAlquilable vehicle=null;
			if( ! vehicleTextField.getText().isBlank()) 
			try {
				vehicle = VehiculoDao.getVehiculoAlquilableByPlate(vehicleTextField.getText());
			} catch (NoResultException p) {
				SwingUtilities.invokeLater(() -> setMessage("Vehiculo Inexistente", false));
				throw p;
			}
			
			try {
				LocalDate date = dp.getSelectedDate();
				Sucursal branch = (Sucursal) branchComboBox.getSelectedItem();
				int amount = Integer.parseInt(amountTextField.getText());
				String description = descriptionTextField.getText();
				String pay = paymentTextField.getText();
				Destino dest = (Destino)destinationComboBox.getSelectedItem();
				
				Gasto g = new Gasto(vehicle, amount, date, description, pay, dest, branch);

				if(id != null) {
					g.setId(id);
					GastoDao.save(g);
					title.setText("NUEVO ALQUILER");			
					id = null;
					setMessage("Modificado correctamente", true);
				}
				GastoDao.save(g);
				clearFields();
				setMessage("Insertado correctamente", true);

			} catch (NumberFormatException e2) {
				setMessage("Asegurese de que todos los campos tengan formato valido. (Campos NUMERICOS no pueden estar vacios)", false);
			} catch (PersistenceException e5) {
				setMessage("ya existe un Vehiculo con esa Patente", false);
			} catch (Exception e3) {
				setMessage("Ha ocurrido un Error:" + e3.getLocalizedMessage(), false);
			}

		}
		
		private boolean validateFields() {
			try {				
					if(branchComboBox.getSelectedIndex() == 0) {
						setMessage("Seleccione una Consecionaria valido", false);
						return false;
					}				
					if(destinationComboBox.getSelectedIndex() == 0) {
						setMessage("Seleccione un Destino valido", false);
						return false;
					}
					if(!dp.isDateSelected()) {
						setMessage("Seleccione Fechas validas", false);
						return false;	
					}			
			}catch (NumberFormatException e) {
				setMessage("Asegurese de que todos los campos tengan formato valido.", false);
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
			vehicleTextField.setText("");
			amountTextField.setText("");
			descriptionTextField.setText("");
			dp.clearSelectedDate();
			paymentTextField.setText("");
			branchComboBox.setSelectedIndex(0);
			destinationComboBox.setSelectedIndex(0);
	
			messageLabel.setText("");
	        messageLabel.setOpaque(false);
		}

		public void setUpdateForm(Gasto g) {
			title.setText("MODIFICANDO GASTO");
			id = g.getId();
			
			if(g.getVehicle() != null)
				vehicleTextField.setText(g.getVehicle().getPlate());
			dp.setSelectedDate(g.getDate());
			descriptionTextField.setText(g.getDescription());
			amountTextField.setText(String.valueOf(g.getAmount()));
			paymentTextField.setText(String.valueOf(g.getPayment()));
			branchComboBox.setSelectedItem(g.getBranch());
			destinationComboBox.setSelectedItem(g.getDestination());
		}
		
}
