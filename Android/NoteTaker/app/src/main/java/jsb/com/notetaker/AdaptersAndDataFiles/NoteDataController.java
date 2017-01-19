package jsb.com.notetaker.AdaptersAndDataFiles;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by Jagjit Singh on 12/29/2016.
 */

public class NoteDataController extends Application {

	private static final String fileName = "data.txt";
	public static String initialNoteTitle = "";
	public static String initialNoteBody = "";
	public static Note.Category initialCategory = Note.Category.PRIVATE;
	public static String initialDate = "";
	public static int chosenNoteID = 0;
	private static ArrayList readNotes;
	private static FileOutputStream fileOutputStream;
	private static Context context;
	private FileInputStream fileReader;
	private NoteDataBaseHelper noteDataBaseHelper;

	public NoteDataController(){
	}



	public static ArrayList getReadNotes() {
		if (readNotes == null) {
			readNotes = new ArrayList();
		}
		return readNotes;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		DataBaseController dataBaseController = DataBaseController.getInstance();
		dataBaseController.initializeDatabase(context);
		readNotes = dataBaseController.read_data();
	}




	public static Typeface getTitleFont(){
		Typeface titleFont = Typeface.createFromAsset(context.getAssets(),"fonts/Kalam-Bold.ttf");
		return titleFont;
	}
	public static Typeface getBodyFont(){
		Typeface titleFont = Typeface.createFromAsset(context.getAssets(),"fonts/Kalam-Regular.ttf");
		return titleFont;
	}
	public static Typeface getDateFont(){
		Typeface titleFont = Typeface.createFromAsset(context.getAssets(),"fonts/Kalam-Light.ttf");
		return titleFont;
	}

}
