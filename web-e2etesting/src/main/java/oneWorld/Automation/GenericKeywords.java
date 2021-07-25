package oneWorld.Automation;

import static oneWorld.Automation.DriverScript.currentTestDataSetID;
import static oneWorld.Automation.DriverScript.currentTestSuiteXLS;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.github.opendevl.JFlat;
import com.paulhammant.ngwebdriver.NgWebDriver;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import utility.DateUtility;
import utility.PostGresUtitity;
import utility.ReportUtility;

public class GenericKeywords extends ReportUtility {
    public String windowHandle;
    public FileInputStream fis = null;
    public FileOutputStream fileOut = null;
    private XSSFWorkbook workbook = null;
    private XSSFSheet sheet = null;
    private XSSFRow row = null;
    private XSSFCell cell = null;
    public int startIndex = 0;
    public static FileInputStream fs;
    private static final String TASKLIST = "tasklist";
    private static final String KILL = "taskkill /IM ";
    public static Properties MSG;
    public static WebDriver driver;
    public static NgWebDriver ngWebDriver;
    public String result = Constants.KEYWORD_FAIL;
    public static boolean screenshotfoldercreate = true;
    public static String filePath = "";
    public static List<String> keywordLevelErrorList = new ArrayList<String>();
    public static Properties msgCONFIG;
    public static JSONObject jsonObject = null;
    public static JSONObject reqGeneric = null;
    public static JSONArray jhGenericJsonArray;
    public static String objectvalue;
    public WebDriver newdriver;
    int waitforelement = Double.valueOf(TestDataReader.Waitforelement).intValue();
    // Integer.parseInt(TestDataReader.Waitforelement.toString());
    int waitfordocUpload = Double.valueOf(TestDataReader.WaitfordocUpload).intValue();
    int waitforClickPause = Double.valueOf(TestDataReader.WaitforClickPause).intValue();
    long pageLoadTimeout = Double.valueOf(TestDataReader.PageLoadTimeout).intValue();
    String baseUrl = Executor.baseUrl;
    Executor data = new Executor();
    public boolean flag = true;
    public String randomEmail;
    public String randomName;
    public String randomPhoneNumber;
    public String randomPassword;
    public String nextDate;
    public String PIN;
    public String randomPin;
    public String randomquote;
    public static String GMAIL_ID;
    public static String browsername;
    public static String OPEN_BROWSER;
    public static String docName;
    public static Map<String, String> localStorage;

    /**
     * This function is used to perform click event on specified link
     *
     * @param String - This var is used to identify object properties
     * @param data   - This var is used to as a test data
     * @return This function is return the status of click event perform on link
     * object.
     */


    public By OBJECT_TYPE_IDENTIFIER(String OR_props_key_value, String... data) {
        By by = null;
        try {
            String[] xpath_split;
            String object_xpath_val;
            String TrimmedXpath;
            if (OR_props_key_value.contains("***")) {
                TrimmedXpath = OR_props_key_value.replace("ByXpath=", "");
                OR_props_key_value = TrimmedXpath;
                if (String.join(" ", data).contains("RandomEmail")) {
                    System.out.println("INSIDE random email" + randomEmail);
                    object_xpath_val = OR_props_key_value.replace("***", randomEmail);
                    System.out.println("updated object : " + object_xpath_val);
                    by = By.xpath(object_xpath_val);
                } else if (String.join(" ", data).contains("GMAIL_ID")) {
                    System.out.println("INSIDE random name" + randomName);
                    object_xpath_val = OR_props_key_value.replace("***", GMAIL_ID);
                    System.out.println("updated object : " + object_xpath_val);
                    by = By.xpath(object_xpath_val);
                } else if (String.join(" ", data).contains("RandomName")) {
                    System.out.println("INSIDE random name" + randomName);
                    object_xpath_val = OR_props_key_value.replace("***", randomName);
                    System.out.println("updated object : " + object_xpath_val);
                    by = By.xpath(object_xpath_val);
                } else if (String.join(" ", data).contains("MATCHED_ELEMENT")) {
                    System.out.println("INSIDE random name" + localStorage.get("matchedElement"));
                    object_xpath_val = OR_props_key_value.replace("***", localStorage.get("matchedElement"));
                    System.out.println("updated object : " + object_xpath_val);
                    by = By.xpath(object_xpath_val);
                } else if (String.join(" ", data).contains("POLICY_NUMBER")) {
                    System.out.println("INSIDE random name" + localStorage.get("Policy_Number"));
                    object_xpath_val = OR_props_key_value.replace("***", localStorage.get("Policy_Number"));
                    System.out.println("updated object : " + object_xpath_val);
                    by = By.xpath(object_xpath_val);
                } else if (String.join(" ", data).contains("CARRIER_NAME")) {
                    System.out.println("INSIDE random name" + localStorage.get("Carrier_Name"));
                    object_xpath_val = OR_props_key_value.replace("***", localStorage.get("Carrier_Name"));
                    System.out.println("updated object : " + object_xpath_val);
                    by = By.xpath(object_xpath_val);
                } else {

                    object_xpath_val = OR_props_key_value.replace("***", String.join(" ", data));
                    by = By.xpath(object_xpath_val);
                }
            } else if (OR_props_key_value.startsWith("ByXpath=")) {
                object_xpath_val = OR_props_key_value.replace("ByXpath=", "");
                by = By.xpath(object_xpath_val);
            } else if (OR_props_key_value.contains("ByLinktext=")) {
                xpath_split = OR_props_key_value.split("=");
                object_xpath_val = xpath_split[1];
                by = By.linkText(object_xpath_val);
            } else if (OR_props_key_value.contains("ById=")) {
                xpath_split = OR_props_key_value.split("=");
                object_xpath_val = xpath_split[1];
                by = By.id(object_xpath_val);
            } else if (OR_props_key_value.contains("ByCss=")) {
                object_xpath_val = OR_props_key_value.replace("ByCss=", "");
                by = By.cssSelector(object_xpath_val);
            } else if (OR_props_key_value.contains("ByName=")) {
                xpath_split = OR_props_key_value.split("=");
                object_xpath_val = xpath_split[1];
                by = By.name(object_xpath_val);
            }
        } catch (Exception e) {
            result = Constants.KEYWORD_FAIL;
            e.printStackTrace();
        }
        return by;
    }

    /**
     * This function is used to open browser.
     *
     * @param object -
     * @param data   - This var is used to as a test data (Browser Name).
     * @return This function is return the status of current browser.
     * @throws IOException
     */
    public String OPEN_BROWSER(String object, String data) throws IOException {
        try {
            if (data.equals("Mozilla")) {
                browsername = data;
                System.setProperty("webdriver.Firefox.driver",
                        System.getProperty("user.dir") + "//lib//geckodriver.exe");
                driver = new FirefoxDriver();
                driver.manage().deleteAllCookies();
                result = Constants.KEYWORD_PASS;
                DriverScript.nodes.log(Status.PASS, "Step === Opening browser ===" + data);
            } else if (data.equals("IE")) {
                browsername = data;
                System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "//lib//IEDriverServer.exe");
                DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
                caps.setCapability("javascriptEnabled", true);
                caps.setCapability("nativeEvents", false);
                caps.setCapability("requireWindowFocus", true);
                caps.setCapability("ignoreProtectedModeSettings", true);
                caps.setCapability("initialBrowserUrl", "");
                DriverScript.nodes.log(Status.PASS, "Step === Opening browser ===" + data);
                driver = new InternetExplorerDriver(caps);
                driver.manage().deleteAllCookies();
                result = Constants.KEYWORD_PASS;
            } else if (data.equals("Chrome")) {
                browsername = data;
                String OS = System.getProperty("os.name").toLowerCase();
                if (OS.contains("window")) {
                    System.setProperty("webdriver.chrome.driver",
                            System.getProperty("user.dir") + "//lib//chromedriver.exe");
                } else if (OS.contains("mac")) {
                    System.setProperty("webdriver.chrome.driver",
                            System.getProperty("user.dir") + "//lib//chromedriver");
                }
                driver = new ChromeDriver();
                driver.manage().window().maximize();
                JavascriptExecutor jse = (JavascriptExecutor) driver;
                ngWebDriver = new NgWebDriver(jse).withRootSelector("");
                localStorage = new HashMap<String, String>();
//				webStorage = (WebStorage) new Augmenter().augment(driver);
//				localStorage = webStorage.getLocalStorage();

                ChromeOptions options = new ChromeOptions();
                result = Constants.KEYWORD_PASS;
            } else if (data.equals("Safari")) {
                browsername = data;
                driver = new SafariDriver();
                result = Constants.KEYWORD_PASS;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = Constants.KEYWORD_FAIL;
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
        }
        return result;
    }

    /**
     * This function is used to navigate to a given URL.
     *
     * @param object - This var is used to identify object properties
     * @param data   - This var is used to as a test data (URL).
     * @return This function is return the status of current URL to load.
     * @throws IOException
     */
    public String OPEN_URL(String object, String data) throws IOException {
        try {

//			driver.manage().window().maximize();
            driver.navigate().to(baseUrl + "/" + data);
            result = Constants.KEYWORD_PASS;
            Thread.sleep(2000);
        } catch (Exception e) {

            result = Constants.KEYWORD_FAIL;
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
        }
        return result;
    }

    public String WINDOW_HANDLE(String object, String... data) throws Exception {
        try {
            String oldTab = driver.getWindowHandle();
            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            System.out.println(tabs.size());
            driver.switchTo().window(tabs.get(1));
            String actualTitle = driver.getTitle();
            System.out.println("actual Tittle is " + actualTitle);
            System.out.println("expected tittle is " + data);
            if (actualTitle.equalsIgnoreCase(data.toString())) {
                System.out.println("Title Matched");
            } else {
                System.out.println("Title didn't match");
            }
            driver.switchTo().window(oldTab);
        } catch (Exception e) {
            if (e.getMessage().contains("Timed out waiting for page to load")) {
                result = Constants.KEYWORD_PASS;
            } else {
                e.printStackTrace();
                result = Constants.KEYWORD_FAIL;
            }
        }
        return result;
    }

    public String VERIFY_ELEMENT_EXISTS_ON_PAGE(String object, String data) throws IOException {
        By by = null;
        try {
            by = OBJECT_TYPE_IDENTIFIER(object);
            driver.findElement(by);
            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {
            result = Constants.KEYWORD_FAIL;
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
        }
        return result;
    }

    public String CLOSE_BROWSER(String object, String data) throws IOException {
        try {
            if (TestDataReader.browser.equalsIgnoreCase("Chrome") == true) {
                localStorage.clear();
                driver.quit();
                result = Constants.KEYWORD_PASS;
            } else {
                Calendar cal1 = Calendar.getInstance();
                Calendar cal = Calendar.getInstance();
                cal.getTime();
                long milliSec1 = cal.getTimeInMillis();
                getRefFlag(object, data);
                cal1.getTime();
                long milliSec2 = cal1.getTimeInMillis();
                long timeDifInMilliSec;
                if (milliSec1 <= milliSec2) {
                    timeDifInMilliSec = milliSec2 - milliSec1;
                } else {
                    timeDifInMilliSec = milliSec1 - milliSec2;
                }
                long timeDifSeconds = timeDifInMilliSec / 1000;
                long timeDifMinutes = timeDifInMilliSec / (60 * 1000);
                long timeDifHours = timeDifInMilliSec / (60 * 60 * 1000);
                long timeDifDays = timeDifInMilliSec / (24 * 60 * 60 * 1000);
                driver.quit();
                result = Constants.KEYWORD_PASS;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = Constants.KEYWORD_FAIL;
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
        }
        return result;
    }

    public String WAIT_FOR_SOME_TIME(String object, String data)
            throws NumberFormatException, InterruptedException, IOException {
        try {
            long time = Long.parseLong(data);
            Thread.sleep(time * 1000);

            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
            result = Constants.KEYWORD_FAIL;
        }
        return result;
    }

    /************************
     * APPLICATION SPECIFIC KEYWORDS
     ********************************/
    public String getIntegerValue() {
        try {
            Date today = Calendar.getInstance().getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyMMddhhmmss");
            String val = formatter.format(today);
            result = val;
        } catch (Exception e) {
            result = Constants.KEYWORD_FAIL;
        }
        return result;
    }

    public boolean setCellData(String path, String sheetName, String colName, int rowNum, String data) {
        try {
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);
            int colNum = -1;
            row = sheet.getRow(0);
            for (int i = 0; i < row.getLastCellNum(); i++) {
                // System.out.println(row.getCell(i).getStringCellValue().trim());
                // if(row.getCell(i).getStringCellValue().trim().equals(colName))
                if (row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName))
                    colNum = i;
                // break;
            }
            if (colNum == -1)
                return false;
            sheet.autoSizeColumn(colNum);
            row = sheet.getRow(rowNum - 1);
            if (row == null)
                row = sheet.createRow(rowNum - 1);
            cell = row.getCell(colNum);
            if (cell == null)
                cell = row.createCell(colNum);
            cell.setCellValue(data);
            fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            // fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String getRefFlag(String object, String Data) throws IOException {
        String refFlag = null;
        String refFlagdata = null;
        int columnid = 0;
        try {
            int colindex = 0;
            int newcolindex = 0;
            startIndex = 0;
            for (colindex = startIndex; colindex <= currentTestSuiteXLS
                    .getColumnCount(DriverScript.currentTestCaseName); colindex++) {
                refFlag = currentTestSuiteXLS.getCellData(DriverScript.currentTestCaseName, colindex, 1);
                // if refFlag column found goes to inner condition
                // System.out.println("refflag"+refFlag);
                if (refFlag.equalsIgnoreCase("RefFlag") == true && refFlag.isEmpty() == false) {
                    newcolindex = colindex;
                    colindex = colindex + 1;
                    result = "Y";
                    // break;
                } else {
                    result = "N";
                }
                if (result == "Y") {
                    refFlagdata = currentTestSuiteXLS.getCellData(DriverScript.currentTestCaseName, newcolindex,
                            currentTestDataSetID);
                    if (refFlagdata.equalsIgnoreCase("Y")) {
                        // return tcid for that row
                        String tcids = currentTestSuiteXLS.getCellData(DriverScript.currentTestCaseName,
                                newcolindex + 1, currentTestDataSetID);
                        for (int firstcolindex = startIndex; firstcolindex < newcolindex; firstcolindex++) {
                            String tcdata = currentTestSuiteXLS.getCellData(DriverScript.currentTestCaseName,
                                    firstcolindex, currentTestDataSetID);
                            String cols = currentTestSuiteXLS.getCellData(DriverScript.currentTestCaseName,
                                    firstcolindex, 1);
                            setRefData(DriverScript.refexcelfilename, tcids, cols, currentTestDataSetID, tcdata);
                        }
                    }
                    startIndex = newcolindex + 2;
                }
            }
            fis.close();
            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {
            System.out.println("Error in getRefFlag");
        }
        return refFlag;
    }

    public void setRefData(String path, String ReftcID, String col, int row, String tcdata) throws IOException {
        try {
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);
            String associatetestCaseIDS = ReftcID;
            String[] items = associatetestCaseIDS.split(",");
            List<String> container = Arrays.asList(items);
            for (int index = 0; index < container.size(); index++) {
                sheet = workbook.getSheet(container.get(index).trim());
                System.out.println("Setting data in Sheet >> " + sheet.getSheetName() + " Col >> " + col
                        + " on row no >> " + row + " with data >> " + tcdata);
                String sheetname = sheet.getSheetName();
                setCellData(path, sheetname, col, row, tcdata);
            }
        } catch (Exception e) {
            System.out.println("Error in setRefData");
        }
    }

    public static void killProcess(String serviceName) throws Exception {
        try {
            Runtime.getRuntime().exec(KILL + serviceName);
        } catch (Exception e) {
        }
    }

    public static boolean isProcessRunging(String serviceName) throws Exception {
        try {
            Process p = Runtime.getRuntime().exec(TASKLIST);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                if (line.contains(serviceName)) {
                    killProcess(serviceName);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public String REFRESH_PAGE(String object, String data) throws IOException {
        By by = null;
        try {
            driver.navigate().refresh();
            result = Constants.KEYWORD_PASS;

        } catch (Exception e) {
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
            result = Constants.KEYWORD_PASS;

        }
        return result;
    }

    public String VERIFY_ELEMENT_NOT_PRESENT(String object, String data) throws Exception {
        By by;
        try {
            by = OBJECT_TYPE_IDENTIFIER(object, data);
            Thread.sleep(3000);
            if (driver.findElement(by).isDisplayed() || driver.findElement(by).isEnabled()) {
                result = Constants.KEYWORD_PASS;
            } else {
                result = Constants.KEYWORD_FAIL;
            }
        } catch (Exception e) {
            result = Constants.KEYWORD_FAIL;
        }
        return result;
    }

    public String CLICK(String object, String data) throws IOException {
        By by;
        by = OBJECT_TYPE_IDENTIFIER(object, data);
        try {
            if (data.contains("RandomEmail")) {
                data = randomEmail;
                System.out.println("RandomEmail: " + data);
            } else if (data.contains("RandomName")) {
                data = randomName;
                System.out.println("RandomName: " + data);
            } else if (data.contains("MATCHED_ELEMENT")) {
                data = localStorage.get("matchedElement");
                System.out.println("MATCHED_ELEMENT: " + data);
            }
            if (TestDataReader.browser.equalsIgnoreCase("IE")) {
                String windowHandle = driver.getWindowHandle();
                driver.switchTo().window(windowHandle);
                driver.findElement(by);
                try {
                    driver.findElement(by).click();
                } catch (Exception e) {
                    driver.findElement(by).sendKeys("\n");
                }
                result = Constants.KEYWORD_PASS;
            } else {
                WebElement objElement = driver.findElement(by);
                WAIT_FOR_PAGE_TO_CONTAIN_ELEMENT(object, data);
                Highlight(object, data);
                objElement.click();

                result = Constants.KEYWORD_PASS;
            }
        } catch (Exception e) {
            result = Constants.KEYWORD_FAIL;
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
        }
        return result;
    }

    public String ENTER_DATA(String object, String data) throws IOException {
        By by = null;
        try {
            by = OBJECT_TYPE_IDENTIFIER(object, data);
            if (data.contains("RandomEmail")) {
                data = randomEmail;
                System.out.println("RandomEmail: " + data);
            } else if (data.contains("RandomName")) {
                data = randomName;
                System.out.println("RandomName: " + data);
            } else if (data.contains("NextDate")) {
                data = nextDate;
                System.out.println("NextDate: " + data);
            } else if (data.contains("GMAIL_ID")) {
                data = GMAIL_ID;
                System.out.println("id: " + data);
            } else if (data.contains("RANDOM_NUMBER")) {
                data = randomPhoneNumber;
                System.out.println("RANDOM_NUMBER: " + data);
            } else if (data.contains("RANDOM_PASSWORD")) {
                data = randomPassword;
                System.out.println("RANDOM_PASSWORD: " + data);
            } else if (data.contains("RANDOM_QUOTE")) {
                data = randomquote;
                System.out.println("RANDOM_QUOTE: " + data);
            } else if (data.contains("PIN_NUMBER")) {
                data = PIN;
                System.out.println("PIN_NUMBER: " + data);
            } else if (data.contains("RANDOM_NEW_NUMBER")) {
                data = randomPin;
                System.out.println("RANDOM_NEW_NUMBER: " + data);
            } else if (data.contains("POLICY_NUMBER")) {
                data = localStorage.get("Policy_Number");
                System.out.println("POLICY_NUMBER : " + data);
            } else if (data.contains("CARRIER_NAME")) {
                data = localStorage.get("Carrier_Name");
                System.out.println("CARRIER_NAME : " + data);
            } else if (data.contains("CICERO_DATE")) {
                data = localStorage.get("ciceroDate");
                System.out.println("CICERO_DATE : " + data);
            }


            WAIT_FOR_PAGE_TO_CONTAIN_ELEMENT(object, data);
            WAIT_FOR_ELEMENT_TO_BE_VISIBLE(object, data);
            driver.findElement(by).clear();
            WebElement ele = driver.findElement(by);
            Highlight(object, data);
            ele.sendKeys(data);

            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {
            result = Constants.KEYWORD_FAIL;
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            e.printStackTrace();
            DriverScript.nodes.log(Status.FAIL, e);
        }
        return result;
    }

    public String VERIFY_TEXT_ON_PAGE(String object, String data) throws IOException {
        By by = null;
        try {
            Thread.sleep(3000);
            by = OBJECT_TYPE_IDENTIFIER(object, data);
            if (data.contains("RandomEmail")) {
                data = randomEmail;
            } else if (data.contains("RandomName")) {
                data = randomName;
            }
            System.out.println("xpath---->>>>>>>>>>>>>>" + by);
            String actual = driver.findElement(by).getText().trim();
            System.out.println("---------->>>>" + actual);
            System.out.println("actual1 value is " + actual);
            String expected = data;
            Highlight(object, data);
            if (actual.equalsIgnoreCase(expected)) {

                System.out.println(actual + "++++++++++++" + expected);
                result = Constants.KEYWORD_PASS;
            } else {

                result = Constants.KEYWORD_FAIL;
            }
        } catch (Exception e) {
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
            result = Constants.KEYWORD_FAIL;
        }
        return result;
    }

    public String CLEAR_INPUT_BOX(String object, String data) throws IOException {
        By by = null;
        try {
            by = OBJECT_TYPE_IDENTIFIER(object);
            Highlight(object, data);
            driver.findElement(by).clear();

            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
            result = Constants.KEYWORD_FAIL;
        }
        return result;
    }

    public String UPLOAD_DOC(String object, String data) throws Exception {
        String strPath = "";
        By by = null;
        try {
            by = OBJECT_TYPE_IDENTIFIER(object, data);
            strPath = System.getProperty("user.dir") + "/Document/" + data;
            System.out.println("path:" + strPath);
            driver.findElement(by).sendKeys(strPath);

            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
            result = Constants.KEYWORD_FAIL;
        }
        return result;
    }

    public String WAIT_FOR_ELEMENT_TO_BE_VISIBLE(String object, String data) throws IOException {
        By by = null;
        try {
            by = OBJECT_TYPE_IDENTIFIER(object, data);
            WebDriverWait waiting = new WebDriverWait(driver, waitforelement);
            waiting.until(ExpectedConditions.visibilityOfElementLocated(by));
            Highlight(object, data);
            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            result = Constants.KEYWORD_FAIL;
            DriverScript.nodes.log(Status.FAIL, e);
        }
        return result;
    }

    public String WAIT_FOR_ELEMENT_TO_NOT_BE_VISIBLE(String object, String data) throws IOException {
        By by = null;
        try {
            by = OBJECT_TYPE_IDENTIFIER(object, data);
            if (data.contains("RandomEmail")) {
                data = randomEmail;
            } else if (data.contains("RandomName")) {
                data = randomName;
            }
            WebDriverWait waiting = new WebDriverWait(driver, waitforelement);
            waiting.until(ExpectedConditions.invisibilityOfElementLocated(by));


            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {
            result = Constants.KEYWORD_FAIL;
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
        }
        return result;
    }

    public String WAIT_FOR_PAGE_TO_CONTAIN_ELEMENT(String object, String data) throws IOException {
        By by = null;
        try {
            by = OBJECT_TYPE_IDENTIFIER(object, data);
            Thread.sleep(3000);
            if (data.contains("RandomEmail")) {
                data = randomEmail;
            } else if (data.contains("RandomName")) {
                data = randomName;
            } else if (data.contains("GMAIL_ID")) {
                data = GMAIL_ID;
                System.out.println("**********" + data);
            } else if (data.contains("POLICY_NUMBER")) {
                data = localStorage.get("Policy_Number");
                System.out.println("POLICY_NUMBER : " + data);
            } else if (data.contains("CARRIER_NAME")) {
                data = localStorage.get("Carrier_Name");
                System.out.println("CARRIER_NAME : " + data);
            }
            WebDriverWait waiting = new WebDriverWait(driver, waitforelement);
            waiting.until(ExpectedConditions.presenceOfElementLocated(by));
            Highlight(object, data);

            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {
            e.printStackTrace();
            result = Constants.KEYWORD_FAIL;
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
        }
        return result;
    }

    public String GENERATE_EMAIL(String Object, String data) throws IOException {
        try {
            int count = 5;
            String data1 = data.split("@")[0];
            String data2 = data.split("@")[1];
            // String data3 = "lokeshsingla874";
            String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvxyz0123456789";
            StringBuilder builder = new StringBuilder();
            while (count-- != 0) {
                int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
                builder.append(ALPHA_NUMERIC_STRING.charAt(character));
            }
            randomEmail = builder.toString();
            String dateTime = "+" + DateUtility.getCurrentTime();
            randomEmail = data1 + dateTime + '@' + data2;
            System.out.println("email generated: " + randomEmail);

            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
            result = Constants.KEYWORD_FAIL;
        }
        return result;
    }

    public String VERIFY_NUMBER_OF_ELEMENTS_ON_PAGE(String object, String data) throws IOException {

        By by = null;
        try {
            String timeout = "300";
            by = OBJECT_TYPE_IDENTIFIER(object, data);
            int assertCount = Integer.parseInt(data);

            List<WebElement> objElement = (new WebDriverWait(driver, Long.parseLong(timeout)))
                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
            if (objElement.size() > assertCount) {

                result = Constants.KEYWORD_PASS;
            } else {
                result = Constants.KEYWORD_FAIL;
            }

        } catch (Exception e) {


            result = Constants.KEYWORD_FAIL;
            DriverScript.nodes.log(Status.FAIL, e);


        }
        return result;
    }

    public String WAIT_FOR_ELEMENT_TO_BE_CLICKABLE(String object, String data) throws IOException {
        data = "60";
        By by = null;
        try {
            by = OBJECT_TYPE_IDENTIFIER(object, data);
            Thread.sleep(3000);
            WebDriverWait waiting = new WebDriverWait(driver, Long.parseLong(data));
            waiting.until(ExpectedConditions.elementToBeClickable(OBJECT_TYPE_IDENTIFIER(object)));
            Highlight(object, data);

            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {
            result = Constants.KEYWORD_FAIL;
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
        }
        return result;
    }

    public String HOVER_OVER(String object, String data) throws IOException {
        By by = null;
        String timeout = "30";
        try {
            by = OBJECT_TYPE_IDENTIFIER(object, data);
            if (data.contains("RandomEmail")) {
                data = randomEmail;
                System.out.println("**********" + data);
            } else if (data.contains("RandomName")) {
                data = randomName;
                System.out.println("**********" + data);
            }
            WebDriverWait waiting = new WebDriverWait(driver, Long.parseLong(timeout));
            waiting.until(ExpectedConditions.presenceOfElementLocated(by));
            Actions action = new Actions(driver);
            action.moveToElement(driver.findElement(by)).perform();

            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {
            e.printStackTrace();
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
            result = Constants.KEYWORD_FAIL;
        }
        return result;
    }

    public String DOUBLE_CLICK(String object, String data) throws IOException {
        By by = null;
        try {
            by = OBJECT_TYPE_IDENTIFIER(object);
            WebElement objEle = driver.findElement(by);
            Highlight(object, data);
            Actions builder = new Actions(driver);
            builder.doubleClick(objEle).perform();
            Thread.sleep(3000);

            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {
            result = Constants.KEYWORD_FAIL + " Not able to click";
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
        }
        return result;
    }

    public String SCROLL_TO_BOTTOM(String object, String data) throws IOException {
        By by = null;
        String timeout = "30";
        try {
            by = OBJECT_TYPE_IDENTIFIER(object, data);
            if (data.contains("RandomName")) {
                data = randomName;
            }
            // Store the current window handle
            String winHandleBefore = driver.getWindowHandle();
            // Perform the click operation that opens new window
            // Switch to new window opened
            for (String winHandle : driver.getWindowHandles()) {
                if (!winHandle.equalsIgnoreCase(winHandleBefore)) {
                    driver.switchTo().window(winHandle);
                }
            }
            by = OBJECT_TYPE_IDENTIFIER(object, data);
            for (int i = 0; i < 200; i++) {
                WebElement obj = driver.findElement(by);
                // Thread.sleep(3000);
                JavascriptExecutor jse = (JavascriptExecutor) driver;
                jse.executeScript("arguments[0].scrollIntoView();", obj);
                WebDriverWait waiting = new WebDriverWait(driver, Long.parseLong(timeout));
                waiting.until(ExpectedConditions.presenceOfElementLocated(by));
            }

            return Constants.KEYWORD_PASS;
        } catch (Exception e) {
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
            return Constants.KEYWORD_FAIL;
        }
    }

    public String GENERATE_NAME(String Object, String data) throws IOException {
        try {
            int leftLimit = 97; // letter 'a'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 10;
            Random random = new Random();
            String generatedString = random.ints(leftLimit, rightLimit + 1).limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
            randomName = data + generatedString;

            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {
            System.out.println(e);

            DriverScript.nodes.log(Status.FAIL, e);
            result = Constants.KEYWORD_FAIL;
        }
        return result;
    }

    public String SCROLL_TO_ELEMENT(String object, String data) throws IOException {
        By by = null;
        String timeout = "30";
        try {
            by = OBJECT_TYPE_IDENTIFIER(object, data);
            if (data.contains("RandomName")) {
                data = randomName;
            }
            WebElement obj = driver.findElement(by);
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("arguments[0].scrollIntoView();", obj);
            WebDriverWait waiting = new WebDriverWait(driver, Long.parseLong(timeout));
            waiting.until(ExpectedConditions.presenceOfElementLocated(by));

            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
            result = Constants.KEYWORD_FAIL;
        }
        return result;
    }

    public String GMAIL_VERIFY_FOR_ADMIN(String object, String data) throws IOException {
        By by = null;
        try {
            Thread.sleep(3000);
            driver.findElement(By.xpath("//input[@aria-label='Search mail']")).sendKeys("PHP welcome");
            System.out.println("Enter text in search Box");
            driver.findElement(By.xpath("//tbody//span[@name='PHP Welcome']/../../../../../tr[1]")).click();
            System.out.println("Click on First Email");
            Thread.sleep(3000);
            String value = driver.findElement(By.linkText("here")).getAttribute("href");
            System.out.println("get url and save in value");
            WebDriverWait wait = new WebDriverWait(driver, 40);
            wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath("//div[@class='ar6 T-I-J3 J-J5-Ji']/../../..")));
            driver.findElement(By.xpath("//div[@class='ar6 T-I-J3 J-J5-Ji']/../../..")).click();
            System.out.println("Click on back arrow");
            Actions action = new Actions(driver);
            action.moveToElement(driver.findElement(By.xpath("//div[@class='asf T-I-J3 J-J5-Ji']"))).perform();
            System.out.println("hover success");
            WebDriverWait wait1 = new WebDriverWait(driver, 40);
            wait1.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//div//span[@class='T-Jo J-J5-Ji']/.")));
            driver.findElement(By.xpath("//div//span[@class='T-Jo J-J5-Ji']/.")).click();
            System.out.println("Click on all select");
            // Thread.sleep(100000);
            WebDriverWait wait2 = new WebDriverWait(driver, 40);
            wait2.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='ar9 T-I-J3 J-J5-Ji']/..")));
            driver.findElement(By.xpath("//div[@class='ar9 T-I-J3 J-J5-Ji']/..")).click();
            System.out.println("Click on delete");
            if ((ExpectedConditions.alertIsPresent()).apply(driver) != null) {
                Alert alert = driver.switchTo().alert();
                alert.accept();
                System.out.println("alert was present and accepted");
                driver.navigate().to(value);

                return Constants.KEYWORD_PASS;
            } else {
                System.out.println("value is " + value);
                driver.navigate().to(value);

                return Constants.KEYWORD_PASS;
            }
        } catch (Exception e) {
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
            return Constants.KEYWORD_FAIL;
        }
    }

    public String FORGET_PASSWORD(String object, String data) throws IOException {
        By by = null;
        try {
            Thread.sleep(60000);
            driver.findElement(By.xpath("//input[@aria-label='Search mail']")).sendKeys("7t.integration.mail");
            System.out.println("Enter text in search Box");
            driver.findElement(By.xpath("//tbody//span[@name='7t.integration.mail']/../../../../../tr[1]")).click();
            System.out.println("Click on First Email");
            String value = driver.findElement(By.linkText("clicking here")).getAttribute("href");
            System.out.println("get url and save in value");
            System.out.println("get url and save in value");
            Thread.sleep(3000);
            WebDriverWait wait = new WebDriverWait(driver, 40);
            wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath("//div[@class='ar6 T-I-J3 J-J5-Ji']/../../..")));
            driver.findElement(By.xpath("//div[@class='ar6 T-I-J3 J-J5-Ji']/../../..")).click();
            System.out.println("Click on back arrow");
            Actions action = new Actions(driver);
            action.moveToElement(driver.findElement(By.xpath("//div[@class=\"asf T-I-J3 J-J5-Ji\"]"))).perform();
            System.out.println("hover success");
            WebDriverWait wait1 = new WebDriverWait(driver, 40);
            wait1.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//div//span[@class='T-Jo J-J5-Ji']/.")));
            JavascriptExecutor jse1 = (JavascriptExecutor) driver;
            jse1.executeScript("arguments[0].click();",
                    driver.findElement(By.xpath("//div//span[@class='T-Jo J-J5-Ji']/.")));
            System.out.println("Click on all select");
            WebDriverWait wait2 = new WebDriverWait(driver, 40);
            wait2.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='ar9 T-I-J3 J-J5-Ji']/..")));
            driver.findElement(By.xpath("//div[@class='ar9 T-I-J3 J-J5-Ji']/..")).click();
            System.out.println("Click on delete");
            Thread.sleep(3000);
            if ((ExpectedConditions.alertIsPresent()).apply(driver) != null) {
                Alert alert = driver.switchTo().alert();
                alert.accept();
                System.out.println("alert was present and accepted");
                driver.navigate().to(value);

                return Constants.KEYWORD_PASS;
            } else {
                System.out.println("value is " + value);
                driver.navigate().to(value);

                return Constants.KEYWORD_PASS;
            }
        } catch (Exception e) {
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
            return Constants.KEYWORD_FAIL;
        }
    }

    public String NAVIGATE(String object, String data) throws IOException {
        try {
//			driver.manage().window().maximize();
            driver.navigate().to(data);

            Thread.sleep(2000);
            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            result = Constants.KEYWORD_FAIL;
            DriverScript.nodes.log(Status.FAIL, e);
        }
        return result;
    }

    public String GMAIL_VERIFY(String object, String data) throws IOException {


        try {
            String value;
            Thread.sleep(3000);
            driver.findElement(By.xpath("//input[@aria-label='Search mail']")).sendKeys("PHP welcome");
            System.out.println("Enter text in search Box");
            driver.findElement(By.xpath("//tbody//span[@name='PHP Welcome']/../../../../../tr[1]")).click();
            System.out.println("Click on First Email");
            Thread.sleep(5000);

            if (driver.findElements(By.linkText("Pay Now")).isEmpty() != true) {

                WebDriverWait wait5 = new WebDriverWait(driver, 40);
                wait5.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Hi')]")));

                value = driver.findElement(By.linkText("Pay Now")).getAttribute("href");
                System.out.println("In value " + value);
                System.out.println("get url and save in value");
                Thread.sleep(5000);


            } else if (driver.findElements(By.linkText("Activate Account")).isEmpty() != true) {

                WebDriverWait wait5 = new WebDriverWait(driver, 40);
                wait5.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Hi')]")));

                value = driver.findElement(By.linkText("Activate Account")).getAttribute("href");
                System.out.println("In value " + value);
                System.out.println("get url and save in value");
                Thread.sleep(5000);


            } else {

                WebDriverWait wait6 = new WebDriverWait(driver, 40);
                wait6.until(ExpectedConditions
                        .visibilityOfElementLocated(By.xpath("//p[contains(text(),'Hi')]//following::p[2]/b")));
                GMAIL_ID = driver.findElement(By.xpath("//p[contains(text(),'Hi')]//following::p[2]/b")).getText();
                System.out.println("your id is " + GMAIL_ID);
                value = driver.findElement(By.linkText("here")).getAttribute("href");
                System.out.println("In value " + value);
                System.out.println("get url and save in value");
                Thread.sleep(5000);
            }

            WebDriverWait wait = new WebDriverWait(driver, 40);
            wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath("//div[@class='ar6 T-I-J3 J-J5-Ji']/../../..")));
            driver.findElement(By.xpath("//div[@class='ar6 T-I-J3 J-J5-Ji']/../../..")).click();
            System.out.println("Click on back arrow");
            Actions action = new Actions(driver);
            action.moveToElement(driver.findElement(By.xpath("//div[@class=\"asf T-I-J3 J-J5-Ji\"]"))).perform();
            System.out.println("hover success");
            WebDriverWait wait1 = new WebDriverWait(driver, 40);
            wait1.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//div//span[@class='T-Jo J-J5-Ji']/.")));
            JavascriptExecutor jse1 = (JavascriptExecutor) driver;
            jse1.executeScript("arguments[0].click();",
                    driver.findElement(By.xpath("//div//span[@class='T-Jo J-J5-Ji']/.")));
            System.out.println("Click on all select");
            WebDriverWait wait2 = new WebDriverWait(driver, 40);
            wait2.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='ar9 T-I-J3 J-J5-Ji']/..")));
            driver.findElement(By.xpath("//div[@class='ar9 T-I-J3 J-J5-Ji']/..")).click();
            System.out.println("Click on delete");
            Thread.sleep(3000);
            if ((ExpectedConditions.alertIsPresent()).apply(driver) != null) {
                Alert alert = driver.switchTo().alert();
                alert.accept();
                System.out.println("alert was present and accepted");
                driver.navigate().to(value);

                return Constants.KEYWORD_PASS;
            } else {
                System.out.println("value is " + value);
                driver.navigate().to(value);

                return Constants.KEYWORD_PASS;
            }
        } catch (Exception e) {
            e.printStackTrace();
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
            return Constants.KEYWORD_FAIL;
        }
    }

    public String SWITCH_TO_WINDOW(String object, String data) throws Exception {
        object = "";
        try {
            String currentWindow = driver.getWindowHandle(); // will keep current window to switch back
            for (String winHandle : driver.getWindowHandles()) {
                if (driver.getTitle().equals(data)) {
                    // This is the one you're looking for
                    driver.switchTo().window(currentWindow);
                    break;
                } else {
                    driver.switchTo().window(currentWindow);
                }
            }

            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
            result = Constants.KEYWORD_FAIL;
        }
        return result;
    }

    public String QUESTIONNAIRE_FORM(String object, String data) throws IOException {
        try {
            if (driver.findElements(By.xpath("//mat-dialog-container[@id='agent-questionnaire']")).isEmpty() != true) {
                WebDriverWait waiting = new WebDriverWait(driver, waitforelement);
                waiting.until(ExpectedConditions
                        .visibilityOfElementLocated(By.xpath("//mat-dialog-container[@id='agent-questionnaire']")));
                System.out.println("Question form visible");
                WebDriverWait wait = new WebDriverWait(driver, 40);
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[contains(text(),' White ')]/..//div[@class='mat-radio-container']")));
                WebElement white = driver.findElement(
                        By.xpath("//div[contains(text(),' White ')]/..//div[@class='mat-radio-container']"));
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", white);
                System.out.println("Click on white radio button");
                WebDriverWait wait1 = new WebDriverWait(driver, 40);
                wait1.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[contains(text(),' Some high school, no diploma ')]")));
                WebElement education_level = driver
                        .findElement(By.xpath("//div[contains(text(),' Some high school, no diploma ')]"));
                JavascriptExecutor executor1 = (JavascriptExecutor) driver;
                executor1.executeScript("arguments[0].click();", education_level);
                System.out.println("Click on Associate radio button");
                WebDriverWait wait2 = new WebDriverWait(driver, 40);
                wait2.until(ExpectedConditions
                        .visibilityOfElementLocated(By.xpath("//div//div[contains(text(),'Single')]")));
                WebElement single = driver.findElement(By.xpath("//div//div[contains(text(),'Single')]"));
                JavascriptExecutor executor2 = (JavascriptExecutor) driver;
                executor2.executeScript("arguments[0].click();", single);
                System.out.println("Click on Single radio button");
                WebDriverWait wait3 = new WebDriverWait(driver, 40);
                wait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//span[contains(text(),'Career History is this your first work experience?')]/..//div[contains(text(),'No')]/..//div[@class='mat-radio-container']")));
                WebElement carrier = driver.findElement(By.xpath(
                        "//div//span[contains(text(),'Career History is this your first work experience?')]/..//div[contains(text(),'No')]/..//div[@class='mat-radio-container']"));
                JavascriptExecutor executor3 = (JavascriptExecutor) driver;
                executor3.executeScript("arguments[0].click();", carrier);
                System.out.println("Click on career yes radio button");
                Thread.sleep(5000);
                WebDriverWait wait4 = new WebDriverWait(driver, 40);
                wait4.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//span[contains(text(),'Have you been in the insurance industry prior to PHP?')]/..//div[contains(text(),'Yes')]/..")));
                WebElement insurance = driver.findElement(By.xpath(
                        "//span[contains(text(),'Have you been in the insurance industry prior to PHP?')]/..//div[contains(text(),'Yes')]/.."));
                JavascriptExecutor executor4 = (JavascriptExecutor) driver;
                executor4.executeScript("arguments[0].click();", insurance);
                System.out.println("Click on insurance yes radio button");
                Thread.sleep(7000);
                WebDriverWait wait5 = new WebDriverWait(driver, 40);
                wait5.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//span[contains(text(),'How did you hear about PHP?')]/..//div[contains(text(),' Friend ')]/..//div[@class='mat-radio-container']")));
                WebElement about_php = driver.findElement(By.xpath(
                        "//span[contains(text(),'How did you hear about PHP?')]/..//div[contains(text(),' Friend ')]/..//div[@class='mat-radio-container']"));
                JavascriptExecutor executor5 = (JavascriptExecutor) driver;
                executor5.executeScript("arguments[0].click();", about_php);
                System.out.println("Click on PHPâ€™s Web-Site radio button");
                WebDriverWait wait6 = new WebDriverWait(driver, 40);
                wait6.until(ExpectedConditions
                        .visibilityOfElementLocated(By.xpath("//button[contains(text(),'SUBMIT QUESTIONNAIRE')]")));
                driver.findElement(By.xpath("//button[contains(text(),'SUBMIT QUESTIONNAIRE')]")).click();
                System.out.println("Click on submit button");
                result = Constants.KEYWORD_PASS;
            } else {
                result = Constants.KEYWORD_PASS;
            }
        } catch (Exception e) {
            System.out.println("fail");
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
            result = Constants.KEYWORD_FAIL;
        }
        return result;
    }

    public String GET_PIN_VALUE(String object, String data) throws Exception {
        try {
            String text = driver.findElement(By.xpath("//span[contains(text(),'Use this pin')]")).getText();
            text = text.replace("Use this pin to complete signup process: ", "");

            String[] pinValue = text.toString().split("");

            for (int i = 0; i < pinValue.length; i++) {

                driver.findElement(
                        By.xpath("//div[contains(@class,'button-div')]/..//div//input[@type='text'][" + (i + 1) + "]"))
                        .sendKeys(pinValue[i].trim().toString());
                result = Constants.KEYWORD_PASS;
            }
        } catch (Exception e) {
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
            result = Constants.KEYWORD_PASS;
        }
        return result;
    }

    public String GENERATE_PHONE_NUMBER(String Object, String data) throws IOException {
        try {
            String dateTime = DateUtility.getCurrentTime() + "1";
            randomPhoneNumber = dateTime;

            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {
            System.out.println(e);
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
            result = Constants.KEYWORD_FAIL;
        }
        return result;
    }

    public String FORCE_CLICK(String object, String data) throws IOException {
        By by;
        by = OBJECT_TYPE_IDENTIFIER(object, data);
        try {
            if (data.contains("RandomEmail")) {
                data = randomEmail;
                System.out.println("**********" + data);
            } else if (data.contains("RandomName")) {
                data = randomName;
                System.out.println("**********" + data);
            }
            if (TestDataReader.browser.equalsIgnoreCase("IE")) {
                String windowHandle = driver.getWindowHandle();
                driver.switchTo().window(windowHandle);
                driver.findElement(by);
                try {
                    driver.findElement(by).click();
                } catch (Exception e) {
                    driver.findElement(by).sendKeys("\n");
                }
                result = Constants.KEYWORD_PASS;
            } else {
                WebElement objElement = driver.findElement(by);
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", objElement);

                result = Constants.KEYWORD_PASS;
            }
        } catch (Exception e) {
            result = Constants.KEYWORD_FAIL + " Not able to click" + e;
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
        }
        return result;
    }

    public String GENERATE_PASSWORD(String Object, String data) throws IOException {
        try {
            int leftLimit = 97; // letter 'a'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 10;
            Random random = new Random();
            String generatedString = random.ints(leftLimit, rightLimit + 1).limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
            String dateTime = data + DateUtility.getCurrentTime() + "@" + generatedString;
            randomPassword = dateTime;
            String Time = DateUtility.getCurrentTimes();
            randomPin = Time;

            return Constants.KEYWORD_PASS;
        } catch (Exception e) {
            System.out.println(e);
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
            return Constants.KEYWORD_FAIL;
        }
    }

    public String SELECT_RANDOM_QUOTE(String Object, String data) throws IOException {
        try {
            Random random = new Random();
            int randomInt = random.nextInt(2);
            ArrayList<String> quote = new ArrayList<String>();
            quote.add("Ready. Set. Go! Your dream of success starts today.");
            quote.add("YOUR FUTURE IS NOW.");
            randomquote = quote.get(randomInt);

            return Constants.KEYWORD_PASS;
        } catch (Exception e) {
            System.out.println(e);
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
            return Constants.KEYWORD_FAIL;
        }
    }

    public void Highlight(String object, String data) throws IOException {
        By by;
        by = OBJECT_TYPE_IDENTIFIER(object, data);
        WebElement objElement = driver.findElement(by);
        ReportUtility.highlightText(objElement, driver);
    }

    public String GET_PIN_NUMBER(String object, String data) throws Exception {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            Object text = js.executeScript("return(document.querySelector('.plaintext').value)");
            System.out.println("OBJECT TEXT=" + text.toString());
            PIN = text.toString();

            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
            result = Constants.KEYWORD_PASS;
        }
        return result;
    }

    public String DATE_PICKER(String object, String data) throws Exception {
        By by = null;
        try {
            if (data.equalsIgnoreCase("Today")) {
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("d/MMM/yyyy");
                String strDate = formatter.format(date).toString().toUpperCase();
                System.out.println("date is " + strDate);
                data = strDate;
            } else if (data.equalsIgnoreCase("Tomorrow")) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, 1);
                Date date = new Date();
                date = calendar.getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("d/MMM/yyyy");
                String Tomorrow = formatter.format(date).toString().toUpperCase();
                System.out.println(Tomorrow);
                data = Tomorrow;
            } else if (data.equalsIgnoreCase("Dayaftertomorrow")) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, 15);
                Date date = new Date();
                date = calendar.getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("d/MMM/yyyy");
                String Dayaftertomorrow = formatter.format(date).toString().toUpperCase();
                System.out.println(Dayaftertomorrow);
                data = Dayaftertomorrow;
            }
            System.out.println("date is " + data);
            String[] dateParts = data.split("/");
            String day = dateParts[0];
            System.out.println("date is " + dateParts[0]);
            String month = dateParts[1];
            System.out.println("date is " + dateParts[1]);
            String year = dateParts[2];
            System.out.println("date is " + dateParts[2]);
            WebElement pickdate = driver.findElement(By.xpath(
                    "//button[contains(@class,'mat-focus-indicator mat-calendar-period-button mat-button mat-button-base')]"));
            pickdate.click();
            WebElement YEAR = driver.findElement(By.xpath(
                    "//div[contains(@class,'mat-calendar-body-cell-content') and contains(text(),'" + year + "')]"));
            YEAR.click();
            WebElement MONTH = driver.findElement(By.xpath(
                    "//div[contains(@class,'mat-calendar-body-cell-content') and contains(text(),'" + month + "')]"));
            MONTH.click();
            WebElement DATE = driver.findElement(By.xpath(
                    "//div[contains(@class,'mat-calendar-body-cell-content') and contains(text(),'" + day + "')]"));
            DATE.click();

            return Constants.KEYWORD_PASS;
        } catch (Exception e) {
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            e.printStackTrace();
            DriverScript.nodes.log(Status.FAIL, e);
            return Constants.KEYWORD_FAIL;
        }
    }

    public String CLICK_AND_IGNORE_STALEREFERENCE(String object, String data) throws IOException {
        By by;
        by = OBJECT_TYPE_IDENTIFIER(object, data);
        try {
            if (data.contains("RandomEmail")) {
                data = randomEmail;
                System.out.println("**********" + data);
            } else if (data.contains("RandomName")) {
                data = randomName;
                System.out.println("**********" + data);
            }
            if (TestDataReader.browser.equalsIgnoreCase("IE")) {
                String windowHandle = driver.getWindowHandle();
                driver.switchTo().window(windowHandle);
                driver.findElement(by);
                try {
                    driver.findElement(by).click();
                } catch (Exception e) {
                    driver.findElement(by).sendKeys("\n");
                }
                result = Constants.KEYWORD_PASS;
            } else {
                for (int i = 0; i <= 15; i++) {
                    try {
                        WebElement objElement = driver.findElement(by);
                        ReportUtility.highlightText(objElement, driver);
                        objElement.click();
                        break;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }

                result = Constants.KEYWORD_PASS;
            }
        } catch (Exception e) {
            result = Constants.KEYWORD_FAIL;
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
        }
        return result;
    }

    public String SELECT_CURRENT_TIME(String object, String data) throws Exception {
        try {
            Calendar cal = Calendar.getInstance();
            DateFormat dateFormat = new SimpleDateFormat("HH:mm aa");
            System.out.println("time is " + dateFormat);
            SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm");
            // System.out.println( sdf2.format(cal.getTime()) );
            cal.add(Calendar.MINUTE, 2);
            String time = sdf2.format(cal.getTime());
            System.out.println(time);
            String[] timeParts = time.split(":");
            String Hour = timeParts[0];
            System.out.println("hour is " + Hour);
            String Minute = timeParts[1];
            System.out.println("Minute is " + Minute);
            WebElement hour = driver.findElement(By.xpath("//input[@placeholder='HH']"));
            hour.clear();
            System.out.println("clear text pass ");
            hour.sendKeys(Hour);
            System.out.println("sendkeys pass ");
            WebElement minute = driver.findElement(By.xpath("//input[@placeholder='MM']"));
            minute.clear();
            System.out.println("clera text pass ");
            minute.sendKeys(Minute);
            System.out.println("sendkeys pass ");

            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {
            System.out.println(e);
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
            result = Constants.KEYWORD_FAIL;
        }
        return result;
    }

    public String WAIT_FOR_PAGE_TO_CONTAIN_NEWS(String object, String data) throws IOException {
        By by = null;
        String timeout = "5";
        try {
            by = OBJECT_TYPE_IDENTIFIER(object, data);
            Thread.sleep(3000);
            if (data.contains("RandomEmail")) {
                data = randomEmail;
            } else if (data.contains("RandomName")) {
                data = randomName;
            }
            for (int i = 0; i <= 15; i++) {
                try {
                    driver.navigate().refresh();
                    WebDriverWait waiting = new WebDriverWait(driver, Long.parseLong(timeout));
                    waiting.until(ExpectedConditions.presenceOfElementLocated(by));
                    break;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            WebDriverWait waiting = new WebDriverWait(driver, Long.parseLong(timeout));
            waiting.until(ExpectedConditions.presenceOfElementLocated(by));

            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {
            result = Constants.KEYWORD_FAIL;
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
        }
        return result;
    }

    public String SET_PIN_VALUE_IN_W9_FORM(String object, String data) throws Exception {
        try {
            if (data.contains("123456")) {
                PIN = data;
            }
            System.out.println("pin vlue is " + PIN);
            String[] pinValue = PIN.split("");
            System.out.println(pinValue);
            for (int i = 0; i < pinValue.length; i++) {
                System.out.println("lenth is " + pinValue.length);
                driver.findElement(By.xpath("//p[@class='otp-desc']/..//div//input[@type='text'][" + (i + 1) + "]"))
                        .sendKeys(pinValue[i].trim().toString());
            }

            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {
            result = Constants.KEYWORD_FAIL;
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
        }
        return result;
    }

    public String CUSTOM_WAIT_FOR_MANUAL_INTERVENTION(String object, String data) throws Exception {
        try {
            String[] timeinfo = data.split(":");
            String time = timeinfo[0];
            String message = timeinfo[1];
            int TIME_VISIBLE = Integer.parseInt(time);
            JOptionPane pane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
            JDialog dialog = pane.createDialog(null, "Warning");
            dialog.setModal(false);
            dialog.setVisible(true);
            dialog.setAlwaysOnTop(true);
            Thread.sleep(TIME_VISIBLE * 10);
            new Timer(TIME_VISIBLE, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                    ;
                }
            }).start();

            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {
            result = Constants.KEYWORD_FAIL;
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
        }
        return result;
    }

    public String WAIT_FOR_MANUAL_INTERVENTION_TO_RUN_SQL(String object, String data) throws Exception {

        try {
            int TIME_VISIBLE = 4000;
            JOptionPane pane = new JOptionPane(data, JOptionPane.INFORMATION_MESSAGE);
            JDialog dialog = pane.createDialog(null, "Warning");
            dialog.setModal(false);
            dialog.setVisible(true);
            Thread.sleep(TIME_VISIBLE * 10);
            new Timer(TIME_VISIBLE, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                    ;
                }
            }).start();

            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {
            result = Constants.KEYWORD_FAIL;
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
        }
        return result;
    }

    public String CLICK_ON_SUGGEST_ADDRESS(String object, String data) throws IOException {
        By by;
        by = OBJECT_TYPE_IDENTIFIER(object, data);
        try {
            WebElement objElement = driver.findElement(by);
            Highlight(object, data);
            objElement.sendKeys(data);
            Thread.sleep(3000);
            objElement.sendKeys(Keys.ARROW_DOWN, Keys.ENTER);

            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {
            result = Constants.KEYWORD_FAIL;
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
        }
        return result;
    }

    public String ENTER_DATA_AND_SEND(String object, String data) throws IOException {
        By by;
        by = OBJECT_TYPE_IDENTIFIER(object, data);
        try {
            if (data.contains("RandomEmail")) {
                data = randomEmail;
            } else if (data.contains("RandomName")) {
                data = randomName;
            } else if (data.contains("GMAIL_ID")) {
                data = GMAIL_ID;
                System.out.println("**********" + data);
            }
            WebElement objElement = driver.findElement(by);
            WAIT_FOR_PAGE_TO_CONTAIN_ELEMENT(object, data);
            Highlight(object, data);
            objElement.sendKeys(data);
            Thread.sleep(3000);
            objElement.sendKeys(Keys.ENTER);
            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {
            result = Constants.KEYWORD_FAIL;
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
        }
        return result;
    }

    public String COMPARE_DASHBOARD(String object, String data) throws IOException {

        try {
            Thread.sleep(100);
//			Click on Agency
            driver.findElement(By.xpath("//span[contains(text(),'agency')]")).click();
//			Then click on the Agents tab
            System.out.println("Data from the site: - ");
            By byAgents = By.xpath("//a[contains(text(),'Agents')]");
            WebDriverWait waiting = new WebDriverWait(driver, waitforelement);
            waiting.until(ExpectedConditions.visibilityOfElementLocated(byAgents));
            WebElement elAgents = driver.findElement(byAgents);
            ReportUtility.highlightText(elAgents, driver);
            elAgents.click();

            By agentNum = By.xpath("//div[@class='sub-component-wrapper']/div[@class='title']");
            waiting.until(ExpectedConditions.visibilityOfElementLocated(agentNum));

            Thread.sleep(2000);
            WebElement eleAgentNum = driver.findElement(agentNum);
            ReportUtility.highlightText(eleAgentNum, driver);
            String agentNumbers = driver.findElement(agentNum).getText();
//			System.out.println("agentNumbers Raw: "+agentNumbers);
            int agentNumbersInt = 0;
            agentNumbers = agentNumbers.replace("Agents", "");
            if (agentNumbers.replaceAll("\\s", "").equals("")) {
                agentNumbersInt = 0;
            } else {
                agentNumbersInt = Integer.parseInt(agentNumbers.replaceAll("\\s", ""));
            }

            System.out.println("agentNumbers: " + agentNumbersInt);

//			Click on New Business
            driver.findElement(By.xpath("//span[contains(text(),'new business')]")).click();
            By byPersonal = By.xpath("//a[contains(text(),'Personal Policies')]");
            waiting.until(ExpectedConditions.visibilityOfElementLocated(byPersonal));
            WebElement personalPolicies = driver.findElement(byPersonal);
            ReportUtility.highlightText(personalPolicies, driver);
//			Then click on the Personal Policies tab
            personalPolicies.click();

            By appNum = By.xpath("//div[@id='title-color']/span");
            waiting.until(ExpectedConditions.visibilityOfElementLocated(appNum));
            WebElement eleAppNum = driver.findElement(appNum);
            ReportUtility.highlightText(eleAppNum, driver);

            Thread.sleep(1000);
            String applicationNumbers = driver.findElement(appNum).getText();
//			System.out.println("applicationNumbers raw: "+applicationNumbers);
            int applicationNumbersInt = 0;
            if (applicationNumbers.replaceAll("\\s", "").equals("")) {
                applicationNumbersInt = 0;
            } else {
                applicationNumbersInt = Integer.parseInt(applicationNumbers.replaceAll("\\s", ""));
            }
//			System.out.println("applicationNumbers: "+applicationNumbers);
            int all_rows_apps = driver.findElements(By.xpath("/html/body/php-root/div/php-client-shell/div[1]/php-new-business-container/div/php-policies-info-grid/div/div[2]/cdk-virtual-scroll-viewport/div[1]/table/tbody/tr")).size();
            System.out.println(" size of application table " + all_rows_apps);

            int pointsApp = 0;
            WebElement teEl1 = null;
            for (int i = 1; i <= all_rows_apps; i++) {
                teEl1 = driver.findElement(By.xpath("/html/body/php-root/div/php-client-shell/div[1]/php-new-business-container/div/php-policies-info-grid/div/div[2]/cdk-virtual-scroll-viewport/div[1]/table/tbody/tr[" + i + "]/td[8]/div"));
                String textEl1 = teEl1.getText();
//				System.out.println("textEl1:"+textEl1);
                textEl1 = textEl1.replaceAll(",", "");
                pointsApp = pointsApp + Integer.parseInt(textEl1);
            }
            System.out.println("Total Points: " + pointsApp);

            System.out.println("Data from Dashboard: ");
            By home = By.xpath("//span[contains(text(),'home')]");
            WebElement elehome = driver.findElement(home);
            ReportUtility.highlightText(elehome, driver);
            driver.findElement(home).click();

            By dash = By.xpath("//a[contains(text(),'dashboard')]");
            WebElement eleDash = driver.findElement(dash);
            ReportUtility.highlightText(eleDash, driver);
            driver.findElement(dash).click();

            By recruit = By.xpath("//php-score-card[1]//div[1]//div[1]//h1[1]");
            waiting.until(ExpectedConditions.visibilityOfElementLocated(recruit));
            WebElement elerecruit = driver.findElement(recruit);
            ReportUtility.highlightText(elerecruit, driver);
            String recruits = driver.findElement(recruit).getText();
            int recruitsInt = Integer.parseInt(recruits);
            System.out.println("recruits: " + recruitsInt);

            By subApp = By.xpath("//php-score-card[2]//div[1]//div[1]//h1[1]");
            waiting.until(ExpectedConditions.visibilityOfElementLocated(subApp));
            WebElement elesubApp = driver.findElement(subApp);
            ReportUtility.highlightText(elesubApp, driver);
            String subApps = driver.findElement(subApp).getText();
            int subAppsInt = Integer.parseInt(subApps);
            System.out.println("subApps: " + subAppsInt);

            By subPoint = By.xpath("//php-score-card[3]//div[1]//div[1]//h1[1]");
            waiting.until(ExpectedConditions.visibilityOfElementLocated(subPoint));
            WebElement elesubPoint = driver.findElement(subPoint);
            ReportUtility.highlightText(elesubPoint, driver);
            String subPoints = driver.findElement(subPoint).getText();

            int subPointsInt = 0;
            if (subPoints.contains("K")) {
                subPoints = subPoints.replace("K", "");
                subPoints = subPoints.replaceAll("\\s", "");
                float subPointsFloat = Float.parseFloat(subPoints);
                subPointsFloat = subPointsFloat * 1000;
                subPointsInt = (int) subPointsFloat;

            }
            System.out.println("subPoints: " + subPointsInt);

            By first_Check = By.xpath("//php-score-card[4]//div[1]//div[1]//h1[1]");
            waiting.until(ExpectedConditions.visibilityOfElementLocated(first_Check));
            WebElement elefirst_Check = driver.findElement(first_Check);
            ReportUtility.highlightText(elefirst_Check, driver);
            String first_Checks = driver.findElement(subApp).getText();
            System.out.println("first_Checks: " + first_Checks);


            By licencedAgent = By.xpath("//php-score-card[5]//div[1]//div[1]//h1[1]");
            waiting.until(ExpectedConditions.visibilityOfElementLocated(licencedAgent));
            WebElement elelicencedAgent = driver.findElement(licencedAgent);
            ReportUtility.highlightText(elelicencedAgent, driver);
            String licencedAgents = driver.findElement(subApp).getText();
            System.out.println("licencedAgents: " + licencedAgents);

            Thread.sleep(1000);
            System.out.println("-----Comparison of two------------");
            if (agentNumbersInt == recruitsInt) {
                System.out.println("Agent Number matches");
            } else {
                System.out.println("Agent Number doesn't matches");
            }
            if (applicationNumbersInt == subAppsInt) {
                System.out.println("Application Number matches");
            } else {
                System.out.println("Application Number doesn't matches");
            }

            if (pointsApp == subPointsInt) {
                System.out.println("Application Points matches");
            } else {
                System.out.println("Application Points doesn't matches");
            }
            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {
            e.printStackTrace();
            result = Constants.KEYWORD_FAIL;
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
        }
        return result;
    }

    public String GET_VALUES(String object, String data) throws IOException {

        try {
            for (int i = 1; i < 500; i++) {
                Thread.sleep(10000);
                WebElement phpDate = driver.findElement(By.xpath("(//tr/td/div/ancestor::tr//td[2])[" + i + "]"));
                String PhpDate = phpDate.getText();
                WebElement arrowIcon = driver.findElement(By.xpath("(//tr/td/div/ancestor::tr//td/div/img)[" + i + "]"));

                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", arrowIcon);
                Thread.sleep(10000);
                if (driver.findElements(By.xpath("(//tr/td/div/ancestor::tr//td[3])")).isEmpty() != true) {
                    if (driver.findElement(By.xpath("(//tr/td/div/ancestor::tr//td[2])")).getText().contains("AIG Partners Group AGLA")) {

                        driver.findElement(By.xpath("//div[@class='breadcrumb-item']")).click();
                    } else if (driver.findElement(By.xpath("(//tr/td/div/ancestor::tr//td[3])")).getText().isEmpty()) {


                        driver.findElement(By.xpath("//div[@class='breadcrumb-item']")).click();
                    } else {

                        WebDriverWait wait = new WebDriverWait(driver, 40);
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tr/td/div/ancestor::tr//td[2])")));
                        WebElement carrier_name = driver.findElement(By.xpath("(//tr/td/div/ancestor::tr//td[2])"));
                        String Carrier_Name = carrier_name.getText();
                        WebElement policy_number = driver.findElement(By.xpath("(//tr/td/div/ancestor::tr//td[3])"));
                        String Policy_Number = policy_number.getText();
                        WebElement payment_basis = driver.findElement(By.xpath("(//tr/td/div/ancestor::tr//td[12])"));
                        String Payment_Basis = payment_basis.getText();
                        Payment_Basis = Payment_Basis.replace("$", "");
                        WebElement field_rate_value = driver.findElement(By.xpath("(//tr/td/div/ancestor::tr//td[13])"));
                        String Field_Rate_Value = field_rate_value.getText();
                        Field_Rate_Value = Field_Rate_Value.replace("%", "");
                        WebElement paid_commissions = driver.findElement(By.xpath("(//tr/td/div/ancestor::tr//td[14])"));
                        String Paid_Commissions = paid_commissions.getText();
                        Paid_Commissions = Paid_Commissions.replace("$", "");

                        localStorage.put("Payment_Basis", Payment_Basis);
                        localStorage.put("Field_Rate_Value", Field_Rate_Value);
                        localStorage.put("Paid_Commissions", Paid_Commissions);
                        localStorage.put("Carrier_Name", Carrier_Name);
                        localStorage.put("Policy_Number", Policy_Number);
                        localStorage.put("PhpDate", PhpDate);

                        CHANGE_DATE_FORMAT(localStorage.get("PhpDate"));
                        break;
                    }
                } else {
                    driver.findElement(By.xpath("//div[@class='breadcrumb-item']")).click();
                }
            }

            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {
            e.printStackTrace();
            result = Constants.KEYWORD_FAIL;
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
        }
        return result;
    }

    public int GET_ROW_COUNT(JSONObject records) throws SQLException {
        int rowCount = 0;
        if (((ResultSet) records).last()) {// make cursor to point to the last row in the ResultSet object
            rowCount = ((ResultSet) records).getRow();
            ((ResultSet) records).beforeFirst(); // make cursor to point to the front of the ResultSet object, just before the
            // first row.
        }
        return rowCount;
    }

    public String EXTRACT_AGENT_ID_FROM_DB(String object, String Query) {

        String result;
        try {
            JSONObject records = new PostGresUtitity().executeQuery(Query);
            DriverScript.nodes.log(Status.DEBUG, "Query Executed " + Query);
            DriverScript.nodes.log(Status.DEBUG, "Total Record/s Found: " + GET_ROW_COUNT(records));
            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
            result = Constants.KEYWORD_FAIL;
        }
        return result;
    }

    public String RUN_QUERY(String object, String Query) {
        String result;
        String Records = null;
        try {
            localStorage = new HashMap<String, String>();
            JSONObject records = new PostGresUtitity().executeQuery(Query);
            Records = records.toString();

            localStorage.put("QueryResults", Records);

            DriverScript.nodes.log(Status.DEBUG, "Query Executed " + Query);
            //DriverScript.nodes.log(Status.DEBUG, "Total Record/s Found: " + GET_ROW_COUNT(records));

            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
            result = Constants.KEYWORD_FAIL;
        }
        return result;
    }

    public String captureScreenshot(String filename, String keyword_execution_result) throws IOException {
        // take screen shots
        String strPath = null;
        try {
            if (screenshotfoldercreate) {
                filePath = System.getProperty("user.dir") + "//reports//Screenshots//";
                File f = new File(filePath);
                if (f.exists() == false) {
                    f.mkdirs();
                }
                screenshotfoldercreate = false;
            }
        } catch (Exception e) {
            screenshotfoldercreate = false;
            filePath = System.getProperty("user.dir") + "//reports//screenshots//";
        }
        try {
            if (TestDataReader.screenshot_everystep.equals("Y")) {
                File DestFile = new File(filePath + filename + ".jpg");
                File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(scrFile, DestFile);
                strPath = DestFile.getAbsolutePath();
                //new AWSS3utility().uploadFileToS3("Screenshots/" + DestFile.getName(), DestFile);
                System.out.println(strPath);
            } else if (keyword_execution_result.startsWith(Constants.KEYWORD_FAIL)
                    && TestDataReader.screenshot_error.equals("Y")) {
                File DestFile = new File(filePath + filename + ".jpg");
                File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(scrFile, DestFile);
                strPath = "./Screenshots/" + DestFile.getName();
                System.out.println("IN ERROR CONDITION:" + strPath);
                DriverScript.nodes.fail("details", MediaEntityBuilder.createScreenCaptureFromPath(strPath).build());
                //new AWSS3utility().uploadFileToS3("Screenshots/" + DestFile.getName(), DestFile);
            }
        } catch (Exception e) {
        }
        return strPath;
    }

    public String UPLOAD_FROM_URL(String object, String data) throws Exception {
        String strPath = "";
        By by = null;
        try {
            by = OBJECT_TYPE_IDENTIFIER(object, data);
            downloadImage(data,
                    new File("").getAbsolutePath());
            strPath = System.getProperty("user.dir") + "/Document/" + docName;
            System.out.println("path:" + strPath);
            driver.findElement(by).sendKeys(strPath);

            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
            result = Constants.KEYWORD_FAIL;
        }
        return result;
    }

    public static void downloadImage(String sourceUrl, String targetDirectory)
            throws MalformedURLException, IOException, FileNotFoundException {
        URL imageUrl = new URL(sourceUrl);
        try (InputStream imageReader = new BufferedInputStream(
                imageUrl.openStream());
             OutputStream imageWriter = new BufferedOutputStream(
                     new FileOutputStream(System.getProperty("user.dir") + "/Document/" + File.separator
                             + FilenameUtils.getName(sourceUrl)));) {
            int readByte;

            while ((readByte = imageReader.read()) != -1) {
                imageWriter.write(readByte);
            }
            docName = FilenameUtils.getName(sourceUrl);
        }
    }

    public String COMPARE_PREMIUM_VALUE_WITH_LEGACY(String object, String data) throws IOException {

        try {
            Map<String, String> ciceroData = new HashMap<>();
            WebElement base_premium = driver.findElement(By.xpath("//tr/td/ancestor::tr[@role='row']//td[10]"));
            String Base_premium = base_premium.getText();
            WebElement rate = driver.findElement(By.xpath("//tr/td/ancestor::tr[@role='row']//td[11]"));
            String Rate = rate.getText();
            WebElement point = driver.findElement(By.xpath("//tr/td/ancestor::tr[@role='row']//td[12]"));
            String Point = point.getText();
            ciceroData.put("Payment_Basis", Base_premium);
            ciceroData.put("Field_Rate_Value", Rate);
            ciceroData.put("Paid_Commissions", Point);


            ciceroData.forEach((key, value) -> {
                System.out.println(" Key = " + key);
                System.out.println(" Value  = " + value + " localstorage " + localStorage.get(key));
                if ((localStorage.get(key)).trim().equals(ciceroData.get(key).trim())) {
                    System.out.println(key + " values are matching ");
                    result = Constants.KEYWORD_PASS;
                } else {
                    System.out.println(key + " values are not matching " + "[expected]" + localStorage.get(key) + " [Actual] " + value);
                    result = Constants.KEYWORD_FAIL;
                }
            });


        } catch (Exception e) {

            DriverScript.nodes.log(Status.FAIL, e);
            result = Constants.KEYWORD_FAIL;
        }
        return result;
    }

    public String CONTEXT_CLICK(String object, String data) throws IOException {
        By by = null;
        try {
            by = OBJECT_TYPE_IDENTIFIER(object, data);
            if (data.contains("POLICY_NUMBER")) {
                data = localStorage.get("Policy_Number");
                System.out.println("POLICY_NUMBER : " + data);
            } else if (data.contains("CARRIER_NAME")) {
                data = localStorage.get("Carrier_Name");
                System.out.println("CARRIER_NAME : " + data);
            }

            WebElement objEle = driver.findElement(by);
            Highlight(object, data);
            Actions action = new Actions(driver);
            action.click(objEle).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.RETURN).build().perform();
            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {
            result = Constants.KEYWORD_FAIL + " Not able to click";
            DriverScript.nodes.log(Status.FAIL, "Unable to " + DriverScript.TCSDesc);
            DriverScript.nodes.log(Status.FAIL, e);
        }
        return result;
    }

    public String VALIDATE_DB_DATA_WITH_UI_DATA(String object, String data) throws IOException {

        try {
            Map<String, String> profileData = new HashMap<String, String>();
            String agentname = driver.findElement(By.xpath("//div[@class and contains(text(),'Full Name')]/following-sibling::div")).getText();
            String php_code = driver.findElement(By.xpath("//div[@class and contains(text(),'PHP Code')]/following-sibling::div")).getText();
            String level = driver.findElement(By.xpath("//div[@class and contains(text(),'Level')]/following-sibling::div")).getText();
            profileData.put("agentname", agentname);
            profileData.put("php_code", php_code);
            profileData.put("level", level);

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(localStorage.get("QueryResults"));
            localStorage.put("agentname", json.get("agentname").toString());
            localStorage.put("php_code", json.get("php_code").toString());
            localStorage.put("level", json.get("level").toString());

            profileData.forEach((key, value) -> {

                if (localStorage.get(key).trim().equals(profileData.get(key).trim())) {

                    result = Constants.KEYWORD_PASS;
                } else {
                    System.out.println(key + " Values are not matching " + " [expected] " + localStorage.get(key) + " ||" + " [Actual] " + value);
                    DriverScript.nodes.log(Status.DEBUG, key + " Values are not matching " + " [expected] " + localStorage.get(key) + " ||" + " [Actual] " + value);
                    result = Constants.KEYWORD_FAIL;
                }
            });

        } catch (Exception e) {
            result = Constants.KEYWORD_FAIL;

            DriverScript.nodes.log(Status.FAIL, e);
        }
        return result;
    }

    public String CHANGE_DATE_FORMAT(String data) throws ParseException {

        try {
            SimpleDateFormat input = new SimpleDateFormat("MM/dd/yy");
            Date ciceroData = input.parse(data);

            SimpleDateFormat output = new SimpleDateFormat("MM/dd/YYYY");
            System.out.println("" + output.format(ciceroData) + " real date " + data);
            localStorage.put("ciceroDate", output.format(ciceroData));
            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {
            result = Constants.KEYWORD_FAIL;

            DriverScript.nodes.log(Status.FAIL, e);
        }
        return result;
    }

    public String DATA_PROVIDE_URL_ID(String object, String data) throws IOException {
        WAIT_FOR_ANGULAR_REQUEST_TO_FINISH(object, data);
        try {
//			driver.navigate().to("https://lockton-cms.seventablets.com/DLE/2/dataproviders/"+data+"/sftp");
            int all_left_links = driver.findElements(By.xpath("/html/body/app/div/div/dashboard/div[2]/div/dataproviders/div/div[2]/div[1]/div[3]/div/div")).size();
//			System.out.println(all_left_links + " all_left_links  size ");
            int matcher = 0;
            WebElement teEl1 = null;
            for (int i = 1; i <= all_left_links; i++) {
                teEl1 = driver.findElement(By.xpath("/html/body/app/div/div/dashboard/div[2]/div/dataproviders/div/div[2]/div[1]/div[3]/div/div[" + i + "]/div/div[1]"));
                if (data.equalsIgnoreCase(teEl1.getText())) {
                    matcher = i;
                }
                System.out.println(matcher + " matcher");
            }
            if (matcher > 0) {
                teEl1 = driver.findElement(By.xpath("/html/body/app/div/div/dashboard/div[2]/div/dataproviders/div/div[2]/div[1]/div[3]/div/div[" + matcher + "]/div/div[1]"));
                teEl1.click();
            } else {
                System.out.println("No Match for Carrier Name found");
            }
//			System.out.println(teEl1.getText() + " clicked on ");
            result = Constants.KEYWORD_PASS;
        } catch (Exception e) {

            result = Constants.KEYWORD_FAIL;

            DriverScript.nodes.log(Status.FAIL, e);
        }
        return result;
    }

    public String WAIT_FOR_ANGULAR_REQUEST_TO_FINISH(String object, String data) throws IOException {
        try {
            Thread.sleep(2000);
            ngWebDriver.waitForAngularRequestsToFinish();
            result = Constants.KEYWORD_PASS;

        } catch (Exception e) {

            result = Constants.KEYWORD_FAIL;

            DriverScript.nodes.log(Status.FAIL, e);
        }
        return result;
    }

    public String ANGULAR_MATCHED_FTP_FILE_AND_YEAR_VALUE(String object, String data) throws IOException {
        try {
            Thread.sleep(2000);
            int all_elements = driver.findElements(By.xpath("/html/body/app/div/div/dashboard/div[2]/div/dataproviders/div/div[3]/div[2]/div/sftp/div/div[2]/sftpspec")).size();
//			System.out.println(all_elements + " all_elements");
            String[] val = data.split(",");
            String FTP_FILE_VAL = val[0];
//			System.out.println("FTP_FILE_VAL " + val[0]);
            String YEAR_VAL = val[1];
//			System.out.println("YEAR_VAL " + val[1]);
            int matchedElement = 0;
            for (int i = 1; i <= all_elements; i++) {
                int numberOfTDs = driver.findElements(By.xpath("/html/body/app/div/div/dashboard/div[2]/div/dataproviders/div/div[3]/div[2]/div/sftp/div/div[2]/sftpspec[" + i + "]/div/div/div[2]/div[2]/div/md-input-container[2]/input")).size();
                WebElement teEl1 = driver.findElement(By.xpath("/html/body/app/div/div/dashboard/div[2]/div/dataproviders/div/div[3]/div[2]/div/sftp/div/div[2]/sftpspec[" + i + "]/div/div/div[2]/div[2]/div/md-input-container[2]/input"));
                String textEl1 = ngWebDriver.retrieveAsString(teEl1, "self.ssSpec.ftpfilenamepattern");
                WebElement teEl2 = driver.findElement(By.xpath("/html/body/app/div/div/dashboard/div[2]/div/dataproviders/div/div[3]/div[2]/div/sftp/div/div[2]/sftpspec[" + i + "]/div/div/div[2]/div[4]/div/div[2]/div[2]/md-input-container[2]/input"));
                String textEl2 = ngWebDriver.retrieveAsString(teEl2, "tag.value");
//				System.out.println(numberOfTDs + " numberOfTDs," +textEl1 + " textEl1," +textEl2 + " textEl2," );
                if (textEl1.equalsIgnoreCase(FTP_FILE_VAL) && textEl2.equalsIgnoreCase(YEAR_VAL)) {
                    System.out.println("Match found");
                    matchedElement = i;
                    localStorage.put("matchedElement", String.valueOf(matchedElement));
                    break;
                }
                result = Constants.KEYWORD_PASS;
            }
            if (matchedElement == 0) {
                System.err.println("No match found");
                result = Constants.KEYWORD_FAIL;
            }
        } catch (Exception e) {
            result = Constants.KEYWORD_FAIL;
            DriverScript.nodes.log(Status.FAIL, e);
        }
        return result;
    }

    /**
     * @param object
     * @param data   using a json string as input for it.
     *               {
     *               "base_url":"https://qa-oneworld.thephpagency.com/",
     *               "uri":"v1/agents/me",
     *               "is_login_required":true,
     *               "is_auth_token_required":true,
     *               "output_file":"meapi.csv",
     *               "login":{
     *               "uri":"v1/agents/login",
     *               "login_payload":{"php_code":"1000003","password":"phpAgent@1"}
     *               },
     *               "method":"GET",
     *               "is_payload":false,
     *               "payload":{}
     *               }
     * @return
     * @throws IOException
     */
    public String INVOKE_REST_API(String object, String data) throws IOException {
        try {
            Thread.sleep(2000);
            final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            // Parse the data to get the url and other informational entities.
            JSONParser parser = new JSONParser();
            JSONObject jsonObject;
            if (data.isEmpty()) {
                System.err.println("data can not be empty field.");
            }
            Object inputDataObject = parser.parse(data);
            jsonObject = (JSONObject) inputDataObject;
            OkHttpClient client = new OkHttpClient();

            String baseUrl = (String) jsonObject.get("base_url");
            String uri = (String) jsonObject.get("uri");
            boolean isOutputRequired = (Boolean) jsonObject.get("is_output");
            String outputFileName = (String) jsonObject.get("output_file");
            boolean isTokenRequired = (Boolean) jsonObject.get("is_auth_token_required");
            String providedToken = (String) jsonObject.get("token");
            System.out.println("URL to hit:" + baseUrl + uri);
            boolean isLoginRequired = (Boolean) jsonObject.get("is_login_required");
            boolean isPayload = (Boolean) jsonObject.get("is_payload");
            String method = (String) jsonObject.get("method");
            String emailSubject = (String) jsonObject.get("email_subject");
            String emailText = (String) jsonObject.get("email_text");
            String recipients = (String) jsonObject.get("email_recipients");
            JSONObject payload = (JSONObject) jsonObject.get("payload");

            System.out.println("isLoginRequired:" + isLoginRequired + ", isPayload:" + isPayload + " , method:" + method + ", payload:" + payload);

            String loginUrl = "";
            JSONObject loginPayload = null;
            String token = "";
            if (isLoginRequired) {
                System.out.println("in Login");
                JSONObject loginObject = (JSONObject) jsonObject.get("login");
                loginUrl = (String) loginObject.get("uri");
                loginPayload = (JSONObject) loginObject.get("login_payload");
                System.out.println("loginpayload:" + loginPayload);
                System.out.println("Urltohitforlogin : " + baseUrl + loginUrl);
                Request request = new Request.Builder().url(baseUrl + loginUrl)
                        .post(RequestBody.create(JSON, loginPayload.toString()))
                        .build();
                Response response = client.newCall(request).execute();
//                System.out.println("response here:" + response.body().string());
                ResponseBody body = response.body();
                JSONParser parser2 = new JSONParser();
                Object obj = parser2.parse(body.string());
//                System.out.println("obj"+ obj);
                jsonObject = (JSONObject) obj;
                token = (String) jsonObject.get("token");
            } else {
                token = providedToken;
            }
            System.out.println("token is saved as " + token);

            ResponseBody body2 = null;
            try {
//            System.out.println("token:"+token);
                Request requestApi = null;
                if (method.equalsIgnoreCase("GET")) {
                    HttpUrl.Builder httpBuilder = HttpUrl.parse(baseUrl + uri).newBuilder();
                    if (isPayload) {
                        for (Object param : payload.keySet()) {
                            httpBuilder.addQueryParameter((String) param, (String) payload.get(param));
                        }
                    }
                    System.out.println("connected URL: " + httpBuilder.build());
                    if (isTokenRequired) {
                        requestApi = new Request.Builder().url(httpBuilder.build())
                                .header("Authorization", "Bearer " + token)
                                .get()
                                .build();
                    } else {
                        requestApi = new Request.Builder().url(httpBuilder.build())
                                .get()
                                .build();
                    }
                } else if (method.equalsIgnoreCase("POST")) {
                    if (isTokenRequired) {
                        requestApi = new okhttp3.Request.Builder().url(baseUrl + uri)
                                .header("Authorization", "Bearer " + token)
                                .post(RequestBody.create(JSON, payload.toJSONString()))
                                .build();
                    } else {
                        requestApi = new Request.Builder().url(baseUrl + uri)
                                .post(RequestBody.create(JSON, payload.toJSONString()))
                                .build();
                    }
                }

                Response response2 = client.newCall(requestApi).execute();
//              System.out.println("response here:"+response.body().string());
                body2 = response2.body();
//              System.out.println(body2.string());
                int responseCode = response2.code();
                if (response2.isSuccessful()) {
                    if (responseCode < 100 || responseCode > 200) {
                        System.out.println("RESPONSE ");
                    } else if (responseCode == 200) {
                        String result = body2.string();
                        System.out.println("-----------------API OUTPUT--------------------");
                        System.out.println(result);
                        System.out.println("-----------------------------------------------");
                        if (isOutputRequired) {
                            JFlat flatMe = new JFlat(result);
                            flatMe.json2Sheet().headerSeparator("_").write2csv(System.getProperty("user.dir") + "//Document//" + outputFileName);
                        }
                    }
                    result = Constants.KEYWORD_PASS;
                } else {
                    if (responseCode >= 400 && responseCode < 500) {
                        System.out.println("BACKEND ISSUE");
                        System.out.println("BACKEND Response Code:" + responseCode);

                        Properties props = new Properties();
                        props.put("mail.smtp.host", "smtp.gmail.com");
                        props.put("mail.smtp.socketFactory.port", "465");
                        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                        props.put("mail.smtp.auth", "true");
                        props.put("mail.smtp.port", "465");

                        Session session = Session.getDefaultInstance(props,
                                new javax.mail.Authenticator() {
                                    protected PasswordAuthentication getPasswordAuthentication() {
                                        return new PasswordAuthentication("test@seventablets.com", "phpAdmin@1");
                                    }
                                });
                        MimeMessage message = new MimeMessage(session);

//                        message.addRecipient(Message.RecipientType.TO, new InternetAddress("ravi.d@seventablets.com"));
                        String[] addresses = recipients.split(";");
                        for (String address : addresses) {
                            message.addRecipients(Message.RecipientType.TO, address);
                        }
                        message.setSubject(emailSubject);
                        message.setText(emailText + "\nThere is issue in the following URI: " + uri + "\nIt returned " + responseCode + " ResponseCode.");
                        //send message
                        Transport.send(message);
                        System.out.println("message sent successfully");
                        result = Constants.KEYWORD_FAIL;
                    }
                }
            } catch (Exception e) {
                System.out.println("Exception occurred. Kindly check the message");
                System.out.println(e.getMessage());
                result = Constants.KEYWORD_FAIL;
            }
//            System.out.println(response.body().string());
            Thread.sleep(5000);
        } catch (Exception e) {
            result = Constants.KEYWORD_FAIL;
            DriverScript.nodes.log(Status.FAIL, e);
        }
        return result;
    }
}
