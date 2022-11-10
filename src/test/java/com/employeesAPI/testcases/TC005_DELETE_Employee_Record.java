package com.employeesAPI.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.employeesAPI.base.TestBase;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;

public class TC005_DELETE_Employee_Record extends TestBase {
	
	@BeforeClass
	void getAllEmployees() throws InterruptedException
	{
		logger.info("Started TC005_DELETE_Employee_Record Testcase");
		
		RestAssured.baseURI = "https://dummy.restapiexample.com/api/v1";
		httpRequest = RestAssured.given();
		
		response = httpRequest.request(Method.GET, "/employees");
		
		// First get the JsonPath object instance from the Response Interface
		JsonPath jsonpathEvaluator = response.jsonPath();
		
		// Capture id
		String empid = jsonpathEvaluator.get("[0].id");
		response = httpRequest.request(Method.DELETE, "/delete/" + empid);	// pass the above captured id to delete record
		Thread.sleep(5000);
	}
	
	@Test(priority=1)
	void checkResponseBody()
	{
		logger.info("Checking Response Body");
		String responseBody = response.getBody().asString();
		logger.info("Response body is: " + responseBody);
		Assert.assertEquals(responseBody.contains("Successfully! Record has been deleted"), true);	
	}
	
	@Test
	void checkSuccessCode()
	{
		logger.info("Checking Success Code");
		int successCode = response.getStatusCode();
		Assert.assertEquals(successCode, 200);
	}
	
	@Test
	void checkStatusLine()
	{
		logger.info("Checking Status Line");
		String StatusLine = response.getStatusLine();
		logger.info("Status line is: " + StatusLine);
		Assert.assertEquals(StatusLine, "HTTP/1.1 200 OK");
	}
	
	@Test
	void checkResponseTime()
	{
		logger.info("Checking Response Time");
		long responseTime = response.getTime();
		logger.info("Response Time is: " + responseTime);	// 1415
		
		if(responseTime>2000)
		{
			logger.warn("Response time is greater than 2000");
		}
		Assert.assertTrue(responseTime<2000);
		
	}
	
	@Test
	void checkContentType()
	{
		logger.info("Checking Content-Type");
		String contentType = response.getHeader("Content-Type");
		logger.info("Content type is: " + contentType);
		Assert.assertEquals(contentType, "application/json");	//text/html; charset=UTF-8
	}
	
	@Test
	void checkServer()
	{
		logger.info("Checking Server Type");
		String server = response.getHeader("Server");
		logger.info("Server is: " + server);
		Assert.assertEquals(server, "Apache");
	}
	
	@Test
	void checkContentLength()
	{
		logger.info("Checking Content-Length");
		String contentLength = response.getHeader("Content-Length");
		logger.info("Content-Length is: " + contentLength);
		
		Assert.assertTrue(Integer.parseInt(contentLength)<100);
	}
	
	@AfterClass
	void tearDown()
	{
		logger.info("Finished TC002_GET_Single_Employee_Record testcase");	
	}


}
