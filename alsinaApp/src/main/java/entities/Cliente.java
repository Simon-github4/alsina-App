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
@Table(name = "clientes")
@Where(clause = "is_deleted = false")
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
	
	private String license;
	private String expiration;
	private String cod;
	private String card;
	private String cardNumber;
	private String cardExpiration;	
	@Column(name = "is_deleted", columnDefinition = "boolean default false")
	private Boolean isDeleted;
	
		public Cliente() {
			//Constructor por defecto NECESARIO
		}

		public Cliente(String name, String adress, String phone, String dni,  String cuil, String license, String expiration, String cod, String card, String cardNumber, String cardExpiration) {
			super();
			this.id=null;
			this.name = name;
			this.adress = adress;
			this.phone = phone;
			this.dni=dni;
			this.cuil=cuil;
			this.license=license;
			this.expiration=expiration;
			this.cod=cod;
			this.card=card;
			this.cardNumber=cardNumber;
			this.cardExpiration=cardExpiration;
			this.isDeleted= false;

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
		
		public String getLicense() {
			return license;
		}

		public void setLicense(String license) {
			this.license = license;
		}

		public String getExpiration() {
			return expiration;
		}

		public void setExpiration(String expiration) {
			this.expiration = expiration;
		}

		public String getCod() {
			return cod;
		}

		public void setCod(String cod) {
			this.cod = cod;
		}

		public String getCard() {
			return card;
		}

		public void setCard(String card) {
			this.card = card;
		}

		public String getCardNumber() {
			return cardNumber;
		}

		public void setCardNumber(String cardNumber) {
			this.cardNumber = cardNumber;
		}

		public String getCardExpiration() {
			return cardExpiration;
		}

		public void setCardExpiration(String cardExpiration) {
			this.cardExpiration = cardExpiration;
		}

		@Override
		public String toString() {
			return name;
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
		
		public Boolean getIsDeleted() {
			return isDeleted;
		}

		public void setIsDeleted(Boolean isDeleted) {
			this.isDeleted = isDeleted;
		}

		@Override
		public int hashCode() {
			return Objects.hash(adress, card, cardExpiration, cardNumber, cod, cuil, dni, expiration, id, isDeleted,
					license, name, phone);
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
			return Objects.equals(adress, other.adress) && Objects.equals(card, other.card)
					&& Objects.equals(cardExpiration, other.cardExpiration)
					&& Objects.equals(cardNumber, other.cardNumber) && Objects.equals(cod, other.cod)
					&& Objects.equals(cuil, other.cuil) && Objects.equals(dni, other.dni)
					&& Objects.equals(expiration, other.expiration) && Objects.equals(id, other.id)
					&& Objects.equals(isDeleted, other.isDeleted) && Objects.equals(license, other.license)
					&& Objects.equals(name, other.name) && Objects.equals(phone, other.phone);
		}
	

}
