package entities;

import java.time.LocalDate;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue(value = "C")
public class Compra extends Transaccion{

	public Compra() {}
	public Compra(Vehiculo vehicle, Cliente client, long amount, LocalDate date, String description) {
		super(vehicle, client, amount, date, description);
	}

}
