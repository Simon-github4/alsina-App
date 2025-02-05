package entityManagers;

import java.util.List;
import java.util.NoSuchElementException;

import entities.Destino;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;

public class DestinoDao {

	private static EntityManagerFactory emf  ;//= Persistence.createEntityManagerFactory("persistencia");
	
	public DestinoDao(EntityManagerFactory emf2) {
		emf = emf2;
	}
	
	public void save(Destino destino) {
		
		try(EntityManager manager = emf.createEntityManager();){ 
			
			manager.getTransaction().begin();
			
			if(destino.getId() == null)
				manager.persist(destino);
			else
				manager.merge(destino);

			manager.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public Destino getDestinoById(long id) {
		Destino Destino = null;
		    try (EntityManager manager = emf.createEntityManager();) {
		        
		    	manager.getTransaction().begin();
		    	Destino = manager.find(Destino.class, id);	        
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
		        		        
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    
		return Destino;
	}

	public List<Destino> getDestinos(){
		List<Destino> destinos = null;
		try (EntityManager manager = emf.createEntityManager();) {
		        
		    	manager.getTransaction().begin();
		    	destinos = manager.createQuery("select m FROM Destino m ORDER BY m.id desc", Destino.class).getResultList();       
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
		        		        
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
			
		return destinos;
		
	}
	
	public void delete(long id) throws Exception{
		
		try(EntityManager manager = emf.createEntityManager()){ 
			
			manager.getTransaction().begin();
	        
			Destino destino = manager.find(Destino.class, id);    
	        if (destino != null) 
	        	manager.remove(destino);
	        else 
	            throw new NoSuchElementException("Destino with ID " + id + " not found.");
	        	        
	        manager.getTransaction().commit();
	        
	    }catch(PersistenceException e) {
	    	EntityManager manager = emf.createEntityManager();
				
			manager.getTransaction().begin();
		        
			Destino destino = manager.find(Destino.class, id);    
			destino.setIsDeleted(true);
			
		    manager.getTransaction().commit();
	    }
	}
	
}
