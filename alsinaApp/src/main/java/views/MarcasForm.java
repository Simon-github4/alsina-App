package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class MarcasForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel inputPanel;
	private JPanel tablePanel;
	private JTable table;
	private DefaultTableModel tableModel;
	private JLabel messageLabel;
	
	public MarcasForm() {

		setBounds(100, 100, 1050, 700);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 5));
		
		inputPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
		contentPane.add(inputPanel, BorderLayout.NORTH);
		

		JPanel horizontalPanel = new JPanel(new GridLayout());
		
		JLabel titulo = new JLabel("MARCAS", JLabel.CENTER);
		titulo.setFont(new Font("Montserrat Black", Font.BOLD, 46));
		horizontalPanel.add(titulo);		
		inputPanel.add(horizontalPanel);

		horizontalPanel = new JPanel(new GridLayout());
		horizontalPanel.add(new JLabel("Descripcion", JLabel.RIGHT));
		horizontalPanel.add(new JTextField(30));
		horizontalPanel.add(new JLabel("Id", JLabel.RIGHT));
		JTextField l = new JTextField(10);	 l.setEditable(false);
		horizontalPanel.add(l);
		horizontalPanel.add(new JLabel("", JLabel.RIGHT));
		inputPanel.add(horizontalPanel);		
		
		horizontalPanel = new JPanel(new GridLayout());
		horizontalPanel.setPreferredSize(new Dimension(WIDTH, 30));
		inputPanel.add(horizontalPanel);		
		
		horizontalPanel = new JPanel(new FlowLayout());
		JButton confirm = new JButton("Confirmar");
        confirm.setPreferredSize(new Dimension(250,40));
		JButton delete = new JButton("Eliminar");
		delete.setPreferredSize(new Dimension(250,40));
		JButton cancel = new JButton("Cancelar");
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
        tableModel.addColumn("Id");
        
		table = new JTable(tableModel);
		table.getColumnModel().getColumn(1).setMaxWidth(100);
		table.getColumnModel().getColumn(1).setMinWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.setShowGrid(true);
		
		tablePanel = new JPanel();
		tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
		contentPane.add(tablePanel, BorderLayout.CENTER);
		
		JScrollPane scroll = new JScrollPane(table);
		
		horizontalPanel = new JPanel(new GridLayout());
		horizontalPanel.add(scroll);
		tablePanel.add(horizontalPanel);			
		
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		contentPane.add(south, BorderLayout.SOUTH);
		
		horizontalPanel = new JPanel(new GridLayout());

		messageLabel = new JLabel(" alo", SwingConstants.CENTER);
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        messageLabel.setBackground(Color.RED);
        messageLabel.setOpaque(false);		
        messageLabel.setPreferredSize(new Dimension(500,70));
        JButton back = new JButton("Volver");

		horizontalPanel.add(messageLabel);
		horizontalPanel.add(back);
		
		south.add(horizontalPanel);

		
	}

}
