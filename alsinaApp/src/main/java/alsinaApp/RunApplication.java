package alsinaApp;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;

import org.json.JSONException;

import entityManagers.UsuarioDao;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import utils.DolarData;
import utils.ViewUtils;
import views.Dashboard;
import views.temporalFrames.Login;

public class RunApplication {
	
	private static EntityManagerFactory emf;
	private static Dashboard d;
	
	public static void main(String[] args) {

		Map<String, String> persistenceMap = new HashMap<String, String>();
		
		String ip = ((System.getProperty("user.home").equalsIgnoreCase("C:\\Users\\simon"))?"192.168.0.178": "192.168.0.108");
		ip="localhost";
		persistenceMap.put("jakarta.persistence.jdbc.url", "jdbc:postgresql://"+ip+":5432/consecionaria");
		
		String password = (System.getProperty("user.home").equalsIgnoreCase("C:\\Users\\simon"))?"niidea2004": "danipersello";
		persistenceMap.put("jakarta.persistence.jdbc.password", password);
		
		JFrame f =new JFrame();		f.setLayout(new BorderLayout());
		JLabel img = new JLabel();
		ViewUtils.setIconToLabel(img, "/resources/imgs/Logo Alsina.png", 714, 573);
		f.add(img, BorderLayout.CENTER);
		f.setBounds(100, 100, 714, 573);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		
		emf = Persistence.createEntityManagerFactory(("persistencia"), persistenceMap);
		
		SwingUtilities.invokeLater(()->{ 		
			f.dispose();
			Login login = new Login(new UsuarioDao(emf));
			login.setVisible(true);
			login.setLocationRelativeTo(null);
		});
		
		d  = new Dashboard(emf);
		try (DolarData dolar = new DolarData()) {
			d.setHistoricalValues(dolar.getHistoricalValues());
			d.setActualValue(dolar.getActualValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void startApp() {
		d.setVisible(true);
		d.setLocationRelativeTo(null);
	}

}
