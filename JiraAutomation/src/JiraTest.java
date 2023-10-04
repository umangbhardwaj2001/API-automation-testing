import static io.restassured.RestAssured.given;

import org.testng.Assert;

import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class JiraTest {

	public static void main(String[] args) {
		RestAssured.baseURI = "http://localhost:32769";

//		POST: get session ID
		String SID = given().header("Content-Type", "application/json").body(payload.credentials()).when()
				.post("/rest/auth/1/session").then().assertThat().statusCode(200).extract().response().asString();

		JsonPath sObj = new JsonPath(SID);
		String cookie = sObj.getString("session.value");
		System.out.println("Cookie: " + cookie);

//		POST: Create issue
		String createIssue = given().header("Content-Type", "application/json").header("Cookie", "JSESSIONID=" + cookie)
				.body(payload.issueBody()).when().post("/rest/api/2/issue").then().assertThat().statusCode(201)
				.extract().response().asString();
		JsonPath cIObj = new JsonPath(createIssue);
		String key = cIObj.getString("key");
		System.out.println(key);

//		POST: Add comment
		String addCmnt = given().header("Content-Type", "application/json").header("Cookie", "JSESSIONID=" + cookie)
				.pathParam("key", key).body(payload.addCmnt()).when().post("rest/api/2/issue/{key}/comment").then()
				.assertThat().statusCode(201).extract().response().asString();
		JsonPath aCObj = new JsonPath(addCmnt);
		String id = aCObj.getString("id");
		System.out.println(id);

//		PUT: update comment
		String updateCmnt = given().header("Content-Type", "application/json").header("Cookie", "JSESSIONID=" + cookie)
				.pathParam("key", key).pathParam("id", id).body(payload.updateCmnt()).when()
				.put("rest/api/2/issue/{key}/comment/{id}").then().assertThat().statusCode(200).extract().response()
				.asString();
		JsonPath uCObj = new JsonPath(updateCmnt);
		String uid = uCObj.getString("id");
		System.out.println(uid);

//		DEL Issue
		given().header("Cookie", "JSESSIONID=" + cookie).pathParam("key", key).when().delete("/rest/api/2/issue/{key}")
				.then().assertThat().statusCode(204);
		System.out.println(key + " deleted");
	}

}
