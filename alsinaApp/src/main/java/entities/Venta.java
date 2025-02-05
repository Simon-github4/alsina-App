package entities;

import java.time.LocalDate;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue(value = "V")
public class Venta extends Transaccion{

	public Venta() {}
	public Venta(Vehiculo vehicle, Cliente client, long amount, LocalDate date, String description) {
		super(vehicle, client, amount, date, description);
	}

	
}
