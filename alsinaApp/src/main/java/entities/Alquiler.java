package entities;

import java.time.LocalDate;
import java.util.Date;

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
	
	public Alquiler() {}
	
	public Alquiler(Long id, LocalDate start, LocalDate end, Cliente client, VehiculoAlquilable vehicle, int totalPrice) {
		super();
		this.id = id;
		this.start = start;
		this.end = end;
		this.client = client;
		this.vehicle = vehicle;
		this.totalPrice = totalPrice;
	}
	
	public Alquiler(LocalDate start, LocalDate end, Cliente client, VehiculoAlquilable vehicle, int totalPrice) {
		super();
		this.id=null;
		this.start = start;
		this.end = end;
		this.client = client;
		this.vehicle = vehicle;
		this.totalPrice = totalPrice;
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
	
}
