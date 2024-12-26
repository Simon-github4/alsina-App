package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatIntelliJLaf;

import java.awt.Color;
import java.awt.Component;

public class Dashboard extends JFrame{

	private JPanel mainPanel;
	
	public Dashboard() {
		
		setStyling();
		
		setSize(719,448);
		getContentPane().setLayout(new BorderLayout(40, 40));
		
		mainPanel= new JPanel();
		//mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		mainPanel.setLayout(new GridLayout(3, 4, 15, 15));
		mainPanel.setBackground(new Color(0, 102, 204));
		getContentPane().add(mainPanel, BorderLayout.CENTER);
				
		JButton btnNewButton_2 = new JButton("Autos Alquiler");
		btnNewButton_2.setBounds(45, 35, 128, 100);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Alquileres().setVisible(true);
			}
		});
		mainPanel.add(btnNewButton_2);
		
		JButton btnNewButton_2_1 = new JButton("Autos a la Venta");
		btnNewButton_2_1.setBounds(208, 35, 128, 100);
		mainPanel.add(btnNewButton_2_1);
		
		JButton btnNewButton_2_2 = new JButton("Motos");
		btnNewButton_2_2.setBounds(374, 35, 128, 100);
		mainPanel.add(btnNewButton_2_2);
		
		JButton btnNewButton_2_3 = new JButton("Clientes");
		btnNewButton_2_3.setBounds(45, 194, 128, 100);
		mainPanel.add(btnNewButton_2_3);
		
		JButton btnNewButton_2_4 = new JButton("Marcas");
		btnNewButton_2_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			new MarcasForm().setVisible(true);
			}
		});
		btnNewButton_2_4.setBounds(208, 194, 128, 100);
		mainPanel.add(btnNewButton_2_4);
		
		JButton btnNewButton_2_9 = new JButton("Sucursales");
		btnNewButton_2_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			//new SucursalesForm().setVisible(true);
			}
		});
		btnNewButton_2_9.setBounds(208, 194, 128, 100);
		mainPanel.add(btnNewButton_2_9);
		
		JButton btnNewButton_2_5 = new JButton("Ventas/Compras");
		btnNewButton_2_5.setBounds(374, 194, 128, 100);
		mainPanel.add(btnNewButton_2_5);
		
		JButton btnNewButton_2_2_1 = new JButton("Baterias");
		btnNewButton_2_2_1.setBounds(542, 35, 128, 100);
		mainPanel.add(btnNewButton_2_2_1);
		
		JButton btnNewButton_2_5_1 = new JButton("Ver Reporte total");
		btnNewButton_2_5_1.setBounds(542, 194, 128, 100);
		mainPanel.add(btnNewButton_2_5_1);
		
		JButton btnNewButton = new JButton("Gastos");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Gastos().setVisible(true);
			}
		});
		btnNewButton.setBounds(45, 320, 628, 62);
		mainPanel.add(btnNewButton);
		
		for(Component b : mainPanel.getComponents()) {
			((JButton) b).setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3, true));
		}
		
	}

	private void setStyling() {
		FlatIntelliJLaf.setup();	
		
		UIManager.put("Button.arc", 15);		
		UIManager.put("Button.foreground", Color.BLACK);		
		//UIManager.put("Button.background", Color.BLUE);		
		//UIManager.put("Button.borderColor", Color.BLUE);		
		UIManager.put("Button.outline", Color.BLUE); // Outline color

		UIManager.put("TextComponent.arc", 10);		
		UIManager.put("Component.arc", 10);		
		UIManager.put("Component.innerFocusWidth", 1);		
		UIManager.put("Table.alternateRowColor", (Color.LIGHT_GRAY));
		//UIManager.put("Table.showVerticalLines", "value = true");	
	    UIManager.put("defaultFont", new Font("Montserrat", Font.TRUETYPE_FONT, 13));
		//contentPane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3, true)); // Soft shadow
		
	}

}
