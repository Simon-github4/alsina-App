package interfaces;

import java.awt.Color;
import java.awt.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import entities.Alquiler;

public class ColorearTabla extends DefaultTableCellRenderer{

		@Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if(column == 2) {
				LocalDate end = LocalDate.parse((CharSequence)table.getValueAt(row, column), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	            if (end.isBefore(LocalDate.now())) {
	            	this.setBackground(Color.RED); 
	            	this.setForeground(Color.WHITE); 
	            } else {
	            	this.setBackground(Color.WHITE); 
	            	this.setForeground(Color.BLACK);
	            }
            }
            /* Si la celda está seleccionada, cambiar el color de fondo
            if (isSelected) {
                cell.setBackground(Color.BLUE);
            }
*/
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            return this;
        }
    
}
