package entityManagers;

import java.util.List;
import java.util.NoSuchElementException;

import entities.Marca;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;

public class MarcaDao {

	private static EntityManagerFactory emf  ;//= Persistence.createEntityManagerFactory("persistencia");
	
	public MarcaDao(EntityManagerFactory emf2) {
		emf = emf2;
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
	
	public void delete(long id) throws Exception{
		
		try(EntityManager manager = emf.createEntityManager();){ 
			
			manager.getTransaction().begin();
	        
	        Marca marca = manager.find(Marca.class, id);    
	        if (marca != null) 
	        	manager.remove(marca);
	        else 
	            throw new Exception("Marca with ID " + id + " not found.");
	        	        
	        manager.getTransaction().commit();
	        
	    }catch(PersistenceException e) {
	    	EntityManager manager = emf.createEntityManager();
				
			manager.getTransaction().begin();
		        
		    Marca marca = manager.find(Marca.class, id);    
		    if (marca != null) 
		    	marca.setIsDeleted(true);
		    else 
		    	throw new NoSuchElementException("Marca with ID " + id + " not found.");
		        	        
		    manager.getTransaction().commit();
	    }
	}
	
}
