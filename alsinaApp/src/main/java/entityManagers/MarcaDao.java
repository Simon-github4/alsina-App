package entityManagers;

import java.util.Collection;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;

import entities.Marca;

public class MarcaDao {

	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");

	public static void main(String args[]) {

		try(EntityManagerFactory emf = Persistence.createEntityManagerFactory(("persistencia"))){ 
		
		//manager = emf.createEntityManager();    
		//manager.getTransaction().begin();
		
		Marca m = new Marca("nashe2");
		//m.setId(null);
		//m.setNombre("nashe");
		//save(m);
		
		//Marca u = new MarcaDao().getMarcaById(2);
		//u.setNombre("999");	Hace luego el UPDATE en la BD
		
		//manager.getTransaction().commit();
		
		}
	}

	public void save(Marca marca) {
		
		try(//EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
			EntityManager manager = emf.createEntityManager();){ 
			
			manager.getTransaction().begin();
			
			if(marca.getId() == null)
				manager.persist(marca);
			else
				manager.merge(marca);

			manager.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public Marca getMarcaById(long id) {
		Marca marca = null;
		    try (//EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
		    	EntityManager manager = emf.createEntityManager();) {
		        
		    	manager.getTransaction().begin();
		        marca = manager.find(Marca.class, id);	        
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
		        		        
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    
		return marca;
	}

	public List<Marca> getMarcas(){
		List<Marca> marcas = null;
		try (//EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
		    EntityManager manager = emf.createEntityManager();) {
		        
		    	manager.getTransaction().begin();
		        marcas = manager.createQuery("select m FROM Marca m ORDER BY m.id desc", Marca.class).getResultList();       
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
		        		        
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
			
		return marcas;
		
	}
	
	public void delete(long id) {
		
		try(//EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
			EntityManager manager = emf.createEntityManager();){ 
			
			manager.getTransaction().begin();
	        
	        Marca marca = manager.find(Marca.class, id);    
	        if (marca != null) 
	        	manager.remove(marca);
	        else 
	            throw new Exception("Marca with ID " + id + " not found.");
	        	        
	        manager.getTransaction().commit();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
}
