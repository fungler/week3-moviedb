package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Movie;
import entities.MovieDTO;
import utils.EMF_Creator;
import facades.MovieFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("monday")
public class ApplicationResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/moviedb_test",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
    private static final MovieFacade FACADE =  MovieFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    public ApplicationResource(){
        
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Default page\"}";
    }
    
    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getRenameMeCount() {
        List<MovieDTO> allMovies = FACADE.getAllMovies();
        return GSON.toJson(allMovies);
    }
    
    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMovieCount() {
        long count = FACADE.getCount();
        return "{\"count\":" + count + "}"; 
    }

    
    @Path("create")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String makeUsers() {
        FACADE.CreateMovie(2004, "Bingbong land", new String[]{"John Doe", "Pia Lone"});
        FACADE.CreateMovie(2006, "xD machine", new String[]{"Trump", "Justin Bieber"});
        return "Making users.."; 
    } 
//    @POST
//    @Consumes({MediaType.APPLICATION_JSON})
//    public void create(Movie entity) {
//        throw new UnsupportedOperationException();
//    }
//    
    
    // Har problemer med denne opgave. (Også forklaret i facaden)
    @GET
    @Path("name/{name}")
    @Consumes({MediaType.APPLICATION_JSON})
    public String findActor(@PathParam("name") String name) {
        List<Movie> foundMovs = FACADE.getActorInFilms("Mark Boi");
        return GSON.toJson(foundMovs);
    }
    
    @GET
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public String findMovieByID(@PathParam("id") int id) {
        try {
            Long toLong = new Long(id);
            MovieDTO m = FACADE.GetMovieByID(toLong);
            return GSON.toJson(m);
        }
        catch (Exception e){
            return "No movie found with the specified ID.";
        }
    }
}
