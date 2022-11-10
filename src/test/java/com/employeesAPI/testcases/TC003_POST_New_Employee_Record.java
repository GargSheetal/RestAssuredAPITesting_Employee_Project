package com.employeesAPI.testcases;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.employeesAPI.base.TestBase;
import com.employeesAPI.utilities.RestUtils;

import io.restassured.RestAssured;
import io.restassured.http.Method;


public class TC003_POST_New_Employee_Record extends TestBase {
	
	String empName = RestUtils.empName();
	String empSalary = RestUtils.empSalary();
	String empAge = RestUtils.empAge();
	
	@BeforeClass
	void getAllEmployees() throws InterruptedException
	{
		logger.info("*************Started TC003_POST_New_Employee_Record");
		RestAssured.baseURI = "https://dummy.restapiexample.com/api/v1";
		httpRequest = RestAssured.given();
		
		// JSONObject is a class that represents a simple JSON. We can add key-value pairs using the put method
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", empName);
		requestParams.put("salary", empSalary);
		requestParams.put("age", empAge);
		
		// Adding a header stating the request body is JSON
		httpRequest.header("Content-Type", "application/json");
		
		// Adding the JSON to the body of the request
		httpRequest.body(requestParams.toJSONString());
		
		response = httpRequest.request(Method.POST, "/create");
		Thread.sleep(3000);
		
	}
	
	@Test(priority=1)
	void checkResponseBody()
	{
		logger.info("Checking Response Body");
		String responseBody = response.getBody().asString();
		Assert.assertEquals(responseBody.contains(empName), true);
		Assert.assertEquals(responseBody.contains(empSalary), true);
		Assert.assertEquals(responseBody.contains(empAge), true);
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
	
		Assert.assertTrue(Integer.parseInt(contentLength)>100);
	}
	
	@AfterClass
	void tearDown()
	{
		logger.info("Finished Started TC003_POST_New_Employee_Record****************");	
	}
	
	
	
	

}
