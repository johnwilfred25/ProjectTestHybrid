package oneWorld.Automation;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class Executor {
	public static String path;
	public static String masterFilePath;
	public static String module = "";
	public static String master_module = "masterFile";
	public static String branch = "config";
	public static String release = "";
	public static String buildNumber = "New Build";
	public static String testcase = "";
	public static String BuildDate = "";
	public static String author = "7T Automation Team";
	public static boolean result = true;
	public static String USER_DIR = System.getProperty("user.dir");
	public static String baseUrl;
	public static String apiUrl;
	public static String fileID;
	public static String masterFileID;
	public static String APP_CONFIG_PATH = "//config//";
	
	public static FileInputStream fs;

	public static void main(String[] args) throws Exception {
		
		printMessage("Automation Started ...");
		if (System.getProperty("CONFIG") != null) {
			
		    // Load properties file from the config property and set the configuration properties
			String configFileDir = USER_DIR + APP_CONFIG_PATH + System.getProperty("CONFIG") + ".properties";
			fs = new FileInputStream(configFileDir);
			Properties CONFIG = new Properties();
			CONFIG.load(fs);
			loadProperties(CONFIG);
			System.out.println("Deriving Testcases from : " + module);

			//Create the xls Directory if doesn't exists
			File xlsPath = new File(USER_DIR + "/xls");
			if (!xlsPath.exists()) {
				xlsPath.mkdir();
			}
			
			//Configure the path for masterfile based on the Operating System
			configurePathForOS();
			
			//Download the Testsuite and Master Files and set all necessary configurations
			TestDataReader testData = new TestDataReader(path, fileID, masterFileID, masterFilePath);
			testData.setPropsInMaps();
			
			//Executing the TestScript
			DriverScript driverScript = new DriverScript(TestDataReader.browser, TestDataReader.version, module, testcase);
			result = driverScript.callExecutor();
			
			if (result == false) {
				System.exit(1);
			}
		} else {
			printMessage("No Coniguration Found in the Environment properties");
		}
	}

	private static void configurePathForOS() {
		String OS = System.getProperty("os.name").toLowerCase();
		System.out.println("OS Name: " + OS);
		if (OS.contains("window")) {
			path = USER_DIR + "\\xls\\" + module + ".xlsx";
			masterFilePath = USER_DIR + "\\xls\\" + master_module + ".xlsx";
		} else if (OS.contains("mac")) {
			path = USER_DIR + "/xls/" + module + ".xlsx";
			masterFilePath = USER_DIR + "/xls/" + master_module + ".xlsx";
		}
	}

	private static void loadProperties(Properties CONFIG) {
		module = CONFIG.getProperty("driverExcelName");
		master_module = CONFIG.getProperty("masterExcelFIle");
		fileID = CONFIG.getProperty("fileID");
		masterFileID = CONFIG.getProperty("masterFileID");
		baseUrl = CONFIG.getProperty("targetURL");
		apiUrl = CONFIG.getProperty("targetAPIURL");
		
		if(System.getProperty("testcase")!=null && System.getProperty("testcase").equalsIgnoreCase("ALL")) {
			testcase = "ALL";
		}
	}

	private static void printMessage(String string) {
		
		System.out.println("=========================================================");
		System.out.println(string);
		System.out.println("=========================================================");
		
		
	}
}
