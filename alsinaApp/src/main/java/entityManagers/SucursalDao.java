package entityManagers;

import java.util.List;

import entities.Marca;
import entities.Sucursal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class SucursalDao {

	public static void save(Sucursal sucursal) {
		
		try(EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
			EntityManager manager = emf.createEntityManager();){ 
			
			manager.getTransaction().begin();
			
			if(sucursal.getId() == null)
				manager.persist(sucursal);
			else
				manager.merge(sucursal);

			manager.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static Sucursal getMarcaById(long id) {
		Sucursal sucursal = null;
		    try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
		    	EntityManager manager = emf.createEntityManager();) {
		        
		    	manager.getTransaction().begin();
		        sucursal = manager.find(Sucursal.class, id);	        
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
		        		        
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    
		return sucursal;
	}

	public static List<Sucursal> getMarcas(){
		List<Sucursal> sucursales = null;
		try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
		    EntityManager manager = emf.createEntityManager();) {
		        
		    	manager.getTransaction().begin();
		    	sucursales = manager.createQuery("select s FROM Sucursal s ORDER BY s.id desc", Sucursal.class).getResultList();       
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
		        		        
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
			
		return sucursales;
		
	}
	
	public static void delete(long id) {
		
		try(EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
			EntityManager manager = emf.createEntityManager();){ 
			
			manager.getTransaction().begin();
	        
			Sucursal sucursal = manager.find(Sucursal.class, id);    
	        if (sucursal != null) 
	        	manager.remove(sucursal);
	        else 
	            throw new Exception("Sucursal with ID " + id + " not found.");
	        	        
	        manager.getTransaction().commit();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
}
