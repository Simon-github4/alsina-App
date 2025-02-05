package entityManagers;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import org.postgresql.util.PSQLException;

import entities.Alquiler;
import entities.Marca;
import entities.Sucursal;
import entities.Vehiculo;
import entities.VehiculoAlquilable;
import entities.VehiculoVenta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

public class VehiculoDao {

	private static EntityManagerFactory emf  ;//= Persistence.createEntityManagerFactory("persistencia");
	
	public VehiculoDao(EntityManagerFactory emf2) {
		emf = emf2;
	}
	
	public void save(Vehiculo vehiculo){
		
		try(EntityManager manager = emf.createEntityManager();){ 
			
			manager.getTransaction().begin();
			
			if(vehiculo.getId() == null)
				manager.persist(vehiculo);
			else
				manager.merge(vehiculo);

			manager.getTransaction().commit();
		}
	}

	public Vehiculo getVehiculoById(long id) {
		Vehiculo vehiculo = null;
		    try (EntityManager manager = emf.createEntityManager();) {
		        
		    	manager.getTransaction().begin();
		    	vehiculo = manager.find(Vehiculo.class, id);	        
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
		    }
		    
		return vehiculo;
	}

	public Vehiculo getVehiculoByPlate(String plate) {
		Vehiculo vehiculo = null;
		    try (EntityManager manager = emf.createEntityManager()){
		        
		    	manager.getTransaction().begin();
		    	vehiculo = manager.createQuery("SELECT v FROM Vehiculo v WHERE lower(v.plate) = :plate AND v.isDeleted = false ", Vehiculo.class)
		    						  		 .setParameter("plate", plate.toLowerCase()).getSingleResultOrNull();
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
		    }
		    
		return vehiculo;
	}
	
	public Vehiculo getVehiculoAndDeletedByPlate(String plate) {
		Vehiculo vehiculo = null;
		    try (EntityManager manager = emf.createEntityManager()){
		        
		    	manager.getTransaction().begin();
		    	vehiculo = manager.createQuery("SELECT v FROM Vehiculo v WHERE lower(v.plate) = :plate ", Vehiculo.class)
		    						  		 .setParameter("plate", plate.toLowerCase()).getSingleResultOrNull();
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
		    }
		    
		return vehiculo;
	}
	
	public VehiculoAlquilable getVehiculoAlquilableByPlate(String plate) {
		VehiculoAlquilable vehiculo = null;
		    try (EntityManager manager = emf.createEntityManager()){
		        
		    	manager.getTransaction().begin();
		    	vehiculo = manager.createQuery("SELECT v FROM VehiculoAlquilable v WHERE lower(v.plate) = :plate AND v.isDeleted = false ", VehiculoAlquilable.class)
		    						  		 .setParameter("plate", plate.toLowerCase()).getSingleResult();
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
		    }
		    
		return vehiculo;
	}

	public List<VehiculoAlquilable> getVehiculosAlquilablesByPlate(String plate, Marca marca) {
		List<VehiculoAlquilable> vehiculos = null;
		try (EntityManager manager = emf.createEntityManager()) {
		        
				StringBuilder query = new StringBuilder("select v FROM VehiculoAlquilable v WHERE lower(v.plate) like: filterplate AND v.isDeleted = false ");
		    	
				if(marca.getId() != null) 
		    		query.append("AND v.brand = :brand");
				
				query.append(" ORDER BY v.plate ASC");
		    					
				manager.getTransaction().begin();
		        TypedQuery<VehiculoAlquilable> queryResult = manager.createQuery(query.toString() , VehiculoAlquilable.class)
		    					   									.setParameter("filterplate", plate.toLowerCase()+ "%");       
		        if(marca.getId() != null) {
		        	queryResult.setParameter("brand", marca);
		    	}
		        vehiculos = queryResult.getResultList();
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
		}
			
		return vehiculos;
	}

	public List<VehiculoAlquilable> getVehiculosAlquilablesAvailable(LocalDate startDate, LocalDate endDate) {
		List<VehiculoAlquilable> vehiculos = null;
		try (EntityManager manager = emf.createEntityManager()) {
		        		    	   					
				manager.getTransaction().begin();
		        TypedQuery<VehiculoAlquilable> queryResult = manager.createQuery( 
		        			    "SELECT v FROM VehiculoAlquilable v " +
		        			    "LEFT JOIN Alquiler a ON a.vehicle.id = v.id " +
		        			    "WHERE v.isDeleted = false GROUP BY v " +
		        			    "HAVING (COUNT(a.id) = " +
		        			    "   (SELECT COUNT(a2.id) " +
		        			    "    FROM Alquiler a2 " +
		        			    "    WHERE a2.vehicle.id = v.id"+
		        			    "			AND (NOT (a2.start > :startDate AND a2.start < :endDate)) " +//S(--------xx)Exxxx    NOT BETWEEN no incluiria start en mismo dia con endDate
		        			    "       	AND (NOT (a2.end > :startDate AND a2.end < :endDate)) " + //   xxxS(xxx--------)E	 NOT BETWEEN no incluiria end en mismo dia con startDate
		        			    "       	AND (NOT (a2.start < :startDate AND a2.end > :endDate)) " +//  xxS(xxxxxxxxxx)Exxxx
		        			    "       	AND (NOT (a2.start > :startDate AND a2.end < :endDate)) " +//  S(---xxxx---)E
		        			    "       	)) " +
		        			    "OR (COUNT(a.id) = 0) " +
		        			    "ORDER BY v.plate ASC " , VehiculoAlquilable.class);

		        vehiculos = queryResult.setParameter("startDate", startDate).setParameter("endDate", endDate).getResultList();
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
		}
			
		return vehiculos;
	}
	
	public void delete(long id) throws Exception {
		
		try(EntityManager manager = emf.createEntityManager();){ 
			
			manager.getTransaction().begin();
	        
			Vehiculo vehiculo = manager.find(Vehiculo.class, id);    
	        if (vehiculo != null) 
	        	manager.remove(vehiculo);
	        else 
	            throw new NoSuchElementException("Sucursal with ID " + id + " not found.");
	        	        
	        manager.getTransaction().commit();
	        
		}catch(PersistenceException e) {
	    	EntityManager manager = emf.createEntityManager();
				
			manager.getTransaction().begin();
		        
			Vehiculo vehiculo = manager.find(Vehiculo.class, id);    
			vehiculo.setIsDeleted(true);
			
		    manager.getTransaction().commit();
	    }
	}
	
	public List<VehiculoVenta> getVehiculosVenta(String plate, Marca marca){
		List<VehiculoVenta> vehiculos = null;
		try (EntityManager manager = emf.createEntityManager();) {
		        
				StringBuilder query = new StringBuilder("select v FROM VehiculoVenta v WHERE lower(v.plate) like :filterplate  AND v.isDeleted = false ");
				
				if(marca.getId() != null) 
		    		query.append("AND v.brand = :brand ");
				
				query.append(" ORDER BY v.plate ASC");
				
		    	manager.getTransaction().begin();
		    	TypedQuery<VehiculoVenta> queryResult = manager.createQuery(query.toString(), VehiculoVenta.class)
		    													.setParameter("filterplate", plate.toLowerCase()+"%");
		    	if(marca.getId() != null) {
		        	queryResult.setParameter("brand", marca);
		    	}
		        vehiculos = queryResult.getResultList();
		        manager.getTransaction().commit();
		}		
		
		return vehiculos;
		
	}

	

}
