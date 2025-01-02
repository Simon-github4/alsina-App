package entityManagers;

import java.time.LocalDate;
import java.util.List;

import org.postgresql.util.PSQLException;

import entities.Alquiler;
import entities.Marca;
import entities.Sucursal;
import entities.Vehiculo;
import entities.VehiculoAlquilable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class VehiculoDao {

	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");

	public void save(Vehiculo vehiculo){
		
		try(//EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
			EntityManager manager = emf.createEntityManager();){ 
			
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
		    try (//EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
		    	EntityManager manager = emf.createEntityManager();) {
		        
		    	manager.getTransaction().begin();
		    	vehiculo = manager.find(Vehiculo.class, id);	        
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
		    }
		    
		return vehiculo;
	}

	public VehiculoAlquilable getVehiculoAlquilableByPlate(String plate) {
		VehiculoAlquilable vehiculo = null;
		    try (//EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
		    	EntityManager manager = emf.createEntityManager();) {
		        
		    	manager.getTransaction().begin();
		    	vehiculo = manager.createQuery("SELECT v FROM VehiculoAlquilable v WHERE lower(v.plate) = :plate ", VehiculoAlquilable.class)
		    						  		 .setParameter("plate", plate.toLowerCase()).getSingleResult();
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
		    }
		    
		return vehiculo;
	}
	
	public List<Vehiculo> getVehiculos(){
		List<Vehiculo> vehiculos = null;
		try (//EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
		    EntityManager manager = emf.createEntityManager();) {
		        
		    	manager.getTransaction().begin();
		    	vehiculos = manager.createQuery("select s FROM Vehiculo s ORDER BY s.id desc", Vehiculo.class).getResultList();       
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
		}
			
		return vehiculos;
		
	}

	public List<VehiculoAlquilable> getVehiculosAlquilablesByPlate(String plate, Marca marca) {
		List<VehiculoAlquilable> vehiculos = null;
		try (//EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
		    EntityManager manager = emf.createEntityManager();) {
		        
				StringBuilder query = new StringBuilder("select a FROM VehiculoAlquilable a WHERE lower(a.plate) like: filterplate ");
		    	
				if(marca.getId() != null) 
		    		query.append("AND a.brand = :brand");
				query.append(" ORDER BY a.plate ASC");
		    					
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
		        			    "GROUP BY v " +
		        			    "HAVING (COUNT(a.id) = " +
		        			    "   (SELECT COUNT(a2.id) " +
		        			    "    FROM Alquiler a2 " +
		        			    "    WHERE a2.vehicle.id = v.id"+
		        			    "			AND(a2.start NOT BETWEEN :startDate AND :endDate) " +
		        			    "       	AND (a2.end NOT BETWEEN :startDate AND :endDate) " +
		        			    "       	AND (NOT (a2.start < :startDate AND a2.end > :endDate)) " +
		        			    "       	AND (NOT (a2.start > :startDate AND a2.end < :endDate)) " +
		        			    "       	)) " +
		        			    "OR (COUNT(a.id) = 0) " +
		        			    "ORDER BY v.plate ASC" , VehiculoAlquilable.class);

		        vehiculos = queryResult.setParameter("startDate", startDate).setParameter("endDate", endDate).getResultList();
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
		}
			
		return vehiculos;
	}
	
	public void delete(long id) throws Exception {
		
		try(EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
			EntityManager manager = emf.createEntityManager();){ 
			
			manager.getTransaction().begin();
	        
			Vehiculo vehiculo = manager.find(Vehiculo.class, id);    
	        if (vehiculo != null) 
	        	manager.remove(vehiculo);
	        else 
	            throw new Exception("Sucursal with ID " + id + " not found.");
	        	        
	        manager.getTransaction().commit();
		}
	}


	

}
