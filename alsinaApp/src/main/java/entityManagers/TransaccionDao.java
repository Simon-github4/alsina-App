package entityManagers;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import entities.Cliente;
import entities.Compra;
import entities.Transaccion;
import entities.Vehiculo;
import entities.Venta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

public class TransaccionDao {

	private static EntityManagerFactory emf ;
	
	public TransaccionDao(EntityManagerFactory emf2) {
		emf = emf2;
	}

	public void save(Transaccion transaccion) {
		
		try(EntityManager manager = emf.createEntityManager();){ 
			
			manager.getTransaction().begin();
			
			if(transaccion.getId() == null) {
				manager.persist(transaccion);
				
			}
			else
				manager.merge(transaccion);

			manager.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public Transaccion getTransaccionById(long id) {
		Transaccion transaccion = null;
		    try (EntityManager manager = emf.createEntityManager();) {
		        
		    	manager.getTransaction().begin();
		    	transaccion = manager.find(Transaccion.class, id);	        
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
		        		        
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    
		return transaccion;
	}
	
	public Compra getCompraByVehicle(Vehiculo vehicle) {
		Compra transaccion = null;
		    try (EntityManager manager = emf.createEntityManager()) {
		        
		    	manager.getTransaction().begin();
		    	transaccion = manager.createQuery("SELECT t FROM Compra t WHERE t.vehicle = :vehicle ", Compra.class).setParameter("vehicle",	vehicle).getSingleResultOrNull();	        
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
		        		        
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    
		return transaccion;
	}
	
	public Venta getVentaByVehicle(Vehiculo vehicle) {
		Venta transaccion = null;
		    try (EntityManager manager = emf.createEntityManager()) {
		        
		    	manager.getTransaction().begin();
		    	transaccion = manager.createQuery("SELECT t FROM Venta t WHERE t.vehicle = :vehicle ", Venta.class).setParameter("vehicle", vehicle).getSingleResultOrNull();	        
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
		        		        
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    
		return transaccion;
	}
	
	public List<Transaccion> getTransactions(String plate, Cliente client, LocalDate[] dates){
		List<Transaccion> ventas = null;
		try (EntityManager manager = emf.createEntityManager();) {
		        
				StringBuilder query = new StringBuilder("select v FROM Transaccion v WHERE 1=1 ");

				if(!plate.isBlank())
					query.append("AND lower(v.vehicle.plate) like :filterplate ");
		    	
				if(dates != null)
					query.append("AND v.date BETWEEN :start AND :end ");
				
				if(client.getId() != null)
					query.append("AND v.client = :cliente ");
				
				query.append("ORDER BY v.date desc");
		    					
		    	manager.getTransaction().begin();
		        TypedQuery<Transaccion> queryResult = manager.createQuery(query.toString(), Transaccion.class);

		    	if(!plate.isBlank())
		    		queryResult.setParameter("filterplate", plate+"%");
		    	
				if(dates != null)
		    		queryResult.setParameter("start", dates[0]).setParameter("end", dates[1]);
				
				if(client.getId() != null)
					queryResult.setParameter("cliente", client);

				ventas = queryResult.getResultList();     
		        manager.getTransaction().commit();
		        		        
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
			
		return ventas;
		
	}
	
	public void delete(long id) throws Exception{
		
		try(EntityManager manager = emf.createEntityManager()){ 
			
			manager.getTransaction().begin();
	        
			Transaccion transaccion = manager.find(Transaccion.class, id);    
	        if (transaccion != null) 
	        	manager.remove(transaccion);
	        else 
	            throw new NoSuchElementException("Transaccion with ID " + id + " not found.");
	        	        
	        manager.getTransaction().commit();
	        
	    }
	}
	
	
}
