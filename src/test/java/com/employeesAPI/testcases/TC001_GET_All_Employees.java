package com.employeesAPI.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.employeesAPI.base.TestBase;

import io.restassured.RestAssured;
import io.restassured.http.Method;

public class TC001_GET_All_Employees extends TestBase {
	
	@BeforeClass
	void getAllEmployees() throws InterruptedException
	{
		logger.info("Started TC001_GET_All_Employees testcase");
		RestAssured.baseURI = "https://dummy.restapiexample.com/api/v1";
		httpRequest = RestAssured.given();
		response = httpRequest.request(Method.GET, "/employees");
		Thread.sleep(5000);
	}
	
	@Test(priority=1)
	void checkResponseBody()
	{
		logger.info("Checking Response Body");
		String responseBody = response.getBody().asString();
		Assert.assertTrue(responseBody!=null);	
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
		Assert.assertEquals(server, "nginx/1.21.6");
	}
	
	@Test
	void checkContentEncoding()
	{
		logger.info("Checking Content-Encoding");
		String contentEncoding = response.getHeader("Content-Encoding");
		logger.info("Content-Encoding is: " + contentEncoding);
		Assert.assertEquals(contentEncoding, "gzip");
	}
	
	@Test
	void checkContentLength()
	{
		logger.info("Checking Content-Length");
		String contentLength = response.getHeader("Content-Length");
		logger.info("Content-Length is: " + contentLength);
		
		if(Integer.parseInt(contentLength)<100)
		{
			logger.warn("Content length is less than 100");
		}
		Assert.assertTrue(Integer.parseInt(contentLength)>100);
	}
	
	@AfterClass
	void tearDown()
	{
		logger.info("Finished TC001_GET_All_Employees testcase");	
	}
	
	
	
	
	

}
