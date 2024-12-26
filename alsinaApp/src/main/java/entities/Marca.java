package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "marcas")
public class Marca {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id")
private Long id;

@Column(name = "description")
private String description;

	public Marca() {
		//Constructor por defecto NECESARIO
	}
	
	public Marca(String description) {
	super();
	this.description = description;
}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNombre() {
		return description;
	}
	
	public void setNombre(String nombre) {
		this.description = nombre;
	}

	@Override
	public String toString() {
		return "this is : " +description;
	}

}
