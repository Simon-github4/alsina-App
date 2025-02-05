package views.temporalFrames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import entities.Compra;
import entities.Gasto;
import entities.Vehiculo;
import entities.Venta;
import entityManagers.GastoDao;
import entityManagers.TransaccionDao;

public class VehiculoVentaResumenFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel tablePanel;
	private JTable table;
	private DefaultTableModel tableModel;
	private TransaccionDao transaccionDao;
	private GastoDao gastoDao;
	private JLabel saldoLabel;
	private JLabel ingresosLabel;
	private JLabel egresosLabel;
	private JTextField saldoTextField;
		
	public VehiculoVentaResumenFrame(TransaccionDao transaccionDao1, GastoDao gastoDao1, Vehiculo v) {
		this.transaccionDao = transaccionDao1;
		this.gastoDao = gastoDao1;
		
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
		JLabel label = new JLabel("RESUMEN", JLabel.CENTER);
		label.setFont(new Font("Montserrat Black", Font.PLAIN, 27));
		horizontalPanel.add(label);
		//tablePanel.add(horizontalPanel);
		
		tableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
                return false; // Hacer que todas las celdas sean no editables
            }
        };
        tableModel.addColumn("id");
        tableModel.addColumn("Vehiculo");
        tableModel.addColumn("Monto");
        tableModel.addColumn("Fecha");
        tableModel.addColumn("Descripcion");
        tableModel.addColumn("Categoria");
        //tableModel.addColumn("Cliente");
        
		table = new JTable(tableModel);
		
		table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);
        //table.getColumnModel().getColumn(2).setMinWidth(93);
        //table.getColumnModel().getColumn(2).setMaxWidth(93);
        //table.getColumnModel().getColumn(2).setPreferredWidth(93);  
        table.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;
			@Override
            public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column
            ) {
                // Call the parent method to set up the default rendering
                Component c = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column
                );
                try {
	                if((long) value < 0)
	                	c.setBackground(Color.RED);
	                else
	                	c.setBackground(Color.GREEN);
                }catch(ClassCastException e) {
	            	 if((int) value < 0)
		                	c.setBackground(Color.RED);
		                else
		                	c.setBackground(Color.GREEN);
	             }
                /*if (row % 2 == 0) 
                    c.setBackground(Color.LIGHT_GRAY); 
                else
                    c.setBackground(Color.WHITE); 
                */    
                if (isSelected) 
                    c.setBackground(super.getBackground()); // Highlight selected cells-            

                
                return c;
            }
        });
        
        JScrollPane scroll = new JScrollPane(table); 
		tablePanel.add(scroll);
		
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
		contentPane.add(south, BorderLayout.SOUTH);
		
		loadTable(v);
	}

	private void loadTable(Vehiculo v) {
		tableModel.setRowCount(0);
		long ingresos=0, egresos = 0;
		
		Venta venta = transaccionDao.getVentaByVehicle(v);
		if(venta != null) {	
			Object[] row2 = {venta.getId(), v, venta.getAmount(), venta.getDate(), venta.getDescription(), "Venta de Vehiculo"};
			tableModel.addRow(row2);
			ingresos += venta.getAmount();
		}
		
		Compra compra = transaccionDao.getCompraByVehicle(v);
		if(compra != null) {	
			Object[] row = {compra.getId(), v, compra.getAmount() * -1, compra.getDate(), compra.getDescription(), "Compra de Vehiculo"};
			tableModel.addRow(row);
			egresos -= compra.getAmount();
		}
		
		List<Gasto> Gastos = gastoDao.getGastosByVehicle(v);
		for(Gasto c : Gastos) {
			Object[] row = {c.getId(), v, c.getAmount() * -1, c.getDate(), c.getDescription(), c.getDestination()};
			tableModel.addRow(row);
			egresos -= c.getAmount();
		}
		
		long saldo = ingresos + egresos;
		saldoTextField.setText(String.valueOf(saldo));
    	saldoTextField.setForeground(Color.BLACK);
		if(saldo < 0 )
			saldoTextField.setBackground(Color.red);
        else
        	saldoTextField.setBackground(new Color(0,245,0));

		ingresosLabel.setText("Ingresos: $"+ ingresos);
        egresosLabel.setText("Egresos: $"+ egresos);
    }


}
