package entities;

import java.util.Objects;

import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "marcas")
@Where(clause = "is_deleted = false")
public class Marca {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id")
private Long id;

@Column(name = "description")
private String description;

@Column(name = "is_deleted", columnDefinition = "boolean default false")
private Boolean isDeleted;

	public Marca() {
		//Constructor por defecto NECESARIO
	}
	
	public Marca(String description) {
		super();
		this.description = description;
		this.id=null;
		this.isDeleted= false;
	}

	public Marca(String description, long id) {
		super();
		this.description = description;
		this.id = id;
		this.isDeleted= false;
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
		return description;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Marca other = (Marca) obj;
		return Objects.equals(description, other.description) && id == other.id;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}
