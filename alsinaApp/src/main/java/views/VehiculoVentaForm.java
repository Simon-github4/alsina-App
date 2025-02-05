package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
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

import entities.Compra;
import entities.Marca;
import entities.Sucursal;
import entities.Vehiculo;
import entities.VehiculoVenta;
import entityManagers.MarcaDao;
import entityManagers.SucursalDao;
import entityManagers.TransaccionDao;
import entityManagers.VehiculoDao;
import jakarta.persistence.PersistenceException;
import utils.ViewUtils;
import views.temporalFrames.VehiculoVentaResumenFrame;

public class VehiculoVentaForm extends JPanel{
	
private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JPanel inputPanel;
	private JPanel west;
	private JPanel tablePanel;
	private JTable table;
	private DefaultTableModel tableModel;
	private JLabel messageLabel;
	private JTextField yearTextField;
	private JTextField patenteTextField;
	private JTextField modelTextField;
	private JTextField kilometersTextField;
	private JComboBox<Marca> brandComboBox;
	private JComboBox<Sucursal> branchComboBox;
	private JTextField searchTextField;
	private JComboBox<Marca> filterBrandComboBox;
	private VehiculoDao VehiculoDao;
	private SucursalDao SucursalDao;
	private MarcaDao MarcaDao;
	private TransaccionDao transaccionDao;
	private JButton searchButton;

	private JTextField sellingPriceTextField;
	
	//@SuppressWarnings("serial")
	public VehiculoVentaForm(VehiculoDao vehiculoDao, SucursalDao sucursalDao, MarcaDao marcaDao, TransaccionDao tDao) {
			this.VehiculoDao = vehiculoDao;
			this.SucursalDao = sucursalDao;
			this.MarcaDao = marcaDao;
			this.transaccionDao = tDao;
			
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.setLayout(new BorderLayout(0, 5));
			contentPane.setPreferredSize(new Dimension(1050, 670));
			this.setSize(1100, 850);
			this.setLayout(new BorderLayout());
			this.add(contentPane, BorderLayout.CENTER);
			
			inputPanel = new JPanel();
			inputPanel.setBorder(new LineBorder(new Color(84, 173, 253 ), 2, true));
			inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
			//inputPanel.setPreferredSize(new Dimension(150, HEIGHT));
			contentPane.add(inputPanel, BorderLayout.NORTH);
			

			JPanel horizontalPanel = new JPanel(new GridLayout());
			
			JLabel titulo = new JLabel("VEHICULOS DE VENTA", JLabel.CENTER);
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
			horizontalPanel.add(new JLabel("Marca", JLabel.RIGHT));
			brandComboBox = new JComboBox<Marca>();
			horizontalPanel.add(brandComboBox);
			horizontalPanel.add(new JLabel("", JLabel.RIGHT));
			inputPanel.add(horizontalPanel);		
			
			horizontalPanel = new JPanel(new GridLayout());
			horizontalPanel.add(new JLabel("Modelo", JLabel.RIGHT));
			modelTextField = new JTextField("",30);
			horizontalPanel.add(modelTextField);
			horizontalPanel.add(new JLabel("Kilometros", JLabel.RIGHT));
			kilometersTextField = new JTextField(10);
			horizontalPanel.add(kilometersTextField);
			horizontalPanel.add(new JLabel("Concesionaria", JLabel.RIGHT));
			branchComboBox = new JComboBox<Sucursal>();
			horizontalPanel.add(branchComboBox);
			horizontalPanel.add(new JLabel("", JLabel.RIGHT));
			inputPanel.add(horizontalPanel);		
			
			horizontalPanel = new JPanel(new GridLayout());
			horizontalPanel.add(new JLabel("Precio de venta", JLabel.RIGHT));
			sellingPriceTextField = new JTextField();
			horizontalPanel.add(sellingPriceTextField);
			/*horizontalPanel.add(new JLabel("Seguro", JLabel.RIGHT));
			ensuranceTextField = new JTextField(20);
			horizontalPanel.add(ensuranceTextField);*/
			horizontalPanel.add(new JLabel("", JLabel.RIGHT));
			horizontalPanel.add(new JLabel("", JLabel.RIGHT));
			horizontalPanel.add(new JLabel("", JLabel.RIGHT));
			horizontalPanel.add(new JLabel("", JLabel.RIGHT));
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
	        tableModel.addColumn("Id");
	        tableModel.addColumn("Patente");
	        tableModel.addColumn("Marca");
	        tableModel.addColumn("Modelo");
	        tableModel.addColumn("Año");
	        tableModel.addColumn("KM");
	        tableModel.addColumn("Precio Venta");
	        tableModel.addColumn("Precio Compra");
	        //tableModel.addColumn("Seguro");
	        tableModel.addColumn("Consecionaria");
	        
			table = new JTable(tableModel);
			table.getColumnModel().getColumn(0).setMaxWidth(0);
			table.getColumnModel().getColumn(0).setMinWidth(0);
			table.getColumnModel().getColumn(0).setPreferredWidth(0);
			table.setShowGrid(true);
			table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
	                if (!e.getValueIsAdjusting()) {
	                	int row = table.getSelectedRow();
	                	if(row != -1){
							String plate = (String) tableModel.getValueAt(row, 1);
							Marca m = (Marca) tableModel.getValueAt(row, 2);
							String model = (String) tableModel.getValueAt(row, 3);
							int year = (int) tableModel.getValueAt(row, 4);
							int km = (int) tableModel.getValueAt(row, 5);
							Long sellPrice = (Long) tableModel.getValueAt(row, 6);
							Sucursal branch = (Sucursal) tableModel.getValueAt(row, 8);
							
							patenteTextField.setText(plate);
							yearTextField.setText(String.valueOf(year));
							modelTextField.setText(model);
							sellingPriceTextField.setText(String.valueOf(sellPrice));
							kilometersTextField.setText(String.valueOf(km));
							brandComboBox.setSelectedItem(m);
							//ensuranceTextField.setText(ensurance);
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

			//west = new JPanel();
			//west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));
			
			JPanel vert = new JPanel(new GridLayout(9,0));

			vert.add(new JLabel("Patente"));
			searchTextField = new JTextField();
			//searchTextField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(opcional)");
			searchTextField.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);
			filterBrandComboBox = new JComboBox<Marca>();
			vert.add(searchTextField);
			vert.add(new JLabel("Marca"));
			vert.add(filterBrandComboBox);
			searchTextField.setPreferredSize(new Dimension(180, 80));
			filterBrandComboBox.setPreferredSize(new Dimension(180, 80));
			vert.setBorder(new TitledBorder(new LineBorder(Color.black, 2), "Buscar/Filtrar", TitledBorder.CENTER, TitledBorder.TOP, null, Color.BLACK));
			searchButton =new JButton("Buscar");
			searchButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					loadTable(searchTextField.getText(), (Marca) filterBrandComboBox.getSelectedItem());
					clearFields();
				}
			});
			searchButton.setPreferredSize(new Dimension(180, 160));
			vert.add(searchButton);

			searchTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					SwingUtilities.invokeLater(()-> searchButton.doClick());
				}
			});
			JButton newSaleButton =new JButton("Nueva Venta");
			newSaleButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(table.getSelectedRow()==-1)
						setMessage("Ningun Vehiculo seleccionado", false);
					else { 
						JTabbedPane t = getTabbedPane();
						VentaFrame v =(VentaFrame) t.getParent().getParent().getParent().getParent();
						Long id = (Long) tableModel.getValueAt(table.getSelectedRow(), 0);
						v.openNuevaVenta(VehiculoDao.getVehiculoById(id), null, "VENTA");
					}
				}
			});
			vert.add(newSaleButton);
			JButton newPurchaseButton =new JButton("Nueva Compra");
			newPurchaseButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(table.getSelectedRow()==-1)
						setMessage("Ningun Vehiculo seleccionado", false);
					else { 
						JTabbedPane t = getTabbedPane();
						VentaFrame v =(VentaFrame) t.getParent().getParent().getParent().getParent();
						Long id = (Long) tableModel.getValueAt(table.getSelectedRow(), 0);
						v.openNuevaVenta(VehiculoDao.getVehiculoById(id), null, "COMPRA");
					}
				}
			});
			vert.add(newPurchaseButton);
			
			JButton gastosButton = new JButton("Ver Resumen");
			gastosButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String plate;
					if(table.getSelectedRow() == -1)
						plate = JOptionPane.showInputDialog("Seleccione un Vehiculo de la grilla o \n Ingrese la Patente manualmente:");
					else { 
						plate = (String) tableModel.getValueAt(table.getSelectedRow(), 1);
						//Dashboard.getInstance().verGastosByPlate(plate);
					}
					Vehiculo v = vehiculoDao.getVehiculoAndDeletedByPlate(plate);
					if(v != null)
						new VehiculoVentaResumenFrame(transaccionDao, Dashboard.getGastoDao(), v).setVisible(true);
					else
						setMessage("Vehiculo no encontrado", false);
				}});
			vert.add(gastosButton);
			JButton NewGastoButton = new JButton("Agregar Gasto");
			NewGastoButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String plate;
					if(table.getSelectedRow() == -1)
						plate = JOptionPane.showInputDialog("Seleccione un Vehiculo de la grilla o \n Ingrese la Patente manualmente:");
					else { 
						plate = (String)tableModel.getValueAt(table.getSelectedRow(), 1);
						Dashboard.getInstance().nuevoGastoByPlate(plate);
						JFrame frame = new JFrame();
						frame.setVisible(false);
						frame.setSize(1250, 650);
						frame.setContentPane(new GastosForm(vehiculoDao, sucursalDao, Dashboard.getGastoDao(), Dashboard.getDestinoDao()));
					}
				}});
			vert.add(NewGastoButton);
			
			vert.setPreferredSize(new Dimension(225, 160));
			contentPane.add(vert, BorderLayout.WEST);

			ViewUtils.setIconToButton(confirm, "/resources/imgs/confirmar.png", 32, 32);
			ViewUtils.setIconToButton(cancel, "/resources/imgs/escoba.png", 32, 32);
			ViewUtils.setIconToButton(delete, "/resources/imgs/eliminar.png", 32, 32);

			ViewUtils.setIconToButton(searchButton, "/resources/imgs/lupa.png", 32, 32);
			ViewUtils.setIconToButton(gastosButton, "/resources/imgs/gastos-historial.png", 32, 32);
			ViewUtils.setIconToButton(newSaleButton, "/resources/imgs/nuevo-alquiler.png", 32, 32);
			ViewUtils.setIconToButton(newPurchaseButton, "/resources/imgs/nuevo-alquiler.png", 32, 32);

			fillBrands();
			fillBranches();
			searchButton.doClick();
		}
		
		private void fillBranches() {
			branchComboBox.removeAllItems();
			branchComboBox.addItem(new Sucursal("Seleccione una Concesionaria", null));

			List<Sucursal> sucursales = SucursalDao.getSucursales();
			for(Sucursal s : sucursales) {
				branchComboBox.addItem(s);
			}
		}

		private void fillBrands() {
			brandComboBox.removeAllItems();
			filterBrandComboBox.removeAllItems();
			brandComboBox.addItem(new Marca("Seleccione una Marca"));
			filterBrandComboBox.addItem(new Marca("Filtre por Marca"));

			List<Marca> marcas = MarcaDao.getMarcas();
			for(Marca m : marcas) {
				filterBrandComboBox.addItem(m);
				brandComboBox.addItem(m);
			}
		}

		private void insert() {
			try {
				int year = Integer.parseInt(yearTextField.getText());
				int km = Integer.parseInt(kilometersTextField.getText());
				Long sellPrice = Long.parseLong(sellingPriceTextField.getText());
				String plate = patenteTextField.getText().toUpperCase();
				String model = modelTextField.getText();
				//String ensurance = ensuranceTextField.getText();
				Marca m = (Marca)brandComboBox.getSelectedItem();
				Sucursal s = (Sucursal)branchComboBox.getSelectedItem();			
				
				VehiculoVenta v = new VehiculoVenta(sellPrice, year, km,  plate, model,  m, s);
				if(table.getSelectedRow() != -1) {
					Long id = (Long)tableModel.getValueAt(table.getSelectedRow(), 0);
					v.setId(id);
				}
				VehiculoDao.save(v);
				clearFields();
				searchButton.doClick();
				setMessage("Insertado correctamente", true);

			} catch (NumberFormatException e2) {
				setMessage("Asegurese de que todos los campos tengan formato valido. (Campos NUMERICOS no pueden estar vacios)", false);
			}catch(PersistenceException e5) {
				setMessage("ya existe un Vehiculo con esa Patente", false);	
			} catch(Exception e3) {
				setMessage("Ha ocurrido un Error:"+e3.getLocalizedMessage(), false);	
			}

		}
		
		private void delete() {
			long id = (long)tableModel.getValueAt(table.getSelectedRow(), 0);
			
			try {
				if(JOptionPane.showConfirmDialog(null, "Desea eliminar al vehiculo: "+VehiculoDao.getVehiculoById(id).getPlate(),  "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
					VehiculoDao.delete(id);
					clearFields();
					searchButton.doClick();
					setMessage("Eliminado correctamente", true);
				}
			}catch(Exception e) {
				setMessage(e.getCause().getMessage(), false);
			}
		}

		private void loadTable(String plate, Marca marca) {
	        tableModel.setRowCount(0);

			List<VehiculoVenta> vehiculos = VehiculoDao.getVehiculosVenta(plate, marca);
			for(VehiculoVenta v : vehiculos) {
				Compra c = transaccionDao.getCompraByVehicle(v);
				long buyPrice = (c != null)?c.getAmount(): 0;
				Object[] row = {v.getId(), v.getPlate(), v.getBrand(), v.getModel(), v.getYear(), v.getKilometers(), v.getSellPrice(), buyPrice, v.getBranch()};
				tableModel.addRow(row);
			}

		}

		private boolean validateFields() {
					if(patenteTextField.getText().length() < 6 || patenteTextField.getText().isBlank()) {
						setMessage("la Patente no puede tener menos de 6 caracteres", false);
						return false;
					}				
					if(yearTextField.getText().isBlank() || Integer.parseInt(yearTextField.getText()) < 1950) {
						setMessage("el Año no puede ser menor a 1950", false);
						return false;	
					}					
					if(brandComboBox.getSelectedIndex() == 0) {
						setMessage("Seleccione una Marca valida", false);
						return false;
					}				
					if(branchComboBox.getSelectedIndex() == 0) {
						setMessage("Seleccione una Sucursal valida", false);
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
			yearTextField.setText("");
			patenteTextField.setText("");
			modelTextField.setText("");
			kilometersTextField.setText("");
			brandComboBox.setSelectedIndex(0);
			branchComboBox.setSelectedIndex(0);
			sellingPriceTextField.setText("");
			
			//searchTextField.setText("");
			//filterBrandComboBox.setSelectedIndex(0);
			
			messageLabel.setText("");
	        messageLabel.setOpaque(false);
	        table.clearSelection();
			fillBrands();
			fillBranches();
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

		public JButton getSearchButton() {
			return searchButton;
		}
		
}
