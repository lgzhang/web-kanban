package api;

import com.sun.net.httpserver.*;
import org.junit.*;

import java.io.*;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class WebKanbanServerTest {
	private HttpServer server;

	@Before
	public void startWebServer() throws IOException {
		server = WebKanbanServer.start();
	}

	@After
	public void stopWebServer() {
		server.stop(1);
	}

	@Test
	public void should_retrieve_stories_as_JSON() {
		expect().body("stories.state", hasItems("TODO", "WIP", "DONE")).and().body("stories.label", hasItems("sleep at night", "rest in front of the tv", "eat. a lot.")).when().get("/stories.json");
	}

	@Test
	public void should_add_story() {
		expect().body("state", equalTo("TODO")).and().body("label", equalTo("junit")).when().put("/story/junit").thenReturn();
		expect().body("stories.label", hasItem("junit")).when().get("/stories.json");
	}

	@Test
	public void should_not_add_existing_story() {
		expect().statusCode(400).content(equalTo("this story already exists")).when().put("/story/sleep at night").thenReturn();
	}
}
