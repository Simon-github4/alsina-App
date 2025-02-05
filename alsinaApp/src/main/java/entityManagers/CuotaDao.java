package entityManagers;

import java.util.List;
import java.util.NoSuchElementException;

import entities.Cuota;
import entities.Transaccion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;

public class CuotaDao {

	private static EntityManagerFactory emf ;

	public CuotaDao(EntityManagerFactory emf2) {
		emf = emf2;
	}
	
	public void save(Cuota cuota) {
			
			try(EntityManager manager = emf.createEntityManager();){ 
				
				manager.getTransaction().begin();
				
				if(cuota.getId() == null)
					manager.persist(cuota);
				else
					manager.merge(cuota);
	
				manager.getTransaction().commit();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	
		public Cuota getCuotaById(long id) {
			Cuota cuota = null;
			    try (EntityManager manager = emf.createEntityManager();) {
			        
			    	manager.getTransaction().begin();
			    	cuota = manager.find(Cuota.class, id);	        
			        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
			        		        
			    } catch (Exception e) {
			        e.printStackTrace();
			    }
			    
			return cuota;
		}
	
		public List<Cuota> getCuotasByTransaccion(Long transaccion){
			List<Cuota> cuotas = null;
			try (EntityManager manager = emf.createEntityManager();) {
			        
			    	manager.getTransaction().begin();
			    	cuotas = manager.createQuery("select c FROM Cuota c "
								    			+ "WHERE c.transaccion.id = :t "
								    			+ "ORDER BY c.quoteNumber DESC", Cuota.class)
								    			.setParameter("t", transaccion).getResultList();       
			        manager.getTransaction().commit();
			        		        
			    } catch (Exception e) {
			        e.printStackTrace();
			    }
				
			return cuotas;
			
		}
		public void delete(long id) throws Exception{
			
			try(EntityManager manager = emf.createEntityManager()){ 
				
				manager.getTransaction().begin();
		        
				Cuota cuota = manager.find(Cuota.class, id);    
		        if (cuota != null) 
		        	manager.remove(cuota);
		        else 
		            throw new NoSuchElementException("cuota with ID " + id + " not found.");
		        	        
		        manager.getTransaction().commit();
		        
		    }
		}
		
}
