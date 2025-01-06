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

public class GastosForm extends JPanel{

	private JPanel contentPane;
	private JPanel inputPanel;
	private JPanel west;
	private JPanel tablePanel;
	private JTable table;
	private DefaultTableModel tableModel;
	private JLabel messageLabel;
	
	private JTextField vehicleTextField;
	private JTextField descriptionTextField;
	private JTextField paymentTextField;
	private JTextField amountTextField;
	private JFormattedTextField date;
	private JComboBox<Sucursal> branchComboBox;
	private JComboBox<Destino> destinationComboBox;

	private JTextField searchTextField;
	private JFormattedTextField filterDatesField;
	private DatePicker dpFilters;
	private DatePicker dp;
	private JButton searchButton;
	
	private AlquilerDao AlquilerDao;
	private VehiculoDao VehiculoDao;
	private SucursalDao SucursalDao;
	private GastoDao GastoDao;
	private DestinoDao DestinoDao;
	private JComboBox<Sucursal> filterBranchComboBox;
	private JLabel totalLabel;
	
	public GastosForm(AlquilerDao alquilerDao, VehiculoDao vehiculoDao, SucursalDao SucursalDao, GastoDao GastoDao, DestinoDao DestinoDao) {
			this.AlquilerDao= alquilerDao;	
			this.VehiculoDao = vehiculoDao;
			this.SucursalDao = SucursalDao;
			this.GastoDao = GastoDao;
			this.DestinoDao = DestinoDao;
			
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.setLayout(new BorderLayout(0, 5));
			contentPane.setPreferredSize(new Dimension(1100, 750));
			this.setSize(1100, 850);
			this.setLayout(new BorderLayout());
			this.add(contentPane, BorderLayout.CENTER);
			
			inputPanel = new JPanel();
			inputPanel.setBorder(new LineBorder(new Color(84, 173, 253 ), 2, true));
			inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
			contentPane.add(inputPanel, BorderLayout.NORTH);		

			JPanel horizontalPanel = new JPanel(new GridLayout());
			
			JLabel titulo = new JLabel("GASTOS", JLabel.CENTER);
			titulo.setFont(new Font("Montserrat Black", Font.BOLD, 46));
			horizontalPanel.add(titulo);		
			inputPanel.add(horizontalPanel);

			horizontalPanel = new JPanel(new GridLayout());
			horizontalPanel.setPreferredSize(new Dimension(WIDTH, 9));
			inputPanel.add(horizontalPanel);		
			
			horizontalPanel = new JPanel(new GridLayout());
			horizontalPanel.add(new JLabel("Vehiculo", JLabel.RIGHT));
			vehicleTextField = new JTextField();
			horizontalPanel.add(vehicleTextField);
			horizontalPanel.add(new JLabel("Importe", JLabel.RIGHT));
			amountTextField = new JTextField(10);
			horizontalPanel.add(amountTextField);
			horizontalPanel.add(new JLabel("Descripcion", JLabel.RIGHT));
			descriptionTextField = new JTextField();
			horizontalPanel.add(descriptionTextField);
			horizontalPanel.add(new JLabel("", JLabel.RIGHT));

			inputPanel.add(horizontalPanel);		
			
			horizontalPanel = new JPanel(new GridLayout());
			horizontalPanel.add(new JLabel("Destino", JLabel.RIGHT));
			destinationComboBox = new JComboBox<Destino>();
			horizontalPanel.add(destinationComboBox);
			horizontalPanel.add(new JLabel("Forma de Pago", JLabel.RIGHT));
			paymentTextField = new JTextField();
			horizontalPanel.add(paymentTextField);

			horizontalPanel.add(new JLabel("", JLabel.RIGHT));
			inputPanel.add(horizontalPanel);		
			
			horizontalPanel = new JPanel(new GridLayout());
			horizontalPanel.add(new JLabel("Consecionaria", JLabel.RIGHT));
			branchComboBox = new JComboBox<Sucursal>();
			horizontalPanel.add(branchComboBox);
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
			inputPanel.add(horizontalPanel);		
			
			horizontalPanel = new JPanel(new FlowLayout());
			JButton confirm = new JButton("Confirmar");
			confirm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

						if(validateFields())
								insert();				

				}});
	        confirm.setPreferredSize(new Dimension(250,40));
			JButton delete = new JButton("Eliminar");
			delete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(table.getSelectedRow() != -1)
						delete();
					else setMessage("Seleccione un Vehiculo para eliminar", false);
				}
			});
			delete.setPreferredSize(new Dimension(250,40));
			JButton cancel = new JButton("Cancelar");
			cancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					clearFields();
				}
			});
			cancel.setPreferredSize(new Dimension(250,40));

			horizontalPanel.add(confirm);
			horizontalPanel.add(delete);
			horizontalPanel.add(cancel);		
			inputPanel.add(horizontalPanel);		
			
			
			tableModel = new DefaultTableModel(){
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false; // Hacer que todas las celdas sean no editables
	            }
	        };
	        tableModel.addColumn("Vehiculo");
	        tableModel.addColumn("Fecha");
	        tableModel.addColumn("Descripcion");
	        tableModel.addColumn("Importe");
	        tableModel.addColumn("Destino");
	        tableModel.addColumn("Forma de Pago");
	        tableModel.addColumn("Consecionaria");
	        tableModel.addColumn("Id");
	        
			table = new JTable(tableModel);
			table.getColumnModel().getColumn(7).setMaxWidth(0);
			table.getColumnModel().getColumn(7).setMinWidth(0);
			table.getColumnModel().getColumn(7).setPreferredWidth(0);
			table.getColumnModel().getColumn(3).setPreferredWidth(80);
			table.getColumnModel().getColumn(4).setPreferredWidth(100);
			table.setShowGrid(true);
			table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
	                if (!e.getValueIsAdjusting()) {
	                	int row = table.getSelectedRow();
	                	if(row != -1){
	                		VehiculoAlquilable vehicle = (VehiculoAlquilable) tableModel.getValueAt(row, 0);
							LocalDate date = (LocalDate) tableModel.getValueAt(row, 1);
							String description = (String) tableModel.getValueAt(row, 2);
							int amount = (int) tableModel.getValueAt(row, 3);
							Destino destination = (Destino) tableModel.getValueAt(row, 4);
							String pay = (String) tableModel.getValueAt(row, 5);
							Sucursal branch = (Sucursal) tableModel.getValueAt(row, 6);
							
							if(vehicle != null)
								vehicleTextField.setText(vehicle.getPlate());
							dp.setSelectedDate(date);
							descriptionTextField.setText(description);
							amountTextField.setText(String.valueOf(amount));
							paymentTextField.setText(String.valueOf(pay));
							branchComboBox.setSelectedItem(branch);
							destinationComboBox.setSelectedItem(destination);
	                	}
	                }
				}
				
			});
			
			tablePanel = new JPanel();
			tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
			contentPane.add(tablePanel, BorderLayout.CENTER);
			
			JScrollPane scroll = new JScrollPane(table);
			
			horizontalPanel = new JPanel(new GridLayout());
			horizontalPanel.add(scroll);
			tablePanel.add(horizontalPanel);			
			
	        JPanel south = new JPanel();
	        south.setLayout(new BoxLayout(south, BoxLayout.Y_AXIS));

			horizontalPanel = new JPanel(new GridLayout());
			totalLabel = new JLabel("Total: $", JLabel.CENTER);
			totalLabel.setFont(new Font("Montserrat", Font.BOLD, 23));
			horizontalPanel.add(totalLabel);
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

			west = new JPanel();
			west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));
			west.setBorder(new TitledBorder(new LineBorder(Color.black, 2), "Buscar/Filtrar", TitledBorder.CENTER, TitledBorder.TOP, null, Color.BLACK));
			
			JPanel vert = new JPanel(new GridLayout(0,1));
			vert.add(new JLabel("Patente"));
			searchTextField = new JTextField();
			searchTextField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Presione Enter para buscar");
			searchTextField.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);
			vert.add(searchTextField);
			vert.add(new JLabel("Fechas"));
			dpFilters = new DatePicker();
			dpFilters.setDateSelectionMode(DatePicker.DateSelectionMode.BETWEEN_DATE_SELECTED);
			dpFilters.setUsePanelOption(true);
			dpFilters.setBackground(Color.GRAY);
			dpFilters.setDateFormat("yyyy/MM/dd");
			filterDatesField = new JFormattedTextField();
			dpFilters.setEditor(filterDatesField);
			vert.add(filterDatesField);
			vert.add(new JLabel("Consecionaria", JLabel.LEFT));
			filterBranchComboBox = new JComboBox<Sucursal>();
			vert.add(filterBranchComboBox);
			vert.add(new JLabel(""));
			searchButton =new JButton("Buscar");
			searchButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					loadTable(searchTextField.getText(), dpFilters.getSelectedDateRange(), (Sucursal)filterBranchComboBox.getSelectedItem());
				}
			});
			vert.add(searchButton);
			searchTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER)
						searchButton.doClick();
				}
			});
			west.add(vert);
			vert.setPreferredSize(new Dimension(200, HEIGHT));
			contentPane.add(west, BorderLayout.WEST);

			fillBranchs();
			fillDestinations();
			clearFields();
			searchButton.doClick();
		}	
	
		private void fillBranchs() {
			branchComboBox.addItem(new Sucursal("Seleccione una Consecionaria"));
			filterBranchComboBox.addItem(new Sucursal("Seleccione una Consecionaria"));

			List<Sucursal> sucursales = SucursalDao.getSucursales();
			for(Sucursal s : sucursales) {
				branchComboBox.addItem(s);
				filterBranchComboBox.addItem(s);
				
			}
		}

		private void fillDestinations() {
			destinationComboBox.addItem(new Destino("Seleccione un Destino"));

			List<Destino> destinos = DestinoDao.getDestinos();
			for(Destino s : destinos) {
				destinationComboBox.addItem(s);
			}
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
				if(table.getSelectedRow() != -1) {
					Long id = (Long) tableModel.getValueAt(table.getSelectedRow(), 7);
					g.setId(id);
				}
				GastoDao.save(g);
				clearFields();
				searchButton.doClick();
				setMessage("Insertado correctamente", true);

			} catch (NumberFormatException e2) {
				setMessage("Asegurese de que todos los campos tengan formato valido. (Campos NUMERICOS no pueden estar vacios)", false);
			} catch (PersistenceException e5) {
				setMessage("ya existe un Vehiculo con esa Patente", false);
			} catch (Exception e3) {
				setMessage("Ha ocurrido un Error:" + e3.getLocalizedMessage(), false);
			}

		}
		
		private void delete() {
			long id = (long)tableModel.getValueAt(table.getSelectedRow(), 7);
			
			try {
				if(JOptionPane.showConfirmDialog(null, "Desea eliminar el alquiler: "+id ,"", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
					AlquilerDao.delete(id);
					clearFields();
					searchButton.doClick();
					setMessage("Eliminado correctamente", true);
				}
			}catch(Exception e) {
				setMessage(e.getCause().getMessage(), false);
			}
			
		}
		
		private void loadTable(String plate, LocalDate[] dates, Sucursal branch) {
			tableModel.setRowCount(0);
			long total =0;
	        
			List<Gasto> gastos = GastoDao.getGastosByFilters(plate.toLowerCase(), dates, branch);			
			for(Gasto a : gastos) {
				Object[] row = {a.getVehicle(), a.getDate(), a.getDescription(), a.getAmount(), a.getDestination(), a.getPayment(), a.getBranch(), a.getId()};
				tableModel.addRow(row);
				total += a.getAmount();
			}
	        totalLabel.setText("Total: $"+ total);
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
					
					/*if(Integer.parseInt(kilometersDepartureTextField.getText()) > Integer.parseInt(kilometersReturnTextField.getText())) {
						setMessage("KM de retorno no puede ser menor a Salida", false);
						return false;
					}*/
			
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
			/*searchTextField.setText("");
			dpFilters.clearSelectedDate();*/
			
			totalLabel.setText("Total: $");
			messageLabel.setText("");
	        messageLabel.setOpaque(false);
	        table.clearSelection();
		}

		public JTextField getSearchTextField() {
			return searchTextField;
		}

		public JButton getSearchButton() {
			return searchButton;
		}
	
}
