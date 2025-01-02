package views;

import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import entityManagers.ClienteDao;
import entityManagers.DestinoDao;
import entityManagers.MarcaDao;
import entityManagers.SucursalDao;

public class MarcasSucursalesClientesFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTabbedPane tabbedPane;

	
	public MarcasSucursalesClientesFrame(MarcaDao mdao, SucursalDao sdao, ClienteDao cdao, DestinoDao ddao) {
		//super("Informacion");
		setVisible(true);
		setBounds(100, 100, 1110, 860);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new GridLayout());

		setContentPane(contentPane);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		tabbedPane.addTab("Clientes", new ClientesForm(cdao));
		tabbedPane.addTab("Marcas", new MarcasForm(mdao));
		tabbedPane.addTab("Sucursales", new SucursalesForm(sdao));
		tabbedPane.addTab("Destinos", new DestinosForm(ddao));
		

		contentPane.add(tabbedPane);
		this.pack(); 

	}

}
