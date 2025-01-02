package entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Gasto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "vehicle", nullable = true)
	private VehiculoAlquilable vehicle;

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

	public Gasto(Long id, VehiculoAlquilable vehicle, int amount, LocalDate date, String description, String payment,
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
	}
	
	public Gasto(VehiculoAlquilable vehicle, int amount, LocalDate date, String description, String payment,
			Destino destination, Sucursal branch) {
		super();
		this.vehicle = vehicle;
		this.amount = amount;
		this.date = date;
		this.description = description;
		this.payment = payment;
		this.destination = destination;
		this.branch = branch;
	}

	public Gasto() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public VehiculoAlquilable getVehicle() {
		return vehicle;
	}

	public void setVehicle(VehiculoAlquilable vehicle) {
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
	
	
}
