package facades;

import entities.Movie;
import entities.MovieDTO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class MovieFacade {

    private static MovieFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private MovieFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MovieFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MovieFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public List<MovieDTO> getAllMovies(){
        EntityManager em = emf.createEntityManager();
        
        List<MovieDTO> mldto = null;
        
        try{
            List<Movie> movieList = em.createQuery("SELECT m FROM Movie m").getResultList();
            mldto = new ArrayList();
            
            for (Movie m : movieList) 
            {
                mldto.add(new MovieDTO(m.getId(), m.getYear(), m.getName()));
            }
            
            return mldto;
            
        }finally{  
            em.close();
        }
    }
    
    public MovieDTO GetMovieByID(Long id){
        EntityManager em = emf.createEntityManager();
        try{
            Movie m = (Movie)em.createQuery("SELECT m FROM Movie m WHERE m.id=" + id).getSingleResult();
            
            MovieDTO foundMovie = new MovieDTO(m.getId(), m.getYear(), m.getName());
            
            return foundMovie;
            
        }finally{  
            em.close();
        }
    }
    
    public void CreateMovie(int year, String name) {
        EntityManager em = emf.createEntityManager();
        Movie newMovie = new Movie(year, name);
        
        try {
            em.getTransaction().begin();
            em.persist(em);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    public long getCount() {
        EntityManager em = emf.createEntityManager();
        try{
            long count = (long)em.createQuery("SELECT count(m) FROM Movie m").getSingleResult();
            
            return count;
            
        }finally{  
            em.close();
        }
    }

}
