package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatClientProperties;

import entities.Cliente;
import entities.Marca;
import entities.Sucursal;
import entities.Vehiculo;
import entityManagers.ClienteDao;
import entityManagers.MarcaDao;
import entityManagers.SucursalDao;
import entityManagers.VehiculoDao;

import javax.swing.border.LineBorder;

public class AutosAlquilerForm extends JPanel{
	
private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JPanel inputPanel;
	private JPanel westPanel;
	private JPanel tablePanel;
	private JTable table;
	private DefaultTableModel tableModel;
	private JLabel messageLabel;
	private JTextField yearTextField;
	private JTextField patenteTextField;
	private JTextField modelTextField;
	private JTextField kilometersTextField;
	private JComboBox<Marca> brandComboBox;
	private JTextField ensuranceTextField;
	private JTextField priceTextField;
	private JComboBox<Sucursal> branchComboBox;
	private JTextField searchTextField;
	private JComboBox<Marca> filterBrandComboBox;
	

	public AutosAlquilerForm() {
							
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
			//inputPanel.setPreferredSize(new Dimension(150, HEIGHT));
			contentPane.add(inputPanel, BorderLayout.NORTH);
			

			JPanel horizontalPanel = new JPanel(new GridLayout());
			
			JLabel titulo = new JLabel("Vehiculos Alquiler", JLabel.CENTER);
			titulo.setFont(new Font("Montserrat Black", Font.BOLD, 46));
			horizontalPanel.add(titulo);		
			inputPanel.add(horizontalPanel);

			horizontalPanel = new JPanel(new GridLayout());
			horizontalPanel.setPreferredSize(new Dimension(WIDTH, 7));
			inputPanel.add(horizontalPanel);
			
			
			horizontalPanel = new JPanel(new GridLayout());
			horizontalPanel.add(new JLabel("Patente", JLabel.RIGHT));
			patenteTextField = new JTextField("",30);
			horizontalPanel.add(patenteTextField);
			horizontalPanel.add(new JLabel("Año", JLabel.RIGHT));
			yearTextField = new JTextField(10);	 
			horizontalPanel.add(yearTextField);
			horizontalPanel.add(new JLabel("tarifa alquiler", JLabel.RIGHT));
			priceTextField = new JTextField(10);
			horizontalPanel.add(priceTextField);
			horizontalPanel.add(new JLabel("", JLabel.RIGHT));
			inputPanel.add(horizontalPanel);		
			
			horizontalPanel = new JPanel(new GridLayout());
			horizontalPanel.add(new JLabel("Modelo", JLabel.RIGHT));
			modelTextField = new JTextField("",30);
			horizontalPanel.add(modelTextField);
			horizontalPanel.add(new JLabel("Kilometros", JLabel.RIGHT));
			kilometersTextField = new JTextField(10);
			horizontalPanel.add(kilometersTextField);
			horizontalPanel.add(new JLabel("Sucursal", JLabel.RIGHT));
			branchComboBox = new JComboBox<Sucursal>();
			horizontalPanel.add(branchComboBox);
			fillBranches();
			horizontalPanel.add(new JLabel("", JLabel.RIGHT));
			inputPanel.add(horizontalPanel);		
			
			horizontalPanel = new JPanel(new GridLayout());
			horizontalPanel.add(new JLabel("Marca", JLabel.RIGHT));
			brandComboBox = new JComboBox<Marca>();
			horizontalPanel.add(brandComboBox);
			horizontalPanel.add(new JLabel("Seguro", JLabel.RIGHT));
			ensuranceTextField = new JTextField(20);
			horizontalPanel.add(ensuranceTextField);
			horizontalPanel.add(new JLabel("", JLabel.RIGHT));
			inputPanel.add(horizontalPanel);		
			
			horizontalPanel = new JPanel(new FlowLayout());
			JButton confirm = new JButton("Confirmar");
			confirm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						if(validateFields())
							if(table.getSelectedRow() == -1)
								insert();
							else
								update();
						
					} catch (NumberFormatException e2) {
						setMessage("Asegurese de que todos los campos tengan formato valido.");
					}
			}
			});
	        confirm.setPreferredSize(new Dimension(250,40));
			JButton delete = new JButton("Eliminar");
			delete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(validateFields() && table.getSelectedRow() != -1)
						delete();
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
	        tableModel.addColumn("Patente");
	        tableModel.addColumn("Año");
	        tableModel.addColumn("Modelo");
	        tableModel.addColumn("Tarifa Alquiler");
	        tableModel.addColumn("KM");
	        tableModel.addColumn("Marca");
	        tableModel.addColumn("Seguro");
	        tableModel.addColumn("Sucursal");
	        tableModel.addColumn("Id");
	        
			table = new JTable(tableModel);
			table.getColumnModel().getColumn(8).setMaxWidth(0);
			table.getColumnModel().getColumn(8).setMinWidth(0);
			table.getColumnModel().getColumn(8).setPreferredWidth(0);
			table.setShowGrid(true);
			table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
	                if (!e.getValueIsAdjusting()) {
	                	int row = table.getSelectedRow();
	                	if(row != -1){
							String plate = (String) tableModel.getValueAt(row, 0);
							int year = (int) tableModel.getValueAt(row, 1);
							String model = (String) tableModel.getValueAt(row, 2);
							int price = (int) tableModel.getValueAt(row, 3);
							int km = (int) tableModel.getValueAt(row, 4);
							Marca m = (Marca) tableModel.getValueAt(row, 5);
							String ensurance = (String) tableModel.getValueAt(row, 6);
							Sucursal branch = (Sucursal) tableModel.getValueAt(row, 7);
							
							patenteTextField.setText(plate);
							yearTextField.setText(String.valueOf(year));
							modelTextField.setText(model);
							priceTextField.setText(String.valueOf(price));
							kilometersTextField.setText(String.valueOf(km));
							brandComboBox.setSelectedItem(m);
							ensuranceTextField.setText(ensurance);
							branchComboBox.setSelectedItem(branch);
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
			contentPane.add(south, BorderLayout.SOUTH);
			
			horizontalPanel = new JPanel(new GridLayout());
			horizontalPanel.add(new JLabel(""));
			searchTextField = new JTextField();
			searchTextField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter para buscar por patente");
			filterBrandComboBox = new JComboBox<Marca>();
			filterBrandComboBox.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter para buscar por patente");
			horizontalPanel.add(searchTextField);
			horizontalPanel.add(filterBrandComboBox);
			horizontalPanel.add(new JLabel(""));
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
			//contentPane.add(horizontalPanel, BorderLayout.SOUTH);
			
			
			loadTable();
			fillBrands();
			
			/*patenteTextField.setPreferredSize(new Dimension(WIDTH, 10));
			yearTextField.setPreferredSize(new Dimension(WIDTH, 10));
			modelTextField.setPreferredSize(new Dimension(WIDTH, 10));
			priceTextField.setPreferredSize(new Dimension(WIDTH, 10));
			kilometersTextField.setPreferredSize(new Dimension(WIDTH, 10));
			ensuranceTextField.setPreferredSize(new Dimension(WIDTH, 10));
			brandComboBox.setPreferredSize(new Dimension(WIDTH, 10));
			branchComboBox.setPreferredSize(new Dimension(WIDTH, 10));
			*/
			
		}
		
		private void fillBranches() {
			branchComboBox.addItem(new Sucursal("Seleccione una Sucursal"));

			List<Sucursal> sucursales = SucursalDao.getSucursales();
			for(Sucursal s : sucursales) {
				branchComboBox.addItem(s);
			}
		}

		private void fillBrands() {
			brandComboBox.addItem(new Marca("Seleccione una Marca"));
			filterBrandComboBox.addItem(new Marca("Filtre por Marca"));

			List<Marca> marcas = MarcaDao.getMarcas();
			for(Marca m : marcas) {
				filterBrandComboBox.addItem(m);
				brandComboBox.addItem(m);
			}
		}

		private void insert() {
			int year = Integer.parseInt(yearTextField.getText());
			int km = Integer.parseInt(kilometersTextField.getText());
			int price = Integer.parseInt(priceTextField.getText());
			String plate = patenteTextField.getText();
			String model = modelTextField.getText();
			String ensurance = ensuranceTextField.getText();
			Marca m = (Marca)brandComboBox.getSelectedItem();
			Sucursal s = (Sucursal)branchComboBox.getSelectedItem();			
			
			Vehiculo v = new Vehiculo(year, km, price, plate, model, ensurance, m, s);
			
			VehiculoDao.save(v);
			clearFields();
			loadTable();
		}

		private void update() {
			int year = Integer.parseInt(yearTextField.getText());
			int km = Integer.parseInt(kilometersTextField.getText());
			int price = Integer.parseInt(priceTextField.getText());
			String plate = patenteTextField.getText();
			String model = modelTextField.getText();
			String ensurance = ensuranceTextField.getText();
			Marca m = (Marca)brandComboBox.getSelectedItem();
			Sucursal s = (Sucursal)branchComboBox.getSelectedItem();
			
			long id = (long)tableModel.getValueAt(table.getSelectedRow(), 8);

			Vehiculo v = new Vehiculo(id, year, km, price, plate, model, ensurance, m, s);

			VehiculoDao.save(v);
			clearFields();
			loadTable();
		}
		
		private void delete() {
			long id = (long)tableModel.getValueAt(table.getSelectedRow(), 8);
			
			try {
				if(JOptionPane.showConfirmDialog(null, "Desea eliminar al vehiculo: "+VehiculoDao.getVehiculoById(id).getPlate(),  "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
					VehiculoDao.delete(id);
					clearFields();
					loadTable();
				}
			}catch(Exception e) {
				//JOptionPane.showMessageDialog(null, e.getCause(), "ERROR AL ELIMINAR", JOptionPane.ERROR_MESSAGE);
				setMessage(e.getCause().getMessage());
			}
			
		}
		
		private void loadTable() {
	        tableModel.setRowCount(0);

			List<Vehiculo> vehiculos = VehiculoDao.getVehiculos();
			for(Vehiculo v : vehiculos) {
				Object[] row = {v.getPlate(), v.getYear(), v.getModel(), v.getPrice(), v.getKilometers(), v.getBrand(), v.getEnsurance(), v.getBranch(), v.getId()};
				tableModel.addRow(row);
			}

		}

		private boolean validateFields() {

					if(patenteTextField.getText().length() < 6 || patenteTextField.getText().isBlank()) {
						setMessage("la Patente no puede tener menos de 6 caracteres");
						return false;
					}				
					if(yearTextField.getText().isBlank() || Integer.parseInt(yearTextField.getText()) < 1950) {
						setMessage("el Año no puede ser menor a 1950");
						return false;	
					}					
					if(brandComboBox.getSelectedIndex() == 0) {
						setMessage("Seleccione una Marca valida");
						return false;
					}				
					if(branchComboBox.getSelectedIndex() == 0) {
						setMessage("Seleccione una Sucursal valida");
						return false;
					}

			return true;	
		}

		private void setMessage(String message) {
			messageLabel.setText(message);
	        messageLabel.setOpaque(true);

		}

		private void clearFields() {
			yearTextField.setText("");
			patenteTextField.setText("");
			modelTextField.setText("");
			kilometersTextField.setText("");
			brandComboBox.setSelectedIndex(0);
			branchComboBox.setSelectedIndex(0);
			ensuranceTextField.setText("");
			priceTextField.setText("");
			
			messageLabel.setText("");
	        messageLabel.setOpaque(false);
	        table.clearSelection();
		}

}
