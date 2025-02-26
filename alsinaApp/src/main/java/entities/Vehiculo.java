package entities;

import java.util.Objects;

import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "vehiculos", 
			uniqueConstraints = @UniqueConstraint(columnNames = {"plate", "esalquilable"}))
@DiscriminatorColumn(name = "esalquilable", discriminatorType = DiscriminatorType.STRING) 
//@Where(clause = "is_deleted = false")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Vehiculo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	private int year;
	private int kilometers;
    //@Column(name = "plate", unique = true)
	private String plate;
	private String model;
	@ManyToOne
	@JoinColumn(name = "brand")
	private Marca brand;
	@ManyToOne
	@JoinColumn(name = "branch")
	private Sucursal branch;
	@Column(name = "is_deleted", columnDefinition = "boolean default false")
	private Boolean isDeleted;
	
	public Vehiculo() {}

	public Vehiculo(long id, int year, int kilometers, String plate, String model, Marca brand, Sucursal branch) {
		super();
		this.id = id;
		this.year = year;
		this.kilometers = kilometers;
		this.plate = plate;
		this.model = model;
		this.brand = brand;
		this.branch = branch;
		this.isDeleted= false;

	}

	public Vehiculo(int year, int kilometers, String plate, String model, Marca brand, Sucursal branch) {
		super();
		this.id= null;
		this.year = year;
		this.kilometers = kilometers;
		this.plate = plate;
		this.model = model;
		this.brand = brand;
		this.branch = branch;
		this.isDeleted= false;

	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
	
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public int hashCode() {
		return Objects.hash(branch, brand, id, kilometers, model, plate, year);
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
			    && id == other.id && kilometers == other.kilometers
				&& Objects.equals(model, other.model) && Objects.equals(plate, other.plate) 
				&& year == other.year;
	}

	@Override
	public String toString() {
		return plate ;
	}

	
}
