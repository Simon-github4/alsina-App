package entityManagers;

import java.util.List;

import entities.Cliente;
import entities.Marca;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ClienteDao {

	public static void save(Cliente cliente) {
		
		try(EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
			EntityManager manager = emf.createEntityManager();){ 
			
			manager.getTransaction().begin();
			
			if(cliente.getId() == null)
				manager.persist(cliente);
			else
				manager.merge(cliente);

			manager.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static Cliente getClienteById(long id) {
		Cliente cliente = null;
		    try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
		    	EntityManager manager = emf.createEntityManager();) {
		        
		    	manager.getTransaction().begin();
		    	cliente = manager.find(Cliente.class, id);	        
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
		        		        
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    
		return cliente;
	}

	public static List<Cliente> getClientes(){
		List<Cliente> clientes = null;
		try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
		    EntityManager manager = emf.createEntityManager();) {
		        
		    	manager.getTransaction().begin();
		    	clientes = manager.createQuery("select c FROM Cliente c ORDER BY c.id desc", Cliente.class).getResultList();       
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
		        		        
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
			
		return clientes;
		
	}
	
	public static void delete(long id) {
		
		try(EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
			EntityManager manager = emf.createEntityManager();){ 
			
			manager.getTransaction().begin();
	        
			Cliente cliente = manager.find(Cliente.class, id);    
	        if (cliente != null) 
	        	manager.remove(cliente);
	        else 
	            throw new Exception("Cliente with ID " + id + " not found.");
	        	        
	        manager.getTransaction().commit();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
}