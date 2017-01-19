package jsb.com.notetaker.AdaptersAndDataFiles;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static jsb.com.notetaker.AdaptersAndDataFiles.NoteDataBaseContract.TableContracts.SQL_CREATE_ENTRIES;
import static jsb.com.notetaker.AdaptersAndDataFiles.NoteDataBaseContract.TableContracts.SQL_DELETE_ENTRIES;

/**
 * Created by Jagjit Singh on 1/16/2017.
 */

public class NoteDataBaseHelper extends SQLiteOpenHelper {

	public final static int DATABASE_VERSION = 1;
	public final static String DATABASE_NAME = "notesDateBase.db";

	public NoteDataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//Called when the data base is created for the first time
		db.execSQL(SQL_CREATE_ENTRIES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);
	}
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}

}
