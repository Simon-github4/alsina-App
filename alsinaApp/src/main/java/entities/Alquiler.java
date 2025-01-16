package entities;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "alquileres")
public class Alquiler {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "vehicle", nullable = false)
	private VehiculoAlquilable vehicle;
	@Column(name  = "start_date")
	private LocalDate start;
	@Column(name  = "end_date")
	private LocalDate end;
	@ManyToOne
	@JoinColumn(name = "client")
	private Cliente client;
	private int totalPrice;
	@Column(columnDefinition = "int default 0")
	private int pricePaid;

	private int departureKm;	
	@Column(columnDefinition = "int default 0")
	private int returnKm;	
	private Boolean isBooked;	
	
	private String gasExit;
	private String gasReturn;
	
	public Alquiler() {}
	
	/*public Alquiler(LocalDate start, LocalDate end, Cliente client, VehiculoAlquilable vehicle, int totalPrice, int departureKm, int returnKm, String cbE, String cbR) {
		super();
		//this.id = id;
		this.start = start;
		this.end = end;
		this.client = client;
		this.vehicle = vehicle;
		this.totalPrice = totalPrice;
		this.departureKm = departureKm;
		this.returnKm = returnKm;
		this.gasExit=cbE;
		this.gasReturn=cbR;
		//this.isBooked = booked;
	}*/

	public Alquiler(LocalDate start, LocalDate end, Cliente client, VehiculoAlquilable vehicle, int totalPrice, int departureKm, int returnKm, String cbE, String cbR, Boolean booked) {
		super();
		this.id=null;
		this.start = start;
		this.end = end;
		this.client = client;
		this.vehicle = vehicle;
		this.totalPrice = totalPrice;
		this.departureKm = departureKm;
		this.returnKm = returnKm;
		this.gasExit=cbE;
		this.gasReturn=cbR;
		this.isBooked = booked;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getStart() {
		return start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	public LocalDate getEnd() {
		return end;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
	}

	public Cliente getClient() {
		return client;
	}

	public void setClient(Cliente client) {
		this.client = client;
	}

	public VehiculoAlquilable getVehicle() {
		return vehicle;
	}

	public void setVehicle(VehiculoAlquilable vehicle) {
		this.vehicle = vehicle;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
	public int getPricePaid() {
		return pricePaid;
	}

	public void setPricePaid(int pricePaid) {
		this.pricePaid = pricePaid;
	}
	
	public void openExcelPrint() throws FileNotFoundException, IOException {
		final int returnrow = 13;
		final int exitrow = 14;

		Workbook libro = new XSSFWorkbook();
		Sheet hoja = libro.createSheet("Hoja 1");
		hoja.setDefaultColumnWidth(5);
		Row row = hoja.createRow(4);
		Cell actualCell = row.createCell(1);
		actualCell.setCellValue(client.getName());

		hoja.createRow(9).createCell(1).setCellValue(vehicle.getPlate());//arranca de 0
		hoja.getRow(9).createCell(6).setCellValue(vehicle.getModel());
		hoja.createRow(exitrow).createCell(9).setCellValue(String.valueOf(departureKm));	
		hoja.createRow(returnrow).createCell(9).setCellValue(String.valueOf(returnKm));
		hoja.getRow(exitrow).createCell(1).setCellValue(getStart().toString());
		hoja.getRow(returnrow).createCell(1).setCellValue(getEnd().toString());
		hoja.createRow(43).createCell(9).setCellValue(vehicle.getEnsuranceFranchise());	

		int gasExitColumn = -1;
		if(getGasExit().equalsIgnoreCase("RESERVA"))
			gasExitColumn = 12;
		else if(getGasExit().equalsIgnoreCase("1/4"))
			gasExitColumn=13;
		else if(getGasExit().equalsIgnoreCase("1/2"))
			gasExitColumn=14;
		else if(getGasExit().equalsIgnoreCase("3/4"))
			gasExitColumn=15;
		else if(getGasExit().equalsIgnoreCase("FULL"))
			gasExitColumn=15;

		int gasReturnColumn = -1;
		if(getGasReturn().equalsIgnoreCase("RESERVA"))
			gasReturnColumn = 12;
		else if(getGasReturn().equalsIgnoreCase("1/4"))
			gasReturnColumn=13;
		else if(getGasReturn().equalsIgnoreCase("1/2"))
			gasReturnColumn=14;
		else if(getGasReturn().equalsIgnoreCase("3/4"))
			gasReturnColumn=15;
		else if(getGasReturn().equalsIgnoreCase("FULL"))
			gasReturnColumn=15;

		hoja.getRow(exitrow).createCell(gasExitColumn).setCellValue("X");
		hoja.getRow(returnrow).createCell(gasReturnColumn).setCellValue("X");
		
		String user = System.getProperty("user.home");
		final String nombreArchivo = user + "\\OneDrive\\.AA-Escritorio\\Imprimir Contrato.xlsx";//"\\Desktop\\Imprimir Contrato.xlsx"; // \\OneDrive\\.AA-Escritorio\\Imprimir Contrato.xlsx
		File directorioActual = new File(nombreArchivo);
		FileOutputStream outputStream = null;
		
		outputStream = new FileOutputStream(nombreArchivo);
		libro.write(outputStream);
		libro.close();
		outputStream.close();
	    
		Desktop.getDesktop().open(directorioActual);

		/*String user = System.getProperty("user.home");
		final String nombreArchivo = user + "\\OneDrive\\.AA-Escritorio\\Imprimir ContratoFASTEXCEL.xlsx";// \\OneDrive\\.AA-Escritorio\\Imprimir Contrato.xlsx
		File directorioActual = new File(nombreArchivo);

		try (FileOutputStream outputStream = new FileOutputStream(directorioActual)) {
				Workbook workbook = new Workbook(outputStream, "Application", "1.0");

		        Worksheet sheet = workbook.newWorksheet("Hoja 1");
		        for (int i = 0; i < 30; i++) {
		            sheet.width((char)i, 5.00);// Set width to 5 characters (5 * 256 is the width in units)
		        }	        
		        sheet.value(4, 1, client.getName()); 
		        sheet.value(9, 1, vehicle.getPlate()); 
		        sheet.value(9, 7, vehicle.getModel()); 
		        sheet.value(13, 9, String.valueOf(departureKm)); 
		        sheet.value(14, 9, String.valueOf(returnKm));
		        sheet.value(13, 1, getStart().toString()); 
		        sheet.value(14, 1, getEnd().toString()); 

		        workbook.finish();
		        Desktop.getDesktop().open(directorioActual);
		}FastExcel*/
		
	}
	
	 public static boolean isFileOpen(String filePath) {
	        File file = new File(filePath);
	        FileOutputStream fos = null;

	        try {
	            // Attempt to open the file in append mode
	            fos = new FileOutputStream(file); // 'true' for append mode
	            return false; // If successful, the file is not open
	        } catch (IOException e) {
	            // If an IOException occurs, the file is likely open
	            return true;
	        } finally {
	            // Close the FileOutputStream if it was opened
	            if (fos != null) {
	                try {
	                    fos.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }

	public int getDepartureKm() {
		return departureKm;
	}

	public void setDepartureKm(int departureKm) {
		this.departureKm = departureKm;
	}

	public int getReturnKm() {
		return returnKm;
	}

	public void setReturnKm(int returnKm) {
		this.returnKm = returnKm;
	}

	public String getGasExit() {
		return gasExit;
	}

	public void setGasExit(String gasExit) {
		this.gasExit = gasExit;
	}

	public String getGasReturn() {
		return gasReturn;
	}

	public void setGasReturn(String gasReturn) {
		this.gasReturn = gasReturn;
	}

	public Boolean getIsBooked() {
		return isBooked;
	}

	public void setIsBooked(Boolean isBooked) {
		this.isBooked = isBooked;
	}
	 
}
