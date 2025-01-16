package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import entities.Alquiler;
import entities.Cliente;
import entities.VehiculoAlquilable;
import entityManagers.AlquilerDao;
import entityManagers.ClienteDao;
import entityManagers.VehiculoDao;
import interfaces.ViewUtils;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import raven.datetime.DatePicker;
import raven.datetime.event.DateSelectionEvent;
import raven.datetime.event.DateSelectionListener;

public class AlquileresForm extends JPanel{
	
private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JPanel inputPanel;
	private JPanel west;
	private JPanel tablePanel;
	private JTable table;
	private DefaultTableModel tableModel;
	private JLabel messageLabel;
	
	private JTextField vehicleTextField;
	private JTextField kilometersDepartureTextField;
	private JTextField kilometersReturnTextField;
	private JTextField priceTextField;
	private JFormattedTextField dates;
	private JComboBox<Cliente> clientComboBox;
	private Long id;
	
	private JTextField searchTextField;
	private JFormattedTextField filterDatesField;
	private DatePicker dpFilters;
	private DatePicker dp;
	private JButton searchButton;
	
	private AlquilerDao AlquilerDao;
	private VehiculoDao VehiculoDao;
	private ClienteDao ClienteDao;

	private JComboBox<String> gasExitComboBox;
	private JComboBox<String> gasReturnComboBox;
	private JLabel title;

	private JLabel labelSuggestedAmount;

	private JRadioButton reservaRadioButton;

	public AlquileresForm(AlquilerDao alquilerDao, VehiculoDao vehiculoDao, ClienteDao clienteDao) {
			this.AlquilerDao= alquilerDao;	
			this.VehiculoDao = vehiculoDao;
			this.ClienteDao = clienteDao;
			this.id=null;
			
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.setLayout(new BorderLayout(0, 5));
			contentPane.setPreferredSize(new Dimension(1100, 750));
			this.setSize(1100, 850);
			this.setLayout(new BorderLayout());
			this.add(contentPane, BorderLayout.CENTER);
			
			inputPanel = new JPanel();
			//inputPanel.setBorder(new LineBorder(new Color(84, 173, 253 ), 2, true));
			inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
			contentPane.add(inputPanel, BorderLayout.CENTER);		
			
			JPanel horizontalPanel = new JPanel(new GridLayout());
			title = new JLabel("NUEVO CONTRATO", JLabel.CENTER);
			title.setFont(new Font("Montserrat Black", Font.BOLD, 46));
			horizontalPanel.add(title);		
			horizontalPanel.setPreferredSize(new Dimension(1920, 110));
			horizontalPanel.setMaximumSize(new Dimension(1920, 110));
			inputPanel.add(horizontalPanel);
			
			horizontalPanel = new JPanel(new GridLayout(0,3));
			
			horizontalPanel.add(new JLabel("Vehiculo", JLabel.RIGHT));
			vehicleTextField = new JTextField();
			horizontalPanel.add(vehicleTextField);
			horizontalPanel.add(new JLabel(""));
			
			horizontalPanel.add(new JLabel("Cliente", JLabel.RIGHT));
			clientComboBox = new JComboBox<Cliente>();
			horizontalPanel.add(clientComboBox);
			horizontalPanel.add(new JLabel(""));
			
			horizontalPanel.add(new JLabel("Desde/Hasta", JLabel.RIGHT));
			dp = new DatePicker();
			dp.setDateSelectionMode(DatePicker.DateSelectionMode.BETWEEN_DATE_SELECTED);
			dp.setUsePanelOption(true);  
			dp.setBackground(Color.GRAY); // Color de fondo oscuro
			dp.setDateFormat("dd/MM/yyyy");
			dp.addDateSelectionListener(new DateSelectionListener(){
				@Override
				public void dateSelected(DateSelectionEvent dateSelectionEvent) {
					if( ! vehicleTextField.getText().isBlank()) {
						VehiculoAlquilable vehicle = VehiculoDao.getVehiculoAlquilableByPlate(vehicleTextField.getText());
						LocalDate[] date = dp.getSelectedDateRange(); 
						long daysBetween = ChronoUnit.DAYS.between(date[0], date[1]);
						int price = (int) (vehicle.getPrice() * daysBetween);
						labelSuggestedAmount.setText("(Sugerido): " + String.valueOf(price));
					}
				}
			});
			dates = new JFormattedTextField();
			dp.setEditor(dates);
			horizontalPanel.add(dates);
			horizontalPanel.add(new JLabel(""));
			
			horizontalPanel.add(new JLabel("Kilometros Salida", JLabel.RIGHT));
			kilometersDepartureTextField = new JTextField();
			horizontalPanel.add(kilometersDepartureTextField);
			horizontalPanel.add(new JLabel(""));
			
			horizontalPanel.add(new JLabel("Kilometros Retorno", JLabel.RIGHT));
			kilometersReturnTextField = new JTextField();
			horizontalPanel.add(kilometersReturnTextField);
			horizontalPanel.add(new JLabel(""));
				
			String[] values = {"RESERVA", "1/4", "1/2", "3/4", "FULL"};
			
			horizontalPanel.add(new JLabel("Combustible Salida", JLabel.RIGHT));
			gasExitComboBox = new JComboBox<String>();
			gasExitComboBox.setModel(new DefaultComboBoxModel<String>(values));
			horizontalPanel.add(gasExitComboBox);
			horizontalPanel.add(new JLabel(""));
			
			horizontalPanel.add(new JLabel("Combustible Retorno", JLabel.RIGHT));
			gasReturnComboBox = new JComboBox<String>();
			gasReturnComboBox.setModel(new DefaultComboBoxModel<String>(values));
			horizontalPanel.add(gasReturnComboBox);
			horizontalPanel.add(new JLabel(""));

			horizontalPanel.add(new JLabel("Alquiler de Reservacion", JLabel.RIGHT));
			reservaRadioButton = new JRadioButton("");
			horizontalPanel.add(reservaRadioButton);
			horizontalPanel.add(new JLabel(""));
			
			horizontalPanel.add(new JLabel("Monto Total", JLabel.RIGHT));
			priceTextField = new JTextField(10);
			priceTextField.setBackground(Color.YELLOW);
			horizontalPanel.add(priceTextField);
			labelSuggestedAmount = new JLabel("", JLabel.LEFT);
			horizontalPanel.add(labelSuggestedAmount);

			inputPanel.add(horizontalPanel);		
			
			
//<-------------------------------------------------------SOUTH------------------------------------------------------------------------>

	        JPanel south = new JPanel();
	        south.setLayout(new BoxLayout(south, BoxLayout.Y_AXIS));
			
	        horizontalPanel = new JPanel(new GridLayout(0,4));
	        horizontalPanel.add(new JLabel(""));
			JButton confirm = new JButton("Confirmar");
			confirm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
						if(validateFields())
								insert();
				}});
	        confirm.setPreferredSize(new Dimension(250,80));
			JButton cancel = new JButton("Cancelar");
			cancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					clearFields();
				}
			});
			cancel.setPreferredSize(new Dimension(250,80));

			horizontalPanel.add(confirm);
			horizontalPanel.add(cancel);		
			south.add(horizontalPanel);		

			ViewUtils.setIconToButton(confirm, "/resources/imgs/confirmar.png", 32, 32);
			ViewUtils.setIconToButton(cancel, "/resources/imgs/escoba.png", 32, 32);

			
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

//<------------------------------------------------------------------------------------------------------------------------------->
			fillClients();
			clearFields();
		}	

		private void fillClients() {
			clientComboBox.addItem(new Cliente("Seleccione un Cliente", null, null, null, null));

			List<Cliente> clientes = ClienteDao.getClientes();
			for(Cliente s : clientes) {
				clientComboBox.addItem(s);
			}

		}

		private void insert() {
			VehiculoAlquilable vehicle;
			try {
				vehicle = VehiculoDao.getVehiculoAlquilableByPlate(vehicleTextField.getText());
			} catch (NoResultException p) {
				SwingUtilities.invokeLater(() -> setMessage("Vehiculo Inexistente", false));
				throw p;
			}
			try {
				LocalDate[] date = dp.getSelectedDateRange();
				LocalDate start = date[0];
				LocalDate end = date[1];
				int price = Integer.parseInt(priceTextField.getText());
				Cliente client = (Cliente) clientComboBox.getSelectedItem();
				int kmD = Integer.parseInt(kilometersDepartureTextField.getText());
				int kmR = Integer.parseInt(kilometersReturnTextField.getText());
				String cbE = (String) gasExitComboBox.getSelectedItem();
				String cbR = (String) gasReturnComboBox.getSelectedItem();
				Boolean isBooked = false;
				if(reservaRadioButton.isSelected())
					isBooked=true;
				
				Alquiler v = new Alquiler(start, end, client, vehicle, price, kmD, kmR, cbE, cbR, isBooked);
				if(id != null) { 
					v.setId(id);
					AlquilerDao.save(v);
					title.setText("NUEVO ALQUILER");			
					id = null;
					setMessage("Modificado correctamente", true);
				}else {
					AlquilerDao.save(v);
					setMessage("Insertado correctamente", true);
				}
				clearFields();

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
					if(vehicleTextField.getText().length() < 6 || vehicleTextField.getText().isBlank()) {
						setMessage("la Patente no puede tener menos de 6 caracteres", false);
						return false;
					}				
					if(clientComboBox.getSelectedIndex() == 0) {
						setMessage("Seleccione un Cliente valido", false);
						return false;
					}				
					if(!dp.isDateSelected()) {
						setMessage("Seleccione Fechas validas", false);
						return false;	
					}
					/*if(kilometersDepartureTextField.getText().isBlank() || kilometersReturnTextField.getText().isBlank()) {
						setMessage("KM no pueden estar Vacio", false);
						return false;
					}
					if(Integer.parseInt(kilometersDepartureTextField.getText()) > Integer.parseInt(kilometersReturnTextField.getText())) {
						setMessage("KM de retorno no puede ser menor a Salida", false);
						return false;
					}*/
			
			}catch (NumberFormatException e) {
				setMessage("Asegurese de que todos los campos tengan formato valido. (Campos NUMERICOS no pueden estar vacios)", false);
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
			kilometersDepartureTextField.setText("");
			clientComboBox.setSelectedIndex(0);
			priceTextField.setText("");
			dp.clearSelectedDate();
			kilometersDepartureTextField.setText("");
			kilometersReturnTextField.setText("");
			gasExitComboBox.setSelectedIndex(0);
			gasReturnComboBox.setSelectedIndex(0);
			reservaRadioButton.setSelected(false);
			
			labelSuggestedAmount.setText("");
			messageLabel.setText("");
	        messageLabel.setOpaque(false);
		}

		public void setUpdateForm(Alquiler alquiler) {
			title.setText("MODIFICANDO ALQUILER");
			id = alquiler.getId();
			
			vehicleTextField.setText(alquiler.getVehicle().getPlate());
			dp.setSelectedDateRange(alquiler.getStart(), alquiler.getEnd());
			clientComboBox.setSelectedItem(alquiler.getClient());
			priceTextField.setText(String.valueOf(alquiler.getTotalPrice()));
			kilometersDepartureTextField.setText(String.valueOf(alquiler.getDepartureKm()));
			kilometersReturnTextField.setText(String.valueOf(alquiler.getReturnKm()));
			gasExitComboBox.setSelectedItem(alquiler.getGasExit());
			gasReturnComboBox.setSelectedItem(alquiler.getGasReturn());
			reservaRadioButton.setSelected(alquiler.getIsBooked());

		}
		
		public JTextField getSearchTextField() {
			return searchTextField;
		}	
		public JTextField getVehicleTextField() {
			return vehicleTextField;
		}
		public JButton getSearchButton() {
			return searchButton;
		}
		public JTextField getKilometersDepartureTextField() {
			return kilometersDepartureTextField;
		}
}
