package utility;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import oneWorld.Automation.Executor;

public class ReportUtility extends Executor {
	public static ExtentReports report = reportsConfig();
	public static ExtentHtmlReporter htmlReporter;

	// = new
	// ExtentHtmlReporter(System.getProperty("user.dir")+"\\reports\\AutomationResult.html");
	/**
	 * @return
	 */
	public static ExtentHtmlReporter htmlConfiguration() {
		// ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(dynamicPath() +
		// "\\AutomationResult.html");
		File reportPath = new File(System.getProperty("user.dir") + "/reports");
		if (reportPath.exists()) {
			try {
				FileUtils.cleanDirectory(reportPath);
				FileUtils.deleteDirectory(reportPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		 if (!reportPath.exists()) {
			reportPath.mkdir();
		}
		htmlReporter = new ExtentHtmlReporter(
				System.getProperty("user.dir") + "/reports/" + Executor.module + "_AutomationResult.html");
		htmlReporter.config().setDocumentTitle("Automation Report"); // Tile of report
		htmlReporter.config().setReportName("Functional Testing"); // Name of the report
		htmlReporter.config().setTheme(Theme.DARK);
		return htmlReporter;
	}

	/**
	 * @param element
	 */
	// highlightText on screen
	public static void highlightText(WebElement element, WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// use executeScript() method and pass the arguments
		// Here i pass values based on css style. Yellow background color with solid red
		// color border.
		js.executeScript("arguments[0].setAttribute('style', 'background: LightGray; border: 1px solid red;');", element);
	}

	/**
	 * @return
	 */
	public static ExtentReports reportsConfig() {
		report = new ExtentReports();
		report.attachReporter(htmlConfiguration());
		return report;
	}

	public static void generateCompressedReport() throws IOException {
		DateUtility date = new DateUtility();
	
		
	
		ZipFolder.createZipWithFolderInternalElements(System.getProperty("user.dir") + "//reports",
				System.getProperty("user.dir") + "//reports.zip");
		// System.getProperty("user.dir") + "//reports_"+date.getCurrentTime()+".zip");
	}

	public static void cleanScreenshotDirectory() throws IOException {
		File source = new File(System.getProperty("user.dir") + "//reports//Screenshots//");
		File reportPath = new File(System.getProperty("user.dir") + "//reports//");
		if (source.exists()) {
			FileUtils.cleanDirectory(source);
			FileUtils.deleteDirectory(source);
		}
	}
}