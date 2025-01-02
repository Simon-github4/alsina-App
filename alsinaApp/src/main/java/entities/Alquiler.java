package entities;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

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
	@Column(nullable=true)
	private int departureKm;	
	@Column(nullable=true)
	private Integer returnKm;	
	
	public Alquiler() {}
	
	public Alquiler(Long id, LocalDate start, LocalDate end, Cliente client, VehiculoAlquilable vehicle, int totalPrice, int departureKm, int returnKm) {
		super();
		this.id = id;
		this.start = start;
		this.end = end;
		this.client = client;
		this.vehicle = vehicle;
		this.totalPrice = totalPrice;
		this.departureKm = departureKm;
		this.returnKm = returnKm;
	}

	public Alquiler(LocalDate start, LocalDate end, Cliente client, VehiculoAlquilable vehicle, int totalPrice, int departureKm, int returnKm) {
		super();
		this.id=null;
		this.start = start;
		this.end = end;
		this.client = client;
		this.vehicle = vehicle;
		this.totalPrice = totalPrice;
		this.departureKm = departureKm;
		this.returnKm = returnKm;
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
	
	public void openExcelPrint() throws FileNotFoundException, IOException {
		Workbook libro = new XSSFWorkbook();
		Sheet hoja = libro.createSheet("Hoja 1");
		hoja.setDefaultColumnWidth(5);
		Row row = hoja.createRow(4);
		Cell actualCell = row.createCell(1);
		actualCell.setCellValue(client.getName());

		hoja.createRow(9).createCell(1).setCellValue(vehicle.getPlate());//arranca de 0
		hoja.getRow(9).createCell(7).setCellValue(vehicle.getModel());
		hoja.createRow(13).createCell(9).setCellValue(String.valueOf(departureKm));
		hoja.createRow(14).createCell(9).setCellValue(String.valueOf(returnKm));
		
		hoja.getRow(13).createCell(1).setCellValue(getStart().toString());
		hoja.getRow(14).createCell(1).setCellValue(getEnd().toString());//new SimpleDateFormat("yyyy-MM-dd").format(start));
		
		String user = System.getProperty("user.home");
		final String nombreArchivo = user + "\\Desktop\\"+ "Imprimir Contrato.xlsx";
		File directorioActual = new File(nombreArchivo);
		FileOutputStream outputStream = null;
		
		outputStream = new FileOutputStream(nombreArchivo);
		libro.write(outputStream);
		libro.close();
		outputStream.close();
	    
		Desktop.getDesktop().open(directorioActual);
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
	 
}
