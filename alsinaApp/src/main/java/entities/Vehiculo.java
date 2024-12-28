package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehiculos")
public class Vehiculo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	private int year;
	private int kilometers;
	private String plate;
	private String model;

	public Vehiculo() {}

	public Vehiculo(long id, int year, int kilometers, String plate, String model) {
		this.id = id;		
		this.year = year;
		this.kilometers = kilometers;
		this.plate = plate;
		this.model = model;
	}
	
	public Vehiculo(int year, int kilometers, String plate, String model) {
		super();
		this.year = year;
		this.kilometers = kilometers;
		this.plate = plate;
		this.model = model;
	}
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getKilometers() {
		return kilometers;
	}
	public void setKilometers(int kilometers) {
		this.kilometers = kilometers;
	}
	public String getPlate() {
		return plate;
	}
	public void setPlate(String plate) {
		this.plate = plate;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}

	
}
