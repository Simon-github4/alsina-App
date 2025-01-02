package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
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
import javax.swing.text.DateFormatter;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;

import com.formdev.flatlaf.FlatClientProperties;

import entities.Alquiler;
import entities.Cliente;
import entities.Marca;
import entities.Sucursal;
import entities.Vehiculo;
import entities.VehiculoAlquilable;
import entityManagers.AlquilerDao;
import entityManagers.ClienteDao;
import entityManagers.VehiculoDao;
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
	
	private JTextField searchTextField;
	private JFormattedTextField filterDatesField;
	private DatePicker dpFilters;
	private DatePicker dp;
	private JButton searchButton;
	
	private AlquilerDao AlquilerDao;
	private VehiculoDao VehiculoDao;
	private ClienteDao ClienteDao;

	public AlquileresForm(AlquilerDao alquilerDao, VehiculoDao vehiculoDao, ClienteDao clienteDao) {
			this.AlquilerDao= alquilerDao;	
			this.VehiculoDao = vehiculoDao;
			this.ClienteDao = clienteDao;
			
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
			
			JLabel titulo = new JLabel("Alquileres", JLabel.CENTER);
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
			horizontalPanel.add(new JLabel("Monto Total", JLabel.RIGHT));
			priceTextField = new JTextField(10);
			priceTextField.setEditable(false);
			horizontalPanel.add(priceTextField);
			horizontalPanel.add(new JLabel("", JLabel.RIGHT));
			inputPanel.add(horizontalPanel);		
			
			horizontalPanel = new JPanel(new GridLayout());
			horizontalPanel.add(new JLabel("Kilometros Salida", JLabel.RIGHT));
			kilometersDepartureTextField = new JTextField();
			horizontalPanel.add(kilometersDepartureTextField);
			horizontalPanel.add(new JLabel("Kilometros Retorno", JLabel.RIGHT));
			kilometersReturnTextField = new JTextField();
			horizontalPanel.add(kilometersReturnTextField);

			horizontalPanel.add(new JLabel("", JLabel.RIGHT));
			inputPanel.add(horizontalPanel);		
			
			horizontalPanel = new JPanel(new GridLayout());
			horizontalPanel.add(new JLabel("Cliente", JLabel.RIGHT));
			clientComboBox = new JComboBox<Cliente>();
			horizontalPanel.add(clientComboBox);
			horizontalPanel.add(new JLabel("Desde/Hasta", JLabel.RIGHT));
			dp = new DatePicker();
			dp.setDateSelectionMode(DatePicker.DateSelectionMode.BETWEEN_DATE_SELECTED);
			dp.setUsePanelOption(true);  
			dates = new JFormattedTextField();
			dp.setBackground(Color.GRAY); // Color de fondo oscuro
			dp.setForeground(Color.WHITE); // Color de texto claro
			dp.setEditor(dates);
			horizontalPanel.add(dates);
			horizontalPanel.add(new JLabel("", JLabel.RIGHT));
			inputPanel.add(horizontalPanel);		
			
			horizontalPanel = new JPanel(new FlowLayout());
			JButton confirm = new JButton("Confirmar");
			confirm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

						if(validateFields())
							if(table.getSelectedRow() == -1)
								insert();
							else
								update();					

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
	        tableModel.addColumn("Inicio");
	        tableModel.addColumn("Fin");
	        tableModel.addColumn("Cliente");
	        tableModel.addColumn("Monto Total");
	        tableModel.addColumn("KM Salida");
	        tableModel.addColumn("KM Retorno");
	        tableModel.addColumn("Id");
	        
			table = new JTable(tableModel);
			table.getColumnModel().getColumn(7).setMaxWidth(0);
			table.getColumnModel().getColumn(7).setMinWidth(0);
			table.getColumnModel().getColumn(7).setPreferredWidth(0);
			table.getColumnModel().getColumn(3).setPreferredWidth(190);
			table.getColumnModel().getColumn(4).setPreferredWidth(100);
			table.setShowGrid(true);
			table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
	                if (!e.getValueIsAdjusting()) {
	                	int row = table.getSelectedRow();
	                	if(row != -1){
	                		VehiculoAlquilable vehicle = (VehiculoAlquilable) tableModel.getValueAt(row, 0);
							LocalDate start = (LocalDate) tableModel.getValueAt(row, 1);
							LocalDate end = (LocalDate) tableModel.getValueAt(row, 2);
							Cliente client = (Cliente) tableModel.getValueAt(row, 3);
							int price = (int) tableModel.getValueAt(row, 4);
							int kmD = (int) tableModel.getValueAt(row, 5);
							int kmR = (int) tableModel.getValueAt(row, 6);
							
							vehicleTextField.setText(vehicle.getPlate());
							dp.setSelectedDateRange(start, end);
							clientComboBox.setSelectedItem(client);
							priceTextField.setText(String.valueOf(price));
							kilometersDepartureTextField.setText(String.valueOf(kmD));
							kilometersReturnTextField.setText(String.valueOf(kmR));
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
			
			JPanel vert = new JPanel(new GridLayout(0,1));
			vert.add(new JLabel(""));
			vert.add(new JLabel("Patente"));
			searchTextField = new JTextField();
			searchTextField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Presione Enter para buscar");
			searchTextField.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);
			vert.add(searchTextField);
			vert.add(new JLabel("Fechas"));
			dpFilters = new DatePicker();
			dpFilters.setDateSelectionMode(DatePicker.DateSelectionMode.BETWEEN_DATE_SELECTED);
			dpFilters.setUsePanelOption(true);
			filterDatesField = new JFormattedTextField();
			dpFilters.setEditor(filterDatesField);
			vert.add(filterDatesField);
			searchTextField.setPreferredSize(new Dimension(180, 80));
			filterDatesField.setPreferredSize(new Dimension(180, 80));
			vert.setBorder(new TitledBorder(new LineBorder(Color.black, 2), "Buscar/Filtrar", TitledBorder.CENTER, TitledBorder.TOP, null, Color.BLACK));
			vert.add(new JLabel(""));
			searchButton =new JButton("Buscar");
			searchButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					loadTable(searchTextField.getText(), dpFilters.getSelectedDateRange());
				}
			});
			searchButton.setPreferredSize(new Dimension(180, 160));
			vert.add(searchButton);
			searchTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER)
						searchButton.doClick();
				}
			});
			JButton excelButton = new JButton("Imprimir Contrato");
			excelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(table.getSelectedRow()==-1)
						setMessage("Ningun Alquiler seleccionado", false);
					else {
						try {
							int row =table.getSelectedRow();
							VehiculoAlquilable vehicle = (VehiculoAlquilable) tableModel.getValueAt(row, 0);
							LocalDate start = (LocalDate) tableModel.getValueAt(row, 1);
							LocalDate end = (LocalDate) tableModel.getValueAt(row, 2);
							Cliente client = (Cliente) tableModel.getValueAt(row, 3);
							int price = (int) tableModel.getValueAt(row, 4);
							int kmD = (int) tableModel.getValueAt(row, 5);
							int kmR = (int) tableModel.getValueAt(row, 6);
							
							Alquiler alquiler = new Alquiler(start, end, client, vehicle, price, kmD, kmR);
							alquiler.openExcelPrint();
						} catch (FileNotFoundException ex) {
							setMessage(ex.getMessage(), false);
							ex.printStackTrace();
						} catch (IOException ex) {
							setMessage(ex.getMessage(), false);
							ex.printStackTrace();
						}	
					}
				}
			});
			excelButton.setPreferredSize(new Dimension(180, 160));
			vert.add(excelButton);
			vert.add(new JLabel(""));
			vert.add(new JLabel(""));
			west.add(vert);
			contentPane.add(west, BorderLayout.WEST);

			fillClients();
			clearFields();
			searchButton.doClick();
		}	
	
		private void fillClients() {
			clientComboBox.addItem(new Cliente( "Seleccione un Cliente", null, null));

			List<Cliente> clientes = ClienteDao.getClientes();
			for(Cliente s : clientes) {
				clientComboBox.addItem(s);
			}
		}

		private void insert() {
			VehiculoAlquilable vehicle;
			LocalDate[] date = dp.getSelectedDateRange();
	        long daysBetween = ChronoUnit.DAYS.between(date[0], date[1]);

			try {
				vehicle = VehiculoDao.getVehiculoAlquilableByPlate(vehicleTextField.getText());
			} catch (NoResultException p) {
				SwingUtilities.invokeLater(() -> setMessage("Vehiculo Inexistente", false));
				throw p;
			}

			try {
				LocalDate start = date[0];
				LocalDate end = date[1];
				Cliente client = (Cliente) clientComboBox.getSelectedItem();
				int price = (int) (vehicle.getPrice() * daysBetween);
				int kmD = Integer.parseInt(kilometersDepartureTextField.getText());
				int kmR = Integer.parseInt(kilometersReturnTextField.getText());
				
				Alquiler v = new Alquiler(start, end, client, vehicle, price, kmD, kmR);
				AlquilerDao.save(v);
				clearFields();
				searchButton.doClick();
				setMessage("Insertado correctamente", true);

			} catch (NumberFormatException e2) {
				setMessage("Asegurese de que todos los campos tengan formato valido.", false);
			} catch (PersistenceException e5) {
				setMessage("ya existe un Vehiculo con esa Patente", false);
			} catch (Exception e3) {
				setMessage("Ha ocurrido un Error:" + e3.getLocalizedMessage(), false);
			}

		}

		private void update() {
			Long id = (Long) tableModel.getValueAt(table.getSelectedRow(), 7);

			VehiculoAlquilable vehicle;		
			try {
				vehicle = VehiculoDao.getVehiculoAlquilableByPlate(vehicleTextField.getText());
			} catch (NoResultException p) {
				SwingUtilities.invokeLater(() -> setMessage("Vehiculo Inexistente", false));
				throw p;
			}

			try {
				LocalDate[] date = dp.getSelectedDateRange();
				long daysBetween = ChronoUnit.DAYS.between(date[0], date[1]);
				int price = (int) (vehicle.getPrice() * daysBetween);
				LocalDate start = date[0];
				LocalDate end = date[1];
				Cliente client = (Cliente) clientComboBox.getSelectedItem();
				int kmD = Integer.parseInt(kilometersDepartureTextField.getText());
				int kmR = Integer.parseInt(kilometersReturnTextField.getText());
		
				Alquiler v = new Alquiler(id, start, end, client, vehicle, price, kmD, kmR);
				AlquilerDao.save(v);
				clearFields();
				searchButton.doClick();
				setMessage("Actualizado correctamente", true);

			} catch (NumberFormatException e2) {
				setMessage("Asegurese de que todos los campos tengan formato valido.", false);
			} catch (Exception e3) {
				setMessage("Ha ocurrido un Error:" + e3.getLocalizedMessage(), false);
			}
		}
		
		private void delete() {
			long id = (long)tableModel.getValueAt(table.getSelectedRow(), 5);
			
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
		
		private void loadTable(String plate, LocalDate[] dates) {
	        tableModel.setRowCount(0);

			List<Alquiler> alquileres = AlquilerDao.getAlquileresByPlateAndDate(plate, dates);
			for(Alquiler a : alquileres) {
				Object[] row = {a.getVehicle(), a.getStart(), a.getEnd(), a.getClient(), a.getTotalPrice(), a.getDepartureKm(), a.getReturnKm(), a.getId()};
				tableModel.addRow(row);
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
					if(kilometersDepartureTextField.getText().isBlank() || kilometersReturnTextField.getText().isBlank()) {
						setMessage("KM no pueden estar Vacio", false);
						return false;
					}
					if(Integer.parseInt(kilometersDepartureTextField.getText()) < 0 || Integer.parseInt(kilometersReturnTextField.getText())<0) {
						setMessage("KM no pueden ser menor a 0", false);
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
			kilometersDepartureTextField.setText("");
			clientComboBox.setSelectedIndex(0);
			priceTextField.setText("");
			dp.clearSelectedDate();
			kilometersDepartureTextField.setText("0");
			kilometersReturnTextField.setText("0");
			/*searchTextField.setText("");
			dpFilters.clearSelectedDate();*/

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
