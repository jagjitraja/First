package jsb.com.notetaker.AdaptersAndDataFiles;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import jsb.com.notetaker.Activities.MainActivity;

/**
 * Created by Jagjit Singh on 12/29/2016.
 */

public class NoteDataController extends Application {

	public static String initialNoteTitle = "";
	public static String initialNoteBody = "";
	public static Note.Category initialCategory = Note.Category.PRIVATE;
	public static int chosenNoteID = 0;

	private static ArrayList readNotes;
	private static final String fileName = "data.txt";
	private static FileOutputStream fileOutputStream;
	private FileInputStream fileReader;
	private static Context context;


	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		readNotes = read_data();

		for (File x : context.getFilesDir().listFiles()) {
			Log.d(MainActivity.APP_ID_KEY, x.getName());
		}
	}

	public static void setReadNotesOnTerminate(ArrayList notesOnTerminate){
		readNotes = notesOnTerminate;
		write_data(notesOnTerminate);
	}

	public void onPause() {
		Log.d(MainActivity.APP_ID_KEY,"TERMINATING");

		super.onTerminate();
	}

	public static void write_data(ArrayList<Note> notes) {

		Log.d(MainActivity.APP_ID_KEY, "WRITING DATA");

		try {
			fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			ObjectOutputStream objectWriter = new ObjectOutputStream(fileOutputStream);
			objectWriter.writeObject(notes);
			objectWriter.flush();
			objectWriter.close();
			fileOutputStream.flush();
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public ArrayList<Note> read_data() {
		ArrayList<Note> notesRead = new ArrayList<Note>();

		ObjectInputStream objectReader;

		try {
			fileReader = context.openFileInput(fileName);

			Log.d(MainActivity.APP_ID_KEY,"READING DATA ------------------ ");

			objectReader = new ObjectInputStream(fileReader);
			notesRead = (ArrayList<Note>) objectReader.readObject();

			for(Note v: notesRead){
				Log.d(MainActivity.APP_ID_KEY,"THIS NOTE WAS READ => "+v.toString());
			}


			Log.d(MainActivity.APP_ID_KEY, notesRead.size() + "-------------------------------");
			objectReader.close();
			fileReader.close();
		} catch (FileNotFoundException e) {

			try {
				fileReader = context.openFileInput(fileName);
				Log.d(MainActivity.APP_ID_KEY,"READING DATA ------------------ ");

				objectReader = new ObjectInputStream(fileReader);
				notesRead = (ArrayList<Note>) objectReader.readObject();

				for(Note v: notesRead){
					Log.d(MainActivity.APP_ID_KEY,"THIS NOTE WAS READ => "+v.toString());
				}


				Log.d(MainActivity.APP_ID_KEY, notesRead.size() + "-------------------------------");
				objectReader.close();
				fileReader.close();

				Log.d(MainActivity.APP_ID_KEY,"CRASHED HERE FILE NOT FOUND ");
				e.printStackTrace();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}


		} catch (IOException e) {

			Log.d(MainActivity.APP_ID_KEY,"CRASHED HERE IO EXCEPTION ");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			Log.d(MainActivity.APP_ID_KEY,"CRASHED HERE CLASS NOT FOUND ");
			e.printStackTrace();
		}



		readNotes = notesRead;
		return notesRead;
	}

	public static ArrayList getReadNotes(){
		if(readNotes==null){
			readNotes = new ArrayList();
		}
		return readNotes;
	}

}
