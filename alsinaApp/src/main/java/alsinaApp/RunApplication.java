package alsinaApp;

import java.util.HashMap;
import java.util.Map;

import com.formdev.flatlaf.FlatIntelliJLaf;

import entityManagers.UsuarioDao;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import views.Dashboard;
import views.temporalFrames.Login;

public class RunApplication {
	
	private static EntityManagerFactory emf;
	
	public static void main(String[] args) {

		Map<String, String> persistenceMap = new HashMap<String, String>();
		
		String ip = ((System.getProperty("user.home").equalsIgnoreCase("C:\\Users\\simon"))?"192.168.0.178": "192.168.0.108");
		ip="localhost";
		persistenceMap.put("jakarta.persistence.jdbc.url", "jdbc:postgresql://"+ip+":5432/consecionaria");
		
		String password = (System.getProperty("user.home").equalsIgnoreCase("C:\\Users\\simon"))?"niidea2004": "danipersello";
		persistenceMap.put("jakarta.persistence.jdbc.password", password);
		
		emf = Persistence.createEntityManagerFactory(("persistencia"), persistenceMap);
		
		Login login = new Login(new UsuarioDao(emf));
		login.setVisible(true);
		login.setLocationRelativeTo(null);	
	
		/*		
		        String classpath = System.getProperty("java.class.path");
		        System.out.println("Classpath:");
		        for (String path : classpath.split(";")) { // Use ";" on Windows
		            System.out.println(path);
		        }
		*/
	}
	public static void startApp() {
		Dashboard d  = new Dashboard(emf);
		d.setVisible(true);
		d.setLocationRelativeTo(null);
	}

}
