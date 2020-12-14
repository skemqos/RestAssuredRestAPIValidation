import io.restassured.RestAssured;
import static  io.restassured.RestAssured.*;
import static  org.hamcrest.Matchers.*;

public class TestDemoRestApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssured.baseURI = "http://localhost:8080";
		
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
		
		//Validate Get Student
		given().log().all().header("Content-Type","application/json")
		.when().get("Students")
		.then().log().all().assertThat().and().statusCode(200);
		
		//Validate Post StudentData
		given().log().all().header("Content-Type","application/json").body(" {\r\n" + 
				"        \"studentId\": 4,\r\n" + 
				"        \"studentName\": \"Aarush\",\r\n" + 
				"        \"monthOfBirth\": \"Oct\",\r\n" + 
				"        \"bloodGroup\": \"A+ve\"\r\n" + 
				"    }")
		.when().post("Students")
		.then().log().all().assertThat().and().statusCode(201);
	 //.assertThat().body("msg", equalTo("studentData is created successfully")).header("Content-Type","application/json;charset=UTF-8").extract().asString();
	}
}
