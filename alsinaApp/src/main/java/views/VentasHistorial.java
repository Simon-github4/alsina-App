package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatClientProperties;

import entities.Cliente;
import entities.Compra;
import entities.Transaccion;
import entities.Venta;
import entityManagers.ClienteDao;
import entityManagers.TransaccionDao;
import raven.datetime.DatePicker;
import utils.PdfUtils;
import utils.ViewUtils;

public class VentasHistorial extends JPanel{

	
	private static final long serialVersionUID = 1L;
	private TransaccionDao transaccionDao;
	private ClienteDao clienteDao;
	private JPanel northPanel;
	private JPanel tablePanel;
	private JTable table;
	private DefaultTableModel tableModel;
	private JPanel contentPane;

	private JTextField searchTextField;
	private DatePicker dpFilters;
	private JComboBox<Cliente> clientFilterComboBox;
	private JButton searchButton;
	
	private JLabel messageLabel;
	private JRadioButton salesRadioButton;
	private JRadioButton purchasesRadioButton;
	
	public VentasHistorial(TransaccionDao TransaccionDao, ClienteDao clienteDao) {
		this.transaccionDao = TransaccionDao;
		this.clienteDao= clienteDao;
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 5));
		contentPane.setPreferredSize(new Dimension(1050, 670));
		this.setSize(1100, 750);
		this.setLayout(new BorderLayout());
		this.add(contentPane, BorderLayout.CENTER);
		
		northPanel = new JPanel();
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
		contentPane.add(northPanel, BorderLayout.NORTH);
		
		JPanel horizontalPanel = new JPanel(new GridLayout());
		
		JLabel titulo = new JLabel("HISTORIAL BOLETOS DE C - V", JLabel.CENTER);
		titulo.setFont(new Font("Montserrat Black", Font.BOLD, 46));
		horizontalPanel.add(titulo);		
		northPanel.add(horizontalPanel);
		
		JLabel l = new JLabel("");
		l.setPreferredSize(new Dimension(WIDTH, 15));
		northPanel.add(l);
		
		horizontalPanel = new JPanel(new GridLayout(1,0));
		horizontalPanel.add(new JLabel("Patente", JLabel.RIGHT));
		searchTextField = new JTextField();
		searchTextField.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);
		horizontalPanel.add(searchTextField);
		horizontalPanel.add(new JLabel("Fechas", JLabel.RIGHT));
		dpFilters = new DatePicker();
		dpFilters.setDateSelectionMode(DatePicker.DateSelectionMode.BETWEEN_DATE_SELECTED);
		dpFilters.setUsePanelOption(true);
		dpFilters.setBackground(Color.GRAY);
		dpFilters.setDateFormat("dd/MM/yyyy");
		JFormattedTextField filterDatesField = new JFormattedTextField();
		dpFilters.setEditor(filterDatesField);
		horizontalPanel.add(filterDatesField);
		horizontalPanel.add(new JLabel("Cliente", JLabel.RIGHT));
		clientFilterComboBox = new JComboBox<Cliente>();
		horizontalPanel.add(clientFilterComboBox);
		horizontalPanel.add(new JLabel(""));
		horizontalPanel.setPreferredSize(new Dimension(WIDTH, 40));
		northPanel.add(horizontalPanel);
					
		horizontalPanel = new JPanel(new GridLayout(1,0));
		horizontalPanel.add(new JLabel(""));
		horizontalPanel.add(new JLabel(""));
		salesRadioButton = new JRadioButton("Ventas");
		horizontalPanel.add(salesRadioButton);
		purchasesRadioButton = new JRadioButton("Compras");
		horizontalPanel.add(purchasesRadioButton);
		horizontalPanel.add(new JLabel(""));
		horizontalPanel.add(new JLabel(""));
		northPanel.add(horizontalPanel);

		purchasesRadioButton.setSelected(true);
		salesRadioButton.setSelected(true);
		
		l = new JLabel("");
		l.setPreferredSize(new Dimension(WIDTH, 15));
		//northPanel.add(l);

		horizontalPanel = new JPanel(new GridLayout(1,0));
		horizontalPanel.add(new JLabel(""));
		searchButton =new JButton("Buscar");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadTable(searchTextField.getText(), (Cliente)clientFilterComboBox.getSelectedItem(), dpFilters.getSelectedDateRange());
				fillClients();
			}
		});
		horizontalPanel.add(searchButton);
		ViewUtils.setIconToButton(searchButton, "/resources/imgs/lupa.png", 32, 32);
		searchTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				SwingUtilities.invokeLater(()-> searchButton.doClick());
			}
		});
		JButton deleteButton = new JButton("Eliminar Boleto");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow()==-1)
					setMessage("Ningun Alquiler seleccionado", false);
				else 
					try {
						delete();
					}catch(Exception ee) {
						ee.printStackTrace();
					}
			
			}
		});
		horizontalPanel.add(deleteButton);
		ViewUtils.setIconToButton(deleteButton, "/resources/imgs/eliminar.png", 32, 32);
		horizontalPanel.setPreferredSize(new Dimension(WIDTH, 55));
	
		JButton updateButton = new JButton("Modificar Boleto");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow()==-1)
					setMessage("Ningun Alquiler seleccionado", false);
				else {
					try {
						update();
					}catch(Exception ee) {
						ee.printStackTrace();
					}
			}
			}
		});
		ViewUtils.setIconToButton(updateButton, "/resources/imgs/modificar.png", 32, 32);
		horizontalPanel.add(updateButton);
		JButton quotesButton = new JButton("Cobros");
		quotesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow()==-1)
					setMessage("Ninguna Venta seleccionado", false);
				else {
					try {
							JTabbedPane t = getTabbedPane();
							VentaFrame v =(VentaFrame) t.getParent().getParent().getParent().getParent();
							Long id = (Long) tableModel.getValueAt(table.getSelectedRow(), 0);
							v.openListadoCuotas(id);
						}catch(Exception ee) {
						ee.printStackTrace();
					}
			}
			}
		});
		ViewUtils.setIconToButton(quotesButton, "/resources/imgs/factura.png", 32, 32);
		horizontalPanel.add(quotesButton);
		JButton pdfButton = new JButton("Generar Boleto");
		pdfButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow()==-1)
					setMessage("Ninguna Venta seleccionado", false);
				else {
					try {
							Long id = (Long)tableModel.getValueAt(table.getSelectedRow(), 0); 
							Transaccion t = transaccionDao.getTransaccionById(id);
							PdfUtils.createTransactionPdf(t);
					}catch(IllegalArgumentException il) {
						setMessage("Datos incompletos para Imprimir Contrato:" + il.getLocalizedMessage(), false);
						il.printStackTrace();;				
					}catch(Exception ee) {
						setMessage("Ha ocurrido un Error" + ee.getLocalizedMessage(), false);
						ee.printStackTrace();
					}
			}
			}
		});
		ViewUtils.setIconToButton(pdfButton, "/resources/imgs/pdf.png", 32, 32);
		horizontalPanel.add(pdfButton);
		horizontalPanel.add(new JLabel(""));
		northPanel.add(horizontalPanel);
		
/*------------------------------------------------------------------------------------------------------------------------------------------------------*/
		
		tablePanel = new JPanel();
		tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
		contentPane.add(tablePanel, BorderLayout.CENTER);
		
		tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que todas las celdas sean no editables
            }
        };
        tableModel.addColumn("Id");
        tableModel.addColumn("Vehiculo");
        tableModel.addColumn("Cliente");
        tableModel.addColumn("Monto");
        tableModel.addColumn("Fecha");
        tableModel.addColumn("Observaciones");
        tableModel.addColumn("C/V");
        
		table = new JTable(tableModel);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                	int row = table.getSelectedRow();
                	if(row != -1){
                		
                	}
                }
			}		
		});
		//table.getColumnModel().getColumn(4).setCellRenderer(new ColorearTabla(AlquilerDao));
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		table.getColumnModel().getColumn(1).setMaxWidth(155);	
		table.getColumnModel().getColumn(1).setPreferredWidth(155);			
		table.getColumnModel().getColumn(2).setPreferredWidth(160);		
		table.getColumnModel().getColumn(4).setMaxWidth(140);	
		table.getColumnModel().getColumn(4).setPreferredWidth(140);		
		table.getColumnModel().getColumn(5).setPreferredWidth(160);		
		table.getColumnModel().getColumn(6).setMaxWidth(95);	
		table.getColumnModel().getColumn(6).setPreferredWidth(95);	
		table.setShowGrid(true);
			
		JScrollPane scroll = new JScrollPane(table);
		
		horizontalPanel = new JPanel(new GridLayout());
		horizontalPanel.add(scroll);
		tablePanel.add(horizontalPanel);

/*------------------------------------------------------------------------------------------------------------------------------------------------------*/

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

		fillClients();
		searchButton.doClick();
	}
	
	private void fillClients() {
		clientFilterComboBox.removeAllItems();
		
		Cliente first = new Cliente();
		first.setName("Seleccione un cliente");
		clientFilterComboBox.addItem(first);
		for(Cliente c : clienteDao.getClientes())
			clientFilterComboBox.addItem(c);
	}

	private void loadTable(String plate, Cliente client, LocalDate[] dates) {
        tableModel.setRowCount(0);
        List<Transaccion> transacciones = transaccionDao.getTransactions(plate, client, dates);
        if(! purchasesRadioButton.isSelected() && salesRadioButton.isSelected())
        	transacciones = transacciones.stream().filter(t -> t instanceof Venta).toList();
        else if(purchasesRadioButton.isSelected() && ! salesRadioButton.isSelected())
        	transacciones = transacciones.stream().filter(t -> t instanceof Compra).toList();
        else if(! purchasesRadioButton.isSelected() && ! salesRadioButton.isSelected())
        	transacciones = List.of();
        
		for(Transaccion a : transacciones) {
			Object[] row = {a.getId(), a.getVehicle(), a.getClient(), a.getAmount(), 
							a.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), a.getDescription(), a.getClass().getSimpleName()};
			tableModel.addRow(row);
		}
	}

	private void update() {
		int row =table.getSelectedRow();
		Long id = (Long) tableModel.getValueAt(row, 0);
		
		Transaccion transaction = (Transaccion) transaccionDao.getTransaccionById(id);		

		JTabbedPane t = getTabbedPane();
		VentaFrame v =(VentaFrame) t.getParent().getParent().getParent().getParent();
		v.openNuevaVenta(transaction.getVehicle(), transaction, transaction.getClass().getSimpleName());
	}
	
	private void delete() {
		long id = (long)tableModel.getValueAt(table.getSelectedRow(), 0);
		
		try {
			if(JOptionPane.showConfirmDialog(null, "Desea eliminar el alquiler: "+id ,"", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
				transaccionDao.delete(id);
				searchButton.doClick();
				setMessage("Eliminado correctamente", true);
			}
		}catch(Exception e) {
			setMessage(e.getCause().getMessage(), false);
		}
		
	}
	
	private void setMessage(String message, boolean succes) {
		messageLabel.setText(message);
        messageLabel.setOpaque(true);
        if(succes == true)
        	messageLabel.setBackground(Color.GREEN);
        else 
        	messageLabel.setBackground(Color.RED);
	}
	
	public JTextField getSearchTextField() {
		return searchTextField;
	}

	public JButton getSearchButton() {
		return searchButton;
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
	

	private void clearFields() {
		messageLabel.setText("");
		messageLabel.setOpaque(false);
		table.clearSelection();
	}
	
}
