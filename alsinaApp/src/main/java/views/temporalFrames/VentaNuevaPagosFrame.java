package views.temporalFrames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatClientProperties;

import entities.Cuota;
import entityManagers.CuotaDao;
import entityManagers.TransaccionDao;
import raven.datetime.DatePicker;
import raven.datetime.event.DateSelectionEvent;
import raven.datetime.event.DateSelectionListener;

public class VentaNuevaPagosFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel tablePanel;
	private JPanel inputPanel;
	private JTable table;
	private DefaultTableModel tableModel;
	private JSpinner quantitySpinner;
	private JTextField amountTextField;
	private TransaccionDao transaccionDao;
	private CuotaDao cuotaDao;
	private JTextField payMethodTextField;
	private DatePicker dp;
	
	private Long transaccionId;
	private JTextField plateTextField;
	
	public VentaNuevaPagosFrame(TransaccionDao transaccionDao1, CuotaDao cuotaDao1, Long transaccion) {
		this.cuotaDao = cuotaDao1;
		this.transaccionDao = transaccionDao1;
		this.transaccionId = transaccion;

		contentPane = new JPanel(new BorderLayout());
		getContentPane().setLayout(new BorderLayout());		
		getContentPane().add(contentPane, BorderLayout.CENTER);
		this.setSize(1250, 650);
		setLocationRelativeTo(null);
		
		contentPane.setPreferredSize(new Dimension(1250, 670));
		contentPane.setBorder(new EmptyBorder(12,12,12,12));

		tablePanel = new JPanel();
		tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
		contentPane.add(tablePanel, BorderLayout.CENTER);
		
		JPanel horizontalPanel = new JPanel(new GridLayout());
		JLabel label = new JLabel("COBROS", JLabel.CENTER);
		label.setFont(new Font("Montserrat Black", Font.PLAIN, 27));
		horizontalPanel.add(label);
		tablePanel.add(horizontalPanel);
		
		tableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
                return false; // Hacer que todas las celdas sean no editables
            }
        };
        tableModel.addColumn("id");
        tableModel.addColumn("transaccion");
        tableModel.addColumn("Cuota Nro");
        tableModel.addColumn("Monto");
        tableModel.addColumn("Pago realizado");
        tableModel.addColumn("Forma de pago");
        tableModel.addColumn("Vehiculo recibido");
        
		table = new JTable(tableModel);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                	int row = table.getSelectedRow();
                	if(row != -1){
                		Cuota c = cuotaDao.getCuotaById((Long)tableModel.getValueAt(row, 0));
	            		if(c.getDatePayed() != null)
                			dp.setSelectedDate(c.getDatePayed());
                		else
                			dp.clearSelectedDate();
                		
                	}
                }
			}		
		});
		table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);
        table.getColumnModel().getColumn(1).setMinWidth(0);
        table.getColumnModel().getColumn(1).setMaxWidth(0);
        table.getColumnModel().getColumn(1).setPreferredWidth(0);
        table.getColumnModel().getColumn(2).setMinWidth(93);
        table.getColumnModel().getColumn(2).setMaxWidth(93);
        table.getColumnModel().getColumn(2).setPreferredWidth(93);
        
        JScrollPane scroll = new JScrollPane(table); 
		tablePanel.add(scroll);
		
		inputPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
		contentPane.add(inputPanel, BorderLayout.EAST);
		
		horizontalPanel = new JPanel(new GridLayout());
		horizontalPanel.add(new JLabel(""));
		inputPanel.add(horizontalPanel);
		horizontalPanel = new JPanel(new GridLayout());
		horizontalPanel.add(new JLabel(""));
		inputPanel.add(horizontalPanel);
		
		horizontalPanel = new JPanel(new GridLayout());
		horizontalPanel.add(new JLabel("Cantidad de cuotas", JLabel.RIGHT));
		quantitySpinner = new JSpinner();
		quantitySpinner.setValue(1);
		horizontalPanel.add(quantitySpinner);
		inputPanel.add(horizontalPanel);
		
		horizontalPanel = new JPanel(new GridLayout());
		horizontalPanel.add(new JLabel("Monto de la/s Cuotas", JLabel.RIGHT));
		amountTextField = new JTextField();
		horizontalPanel.add(amountTextField);
		inputPanel.add(horizontalPanel);

		horizontalPanel = new JPanel(new GridLayout());
		horizontalPanel.add(new JLabel("Forma de Pago", JLabel.RIGHT));
		payMethodTextField = new JTextField();
		horizontalPanel.add(payMethodTextField);
		inputPanel.add(horizontalPanel);
		
		horizontalPanel = new JPanel(new GridLayout());
		horizontalPanel.add(new JLabel("Patente de Vehiculo"));
		plateTextField = new JTextField();
		plateTextField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(OPCIONAL)");
		horizontalPanel.add(plateTextField);
		inputPanel.add(horizontalPanel);
		
		horizontalPanel = new JPanel(new GridLayout());
		JButton confirmButton = new JButton("Agregar");
		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int lastQuote;
				if(tableModel.getRowCount() == 0)
					lastQuote = 0;
				else
					lastQuote = (int)tableModel.getValueAt(0, 2);
				
				int quotes = (int)quantitySpinner.getValue();
				int amount = Integer.parseInt(amountTextField.getText());
				String payMethod = payMethodTextField.getText();
				String plate = plateTextField.getText();
				
				for(int i = 0; i < quotes; i++) {
					Cuota c = new Cuota(amount, ++lastQuote, payMethod, plate, transaccionDao.getTransaccionById(transaccionId));
					cuotaDao.save(c);
				}
				loadTable();
			}
		});
		horizontalPanel.add(confirmButton);
		JButton deleteButton = new JButton("Eliminar");
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() == -1)
					JOptionPane.showMessageDialog(null, "Ningun Pago seleccionado");
				else {
						try {
							cuotaDao.delete((Long)tableModel.getValueAt(table.getSelectedRow(), 0));
							loadTable();
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, e1.getLocalizedMessage());
						}
					}
			}
		});
		horizontalPanel.add(deleteButton);
		inputPanel.add(horizontalPanel);
		
		horizontalPanel = new JPanel(new GridLayout());
		horizontalPanel.add(new JLabel("Fecha Pago:", JLabel.RIGHT));
		JFormattedTextField ft = new JFormattedTextField();
		dp = new DatePicker();
		dp.setDateSelectionMode(DatePicker.DateSelectionMode.SINGLE_DATE_SELECTED);
		dp.setBackground(Color.GRAY);
		dp.setDateFormat("dd/MM/yyyy");
		dp.setEditor(ft);
		horizontalPanel.add(ft);
		inputPanel.add(horizontalPanel);

		
		dp.addDateSelectionListener(new DateSelectionListener() {
			@Override
			public void dateSelected(DateSelectionEvent e) {
                	int row = table.getSelectedRow();
	            	if(row != -1 && dp.getSelectedDate() != null){
	            		Cuota c = cuotaDao.getCuotaById((Long)tableModel.getValueAt(row, 0));
	            		c.setDatePayed(dp.getSelectedDate());
	            		tableModel.setValueAt(dp.getSelectedDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), row, 4);
	            		cuotaDao.save(c);
	            	}
			}	
		});
		loadTable();
	}

	private void loadTable() {
		tableModel.setRowCount(0);
		
		List<Cuota> cuotas = cuotaDao.getCuotasByTransaccion(transaccionId);
		for(Cuota c : cuotas) {
			Object[] row = {c.getId(), c.getTransaccion().getId(), c.getQuoteNumber(), c.getAmount(), c.getDatePayed(), c.getPayMethod(), c.getVehiclePlate()};
			tableModel.addRow(row);
		}
	}
	
}
