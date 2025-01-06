package entities;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	private String name;
	private String adress;
	private String phone;
	private String dni;
	private String cuil;

		public Cliente() {
			//Constructor por defecto NECESARIO
		}
		
		public Cliente(long id, String name, String adress, String phone, String dni,  String cuil) {
			super();
			this.id = id;
			this.name = name;
			this.adress = adress;
			this.phone = phone;
			this.dni=dni;
			this.cuil=cuil;
		}

		public Cliente(String name, String adress, String phone, String dni,  String cuil) {
			super();
			this.id=null;
			this.name = name;
			this.adress = adress;
			this.phone = phone;
			this.dni=dni;
			this.cuil=cuil;
		}

		public Long getId() {
			return id;
		}
		
		public void setId(Long id) {
			this.id = id;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}

		public String getAdress() {
			return adress;
		}

		public void setAdress(String adress) {
			this.adress = adress;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		@Override
		public String toString() {
			return name;
		}

		@Override
		public int hashCode() {
			return Objects.hash(adress, id, name, phone);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Cliente other = (Cliente) obj;
			return Objects.equals(adress, other.adress) && id == other.id && Objects.equals(name, other.name)
					&& Objects.equals(phone, other.phone);
		}

		public String getDni() {
			return dni;
		}

		public void setDni(String dni) {
			this.dni = dni;
		}

		public String getCuil() {
			return cuil;
		}

		public void setCuil(String cuil) {
			this.cuil = cuil;
		}

	

}
