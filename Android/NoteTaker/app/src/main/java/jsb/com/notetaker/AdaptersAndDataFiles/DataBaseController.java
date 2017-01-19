package jsb.com.notetaker.AdaptersAndDataFiles;

/**
 * Created by Jagjit Singh on 1/17/2017.
 */
public class DataBaseController {
	private static DataBaseController ourInstance = new DataBaseController();

	public static DataBaseController getInstance() {
		return ourInstance;
	}

	private DataBaseController() {
	}
}
