package com.tis.savemytime.resources;

import java.sql.Connection;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.tis.savemytime.helpers.DbConnection;
import com.tis.savemytime.helpers.UserHandler;
import com.tis.savemytime.models.Status;
import com.tis.savemytime.models.User;
import com.tis.savemytime.models.VerifyBean;


@Path("/userservice")
public class UserService {

	private static final Logger logger = Logger.getLogger(UserService.class);
	private DbConnection database;
    private Connection connection;
    private UserHandler userHandler;
   
    public UserService()
    {
    		database = new DbConnection();
    		userHandler = new UserHandler();
    }
    
    @POST
    @Path("authenticate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User authenticate(User user){
		try {
			connection = database.getConnection();
			user =  userHandler.authenticateUser(connection, user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return user;
		} 
 		finally {
 			try 
 			{
 				if(connection!= null)
 					connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 		} 
 		return user;
    }
    
    @POST
    @Path("/sendemail")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Status sendemail(User user){
    		Status status = new Status();
		try {
			connection = database.getConnection();
			status =  userHandler.sendVerificationEmail(connection, user,user.getEmailLink());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} 
 		finally {
 			try 
 			{
 				if(connection!= null)
 					connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 		} 
 		return status;
    }
    
    @POST
    @Path("/verifyemail")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Status verifyEmail(VerifyBean verifyBean){
    		Status status = new Status();
		try {
			connection = database.getConnection();
			status =  userHandler.verifyEmail(connection, verifyBean.getUserId(), verifyBean.getAccessToken());	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			status.setMessage("exception.."+e.getMessage());
			status.setStatus("Failure");
			status.setStatusCode("500");
		} 
 		finally {
 			try 
 			{
 				if(connection!= null)
 					connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				status.setMessage("exception.."+e.getMessage());
				status.setStatus("Failure");
				status.setStatusCode("500");
			}
 		} 
 		return status;
    }
    
	@GET
    @Path("getUserBasedID/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUserBasedID(@PathParam("id") int id){
		User user =  null;
 		try {
			connection = database.getConnection();
			user =  userHandler.getUserByID(connection, id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return user;
		} 
 		finally {
 			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 		} 
 		return user;
    }
	
	
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Status createUser(User user) {
		Status status = new Status();
		
		try {
			connection = database.getConnection();
			UserHandler userHandler = new UserHandler();
			if(userHandler.saveUser(connection, user)) {
				status = new Status("SUCCESS", "Inserted " + user.getUserName());
			}
			else {
				status = new Status("Failure", "Not able to insert " + user.getUserName());
			}
		}
		catch(Exception ex) {
			logger.debug(""+ex.getMessage());
			ex.printStackTrace();
			status = new Status("Failure", "Server error, please try again after some time...");
			return status;
		}
        return status;	
	}
}
