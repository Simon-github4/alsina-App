package entityManagers;

import java.time.LocalDate;
import java.util.List;

import entities.Alquiler;
import entities.Marca;
import entities.Vehiculo;
import entities.VehiculoAlquilable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class AlquilerDao {

	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
			
	public void save(Alquiler alquiler){
		
		try(//EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
			EntityManager manager = emf.createEntityManager();){ 
			
			manager.getTransaction().begin();
			
			if(alquiler.getId() == null)
				manager.persist(alquiler);
			else
				manager.merge(alquiler);

			manager.getTransaction().commit();
		}
	}

	public Alquiler getAlquilerById(long id) {
		Alquiler alquiler = null;
		    try (//EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
		    	EntityManager manager = emf.createEntityManager();) {
		        
		    	manager.getTransaction().begin();
		    	alquiler = manager.find(Alquiler.class, id);	        
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
		    }
		    
		return alquiler;
	}

	public List<Alquiler> getAlquileresByPlateAndDate(String plate, LocalDate[] dates) {
		List<Alquiler> alquileres = null;
		try (//EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
		    EntityManager manager = emf.createEntityManager();) {
		        
				StringBuilder query = new StringBuilder("select a FROM Alquiler a WHERE lower(a.vehicle.plate) like :filterplate ");
		    	
				if(dates != null)
					if(dates.length == 2) 
						query.append("AND a.start BETWEEN :start AND :end OR a.end BETWEEN :start AND :end OR (a.start < :start AND a.end > :end) ");
				
				query.append("ORDER BY a.end desc");
		    					
				manager.getTransaction().begin();
		        TypedQuery<Alquiler> queryResult = manager.createQuery(query.toString() , Alquiler.class)
		    					   									.setParameter("filterplate", plate.toLowerCase()+ "%");       
				if(dates != null)
					if(dates.length == 2) 
						queryResult.setParameter("start", dates[0]).setParameter("end", dates[1]);//.setParameter("start", dates[0]).setParameter("end", dates[1]);
		    	
		        alquileres = queryResult.getResultList();
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
		}
			
		return alquileres;
	}
	
	public void delete(long id) throws Exception {
		
		try(EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
			EntityManager manager = emf.createEntityManager();){ 
			
			manager.getTransaction().begin();
	        
			Alquiler alquiler = manager.find(Alquiler.class, id);    
	        if (alquiler != null) 
	        	manager.remove(alquiler);
	        else 
	            throw new Exception("Sucursal with ID " + id + " not found.");
	        	        
	        manager.getTransaction().commit();
		}
	}

}
