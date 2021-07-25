package oneWorld.Automation;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import utility.TestFileReader;
import utility.Xls_Reader;

public class TestDataReader {
	private String sheetPath;
	private String fileID;
	private static Xls_Reader xls;
	private static Xls_Reader masterXLSReader;
	public static String browser;
	public static String version;
	public static String Waitforelement;
	public static String WaitfordocUpload;
	public static String WaitforClickPause;
	public static String Resultset_error_only;
	public static String ElementToBeClickable;
	public static String visibilityOfElementLocated;
	public static String screenshot_everystep;
	public static String screenshot_error;
	public static String PageLoadTimeout;
	public static String Url;
	public static String Username;
	public static String Password;
	public static Map<String, String> configMap;
	public static Map<String, String> queryMap;
	public static Map<String, String> screenMap;
	public static Map<String, String> locatorMap = new HashMap<String, String>();

	public TestDataReader(String _sheetPath) {
		sheetPath = _sheetPath;
		xls = new Xls_Reader(sheetPath);
	}

	public TestDataReader(String _sheetPath, String _fileID) {
		sheetPath = _sheetPath;
		fileID = _fileID;
		if (fileID != null) {
			try {
				TestFileReader.downloadFilesFromDrive(sheetPath, fileID);
			} catch (IOException | GeneralSecurityException e) {
				System.err.println("Unable to download spreadsheet from drive.");
				e.printStackTrace();
				System.exit(1);
			}
		}
		xls = new Xls_Reader(sheetPath);
	}

	public TestDataReader(String _sheetPath, String _fileID, String masterFileID, String masterFileName) {
		sheetPath = _sheetPath;
		fileID = _fileID;

		if (fileID != null) {
			try {
				TestFileReader.downloadFilesFromDrive(sheetPath, fileID);
				TestFileReader.downloadFilesFromDrive(masterFileName, masterFileID);
				System.out.println("Downloaded two files.....................");
			} catch (IOException | GeneralSecurityException e) {
				System.err.println("Unable to download spreadsheet from drive.");
				e.printStackTrace();
				System.exit(1);
			}
		}

		xls = new Xls_Reader(sheetPath);
		masterXLSReader = new Xls_Reader(masterFileName);
	}

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public void setPropsInMaps() {
		setConfigProperties();
		setLocatordata();
		queryMap = setdata(Constants.QUERY_SHEET);

		setScreenData();
	}

	public static Map<String, String> setTestdata(String sheetName ,int currentId) {
		HashMap<String, String> maps = new HashMap<String, String>();
		if (xls.isSheetExist(sheetName)) {
			int rowCount = xls.getRowCount(sheetName);
			int colCount = xls.getColumnCount(sheetName);
			for (int i = 0; i <= currentId; i++) {
				for (int j = 0; j < colCount; j++) {
					String runMode = xls.getCellData(sheetName, Constants.RUNMODE, i);
					if (runMode.equalsIgnoreCase(Constants.RUNMODE_YES)) {
						String Key = xls.getCellData(sheetName, j, 1);
						String value = xls.getCellData(sheetName, j, i);
						maps.put(Key, value);
					}
				}
			}
		}
		return maps;
	}

	/**
	 * 
	 * @param sheetName
	 * @return
	 * @throws IOException
	 */
	public Map<String, String> setdata(String sheetName) {
		Map<String, String> dataMap = new HashMap<String, String>();
		if (xls.isSheetExist(sheetName)) {
			for (int i = 1; i <= xls.getRowCount(sheetName); i++) {
				dataMap.put(xls.getCellData(sheetName, 0, i), xls.getCellData(sheetName, 1, i));
			}
		} else {
			System.err.println(sheetName + " Sheet does not exist in Workbook");
			System.exit(1);
		}
		return dataMap;
	}

	/**
	 * 
	 * @return
	 */
	public void setLocatordata() {
		String sheetName = Constants.LOCATOR_SHEET;
		for (int i = 1; i <= masterXLSReader.getRowCount(sheetName); i++) {
		String key = masterXLSReader.getCellData(sheetName, 0, i) + "." + masterXLSReader.getCellData(sheetName, 1, i);
		String value = masterXLSReader.getCellData(sheetName, 2, i);
		locatorMap.put(key, value);
		}
		}

	public void setScreenData() {
		String sheetName = Constants.SCREEN_SHEET;
		Map<String, String> dataMap = new HashMap<String, String>();
		if (masterXLSReader.isSheetExist(sheetName)) {
			for (int i = 1; i <= masterXLSReader.getRowCount(sheetName); i++) {
				dataMap.put(masterXLSReader.getCellData(sheetName, 0, i), masterXLSReader.getCellData(sheetName, 1, i));
			}
		} else {
			System.err.println(sheetName + " Sheet does not exist in Workbook");
			System.exit(1);
		}
		screenMap = dataMap;
	}

	public void setConfigProperties() {
		configMap = setdata(Constants.CONFIG_SHEET);
		browser = configMap.get("BROWSER_TYPE").toString();
		version = configMap.get("BROWSER_VERSION").toString();
		Waitforelement = configMap.get("WAIT_FOR_ELEMENT").toString();
		WaitfordocUpload = configMap.get("WAIT_FOR_DOC_UPLOAD").toString();
		WaitforClickPause = configMap.get("ELEMENT_TO_BE_CLICKABLE").toString();
		Resultset_error_only = configMap.get("RESULTSET_ERROR_ONLY").toString();
		ElementToBeClickable = configMap.get("ELEMENT_TO_BE_CLICKABLE").toString();
		visibilityOfElementLocated = configMap.get("VISIBILITY_OF_ELEMENT_LOCATED");
		screenshot_everystep = configMap.get("SCREENSHOT_EVERY_STEP").toString();
		PageLoadTimeout = configMap.get("PAGE_LOAD_TIMEOUT").toString();
		screenshot_error = configMap.get("SCREENSHOT_ERROR");
		Url = configMap.get("DB_HOST");
		Username = configMap.get("DB_USERNAME");
		Password = configMap.get("DB_PASSWORD");
	}
}
