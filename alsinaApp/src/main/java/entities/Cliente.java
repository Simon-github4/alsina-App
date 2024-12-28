package entities;

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
	private long id;
	private String name;
	private String adress;
	private String phone;

		public Cliente() {
			//Constructor por defecto NECESARIO
		}
		
		public Cliente(long id, String name, String adress, String phone) {
			super();
			this.id = id;
			this.name = name;
			this.adress = adress;
			this.phone = phone;
		}

		public Cliente(String name, String adress, String phone) {
			super();
			this.name = name;
			this.adress = adress;
			this.phone = phone;
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

	

}
