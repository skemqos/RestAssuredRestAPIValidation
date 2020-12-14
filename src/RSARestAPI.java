import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static  io.restassured.RestAssured.*;
import static  org.hamcrest.Matchers.*;

import files.payload;

public class RSARestAPI {

 public static void main(String args[]) {

		// TODO Auto-generated method stub
		try {
//Usecase: Automate Validation of AddPlace API
			RestAssured.baseURI = "https://rahulshettyacademy.com";

//Notes: Rest Assured Rest API Implementation Principles for all Http Methods: 
			// 1-of-3. given: specify all inputs
			// 2-of-3. when: submit API: resource, http method
			// 3-of-3. then:

			// 1-of-3. given
			// 2-of-3. when
			// 3-of-3. then
			
			//scenario-1. http response status code 200 assertion
			/*
			 * given().log().all().queryParam("key", "qaclick123").header("Content-Type",
			 * "application/json") .body("{\r\n" + "  \"location\": {\r\n" +
			 * "    \"lat\": -38.383494,\r\n" + "    \"lng\": 33.427362\r\n" + "  },\r\n" +
			 * "  \"accuracy\": 50,\r\n" + "  \"name\": \"RahulSettyAcademy\",\r\n" +
			 * "  \"phone_number\": \"(+91) 983 893 3937\",\r\n" +
			 * "  \"address\": \"29, side layout, cohen 09\",\r\n" + "  \"types\": [\r\n" +
			 * "    \"shoe park\",\r\n" + "    \"shop\"\r\n" + "  ],\r\n" +
			 * "  \"website\": \"http://google.com\",\r\n" +
			 * "  \"language\": \"French-IN\"\r\n" + "}")
			 * .when().post("maps/api/place/add/json").then().log().all().assertThat().
			 * statusCode(200);
			 */
			 
			//scenario-2. http response status code 200, response body, headers assertion
			/*
			 * given().log().all().queryParam("key", "qaclick123").header("Content-Type",
			 * "application/json") .body(payload.addPlace())
			 * .when().post("maps/api/place/add/json")
			 * .then().log().all().assertThat().statusCode(200).body("scope",
			 * equalTo("APP")).header("Server", "Apache/2.4.18 (Ubuntu)");
			 */
			 
			  //scenario-3. Add Place -> Update Place for New Address -> GetPlace to validate Address actually updated
			  String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type",
			  "application/json") .body(payload.addPlace())
			  .when().post("maps/api/place/add/json")
			  .then().assertThat().statusCode(200).body("scope", equalTo("APP")).header("Server", "Apache/2.4.18 (Ubuntu)").extract().asString();
			 System.out.println("Response string(given,when,then)="+response);
			  
			 JsonPath jp = new JsonPath(response);
			 String placeId = jp.getString("place_id");
			 System.out.println("placeId="+placeId);
		} catch (Exception e) {
			e.printStackTrace();
		}
 }

}
