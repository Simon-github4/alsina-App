package views;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class MarcasSucursalesClientesFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTabbedPane tabbedPane; 

	
	public MarcasSucursalesClientesFrame() {
		//super("Informacion");
		setVisible(true);
		setBounds(100, 100, 1110, 860);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new GridLayout());
		//contentPane.setLayout(null);

		setContentPane(contentPane);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		tabbedPane.addTab("Clientes", new ClientesForm());
		tabbedPane.addTab("Marcas", new MarcasForm());
		tabbedPane.addTab("Sucursales", new SucursalesForm());

		contentPane.add(tabbedPane);
		this.pack(); 

	}

}
