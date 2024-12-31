package entityManagers;

import java.util.List;

import org.postgresql.util.PSQLException;

import entities.Marca;
import entities.Sucursal;
import entities.Vehiculo;
import entities.VehiculoAlquilable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class VehiculoDao {

	public static void save(Vehiculo vehiculo){
		
		try(EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
			EntityManager manager = emf.createEntityManager();){ 
			
			manager.getTransaction().begin();
			
			if(vehiculo.getId() == null)
				manager.persist(vehiculo);
			else
				manager.merge(vehiculo);

			manager.getTransaction().commit();
		}
	}

	public static Vehiculo getVehiculoById(long id) {
		Vehiculo vehiculo = null;
		    try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
		    	EntityManager manager = emf.createEntityManager();) {
		        
		    	manager.getTransaction().begin();
		    	vehiculo = manager.find(Vehiculo.class, id);	        
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
		    }
		    
		return vehiculo;
	}

	public static VehiculoAlquilable getVehiculoAlquilableByPlate(String plate) {
		VehiculoAlquilable vehiculo = null;
		    try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
		    	EntityManager manager = emf.createEntityManager();) {
		        
		    	manager.getTransaction().begin();
		    	vehiculo = manager.createQuery("SELECT v FROM VehiculoAlquilable v WHERE lower(v.plate) = :plate ", VehiculoAlquilable.class)
		    						  		 .setParameter("plate", plate.toLowerCase()).getSingleResult();
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
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
		}
			
		return vehiculos;
		
	}
	
	public static List<VehiculoAlquilable> getVehiculosAlquilables() {
		List<VehiculoAlquilable> vehiculos = null;
		try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
		    EntityManager manager = emf.createEntityManager();) {
		        
		    	manager.getTransaction().begin();
		    	vehiculos = manager.createQuery("select a FROM VehiculoAlquilable a ORDER BY a.id desc", VehiculoAlquilable.class).getResultList();       
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
		}
			
		return vehiculos;
	}

	public static List<VehiculoAlquilable> getVehiculosAlquilablesByPlate(String plate, Marca marca) {
		List<VehiculoAlquilable> vehiculos = null;
		try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
		    EntityManager manager = emf.createEntityManager();) {
		        
				StringBuilder query = new StringBuilder("select a FROM VehiculoAlquilable a WHERE lower(a.plate) like: filterplate ");
		    	
				if(marca.getId() != null) 
		    		query.append("AND a.brand = :brand");
				query.append(" ORDER BY a.id desc");
		    					
				manager.getTransaction().begin();
		        TypedQuery<VehiculoAlquilable> queryResult = manager.createQuery(query.toString() , VehiculoAlquilable.class)
		    					   									.setParameter("filterplate", "%" + plate.toLowerCase()+ "%");       
		        if(marca.getId() != null) {
		        	queryResult.setParameter("brand", marca);
		    	}
		        vehiculos = queryResult.getResultList();
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
		}
			
		return vehiculos;
	}
/*
	public static List<VehiculoAlquilable> getVehiculosAlquilablesAvailabel() {
		List<VehiculoAlquilable> vehiculos = null;
		try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
		    EntityManager manager = emf.createEntityManager();) {
		        
				StringBuilder query = new StringBuilder("select a FROM VehiculoAlquilable a WHERE lower(a.plate) like: filterplate ");
		    	
				if() 
		    		query.append("AND a.brand = :brand");
				query.append(" ORDER BY a.id desc");
		    					
				manager.getTransaction().begin();
		        TypedQuery<VehiculoAlquilable> queryResult = manager.createQuery(query.toString() , VehiculoAlquilable.class)
		    					   									.setParameter("filterplate", "%" + plate.toLowerCase()+ "%");       
		        if() {
		        	queryResult.setParameter("brand", marca);
		    	}
		        vehiculos = queryResult.getResultList();
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
		}
			
		return vehiculos;
	}
	*/
	public static void delete(long id) throws Exception {
		
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
