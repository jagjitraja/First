package jsb.com.notetaker.AdaptersAndDataFiles;

import android.provider.BaseColumns;

/**
 * Created by Jagjit Singh on 1/16/2017.
 */
//class to organise and design database
public class NoteDataBaseContract {

	private NoteDataBaseContract (){}

	public static class TableContracts implements BaseColumns {


		public static final String TABLE_NAME="notes_table";

		//TABLE COLUMN NAMES
		public static final String COLUMN_CATEGORY = "category_column";
		public static final String COLUMN_TITLE = "title_column";
		public static final String COLUMN_BODY = "body_column";
		public static final String COLUMN_DATE = "date_column";

		public static final String SQL_CREATE_ENTRIES =
				"CREATE TABLE " + TableContracts.TABLE_NAME + " (" +
						TableContracts._ID + " INTEGER KEY," +
						TableContracts.COLUMN_CATEGORY + " CATEGORY," +
						TableContracts.COLUMN_TITLE + " TITLE,"+
						TableContracts.COLUMN_BODY+" BODY,"+
						TableContracts.COLUMN_DATE+" DATE)";

		public static final String SQL_DELETE_ENTRIES =
				"DROP TABLE IF EXISTS " + TableContracts.TABLE_NAME;
	}



}
