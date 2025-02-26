package entityManagers;

import java.util.NoSuchElementException;

import entities.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;

public class UsuarioDao {

private static EntityManagerFactory emf  ;//= Persistence.createEntityManagerFactory("persistencia");
	
	public UsuarioDao(EntityManagerFactory emf2) {
		emf = emf2;
	}
	
	public void save(Usuario usuario) {
		
		try(EntityManager manager = emf.createEntityManager();){ 
			
			manager.getTransaction().begin();
			
			if(usuario.getId() == null)
				manager.persist(usuario);
			else
				manager.merge(usuario);

			manager.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public Usuario getUsuarioById(long id) {
		Usuario cliente = null;
		    try (EntityManager manager = emf.createEntityManager();) {
		        
		    	manager.getTransaction().begin();
		    	cliente = manager.find(Usuario.class, id);	        
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
		        		        
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    
		return cliente;
	}

	public Usuario getUsuario(String username, String password){
		Usuario cliente = null;
		try (EntityManager manager = emf.createEntityManager();) {
		        
		    	manager.getTransaction().begin();
		    	cliente = manager.createQuery("select c FROM Usuario c WHERE c.username = :user AND c.password = :pass", Usuario.class)
		    						.setParameter("user", username).setParameter("pass", password).getSingleResultOrNull();       
		        manager.getTransaction().commit();
		        		        
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
			
		return cliente;
		
	}
	
	public void delete(long id) throws Exception{
		
		try(EntityManager manager = emf.createEntityManager()){ 
			
			manager.getTransaction().begin();
	        
			Usuario cliente = manager.find(Usuario.class, id);    
	        if (cliente != null) 
	        	manager.remove(cliente);
	        else 
	            throw new NoSuchElementException("Cliente with ID " + id + " not found.");
	        	        
	        manager.getTransaction().commit();
	        
	    }catch(PersistenceException e) {
	    	EntityManager manager = emf.createEntityManager();
				
			manager.getTransaction().begin();
		        
			Usuario cliente = manager.find(Usuario.class, id);    
			//cliente.setIsDeleted(true);
			
		    manager.getTransaction().commit();
	    }
	}
	
}
