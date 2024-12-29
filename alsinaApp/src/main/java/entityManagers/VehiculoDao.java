package entityManagers;

import java.util.List;

import entities.Sucursal;
import entities.Vehiculo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class VehiculoDao {

public static void save(Vehiculo vehiculo) {
		
		try(EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
			EntityManager manager = emf.createEntityManager();){ 
			
			manager.getTransaction().begin();
			
			if(vehiculo.getId() == null)
				manager.persist(vehiculo);
			else
				manager.merge(vehiculo);

			manager.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static Vehiculo getVehiculoById(long id) {
		Vehiculo vehiculo = null;
		    try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
		    	EntityManager manager = emf.createEntityManager();) {
		        
		    	manager.getTransaction().begin();
		    	vehiculo = manager.find(Vehiculo.class, id);	        
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
		        		        
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    
		return vehiculo;
	}

	public static List<Vehiculo> getVehiculos(){
		List<Vehiculo> vehiculos = null;
		try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
		    EntityManager manager = emf.createEntityManager();) {
		        
		    	manager.getTransaction().begin();
		    	vehiculos = manager.createQuery("select s FROM Vehiculo s ORDER BY s.id desc", Vehiculo.class).getResultList();       
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
		        		        
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
			
		return vehiculos;
		
	}
	
	public static void delete(long id) {
		
		try(EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
			EntityManager manager = emf.createEntityManager();){ 
			
			manager.getTransaction().begin();
	        
			Vehiculo vehiculo = manager.find(Vehiculo.class, id);    
	        if (vehiculo != null) 
	        	manager.remove(vehiculo);
	        else 
	            throw new Exception("Sucursal with ID " + id + " not found.");
	        	        
	        manager.getTransaction().commit();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	

}
