package entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@DiscriminatorValue(value= "no")
public class VehiculoVenta extends Vehiculo{

	//@Id
	//private Long id;
	private Long buyPrice;
	private Long sellPrice;
	
	public VehiculoVenta(Long buyPrice, Long sellPrice, int year, int kilometers, String plate, String model, Marca brand, Sucursal branch) {
		super(year, kilometers, plate, model, brand, branch);
		this.buyPrice = buyPrice;
		this.sellPrice = sellPrice;
	}
	
	public VehiculoVenta() {	}

	public Long getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(Long buyPrice) {
		this.buyPrice = buyPrice;
	}
	public Long getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(Long sellPrice) {
		this.sellPrice = sellPrice;
	}

	/*public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	*/
	
}
