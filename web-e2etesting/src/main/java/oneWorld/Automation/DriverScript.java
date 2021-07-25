package oneWorld.Automation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import utility.DateUtility;
import utility.ReportUtility;
import utility.Utilities;
import utility.Xls_Reader;

public class DriverScript extends ReportUtility {
	public static ExtentTest htmlReport;
	public static ExtentTest nodes;
	public Xls_Reader suiteXLS;
	public int currentSuiteID;
	public String currentTestSuite;
	public static String currentTestSuitecpy;
	public static int datarowid;
	public int startIndex = 0;
	public String result = Constants.KEYWORD_FAIL;
	public String testcase;
	public static Xls_Reader currentTestSuiteXLS; // current test suite
	public static int currentTestCaseID;
	public static String currentTestCaseName;
	public static String description;
	public static int currentTestStepID;
	public static String currentKeyword;
	public static int currentTestDataSetID = 2;
	public static int pass = 0;
	public static int fail = 0;
	public static int skip = 0;
	public static int Total_testcase=0 ;
	public static int tcnumber = 0;
	public static Method method[];
	public static Method capturescreenShot_method;
	public FileInputStream fis;
	public static GenericKeywords keywords;
	public static String keyword_execution_result;
	public static String keyword_execution_time;
	public static String keyword_execution_screenshot;
	public static ArrayList<String> resultSet;
	public static ArrayList<String> screenshotSet;
	public static String data;
	public static String columnName;
	public static String TCSDesc;
	public static String refexcelfilename;
	public static String refexcelsheetname;
	public static String object;
	public static String objectValue;
	public static String project;
	public Boolean executeTestCase = true;
	public static int finalRow;
	public static int totaltc;
	public static Properties OR; // public static Properties CONFIG;
	public static FileInputStream fs;
	public static String HtmlResultsPath;
	public static WebDriver executionDriver;
	public static String browser;
	public static String version;
	public static String module;
	public static String buildDate = Executor.BuildDate;
	public static boolean videoRecording = false;
	public static String dest_path = "";
	public static ArrayList<String> resulttestSet;
	long timeDifInMilliSec;
	int openUrlCount = 0;
	public static String api;
	public static String api_name;
	public static String api_method;
	public static String api_body;
	public static String api_username;
	public static String api_password;
	public static String api_input_params;
	public static String api_url;
	public Map<String, String> dataValuesMap = new HashMap<String, String>();
	public List<Map<String, String>> dataValuesMapList = new ArrayList<Map<String,String>>();
	
	public DriverScript() throws NoSuchMethodException, SecurityException, IOException {
		keywords = new GenericKeywords();
		method = keywords.getClass().getMethods();
		capturescreenShot_method = keywords.getClass().getMethod("captureScreenshot", String.class, String.class);
	}
	
	public DriverScript(String browser, String version, String module, String testcase) {
		DriverScript.browser = browser;
		DriverScript.version = version;
		DriverScript.module = module;
		this.testcase = testcase;
	}

	public boolean callExecutor()
			throws Exception {
		
		DriverScript test = new DriverScript();
		
		long startTime = DateUtility.getCurrentTimeInMillis();
		
		test.start(); // Start execution

		long EndTime = DateUtility.getCurrentTimeInMillis();
		printExecutionTime(startTime, EndTime, "Total taken time to execute complete suite is");

		System.out.println("Total Number of Test Cases: " + Total_testcase);
		System.out.println("Executed Test Cases: " + tcnumber);
		System.out.println("Passed Test Cases: " + pass);
		System.out.println("Failed Test Cases: " + fail);

		//new AWSS3utility().uploadFileToS3(new File(System.getProperty("user.dir") + "/reports/" + Executor.module + "_AutomationResult.html").getName(),
				//new File(System.getProperty("user.dir") + "/reports/" + Executor.module + "_AutomationResult.html"));
		//Generate Report
		generateCompressedReport();
		return executeTestCase;
	}

	private void printExecutionTime(long startTime, long EndTime, String Message) {
		if (startTime <= EndTime) {
			timeDifInMilliSec = EndTime - startTime;
		} else {
			timeDifInMilliSec = startTime - EndTime;
		}
		long timeDifSeconds = timeDifInMilliSec / 1000;
		long timeDifMinutes = timeDifInMilliSec / (60 * 1000);
		long timeDifHours = timeDifInMilliSec / (60 * 60 * 1000);
		System.out.println(Message + " " + timeDifHours + " " + "Hrs" + " "
				+ timeDifMinutes + " " + "Mins" + " " + timeDifSeconds + " " + "sec");
	}

	public void start() throws Exception {
		
		/* 
		 * Clear the Screenshots in the directory 
		 */
		cleanScreenshotDirectory();
		
		/* 
		 * Copy the TestSuite to TestSuite_browser 
		 */
		currentTestSuite = DriverScript.module;
		currentTestSuitecpy = DriverScript.module;
		File source = new File(System.getProperty("user.dir") + "//xls//" + currentTestSuite + ".xlsx");
		File target = new File(System.getProperty("user.dir") + "//xls//" + currentTestSuite + "_" + browser + ".xlsx");
		currentTestSuitecpy = currentTestSuite + "_" + browser;
		FileUtils.copyFile(source, target);
		System.out.println("currentTestSuiteXLS: " + target);
		currentTestSuiteXLS = new Xls_Reader(target.getPath());
		refexcelfilename = System.getProperty("user.dir") + "//xls//" + currentTestSuite + "_" + browser + ".xlsx";
		
		
		tcnumber = 0;
		totaltc = 0;
		currentTestSuiteXLS.clearCellData(Constants.TEST_CASES_SHEET, "Status", ""); // To clear the status column in current running excel sheet
		
		
		for (currentTestCaseID = 2; currentTestCaseID <= currentTestSuiteXLS
				.getRowCount("Test Cases"); currentTestCaseID++) {
			if (currentTestSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, Constants.RUNMODE, currentTestCaseID)
					.equals("")
					&& !currentTestSuiteXLS
							.getCellData(Constants.TEST_CASES_SHEET, Constants.TCID, currentTestCaseID)
							.equals("")) {
				currentTestSuiteXLS.setCellData(Constants.TEST_CASES_SHEET, Constants.RUNMODE, currentTestCaseID,
						"Y");
			}
		}
		
		resulttestSet = new ArrayList<String>();
		
		/* 
		 * Commented the below method code as getting executedtestcasescount code is already implemented in the other part (tcnumber)
		 */
		
		/* 
		 * Iterate through each test case in Test Cases sheet and execute the test steps 
		 */
		for (currentTestCaseID = 2; currentTestCaseID <= currentTestSuiteXLS
				.getRowCount("Test Cases"); currentTestCaseID++) {
			currentTestCaseName = currentTestSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, Constants.TCID,
					currentTestCaseID);
			TotalTestcaseCount();
			
			description = currentTestSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, Constants.DESCRIPTION,
					currentTestCaseID);
			//
			if (currentTestSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, Constants.RUNMODE, currentTestCaseID)
					.equals(Constants.RUNMODE_YES)) {
				
				htmlReport = report.createTest(description,currentTestCaseName);
				nodes = getNodeReference();
				tcnumber = tcnumber + 1;
				
				//Iterate through each row in the test data sheet and run the test cases.
				if (currentTestSuiteXLS.isSheetExist(currentTestCaseName)) {
					// RUN as many times as number of test data sets with runmode Y
					currentTestSuiteXLS.clearCellData(currentTestCaseName, "Result", "");
					System.out.println("total count "+currentTestSuiteXLS
							.getRowCount(currentTestCaseName));
					for (currentTestDataSetID = 2; currentTestDataSetID <= currentTestSuiteXLS
							.getRowCount(currentTestCaseName); currentTestDataSetID++) {
						
						long startTime = DateUtility.getCurrentTimeInMillis();
						resultSet = new ArrayList<String>();
						screenshotSet = new ArrayList<String>();
						if (currentTestSuiteXLS
								.getCellData(currentTestCaseName, Constants.RUNMODE, currentTestDataSetID)
								.equals(Constants.RUNMODE_YES)) {
							// iterating through all keywords
							System.out.println();
							System.out.println("***************Executing Test Case***************");
							System.out.println("TC_ID: " + currentTestCaseName);
							System.out.println("TC_DESCRIPTION: " + description);
							
							if(!Executor.testcase.equalsIgnoreCase("ALL")) {
								keywords.OPEN_BROWSER("", browser);
								executeKeywords(); // multiple sets of data
								keywords.CLOSE_BROWSER("", browser);
							}else {
								executeKeywords(); 
							}
							
							createXLSReport();
							// Logging for Test Case level Status
							currentTestSuiteXLS.setCellData(Constants.TEST_CASES_SHEET, Constants.TC_STATUS,
									currentTestCaseID, LogTestCaseResult());
							resulttestSet.add(
									currentTestCaseName + "||" + " " + " Datasheet row no.: " + currentTestDataSetID
											+ "||" + description + " " + "||" + " " + LogTestCaseResult());
							if (LogTestCaseResult().equalsIgnoreCase("PASS")) {
								pass++;
							} else if (LogTestCaseResult().equalsIgnoreCase("FAIL")) {
								fail++;
							} else if (LogTestCaseResult().equalsIgnoreCase("SKIP")) {
								skip++;
							}
						}
						boolean iskeywordFail = false;
						Iterator<String> resultIter = resultSet.iterator();
						while (resultIter.hasNext()) {
							if (resultIter.next().startsWith(Constants.KEYWORD_FAIL)) {
								iskeywordFail = true;
								break;
							}
						}
						if (currentTestSuiteXLS
								.getCellData(currentTestCaseName, Constants.RUNMODE, currentTestDataSetID)
								.equals(Constants.RUNMODE_YES)) {
							if (iskeywordFail) {
								System.out.println(currentTestCaseName + " resultSet >> TEST FAILED " + resultSet);
							} else {
								System.out.println(currentTestCaseName + " resultSet >> TEST PASSED " + resultSet);
							}
							long endTime = DateUtility.getCurrentTimeInMillis();
							printExecutionTime(startTime, endTime, "Running " + currentTestCaseName + " for Test Data row no >> "
									+ currentTestDataSetID + " took >> ");

							System.out.println();
							System.out.println("*********Status of Last Executed Test Cases*************** ");
							for (int i = 0; i < resulttestSet.size(); i++) {
								System.out.println(resulttestSet.get(i));
							}
						} else if (!(currentTestSuiteXLS
								.getCellData(currentTestCaseName, Constants.TCID, currentTestDataSetID).trim()
								.equals("")
								|| currentTestSuiteXLS
										.getCellData(currentTestCaseName, Constants.DESCRIPTION, currentTestDataSetID)
										.equals(null))) {
							System.out.println("Skipping to run " + currentTestCaseName + " for Test Data row no >> "
									+ currentTestDataSetID);
						}
					}
				} else {
					// iterating through all keywords
					resultSet = new ArrayList<String>();
					keywords.OPEN_BROWSER("", browser);
					executeKeywords();
					//keywords.CLOSE_BROWSER("", browser);
					createXLSReport();
				}
			}
		}
		try {
			if(executionDriver.getTitle()!=null) {
				keywords.CLOSE_BROWSER("", browser);
			}
		}catch(Exception e) {}
		report.setSystemInfo("Operating System", System.getProperty("os.name"));
		report.setSystemInfo("OS Version", System.getProperty("os.version"));
		report.flush();
	}

	public static ExtentTest getNodeReference() throws IOException {
		return nodes = htmlReport.createNode(description);
	}

	/* 
	 * Iterate through all the test steps of the test case and execute with the keywords
	 */
	public void executeKeywords() throws Exception {
		Boolean blnFlag = false;
		Boolean rowFlag = false;
		boolean keywordExecutor = true;
		
		// iterating through all keywords
		for (currentTestStepID = 2; currentTestStepID <= currentTestSuiteXLS
				.getRowCount(Constants.TEST_STEPS_SHEET); currentTestStepID++) {
			// checking TCID
			if (currentTestCaseName.equals(
					currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.TCID, currentTestStepID))) {
				if (rowFlag == false) {
					finalRow = currentTestStepID;
					rowFlag = true;
				}
				columnName = currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.DATA,
						currentTestStepID);
				object = currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.OBJECT,
						currentTestStepID);
				currentKeyword = currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.KEYWORD,
						currentTestStepID);
				TCSDesc = currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.DESCRIPTION,
						currentTestStepID);

				//If testcase execution is ALL, then below code avoids opening the new browser and login again with credentials
				if(currentKeyword.contains("OPEN_URL")) {
					openUrlCount = openUrlCount + 1;
				}
				if(openUrlCount > 1 && currentKeyword.equals("OPEN_URL") && Executor.testcase.equalsIgnoreCase("ALL")) {
					System.out.println("Current Keyword ==> "+currentKeyword); 
					currentTestStepID = currentTestStepID + 4; 
					keywords.REFRESH_PAGE("", browser);
					continue; 
				}
				
				//Executing the keywords
				if (executeTestCase == true) {
					blnFlag = false;
					for (int i = 0; i < method.length; i++) {
						method[i].getName();
						if (method[i].getName().equals(currentKeyword)) {
							if (currentKeyword.contains("OPEN_BROWSER") || currentKeyword.contains("CLOSE_BROWSER")) {
								resultSet.add(Constants.KEYWORD_SKIP);
								break;
							}
							blnFlag = true;
							if (keywordExecutor) {
								long startTime = DateUtility.getCurrentTimeInMillis();
								objectValue = TestDataReader.locatorMap.get(object.trim());
								System.out.println("executingKeyword: " + method[i].getName());
								if (!object.equalsIgnoreCase("")) {
									System.out.println("object: " + object);
								
								//if (!objectValue.equalsIgnoreCase("")) {
									System.out.println("objectValue: " + objectValue);
								//}
								}
								data = columnName;
								if (!data.equalsIgnoreCase("")) {
									System.out.println("data: " + data);
								}
								method[i].setAccessible(true);
								if (data.contains("Config.")) {
									data = data.replace("Config.", "");
									data = TestDataReader.configMap.get(data);
								} else if (data.contains("Screens")) {
									data = data.replace("Screens.", "");
									data = TestDataReader.screenMap.get(data).toString();
								} else if (data.contains("REST")) {
									String restMethod = data.split("\\.")[1];
									String testDataKey = data.split("\\.")[2];
									System.out.println("REST DETAILS ===> "+ restMethod + " "+testDataKey);
									dataValuesMap = Utilities.getJson(restMethod).get(0);
									data = (String) dataValuesMap.get(testDataKey);
								} else if (data.contains("Query")) {
									data = data.replace("Query.", "");
									data = TestDataReader.queryMap.get(data).toString();
								} else {
									if (data == "" || data == null) {
										data = "";
									} else {
										String testCaseID = data.split("\\.")[0];
										String testDataKey = data.split("\\.")[1];
										Map<String, String> testDataMap = TestDataReader.setTestdata(testCaseID,currentTestDataSetID);
										data = testDataMap.get(testDataKey);
									}
								}
								if (!data.equalsIgnoreCase("")) {
									System.out.println("dataValue: " + data);
								}
								
								executionDriver = GenericKeywords.driver;
								try {
									executionDriver.getTitle();
								} catch (Exception e) {
									keywords.OPEN_BROWSER("", browser);
								}
								keyword_execution_result = (String) method[i].invoke(keywords, objectValue, data);
								System.out.println("status: " + keyword_execution_result);
								long endTime = DateUtility.getCurrentTimeInMillis();
								printExecutionTime(startTime, endTime, "Total time taken to execute keyword  is");
								if (keyword_execution_result.equalsIgnoreCase(Constants.KEYWORD_FAIL)) {
								} else if (data == "" || data == null) {
									DriverScript.nodes.log(Status.PASS, "" + TCSDesc);
								} else {
									DriverScript.nodes.log(Status.PASS, "" + TCSDesc + " - " + data );
								}
								System.out.println();
								if (!keyword_execution_result.equalsIgnoreCase(Constants.KEYWORD_PASS)) {
									keywordExecutor = false;
									keywords.captureScreenshot(currentTestSuite + "_" + currentTestCaseName + "_TS"
											+ currentTestStepID + "_" + (currentTestDataSetID - 1),
											keyword_execution_result);
									resultSet.add(keyword_execution_result);
									break;
								}
							}

							Thread.sleep(0);
							data = "";
							if (keywordExecutor) {
								resultSet.add(keyword_execution_result);
							}
							
						} else if (i == (method.length - 1) && (!blnFlag)) {
							resultSet.add(Constants.KEYWORD_FAIL + " Keyword not found :" + currentKeyword);
						}
					}
				}
			}
		}
	}

	public void createXLSReport() {
		try {
			String colName = Constants.RESULT + (currentTestDataSetID - 1);
			boolean isColExist = false;
			for (int c = 0; c < currentTestSuiteXLS.getColumnCount(Constants.TEST_STEPS_SHEET); c++) {
				if (currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, c, 1).equals(colName)) {
					isColExist = true;
					break;
				}
			}
			if (!isColExist)
				currentTestSuiteXLS.addColumn(Constants.TEST_STEPS_SHEET, colName);
			//
			boolean rFlag = false;
			if (resultSet.size() != 0) {
				for (int i = 0; i < resultSet.size(); i++) {
					if (!resultSet.get(i).startsWith(Constants.KEYWORD_PASS)
							&& !resultSet.get(i).startsWith(Constants.KEYWORD_WARN)) {
						rFlag = true;
						break;
					}
				}
				if (rFlag == true) {
					currentTestSuiteXLS.setCellData(currentTestCaseName, Constants.RESULT, currentTestDataSetID,
							"FAIL");
				} else {
					currentTestSuiteXLS.setCellData(currentTestCaseName, Constants.RESULT, currentTestDataSetID,
							"PASS");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error in CreateXLSReport Method " + e);
		}
		finalRow = 2;
	}

	public String LogTestCaseResult() {
		List<String> runStatusList = new ArrayList<String>();
		int noOfRows = currentTestSuiteXLS.getRowCount(currentTestCaseName);
		for (int rowPointer = 2; rowPointer <= noOfRows; rowPointer++) {
			String currStatus = currentTestSuiteXLS.getCellData(currentTestCaseName, Constants.RESULT, rowPointer);
			if (currStatus.startsWith("PASS"))
				runStatusList.add("PASS");
			else if (currStatus.startsWith("FAIL"))
				runStatusList.add("FAIL");
			else if (currStatus.equals("") || currStatus.startsWith("SKIP"))
				runStatusList.add("SKIP");
		}
		if (runStatusList.contains("FAIL")) {
			return Constants.KEYWORD_FAIL;
		} else if (!runStatusList.contains("FAIL") && !runStatusList.contains("PASS")
				&& runStatusList.contains("SKIP")) {
			return Constants.KEYWORD_SKIP;
		} else {
			return Constants.KEYWORD_PASS;
		}
	}
	public void TotalTestcaseCount() {
		if (currentTestCaseName.contains("TC_SNT")) {
			
			Total_testcase++;
		}
	}
}
