package jsb.com.notetaker.AdaptersAndDataFiles;

import android.content.Context;

/**
 * Created by Jagjit Singh on 12/29/2016.
 */

public class NoteDataController {

	public static String initialNoteTitle = "";
	public static String initialNoteBody = "";
	public static Note.Category initialCategory = Note.Category.PRIVATE;
	public static int chosenNoteID = 0;
    public static DataFile dataFile;

    public static DataFile createDataFile(Context c){
       dataFile = new DataFile(c);
        return dataFile;
    }
    public static DataFile getDataFile(){
        return dataFile;
    }

}
