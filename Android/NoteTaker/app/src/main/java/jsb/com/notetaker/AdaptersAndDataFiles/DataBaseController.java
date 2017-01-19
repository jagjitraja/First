package jsb.com.notetaker.AdaptersAndDataFiles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import jsb.com.notetaker.Activities.MainActivity;

/**
 * Created by Jagjit Singh on 1/17/2017.
 */
public class DataBaseController {
	private static DataBaseController ourInstance = new DataBaseController();

	public static DataBaseController getInstance() {
		return ourInstance;
	}

	private NoteDataBaseHelper dataBaseHelper;

	public DataBaseController() {

	}

	public void initializeDatabase(Context context){
		dataBaseHelper = new NoteDataBaseHelper(context);
	}

	public void write_Note(Note note){

		ContentValues values = new ContentValues();
		SQLiteDatabase database = dataBaseHelper.getWritableDatabase();

		values.put(NoteDataBaseContract.TableContracts._ID,note.getID());
		values.put(NoteDataBaseContract.TableContracts.COLUMN_TITLE,note.getTitle());
		values.put(NoteDataBaseContract.TableContracts.COLUMN_DATE,note.getDate());
		values.put(NoteDataBaseContract.TableContracts.COLUMN_BODY,note.getBody());
		values.put(NoteDataBaseContract.TableContracts.COLUMN_CATEGORY,note.getCategory()+"");

		long id = database.insertOrThrow(NoteDataBaseContract.TableContracts.TABLE_NAME,null,values);

		Log.d(MainActivity.APP_ID_KEY,id+"=====================================");
		database.close();
		dataBaseHelper.close();

	}

	public ArrayList<Note> read_data(){


		SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
		ArrayList<Note> notes = new ArrayList<>();

		String [] columnStructure = {
				NoteDataBaseContract.TableContracts._ID,
				NoteDataBaseContract.TableContracts.COLUMN_TITLE,
				NoteDataBaseContract.TableContracts.COLUMN_DATE,
				NoteDataBaseContract.TableContracts.COLUMN_BODY,
				NoteDataBaseContract.TableContracts.COLUMN_CATEGORY
		};

		String selectionCriteria = "";
		String [] selectionArguments ={};
		String sortOrder = "";

		Cursor cursor = database.query(NoteDataBaseContract.TableContracts.TABLE_NAME,columnStructure,selectionCriteria,
				selectionArguments,null,null,sortOrder);

		String title = "";
		String body = "";
		String date = "";
		String category = "";
		int i = 0;

		while (cursor.moveToNext()){
			title = cursor.getString(cursor.getColumnIndexOrThrow(NoteDataBaseContract.TableContracts.COLUMN_TITLE));
			body = cursor.getString(cursor.getColumnIndexOrThrow(NoteDataBaseContract.TableContracts.COLUMN_BODY));
			date = cursor.getString(cursor.getColumnIndexOrThrow(NoteDataBaseContract.TableContracts.COLUMN_DATE));
			category = cursor.getString(cursor.getColumnIndexOrThrow(NoteDataBaseContract.TableContracts.COLUMN_CATEGORY));
			notes.add(new Note(title,body,date,Note.getCategoryFromString(category),i));
			i++;
		}
		cursor.close();
		database.close();
		dataBaseHelper.close();
		return notes;
	}

	public void deleteNote(Note note){

		SQLiteDatabase database = dataBaseHelper.getWritableDatabase();

		int x = database.delete(NoteDataBaseContract.TableContracts.TABLE_NAME, NoteDataBaseContract.TableContracts._ID+"=?",new String[]{note.getID()+""});

		database.close();
		dataBaseHelper.close();
		Log.d(MainActivity.APP_ID_KEY,note.getID()+"-------------"+x);
	}

	public void updateNote(Note note){

		ContentValues contentValues = new ContentValues();
		contentValues.put(NoteDataBaseContract.TableContracts._ID,note.getID());
		contentValues.put(NoteDataBaseContract.TableContracts.COLUMN_TITLE,note.getTitle());
		contentValues.put(NoteDataBaseContract.TableContracts.COLUMN_CATEGORY,note.getCategory().toString());
		contentValues.put(NoteDataBaseContract.TableContracts.COLUMN_BODY,note.getBody());
		contentValues.put(NoteDataBaseContract.TableContracts.COLUMN_DATE,note.getDate());

		SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
		database.update(NoteDataBaseContract.TableContracts.TABLE_NAME,contentValues, NoteDataBaseContract.TableContracts._ID +" = ?",new String[]{note.getID()+""});

		contentValues.clear();
		database.close();
		dataBaseHelper.close();


		Log.d(MainActivity.APP_ID_KEY,"UPDATED");
	}





}
