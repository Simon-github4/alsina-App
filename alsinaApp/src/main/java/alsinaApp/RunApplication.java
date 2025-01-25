package alsinaApp;

import views.Dashboard;

public class RunApplication {
	
	public static void main(String[] args) {
		
		Dashboard d  = Dashboard.getInstance();
		d.setVisible(true);
		d.setLocationRelativeTo(null);					
		/*
		        String classpath = System.getProperty("java.class.path");
		        System.out.println("Classpath:");
		        for (String path : classpath.split(":")) { // Use ";" on Windows
		            System.out.println(path);
		        }
		 */
		
	}

}
