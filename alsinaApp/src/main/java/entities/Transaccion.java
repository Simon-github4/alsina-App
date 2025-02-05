package entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_transaccion", discriminatorType = DiscriminatorType.CHAR) 
@DiscriminatorValue("T") 
//@Where(clause = "is_deleted = false")
public abstract class Transaccion {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Vehiculo vehicle;
	
	@ManyToOne
	private Cliente client;
	
	@OneToMany(mappedBy = "transaccion", cascade = CascadeType.REMOVE)
	private List<Cuota> quotes;
	
	private long amount;
	private LocalDate date;
	private String description;
	
	public Transaccion() {}
	public Transaccion(Vehiculo vehicle, Cliente client, long amount, LocalDate date,
			String description) {
		this.vehicle = vehicle;
		this.client = client;
		this.amount = amount;
		this.date = date;
		this.description = description;
	}

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

	public Cliente getClient() {
		return client;
	}

	public void setClient(Cliente client) {
		this.client = client;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
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

	
}
