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

import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.GridColumnShorthandResolver;

import entities.Cliente;
import entities.Marca;
import entityManagers.ClienteDao;
import entityManagers.MarcaDao;

public class AutosAlquilerForm extends JPanel{
	
private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JPanel inputPanel;
	private JPanel tablePanel;
	private JTable table;
	private DefaultTableModel tableModel;
	private JLabel messageLabel;
	private JTextField yearTextField;
	private JTextField patenteTextField;
	private JTextField modelTextField;
	private JTextField kilometersTextField;
	private JComboBox<Marca> marcasComboBox;
	private JTextField ensuranceTextField;


	public AutosAlquilerForm() {
							
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.setLayout(new BorderLayout(0, 5));
			contentPane.setPreferredSize(new Dimension(1100, 750));
			this.setSize(1100, 850);
			this.setLayout(new BorderLayout());
			this.add(contentPane, BorderLayout.CENTER);
			
			inputPanel = new JPanel();
			inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
			contentPane.add(inputPanel, BorderLayout.NORTH);
			

			JPanel horizontalPanel = new JPanel(new GridLayout());
			
			JLabel titulo = new JLabel("Vehiculos Alquiler", JLabel.CENTER);
			titulo.setFont(new Font("Montserrat Black", Font.BOLD, 46));
			horizontalPanel.add(titulo);		
			inputPanel.add(horizontalPanel);

			horizontalPanel = new JPanel(new GridLayout());
			horizontalPanel.add(new JLabel("Patente", JLabel.RIGHT));
			patenteTextField = new JTextField("",30);
			horizontalPanel.add(patenteTextField);
			horizontalPanel.add(new JLabel("Año", JLabel.RIGHT));
			yearTextField = new JTextField(10);	 
			horizontalPanel.add(yearTextField);
			horizontalPanel.add(new JLabel("", JLabel.RIGHT));
			inputPanel.add(horizontalPanel);		
			
			horizontalPanel = new JPanel(new GridLayout());
			horizontalPanel.add(new JLabel("Modelo", JLabel.RIGHT));
			modelTextField = new JTextField("",30);
			horizontalPanel.add(modelTextField);
			horizontalPanel.add(new JLabel("Kilometros", JLabel.RIGHT));
			kilometersTextField = new JTextField(10);
			horizontalPanel.add(kilometersTextField);
			horizontalPanel.add(new JLabel("", JLabel.RIGHT));
			inputPanel.add(horizontalPanel);		
			
			horizontalPanel = new JPanel(new GridLayout());
			horizontalPanel.add(new JLabel("Marca", JLabel.RIGHT));
			marcasComboBox = new JComboBox<Marca>();
			horizontalPanel.add(marcasComboBox);
			horizontalPanel.add(new JLabel("Seguro", JLabel.RIGHT));
			ensuranceTextField = new JTextField(20);
			horizontalPanel.add(ensuranceTextField);
			horizontalPanel.add(new JLabel("", JLabel.RIGHT));
			fillMarcas();
			inputPanel.add(horizontalPanel);	
			
			horizontalPanel = new JPanel(new GridLayout());
			horizontalPanel.setPreferredSize(new Dimension(WIDTH, 30));
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
				}
			});
	        confirm.setPreferredSize(new Dimension(250,40));
			JButton delete = new JButton("Eliminar");
			delete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(validateFields())
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
	        tableModel.addColumn("Descripcion");
	        tableModel.addColumn("Direccion");
	        tableModel.addColumn("Telefono");
	        tableModel.addColumn("Id");
	        
			table = new JTable(tableModel);
			table.getColumnModel().getColumn(3).setMaxWidth(100);
			table.getColumnModel().getColumn(3).setMinWidth(100);
			table.getColumnModel().getColumn(3).setPreferredWidth(100);
			table.setShowGrid(true);
			table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent e) {
	                if (!e.getValueIsAdjusting()) {
	                	int row = table.getSelectedRow();
	                	if(row != -1){
							String name = (String) tableModel.getValueAt(row, 0);
							String phone = (String) tableModel.getValueAt(row, 1);
							String adress = (String) tableModel.getValueAt(row, 2);
							long id = (long)tableModel.getValueAt(row, 3);
									
							patenteTextField.setText(name);
							modelTextField.setText(adress);
							kilometersTextField.setText(phone);
							yearTextField.setText(String.valueOf(id));
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
			
	        //JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			//contentPane.add(south, BorderLayout.SOUTH);
			
			horizontalPanel = new JPanel(new GridLayout());

			messageLabel = new JLabel("", SwingConstants.CENTER);
	        messageLabel.setForeground(Color.WHITE);
	        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
	        messageLabel.setBackground(Color.RED);
	        messageLabel.setOpaque(false);		
	        messageLabel.setPreferredSize(new Dimension(500,70));

			horizontalPanel.add(messageLabel);
			
			contentPane.add(horizontalPanel, BorderLayout.SOUTH);
			//south.add(horizontalPanel);

			loadTable();

		}
		
		private void fillMarcas() {
			marcasComboBox.addItem(new Marca("Seleccione una Marca"));

			List<Marca> marcas = MarcaDao.getMarcas();
			for(Marca m : marcas) {
				marcasComboBox.addItem(m);
			}
		}

		private void insert() {
			String description = patenteTextField.getText();
			String adress = modelTextField.getText();
			String phone = kilometersTextField.getText();
			
			Cliente m = new Cliente( description, adress, phone);
			
			ClienteDao.save(m);
			clearFields();
			loadTable();
		}

		private void update() {
			String description = patenteTextField.getText();
			long id = Long.parseLong(yearTextField.getText());
			String adress = modelTextField.getText();
			String phone = kilometersTextField.getText();

			Cliente m = new Cliente(id, description, phone, adress);

			ClienteDao.save(m);
			clearFields();
			loadTable();
		}
		
		private void delete() {
			long id = Long.parseLong(yearTextField.getText());
			try {
				if(JOptionPane.showConfirmDialog(null, "Desea eliminar al cliente con el id: "+id,  "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
					ClienteDao.delete(id);
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

			List<Cliente> clientes = ClienteDao.getClientes();
			for(Cliente m : clientes) {
				Object[] row = {m.getName(), m.getAdress(), m.getPhone(), m.getId()};
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
			if(marcasComboBox.getSelectedIndex() == 0) {
				setMessage("Seleccione una Marca valida");
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
			marcasComboBox.setSelectedIndex(0);
			ensuranceTextField.setText("");
			
			messageLabel.setText("");
	        messageLabel.setOpaque(false);
	        table.clearSelection();
		}

}
