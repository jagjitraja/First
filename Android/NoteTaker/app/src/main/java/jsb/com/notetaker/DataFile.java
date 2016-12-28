package jsb.com.notetaker;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Jagjit Singh on 12/25/2016.
 */

public class DataFile {

	private final String fileName = "data.txt";
	private File dataSource;
	private FileOutputStream fileOutputStream;
	private FileInputStream fileReader;
	private Context context;

	public DataFile(Context c){
		context =c;

		File [] files = context.getFilesDir().listFiles();
		dataSource = new File(c.getFilesDir(),fileName);
	}

	public void write_data(ArrayList<Note> notes){

		if(dataSource.exists()){
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
		if(dataSource.exists()){
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
