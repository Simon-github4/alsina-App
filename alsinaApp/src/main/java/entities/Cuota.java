package entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Cuota {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private long amount;
	private int quoteNumber;
	private LocalDate datePayed;
	private String payMethod;
	private String vehiclePlate;
	@ManyToOne
    @JoinColumn(name = "transaccion_id") // Foreign key column
    private Transaccion transaccion;

	public Cuota() {}
	public Cuota(long amount, int quoteNumber, String payMethod, String vehiclePlate, Transaccion transaccion) {
		super();
		this.id = null;
		this.datePayed = null;
		this.amount = amount;
		this.quoteNumber = quoteNumber;
		this.payMethod = payMethod;
		this.vehiclePlate = vehiclePlate;
		this.transaccion = transaccion;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public int getQuoteNumber() {
		return quoteNumber;
	}
	public void setQuoteNumber(int quoteNumber) {
		this.quoteNumber = quoteNumber;
	}
	public LocalDate getDatePayed() {
		return datePayed;
	}
	public void setDatePayed(LocalDate datePayed) {
		this.datePayed = datePayed;
	}
	public String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	
	public Transaccion getTransaccion() {
		return transaccion;
	}

	public void setTransaccion(Transaccion transaccion) {
		this.transaccion = transaccion;
	}
	public String getVehiclePlate() {
		return vehiclePlate;
	}
	public void setVehiclePlate(String vehiclePlate) {
		this.vehiclePlate = vehiclePlate;
	}

	
}
