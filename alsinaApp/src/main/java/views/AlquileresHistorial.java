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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatClientProperties;

import entities.Alquiler;
import entities.Cliente;
import entities.VehiculoAlquilable;
import entityManagers.AlquilerDao;
import interfaces.ColorearTabla;
import interfaces.GetTabbedPane;
import interfaces.PdfUtils;
import interfaces.ViewUtils;
import raven.datetime.DatePicker;

public class AlquileresHistorial extends JPanel implements GetTabbedPane{
	

	private static final long serialVersionUID = 1L;
	private JPanel tablePanel;
	private DefaultTableModel tableModel;
	private JTable table;
	private JPanel contentPane;
	private JPanel northPanel;
	private JTextField searchTextField;
	private DatePicker dpFilters;
	private JFormattedTextField filterDatesField;
	private JButton searchButton;
	private AlquilerDao AlquilerDao;
	private JLabel messageLabel;
	private JTextField newPaymentTextField;
	private JTextField remainingTextField;
	private JTextField paidTextField;
	
	
	public AlquileresHistorial(AlquilerDao AlquilerDao) {
		this.AlquilerDao = AlquilerDao;
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 5));
		contentPane.setPreferredSize(new Dimension(1100, 750));
		this.setSize(1100, 850);
		this.setLayout(new BorderLayout());
		this.add(contentPane, BorderLayout.CENTER);
		
		northPanel = new JPanel();
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
		contentPane.add(northPanel, BorderLayout.NORTH);
		
		JPanel horizontalPanel = new JPanel(new GridLayout());
		
		JLabel titulo = new JLabel("HISTORIAL ALQUILERES", JLabel.CENTER);
		titulo.setFont(new Font("Montserrat Black", Font.BOLD, 46));
		horizontalPanel.add(titulo);		
		northPanel.add(horizontalPanel);
		
		JLabel l = new JLabel("");
		l.setPreferredSize(new Dimension(WIDTH, 15));
		northPanel.add(l);
		
		horizontalPanel = new JPanel(new GridLayout(1,0));
		horizontalPanel.add(new JLabel("Patente", JLabel.RIGHT));
		searchTextField = new JTextField();
		searchTextField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Presione Enter para buscar");
		searchTextField.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);
		horizontalPanel.add(searchTextField);
		horizontalPanel.add(new JLabel("Fechas", JLabel.RIGHT));
		dpFilters = new DatePicker();
		dpFilters.setDateSelectionMode(DatePicker.DateSelectionMode.BETWEEN_DATE_SELECTED);
		dpFilters.setUsePanelOption(true);
		dpFilters.setBackground(Color.GRAY);
		dpFilters.setDateFormat("dd/MM/yyyy");
		filterDatesField = new JFormattedTextField();
		dpFilters.setEditor(filterDatesField);
		horizontalPanel.add(filterDatesField);
		horizontalPanel.add(new JLabel(""));
		horizontalPanel.setPreferredSize(new Dimension(WIDTH, 40));
		northPanel.add(horizontalPanel);
					
		l = new JLabel("");
		l.setPreferredSize(new Dimension(WIDTH, 15));
		northPanel.add(l);

		horizontalPanel = new JPanel(new GridLayout(1,0));
		horizontalPanel.add(new JLabel(""));
		searchButton =new JButton("Buscar");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadTable(searchTextField.getText(), dpFilters.getSelectedDateRange());
				clearFields();
			}
		});
		horizontalPanel.add(searchButton);
		ViewUtils.setIconToButton(searchButton, "/resources/imgs/lupa.png", 32, 32);
		searchTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					searchButton.doClick();
			}
		});
		JButton deleteButton = new JButton("Eliminar Contrato");
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
	
		JButton updateButton = new JButton("Modificar Contrato");
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
		JButton excelButton = new JButton("Imprimir Contrato");
		excelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow()==-1)
					setMessage("Ningun Alquiler seleccionado", false);
				else {
					try {
						int row =table.getSelectedRow();
						Long id = (Long)tableModel.getValueAt(row, 10);
						
						Alquiler alquiler = AlquilerDao.getAlquilerById(id);
						//alquiler.openExcelPrint();
						PdfUtils.openPdfToPrint(alquiler);
					/*} catch (FileNotFoundException ex) {
						setMessage(ex.getMessage(), false);
						ex.printStackTrace();
					*/  } catch(IllegalArgumentException il) {
							setMessage("Los datos a imprimirse NO pueden estar Vacios", false);
							il.printStackTrace();
						} catch (/*IO*/Exception ex) {
						setMessage(ex.getMessage(), false);
						ex.printStackTrace();
					}	
				}
			}
		});
		horizontalPanel.add(excelButton);
		ViewUtils.setIconToButton(excelButton, "/resources/imgs/sobresalir.png", 32, 32);
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
        tableModel.addColumn("Vehiculo");
        tableModel.addColumn("Inicio");
        tableModel.addColumn("Fin");
        tableModel.addColumn("Cliente");
        tableModel.addColumn("Monto Total");
        tableModel.addColumn("Lugar Destino");
        tableModel.addColumn("KM Salida");
        tableModel.addColumn("KM Retorno");
        tableModel.addColumn("Comb.Salida");
        tableModel.addColumn("Comb.Retorno");
        tableModel.addColumn("Id");
        
		table = new JTable(tableModel);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                	int row = table.getSelectedRow();
                	if(row != -1){
                		long id = (long)tableModel.getValueAt(row, 10);

                		int amount = (int) tableModel.getValueAt(row, 4);
                		int paiduntil = AlquilerDao.getAlquilerById(id).getPricePaid();
								
                		paidTextField.setText(String.valueOf(paiduntil));
						remainingTextField.setText(String.valueOf(amount - paiduntil));
                	}
                }
			}
			
		});
		table.getColumnModel().getColumn(4).setCellRenderer(new ColorearTabla(AlquilerDao));
		table.getColumnModel().getColumn(10).setMaxWidth(0);
		table.getColumnModel().getColumn(10).setMinWidth(0);
		table.getColumnModel().getColumn(10).setPreferredWidth(0);
		table.getColumnModel().getColumn(3).setPreferredWidth(190);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(5).setPreferredWidth(55);
		table.getColumnModel().getColumn(6).setPreferredWidth(55);
		table.getColumnModel().getColumn(7).setPreferredWidth(40);
		table.getColumnModel().getColumn(8).setPreferredWidth(40);			
		table.setShowGrid(true);
			
		JScrollPane scroll = new JScrollPane(table);
		
		horizontalPanel = new JPanel(new GridLayout());
		horizontalPanel.add(scroll);
		tablePanel.add(horizontalPanel);

/*------------------------------------------------------------------------------------------------------------------------------------------------------*/

		JPanel east = new JPanel();
		east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
		east.setPreferredSize(new Dimension(180, HEIGHT));
		east.setBorder(new TitledBorder(new LineBorder(Color.black, 2), "Resumen", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0,43,255)));
		
		horizontalPanel = new JPanel(new GridLayout(1,0));
		horizontalPanel.add(new JLabel("Pagado", JLabel.CENTER));
		east.add(horizontalPanel);

		horizontalPanel = new JPanel(new GridLayout());
		paidTextField = new JTextField();
		paidTextField.setEditable(false);
		horizontalPanel.add(paidTextField);
		east.add(horizontalPanel);
		
		horizontalPanel = new JPanel(new GridLayout(1,0));
		horizontalPanel.add(new JLabel("Restante", JLabel.CENTER));
		east.add(horizontalPanel);

		horizontalPanel = new JPanel(new GridLayout());
		remainingTextField = new JTextField();
		remainingTextField.setEditable(false);
		horizontalPanel.add(remainingTextField);
		east.add(horizontalPanel);
		
		horizontalPanel = new JPanel(new GridLayout());
		horizontalPanel.add(new JLabel("Nuevo Pago", JLabel.CENTER));
		east.add(horizontalPanel);
		
		horizontalPanel = new JPanel(new GridLayout(1,0));
		newPaymentTextField = new JTextField();
		horizontalPanel.add(newPaymentTextField);
		east.add(horizontalPanel);

		horizontalPanel = new JPanel(new GridLayout(1,0));
		horizontalPanel.add(new JLabel(""));
		east.add(horizontalPanel);

		horizontalPanel = new JPanel(new GridLayout(1,0));
		JButton confirm = new JButton("Actualizar");
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow()==-1)
					setMessage("Ningun Alquiler seleccionado", false);
				else {
					try {
						int newPayment = Integer.parseInt(newPaymentTextField.getText());
						AlquilerDao.updatePricePaid((Long)tableModel.getValueAt(table.getSelectedRow(), 10), newPayment);
						clearFields();
					}catch(NumberFormatException e1) {
						setMessage("Asegurese de introducir datos validos", false);
					}catch(Exception e1) {
						setMessage("Ocurrio un Error:"+e1.getMessage(), false);						
					}
				}
			}

		});
		horizontalPanel.add(confirm);
		east.add(horizontalPanel);
		
		contentPane.add(east, BorderLayout.EAST);
		
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
		
		searchButton.doClick();
	}
	
	private void loadTable(String plate, LocalDate[] dates) {
        tableModel.setRowCount(0);

		List<Alquiler> alquileres = AlquilerDao.getAlquileresByPlateAndDate(plate, dates);
		for(Alquiler a : alquileres) {
			Object[] row = {a.getVehicle(), a.getStart().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), a.getEnd().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
							a.getClient(), a.getTotalPrice(),a.getPlace(), a.getDepartureKm(), a.getReturnKm(), a.getGasExit(), a.getGasReturn(), a.getId()};
			tableModel.addRow(row);
		}
	}

	private void update() {
		int row =table.getSelectedRow();
		
		Long id = (Long) tableModel.getValueAt(row, 10);
		Alquiler alquiler = AlquilerDao.getAlquilerById(id);
		
		//Alquiler alquiler = new Alquiler(start, end, client, vehicle, price, kmD, kmR, cbE, cbR);
		//alquiler.setId(id);
		
		JTabbedPane t = getTabbedPane();
		t.setSelectedIndex(1);
		AlquileresForm a = (AlquileresForm)t.getSelectedComponent();
		a.setUpdateForm(alquiler);
		
	}
	
	private void delete() {
		long id = (long)tableModel.getValueAt(table.getSelectedRow(), 10);
		
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
		paidTextField.setText("");
		newPaymentTextField.setText("");
		remainingTextField.setText("");
		table.clearSelection();
	}
	
}
