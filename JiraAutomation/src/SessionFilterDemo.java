import static io.restassured.RestAssured.given;

import java.io.File;

import org.testng.Assert;

import files.payload;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class SessionFilterDemo {

	public static void main(String[] args) {
		RestAssured.baseURI = "http://localhost:32769";
		SessionFilter ssn = new SessionFilter();

//		POST: get session ID
		given().header("Content-Type", "application/json").body(payload.credentials()).filter(ssn).when()
				.post("/rest/auth/1/session").then().assertThat().statusCode(200);

//		POST: Create issue
		String createIssue = given().header("Content-Type", "application/json").body(payload.issueBody()).filter(ssn)
				.when().post("/rest/api/2/issue").then().assertThat().statusCode(201).extract().response().asString();
		JsonPath cIObj = new JsonPath(createIssue);
		String key = cIObj.getString("key");
		System.out.println(key);

//		POST: Add comment
		String addCmnt = given().header("Content-Type", "application/json").pathParam("key", key)
				.body(payload.addCmnt()).filter(ssn).when().post("rest/api/2/issue/{key}/comment").then().assertThat()
				.statusCode(201).extract().response().asString();
		JsonPath aCObj = new JsonPath(addCmnt);
		String id = aCObj.getString("id");
		System.out.println(id);

//		PUT: update comment
		String updateCmnt = given().header("Content-Type", "application/json").pathParam("key", key).pathParam("id", id)
				.body(payload.updateCmnt()).when().filter(ssn).put("rest/api/2/issue/{key}/comment/{id}").then()
				.assertThat().statusCode(200).extract().response().asString();
		JsonPath uCObj = new JsonPath(updateCmnt);
		String uid = uCObj.getString("id");
		System.out.println(uid);

//		File attachment
		given().header("X-Atlassian-Token", "no-check").header("Content-Type", "multipart/form-data")
				.pathParam("key", key).filter(ssn).multiPart("file", new File("jira.txt")).when()
				.post("/rest/api/2/issue/{key}/attachments").then().assertThat().statusCode(200);
		
//		GET Issue
		given().filter(ssn).pathParam("key", key).when().get("/rest/api/2/issue/{key}").then().assertThat().statusCode(200);
//		DEL Issue
//		given().pathParam("key", key).filter(ssn).when().delete("/rest/api/2/issue/{key}").then().assertThat()
//				.statusCode(204);
//		System.out.println(key + " deleted");
	}

}
