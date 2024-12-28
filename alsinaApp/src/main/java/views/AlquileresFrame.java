package views;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class AlquileresFrame extends JFrame{
	
	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	
	public AlquileresFrame() {
		setSize(new Dimension(1100, 850));
		//setSize(1100, 850);
		setVisible(true);
		setLocationRelativeTo(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane);
		
		tabbedPane.addTab("Vehiculos de Alquiler", new AutosAlquilerForm());
		
		
	}
	
	

}
