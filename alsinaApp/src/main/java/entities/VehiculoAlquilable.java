package entities;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(value= "si")
public class VehiculoAlquilable extends Vehiculo{

	private int price;
	//private int pricePaidUntil;
	private String ensurance;
	@Column(columnDefinition = "int default 18")
	private int ensuranceFranchise;
	
	public VehiculoAlquilable() {}
	
	public VehiculoAlquilable(int price, String ensurance, long id, int year, int kilometers,  String plate, String model, Marca brand, Sucursal branch) {
		super(id, year, kilometers, plate, model, brand, branch);
		this.price = price;
		this.ensurance = ensurance;
		//this.pricePaidUntil=0;
	}
	
	public VehiculoAlquilable(int price, String ensurance, int franchise, int year, int kilometers,  String plate, String model, Marca brand, Sucursal branch) {
		super(year, kilometers, plate, model, brand, branch);
		this.ensuranceFranchise=franchise;
		this.price = price;
		this.ensurance = ensurance;
		//this.pricePaidUntil=0;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getEnsurance() {
		return ensurance;
	}

	public void setEnsurance(String ensurance) {
		this.ensurance = ensurance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(ensurance, price);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		VehiculoAlquilable other = (VehiculoAlquilable) obj;
		return Objects.equals(ensurance, other.ensurance) && price == other.price;
	}

	public int getEnsuranceFranchise() {
		return ensuranceFranchise;
	}

	public void setEnsuranceFranchise(int ensuranceFranchise) {
		this.ensuranceFranchise = ensuranceFranchise;
	}

	/*public int getPricePaidUntil() {
		return pricePaidUntil;
	}

	public void setPricePaidUntil(int pricePaidUntil) {
		this.pricePaidUntil = pricePaidUntil;
	}
*/
	
}
