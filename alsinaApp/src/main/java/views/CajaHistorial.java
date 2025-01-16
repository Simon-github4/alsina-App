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
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatClientProperties;

import entities.Alquiler;
import entities.Destino;
import entities.Gasto;
import entities.Sucursal;
import entities.VehiculoAlquilable;
import entityManagers.AlquilerDao;
import entityManagers.DestinoDao;
import entityManagers.GastoDao;
import entityManagers.SucursalDao;
import entityManagers.VehiculoDao;
import interfaces.ViewUtils;
import raven.datetime.DatePicker;

public class CajaHistorial extends JPanel {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JPanel north;
	private JPanel tablePanel;
	private JTable table;
	private DefaultTableModel tableModel;
	private JLabel messageLabel;

	private JTextField searchTextField;
	private JFormattedTextField filterDatesField;
	private DatePicker dpFilters;
	private JButton searchButton;
	
	private AlquilerDao AlquilerDao;
	private VehiculoDao VehiculoDao;
	private SucursalDao SucursalDao;
	private GastoDao GastoDao;
	private DestinoDao DestinoDao;
	private JComboBox<Sucursal> filterBranchComboBox;
	private JComboBox<Destino> filterDestinationComboBox;
	private JLabel egresosLabel;

	private JLabel saldoLabel;

	private JLabel ingresosLabel;

	private JRadioButton egresosRadioButton;

	private JRadioButton ingresosRadioButton;

	private DefaultTableModel tableIncomeModel;

	private JTable tableIncome;

	private JPanel tableIncomePanel;

	private JTextField saldoTextField;
	
	
	public CajaHistorial(AlquilerDao alquilerDao, VehiculoDao vehiculoDao, SucursalDao SucursalDao, GastoDao GastoDao, DestinoDao DestinoDao) {
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
		
		tablePanel = new JPanel();
		tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
		contentPane.add(tablePanel, BorderLayout.CENTER);
		
		JScrollPane scroll = new JScrollPane(table);
		
		JPanel horizontalPanel = new JPanel(new GridLayout());
		horizontalPanel.add(scroll);
		tablePanel.add(horizontalPanel);			
		
		north = new JPanel();
		north.setBorder(new EmptyBorder(0, 0, 0, 30));
		north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
		//west.setBorder(new TitledBorder(new LineBorder(Color.black, 2), "Buscar/Filtrar", TitledBorder.CENTER, TitledBorder.TOP, null, Color.BLACK));
		contentPane.add(north, BorderLayout.NORTH);

		horizontalPanel = new JPanel(new GridLayout(1,0));

		JLabel titulo = new JLabel("RESUMEN CAJA", JLabel.CENTER);
		titulo.setFont(new Font("Montserrat Black", Font.BOLD, 46));
		horizontalPanel.add(titulo);		
		north.add(horizontalPanel);

		horizontalPanel = new JPanel(new GridLayout(1,0));
		
		JLabel label = new JLabel("Patente");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		horizontalPanel.add(label);
		searchTextField = new JTextField();
		searchTextField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Presione Enter para buscar");
		searchTextField.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);
		horizontalPanel.add(searchTextField);
		JLabel label_1 = new JLabel("Fechas");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		horizontalPanel.add(label_1);
		dpFilters = new DatePicker();
		dpFilters.setDateSelectionMode(DatePicker.DateSelectionMode.BETWEEN_DATE_SELECTED);
		dpFilters.setUsePanelOption(true);
		dpFilters.setBackground(Color.GRAY);
		dpFilters.setDateFormat("yyyy/MM/dd");
		filterDatesField = new JFormattedTextField();
		dpFilters.setEditor(filterDatesField);
		horizontalPanel.add(filterDatesField);
		ingresosRadioButton = new JRadioButton("Ingresos");
		horizontalPanel.add(ingresosRadioButton);		//horizontalPanel.add(new JLabel("", JLabel.RIGHT));	
		horizontalPanel.setPreferredSize(new Dimension(WIDTH, 40));
		north.add(horizontalPanel);
		
		horizontalPanel = new JPanel(new GridLayout(1,0));

		horizontalPanel.add(new JLabel("Consecionaria", SwingConstants.RIGHT));
		filterBranchComboBox = new JComboBox<Sucursal>();
		horizontalPanel.add(filterBranchComboBox);
		JLabel label_2 = new JLabel("Categoria");
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		horizontalPanel.add(label_2);
		filterDestinationComboBox = new JComboBox<Destino>();
		horizontalPanel.add(filterDestinationComboBox);
		egresosRadioButton = new JRadioButton("Egresos");
		horizontalPanel.add(egresosRadioButton);//horizontalPanel.add(new JLabel("", JLabel.RIGHT));		
		horizontalPanel.setPreferredSize(new Dimension(WIDTH, 40));
		north.add(horizontalPanel);

		ingresosRadioButton.setSelected(true);
		egresosRadioButton.setSelected(true);
		/*horizontalPanel = new JPanel(new GridLayout(1,0));
		horizontalPanel.add(new JLabel("", JLabel.RIGHT));		
		egresosRadioButton.setSelected(true);
		ingresosRadioButton = new JRadioButton("Ingresos");
		ingresosRadioButton.setSelected(true);
		horizontalPanel.add(egresosRadioButton);
		horizontalPanel.add(new JLabel("", JLabel.RIGHT));		
		horizontalPanel.add(ingresosRadioButton);
		horizontalPanel.add(new JLabel("", JLabel.RIGHT));		

		west.add(horizontalPanel);*/
		
		horizontalPanel = new JPanel(new GridLayout(1,0));

		horizontalPanel.add(new JLabel("", JLabel.RIGHT));		
		searchButton =new JButton("Buscar");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadTable(searchTextField.getText(), dpFilters.getSelectedDateRange(), (Sucursal)filterBranchComboBox.getSelectedItem(), (Destino)filterDestinationComboBox.getSelectedItem());
			}
		});
		ViewUtils.setIconToButton(searchButton, "/resources/imgs/lupa.png", 32, 32);
		horizontalPanel.add(searchButton);
		searchTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					searchButton.doClick();
			}
		});
		JButton deleteButton = new JButton("Eliminar");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() == -1)
					setMessage("Ningun elemento Seleccionado", false);
				else
					delete();
			}
		});
		horizontalPanel.add(deleteButton);
		ViewUtils.setIconToButton(deleteButton, "/resources/imgs/eliminar.png", 32, 32);
		JButton updateButton = new JButton("Modificar");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() == -1)
					setMessage("Ningun elemento Seleccionado", false);
				else
					update();
			}
		});
		horizontalPanel.add(updateButton);
		ViewUtils.setIconToButton(updateButton, "/resources/imgs/modificar.png", 32, 32);
		horizontalPanel.setPreferredSize(new Dimension(WIDTH, 55));
		horizontalPanel.add(new JLabel("", JLabel.RIGHT));		

		north.add(horizontalPanel);

		tableIncomeModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que todas las celdas sean no editables
            }
        };
        tableIncomeModel.addColumn("Vehiculo");
        tableIncomeModel.addColumn("Fecha Fin");
        tableIncomeModel.addColumn("Descripcion");
        tableIncomeModel.addColumn("Importe");
        //tableIncomeModel.addColumn("Consecionaria");
        tableIncomeModel.addColumn("Id");
        
		tableIncome = new JTable(tableIncomeModel);
		tableIncome.getColumnModel().getColumn(4).setMaxWidth(0);
		tableIncome.getColumnModel().getColumn(4).setMinWidth(0);
		tableIncome.getColumnModel().getColumn(4).setPreferredWidth(0);
		tableIncome.getColumnModel().getColumn(3).setPreferredWidth(80);
		//tableIncome.getColumnModel().getColumn(4).setPreferredWidth(100);
		tableIncome.setShowGrid(true);
		
		tableIncomePanel = new JPanel();
		tableIncomePanel.setLayout(new BoxLayout(tableIncomePanel, BoxLayout.Y_AXIS));
		tableIncomePanel.setBorder(new TitledBorder(new LineBorder(Color.black, 0), "INGRESOS", TitledBorder.CENTER, TitledBorder.TOP, null, Color.WHITE));
		tableIncomePanel.setBackground(Color.GREEN);

		tablePanel.setBackground(Color.RED);
		tablePanel.setBorder(new TitledBorder(new LineBorder(Color.black, 0), "EGRESOS", TitledBorder.CENTER, TitledBorder.TOP, null, Color.WHITE));
		
		contentPane.add(tableIncomePanel, BorderLayout.EAST);
		
		JScrollPane scrollp = new JScrollPane(tableIncome);
		
		horizontalPanel = new JPanel(new GridLayout());
		horizontalPanel.add(scrollp);
		tableIncomePanel.add(horizontalPanel);			
		
		JPanel south = new JPanel();
        south.setLayout(new BoxLayout(south, BoxLayout.Y_AXIS));

        JPanel labels = new JPanel();
        labels.setLayout(new GridLayout(0,3));

		horizontalPanel = new JPanel(new GridLayout());
		egresosLabel = new JLabel("Egresos: $", JLabel.CENTER);
		egresosLabel.setFont(new Font("Montserrat", Font.BOLD, 23));
		horizontalPanel.add(egresosLabel);
		labels.add(horizontalPanel);
		
		horizontalPanel = new JPanel(new GridLayout());
		saldoLabel = new JLabel("Saldo: $", JLabel.RIGHT);
		saldoLabel.setFont(new Font("Montserrat", Font.BOLD, 23));
		horizontalPanel.add(saldoLabel);
		saldoTextField = new JTextField();
		horizontalPanel.add(saldoTextField);
		labels.add(horizontalPanel);
		
		horizontalPanel = new JPanel(new GridLayout());
		ingresosLabel = new JLabel("Ingresos: $", JLabel.CENTER);
		ingresosLabel.setFont(new Font("Montserrat", Font.BOLD, 23));
		horizontalPanel.add(ingresosLabel);
		labels.add(horizontalPanel);
		
		south.add(labels);
		
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
		//clearFields();
		searchButton.doClick();
	}

	private void fillBranchs() {
		filterBranchComboBox.addItem(new Sucursal("Seleccione una Consecionaria"));

		List<Sucursal> sucursales = SucursalDao.getSucursales();
		for(Sucursal s : sucursales) 
			filterBranchComboBox.addItem(s);		
		
	}

	private void fillDestinations() {
		filterDestinationComboBox.addItem(new Destino("Seleccione un Destino"));
		
		List<Destino> destinos = DestinoDao.getDestinos();
		for(Destino s : destinos) 
			filterDestinationComboBox.addItem(s);
		
	}
	
	private void update() {
		try {
        	int row = table.getSelectedRow();
			VehiculoAlquilable vehicle = (VehiculoAlquilable) tableModel.getValueAt(row, 0);
			LocalDate date = (LocalDate) tableModel.getValueAt(row, 1);
			String description = (String) tableModel.getValueAt(row, 2);
			int amount = (int) tableModel.getValueAt(row, 3);
			Destino destination = (Destino) tableModel.getValueAt(row, 4);
			String pay = (String) tableModel.getValueAt(row, 5);
			Sucursal branch = (Sucursal) tableModel.getValueAt(row, 6);
			
			Long id = (Long) tableModel.getValueAt(table.getSelectedRow(), 7);
			Gasto g = new Gasto(id, vehicle, amount, date, description, pay, destination, branch);

			JTabbedPane t = getTabbedPane();
			t.setSelectedIndex(0);
			GastosForm a = (GastosForm)t.getSelectedComponent();
			a.setUpdateForm(g);
			
		}catch(Exception ee) {
			ee.printStackTrace();
		}
	}
	
	private void delete() {
		Long id = (Long)tableModel.getValueAt(table.getSelectedRow(), 7);
		
		try {
			if(JOptionPane.showConfirmDialog(null, "Desea eliminar el alquiler: "+id ,"", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
				AlquilerDao.delete(id);
				searchButton.doClick();
				setMessage("Eliminado correctamente", true);
			}
		}catch(Exception e) {
			setMessage(e.getCause().getMessage(), false);
		}
		
	}
	
	private void loadTable(String plate, LocalDate[] dates, Sucursal branch, Destino desination) {
		Long egresos = new Long(0);
		tableModel.setRowCount(0);

		if(egresosRadioButton.isSelected()) {
			List<Gasto> gastos = GastoDao.getGastosByFilters(plate.toLowerCase(), dates, branch, desination);			
			for(Gasto a : gastos) {
				Object[] row = {a.getVehicle(), a.getDate(), a.getDescription(), a.getAmount(), a.getDestination(), a.getPayment(), a.getBranch(), a.getId()};
				tableModel.addRow(row);
				egresos += a.getAmount();
			}
		}
        egresosLabel.setText("Egresos: $"+ egresos);
		
        Long ingresos = new Long(0);
        tableIncomeModel.setRowCount(0);

		if(ingresosRadioButton.isSelected()) {
	        for(Alquiler a: AlquilerDao.getAlquileresByPlateAndDate(plate, dates)) {
	        	Object[] row = {a.getVehicle(), a.getEnd().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), a.getClient(), a.getPricePaid(), a.getId()};
				tableIncomeModel.addRow(row);
				ingresos += a.getPricePaid();
	        }
		}
		ingresosLabel.setText("Ingresos: $"+ ingresos);
		
        saldoLabel.setText("SALDO: $");
        saldoTextField.setText(String.valueOf(ingresos-egresos));
    	saldoTextField.setForeground(Color.BLACK);
        if(ingresos - egresos < 0) 
        	saldoTextField.setBackground(Color.red);
        else
        	saldoTextField.setBackground(new Color(0,245,0));
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
	
}
