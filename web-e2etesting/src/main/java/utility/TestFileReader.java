package utility;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import oneWorld.Automation.Executor;
import oneWorld.Automation.TestDataReader;

public class TestFileReader {
	private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
	private static Properties CONFIG;

	/**
	 * Creates an authorized Credential object.
	 *
	 * @param HTTP_TRANSPORT
	 *            The network HTTP Transport.
	 * @return An authorized Credential object.
	 * @throws IOException
	 *             If the credentials.json file cannot be found.
	 */
	private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
		// Load client secrets.
		InputStream in = TestFileReader.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		if (in == null) {
			throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
		}
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public static void downloadFilesFromDrive(String path, String fileId) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

		// Print the names and IDs for up to 10 files.
		com.google.api.services.drive.model.FileList result = service.files().list().setPageSize(50)
				.setFields("nextPageToken, files(id, name)").execute();
		List<com.google.api.services.drive.model.File> files = result.getFiles();
		if (files == null || files.isEmpty()) {
			System.out.println("No files found.");
		} else {
			System.out.println("Files:");
			for (com.google.api.services.drive.model.File file : files) {
//				System.out.printf("%s (%s)\n", file.getName(), file.getId());
			}
		}

		System.out.println("Reading file.. ");

		File sampleFile = new java.io.File(path);
		if (!sampleFile.exists()) {
			sampleFile.createNewFile();
		}
		// OutputStream outputStream = new ByteArrayOutputStream();
		FileOutputStream fileStream = new FileOutputStream(sampleFile);
		// service.files().get(fileId).executeMediaAndDownloadTo(fileStream);
		// "1GuOg1-YIb5WnxSiP2zUBmAEGSMxHN65EaeH1sUTGj3E"
		service.files().export(fileId, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
				.executeMediaAndDownloadTo(fileStream);

//        driveService.files().get(fileId).executeMediaAndDownloadTo(out);
        System.out.println("File Download completed");
    }

    public static void uploadFileToDrive(String path) throws Exception{
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
		FileInputStream fs = new FileInputStream(
				System.getProperty("user.dir") + "//config//AppConfig//" + "config" + ".properties");
		CONFIG = new Properties();
		CONFIG.load(fs);
		boolean shouldCreate = false;
        com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
        fileMetadata.setName(Executor.module + "_AutomationResult.html");
        java.io.File filePath = new java.io.File(path);
        FileContent mediaContent = new FileContent("text/html", filePath);
		if(shouldCreate) {
			fileMetadata.setParents(Collections.singletonList(CONFIG.getProperty("report_folder_id")));
			com.google.api.services.drive.model.File file = service.files().create(fileMetadata, mediaContent)
					.setFields("id")
					.execute();
			System.out.println("File ID : " + file.getId());
		} else  {
			com.google.api.services.drive.model.File file = service.files().update(CONFIG.getProperty("reportFileID"), fileMetadata, mediaContent)
					.setFields("id")
					.execute();
		}
    }
}