package api;

import com.google.common.collect.*;
import com.sun.jersey.api.container.httpserver.*;
import com.sun.net.httpserver.*;
import org.codehaus.jettison.json.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.*;
import java.util.*;

import static java.util.Arrays.*;

@Path("/")
public class WebKanbanServer {
	@GET
	@Path("stories.json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response stories() throws JSONException {
		List<Story> stories = asList(new Story("TODO", "sleep at night"), new Story("WIP", "rest in front of the tv"), new Story("DONE", "eat. a lot."));
		return Response.ok(ImmutableMap.of("stories", stories)).build();
	}

	public static HttpServer start() throws IOException {
		HttpServer httpServer = HttpServerFactory.create("http://localhost:8080/");
		httpServer.start();
		return httpServer;
	}
}