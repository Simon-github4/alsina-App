package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import com.itextpdf.layout.borders.Border;

import entities.Sucursal;
import entityManagers.SucursalDao;

public class SucursalesForm extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JPanel inputPanel;
	private JPanel tablePanel;
	private JTable table;
	private DefaultTableModel tableModel;
	private JLabel messageLabel;
	private JTextField descriptionTextField;
	private SucursalDao SucursalDao;

	private JTextField adressTextField;

	public SucursalesForm(SucursalDao sdao) {
			SucursalDao = sdao;
			
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.setLayout(new BorderLayout(0, 5));
			contentPane.setPreferredSize(new Dimension(1050, 670));
			this.setSize(1100, 850);
			this.setLayout(new BorderLayout());
			this.add(contentPane, BorderLayout.CENTER);
			
			inputPanel = new JPanel();
			inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
			contentPane.add(inputPanel, BorderLayout.NORTH);
			

			JPanel horizontalPanel = new JPanel(new GridLayout());
			
			JLabel titulo = new JLabel("CONCESIONARIAS", JLabel.CENTER);
			titulo.setFont(new Font("Montserrat Black", Font.BOLD, 46));
			horizontalPanel.add(titulo);		
			inputPanel.add(horizontalPanel);

			horizontalPanel = new JPanel(new GridLayout());
			horizontalPanel.add(new JLabel("Descripcion", JLabel.RIGHT));
			descriptionTextField = new JTextField("",30);
			horizontalPanel.add(descriptionTextField);
			horizontalPanel.add(new JLabel("Domicilio", JLabel.RIGHT));
			adressTextField = new JTextField("",30);
			horizontalPanel.add(adressTextField);
			horizontalPanel.add(new JLabel("", JLabel.RIGHT));
			inputPanel.add(horizontalPanel);		
			
			horizontalPanel = new JPanel(new GridLayout());
			horizontalPanel.setPreferredSize(new Dimension(WIDTH, 30));
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
	        tableModel.addColumn("Id");
	        tableModel.addColumn("Descripcion");
	        tableModel.addColumn("Domicilio");
	        
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
							String description = (String) tableModel.getValueAt(row, 1);
							String adress = (String) tableModel.getValueAt(row, 2);
							
							descriptionTextField.setText(description);
							adressTextField.setText(adress);
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
		
		private void insert() {
			String description = descriptionTextField.getText();
			String adress = adressTextField.getText();

			Sucursal m = new Sucursal(description, adress);
			
			if(table.getSelectedRow() != -1) {
				long id = (long)tableModel.getValueAt(table.getSelectedRow(), 0);
				m.setId(id);
				m.setLogoPath(SucursalDao.getSucursalById(id).getLogoPath());
			}
			SucursalDao.save(m);
			clearFields();
			loadTable();
		}
		
		private void delete() {
			long id = (long)tableModel.getValueAt(table.getSelectedRow(), 1);
			try {
				if(JOptionPane.showConfirmDialog(null, "Desea eliminar la consecionaria: "+(String)tableModel.getValueAt(table.getSelectedRow(), 1),
													"", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
					SucursalDao.delete(id);
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

			List<Sucursal> sucursales = SucursalDao.getSucursales();
			for(Sucursal m : sucursales) {
				Object[] row = {m.getId(), m.getDescription(), m.getAdress()};
				tableModel.addRow(row);
			}
		}

		private boolean validateFields() {
			if(descriptionTextField.getText().isBlank()) {
				setMessage("La Descripcion no puede estar Vacia");
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
			descriptionTextField.setText("");
			adressTextField.setText("");
			messageLabel.setText("");
	        messageLabel.setOpaque(false);
		}
		
		

}
