import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static  org.hamcrest.Matchers.*;

import java.io.File;

import org.apache.http.util.Asserts;
import org.testng.Assert;

public class ValidateJiraRestAPI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RestAssured.baseURI= "http://localhost:8080";
		
		//Usecase-1. Validate Jira authentication
		SessionFilter session = new SessionFilter();
		
		String response =given().relaxedHTTPSValidation().log().all().header("Content-Type", "application/json")
							.body("{ \"username\": \"sreekanth.mallela\", \"password\": \"Jira123\" }")
							.filter(session)
						.when().post("rest/auth/1/session")
						.then().log().all().assertThat().statusCode(200).extract().asString();
			
		//Usecase-2. Validate AddJiraCommentAPI, follow up to Usecase-1
		String expectedMsg="Hi How are you?";
		String addCommentResponse = given().pathParam("id", "10104").log().all().header("Content-Type", "application/json")
			.body("{\r\n" + 
					"    \"body\": \""+expectedMsg+"\",\r\n" + 
					"    \"visibility\": {\r\n" + 
					"        \"type\": \"role\",\r\n" + 
					"        \"value\": \"Administrators\"\r\n" + 
					"    }\r\n" + 
					"}")
			.filter(session)
		.when().post("rest/api/2/issue/{id}/comment")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		JsonPath jp = new JsonPath(addCommentResponse);
		String commentId = jp.getString("id");
		
		  //Add attachment 
		   given().pathParam("key", "10104").log().all()
		  .filter(session).header("X-Atlassian-Token",
		  "no-check").header("Content-Type","multipart/form-data")
		  .multiPart("file",new File("jira.txt"))
		  .when().post("rest/api/2/issue/{key}/attachments")
		  .then().log().all().assertThat().statusCode(200);
		   
		   //GetIssue API
		   String getAPIResponse=given().pathParam("key", "10104").log().all().filter(session)
			.when().get("rest/api/2/issue/{key}")
			.then().log().all().assertThat().statusCode(200).extract().response().asString();
		   //Validate comment retrieved is indeed the comment that was added in Jira
		   
		   JsonPath jp1 = new JsonPath(getAPIResponse);
		   int commentsCount = jp1.getInt("fields.comment.comments.size()");
		   
		   for (int i=0; i< commentsCount; i++ ) {
			   String retirevedCommentId = jp1.get("fields.comment.comments["+i+"].id").toString();
			   
			   if (retirevedCommentId.equalsIgnoreCase(commentId)) {
				   String commentMessage = jp1.get("fields.comment.comments["+i+"].body").toString();
				   System.out.println("Comments::"+commentMessage);
				   Assert.assertEquals(commentMessage,expectedMsg);
			   }
		   }
	}
}
