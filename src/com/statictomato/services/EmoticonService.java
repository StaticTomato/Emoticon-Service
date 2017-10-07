/**
 * 
 */
package com.statictomato.services;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.statictomato.persistence.EmoticonEAO;
import com.statictomato.util.Converter;
import com.statictomato.util.Validator;
import com.statictomato.model.Updates;
import com.statictomato.model.Emoticon;

/**
 * @author Thomas Asheim Smedmann
 *
 * **********************************************************
 * 							GET		POST	PUT		DELETE	*
 * 															*
 * .../updates/{logId}		V		-		-		-		*
 * .../emoticons			-		V		-		-		*
 * .../emoticons/{id}		V		-		V		V		*
 * **********************************************************
 *
 * events: create, update, delete
 *
 * **** JSON: ****
 * Emoticon = {"id":1,"icon":"<:-E","description":"Shrug"}
 * 
 * Updates = {	"logId":1,
 * 				"newEmoticons":[	{"id":1,"icon":"<:-E","description":"Shrug"},
 * 									{"id":1,"icon":"<:-E","description":"Shrug"},
 * 									{"id":1,"icon":"<:-E","description":"Shrug"}],
 * 				"updatedEmoticons":[{"id":1,"icon":"<:-E","description":"Shrug"},
 * 									{"id":1,"icon":"<:-E","description":"Shrug"},
 * 									{"id":1,"icon":"<:-E","description":"Shrug"}],
 * 				"deletedEmoticons":[{"id":1,"icon":"<:-E","description":"Shrug"},
 * 									{"id":1,"icon":"<:-E","description":"Shrug"},
 * 									{"id":1,"icon":"<:-E","description":"Shrug"}]}
 *
 */
@Path("/")
//@Produces(MediaType.TEXT_PLAIN) // Default MIME-type
public class EmoticonService {
	
	@EJB
	private EmoticonEAO emoticonEAO;
	
	
	@Path("updates/{logIdString}")
	@GET
	@Produces(MediaType.APPLICATION_JSON )
	public Response getUpdates(@PathParam("logIdString") String logIdString) {
		
		Integer logId = Converter.convertToInteger(logIdString);
		if(logId == null) {
			return Response.status(400).build(); // Bad Request
		}
		if(logId > emoticonEAO.getMaxLogId()) {
			return Response.status(204).build(); // No Content
		}
		Updates updates = emoticonEAO.getUpdates(logId);
		if(updates != null) {
			return Response.status(200).entity(updates).type(MediaType.APPLICATION_JSON).build(); // Ok
		} else {
			return Response.status(304).build(); // Not Modified
		}
	}
	
	@Path("emoticons")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addEmoticon(Emoticon emoticon) {
		
		if(Validator.isValidEmoticon(emoticon)) {
			emoticonEAO.addEmoticon(emoticon);
			return Response.status(201).build(); // Created
		} else {
			return Response.status(400).build(); // Bad Request
		}
	}
	
	@Path("emoticons/{idString}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmoticon(@PathParam("idString") String idString) {
		
		Integer id = Converter.convertToInteger(idString);
		if(id == null) {
			return Response.status(400).build(); // Bad Request
		}
		Emoticon emoticon = emoticonEAO.getEmoticon(id);
		if(emoticon != null) {
			return Response.status(200).entity(emoticon).type(MediaType.APPLICATION_JSON).build(); // Ok
		} else {
			return Response.status(204).build(); // No Content
		}
	}
	
	@Path("emoticons/{idString}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateEmoticon(@PathParam("idString") String idString, Emoticon emoticon) {
		
		Integer id = Converter.convertToInteger(idString);
		if(id == null || !Validator.isValidEmoticon(emoticon)) {
			return Response.status(400).build(); // Bad Request
		}
		if(emoticonEAO.updateEmoticon(id,emoticon)) {
			return Response.status(202).build(); // Accepted
		} else {
			return Response.status(204).build(); // No Content
		}
	}
	
	@Path("emoticons/{idString}")
	@DELETE
	public Response deleteEmoticon(@PathParam("idString") String idString) {
		
		Integer id = Converter.convertToInteger(idString);
		if(id == null) {
			return Response.status(400).build(); // Bad Request
		}
		if(emoticonEAO.deleteEmoticon(id)) {
			return Response.status(202).build(); // Accepted
		} else {
			return Response.status(204).build(); // No Content
		}	
	}
}
