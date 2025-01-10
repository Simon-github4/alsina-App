package entityManagers;

import java.util.List;

import entities.Cliente;
import entities.Marca;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ClienteDao {

	private static EntityManagerFactory emf  ;//= Persistence.createEntityManagerFactory("persistencia");
	
	public ClienteDao(EntityManagerFactory emf2) {
		emf = emf2;
	}
	
	public void save(Cliente cliente) {
		
		try(//EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
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

	public Cliente getClienteById(long id) {
		Cliente cliente = null;
		    try (//EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
		    	EntityManager manager = emf.createEntityManager();) {
		        
		    	manager.getTransaction().begin();
		    	cliente = manager.find(Cliente.class, id);	        
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
		        		        
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    
		return cliente;
	}

	public List<Cliente> getClientes(){
		List<Cliente> clientes = null;
		try (EntityManager manager = emf.createEntityManager();) {
		        
		    	manager.getTransaction().begin();
		    	clientes = manager.createQuery("select c FROM Cliente c ORDER BY c.name ASC", Cliente.class)
		    						.getResultList();       
		        manager.getTransaction().commit();
		        		        
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
			
		return clientes;
		
	}
	
	public List<Cliente> getClientes(String name){
		List<Cliente> clientes = null;
		try (EntityManager manager = emf.createEntityManager();) {
		        
		    	manager.getTransaction().begin();
		    	clientes = manager.createQuery("select c FROM Cliente c WHERE lower(c.name) like :filtername ORDER BY c.name ASC", Cliente.class)
		    						.setParameter("filtername", "%"+name.toLowerCase()+"%").getResultList();       
		        manager.getTransaction().commit();
		        		        
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
			
		return clientes;
		
	}
	
	public void delete(long id) {
		
		try(//EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
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
