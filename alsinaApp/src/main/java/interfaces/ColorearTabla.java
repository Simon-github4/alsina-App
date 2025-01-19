package interfaces;

import java.awt.Color;
import java.awt.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import entities.Alquiler;
import entityManagers.AlquilerDao;

public class ColorearTabla extends DefaultTableCellRenderer{

	private static final long serialVersionUID = 1L;
	private static final int CELL_COLORED = 6;
	private AlquilerDao alquilerDao;
	
		public ColorearTabla(AlquilerDao alquilerDao) {
			this.alquilerDao=alquilerDao;
		}

		@Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

			long id = (long)table.getValueAt(row, 10);
       		
	            if (isRentEnd(table, row)) {
	            	this.setBackground(Color.GREEN); 
	            	this.setForeground(Color.BLACK); 
	            }else if(alquilerDao.getAlquilerById(id).getIsBooked()){
	            	this.setBackground(Color.YELLOW); 
	            	this.setForeground(Color.BLACK);
	            }else {
	            	this.setBackground(Color.RED); 
	            	this.setForeground(Color.WHITE); 
	            }
	            if(isSelected) {
		            if (isRentEnd(table, row)) {
		            	this.setBackground(Color.GREEN); 
		            	this.setForeground(Color.BLACK); 
		            }else if(alquilerDao.getAlquilerById(id).getIsBooked()){
		            	this.setBackground(Color.YELLOW); 
		            	this.setForeground(Color.BLACK);
		            }else {
		            	this.setBackground(Color.RED); 
		            	this.setForeground(Color.WHITE); 
		            }
	            }
	            	
	      	super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            return this;
        }
    
		public boolean isRentEnd(JTable table, int row) {
       		long id = (long)table.getValueAt(row, 10);

       		int amount = (int) table.getValueAt(row, 4);
       		int paiduntil = 0;
       		try {
				paiduntil = alquilerDao.getAlquilerById(id).getPricePaid();
			} catch (Exception e) {
				e.printStackTrace();
			}        	
        	
			LocalDate end = LocalDate.parse((CharSequence)table.getValueAt(row, 2), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			return  end.isBefore(LocalDate.now()) && (amount <= paiduntil);
		}
}
