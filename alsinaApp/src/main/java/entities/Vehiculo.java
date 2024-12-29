package entities;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	private int price;
	private String plate;
	private String model;
	private String ensurance;

	@ManyToOne
	@JoinColumn(name = "brand")
	private Marca brand;
	@ManyToOne
	@JoinColumn(name = "branch")
	private Sucursal branch;

	public Vehiculo() {}

	public Vehiculo(long id, int year, int kilometers, int price, String plate, String model, String ensurance, Marca brand, Sucursal branch) {
		super();
		this.id = id;
		this.year = year;
		this.kilometers = kilometers;
		this.price = price;
		this.plate = plate;
		this.model = model;
		this.brand = brand;
		this.branch = branch;
		this.setEnsurance(ensurance);
	}

	public Vehiculo(int year, int kilometers, int price, String plate, String model, String ensurance, Marca brand, Sucursal branch) {
		super();
		this.year = year;
		this.kilometers = kilometers;
		this.price = price;
		this.plate = plate;
		this.model = model;
		this.brand = brand;
		this.branch = branch;
		this.setEnsurance(ensurance);
	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Marca getBrand() {
		return brand;
	}

	public void setBrand(Marca brand) {
		this.brand = brand;
	}

	public Sucursal getBranch() {
		return branch;
	}

	public void setBranch(Sucursal branch) {
		this.branch = branch;
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

	public String getEnsurance() {
		return ensurance;
	}

	public void setEnsurance(String ensurance) {
		this.ensurance = ensurance;
	}

	@Override
	public int hashCode() {
		return Objects.hash(branch, brand, ensurance, id, kilometers, model, plate, price, year);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vehiculo other = (Vehiculo) obj;
		return Objects.equals(branch, other.branch) && Objects.equals(brand, other.brand)
				&& Objects.equals(ensurance, other.ensurance) && id == other.id && kilometers == other.kilometers
				&& Objects.equals(model, other.model) && Objects.equals(plate, other.plate) && price == other.price
				&& year == other.year;
	}

	
}
