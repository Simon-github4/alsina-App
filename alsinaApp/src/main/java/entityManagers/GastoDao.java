package entityManagers;

import java.time.LocalDate;
import java.util.List;

import entities.Destino;
import entities.Gasto;
import entities.Marca;
import entities.Sucursal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class GastoDao {

	private static EntityManagerFactory emf  ;//= Persistence.createEntityManagerFactory("persistencia");
	
	public GastoDao(EntityManagerFactory emf2) {
		emf = emf2;
	}
	
	public void save(Gasto gasto) {
		
		try(EntityManager manager = emf.createEntityManager();){ 
			
			manager.getTransaction().begin();
			
			if(gasto.getId() == null)
				manager.persist(gasto);
			else
				manager.merge(gasto);

			manager.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public Gasto getGastoById(long id) {
		Gasto gasto = null;
		    try (EntityManager manager = emf.createEntityManager();) {
		        
		    	manager.getTransaction().begin();
		    	gasto = manager.find(Gasto.class, id);	        
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
		        		        
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    
		return gasto;
	}

	
	public List<Gasto> getGastosByFilters(String plate, LocalDate[] dates, Sucursal branch, Destino destination){
		List<Gasto> gastos = null;
		try (EntityManager manager = emf.createEntityManager();) {
		        
		    	manager.getTransaction().begin();
		    	
		    	  StringBuilder queryString = new StringBuilder("select e FROM Gasto e WHERE 1=1 ");
		          
		          if (!plate.isBlank()) 
		              queryString.append("AND lower(e.vehicle.plate) like :filter ");
		          
		          if (dates != null) 
		              queryString.append(" AND e.date BETWEEN :startDate AND :endDate ");
		          
		          if (branch.getId() != null) 
		              queryString.append(" AND e.branch = :branch ");
		          
		          if (destination.getId() != null) 
		              queryString.append(" AND e.destination = :destination ");
		   
		          queryString.append("ORDER BY e.id desc ");
		          
		          TypedQuery<Gasto> query = manager.createQuery(queryString.toString(), Gasto.class);
		          
		          if (!plate.isBlank()) 
		              query.setParameter("filter", plate.toLowerCase()+"%");
		          
		          if (dates != null) {
		              query.setParameter("startDate", dates[0]);
		              query.setParameter("endDate", dates[1]);
		          }
		          if (branch.getId() != null) 
		              query.setParameter("branch", branch);
		          
		          if (destination.getId() != null) 
		              query.setParameter("destination", destination);
		          
		    	gastos = query.getResultList();       
		        manager.getTransaction().commit();
		        		        
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
			
		return gastos;	
	}
	
	public void delete(long id) {
		
		try(EntityManager manager = emf.createEntityManager();){ 
			
			manager.getTransaction().begin();
	        
			Gasto gasto = manager.find(Gasto.class, id);    
	        if (gasto != null) 
	        	manager.remove(gasto);
	        else 
	            throw new Exception("Gasto with ID " + id + " not found.");
	        	        
	        manager.getTransaction().commit();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
}
