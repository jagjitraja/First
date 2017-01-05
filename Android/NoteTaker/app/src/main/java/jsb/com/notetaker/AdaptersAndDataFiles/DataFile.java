package jsb.com.notetaker.AdaptersAndDataFiles;

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
 * Created by Jagjit Singh on 12/25/2016.
 */

public class DataFile {

	private final String fileName = "data.txt";
	private static File dataSource;
	private FileOutputStream fileOutputStream;
	private FileInputStream fileReader;
	private Context context;

	public DataFile(Context c){
		context =c;
		dataSource = c.getFilesDir();
		Log.d(MainActivity.APP_ID_KEY,dataSource.toString());
		File [] files = context.getFilesDir().listFiles();

		Log.d(MainActivity.APP_ID_KEY,files.length+"");

	}

	public void write_data(ArrayList<Note> notes){

		Log.d(MainActivity.APP_ID_KEY,"WRITING DATA");
		if(dataSource!=null){
			try {
				fileOutputStream = context.openFileOutput(fileName,Context.MODE_APPEND);
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
		} else{
			try {
				fileOutputStream = context.openFileOutput(fileName,Context.MODE_PRIVATE);
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
	}

	public ArrayList<Note> read_data(){
		ArrayList notesRead = new ArrayList<Note>();
		if(dataSource!=null){
			ObjectInputStream objectReader;
			try {
				fileReader = new FileInputStream(dataSource);
				objectReader = new ObjectInputStream(fileReader);

				notesRead = (ArrayList)objectReader.readObject();

				objectReader.close();
				fileReader.close();
			}catch (FileNotFoundException e){
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return notesRead;
	}
}
