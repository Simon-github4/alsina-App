package entityManagers;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

import entities.Alquiler;
import entities.VehiculoAlquilable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

public class AlquilerDao {

	private static EntityManagerFactory emf  ;//= Persistence.createEntityManagerFactory("persistencia");
			
	public AlquilerDao(EntityManagerFactory emf2) {
		emf = emf2;
	}

	public void updatePricePaid(Long id, int pay) {
		Alquiler alquiler = null;
	    try (EntityManager manager = emf.createEntityManager();) {
	        
	    	manager.getTransaction().begin();
	    	alquiler = manager.find(Alquiler.class, id);
	    	alquiler.setPricePaid(alquiler.getPricePaid() + pay);
	        manager.getTransaction().commit();
	    }
	}
	
	public void save(Alquiler alquiler) throws Exception{
		
		try(EntityManager manager = emf.createEntityManager();){ 
			
			manager.getTransaction().begin();
			
			if(alquiler.getVehicle() == null && alquiler.getId() == null)
				manager.persist(alquiler);				
			else if(alquiler.getId() == null) {
					List<VehiculoAlquilable> availables = new VehiculoDao(emf).getVehiculosAlquilablesAvailable(alquiler.getStart(), alquiler.getEnd());			
					HashSet<VehiculoAlquilable> hs = new HashSet<VehiculoAlquilable>(availables);
					if(hs.contains(alquiler.getVehicle())) 
						manager.persist(alquiler);
					else
						throw new Exception("No se puedo insertar alquiler debido a Fechas no disponibles");
			}else
				manager.merge(alquiler);

			manager.getTransaction().commit();
		}
	}

	public Alquiler getAlquilerById(long id) {
		Alquiler alquiler = null;
		    try (EntityManager manager = emf.createEntityManager();) {
		        
		    	manager.getTransaction().begin();
		    	alquiler = manager.find(Alquiler.class, id);	        
		        manager.getTransaction().commit();
		    }
		    
		return alquiler;
	}

	public List<Alquiler> getAlquileresByPlateAndDate(String plate, LocalDate[] dates) {
		List<Alquiler> alquileres = null;
		try (EntityManager manager = emf.createEntityManager();) {
		        
				StringBuilder query = new StringBuilder("select a FROM Alquiler a WHERE 1=1 ");
					
				if(!plate.isBlank())
					query.append("AND lower(a.vehicle.plate) like :filterplate ");
		    	
				if(dates != null)
					query.append("AND a.start BETWEEN :start AND :end OR a.end BETWEEN :start AND :end OR (a.start < :start AND a.end > :end) ");
				
				query.append("ORDER BY a.end desc");
		    					
				manager.getTransaction().begin();
		        TypedQuery<Alquiler> queryResult = manager.createQuery(query.toString(), Alquiler.class);

		        if(!plate.isBlank())
		        	queryResult.setParameter("filterplate", plate.toLowerCase()+ "%");       
				
		        if(dates != null)
					queryResult.setParameter("start", dates[0]).setParameter("end", dates[1]);
		        
		        alquileres = queryResult.getResultList();
		        manager.getTransaction().commit();// Confirmar la transacción (aunque find no modifica, es una buena práctica)
		}
			
		return alquileres;
	}
	
	public void delete(long id) throws Exception {
		
		try(EntityManager manager = emf.createEntityManager()){ 
			
			manager.getTransaction().begin();
	        
			Alquiler alquiler = manager.find(Alquiler.class, id);    
	        if (alquiler != null) 
	        	manager.remove(alquiler);
	        else 
	            throw new NoSuchElementException("Sucursal with ID " + id + " not found.");
	        	        
	        manager.getTransaction().commit();
	        
		}catch(PersistenceException e) {
	    	EntityManager manager = emf.createEntityManager();
				
			manager.getTransaction().begin();
		        
			Alquiler alquiler = manager.find(Alquiler.class, id);    
			alquiler.setIsDeleted(true);
			
		    manager.getTransaction().commit();
	    }
	}

}
