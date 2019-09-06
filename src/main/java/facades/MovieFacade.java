package facades;

import entities.Movie;
import entities.MovieDTO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

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
                mldto.add(new MovieDTO(m.getId(), m.getYear(), m.getName(), m.getActors()));
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
            
            MovieDTO foundMovie = new MovieDTO(m.getId(), m.getYear(), m.getName(), m.getActors());
            
            return foundMovie;
            
        }finally{  
            em.close();
        }
    }
    
    public void CreateMovie(int year, String name, String[] actors) {
        EntityManager em = emf.createEntityManager();
        Movie newMovie = new Movie(year, name, actors);
        
        try {
            em.getTransaction().begin();
            em.persist(newMovie);
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
    
    // Jeg kan ikke få denne JPQL query til at virke.. Jeg kan simpelthen ikke finde problemet. Hvis noget kan fortælle mig
    // hvordan man løser problemet skylder jeg en øl.
    public List<Movie> getActorInFilms(String actorName) {
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Movie> foundMovies = em.createQuery("SELECT m FROM Movie m WHERE :actorname MEMBER OF m.actors", Movie.class);
            foundMovies.setParameter("actorname", actorName);
            List<Movie> resultMemberOf = foundMovies.getResultList();
            
            return resultMemberOf;
            
        }finally{  
            em.close();
        }
    }

}
