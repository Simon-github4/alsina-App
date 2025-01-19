package views;

import java.awt.BorderLayout;
import java.awt.Color;
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
import entityManagers.ClienteDao;

public class ClientesForm extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JPanel inputPanel;
	private JPanel tablePanel;
	private JTable table;
	private DefaultTableModel tableModel;
	private JLabel messageLabel;
	private JTextField nameTextField;
	private JTextField adressTextField;
	private JTextField phoneTextField;
	private ClienteDao ClienteDao;
	private JTextField dniTextField;
	private JTextField cuitTextField;
	private JTextField searchTextField;

	private JTextField licenseTextField;

	private JTextField codTextField;

	private JTextField expirationTextField;

	private JTextField cardTextField;

	private JTextField cardNumberTextField;

	private JTextField cardExpirationTextField;


	public ClientesForm(ClienteDao cdao) {
			ClienteDao=cdao;
		
			contentPane = new JPanel();
			contentPane.setLayout(new BorderLayout(0, 5));
			contentPane.setPreferredSize(new Dimension(1100, 750));
			this.setSize(1100, 850);
			this.setLayout(new BorderLayout());
			this.add(contentPane, BorderLayout.CENTER);
			
			inputPanel = new JPanel();
			inputPanel.setBorder(new EmptyBorder(0,0,0,25));
			inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
			contentPane.add(inputPanel, BorderLayout.NORTH);		

			JPanel horizontalPanel = new JPanel(new GridLayout());
			
			JLabel titulo = new JLabel("CLIENTES", JLabel.CENTER);
			titulo.setFont(new Font("Montserrat Black", Font.BOLD, 46));
			horizontalPanel.add(titulo);		
			inputPanel.add(horizontalPanel);

			horizontalPanel = new JPanel(new GridLayout());
			horizontalPanel.add(new JLabel("Nombre/s", JLabel.RIGHT));
			nameTextField = new JTextField("",30);
			horizontalPanel.add(nameTextField);
			horizontalPanel.add(new JLabel("Domicilio", JLabel.RIGHT));
			adressTextField = new JTextField("",30);
			horizontalPanel.add(adressTextField);
			horizontalPanel.add(new JLabel("Telefono", JLabel.RIGHT));
			phoneTextField = new JTextField(10);
			horizontalPanel.add(phoneTextField);			
			inputPanel.add(horizontalPanel);		
			
			horizontalPanel = new JPanel(new GridLayout());
					
			horizontalPanel.add(new JLabel("D.N.I", JLabel.RIGHT));
			dniTextField = new JTextField("",30);
			horizontalPanel.add(dniTextField);
			horizontalPanel.add(new JLabel("Cuit/Cuil", JLabel.RIGHT));
			cuitTextField = new JTextField(10);
			horizontalPanel.add(cuitTextField);
			horizontalPanel.add(new JLabel("Licencia de Conducir", JLabel.RIGHT));
			licenseTextField = new JTextField(10);
			horizontalPanel.add(licenseTextField);
			inputPanel.add(horizontalPanel);	
			
			horizontalPanel = new JPanel(new GridLayout());			
			horizontalPanel.add(new JLabel("Vencimiento de Licencia", JLabel.RIGHT));
			expirationTextField = new JTextField("",30);
			horizontalPanel.add(expirationTextField);
			horizontalPanel.add(new JLabel("Tarjeta", JLabel.RIGHT));
			cardTextField = new JTextField(10);
			horizontalPanel.add(cardTextField);		
			horizontalPanel.add(new JLabel("Numero de Tarjeta", JLabel.RIGHT));
			cardNumberTextField = new JTextField("",30);
			horizontalPanel.add(cardNumberTextField);
			inputPanel.add(horizontalPanel);
			
			horizontalPanel = new JPanel(new GridLayout());	
			horizontalPanel.add(new JLabel("Tarjeta Vencimiento", JLabel.RIGHT));
			cardExpirationTextField = new JTextField(10);
			horizontalPanel.add(cardExpirationTextField);
			horizontalPanel.add(new JLabel("Cod.Seg./Autor", JLabel.RIGHT));
			codTextField = new JTextField(10);
			horizontalPanel.add(codTextField);
			horizontalPanel.add(new JLabel(""));
			horizontalPanel.add(new JLabel(""));
			inputPanel.add(horizontalPanel);	
			
			
			horizontalPanel = new JPanel(new GridLayout());
			horizontalPanel.add(new JLabel(""));
			horizontalPanel.add(new JLabel("Buscar por Nombre", JLabel.RIGHT));
			searchTextField = new JTextField(); 
			searchTextField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Presione Enter para buscar");
			searchTextField.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);
			searchTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER)
						loadTable(searchTextField.getText());
				}
			});
			horizontalPanel.add(searchTextField);
			horizontalPanel.add(new JLabel(""));
			horizontalPanel.add(new JLabel(""));
			inputPanel.add(horizontalPanel);		
			
			horizontalPanel = new JPanel(new FlowLayout());
			JButton confirm = new JButton("Confirmar");
			confirm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(validateFields())
							insert();
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
	        tableModel.addColumn("Descripcion");
	        tableModel.addColumn("Domicilio");
	        tableModel.addColumn("Telefono");
	        tableModel.addColumn("D.N.I");
	        tableModel.addColumn("Cuit/Cuil");
	        tableModel.addColumn("Licencia");
	        tableModel.addColumn("Vencimiento");
	        tableModel.addColumn("Tarjeta");
	        tableModel.addColumn("Numero");
	        tableModel.addColumn("Cod seguridad");
	        tableModel.addColumn("Vencimiento");
	        tableModel.addColumn("Id");
	        
			table = new JTable(tableModel);
			table.getColumnModel().getColumn(11).setMaxWidth(0);
			table.getColumnModel().getColumn(11).setMinWidth(0);
			table.getColumnModel().getColumn(11).setPreferredWidth(0);
			table.setShowGrid(true);
			table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent e) {
	                if (!e.getValueIsAdjusting()) {
	                	int row = table.getSelectedRow();
	                	if(row != -1){
							String name = (String) tableModel.getValueAt(row, 0);
							String adress = (String) tableModel.getValueAt(row, 1);
							String phone = (String) tableModel.getValueAt(row, 2);
							String dni = (String) tableModel.getValueAt(row, 3);
							String cuil = (String) tableModel.getValueAt(row, 4);
							String license = (String) tableModel.getValueAt(row, 5);
							String expiration = (String) tableModel.getValueAt(row, 6);
							String card = (String) tableModel.getValueAt(row, 7);
							String cardNumber = (String) tableModel.getValueAt(row, 8);
							String cod = (String) tableModel.getValueAt(row, 9);
							String cardExpiration = (String) tableModel.getValueAt(row, 10);
							
							nameTextField.setText(name);
							adressTextField.setText(adress);
							phoneTextField.setText(phone);
							dniTextField.setText(dni);
							cuitTextField.setText(cuil);
							
							licenseTextField.setText(license);
							expirationTextField.setText(expiration);
							codTextField.setText(cod);
							cardTextField.setText(card);
							cardNumberTextField.setText(cardNumber);
							cardExpirationTextField.setText(cardExpiration);
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

			loadTable(searchTextField.getText());

		}
		
		private void insert() {
			String description = nameTextField.getText();
			String adress = adressTextField.getText();
			String phone = phoneTextField.getText();
			String dni = dniTextField.getText();
			String cuil = cuitTextField.getText();

			String license = licenseTextField.getText();
			String expiration = expirationTextField.getText();
			String cod = codTextField.getText();
			String card = cardTextField.getText();
			String cardNumber = cardNumberTextField.getText();
			String cardExpiration = cardExpirationTextField.getText();
			
			Cliente m = new Cliente(description, adress, phone, dni, cuil, license, expiration, cod, card, cardNumber, cardExpiration);

			if(table.getSelectedRow() != -1) {
				long id = (Long)tableModel.getValueAt(table.getSelectedRow(), 11);
				m.setId(id);
			}
			ClienteDao.save(m);
			clearFields();
			loadTable(searchTextField.getText());
		}
		
		private void delete() {
			long id = (Long)tableModel.getValueAt(table.getSelectedRow(), 11);
			try {
				if(JOptionPane.showConfirmDialog(null, "Desea eliminar al cliente con el id: "+id,  "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
					ClienteDao.delete(id);
					clearFields();
					loadTable(searchTextField.getText());
				}
			}catch(Exception e) {
				//JOptionPane.showMessageDialog(null, e.getCause(), "ERROR AL ELIMINAR", JOptionPane.ERROR_MESSAGE);
				setMessage(e.getCause().getMessage());
			}
			
		}
		
		private void loadTable(String name) {
	        tableModel.setRowCount(0);

			List<Cliente> clientes = ClienteDao.getClientes(name);
			for(Cliente m : clientes) {
				Object[] row = {m.getName(), m.getAdress(), m.getPhone(), m.getDni(), m.getCuil(), m.getLicense(), m.getExpiration(), m.getCard(), m.getCardNumber(), m.getCod(), m.getCardExpiration(), m.getId()};
				tableModel.addRow(row);
			}
		}

		private boolean validateFields() {
			if(nameTextField.getText().isBlank()) {
				setMessage("El nombre no puede estar vacio");
				return false;
			}
			if(dniTextField.getText().isBlank()) {
				setMessage("El DNI no puede estar vacio");
				return false;
			}
			if(nameTextField.getText().length() > 45) {
				setMessage("El nombre no puede superar los 45 caracteres");
				return false;
			}
			return true;	
		}

		private void setMessage(String message) {
			messageLabel.setText(message);
	        messageLabel.setOpaque(true);

		}

		private void clearFields() {
			table.clearSelection();
			nameTextField.setText("");
			adressTextField.setText("");
			phoneTextField.setText("");
			dniTextField.setText("");
			cuitTextField.setText("");
			
			licenseTextField.setText("");
			expirationTextField.setText("");
			codTextField.setText("");
			cardTextField.setText("");
			cardNumberTextField.setText("");
			cardExpirationTextField.setText("");
			
			searchTextField.setText("");
			messageLabel.setText("");
	        messageLabel.setOpaque(false);
		}

}
