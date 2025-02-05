package entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@DiscriminatorValue(value= "no")
public class VehiculoVenta extends Vehiculo{

	//private Long buyPrice;
	private Long sellPrice;
	
	public VehiculoVenta(Long sellPrice, int year, int kilometers, String plate, String model, Marca brand, Sucursal branch) {
		super(year, kilometers, plate, model, brand, branch);
		//this.buyPrice = buyPrice;
		this.sellPrice = sellPrice;
	}
	
	public VehiculoVenta() {	}

	public Long getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(Long sellPrice) {
		this.sellPrice = sellPrice;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	
}
