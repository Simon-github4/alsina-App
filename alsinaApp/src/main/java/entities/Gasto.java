package entities;

import java.time.LocalDate;

import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table
@Where(clause = "is_deleted = false")
public class Gasto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "vehicle", nullable = true)
	private Vehiculo vehicle;

	private int amount;	
	private LocalDate date;
	private String description;
	private String payment;
	
	@ManyToOne
	@JoinColumn(name = "destination", nullable = false)
	private Destino destination;
	
	@ManyToOne
	@JoinColumn(name = "branch", nullable = false)
	private Sucursal branch;
	@Column(name = "is_deleted", columnDefinition = "boolean default false")
	private Boolean isDeleted;
	
	public Gasto(Long id, Vehiculo vehicle, int amount, LocalDate date, String description, String payment,
			Destino destination, Sucursal branch) {
		super();
		this.id = id;
		this.vehicle = vehicle;
		this.amount = amount;
		this.date = date;
		this.description = description;
		this.payment = payment;
		this.destination = destination;
		this.branch = branch;
		this.isDeleted= false;

	}
	
	public Gasto(Vehiculo vehicle, int amount, LocalDate date, String description, String payment,
			Destino destination, Sucursal branch) {
		super();
		this.vehicle = vehicle;
		this.amount = amount;
		this.date = date;
		this.description = description;
		this.payment = payment;
		this.destination = destination;
		this.branch = branch;
		this.isDeleted= false;

	}

	public Gasto() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Vehiculo getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehiculo vehicle) {
		this.vehicle = vehicle;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public Destino getDestination() {
		return destination;
	}

	public void setDestination(Destino destination) {
		this.destination = destination;
	}

	public Sucursal getBranch() {
		return branch;
	}

	public void setBranch(Sucursal branch) {
		this.branch = branch;
	}
	
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	
}
