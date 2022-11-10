package com.employeesAPI.utilities;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Listeners implements ITestListener{
	
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	
	// This belongs to ITestListener and will execute before starting of Test set/batch
	
	public void onStart(ITestContext context) 
	{
		// create report name with time stamp so that a new report is generated every time and the older is not overridden
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.hh.mm.ss.ssss").format(new Date());
		String reportName = "Test-report-" + timeStamp + ".html";
		
		sparkReporter = new ExtentSparkReporter("./test-output/" + reportName);	// specifying the location of reports
		
		try {
			sparkReporter.loadXMLConfig("./extent-config.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sparkReporter.config().setDocumentTitle("DummyRestApi Test Automation Report");
		sparkReporter.config().setReportName("Employees RestAssured Test Report");
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Host Name", "Local Host");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("Tester", "Sheetal");
		extent.setSystemInfo("OS", "Windows10");
			
	}
	
	// This belongs to ITestListener and will execute after completion of Test set/batch
	
	public void onFinish(ITestContext context) 
	{
		extent.flush();
	}
	
	// This belongs to ITestListener and will execute before the main test start i.e. @Test
	
	public void onTestStart(ITestResult result) 
	{
		System.out.println("Test Started.." + result.getName());
	}
	
	// This belongs to ITestListener and will execute when a test is passed
	
	public void onTestSuccess(ITestResult result) 
	{
		test = extent.createTest(result.getName());	// create new entry in the report
		test.log(Status.PASS, MarkupHelper.createLabel("Testcase PASSED is: " + result.getName(), ExtentColor.GREEN));
	}
	
	// This belongs to ITestListener and will execute when a test is skipped
	
	public void onTestSkipped(ITestResult result) 
	{
		test = extent.createTest(result.getName());
		test.log(Status.SKIP, MarkupHelper.createLabel("Testcase SKIPPED is: " + result.getName(), ExtentColor.ORANGE));
	}
	
	// This belongs to ITestListener and will execute when a test is failed
	
	public void onTestFailure(ITestResult result) 
	{
		test = extent.createTest(result.getName());
		test.log(Status.FAIL, MarkupHelper.createLabel("Testcase FAILED is: " + result.getName(), ExtentColor.RED));
		
	}
	
	
	// not so important.. ignore as of now
	public void onTestFailureButWithinSuccessPercentage(ITestResult result) 
	{
		//TODO Auto-generated method stub
	}
	

}
