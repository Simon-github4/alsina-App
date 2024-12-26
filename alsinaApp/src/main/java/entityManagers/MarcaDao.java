package entityManagers;

import java.util.Collection;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;

import entities.Marca;

public class MarcaDao {

private static EntityManager manager;



	public static void main(String args[]) {

		try(EntityManagerFactory emf = Persistence.createEntityManagerFactory(("persistencia"))){ 
		
		manager = emf.createEntityManager();    
		manager.getTransaction().begin();
		
		Marca m = new Marca("nashe2");
		//m.setId(null);
		//m.setNombre("nashe");
		new MarcaDao().save(m);
		
		//Marca u = new MarcaDao().getMarcaById(2);
		//u.setNombre("999");	Hace luego el UPDATE en la BD
		
		manager.getTransaction().commit();
		
		System.out.println(new MarcaDao().getMarcas().toString());
		}
	}

	public void save(Marca marca) {
		if(marca.getId() == null)
			manager.persist(marca);
		else
			manager.merge(marca);
	}

	public Marca getMarcaById(long id) {
		return manager.find(Marca.class, id);
	}

	public List<Marca> getMarcas(){
		return manager.createQuery("FROM Marca", Marca.class).getResultList();
	}
}
