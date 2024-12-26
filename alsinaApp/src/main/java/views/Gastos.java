package views;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.BoxLayout;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import java.awt.Font;

public class Gastos extends JFrame{
	private JTable table;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	
	public Gastos() {
		getContentPane().setLayout(null);
		setSize(1075,700);
		setLocationRelativeTo(null);
		
		table = new JTable();
		table.setBounds(10, 180, 1028, 380);
		getContentPane().add(table);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(232, 129, 171, 20);
		getContentPane().add(textField);
		
		JLabel lblNewLabel_1 = new JLabel("Fechas");
		lblNewLabel_1.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel_1.setBounds(232, 99, 104, 20);
		getContentPane().add(lblNewLabel_1);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(439, 128, 171, 21);
		getContentPane().add(comboBox);
		
		JLabel lblNewLabel_1_1 = new JLabel("Tipo");
		lblNewLabel_1_1.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel_1_1.setBounds(439, 99, 104, 20);
		getContentPane().add(lblNewLabel_1_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(657, 130, 171, 20);
		getContentPane().add(textField_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Patente/dominio");
		lblNewLabel_1_1_1.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel_1_1_1.setBounds(657, 99, 89, 20);
		getContentPane().add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel = new JLabel("Gastos ");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 47));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(374, 11, 288, 53);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("Total Ingresos");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_2.setBounds(10, 583, 171, 37);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("Total Egresos");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_2_1.setBounds(374, 583, 171, 37);
		getContentPane().add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("Resumen");
		lblNewLabel_2_1_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_2_1_1.setBounds(746, 583, 114, 37);
		getContentPane().add(lblNewLabel_2_1_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(191, 589, 171, 32);
		getContentPane().add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(555, 589, 171, 32);
		getContentPane().add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(856, 589, 171, 32);
		getContentPane().add(textField_4);
	}
}
