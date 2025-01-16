package alsinaApp;

import views.Dashboard;

public class RunApplication {
	
	public static void main(String[] args) {
		
		Dashboard d  = Dashboard.getInstance();
		d.setVisible(true);
		d.setLocationRelativeTo(null);					
		
	}

}
